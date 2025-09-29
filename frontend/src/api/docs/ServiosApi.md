# ServiosApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**create1**](#create1) | **POST** /api/servicos | Criar um novo serviço|
|[**delete1**](#delete1) | **DELETE** /api/servicos/{id} | Excluir um serviço por ID|
|[**findAll1**](#findall1) | **GET** /api/servicos | Listar todos os serviços|
|[**findById1**](#findbyid1) | **GET** /api/servicos/{id} | Buscar serviço por ID|
|[**update1**](#update1) | **PUT** /api/servicos/{id} | Atualizar um serviço existente|

# **create1**
> ServicoDTO create1(servicoDTO)


### Example

```typescript
import {
    ServiosApi,
    Configuration,
    ServicoDTO
} from './api';

const configuration = new Configuration();
const apiInstance = new ServiosApi(configuration);

let servicoDTO: ServicoDTO; //

const { status, data } = await apiInstance.create1(
    servicoDTO
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **servicoDTO** | **ServicoDTO**|  | |


### Return type

**ServicoDTO**

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

# **delete1**
> delete1()


### Example

```typescript
import {
    ServiosApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ServiosApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance.delete1(
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

# **findAll1**
> Array<ServicoDTO> findAll1()


### Example

```typescript
import {
    ServiosApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ServiosApi(configuration);

const { status, data } = await apiInstance.findAll1();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**Array<ServicoDTO>**

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

# **findById1**
> ServicoDTO findById1()


### Example

```typescript
import {
    ServiosApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ServiosApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance.findById1(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**number**] |  | defaults to undefined|


### Return type

**ServicoDTO**

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

# **update1**
> ServicoDTO update1(servicoDTO)


### Example

```typescript
import {
    ServiosApi,
    Configuration,
    ServicoDTO
} from './api';

const configuration = new Configuration();
const apiInstance = new ServiosApi(configuration);

let id: number; // (default to undefined)
let servicoDTO: ServicoDTO; //

const { status, data } = await apiInstance.update1(
    id,
    servicoDTO
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **servicoDTO** | **ServicoDTO**|  | |
| **id** | [**number**] |  | defaults to undefined|


### Return type

**ServicoDTO**

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

