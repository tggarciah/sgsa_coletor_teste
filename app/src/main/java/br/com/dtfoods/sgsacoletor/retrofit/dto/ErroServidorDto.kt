package br.com.dtfoods.sgsacoletor.retrofit.dto

import br.com.dtfoods.sgsacoletor.model.RetornoErroServidor
import com.google.gson.annotations.SerializedName

data class ErroServidorDto(
  @SerializedName("erro")
  val erroServidor: RetornoErroServidor
)