package br.com.dtfoods.sgsacoletor.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.dtfoods.sgsacoletor.model.Solicitacao

@Dao
interface SolicitacaoDao {

  @Query("SELECT * FROM Solicitacao WHERE id = :id")
  suspend fun buscaPor(id: Long?): Solicitacao

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun inserir(solicitacao: Solicitacao)
}