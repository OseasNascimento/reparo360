# TcnicosApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**_delete**](#_delete) | **DELETE** /api/tecnicos/{id} | Excluir um técnico por ID|
|[**create**](#create) | **POST** /api/tecnicos | Criar um novo técnico|
|[**findAll**](#findall) | **GET** /api/tecnicos | Listar todos os técnicos|
|[**findById**](#findbyid) | **GET** /api/tecnicos/{id} | Buscar técnico por ID|
|[**update**](#update) | **PUT** /api/tecnicos/{id} | Atualizar um técnico existente|

# **_delete**
> _delete()


### Example

```typescript
import {
    TcnicosApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new TcnicosApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance._delete(
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

# **create**
> TecnicoDTO create(tecnicoDTO)


### Example

```typescript
import {
    TcnicosApi,
    Configuration,
    TecnicoDTO
} from './api';

const configuration = new Configuration();
const apiInstance = new TcnicosApi(configuration);

let tecnicoDTO: TecnicoDTO; //

const { status, data } = await apiInstance.create(
    tecnicoDTO
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **tecnicoDTO** | **TecnicoDTO**|  | |


### Return type

**TecnicoDTO**

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

# **findAll**
> Array<TecnicoDTO> findAll()


### Example

```typescript
import {
    TcnicosApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new TcnicosApi(configuration);

const { status, data } = await apiInstance.findAll();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**Array<TecnicoDTO>**

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

# **findById**
> TecnicoDTO findById()


### Example

```typescript
import {
    TcnicosApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new TcnicosApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance.findById(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**number**] |  | defaults to undefined|


### Return type

**TecnicoDTO**

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

# **update**
> TecnicoDTO update(tecnicoDTO)


### Example

```typescript
import {
    TcnicosApi,
    Configuration,
    TecnicoDTO
} from './api';

const configuration = new Configuration();
const apiInstance = new TcnicosApi(configuration);

let id: number; // (default to undefined)
let tecnicoDTO: TecnicoDTO; //

const { status, data } = await apiInstance.update(
    id,
    tecnicoDTO
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **tecnicoDTO** | **TecnicoDTO**|  | |
| **id** | [**number**] |  | defaults to undefined|


### Return type

**TecnicoDTO**

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

