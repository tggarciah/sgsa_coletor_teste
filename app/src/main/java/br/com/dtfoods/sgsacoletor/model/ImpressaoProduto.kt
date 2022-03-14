package br.com.dtfoods.sgsacoletor.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
  primaryKeys = ["impressao_id", "id"],
  foreignKeys = [ForeignKey(
    entity = Impressao::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("impressao_id")
  ), ForeignKey(
    entity = Produto::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("produto_id")
  )],
  indices = [Index(
    name = "ImpressaoProdutoIndex01",
    value = ["impressao_id", "id"],
    unique = true
  ), Index(
    name = "ImpressaoProdutoIndex02",
    value = ["produto_id"],
  )]
)
data class ImpressaoProduto(
  @ColumnInfo(name = "impressao_id")
  val impressaoId: Long,
  val id: Long,
  @ColumnInfo(name = "produto_id")
  val produtoId: Long?,
  val quantidade: Long?,
  @ColumnInfo(name = "data_inicio")
  val dataInicio: String?,
  @ColumnInfo(name = "data_termino")
  val dataTermino: String?,
  @ColumnInfo(name = "codigo_impressao")
  val codigoImpressao: Long?,
  @ColumnInfo(name = "codigo_impressao_descricao")
  val codigoImpressaoDescricao: String?
)