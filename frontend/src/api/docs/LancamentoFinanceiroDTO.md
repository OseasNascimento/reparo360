# LancamentoFinanceiroDTO

Dados de um lançamento financeiro

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **number** | Identificador do lançamento | [optional] [default to undefined]
**contaCaixaId** | **number** | ID da conta caixa associada | [default to undefined]
**categoriaFinanceiraId** | **number** | ID da categoria financeira associada | [default to undefined]
**valor** | **number** | Valor do lançamento | [default to undefined]
**dataLancamento** | **string** | Data e hora do lançamento | [optional] [default to undefined]
**tipoTransacao** | **string** | Tipo de transação (RECEITA ou DESPESA) | [default to undefined]
**descricao** | **string** | Descrição complementar do lançamento | [optional] [default to undefined]

## Example

```typescript
import { LancamentoFinanceiroDTO } from './api';

const instance: LancamentoFinanceiroDTO = {
    id,
    contaCaixaId,
    categoriaFinanceiraId,
    valor,
    dataLancamento,
    tipoTransacao,
    descricao,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
