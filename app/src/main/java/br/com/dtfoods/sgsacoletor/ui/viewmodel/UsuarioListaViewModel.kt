package br.com.dtfoods.sgsacoletor.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.dtfoods.sgsacoletor.model.Usuario
import br.com.dtfoods.sgsacoletor.room.repository.ResultSgsaColetor
import br.com.dtfoods.sgsacoletor.room.repository.UsuarioRepository

class UsuarioListaViewModel(
  private val repository: UsuarioRepository
) : ViewModel() {

  fun buscarTodos(): LiveData<ResultSgsaColetor<List<Usuario>>> {
    return repository.buscarTodos()
  }
}