package br.com.dtfoods.sgsacoletor.model

data class ImpressaoProdutoLotes(
  val impressaoId: Long,
  val id: Long,
  val produtoId: Long?,
  val quantidade: Long?,
  val dataInicio: String?,
  val dataTermino: String?,
  val codigoImpressao: Long?,
  val codigoImpressaoDescricao: String?,
  val lotes: List<ImpressaoProdutoLote>
)