package br.com.dtfoods.sgsacoletor.room.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import br.com.dtfoods.sgsacoletor.model.Usuario
import br.com.dtfoods.sgsacoletor.retrofit.SgsaColetorRetrofit
import br.com.dtfoods.sgsacoletor.retrofit.service.UsuarioService
import br.com.dtfoods.sgsacoletor.room.dao.UsuarioDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException

class UsuarioRepository(
  private val usuarioDao: UsuarioDao,
  private val usuarioService: UsuarioService = SgsaColetorRetrofit().usuarioService,
) {

  private val mediator = MediatorLiveData<ResultSgsaColetor<List<Usuario>>>()

  fun buscarTodos(): LiveData<ResultSgsaColetor<List<Usuario>>> {
    mediator.addSource(buscarInterno()) {
      if (it.isEmpty()) {
        mediator.value = ResultSgsaColetor.Error(exception = Exception(MENSAGEM_SEM_OPERADORES))
      } else {
        mediator.value = ResultSgsaColetor.Success(data = it)
      }
    }
    mediator.addSource(buscarExterno()) {
      mediator.value = ResultSgsaColetor.Error(exception = Exception(it.exception.message))
    }
    return mediator
  }

  @WorkerThread
  suspend fun inserir(usuario: Usuario) {
    salvarInterno(usuario)
  }

  @WorkerThread
  suspend fun atualizar(usuario: Usuario) {
    usuarioDao.atualizar(usuario)
  }

  @WorkerThread
  fun remover(usuario: Usuario) {
    CoroutineScope(Dispatchers.IO).launch {
      usuarioDao.remover(usuario)
    }
  }

  fun buscaPorId(usuarioId: Long): LiveData<Usuario?> {
    return usuarioDao.buscarPorId(usuarioId)
  }

  private fun buscarInterno() = usuarioDao.buscarTodos()

  private fun buscarExterno(): LiveData<ResultSgsaColetor.Error> {
    val liveData = MutableLiveData<ResultSgsaColetor.Error>()
    CoroutineScope(Dispatchers.IO).launch {
      try {
        val usuarioResponse = usuarioService.buscaTodos()
        if (usuarioResponse.isSuccessful) {
          val operadores = usuarioResponse.body()?.operadores
          operadores?.let { salvarInterno(it) }
        } else {
          liveData.postValue(ResultSgsaColetor.Error(exception = Exception(MENSAGEM_ERRO_RESPOSTA)))
        }
      } catch (e: ConnectException) {
        liveData.postValue(
          ResultSgsaColetor.Error(exception = Exception("$MENSAGEM_ERRO_CONEXAO - ${e.message}"))
        )
      } catch (e: SocketTimeoutException) {
        liveData.postValue(
          ResultSgsaColetor.Error(exception = Exception("$MENSAGEM_ERRO_SOCKET - ${e.message}"))
        )
      } catch (e: Exception) {
        liveData.postValue(
          ResultSgsaColetor.Error(exception = Exception("ERRO: ${e.message}"))
        )
      }
    }
    return liveData
  }

  private suspend fun salvarInterno(usuarios: List<Usuario>) {
    usuarioDao.inserir(usuarios)
  }

  private suspend fun salvarInterno(usuario: Usuario) {
    usuarioDao.inserir(usuario)
  }
}