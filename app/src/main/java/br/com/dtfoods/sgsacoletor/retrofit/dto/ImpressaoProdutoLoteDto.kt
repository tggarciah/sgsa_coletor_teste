package br.com.dtfoods.sgsacoletor.retrofit.dto

import br.com.dtfoods.sgsacoletor.model.ImpressaoProdutoLote
import br.com.dtfoods.sgsacoletor.model.RetornoErroServidor
import com.google.gson.annotations.SerializedName

class ImpressaoProdutoLoteDto(
  @SerializedName("erro")
  val retornoErro: RetornoErroServidor,
  val impressaoProdutoLotes: List<ImpressaoProdutoLote>
)