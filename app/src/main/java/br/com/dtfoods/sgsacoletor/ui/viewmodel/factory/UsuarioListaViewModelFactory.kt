package br.com.dtfoods.sgsacoletor.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.dtfoods.sgsacoletor.room.repository.UsuarioRepository
import br.com.dtfoods.sgsacoletor.ui.viewmodel.UsuarioListaViewModel

class UsuarioListaViewModelFactory(
  private val repository: UsuarioRepository
) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(UsuarioListaViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return UsuarioListaViewModel(repository) as T
    }
    throw IllegalArgumentException("Class de ViewModel Desconhecida.")
  }
}