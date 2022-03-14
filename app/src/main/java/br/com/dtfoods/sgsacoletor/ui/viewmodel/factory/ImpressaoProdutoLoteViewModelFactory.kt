package br.com.dtfoods.sgsacoletor.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.dtfoods.sgsacoletor.room.repository.ImpressaoProdutoLoteRepository
import br.com.dtfoods.sgsacoletor.ui.viewmodel.ImpressaoProdutoLoteViewModel

class ImpressaoProdutoLoteViewModelFactory(
  private val repository: ImpressaoProdutoLoteRepository,
) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(ImpressaoProdutoLoteViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return ImpressaoProdutoLoteViewModel(repository) as T
    }
    throw IllegalArgumentException(THROW_CLASSE_DESCONHECIDA)
  }
}
