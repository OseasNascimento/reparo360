# OrdemServicoControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**atualizarOrdem**](#atualizarordem) | **PUT** /api/os/{id} | |
|[**buscarPorId**](#buscarporid) | **GET** /api/os/{id} | |
|[**criarOrdem**](#criarordem) | **POST** /api/agendamentos/{agendamentoId}/os | |
|[**excluirOrdem**](#excluirordem) | **DELETE** /api/os/{id} | |
|[**listarPorStatus**](#listarporstatus) | **GET** /api/os/status | |
|[**listarTodas**](#listartodas) | **GET** /api/os | |

# **atualizarOrdem**
> OrdemServicoDTO atualizarOrdem(ordemServicoDTO)


### Example

```typescript
import {
    OrdemServicoControllerApi,
    Configuration,
    OrdemServicoDTO
} from './api';

const configuration = new Configuration();
const apiInstance = new OrdemServicoControllerApi(configuration);

let id: number; // (default to undefined)
let ordemServicoDTO: OrdemServicoDTO; //

const { status, data } = await apiInstance.atualizarOrdem(
    id,
    ordemServicoDTO
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **ordemServicoDTO** | **OrdemServicoDTO**|  | |
| **id** | [**number**] |  | defaults to undefined|


### Return type

**OrdemServicoDTO**

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

# **buscarPorId**
> OrdemServicoDTO buscarPorId()


### Example

```typescript
import {
    OrdemServicoControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new OrdemServicoControllerApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance.buscarPorId(
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

# **criarOrdem**
> OrdemServicoDTO criarOrdem()


### Example

```typescript
import {
    OrdemServicoControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new OrdemServicoControllerApi(configuration);

let agendamentoId: number; // (default to undefined)

const { status, data } = await apiInstance.criarOrdem(
    agendamentoId
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **agendamentoId** | [**number**] |  | defaults to undefined|


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

# **excluirOrdem**
> excluirOrdem()


### Example

```typescript
import {
    OrdemServicoControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new OrdemServicoControllerApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance.excluirOrdem(
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

# **listarPorStatus**
> Array<OrdemServicoDTO> listarPorStatus()


### Example

```typescript
import {
    OrdemServicoControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new OrdemServicoControllerApi(configuration);

let status: 'AGENDADA' | 'EM_ANDAMENTO' | 'ASSINADA' | 'RECUSADA' | 'CONCLUIDA' | 'CANCELADA'; // (default to undefined)

const { status, data } = await apiInstance.listarPorStatus(
    status
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **status** | [**&#39;AGENDADA&#39; | &#39;EM_ANDAMENTO&#39; | &#39;ASSINADA&#39; | &#39;RECUSADA&#39; | &#39;CONCLUIDA&#39; | &#39;CANCELADA&#39;**]**Array<&#39;AGENDADA&#39; &#124; &#39;EM_ANDAMENTO&#39; &#124; &#39;ASSINADA&#39; &#124; &#39;RECUSADA&#39; &#124; &#39;CONCLUIDA&#39; &#124; &#39;CANCELADA&#39;>** |  | defaults to undefined|


### Return type

**Array<OrdemServicoDTO>**

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

# **listarTodas**
> Array<OrdemServicoDTO> listarTodas()


### Example

```typescript
import {
    OrdemServicoControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new OrdemServicoControllerApi(configuration);

const { status, data } = await apiInstance.listarTodas();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**Array<OrdemServicoDTO>**

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

