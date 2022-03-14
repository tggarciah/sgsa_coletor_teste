package br.com.dtfoods.sgsacoletor.room.converter

import androidx.room.TypeConverter
import br.com.dtfoods.sgsacoletor.enumeration.TipoSolicitacao

class TipoSolicitacaoConverter {

  @TypeConverter
  fun fromTipoSolicitacao(valor: TipoSolicitacao?) = valor?.tipo

  @TypeConverter
  fun toTipoSolicitacao(valor: String): TipoSolicitacao? {
    return when (valor) {
      "IMC" -> TipoSolicitacao.IMPRESSAO_COPOS
      else -> null
    }
  }
}
