package br.com.dtfoods.sgsacoletor.retrofit.service

import br.com.dtfoods.sgsacoletor.model.ImpressaoProdutoLote
import br.com.dtfoods.sgsacoletor.retrofit.dto.ImpressaoProdutoLoteDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ImpressaoProdutoLoteService {

  @Headers("Content-Type: application/json")
  @POST("PrProcessarLoteProduto")
  suspend fun salvar(
    @Body impressaoProdutoLote: MutableMap<String, ImpressaoProdutoLote>
  ): Response<ImpressaoProdutoLoteDto>
}