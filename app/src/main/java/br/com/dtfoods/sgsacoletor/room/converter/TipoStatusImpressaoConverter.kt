package br.com.dtfoods.sgsacoletor.room.converter

import androidx.room.TypeConverter
import br.com.dtfoods.sgsacoletor.enumeration.TipoStatusImpressao

class TipoStatusImpressaoConverter {
  @TypeConverter
  fun fromTipoStatusImpressao(valor: TipoStatusImpressao?) = valor?.tipo

  @TypeConverter
  fun toTipoStatusImpressao(valor: String?): TipoStatusImpressao? {
    return when (valor) {
      "PRD" -> TipoStatusImpressao.EM_PRODUCAO
      "CAN" -> TipoStatusImpressao.CANCELADO
      "FIN" -> TipoStatusImpressao.FINALIZADO
      "FIL" -> TipoStatusImpressao.NA_FILA
      else -> null
    }
  }
}