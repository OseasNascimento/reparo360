# ClienteDTO

Dados de um cliente

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**idCliente** | **number** | Identificador do cliente | [optional] [default to undefined]
**nome** | **string** | Nome completo do cliente | [default to undefined]
**email** | **string** | E-mail do cliente | [default to undefined]
**telefone** | **string** | Telefone de contato do cliente | [default to undefined]
**logradouro** | **string** | Logradouro (rua, avenida etc.) | [default to undefined]
**numero** | **string** | Número do endereço | [default to undefined]
**complemento** | **string** | Complemento do endereço | [optional] [default to undefined]
**bairro** | **string** | Bairro do cliente | [default to undefined]
**cidade** | **string** | Cidade do cliente | [default to undefined]
**uf** | **string** | Sigla da UF (2 caracteres) | [default to undefined]
**cep** | **string** | CEP do cliente | [default to undefined]
**dataCadastro** | **string** | Data de cadastro do cliente | [optional] [default to undefined]
**origem** | **string** | Origem do cadastro do cliente (p. ex., WEB, APP) | [optional] [default to undefined]

## Example

```typescript
import { ClienteDTO } from './api';

const instance: ClienteDTO = {
    idCliente,
    nome,
    email,
    telefone,
    logradouro,
    numero,
    complemento,
    bairro,
    cidade,
    uf,
    cep,
    dataCadastro,
    origem,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
