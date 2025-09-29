# MovimentacaoEstoqueDTO

Dados de uma movimentação de estoque

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **number** | Identificador da movimentação de estoque | [optional] [default to undefined]
**produtoId** | **number** | ID do produto movimentado | [default to undefined]
**tipo** | **string** | Tipo de movimentação (ENTRADA ou SAÍDA) | [default to undefined]
**quantidade** | **number** | Quantidade de itens movimentados | [default to undefined]
**dataMovimentacao** | **string** | Data e hora da movimentação | [default to undefined]
**descricao** | **string** | Descrição opcional sobre a movimentação | [optional] [default to undefined]

## Example

```typescript
import { MovimentacaoEstoqueDTO } from './api';

const instance: MovimentacaoEstoqueDTO = {
    id,
    produtoId,
    tipo,
    quantidade,
    dataMovimentacao,
    descricao,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
