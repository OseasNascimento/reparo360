# PecaUtilizadaDTO

Dados de uma peça utilizada em Ordem de Serviço

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **number** | Identificador da peça utilizada | [optional] [default to undefined]
**descricao** | **string** | Descrição da peça utilizada | [default to undefined]
**produtoId** | **number** | ID do produto relacionado | [default to undefined]
**quantidade** | **number** | Quantidade de peças utilizadas | [default to undefined]
**ordemServicoId** | **number** | ID da Ordem de Serviço à qual a peça está vinculada | [default to undefined]

## Example

```typescript
import { PecaUtilizadaDTO } from './api';

const instance: PecaUtilizadaDTO = {
    id,
    descricao,
    produtoId,
    quantidade,
    ordemServicoId,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
