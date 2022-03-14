package br.com.dtfoods.sgsacoletor.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun Activity.exibirSnackbarNaTela(view: View, mensagemErro: String) {
  Snackbar.make(view, mensagemErro, Snackbar.LENGTH_LONG).show()
}

fun Activity.exibirToastNaTela(context: Context, mensagemErro: String) {
  Toast.makeText(context, mensagemErro, Toast.LENGTH_LONG).show()
}