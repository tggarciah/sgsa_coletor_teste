package br.com.dtfoods.sgsacoletor.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.dtfoods.sgsacoletor.databinding.ActivityUsuarioFormularioBinding
import br.com.dtfoods.sgsacoletor.model.Usuario
import br.com.dtfoods.sgsacoletor.ui.SgsaColetorApplication
import br.com.dtfoods.sgsacoletor.ui.viewmodel.UsuarioFormularioViewModel
import br.com.dtfoods.sgsacoletor.ui.viewmodel.factory.UsuarioFormularioViewModelFactory

class UsuarioFormularioActivity : AppCompatActivity() {

  private val binding by lazy {
    ActivityUsuarioFormularioBinding.inflate(layoutInflater)
  }
  private val usuarioId by lazy {
    intent.getLongExtra("USUARIO_ID", 0)
  }
  private val viewModel: UsuarioFormularioViewModel by viewModels {
    UsuarioFormularioViewModelFactory((application as SgsaColetorApplication).usuarioRepository)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    title = "Formulário Usuário"

    val campoNome = binding.activityFormularioUsuarioEdittextNome
    val campoLogin = binding.activityFormularioUsuarioEdittextLogin
    val campoEmail = binding.activityFormularioUsuarioEdittextEmail

    viewModel.getBy(usuarioId).observe(this) {
      if (it != null) {
        campoNome.setText(it.nome)
        campoLogin.setText(it.login)
        campoEmail.setText(it.email)
      }
    }

    binding.activityFormularioUsuarioBotao.setOnClickListener {
      val id = usuarioId
      val nome = campoNome.text.toString()
      val login = campoLogin.text.toString()
      val email = campoEmail.text.toString()
      val usuario = Usuario(id, nome, login, email)
      if (id > 0) {
        viewModel.update(usuario)
      } else {
        viewModel.insert(usuario)
      }
      finish()
    }
  }
}