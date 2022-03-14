package br.com.dtfoods.sgsacoletor.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.dtfoods.sgsacoletor.room.repository.ImpressaoProdutoClienteRepository

class ImpressaoProdutoClienteDetalheViewModel(
  private val repository: ImpressaoProdutoClienteRepository,
  private val impressaoId: Long,
  private val impressaoProdutoId: Long
) : ViewModel() {

  val buscarRegistro = repository.buscarPor(impressaoId, impressaoProdutoId)
}
