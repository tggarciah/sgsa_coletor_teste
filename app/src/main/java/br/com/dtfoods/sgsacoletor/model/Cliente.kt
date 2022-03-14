package br.com.dtfoods.sgsacoletor.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cliente(
  @PrimaryKey
  val id: Long,
  @ColumnInfo(name = "razao_social")
  val razaoSocial: String?,
  @ColumnInfo(name = "nome_fantasia")
  val nomeFantasia: String?,
  val cnpj: String?,
  @ColumnInfo(name = "codigo_interno")
  val codigoInterno: String?,
  val vendedor: String?
)