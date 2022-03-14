package br.com.dtfoods.sgsacoletor.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.dtfoods.sgsacoletor.databinding.UsuarioItemBinding
import br.com.dtfoods.sgsacoletor.model.Usuario

class UsuarioListaAdapter(
  private val context: Context,
  var cliqueItem: (usuario: Usuario) -> Unit = {}
) : RecyclerView.Adapter<UsuarioListaAdapter.ViewHolder>() {

  private val usuarios = emptyList<Usuario>().toMutableList()

  inner class ViewHolder(private val binding: UsuarioItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private lateinit var usuario: Usuario

    init {
      itemView.setOnClickListener {
        if (::usuario.isInitialized) {
          cliqueItem(usuario)
        }
      }
    }

    fun associar(usuario: Usuario) {
      this.usuario = usuario

      val campoNome = binding.usuarioItemNome
      campoNome.text = usuario.nome
      val campoLogin = binding.usuarioItemLogin
      campoLogin.text = usuario.login
      val campoEmail = binding.usuarioItemEmail
      campoEmail.text = usuario.email
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val inflater = LayoutInflater.from(context)
    val binding = UsuarioItemBinding.inflate(inflater, parent, false)
    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val usuario = usuarios[position]
    holder.associar(usuario)
  }

  override fun getItemCount(): Int = usuarios.size

  fun atualizar(usuarios: List<Usuario>) {
    notifyItemRangeRemoved(0, this.usuarios.size)
    this.usuarios.clear()
    this.usuarios.addAll(usuarios)
    notifyItemRangeInserted(0, this.usuarios.size)
  }
}