package br.com.dtfoods.sgsacoletor.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Usuario(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0L,
  val nome: String?,
  val login: String?,
  val email: String?
)