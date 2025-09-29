# PeasUtilizadasApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**listarPorOrdem**](#listarporordem) | **GET** /api/estoque/pecas-utilizadas/ordem/{ordemId} | Listar peças utilizadas de uma ordem de serviço específica|
|[**salvarPorOrdem**](#salvarporordem) | **POST** /api/estoque/pecas-utilizadas/ordem/{ordemId} | Salvar peças utilizadas para uma ordem de serviço|

# **listarPorOrdem**
> Array<PecaUtilizadaDTO> listarPorOrdem()


### Example

```typescript
import {
    PeasUtilizadasApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new PeasUtilizadasApi(configuration);

let ordemId: number; // (default to undefined)

const { status, data } = await apiInstance.listarPorOrdem(
    ordemId
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **ordemId** | [**number**] |  | defaults to undefined|


### Return type

**Array<PecaUtilizadaDTO>**

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

# **salvarPorOrdem**
> Array<PecaUtilizadaDTO> salvarPorOrdem(pecaUtilizadaDTO)


### Example

```typescript
import {
    PeasUtilizadasApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new PeasUtilizadasApi(configuration);

let ordemId: number; // (default to undefined)
let pecaUtilizadaDTO: Array<PecaUtilizadaDTO>; //

const { status, data } = await apiInstance.salvarPorOrdem(
    ordemId,
    pecaUtilizadaDTO
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **pecaUtilizadaDTO** | **Array<PecaUtilizadaDTO>**|  | |
| **ordemId** | [**number**] |  | defaults to undefined|


### Return type

**Array<PecaUtilizadaDTO>**

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

