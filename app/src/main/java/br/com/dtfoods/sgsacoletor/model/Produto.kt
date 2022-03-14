package br.com.dtfoods.sgsacoletor.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.dtfoods.sgsacoletor.enumeration.TipoAtivo
import br.com.dtfoods.sgsacoletor.enumeration.TipoProduto

@Entity
data class Produto(
  @PrimaryKey val id: Long,
  val modelo: String?,
  val descricao: String?,
  val unidadeMedida: String?,
  val ativo: TipoAtivo?,
  val tipo: TipoProduto?
)