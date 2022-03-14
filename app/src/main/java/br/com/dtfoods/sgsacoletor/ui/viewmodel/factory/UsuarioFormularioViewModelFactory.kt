package br.com.dtfoods.sgsacoletor.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.dtfoods.sgsacoletor.room.repository.UsuarioRepository
import br.com.dtfoods.sgsacoletor.ui.viewmodel.UsuarioFormularioViewModel

class UsuarioFormularioViewModelFactory(
  private val repository: UsuarioRepository
) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(UsuarioFormularioViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return UsuarioFormularioViewModel(repository) as T
    }
    throw IllegalArgumentException("Class de ViewModel Desconhecida.")
  }
}