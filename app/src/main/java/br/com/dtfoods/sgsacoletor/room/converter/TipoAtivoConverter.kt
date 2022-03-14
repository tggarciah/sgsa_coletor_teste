package br.com.dtfoods.sgsacoletor.room.converter

import androidx.room.TypeConverter
import br.com.dtfoods.sgsacoletor.enumeration.TipoAtivo

class TipoAtivoConverter {
  @TypeConverter
  fun fromTipoAtivo(value: TipoAtivo?) = value?.valor

  @TypeConverter
  fun toTipoAtivo(value: String): TipoAtivo? {
    return when (value) {
      "S" -> TipoAtivo.SIM
      "N" -> TipoAtivo.NAO
      "E" -> TipoAtivo.MODO_EDICAO
      else -> null
    }
  }
}