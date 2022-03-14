package br.com.dtfoods.sgsacoletor.retrofit

import br.com.dtfoods.sgsacoletor.retrofit.service.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL_DEV = "http://IP_API:PORTA"

class SgsaColetorRetrofit {

  companion object {
    private val client by lazy {
      val interceptor = HttpLoggingInterceptor()
      interceptor.level = HttpLoggingInterceptor.Level.BODY
      OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()
    }

    private val retrofit by lazy {
      Retrofit.Builder()
        .baseUrl(BASE_URL_DEV)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
    }
  }

  val usuarioService: UsuarioService by lazy {
    retrofit.create(UsuarioService::class.java)
  }
  val clienteService: ClienteService by lazy {
    retrofit.create(ClienteService::class.java)
  }
  val produtoService: ProdutoService by lazy {
    retrofit.create(ProdutoService::class.java)
  }
  val impressaoProdutoService: ImpressaoProdutoService by lazy {
    retrofit.create(ImpressaoProdutoService::class.java)
  }
  val impressaoProdutoLoteService: ImpressaoProdutoLoteService by lazy {
    retrofit.create(ImpressaoProdutoLoteService::class.java)
  }
  val loginService: LoginService by lazy {
    retrofit.create(LoginService::class.java)
  }
}