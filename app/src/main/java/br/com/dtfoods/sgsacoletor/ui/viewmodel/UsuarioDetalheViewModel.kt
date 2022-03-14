package br.com.dtfoods.sgsacoletor.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.dtfoods.sgsacoletor.model.Usuario
import br.com.dtfoods.sgsacoletor.room.repository.ResultSgsaColetor
import br.com.dtfoods.sgsacoletor.room.repository.UsuarioRepository

class UsuarioDetalheViewModel(
  private val repository: UsuarioRepository,
  private val id: Long
) : ViewModel() {

  val usuario = repository.buscaPorId(id)

  fun remover(): LiveData<ResultSgsaColetor<Void?>> {
    val liveData = MutableLiveData<ResultSgsaColetor<Void?>>()

    val usuario: Usuario? = usuario.value

    liveData.value = if (usuario != null) {
      repository.remover(usuario)
      ResultSgsaColetor.Success(null)
    } else {
      ResultSgsaColetor.Error(Exception("buu"))
    }

    return liveData
  }
}