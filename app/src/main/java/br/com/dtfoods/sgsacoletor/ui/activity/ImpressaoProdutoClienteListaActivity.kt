package br.com.dtfoods.sgsacoletor.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.dtfoods.sgsacoletor.databinding.ActivityImpressaoProdutoClienteListaBinding
import br.com.dtfoods.sgsacoletor.extensions.exibirSnackbarNaTela
import br.com.dtfoods.sgsacoletor.model.ImpressaoProdutoCliente
import br.com.dtfoods.sgsacoletor.room.repository.ResultSgsaColetor
import br.com.dtfoods.sgsacoletor.ui.SgsaColetorApplication
import br.com.dtfoods.sgsacoletor.ui.adapter.ImpressaoProdutoListaAdapter
import br.com.dtfoods.sgsacoletor.ui.viewmodel.ImpressaoProdutoClienteListaViewModel
import br.com.dtfoods.sgsacoletor.ui.viewmodel.factory.ImpressaoProdutoClienteListaViewModelFactory

class ImpressaoProdutoClienteListaActivity : AppCompatActivity() {

  private val usuarioId: Long by lazy {
    intent.getLongExtra(USUARIO_ID_CHAVE, 0L)
  }
  private val binding by lazy {
    ActivityImpressaoProdutoClienteListaBinding.inflate(layoutInflater)
  }
  private val adapter by lazy {
    ImpressaoProdutoListaAdapter(this)
  }
  private val viewModel: ImpressaoProdutoClienteListaViewModel by viewModels {
    ImpressaoProdutoClienteListaViewModelFactory(
      (application as SgsaColetorApplication).impressaoProdutoClienteRepository,
      usuarioId
    )
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    title = "Lista de Impressões"
    configuraRecyclerView()
    listaProdutosPendentes()
  }

  private fun configuraRecyclerView() {
    binding.actImpressaoProdutoListaRecyclerview.adapter = adapter
    adapter.cliqueItemLista = this::activityImpressaoProdutoClienteDetalhe
  }

  private fun listaProdutosPendentes() {
    viewModel.buscarTodos().observe(this) { resultado ->
      when (resultado) {
        is ResultSgsaColetor.Success -> adapter.atualiza(resultado.data)
        is ResultSgsaColetor.Error -> exibirSnackbarNaTela(
          binding.root,
          resultado.exception.message!!
        )
      }
    }
  }

  private fun activityImpressaoProdutoClienteDetalhe(impressaoProdutoCliente: ImpressaoProdutoCliente) {
    val intent = Intent(this, ImpressaoProdutoClienteDetalheActivity::class.java)
    intent.putExtra(USUARIO_ID_CHAVE, usuarioId)
    intent.putExtra(IMPRESSAO_ID_CHAVE, impressaoProdutoCliente.impressaoId)
    intent.putExtra(IMPRESSAO_PRODUTO_ID_CHAVE, impressaoProdutoCliente.impressaoProdutoId)
    startActivity(intent)
  }
}