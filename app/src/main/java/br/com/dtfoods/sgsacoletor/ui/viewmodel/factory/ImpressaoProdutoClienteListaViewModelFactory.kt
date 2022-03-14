package br.com.dtfoods.sgsacoletor.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.dtfoods.sgsacoletor.room.repository.ImpressaoProdutoClienteRepository
import br.com.dtfoods.sgsacoletor.ui.viewmodel.ImpressaoProdutoClienteListaViewModel

class ImpressaoProdutoClienteListaViewModelFactory(
  private val repository: ImpressaoProdutoClienteRepository,
  private val usuarioId: Long
) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(ImpressaoProdutoClienteListaViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return ImpressaoProdutoClienteListaViewModel(repository, usuarioId) as T
    }
    throw IllegalArgumentException(THROW_CLASSE_DESCONHECIDA)
  }
}