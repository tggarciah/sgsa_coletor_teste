package br.com.dtfoods.sgsacoletor.enumeration

import com.google.gson.annotations.SerializedName

enum class TipoSolicitacao(val tipo: String) {
  @SerializedName("IMC")
  IMPRESSAO_COPOS("IMC")
}
