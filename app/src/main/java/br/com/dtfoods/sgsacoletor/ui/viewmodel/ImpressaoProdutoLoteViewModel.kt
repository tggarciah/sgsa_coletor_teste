package br.com.dtfoods.sgsacoletor.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.dtfoods.sgsacoletor.model.ImpressaoProdutoLote
import br.com.dtfoods.sgsacoletor.room.repository.ImpressaoProdutoLoteRepository
import br.com.dtfoods.sgsacoletor.room.repository.ResultSgsaColetor

class ImpressaoProdutoLoteViewModel(
  private val repository: ImpressaoProdutoLoteRepository
) : ViewModel() {

  fun salvar(
    impressaoProdutoLote: ImpressaoProdutoLote,
    usuarioId: Long
  ): LiveData<ResultSgsaColetor<Void?>> {
    return repository.inserir(impressaoProdutoLote, usuarioId)
  }
}