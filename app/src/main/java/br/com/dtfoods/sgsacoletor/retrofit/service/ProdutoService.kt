package br.com.dtfoods.sgsacoletor.retrofit.service

import br.com.dtfoods.sgsacoletor.retrofit.dto.ProdutoDto
import retrofit2.Response
import retrofit2.http.POST

interface ProdutoService {

  @POST("PrProduto")
  suspend fun buscarTodos(): Response<ProdutoDto>
}