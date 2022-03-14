package br.com.dtfoods.sgsacoletor.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.dtfoods.sgsacoletor.R
import br.com.dtfoods.sgsacoletor.databinding.ActivityImpressaoProdutoClienteDetalheBinding
import br.com.dtfoods.sgsacoletor.extensions.exibirSnackbarNaTela
import br.com.dtfoods.sgsacoletor.model.ImpressaoProdutoLote
import br.com.dtfoods.sgsacoletor.room.repository.ResultSgsaColetor
import br.com.dtfoods.sgsacoletor.ui.SgsaColetorApplication
import br.com.dtfoods.sgsacoletor.ui.viewmodel.ImpressaoProdutoClienteDetalheViewModel
import br.com.dtfoods.sgsacoletor.ui.viewmodel.ImpressaoProdutoLoteViewModel
import br.com.dtfoods.sgsacoletor.ui.viewmodel.factory.ImpressaoProdutoClienteDetalheViewModelFactory
import br.com.dtfoods.sgsacoletor.ui.viewmodel.factory.ImpressaoProdutoLoteViewModelFactory

class ImpressaoProdutoClienteDetalheActivity : AppCompatActivity() {
  private val binding by lazy {
    ActivityImpressaoProdutoClienteDetalheBinding.inflate(layoutInflater)
  }
  private val usuarioId: Long by lazy {
    intent.getLongExtra(USUARIO_ID_CHAVE, 0L)
  }
  private val impressaoId by lazy {
    intent.getLongExtra(IMPRESSAO_ID_CHAVE, 0L)
  }
  private val impressaoProdutoId by lazy {
    intent.getLongExtra(IMPRESSAO_PRODUTO_ID_CHAVE, 0L)
  }
  private val viewModelImpressaoProdutoCliente by viewModels<ImpressaoProdutoClienteDetalheViewModel> {
    ImpressaoProdutoClienteDetalheViewModelFactory(
      (application as SgsaColetorApplication).impressaoProdutoClienteRepository,
      impressaoId,
      impressaoProdutoId
    )
  }
  private val viewModelImpressaoProdutoLote by viewModels<ImpressaoProdutoLoteViewModel> {
    ImpressaoProdutoLoteViewModelFactory((application as SgsaColetorApplication).impressaoProdutoLoteRepository)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    title = "Detalhe da Impressão"
    buscarImpressaoProdutoCliente()
    adicionaListenerCliqueIcone()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_impressao_produto_cliente_detalhe, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == R.id.menu_impressao_produto_cliente_detalhe_salvar) {
      try {
        val delimitador = '-'
        val valorLote = binding.impressaoProdutoDetalheLote.text?.split(delimitador)

        if (valorLote?.size != 2) {
          throw Exception(MENSAGEM_ERRO_LOTE_NAO_INFORMADO)
        }

        val numeroLote = valorLote[0]
        val codigoCaixa = valorLote[1]

        if (numeroLote.isEmpty() or codigoCaixa.isEmpty()) {
          throw Exception(MENSAGEM_ERRO_LOTE_NAO_INFORMADO)
        }

        val impressaoProdutoLote = ImpressaoProdutoLote(
          impressaoId = impressaoId,
          impressaoProdutoId = impressaoProdutoId,
          lote = numeroLote,
          codigoCaixa = codigoCaixa
        )

        viewModelImpressaoProdutoLote.salvar(impressaoProdutoLote, usuarioId).observe(this) {
          binding.impressaoProdutoDetalheTextinputlayoutLote.error = null

          when (it) {
            is ResultSgsaColetor.Success -> {
              binding.impressaoProdutoDetalheLote.setText("")
              binding.impressaoProdutoDetalheLote.requestFocus()
              exibirSnackbarNaTela(
                binding.root,
                "Lote $numeroLote e Caixa $codigoCaixa enviado com sucesso!"
              )
            }
            is ResultSgsaColetor.Error -> binding.impressaoProdutoDetalheTextinputlayoutLote.error =
              it.exception.message
          }
        }
      } catch (e: Exception) {
        binding.impressaoProdutoDetalheTextinputlayoutLote.error = e.message
      }
    }
    return super.onOptionsItemSelected(item)
  }

  private fun buscarImpressaoProdutoCliente() {
    viewModelImpressaoProdutoCliente.buscarRegistro.observe(this) {
      val descricaoSolicitacao = "${it.solicitacaoId}/ ${it.impressaoId}-${it.impressaoProdutoId}"
      val descricaoCliente = "[${it.clienteId}] ${it.clienteNomeFantasia}"
      val descricaoCodImpressao =
        "[${it.impressaoProdutoCodigoImpressao}] ${it.impressaoProdutoCodigoImpressaoDescricao}"
      val descricaoProduto = "[${it.produtoId}] ${it.produtoModelo}"
      val resultadoQuantidade = it.impressaoProdutoQuantidadeRestante ?: 0

      binding.impressaoProdutoDetalheSolicitacaoId.setText(descricaoSolicitacao)
      binding.impressaoProdutoDetalheCliente.setText(descricaoCliente)
      binding.impressaoProdutoDetalheCodigoImpressao.setText(descricaoCodImpressao)
      binding.impressaoProdutoDetalheProduto.setText(descricaoProduto)
      binding.impressaoProdutoDetalheNumeroPedido.setText(it.solicitacaoNumeroPedido.toString())
      binding.impressaoProdutoDetalheQuantidadePedido.setText(it.impressaoProdutoQuantidade.toString())
      binding.impressaoProdutoDetalheQuantidadeRestante.setText(resultadoQuantidade.toString())
    }
  }

  @SuppressLint("ClickableViewAccessibility")
  private fun adicionaListenerCliqueIcone() {
    val registerIntent = capturaRetornoActivity()

    binding.impressaoProdutoDetalheLote.setOnTouchListener { _, event ->
      if (event.action == MotionEvent.ACTION_UP) {
        if (event.rawX >=
          (binding.impressaoProdutoDetalheLote.right -
                  binding.impressaoProdutoDetalheLote.compoundDrawables[DRAWABLE_RIGHT].bounds.width())
        ) {
          registerIntent.launch(
            Intent(this, BarcodeScannerActivity::class.java)
          )
        }
      }
      false
    }
  }

  private fun capturaRetornoActivity(): ActivityResultLauncher<Intent> {
    return registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityRetorno ->
      if (activityRetorno.resultCode == RESULT_OK) {
        val stringLote = activityRetorno.data?.getStringExtra(INICIAR_HARDWARE_CAMERA)
        binding.impressaoProdutoDetalheLote.setText(stringLote)
      }
    }
  }
}