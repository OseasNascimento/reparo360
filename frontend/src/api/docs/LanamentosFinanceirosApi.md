# LanamentosFinanceirosApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**atualizar**](#atualizar) | **PUT** /api/financeiro/lancamentos/{id} | Atualizar um lançamento financeiro existente|
|[**buscarPorId1**](#buscarporid1) | **GET** /api/financeiro/lancamentos/{id} | Buscar lançamento financeiro por ID|
|[**criar**](#criar) | **POST** /api/financeiro/lancamentos | Criar um novo lançamento financeiro|
|[**excluir**](#excluir) | **DELETE** /api/financeiro/lancamentos/{id} | Excluir um lançamento financeiro por ID|
|[**listarPorConta**](#listarporconta) | **GET** /api/financeiro/lancamentos/conta/{contaCaixaId} | Listar lançamentos de uma conta caixa específica|
|[**listarPorTipo**](#listarportipo) | **GET** /api/financeiro/lancamentos/tipo/{tipoTransacao} | Listar lançamentos filtrados por tipo de transação|
|[**listarTodos**](#listartodos) | **GET** /api/financeiro/lancamentos | Listar todos os lançamentos financeiros|

# **atualizar**
> LancamentoFinanceiroDTO atualizar(lancamentoFinanceiroDTO)


### Example

```typescript
import {
    LanamentosFinanceirosApi,
    Configuration,
    LancamentoFinanceiroDTO
} from './api';

const configuration = new Configuration();
const apiInstance = new LanamentosFinanceirosApi(configuration);

let id: number; // (default to undefined)
let lancamentoFinanceiroDTO: LancamentoFinanceiroDTO; //

const { status, data } = await apiInstance.atualizar(
    id,
    lancamentoFinanceiroDTO
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **lancamentoFinanceiroDTO** | **LancamentoFinanceiroDTO**|  | |
| **id** | [**number**] |  | defaults to undefined|


### Return type

**LancamentoFinanceiroDTO**

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

# **buscarPorId1**
> LancamentoFinanceiroDTO buscarPorId1()


### Example

```typescript
import {
    LanamentosFinanceirosApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new LanamentosFinanceirosApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance.buscarPorId1(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**number**] |  | defaults to undefined|


### Return type

**LancamentoFinanceiroDTO**

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

# **criar**
> LancamentoFinanceiroDTO criar(lancamentoFinanceiroDTO)


### Example

```typescript
import {
    LanamentosFinanceirosApi,
    Configuration,
    LancamentoFinanceiroDTO
} from './api';

const configuration = new Configuration();
const apiInstance = new LanamentosFinanceirosApi(configuration);

let lancamentoFinanceiroDTO: LancamentoFinanceiroDTO; //

const { status, data } = await apiInstance.criar(
    lancamentoFinanceiroDTO
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **lancamentoFinanceiroDTO** | **LancamentoFinanceiroDTO**|  | |


### Return type

**LancamentoFinanceiroDTO**

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

# **excluir**
> excluir()


### Example

```typescript
import {
    LanamentosFinanceirosApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new LanamentosFinanceirosApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance.excluir(
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

# **listarPorConta**
> Array<LancamentoFinanceiroDTO> listarPorConta()


### Example

```typescript
import {
    LanamentosFinanceirosApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new LanamentosFinanceirosApi(configuration);

let contaCaixaId: number; // (default to undefined)

const { status, data } = await apiInstance.listarPorConta(
    contaCaixaId
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **contaCaixaId** | [**number**] |  | defaults to undefined|


### Return type

**Array<LancamentoFinanceiroDTO>**

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

# **listarPorTipo**
> Array<LancamentoFinanceiroDTO> listarPorTipo()


### Example

```typescript
import {
    LanamentosFinanceirosApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new LanamentosFinanceirosApi(configuration);

let tipoTransacao: 'RECEITA' | 'DESPESA'; // (default to undefined)

const { status, data } = await apiInstance.listarPorTipo(
    tipoTransacao
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **tipoTransacao** | [**&#39;RECEITA&#39; | &#39;DESPESA&#39;**]**Array<&#39;RECEITA&#39; &#124; &#39;DESPESA&#39;>** |  | defaults to undefined|


### Return type

**Array<LancamentoFinanceiroDTO>**

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

# **listarTodos**
> Array<LancamentoFinanceiroDTO> listarTodos()


### Example

```typescript
import {
    LanamentosFinanceirosApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new LanamentosFinanceirosApi(configuration);

const { status, data } = await apiInstance.listarTodos();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**Array<LancamentoFinanceiroDTO>**

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

