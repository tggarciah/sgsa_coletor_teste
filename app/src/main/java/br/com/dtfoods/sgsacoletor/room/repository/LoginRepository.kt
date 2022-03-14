package br.com.dtfoods.sgsacoletor.room.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.dtfoods.sgsacoletor.model.Login
import br.com.dtfoods.sgsacoletor.model.RetornoErroServidor
import br.com.dtfoods.sgsacoletor.model.Usuario
import br.com.dtfoods.sgsacoletor.retrofit.SgsaColetorRetrofit
import br.com.dtfoods.sgsacoletor.room.dao.UsuarioDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

class LoginRepository(
  private val usuarioDao: UsuarioDao
) {
  private val loginService by lazy {
    SgsaColetorRetrofit().loginService
  }

  fun login(login: Login): LiveData<ResultSgsaColetor<Usuario>> {
    val liveData = MutableLiveData<ResultSgsaColetor<Usuario>>()
    CoroutineScope(Dispatchers.IO).launch {
      try {
        val requisicao = mutableMapOf("autenticacao" to login)
        val response = loginService.login(requisicao)
        if (response.isSuccessful) {
          val usuario = response.body()?.usuario ?: throw IOException(MENSAGEM_ERRO_RESPOSTA)
          val validadeAcesso =
            response.body()?.validadeAcesso ?: throw IOException(MENSAGEM_ERRO_RESPOSTA)
          val erroServidor =
            response.body()?.erroServidor ?: throw IOException(MENSAGEM_ERRO_RESPOSTA)

          if (erroServidor.erroCodigo > 0) {
            throw IOException(erroServidor.erroDescricao)
          }
          usuarioDao.inserir(usuario)
          liveData.postValue(ResultSgsaColetor.Success(data = usuarioDao.buscarId(usuario.id)))
        } else {
          throw IOException(MENSAGEM_ERRO_USUARIO)
        }
      } catch (e: IOException) {
        liveData.postValue(
          ResultSgsaColetor.Error(exception = e)
        )
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

  fun cadastrarSenha(login: Login): LiveData<ResultSgsaColetor<Void?>> {
    val liveData = MutableLiveData<ResultSgsaColetor<Void?>>()
    CoroutineScope(Dispatchers.IO).launch {
      try {
        val requisicao = mutableMapOf("autenticacao" to login)
        val response = loginService.cadastrarSenha(autenticacao = requisicao)
        val erroServidor =
          response.body()?.erroServidor ?: throw IOException(MENSAGEM_ERRO_RESPOSTA)

        if (erroServidor.erroCodigo > 0) {
          throw IOException(erroServidor.erroDescricao)
        }
        liveData.postValue(ResultSgsaColetor.Success(data = null))
      } catch (e: IOException) {
        liveData.postValue(
          ResultSgsaColetor.Error(exception = e)
        )
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

  fun recuperarSenha(login: Login): LiveData<ResultSgsaColetor<RetornoErroServidor>> {
    val liveData = MutableLiveData<ResultSgsaColetor<RetornoErroServidor>>()
    CoroutineScope(Dispatchers.IO).launch {
      try {
        val requisicao = mutableMapOf("autenticacao" to login)
        val response = loginService.recuperarSenha(autenticacao = requisicao)
        val erroServidor = response.body()?.erroServidor
        erroServidor ?: throw IOException(MENSAGEM_ERRO_RESPOSTA)

        if (erroServidor.erroCodigo > 0) {
          throw IOException(erroServidor.erroDescricao)
        }

        liveData.postValue(ResultSgsaColetor.Success(data = erroServidor))
      } catch (e: IOException) {
        liveData.postValue(
          ResultSgsaColetor.Error(exception = e)
        )
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
}