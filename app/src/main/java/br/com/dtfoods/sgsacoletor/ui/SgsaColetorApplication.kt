package br.com.dtfoods.sgsacoletor.ui

import android.app.Application
import br.com.dtfoods.sgsacoletor.room.SgsaRoomDatabase
import br.com.dtfoods.sgsacoletor.room.repository.ImpressaoProdutoClienteRepository
import br.com.dtfoods.sgsacoletor.room.repository.ImpressaoProdutoLoteRepository
import br.com.dtfoods.sgsacoletor.room.repository.LoginRepository
import br.com.dtfoods.sgsacoletor.room.repository.UsuarioRepository

class SgsaColetorApplication : Application() {
  val database by lazy { SgsaRoomDatabase.getDatabase(this) }
  val usuarioRepository by lazy { UsuarioRepository(database.usuarioDao()) }
  val loginRepository by lazy { LoginRepository(database.usuarioDao()) }

  val impressaoProdutoClienteRepository by lazy {
    ImpressaoProdutoClienteRepository(
      database.clienteDao(),
      database.produtoDao(),
      database.solicitacaoDao(),
      database.impressaoDao(),
      database.impressaoProdutoDao(),
      database.impressaoProdutoLoteDao(),
      database.impressaoProdutoClienteDao()
    )
  }
  val impressaoProdutoLoteRepository by lazy {
    ImpressaoProdutoLoteRepository(
      database.impressaoProdutoLoteDao(),
      database.usuarioDao()
    )
  }
}