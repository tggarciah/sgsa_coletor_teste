package br.com.dtfoods.sgsacoletor.model

data class ImpressaoProdutoCliente(
  val impressaoId: Long,
  val impressaoProdutoId: Long,
  val impressaoProdutoCodigoImpressao: Long?,
  val impressaoProdutoCodigoImpressaoDescricao: String?,
  val impressaoProdutoDataInicio: String?,
  val impressaoProdutoDataTermino: String?,
  val impressaoProdutoQuantidade: Long?,
  val impressaoProdutoQuantidadeRestante: Long?,
  val produtoId: Long,
  val produtoModelo: String?,
  val clienteId: Long,
  val clienteCnpj: String?,
  val clienteNomeFantasia: String?,
  val solicitacaoId: Long?,
  val solicitacaoNumeroPedido: Long?
)