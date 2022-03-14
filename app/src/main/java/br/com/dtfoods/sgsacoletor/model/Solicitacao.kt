package br.com.dtfoods.sgsacoletor.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import br.com.dtfoods.sgsacoletor.enumeration.TipoSolicitacao

@Entity(
  primaryKeys = ["id"],
  foreignKeys = [ForeignKey(
    entity = Cliente::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("cliente_id")
  )],
  indices = [Index(
    name = "SolicitacaoIndex01",
    value = ["cliente_id"]
  )]
)
data class Solicitacao(
  val id: Long,
  val tipo: TipoSolicitacao?,
  val data: String?,
  @ColumnInfo(name = "numero_pedido")
  val numeroPedido: Long?,
  @ColumnInfo(name = "cliente_id")
  val clienteId: Long?
)