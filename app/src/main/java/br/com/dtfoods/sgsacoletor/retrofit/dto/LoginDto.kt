package br.com.dtfoods.sgsacoletor.retrofit.dto

import br.com.dtfoods.sgsacoletor.model.RetornoErroServidor
import br.com.dtfoods.sgsacoletor.model.Usuario
import com.google.gson.annotations.SerializedName

data class LoginDto(
  val usuario: Usuario,
  val validadeAcesso: String,
  @SerializedName("erro")
  val erroServidor: RetornoErroServidor
)