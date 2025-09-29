# AgendamentosApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**create3**](#create3) | **POST** /api/agendamentos | Criar um novo agendamento|
|[**criarOs**](#criaros) | **POST** /api/agendamentos/{id}/os | |
|[**delete4**](#delete4) | **DELETE** /api/agendamentos/{id} | Excluir agendamento por ID|
|[**findAll4**](#findall4) | **GET** /api/agendamentos | Listar todos os agendamentos|
|[**findById4**](#findbyid4) | **GET** /api/agendamentos/{id} | Buscar agendamento por ID|
|[**ocupados**](#ocupados) | **GET** /api/agendamentos/disponibilidade | |
|[**update4**](#update4) | **PUT** /api/agendamentos/{id} | Atualizar status do agendamento|
|[**updateStatus**](#updatestatus) | **PATCH** /api/agendamentos/{id}/status | Alterar status do agendamento via PATCH|

# **create3**
> AgendamentoDTO create3(agendamentoDTO)


### Example

```typescript
import {
    AgendamentosApi,
    Configuration,
    AgendamentoDTO
} from './api';

const configuration = new Configuration();
const apiInstance = new AgendamentosApi(configuration);

let agendamentoDTO: AgendamentoDTO; //

const { status, data } = await apiInstance.create3(
    agendamentoDTO
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **agendamentoDTO** | **AgendamentoDTO**|  | |


### Return type

**AgendamentoDTO**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **criarOs**
> OrdemServicoDTO criarOs()


### Example

```typescript
import {
    AgendamentosApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new AgendamentosApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance.criarOs(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**number**] |  | defaults to undefined|


### Return type

**OrdemServicoDTO**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **delete4**
> delete4()


### Example

```typescript
import {
    AgendamentosApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new AgendamentosApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance.delete4(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**number**] |  | defaults to undefined|


### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **findAll4**
> Array<AgendamentoDTO> findAll4()


### Example

```typescript
import {
    AgendamentosApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new AgendamentosApi(configuration);

const { status, data } = await apiInstance.findAll4();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**Array<AgendamentoDTO>**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **findById4**
> AgendamentoDTO findById4()


### Example

```typescript
import {
    AgendamentosApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new AgendamentosApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance.findById4(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**number**] |  | defaults to undefined|


### Return type

**AgendamentoDTO**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **ocupados**
> Array<string> ocupados()


### Example

```typescript
import {
    AgendamentosApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new AgendamentosApi(configuration);

let tecnicoId: number; // (default to undefined)
let dia: string; // (default to undefined)

const { status, data } = await apiInstance.ocupados(
    tecnicoId,
    dia
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **tecnicoId** | [**number**] |  | defaults to undefined|
| **dia** | [**string**] |  | defaults to undefined|


### Return type

**Array<string>**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **update4**
> AgendamentoDTO update4(agendamentoDTO)


### Example

```typescript
import {
    AgendamentosApi,
    Configuration,
    AgendamentoDTO
} from './api';

const configuration = new Configuration();
const apiInstance = new AgendamentosApi(configuration);

let id: number; // (default to undefined)
let agendamentoDTO: AgendamentoDTO; //

const { status, data } = await apiInstance.update4(
    id,
    agendamentoDTO
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **agendamentoDTO** | **AgendamentoDTO**|  | |
| **id** | [**number**] |  | defaults to undefined|


### Return type

**AgendamentoDTO**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **updateStatus**
> AgendamentoDTO updateStatus()


### Example

```typescript
import {
    AgendamentosApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new AgendamentosApi(configuration);

let id: number; // (default to undefined)
let status: string; // (default to undefined)

const { status, data } = await apiInstance.updateStatus(
    id,
    status
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**number**] |  | defaults to undefined|
| **status** | [**string**] |  | defaults to undefined|


### Return type

**AgendamentoDTO**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

