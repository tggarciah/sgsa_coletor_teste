package br.com.dtfoods.sgsacoletor.retrofit.dto

import br.com.dtfoods.sgsacoletor.enumeration.TipoStatusImpressao
import br.com.dtfoods.sgsacoletor.model.ImpressaoProdutoLotes
import br.com.dtfoods.sgsacoletor.model.Solicitacao

data class ImpressaoDto(
  val id: Long,
  val data: String?,
  val status: TipoStatusImpressao?,
  val dataAceite: String?,
  val solicitacaoId: Long?,
  val solicitacao: Solicitacao?,
  val produtos: List<ImpressaoProdutoLotes>?,
)
