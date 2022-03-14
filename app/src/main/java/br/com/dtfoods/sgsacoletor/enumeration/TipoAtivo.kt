package br.com.dtfoods.sgsacoletor.enumeration

import com.google.gson.annotations.SerializedName

enum class TipoAtivo(val valor: String) {
  @SerializedName("E")
  MODO_EDICAO("E"),

  @SerializedName("S")
  SIM("S"),

  @SerializedName("N")
  NAO("N");
}