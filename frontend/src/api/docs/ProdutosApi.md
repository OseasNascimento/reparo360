# ProdutosApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**atualizar3**](#atualizar3) | **PUT** /api/estoque/produtos/{id} | Atualizar um produto existente|
|[**buscarPorId5**](#buscarporid5) | **GET** /api/estoque/produtos/{id} | Buscar produto por ID|
|[**criar3**](#criar3) | **POST** /api/estoque/produtos | Criar um novo produto|
|[**excluir3**](#excluir3) | **DELETE** /api/estoque/produtos/{id} | Excluir um produto por ID|
|[**listarTodos2**](#listartodos2) | **GET** /api/estoque/produtos | Listar todos os produtos|

# **atualizar3**
> ProdutoDTO atualizar3(produtoDTO)


### Example

```typescript
import {
    ProdutosApi,
    Configuration,
    ProdutoDTO
} from './api';

const configuration = new Configuration();
const apiInstance = new ProdutosApi(configuration);

let id: number; // (default to undefined)
let produtoDTO: ProdutoDTO; //

const { status, data } = await apiInstance.atualizar3(
    id,
    produtoDTO
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **produtoDTO** | **ProdutoDTO**|  | |
| **id** | [**number**] |  | defaults to undefined|


### Return type

**ProdutoDTO**

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

# **buscarPorId5**
> ProdutoDTO buscarPorId5()


### Example

```typescript
import {
    ProdutosApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ProdutosApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance.buscarPorId5(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**number**] |  | defaults to undefined|


### Return type

**ProdutoDTO**

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

# **criar3**
> ProdutoDTO criar3(produtoDTO)


### Example

```typescript
import {
    ProdutosApi,
    Configuration,
    ProdutoDTO
} from './api';

const configuration = new Configuration();
const apiInstance = new ProdutosApi(configuration);

let produtoDTO: ProdutoDTO; //

const { status, data } = await apiInstance.criar3(
    produtoDTO
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **produtoDTO** | **ProdutoDTO**|  | |


### Return type

**ProdutoDTO**

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

# **excluir3**
> excluir3()


### Example

```typescript
import {
    ProdutosApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ProdutosApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance.excluir3(
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

# **listarTodos2**
> Array<ProdutoDTO> listarTodos2()


### Example

```typescript
import {
    ProdutosApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ProdutosApi(configuration);

const { status, data } = await apiInstance.listarTodos2();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**Array<ProdutoDTO>**

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

