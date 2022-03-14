package br.com.dtfoods.sgsacoletor.model.barcode

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.graphics.RectF
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class QrCodeAnalyser(
  private val context: Context,
  private val barcodeBoxView: BarcodeBoxView,
  private val alturaCaixa: Float,
  private val larguraCaixa: Float,
  var qrCodeLido: (valorLido: String?) -> Unit = {}
) : ImageAnalysis.Analyzer {

  private var scaleX = 1f
  private var scaleY = 1f

  private fun translateX(x: Float) = x * scaleX
  private fun translateY(y: Float) = y * scaleY

  private fun configuraContornoRect(rect: Rect) = RectF(
    translateX(rect.left.toFloat()),
    translateY(rect.top.toFloat()),
    translateX(rect.right.toFloat()),
    translateY(rect.bottom.toFloat())
  )

  @SuppressLint("UnsafeOptInUsageError")
  override fun analyze(imagem: ImageProxy) {

    val auxImagem = imagem.image
    if (auxImagem != null) {
      val inputImage = InputImage.fromMediaImage(auxImagem, imagem.imageInfo.rotationDegrees)
      val qrCodeOpcao = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
        .build()
      val scanner = BarcodeScanning.getClient(qrCodeOpcao)

      scaleX = alturaCaixa / auxImagem.height.toFloat()
      scaleY = larguraCaixa / auxImagem.width.toFloat()

      scanner.process(inputImage)
        .addOnSuccessListener { barcodes ->
          if (barcodes.isNotEmpty()) {
            barcodes.forEach { barcode ->
              val valorLido = barcode.rawValue

              barcode.boundingBox?.let { rect ->
                barcodeBoxView.setRect(configuraContornoRect(rect))
              }
              qrCodeLido(valorLido)
            }
          } else {
            barcodeBoxView.setRect(RectF())
          }
        }
        .addOnFailureListener { }
    }
    imagem.close()
  }
}