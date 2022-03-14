package br.com.dtfoods.sgsacoletor.room.dao

const val QUERY_IMPRESAO_PRODUTO_LOTE =
  "SELECT IMPPRO.impressao_id AS impressaoId, " +
          "IMPPRO.id AS impressaoProdutoId, " +
          "IMPPRO.codigo_impressao AS impressaoProdutoCodigoImpressao, " +
          "IMPPRO.codigo_impressao_descricao AS impressaoProdutoCodigoImpressaoDescricao, " +
          "IMPPRO.data_inicio AS impressaoProdutoDataInicio, " +
          "IMPPRO.data_termino AS impressaoProdutoDataTermino, " +
          "IMPPRO.quantidade AS impressaoProdutoQuantidade, " +
          "(SELECT IP.quantidade - COUNT(IPL.id) " +
              "FROM ImpressaoProduto IP " +
              "LEFT JOIN ImpressaoProdutoLote IPL ON (IPL.impressao_id = IP.impressao_id and IPL.impressao_produto_id = IP.id) " +
              "WHERE IP.impressao_id = IMPPRO.impressao_id and IP.id = IMPPRO.id" +
          ") AS impressaoProdutoQuantidadeRestante, " +
          "PROD.id AS produtoId, " +
          "PROD.modelo AS produtoModelo, " +
          "CLI.id AS clienteId, " +
          "CLI.cnpj AS clienteCnpj, " +
          "CLI.nome_fantasia AS clienteNomeFantasia, " +
          "SOL.id AS solicitacaoId, " +
          "SOL.numero_pedido AS solicitacaoNumeroPedido " +
  "FROM ImpressaoProduto AS IMPPRO " +
  "JOIN Produto AS PROD ON (PROD.id = IMPPRO.produto_id) " +
  "JOIN Impressao AS IMP ON (IMP.id = IMPPRO.impressao_id) " +
  "JOIN Solicitacao AS SOL ON (SOL.id = IMP.solicitacao_id)" +
  "JOIN Cliente AS CLI ON (CLI.id = SOL.cliente_id)"


const val QUERY_IMPRESSAO_PRODUTO_LOTE_COM_WHERE =
  QUERY_IMPRESAO_PRODUTO_LOTE + " WHERE IMPPRO.impressao_id = :impressaoId and IMPPRO.id = :id"