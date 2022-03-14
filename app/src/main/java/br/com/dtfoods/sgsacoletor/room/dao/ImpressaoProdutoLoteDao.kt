package br.com.dtfoods.sgsacoletor.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.dtfoods.sgsacoletor.model.ImpressaoProdutoLote

@Dao
interface ImpressaoProdutoLoteDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun inserir(impressaoProdutoLotes: List<ImpressaoProdutoLote>)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun inserir(impressaoProdutoLote: ImpressaoProdutoLote)

  @Query("SELECT (id+1) " +
          "FROM ImpressaoProdutoLote " +
          "WHERE impressao_id = :impressaoId and impressao_produto_id = :impressaoProdutoId " +
          "ORDER BY id " +
          "DESC LIMIT 1")
  suspend fun proximoId(impressaoId: Long, impressaoProdutoId: Long): Long
}
