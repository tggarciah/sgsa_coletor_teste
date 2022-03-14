package br.com.dtfoods.sgsacoletor.retrofit.service

import br.com.dtfoods.sgsacoletor.model.Login
import br.com.dtfoods.sgsacoletor.retrofit.dto.ErroServidorDto
import br.com.dtfoods.sgsacoletor.retrofit.dto.LoginDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {

  @Headers("Content-Type: application/json")
  @POST("PrLoginColetor")
  suspend fun login(@Body autenticacao: MutableMap<String, Login>): Response<LoginDto>

  @Headers("Content-Type: application/json")
  @POST("PrOperadorSenha")
  suspend fun cadastrarSenha(@Body autenticacao: MutableMap<String, Login>): Response<ErroServidorDto>

  @Headers("Content-Type: application/json")
  @POST("PrRecuperarSenhaColetor")
  suspend fun recuperarSenha(@Body autenticacao: MutableMap<String, Login>): Response<ErroServidorDto>
}
