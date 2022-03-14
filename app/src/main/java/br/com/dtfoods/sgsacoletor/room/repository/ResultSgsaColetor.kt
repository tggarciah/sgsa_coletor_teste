package br.com.dtfoods.sgsacoletor.room.repository

sealed class ResultSgsaColetor<out T> {
  data class Success<out T>(val data: T) : ResultSgsaColetor<T>()
  data class Error(val exception: Exception) : ResultSgsaColetor<Nothing>()
}