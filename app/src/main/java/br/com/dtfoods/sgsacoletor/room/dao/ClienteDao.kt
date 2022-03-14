package br.com.dtfoods.sgsacoletor.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.dtfoods.sgsacoletor.model.Cliente

@Dao
interface ClienteDao {

  @Query("SELECT * FROM Cliente WHERE id = :id")
  suspend fun buscaPor(id: Long): Cliente

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun inserir(clientes: List<Cliente>)
}