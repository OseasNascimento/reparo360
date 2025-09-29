# ProdutoDTO

Dados de um produto em estoque

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **number** | Identificador do produto | [optional] [default to undefined]
**nome** | **string** | Nome do produto | [default to undefined]
**descricao** | **string** | Descrição opcional do produto | [optional] [default to undefined]
**quantidadeEstoque** | **number** | Quantidade disponível em estoque | [default to undefined]
**valor** | **number** | Valor do produto | [default to undefined]

## Example

```typescript
import { ProdutoDTO } from './api';

const instance: ProdutoDTO = {
    id,
    nome,
    descricao,
    quantidadeEstoque,
    valor,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
