package br.com.dtfoods.sgsacoletor.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import br.com.dtfoods.sgsacoletor.model.ImpressaoProduto

@Dao
interface ImpressaoProdutoDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun inserir(impressaoProdutos: List<ImpressaoProduto>)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun inserir(impressaoProduto: ImpressaoProduto)
}