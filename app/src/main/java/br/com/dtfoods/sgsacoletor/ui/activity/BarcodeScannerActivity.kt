package br.com.dtfoods.sgsacoletor.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import br.com.dtfoods.sgsacoletor.databinding.ActivityBarcodeScannerBinding
import br.com.dtfoods.sgsacoletor.extensions.exibirToastNaTela
import br.com.dtfoods.sgsacoletor.model.barcode.BarcodeBoxView
import br.com.dtfoods.sgsacoletor.model.barcode.QrCodeAnalyser
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class BarcodeScannerActivity : AppCompatActivity() {

  private val binding by lazy {
    ActivityBarcodeScannerBinding.inflate(layoutInflater)
  }

  private lateinit var cameraExecutor: ExecutorService
  private lateinit var barcodeBoxView: BarcodeBoxView

  companion object {
    private val PERMISSOES_REQUERIDAS = arrayOf(Manifest.permission.CAMERA)
    private const val CODIGO_PERMISSAO_SOLICITADA = 10
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    cameraExecutor = Executors.newSingleThreadExecutor()

    barcodeBoxView = BarcodeBoxView(this)
    addContentView(
      barcodeBoxView,
      ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
      )
    )

    if (existePermissaoRecurso()) {
      iniciarCamera()
    } else {
      ActivityCompat.requestPermissions(this, PERMISSOES_REQUERIDAS, CODIGO_PERMISSAO_SOLICITADA)
    }
  }

  private fun existePermissaoRecurso() = PERMISSOES_REQUERIDAS.all {
    ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
  }

  override fun onDestroy() {
    super.onDestroy()
    cameraExecutor.shutdown()
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    if (requestCode == CODIGO_PERMISSAO_SOLICITADA) {
      if (existePermissaoRecurso()) {
        iniciarCamera()
      } else {
        exibirToastNaTela(applicationContext, MENSAGEM_ERRO_PERMISSAO_CAMERA)
        finish()
      }
    }
  }

  private fun iniciarCamera() {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
    val campoCamera = binding.activityBarcodeScannerPreviewview

    cameraProviderFuture.addListener(Runnable {
      val cameraProvider = cameraProviderFuture.get()
      val imagemCapturada = ImageCapture.Builder().build()
      val cameraPadrao = CameraSelector.DEFAULT_BACK_CAMERA

      val visualizacaoCamera = Preview.Builder()
        .build()
        .also {
          it.setSurfaceProvider(campoCamera.surfaceProvider)
        }

      val imagemAnalisada = ImageAnalysis.Builder()
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build()
        .also {
          it.setAnalyzer(cameraExecutor, QrCodeAnalyser(
            this,
            barcodeBoxView,
            binding.activityBarcodeScannerPreviewview.width.toFloat(),
            binding.activityBarcodeScannerPreviewview.height.toFloat()
          ) {
            val intentResposta = Intent()
            intentResposta.putExtra(INICIAR_HARDWARE_CAMERA, it)
            setResult(RESULT_OK, intentResposta)
            finish()
          })
        }

      try {
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
          this,
          cameraPadrao,
          visualizacaoCamera,
          imagemCapturada,
          imagemAnalisada
        )
      } catch (e: Exception) {
        exibirToastNaTela(applicationContext, "$MENSAGEM_ERRO_EXCECAO_CAMERA ${e.message}")
        finish()
      }
    }, ContextCompat.getMainExecutor(this))
  }
}