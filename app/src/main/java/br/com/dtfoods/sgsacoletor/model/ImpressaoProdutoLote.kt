package br.com.dtfoods.sgsacoletor.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
  primaryKeys = ["impressao_id", "impressao_produto_id", "id"],
  foreignKeys = [ForeignKey(
    entity = ImpressaoProduto::class,
    parentColumns = arrayOf("impressao_id", "id"),
    childColumns = arrayOf("impressao_id", "impressao_produto_id")
  )],
  indices = [Index(
    name = "ImpressaoProdutoLoteIndex01",
    value = arrayOf("impressao_id", "impressao_produto_id")
  )]
)
data class ImpressaoProdutoLote(
  @ColumnInfo(name = "impressao_id")
  val impressaoId: Long,
  @ColumnInfo(name = "impressao_produto_id")
  val impressaoProdutoId: Long,
  val id: Long = 0L,
  val lote: String?,
  @ColumnInfo(name = "codigo_caixa")
  val codigoCaixa: String?,
  var usuarioLogin: String? = null
)