package br.com.dtfoods.sgsacoletor.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.dtfoods.sgsacoletor.model.*
import br.com.dtfoods.sgsacoletor.room.converter.TipoAtivoConverter
import br.com.dtfoods.sgsacoletor.room.converter.TipoProdutoConverter
import br.com.dtfoods.sgsacoletor.room.converter.TipoSolicitacaoConverter
import br.com.dtfoods.sgsacoletor.room.converter.TipoStatusImpressaoConverter
import br.com.dtfoods.sgsacoletor.room.dao.*

private const val NOME_BANCO_DADOS = "sgsa_coletor_v3.db"

@Database(
  entities = [
    Usuario::class,
    Cliente::class,
    Produto::class,
    Solicitacao::class,
    Impressao::class,
    ImpressaoProduto::class,
    ImpressaoProdutoLote::class
  ],
  version = 1,
  exportSchema = false
)
@TypeConverters(
  TipoAtivoConverter::class,
  TipoProdutoConverter::class,
  TipoSolicitacaoConverter::class,
  TipoStatusImpressaoConverter::class
)
abstract class SgsaRoomDatabase : RoomDatabase() {

  companion object {
    @Volatile
    private var INSTANCE: SgsaRoomDatabase? = null

    fun getDatabase(context: Context): SgsaRoomDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          SgsaRoomDatabase::class.java,
          NOME_BANCO_DADOS
        )
          .fallbackToDestructiveMigration()
          .build()
        INSTANCE = instance
        instance
      }
    }
  }

  abstract fun usuarioDao(): UsuarioDao
  abstract fun clienteDao(): ClienteDao
  abstract fun produtoDao(): ProdutoDao
  abstract fun solicitacaoDao(): SolicitacaoDao
  abstract fun impressaoDao(): ImpressaoDao
  abstract fun impressaoProdutoDao(): ImpressaoProdutoDao
  abstract fun impressaoProdutoLoteDao(): ImpressaoProdutoLoteDao
  abstract fun impressaoProdutoClienteDao(): ImpressaoProdutoClienteDao
}