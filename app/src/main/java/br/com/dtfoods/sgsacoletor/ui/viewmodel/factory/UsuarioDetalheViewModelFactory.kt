package br.com.dtfoods.sgsacoletor.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.dtfoods.sgsacoletor.room.repository.UsuarioRepository
import br.com.dtfoods.sgsacoletor.ui.viewmodel.UsuarioDetalheViewModel

class UsuarioDetalheViewModelFactory(
  private val repository: UsuarioRepository,
  private val usuarioId: Long
) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(UsuarioDetalheViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return UsuarioDetalheViewModel(repository, usuarioId) as T
    }
    throw IllegalArgumentException("Class de ViewModel Desconhecida.")
  }
}
