package br.com.dtfoods.sgsacoletor.enumeration

import com.google.gson.annotations.SerializedName

enum class TipoStatusImpressao(val tipo: String) {
  @SerializedName("FIL")
  NA_FILA("FIL"),

  @SerializedName("PRD")
  EM_PRODUCAO("PRD"),

  @SerializedName("FIN")
  FINALIZADO("FIN"),

  @SerializedName("CAN")
  CANCELADO("CAN")
}
