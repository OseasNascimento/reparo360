-- Categorias financeiras necessárias pelo service
MERGE INTO categorias_financeiras (nome, descricao) KEY (nome)
VALUES
  ('SERVICO',      'Receitas de serviços'),
  ('MATERIAL',     'Custos de materiais'),
  ('DESLOCAMENTO', 'Custos de deslocamento');

-- Conta caixa padrão
MERGE INTO contas_caixa (nome, saldo_inicial) KEY (nome)
VALUES ('CAIXA_PRINCIPAL', 0.00);
