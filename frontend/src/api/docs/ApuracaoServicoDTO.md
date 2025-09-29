# ApuracaoServicoDTO

Dados de apuração financeira de um serviço

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **number** | Identificador da apuração | [optional] [default to undefined]
**ordemServicoId** | **number** | ID da Ordem de Serviço associada | [default to undefined]
**valorServico** | **number** | Valor total cobrado pelo serviço | [default to undefined]
**valorMateriais** | **number** | Valor total dos materiais utilizados | [default to undefined]
**kmDeslocamento** | **number** | Quilometragem de deslocamento para o serviço | [default to undefined]
**receitaLiquida** | **number** | Receita líquida após custos | [default to undefined]
**dataApuracao** | **string** | Data e hora em que a apuração foi realizada | [default to undefined]

## Example

```typescript
import { ApuracaoServicoDTO } from './api';

const instance: ApuracaoServicoDTO = {
    id,
    ordemServicoId,
    valorServico,
    valorMateriais,
    kmDeslocamento,
    receitaLiquida,
    dataApuracao,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
