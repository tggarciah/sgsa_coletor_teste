package br.com.dtfoods.sgsacoletor.enumeration

import com.google.gson.annotations.SerializedName

enum class TipoProduto(val valor: String) {
  @SerializedName("MP")
  MATERIA_PRIMA("MP"),

  @SerializedName("IN")
  INSUMO("IN"),

  @SerializedName("PI")
  PRODUTO_INTERMEDIARIO("PI"),

  @SerializedName("PA")
  PRODUTO_ACABADO("PA");
}
