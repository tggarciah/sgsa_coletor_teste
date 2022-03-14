package br.com.dtfoods.sgsacoletor.ui.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.dtfoods.sgsacoletor.databinding.ActivityLoginBinding
import br.com.dtfoods.sgsacoletor.databinding.FormularioLoginSenhaBinding
import br.com.dtfoods.sgsacoletor.extensions.exibirSnackbarNaTela
import br.com.dtfoods.sgsacoletor.model.Login
import br.com.dtfoods.sgsacoletor.room.repository.ResultSgsaColetor
import br.com.dtfoods.sgsacoletor.ui.SgsaColetorApplication
import br.com.dtfoods.sgsacoletor.ui.viewmodel.LoginViewModel
import br.com.dtfoods.sgsacoletor.ui.viewmodel.factory.LoginViewModelFactory

class LoginActivity : AppCompatActivity() {

  private lateinit var binding: ActivityLoginBinding
  private val viewModel: LoginViewModel by viewModels {
    LoginViewModelFactory((application as SgsaColetorApplication).loginRepository)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityLoginBinding.inflate(layoutInflater)
    setContentView(binding.root)

    var primeiroAcesso: Boolean

    binding.loginBotaoIniciarSessao.setOnClickListener {
      binding.loginProgressBarLoading.visibility = View.VISIBLE
      val username = binding.loginUsuarioNome.text.toString()
      val password = binding.loginUsuarioSenha.text.toString()

      primeiroAcesso = binding.loginTextinputlayoutPrimeiroAcesso.isChecked

      val bindingDialog = FormularioLoginSenhaBinding.inflate(layoutInflater)
      val builderDialog = AlertDialog.Builder(this)
      builderDialog
        .setTitle("Cadastrar Senha")
        .setView(bindingDialog.root)
        .setPositiveButton("Confirmar", null)
        .setNegativeButton("Cancelar") { dialogInterface, _ ->
          dialogInterface.cancel()
        }
      val mDialog = builderDialog.create()
      mDialog.setOnShowListener { dialogInterface ->
        val positive = mDialog.getButton(AlertDialog.BUTTON_POSITIVE)

        positive.setOnClickListener {
          bindingDialog.formularioLoginSenhaTextinputlayoutCampo1.error = null
          bindingDialog.formularioLoginSenhaTextinputlayoutCampo2.error = null

          val senha = bindingDialog.formularioLoginSenhaCampo1.text.toString()
          val senhaConfirmacao = bindingDialog.formularioLoginSenhaCampo2.text.toString()

          if (senha.isBlank()) {
            bindingDialog.formularioLoginSenhaTextinputlayoutCampo1.error = "Informe a senha"
          } else if (senhaConfirmacao.isBlank()) {
            bindingDialog.formularioLoginSenhaTextinputlayoutCampo2.error =
              "Informe a confirmaçao da senha"
          } else if (!senha.equals(senhaConfirmacao)) {
            bindingDialog.formularioLoginSenhaTextinputlayoutCampo2.error =
              "As senhas não são iguais"
          } else {
            val login = Login(username, senhaConfirmacao)
            viewModel.cadastrarSenha(login).observe(this) {
              binding.loginProgressBarLoading.visibility = View.GONE
              binding.loginTextinputlayoutPrimeiroAcesso.isChecked = false
              primeiroAcesso = binding.loginTextinputlayoutPrimeiroAcesso.isChecked

              when (it) {
                is ResultSgsaColetor.Success -> {
                  exibirSnackbarNaTela(binding.root, "Senha cadastrada com sucesso")
                  dialogInterface.dismiss()
                }
                is ResultSgsaColetor.Error -> exibirSnackbarNaTela(
                  bindingDialog.root,
                  it.exception.message!!
                )
              }
            }
          }
        }
      }

      if (primeiroAcesso) {
        mDialog.show()
      } else {
        binding.loginProgressBarLoading.visibility = View.VISIBLE

        val login = Login(username = username, password = password)
        viewModel.realizarLogin(login).observe(this) {
          binding.loginProgressBarLoading.visibility = View.GONE

          when (it) {
            is ResultSgsaColetor.Success -> {
              val usuario = it.data
              val intent = Intent(this, ImpressaoProdutoClienteListaActivity::class.java)
              intent.putExtra(USUARIO_ID_CHAVE, usuario.id)
              startActivity(intent)

              finish()
            }
            is ResultSgsaColetor.Error -> exibirSnackbarNaTela(binding.root, it.exception.message!!)
          }
        }
      }
    }

    binding.loginButtonEsqueciSenha.setOnClickListener {
      val username = binding.loginUsuarioNome.text.toString()
      val password = binding.loginUsuarioSenha.text.toString()

      val login = Login(username = username, password = password)

      viewModel.recuperarSenha(login).observe(this) {
        when (it) {
          is ResultSgsaColetor.Success -> exibirSnackbarNaTela(
            binding.root,
            it.data.erroDescricao!!
          )
          is ResultSgsaColetor.Error -> exibirSnackbarNaTela(binding.root, it.exception.message!!)
        }
      }
    }
  }
}