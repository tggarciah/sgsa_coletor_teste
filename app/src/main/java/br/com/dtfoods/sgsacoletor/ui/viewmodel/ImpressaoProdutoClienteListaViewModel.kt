package br.com.dtfoods.sgsacoletor.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.dtfoods.sgsacoletor.model.ImpressaoProdutoCliente
import br.com.dtfoods.sgsacoletor.room.repository.ImpressaoProdutoClienteRepository
import br.com.dtfoods.sgsacoletor.room.repository.ResultSgsaColetor

class ImpressaoProdutoClienteListaViewModel(
  private val repository: ImpressaoProdutoClienteRepository,
  private val usuario: Long
) : ViewModel() {

  fun buscarTodos(): LiveData<ResultSgsaColetor<List<ImpressaoProdutoCliente>>> {
    return repository.buscarTodos(usuario)
  }
}
