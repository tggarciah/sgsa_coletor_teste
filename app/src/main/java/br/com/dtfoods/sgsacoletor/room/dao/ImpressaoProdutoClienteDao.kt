package br.com.dtfoods.sgsacoletor.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import br.com.dtfoods.sgsacoletor.model.ImpressaoProdutoCliente

@Dao
interface ImpressaoProdutoClienteDao {

  @Transaction
  @Query(QUERY_IMPRESAO_PRODUTO_LOTE)
  fun buscaTodos(): LiveData<List<ImpressaoProdutoCliente>>

  @Transaction
  @Query(QUERY_IMPRESSAO_PRODUTO_LOTE_COM_WHERE)
  fun buscarPor(impressaoId: Long, id: Long): LiveData<ImpressaoProdutoCliente>
}