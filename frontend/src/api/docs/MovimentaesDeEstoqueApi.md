# MovimentaesDeEstoqueApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**listarPorProduto**](#listarporproduto) | **GET** /api/estoque/movimentacoes/produto/{produtoId} | Listar todas as movimentações de um produto específico|
|[**listarTodas3**](#listartodas3) | **GET** /api/estoque/movimentacoes | Listar todas as movimentações de estoque|
|[**registrar**](#registrar) | **POST** /api/estoque/movimentacoes | Registrar uma movimentação de estoque (entrada ou saída)|

# **listarPorProduto**
> Array<MovimentacaoEstoqueDTO> listarPorProduto()


### Example

```typescript
import {
    MovimentaesDeEstoqueApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new MovimentaesDeEstoqueApi(configuration);

let produtoId: number; // (default to undefined)

const { status, data } = await apiInstance.listarPorProduto(
    produtoId
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **produtoId** | [**number**] |  | defaults to undefined|


### Return type

**Array<MovimentacaoEstoqueDTO>**

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

# **listarTodas3**
> Array<MovimentacaoEstoqueDTO> listarTodas3()


### Example

```typescript
import {
    MovimentaesDeEstoqueApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new MovimentaesDeEstoqueApi(configuration);

const { status, data } = await apiInstance.listarTodas3();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**Array<MovimentacaoEstoqueDTO>**

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

# **registrar**
> MovimentacaoEstoqueDTO registrar(movimentacaoEstoqueDTO)


### Example

```typescript
import {
    MovimentaesDeEstoqueApi,
    Configuration,
    MovimentacaoEstoqueDTO
} from './api';

const configuration = new Configuration();
const apiInstance = new MovimentaesDeEstoqueApi(configuration);

let movimentacaoEstoqueDTO: MovimentacaoEstoqueDTO; //

const { status, data } = await apiInstance.registrar(
    movimentacaoEstoqueDTO
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **movimentacaoEstoqueDTO** | **MovimentacaoEstoqueDTO**|  | |


### Return type

**MovimentacaoEstoqueDTO**

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

