package br.com.dtfoods.sgsacoletor.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dtfoods.sgsacoletor.model.Usuario
import br.com.dtfoods.sgsacoletor.room.repository.UsuarioRepository
import kotlinx.coroutines.launch

class UsuarioFormularioViewModel(
  private val repository: UsuarioRepository
) : ViewModel() {

  fun insert(usuario: Usuario) = viewModelScope.launch {
    repository.inserir(usuario)
  }

  fun update(usuario: Usuario) = viewModelScope.launch {
    repository.atualizar(usuario)
  }

  fun getBy(usuarioId: Long): LiveData<Usuario?> {
    return repository.buscaPorId(usuarioId)
  }
}