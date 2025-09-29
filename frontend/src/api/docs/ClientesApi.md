# ClientesApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**createOrUpdateByEmail**](#createorupdatebyemail) | **POST** /api/clientes | Criar ou atualizar cliente pelo e-mail|
|[**delete3**](#delete3) | **DELETE** /api/clientes/{id} | Remover cliente por ID|
|[**findAll3**](#findall3) | **GET** /api/clientes | Listar todos os clientes|
|[**findByEmail**](#findbyemail) | **GET** /api/clientes/email/{email} | Buscar cliente por e-mail|
|[**findById3**](#findbyid3) | **GET** /api/clientes/{id} | Buscar cliente por ID|
|[**update3**](#update3) | **PUT** /api/clientes/{id} | Atualizar plenamente um cliente existente|

# **createOrUpdateByEmail**
> ClienteDTO createOrUpdateByEmail(clienteDTO)


### Example

```typescript
import {
    ClientesApi,
    Configuration,
    ClienteDTO
} from './api';

const configuration = new Configuration();
const apiInstance = new ClientesApi(configuration);

let clienteDTO: ClienteDTO; //

const { status, data } = await apiInstance.createOrUpdateByEmail(
    clienteDTO
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **clienteDTO** | **ClienteDTO**|  | |


### Return type

**ClienteDTO**

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

# **delete3**
> delete3()


### Example

```typescript
import {
    ClientesApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ClientesApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance.delete3(
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

# **findAll3**
> Array<ClienteDTO> findAll3()


### Example

```typescript
import {
    ClientesApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ClientesApi(configuration);

const { status, data } = await apiInstance.findAll3();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**Array<ClienteDTO>**

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

# **findByEmail**
> ClienteDTO findByEmail()


### Example

```typescript
import {
    ClientesApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ClientesApi(configuration);

let email: string; // (default to undefined)

const { status, data } = await apiInstance.findByEmail(
    email
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **email** | [**string**] |  | defaults to undefined|


### Return type

**ClienteDTO**

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

# **findById3**
> ClienteDTO findById3()


### Example

```typescript
import {
    ClientesApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ClientesApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance.findById3(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**number**] |  | defaults to undefined|


### Return type

**ClienteDTO**

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

# **update3**
> ClienteDTO update3(clienteDTO)


### Example

```typescript
import {
    ClientesApi,
    Configuration,
    ClienteDTO
} from './api';

const configuration = new Configuration();
const apiInstance = new ClientesApi(configuration);

let id: number; // (default to undefined)
let clienteDTO: ClienteDTO; //

const { status, data } = await apiInstance.update3(
    id,
    clienteDTO
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **clienteDTO** | **ClienteDTO**|  | |
| **id** | [**number**] |  | defaults to undefined|


### Return type

**ClienteDTO**

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

