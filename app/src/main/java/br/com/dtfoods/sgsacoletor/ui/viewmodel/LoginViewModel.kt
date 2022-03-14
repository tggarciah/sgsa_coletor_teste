package br.com.dtfoods.sgsacoletor.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.dtfoods.sgsacoletor.model.Login
import br.com.dtfoods.sgsacoletor.model.RetornoErroServidor
import br.com.dtfoods.sgsacoletor.model.Usuario
import br.com.dtfoods.sgsacoletor.room.repository.LoginRepository
import br.com.dtfoods.sgsacoletor.room.repository.ResultSgsaColetor

class LoginViewModel(
  private val repository: LoginRepository,
) : ViewModel() {

  fun realizarLogin(login: Login): LiveData<ResultSgsaColetor<Usuario>> {
    return repository.login(login)
  }

  fun cadastrarSenha(login: Login): LiveData<ResultSgsaColetor<Void?>> {
    return repository.cadastrarSenha(login)
  }

  fun recuperarSenha(login: Login): LiveData<ResultSgsaColetor<RetornoErroServidor>> {
    return repository.recuperarSenha(login)
  }
}
