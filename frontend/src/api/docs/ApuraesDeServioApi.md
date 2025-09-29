# ApuraesDeServioApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**buscarPorId4**](#buscarporid4) | **GET** /api/financeiro/apuracoes-servico/{id} | Buscar apuração de serviço por ID|
|[**listarPorOrdemServico**](#listarporordemservico) | **GET** /api/financeiro/apuracoes-servico/ordem/{ordemServicoId} | Listar apurações de serviço por Ordem de Serviço|
|[**listarTodas2**](#listartodas2) | **GET** /api/financeiro/apuracoes-servico | Listar todas as apurações de serviço|

# **buscarPorId4**
> ApuracaoServicoDTO buscarPorId4()


### Example

```typescript
import {
    ApuraesDeServioApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ApuraesDeServioApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance.buscarPorId4(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**number**] |  | defaults to undefined|


### Return type

**ApuracaoServicoDTO**

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

# **listarPorOrdemServico**
> Array<ApuracaoServicoDTO> listarPorOrdemServico()


### Example

```typescript
import {
    ApuraesDeServioApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ApuraesDeServioApi(configuration);

let ordemServicoId: number; // (default to undefined)

const { status, data } = await apiInstance.listarPorOrdemServico(
    ordemServicoId
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **ordemServicoId** | [**number**] |  | defaults to undefined|


### Return type

**Array<ApuracaoServicoDTO>**

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

# **listarTodas2**
> Array<ApuracaoServicoDTO> listarTodas2()


### Example

```typescript
import {
    ApuraesDeServioApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ApuraesDeServioApi(configuration);

const { status, data } = await apiInstance.listarTodas2();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**Array<ApuracaoServicoDTO>**

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

