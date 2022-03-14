package br.com.dtfoods.sgsacoletor.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.dtfoods.sgsacoletor.model.Usuario

@Dao
interface UsuarioDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun inserir(usuario: Usuario)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun inserir(usuario: List<Usuario>)

  @Delete
  suspend fun remover(usuario: Usuario)

  @Update
  suspend fun atualizar(usuario: Usuario)

  @Query("SELECT * FROM Usuario")
  fun buscarTodos(): LiveData<List<Usuario>>

  @Query("SELECT * FROM Usuario WHERE id = :id")
  fun buscarPorId(id: Long): LiveData<Usuario?>

  @Query("SELECT * FROM Usuario WHERE id = :id")
  suspend fun buscarId(id: Long): Usuario
}