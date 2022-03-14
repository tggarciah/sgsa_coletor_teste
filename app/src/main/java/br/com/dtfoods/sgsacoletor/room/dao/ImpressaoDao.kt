package br.com.dtfoods.sgsacoletor.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.dtfoods.sgsacoletor.model.Impressao

@Dao
interface ImpressaoDao {

  @Query("SELECT * FROM Impressao WHERE id = :id")
  suspend fun buscaPor(id: Long): Impressao

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun inserir(impressao: Impressao)
}