# ServicoDTO

Dados de um serviço oferecido pelo sistema

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**idServico** | **number** | Identificador do serviço | [optional] [default to undefined]
**descricao** | **string** | Descrição detalhada do serviço | [default to undefined]
**categoria** | **string** | Categoria à qual o serviço pertence | [default to undefined]
**valorEstimado** | **number** | Valor estimado do serviço | [default to undefined]
**tempoEstimado** | **number** | Tempo estimado para conclusão do serviço (em minutos) | [default to undefined]

## Example

```typescript
import { ServicoDTO } from './api';

const instance: ServicoDTO = {
    idServico,
    descricao,
    categoria,
    valorEstimado,
    tempoEstimado,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
