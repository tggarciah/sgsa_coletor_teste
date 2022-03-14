package br.com.dtfoods.sgsacoletor.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.dtfoods.sgsacoletor.room.repository.ImpressaoProdutoClienteRepository
import br.com.dtfoods.sgsacoletor.ui.viewmodel.ImpressaoProdutoClienteDetalheViewModel

class ImpressaoProdutoClienteDetalheViewModelFactory(
  private val repository: ImpressaoProdutoClienteRepository,
  private val impressaoId: Long,
  private val impressaoProdutoId: Long
) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(ImpressaoProdutoClienteDetalheViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return ImpressaoProdutoClienteDetalheViewModel(
        repository,
        impressaoId,
        impressaoProdutoId
      ) as T
    }
    throw IllegalArgumentException(THROW_CLASSE_DESCONHECIDA)
  }
}