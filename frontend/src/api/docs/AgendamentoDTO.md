# AgendamentoDTO

Dados de um agendamento de serviço

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **number** | Identificador do agendamento | [optional] [default to undefined]
**nomeCliente** | **string** | Nome completo do cliente | [default to undefined]
**email** | **string** | E-mail do cliente | [default to undefined]
**telefone** | **string** | Telefone de contato do cliente | [default to undefined]
**clienteId** | **number** | ID do cliente | [default to undefined]
**tecnicoId** | **number** | ID do técnico responsável | [default to undefined]
**dataAgendamento** | **string** | Data e hora planejadas para o serviço | [default to undefined]
**servicosId** | **Array&lt;number&gt;** | Lista de IDs de serviços solicitados | [default to undefined]
**observacoes** | **string** | Observações adicionais sobre o agendamento | [optional] [default to undefined]
**status** | **string** | Status atual do agendamento | [default to undefined]
**logradouro** | **string** | Logradouro do local do serviço | [default to undefined]
**numero** | **string** | Número do imóvel | [optional] [default to undefined]
**complemento** | **string** | Complemento do endereço | [optional] [default to undefined]
**bairro** | **string** | Bairro do endereço | [default to undefined]
**cidade** | **string** | Cidade do endereço | [default to undefined]
**uf** | **string** | Unidade Federativa (estado) | [default to undefined]
**cep** | **string** | CEP do endereço | [default to undefined]

## Example

```typescript
import { AgendamentoDTO } from './api';

const instance: AgendamentoDTO = {
    id,
    nomeCliente,
    email,
    telefone,
    clienteId,
    tecnicoId,
    dataAgendamento,
    servicosId,
    observacoes,
    status,
    logradouro,
    numero,
    complemento,
    bairro,
    cidade,
    uf,
    cep,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
