# OrdemServicoDTO

Dados de uma ordem de serviço

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **number** | Identificador da ordem de serviço | [optional] [default to undefined]
**agendamentoId** | **number** | ID do agendamento associado | [default to undefined]
**logradouro** | **string** | Logradouro do endereço do cliente | [optional] [default to undefined]
**numero** | **string** | Número do endereço do cliente | [optional] [default to undefined]
**complemento** | **string** | Complemento do endereço do cliente | [optional] [default to undefined]
**bairro** | **string** | Bairro do endereço do cliente | [optional] [default to undefined]
**cidade** | **string** | Cidade do endereço do cliente | [optional] [default to undefined]
**uf** | **string** | UF do endereço do cliente | [optional] [default to undefined]
**cep** | **string** | CEP do endereço do cliente | [optional] [default to undefined]
**status** | **string** | Status da ordem de serviço | [default to undefined]
**valorServico** | **number** | Valor cobrado pelo serviço | [default to undefined]
**valorMateriais** | **number** | Custo dos materiais utilizados | [default to undefined]
**kmDeslocamento** | **number** | Quilometragem percorrida para o serviço | [default to undefined]
**observacoes** | **string** | Observações adicionais da ordem de serviço | [optional] [default to undefined]

## Example

```typescript
import { OrdemServicoDTO } from './api';

const instance: OrdemServicoDTO = {
    id,
    agendamentoId,
    logradouro,
    numero,
    complemento,
    bairro,
    cidade,
    uf,
    cep,
    status,
    valorServico,
    valorMateriais,
    kmDeslocamento,
    observacoes,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
