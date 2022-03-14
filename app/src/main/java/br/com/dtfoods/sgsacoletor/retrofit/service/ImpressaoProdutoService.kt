package br.com.dtfoods.sgsacoletor.retrofit.service

import br.com.dtfoods.sgsacoletor.retrofit.dto.ImpressaoProdutoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ImpressaoProdutoService {

  @Headers("Content-Type: application/json")
  @POST("PrSolicitacaoImpressao")
  suspend fun buscarImpressaoPendentePor(@Body usuarioId: MutableMap<String, Long>): Response<ImpressaoProdutoDto>
}