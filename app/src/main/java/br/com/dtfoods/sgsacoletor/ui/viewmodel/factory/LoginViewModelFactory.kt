package br.com.dtfoods.sgsacoletor.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.dtfoods.sgsacoletor.room.repository.LoginRepository
import br.com.dtfoods.sgsacoletor.ui.viewmodel.LoginViewModel

class LoginViewModelFactory(
  private val repository: LoginRepository
) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return LoginViewModel(repository) as T
    }
    throw IllegalArgumentException(THROW_CLASSE_DESCONHECIDA)
  }
}