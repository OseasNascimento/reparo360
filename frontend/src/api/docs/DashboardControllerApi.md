# DashboardControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**clientesPorOrigem**](#clientespororigem) | **GET** /api/dashboard/clientes/por-origem | |
|[**clientesUnicos**](#clientesunicos) | **GET** /api/dashboard/clientes/ativos | |
|[**comissoesPorTecnico**](#comissoesportecnico) | **GET** /api/dashboard/tecnicos/comissoes | |
|[**custoTotalOS**](#custototalos) | **GET** /api/dashboard/os/custo-total | |
|[**despesasTotais**](#despesastotais) | **GET** /api/dashboard/financeiro/despesas | |
|[**getMediaAvaliacoes**](#getmediaavaliacoes) | **GET** /api/dashboard/servicos/media-avaliacoes | |
|[**getMediaAvaliacoesAlias**](#getmediaavaliacoesalias) | **GET** /api/dashboard/media-dia | |
|[**lucroPorMes**](#lucropormes) | **GET** /api/dashboard/financeiro/lucro-por-mes | |
|[**mediaAvaliacoes**](#mediaavaliacoes) | **GET** /api/dashboard/avaliacoes/media | |
|[**osPorServico**](#osporservico) | **GET** /api/dashboard/os/por-servico | |
|[**osPorStatus**](#osporstatus) | **GET** /api/dashboard/os/por-status | |
|[**receitaTotal**](#receitatotal) | **GET** /api/dashboard/financeiro/receita | |
|[**resumo**](#resumo) | **GET** /api/dashboard/resumo | |
|[**servicosCancelados**](#servicoscancelados) | **GET** /api/dashboard/servicos/cancelados | |
|[**servicosConcluidos**](#servicosconcluidos) | **GET** /api/dashboard/servicos/concluidos | |
|[**servicosFinalizados**](#servicosfinalizados) | **GET** /api/dashboard/servicos/finalizados | |
|[**servicosMaisVendidos**](#servicosmaisvendidos) | **GET** /api/dashboard/servicos/mais-vendidos | |
|[**totalServicos**](#totalservicos) | **GET** /api/dashboard/servicos/total | |
|[**valorTotalOS**](#valortotalos) | **GET** /api/dashboard/os/valor-total | |
|[**vendasPorTecnico**](#vendasportecnico) | **GET** /api/dashboard/tecnicos/vendas | |

# **clientesPorOrigem**
> { [key: string]: number; } clientesPorOrigem()


### Example

```typescript
import {
    DashboardControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new DashboardControllerApi(configuration);

const { status, data } = await apiInstance.clientesPorOrigem();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**{ [key: string]: number; }**

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

# **clientesUnicos**
> number clientesUnicos()


### Example

```typescript
import {
    DashboardControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new DashboardControllerApi(configuration);

const { status, data } = await apiInstance.clientesUnicos();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**number**

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

# **comissoesPorTecnico**
> { [key: string]: number; } comissoesPorTecnico()


### Example

```typescript
import {
    DashboardControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new DashboardControllerApi(configuration);

const { status, data } = await apiInstance.comissoesPorTecnico();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**{ [key: string]: number; }**

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

# **custoTotalOS**
> number custoTotalOS()


### Example

```typescript
import {
    DashboardControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new DashboardControllerApi(configuration);

const { status, data } = await apiInstance.custoTotalOS();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**number**

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

# **despesasTotais**
> number despesasTotais()


### Example

```typescript
import {
    DashboardControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new DashboardControllerApi(configuration);

const { status, data } = await apiInstance.despesasTotais();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**number**

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

# **getMediaAvaliacoes**
> MediaAvaliacoesDTO getMediaAvaliacoes()


### Example

```typescript
import {
    DashboardControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new DashboardControllerApi(configuration);

const { status, data } = await apiInstance.getMediaAvaliacoes();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**MediaAvaliacoesDTO**

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

# **getMediaAvaliacoesAlias**
> MediaAvaliacoesDTO getMediaAvaliacoesAlias()


### Example

```typescript
import {
    DashboardControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new DashboardControllerApi(configuration);

const { status, data } = await apiInstance.getMediaAvaliacoesAlias();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**MediaAvaliacoesDTO**

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

# **lucroPorMes**
> { [key: string]: number; } lucroPorMes()


### Example

```typescript
import {
    DashboardControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new DashboardControllerApi(configuration);

const { status, data } = await apiInstance.lucroPorMes();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**{ [key: string]: number; }**

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

# **mediaAvaliacoes**
> number mediaAvaliacoes()


### Example

```typescript
import {
    DashboardControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new DashboardControllerApi(configuration);

const { status, data } = await apiInstance.mediaAvaliacoes();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**number**

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

# **osPorServico**
> { [key: string]: number; } osPorServico()


### Example

```typescript
import {
    DashboardControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new DashboardControllerApi(configuration);

const { status, data } = await apiInstance.osPorServico();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**{ [key: string]: number; }**

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

# **osPorStatus**
> { [key: string]: number; } osPorStatus()


### Example

```typescript
import {
    DashboardControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new DashboardControllerApi(configuration);

const { status, data } = await apiInstance.osPorStatus();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**{ [key: string]: number; }**

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

# **receitaTotal**
> number receitaTotal()


### Example

```typescript
import {
    DashboardControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new DashboardControllerApi(configuration);

const { status, data } = await apiInstance.receitaTotal();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**number**

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

# **resumo**
> ResumoDashboardDTO resumo()


### Example

```typescript
import {
    DashboardControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new DashboardControllerApi(configuration);

const { status, data } = await apiInstance.resumo();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**ResumoDashboardDTO**

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

# **servicosCancelados**
> number servicosCancelados()


### Example

```typescript
import {
    DashboardControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new DashboardControllerApi(configuration);

const { status, data } = await apiInstance.servicosCancelados();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**number**

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

# **servicosConcluidos**
> number servicosConcluidos()


### Example

```typescript
import {
    DashboardControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new DashboardControllerApi(configuration);

const { status, data } = await apiInstance.servicosConcluidos();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**number**

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

# **servicosFinalizados**
> number servicosFinalizados()


### Example

```typescript
import {
    DashboardControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new DashboardControllerApi(configuration);

const { status, data } = await apiInstance.servicosFinalizados();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**number**

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

# **servicosMaisVendidos**
> { [key: string]: number; } servicosMaisVendidos()


### Example

```typescript
import {
    DashboardControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new DashboardControllerApi(configuration);

const { status, data } = await apiInstance.servicosMaisVendidos();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**{ [key: string]: number; }**

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

# **totalServicos**
> number totalServicos()


### Example

```typescript
import {
    DashboardControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new DashboardControllerApi(configuration);

const { status, data } = await apiInstance.totalServicos();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**number**

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

# **valorTotalOS**
> number valorTotalOS()


### Example

```typescript
import {
    DashboardControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new DashboardControllerApi(configuration);

const { status, data } = await apiInstance.valorTotalOS();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**number**

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

# **vendasPorTecnico**
> { [key: string]: number; } vendasPorTecnico()


### Example

```typescript
import {
    DashboardControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new DashboardControllerApi(configuration);

const { status, data } = await apiInstance.vendasPorTecnico();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**{ [key: string]: number; }**

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

