package br.com.dtfoods.sgsacoletor.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import br.com.dtfoods.sgsacoletor.enumeration.TipoStatusImpressao

@Entity(
  primaryKeys = ["id"],
  foreignKeys = [ForeignKey(
    entity = Solicitacao::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("solicitacao_id")
  )],
  indices = [Index(
    name = "ImpressaoIndex01",
    value = ["solicitacao_id"],
  )]
)
data class Impressao(
  val id: Long,
  val data: String?,
  val status: TipoStatusImpressao?,
  val dataAceite: String?,
  @ColumnInfo(name = "solicitacao_id")
  val solicitacaoId: Long?
)