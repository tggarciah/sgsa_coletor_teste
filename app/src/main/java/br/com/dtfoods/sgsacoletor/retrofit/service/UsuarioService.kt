package br.com.dtfoods.sgsacoletor.retrofit.service

import br.com.dtfoods.sgsacoletor.retrofit.dto.UsuarioDto
import retrofit2.Response
import retrofit2.http.POST

interface UsuarioService {

  @POST("PrOperadorColetor")
  suspend fun buscaTodos(): Response<UsuarioDto>
}
