package br.com.dtfoods.sgsacoletor.room.converter

import androidx.room.TypeConverter
import br.com.dtfoods.sgsacoletor.enumeration.TipoProduto

class TipoProdutoConverter {
  @TypeConverter
  fun fromTipoProduto(valor: TipoProduto?) = valor?.valor

  @TypeConverter
  fun toTipoProduto(valor: String): TipoProduto? {
    return when (valor) {
      "MP" -> TipoProduto.MATERIA_PRIMA
      "IN" -> TipoProduto.INSUMO
      "PI" -> TipoProduto.PRODUTO_INTERMEDIARIO
      "PA" -> TipoProduto.PRODUTO_ACABADO
      else -> null
    }
  }
}