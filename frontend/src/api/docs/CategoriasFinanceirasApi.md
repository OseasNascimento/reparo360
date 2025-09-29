# CategoriasFinanceirasApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**atualizar2**](#atualizar2) | **PUT** /api/financeiro/categorias/{id} | Atualizar uma categoria financeira existente|
|[**buscarPorId3**](#buscarporid3) | **GET** /api/financeiro/categorias/{id} | Buscar categoria financeira por ID|
|[**criar2**](#criar2) | **POST** /api/financeiro/categorias | Criar uma nova categoria financeira|
|[**excluir2**](#excluir2) | **DELETE** /api/financeiro/categorias/{id} | Excluir categoria financeira por ID|
|[**listarTodas1**](#listartodas1) | **GET** /api/financeiro/categorias | Listar todas as categorias financeiras|

# **atualizar2**
> CategoriaFinanceiraDTO atualizar2(categoriaFinanceiraDTO)


### Example

```typescript
import {
    CategoriasFinanceirasApi,
    Configuration,
    CategoriaFinanceiraDTO
} from './api';

const configuration = new Configuration();
const apiInstance = new CategoriasFinanceirasApi(configuration);

let id: number; // (default to undefined)
let categoriaFinanceiraDTO: CategoriaFinanceiraDTO; //

const { status, data } = await apiInstance.atualizar2(
    id,
    categoriaFinanceiraDTO
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **categoriaFinanceiraDTO** | **CategoriaFinanceiraDTO**|  | |
| **id** | [**number**] |  | defaults to undefined|


### Return type

**CategoriaFinanceiraDTO**

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

# **buscarPorId3**
> CategoriaFinanceiraDTO buscarPorId3()


### Example

```typescript
import {
    CategoriasFinanceirasApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new CategoriasFinanceirasApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance.buscarPorId3(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**number**] |  | defaults to undefined|


### Return type

**CategoriaFinanceiraDTO**

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

# **criar2**
> CategoriaFinanceiraDTO criar2(categoriaFinanceiraDTO)


### Example

```typescript
import {
    CategoriasFinanceirasApi,
    Configuration,
    CategoriaFinanceiraDTO
} from './api';

const configuration = new Configuration();
const apiInstance = new CategoriasFinanceirasApi(configuration);

let categoriaFinanceiraDTO: CategoriaFinanceiraDTO; //

const { status, data } = await apiInstance.criar2(
    categoriaFinanceiraDTO
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **categoriaFinanceiraDTO** | **CategoriaFinanceiraDTO**|  | |


### Return type

**CategoriaFinanceiraDTO**

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

# **excluir2**
> excluir2()


### Example

```typescript
import {
    CategoriasFinanceirasApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new CategoriasFinanceirasApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance.excluir2(
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

# **listarTodas1**
> Array<CategoriaFinanceiraDTO> listarTodas1()


### Example

```typescript
import {
    CategoriasFinanceirasApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new CategoriasFinanceirasApi(configuration);

const { status, data } = await apiInstance.listarTodas1();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**Array<CategoriaFinanceiraDTO>**

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

