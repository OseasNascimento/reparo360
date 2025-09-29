# ContasCaixaApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**atualizar1**](#atualizar1) | **PUT** /api/financeiro/contas/{id} | Atualizar uma conta caixa existente|
|[**buscarPorId2**](#buscarporid2) | **GET** /api/financeiro/contas/{id} | Buscar conta caixa por ID|
|[**criar1**](#criar1) | **POST** /api/financeiro/contas | Criar uma nova conta caixa|
|[**excluir1**](#excluir1) | **DELETE** /api/financeiro/contas/{id} | Excluir uma conta caixa por ID|
|[**listarTodos1**](#listartodos1) | **GET** /api/financeiro/contas | Listar todas as contas caixa|

# **atualizar1**
> ContaCaixaDTO atualizar1(contaCaixaDTO)


### Example

```typescript
import {
    ContasCaixaApi,
    Configuration,
    ContaCaixaDTO
} from './api';

const configuration = new Configuration();
const apiInstance = new ContasCaixaApi(configuration);

let id: number; // (default to undefined)
let contaCaixaDTO: ContaCaixaDTO; //

const { status, data } = await apiInstance.atualizar1(
    id,
    contaCaixaDTO
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **contaCaixaDTO** | **ContaCaixaDTO**|  | |
| **id** | [**number**] |  | defaults to undefined|


### Return type

**ContaCaixaDTO**

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

# **buscarPorId2**
> ContaCaixaDTO buscarPorId2()


### Example

```typescript
import {
    ContasCaixaApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ContasCaixaApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance.buscarPorId2(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**number**] |  | defaults to undefined|


### Return type

**ContaCaixaDTO**

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

# **criar1**
> ContaCaixaDTO criar1(contaCaixaDTO)


### Example

```typescript
import {
    ContasCaixaApi,
    Configuration,
    ContaCaixaDTO
} from './api';

const configuration = new Configuration();
const apiInstance = new ContasCaixaApi(configuration);

let contaCaixaDTO: ContaCaixaDTO; //

const { status, data } = await apiInstance.criar1(
    contaCaixaDTO
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **contaCaixaDTO** | **ContaCaixaDTO**|  | |


### Return type

**ContaCaixaDTO**

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

# **excluir1**
> excluir1()


### Example

```typescript
import {
    ContasCaixaApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ContasCaixaApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance.excluir1(
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

# **listarTodos1**
> Array<ContaCaixaDTO> listarTodos1()


### Example

```typescript
import {
    ContasCaixaApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ContasCaixaApi(configuration);

const { status, data } = await apiInstance.listarTodos1();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**Array<ContaCaixaDTO>**

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

