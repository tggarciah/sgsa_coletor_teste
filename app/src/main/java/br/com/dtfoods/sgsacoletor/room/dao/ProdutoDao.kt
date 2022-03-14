package br.com.dtfoods.sgsacoletor.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.dtfoods.sgsacoletor.model.Produto

@Dao
interface ProdutoDao {

  @Query("SELECT * FROM Produto WHERE id = :id")
  suspend fun buscaPor(id: Long): Produto

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun inserir(produtos: List<Produto>)
}