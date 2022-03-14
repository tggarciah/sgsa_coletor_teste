package br.com.dtfoods.sgsacoletor.room.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.dtfoods.sgsacoletor.model.ImpressaoProdutoLote
import br.com.dtfoods.sgsacoletor.retrofit.SgsaColetorRetrofit
import br.com.dtfoods.sgsacoletor.room.dao.ImpressaoProdutoLoteDao
import br.com.dtfoods.sgsacoletor.room.dao.UsuarioDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

class ImpressaoProdutoLoteRepository(
  private val impressaoProdutoLoteDao: ImpressaoProdutoLoteDao,
  private val usuarioDao: UsuarioDao
) {
  private val impressaoProdutoLoteService by lazy {
    SgsaColetorRetrofit().impressaoProdutoLoteService
  }

  fun inserir(
    impressaoProdutoLote: ImpressaoProdutoLote,
    usuarioId: Long
  ): LiveData<ResultSgsaColetor<Void?>> {
    val liveData = MutableLiveData<ResultSgsaColetor<Void?>>()
    CoroutineScope(Dispatchers.IO).launch {
      try {
        val usuario = usuarioDao.buscarId(usuarioId)
        usuario.login?.let {
          impressaoProdutoLote.usuarioLogin = it
        }
        val requisicao = mutableMapOf("impressaoProdutoLote" to impressaoProdutoLote)
        val response = impressaoProdutoLoteService.salvar(requisicao)
        val retornoErro = response.body()?.retornoErro ?: throw IOException(MENSAGEM_ERRO_RESPOSTA)
        val impressaoProdutoLotes =
          response.body()?.impressaoProdutoLotes ?: throw IOException(MENSAGEM_ERRO_RESPOSTA)

        if (retornoErro.erroCodigo > 0) {
          throw IOException(retornoErro.erroDescricao)
        } else {
          impressaoProdutoLoteDao.inserir(impressaoProdutoLotes)
          liveData.postValue(ResultSgsaColetor.Success(data = null))
        }
      } catch (e: IOException) {
        liveData.postValue(
          ResultSgsaColetor.Error(exception = Exception(e.message))
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