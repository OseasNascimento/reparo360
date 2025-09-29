# TecnicoDTO

Dados de um técnico

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **number** | Identificador do técnico | [optional] [default to undefined]
**nome** | **string** | Nome completo do técnico | [default to undefined]
**email** | **string** | E-mail de acesso do técnico | [default to undefined]
**telefone** | **string** | Telefone de contato do técnico | [default to undefined]
**especialidade** | **string** | Especialidade do técnico | [default to undefined]
**dataContratacao** | **string** | Data de contratação do técnico | [default to undefined]
**senha** | **string** | Senha criptografada do técnico | [default to undefined]
**roles** | [**Set&lt;RoleDTO&gt;**](RoleDTO.md) | Conjunto de roles atribuídas ao técnico | [optional] [default to undefined]

## Example

```typescript
import { TecnicoDTO } from './api';

const instance: TecnicoDTO = {
    id,
    nome,
    email,
    telefone,
    especialidade,
    dataContratacao,
    senha,
    roles,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
