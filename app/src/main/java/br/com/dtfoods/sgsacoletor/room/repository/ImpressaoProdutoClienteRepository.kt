package br.com.dtfoods.sgsacoletor.room.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import br.com.dtfoods.sgsacoletor.model.Impressao
import br.com.dtfoods.sgsacoletor.model.ImpressaoProduto
import br.com.dtfoods.sgsacoletor.model.ImpressaoProdutoCliente
import br.com.dtfoods.sgsacoletor.retrofit.SgsaColetorRetrofit
import br.com.dtfoods.sgsacoletor.room.dao.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

class ImpressaoProdutoClienteRepository(
  private val clienteDao: ClienteDao,
  private val produtoDao: ProdutoDao,
  private val solicitacaoDao: SolicitacaoDao,
  private val impressaoDao: ImpressaoDao,
  private val impressaoProdutoDao: ImpressaoProdutoDao,
  private val impressaoProdutoLoteDao: ImpressaoProdutoLoteDao,
  private val impressaoProdutoClienteDao: ImpressaoProdutoClienteDao
) {
  private val clienteService by lazy {
    SgsaColetorRetrofit().clienteService
  }
  private val produtoService by lazy {
    SgsaColetorRetrofit().produtoService
  }
  private val impressaoProdutoService by lazy {
    SgsaColetorRetrofit().impressaoProdutoService
  }

  private val mediator = MediatorLiveData<ResultSgsaColetor<List<ImpressaoProdutoCliente>>>()

  fun buscarTodos(usuarioId: Long): LiveData<ResultSgsaColetor<List<ImpressaoProdutoCliente>>> {
    mediator.addSource(buscarInterno()) {
      if (it.isEmpty()) {
        mediator.value =
          ResultSgsaColetor.Error(exception = Exception(MENSAGEM_ERRO_PRODUTOS_PENDENTES))
      } else {
        mediator.value = ResultSgsaColetor.Success(data = it)
      }
    }
    mediator.addSource(buscarExterno(usuarioId)) {
      mediator.value = ResultSgsaColetor.Error(exception = it.exception)
    }
    return mediator
  }

  fun buscarPor(
    impressaoId: Long,
    impressaoProdutoId: Long
  ): LiveData<ImpressaoProdutoCliente> {
    return impressaoProdutoClienteDao.buscarPor(impressaoId, impressaoProdutoId)
  }

  private fun buscarInterno(): LiveData<List<ImpressaoProdutoCliente>> =
    impressaoProdutoClienteDao.buscaTodos()

  private fun buscarExterno(usuarioId: Long): LiveData<ResultSgsaColetor.Error> {
    val liveData = MutableLiveData<ResultSgsaColetor.Error>()
    CoroutineScope(Dispatchers.IO).launch {
      try {
        val clienteResponse = clienteService.buscarTodos()
        val clientes = clienteResponse.body()?.clientes ?: throw IOException(MENSAGEM_ERRO_CLIENTE)
        clienteDao.inserir(clientes)

        val produtoResponse = produtoService.buscarTodos()
        val produtos = produtoResponse.body()?.produtos ?: throw IOException(MENSAGEM_ERRO_PRODUTO)
        produtoDao.inserir(produtos)

        val requisicaoUsuario = mutableMapOf("usuarioId" to usuarioId)
        val impressaoProdutoResponse =
          impressaoProdutoService.buscarImpressaoPendentePor(usuarioId = requisicaoUsuario)
        val impressoes = impressaoProdutoResponse.body()?.impressoes ?: throw IOException(
          MENSAGEM_ERRO_IMPRESSAO_PRODUTO
        )

        impressoes.forEach { item ->
          item.solicitacao?.let { solicitacaoDao.inserir(it) }
          impressaoDao.inserir(
            Impressao(
              id = item.id,
              data = item.data,
              status = item.status,
              dataAceite = item.dataAceite,
              solicitacaoId = item.solicitacaoId
            )
          )
          item.produtos?.forEach {
            val impressaoProduto = ImpressaoProduto(
              impressaoId = it.impressaoId,
              id = it.id,
              produtoId = it.produtoId,
              quantidade = it.quantidade,
              dataInicio = it.dataInicio,
              dataTermino = it.dataTermino,
              codigoImpressao = it.codigoImpressao,
              codigoImpressaoDescricao = it.codigoImpressaoDescricao
            )
            impressaoProdutoDao.inserir(impressaoProduto)
            impressaoProdutoLoteDao.inserir(it.lotes)
          }
        }
      } catch (e: IOException) {
        liveData.postValue(ResultSgsaColetor.Error(exception = e))
      } catch (e: ConnectException) {
        liveData.postValue(ResultSgsaColetor.Error(exception = Exception("$MENSAGEM_ERRO_CONEXAO - ${e.message}")))
      } catch (e: SocketTimeoutException) {
        liveData.postValue(ResultSgsaColetor.Error(exception = Exception("$MENSAGEM_ERRO_SOCKET - ${e.message}")))
      } catch (e: Exception) {
        liveData.postValue(ResultSgsaColetor.Error(exception = Exception("ERRO: ${e.message}")))
      }
    }
    return liveData
  }
}