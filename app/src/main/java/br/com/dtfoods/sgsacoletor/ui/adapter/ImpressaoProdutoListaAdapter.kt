package br.com.dtfoods.sgsacoletor.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.dtfoods.sgsacoletor.databinding.ImpressaoProdutoItemBinding
import br.com.dtfoods.sgsacoletor.model.ImpressaoProdutoCliente

class ImpressaoProdutoListaAdapter(
  private val context: Context,
  var cliqueItemLista: (impressaoProduto: ImpressaoProdutoCliente) -> Unit = {}
) : RecyclerView.Adapter<ImpressaoProdutoListaAdapter.ViewHolder>() {

  private val impressaoProdutos = emptyArray<ImpressaoProdutoCliente>().toMutableList()

  inner class ViewHolder(
    private val binding: ImpressaoProdutoItemBinding
  ) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var impressaoProdutoCliente: ImpressaoProdutoCliente

    init {
      itemView.setOnClickListener {
        if (::impressaoProdutoCliente.isInitialized) {
          cliqueItemLista(impressaoProdutoCliente)
        }
      }
    }

    fun associa(impressaoProdutoCliente: ImpressaoProdutoCliente) {
      this.impressaoProdutoCliente = impressaoProdutoCliente

      val descricaoPedido =
        "${impressaoProdutoCliente.solicitacaoNumeroPedido}; ${impressaoProdutoCliente.clienteNomeFantasia}"

      binding.impressaoProdutoItemModelo.text = impressaoProdutoCliente.produtoModelo
      binding.impressaoProdutoItemPedido.text = descricaoPedido
      binding.impressaoProdutoItemQtdeTotal.text =
        impressaoProdutoCliente.impressaoProdutoQuantidade.toString()
      binding.impressaoProdutoItemQtdeRestante.text =
        impressaoProdutoCliente.impressaoProdutoQuantidadeRestante.toString()
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val inflate = LayoutInflater.from(context)
    val binding = ImpressaoProdutoItemBinding.inflate(inflate, parent, false)
    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val impressaoProduto = impressaoProdutos[position]
    holder.associa(impressaoProduto)
  }

  override fun getItemCount(): Int = impressaoProdutos.size

  fun atualiza(impressaoProdutos: List<ImpressaoProdutoCliente>) {
    notifyItemRangeRemoved(0, this.impressaoProdutos.size)
    this.impressaoProdutos.clear()
    this.impressaoProdutos.addAll(impressaoProdutos)
    notifyItemRangeInserted(0, this.impressaoProdutos.size)
  }
}
