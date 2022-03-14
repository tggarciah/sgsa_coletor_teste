package br.com.dtfoods.sgsacoletor.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.dtfoods.sgsacoletor.databinding.ActivityUsuarioListaBinding
import br.com.dtfoods.sgsacoletor.model.Usuario
import br.com.dtfoods.sgsacoletor.room.repository.ResultSgsaColetor
import br.com.dtfoods.sgsacoletor.ui.SgsaColetorApplication
import br.com.dtfoods.sgsacoletor.ui.adapter.UsuarioListaAdapter
import br.com.dtfoods.sgsacoletor.ui.viewmodel.UsuarioListaViewModel
import br.com.dtfoods.sgsacoletor.ui.viewmodel.factory.UsuarioListaViewModelFactory

class UsuarioListaActivity : AppCompatActivity() {

  private val binding by lazy {
    ActivityUsuarioListaBinding.inflate(layoutInflater)
  }
  private val adapter by lazy {
    UsuarioListaAdapter(context = this)
  }
  private val viewModel by viewModels<UsuarioListaViewModel> {
    UsuarioListaViewModelFactory((application as SgsaColetorApplication).usuarioRepository)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    title = "Lista Usuários"
    configuraRecyclerView()
    configuraFabAdicionar()
    buscarUsuarios()
  }

  private fun configuraRecyclerView() {
    binding.acUsuarioListaRecyclerview.adapter = adapter
    configuraAdapter()
  }

  private fun configuraAdapter() {
    adapter.cliqueItem = this::abreDetalheUsuario
  }

  private fun abreDetalheUsuario(it: Usuario) {
    val intent = Intent(this, UsuarioDetalheActivity::class.java)
    intent.putExtra("USUARIO_ID", it.id)
    startActivity(intent)
  }

  private fun configuraFabAdicionar() {
    binding.acUsuarioListaFab.setOnClickListener {
      Intent(this, UsuarioFormularioActivity::class.java).apply {
        startActivity(this)
      }
    }
  }

  private fun buscarUsuarios() {
    viewModel.buscarTodos().observe(this) {
      when (it) {
        is ResultSgsaColetor.Success -> adapter.atualizar(it.data)
        is ResultSgsaColetor.Error -> Toast.makeText(this, it.exception.message, Toast.LENGTH_LONG)
          .show()
      }
    }
  }
}