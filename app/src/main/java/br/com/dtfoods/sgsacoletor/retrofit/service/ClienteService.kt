package br.com.dtfoods.sgsacoletor.retrofit.service

import br.com.dtfoods.sgsacoletor.retrofit.dto.ClienteDto
import retrofit2.Response
import retrofit2.http.POST

interface ClienteService {

  @POST("PrCliente")
  suspend fun buscarTodos(): Response<ClienteDto>
}