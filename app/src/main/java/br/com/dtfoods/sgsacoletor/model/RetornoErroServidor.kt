package br.com.dtfoods.sgsacoletor.model

import com.google.gson.annotations.SerializedName

data class RetornoErroServidor(
  @SerializedName("ErroCodigo")
  val erroCodigo: Long,
  @SerializedName("ErroDescricao")
  val erroDescricao: String?,
  @SerializedName("ErroPrograma")
  val erroPrograma: String?
)