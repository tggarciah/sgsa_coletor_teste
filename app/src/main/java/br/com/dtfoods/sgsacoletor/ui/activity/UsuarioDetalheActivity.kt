package br.com.dtfoods.sgsacoletor.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.dtfoods.sgsacoletor.R
import br.com.dtfoods.sgsacoletor.databinding.ActivityUsuarioDetalheBinding
import br.com.dtfoods.sgsacoletor.room.repository.ResultSgsaColetor
import br.com.dtfoods.sgsacoletor.ui.SgsaColetorApplication
import br.com.dtfoods.sgsacoletor.ui.viewmodel.UsuarioDetalheViewModel
import br.com.dtfoods.sgsacoletor.ui.viewmodel.factory.UsuarioDetalheViewModelFactory

class UsuarioDetalheActivity : AppCompatActivity() {

  private val binding by lazy {
    ActivityUsuarioDetalheBinding.inflate(layoutInflater)
  }
  private val usuarioId by lazy {
    intent.getLongExtra("USUARIO_ID", 0)
  }
  private val viewModel: UsuarioDetalheViewModel by viewModels {
    UsuarioDetalheViewModelFactory(
      (application as SgsaColetorApplication).usuarioRepository,
      usuarioId
    )
  }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    title = "Detalhe Usuário"

    val campoNome = binding.activityUsuarioDetalheEdittextNome
    val campoLogin = binding.activityUsuarioDetalheEdittextLogin
    val campoEmail = binding.activityUsuarioDetalheEdittextEmail

    viewModel.usuario.observe(this) {
      if (it != null) {
        campoNome.setText(it.nome)
        campoLogin.setText(it.login)
        campoEmail.setText(it.email)
      } else {
        finish()
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_detalhe_usuario, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.menu_detalhe_usuario_editar -> {
        Intent(this, UsuarioFormularioActivity::class.java).apply {
          putExtra("USUARIO_ID", usuarioId)
          startActivity(this)
        }
        finish()
      }
      R.id.menu_detalhe_usuario_deletar -> {
        viewModel.remover().observe(this) {
          when (it) {
            is ResultSgsaColetor.Success -> finish()
            is ResultSgsaColetor.Error -> Toast.makeText(this, "ERRAO", Toast.LENGTH_LONG).show()
          }
        }
      }
    }
    return super.onOptionsItemSelected(item)
  }
}