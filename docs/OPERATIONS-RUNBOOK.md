# Runbook Operacional

Guia rápido de operação e troubleshooting para o fluxo de autenticação e integração do projeto `microserice-template`, cobrindo:

- `frontend-angular`
- `Traefik API Gateway`
- `bff-service`
- `catalogo-servico`
- `ordem-servico`
- `pagamento-servico`
- Microsoft Entra ID

> Objetivo: permitir diagnóstico em até 5 minutos, sempre no formato:
>
> **1 comando + 1 tela para olhar + decisão seguinte**

---

## Escopo do runbook

Use este documento quando houver problemas como:

- login com Entra ID falhando
- redirect quebrado no Angular
- token JWT não sendo enviado
- `401`, `403`, `404` ou `502` via gateway
- `bff-service` indisponível
- CRUD de catálogo e ordens não funcionando
- problemas de Kafka, PostgreSQL ou Traefik

---

## Pré-requisitos mínimos

Antes de começar, considere que o ambiente ideal inclui:

- Docker e Docker Compose funcionais
- frontend rodando em `http://localhost:4200`
- gateway em `http://localhost`
- variáveis de Entra ID configuradas corretamente

---

# Runbook de 5 minutos

## Passo 1 — Ver se a stack mínima está de pé

### 1 comando

```bash
cd /home/ezava/dsv/projetos/microserice-template && docker compose ps
```

### 1 tela para olhar

Olhe a coluna `STATUS`.

### Decisão seguinte

- Se `traefik`, `bff-service`, `catalogo-servico`, `ordem-servico`, `postgres`, `redis`, `kafka` estiverem **Up** → vá para o **Passo 2**
- Se algum estiver **Exit / Restarting / ausente** → vá para o **Passo 6**

---

## Passo 2 — Ver se o gateway alcança o BFF

### 1 comando

```bash
curl -i http://localhost/api/health
```

### 1 tela para olhar

Olhe o status HTTP e o body.

### Resultado esperado

- `200`
- body parecido com:

```json
{"service":"bff-service","status":"UP"}
```

### Decisão seguinte

- Se der **200** → vá para o **Passo 3**
- Se der **502** ou **404** → vá para o **Passo 7**

---

## Passo 3 — Ver se as rotas protegidas estão corretas no gateway

### 1 comando

```bash
curl -i http://localhost/catalogo/api/produtos
```

### 1 tela para olhar

Olhe o status HTTP.

### Resultado esperado

- `401 Unauthorized`

Isso significa:

- rota existe
- gateway está roteando
- proteção JWT está ativa

### Decisão seguinte

- Se der **401** → execute mentalmente o mesmo para ordem e vá para o **Passo 4**
- Se der **404** → vá para o **Passo 7**
- Se der **500** → vá para o **Passo 6**

> Para `ordem`, você pode repetir depois:
>
> ```bash
> curl -i http://localhost/ordem/api/ordens
> ```

---

## Passo 4 — Subir o frontend local

### 1 comando

```bash
cd /home/ezava/dsv/projetos/microserice-template/frontend-angular && npm start
```

### 1 tela para olhar

Olhe o terminal do Angular até aparecer algo como:

- `Compiled successfully`
- `Local: http://localhost:4200`

### Decisão seguinte

- Se o Angular subir → abra `http://localhost:4200` e vá para o **Passo 5**
- Se falhar build → vá para o **Passo 8**

---

## Passo 5 — Validar login no navegador

### 1 comando

```bash
xdg-open http://localhost:4200
```

### 1 tela para olhar

Olhe o navegador:

- botão **Login com Entra ID**
- fluxo de redirect Microsoft
- retorno para a app
- nome do usuário no topo

### Decisão seguinte

- Se login funcionar e aparecer usuário logado → vá para o **Passo 9**
- Se aparecer erro da Microsoft (`AADSTS...`) → vá para o **Passo 10**
- Se voltar para a app mas continuar deslogado → vá para o **Passo 11**

---

## Passo 6 — Ver qual serviço está quebrando

### 1 comando

```bash
cd /home/ezava/dsv/projetos/microserice-template && docker compose logs --tail=120 bff-service catalogo-servico ordem-servico pagamento-servico kafka postgres | cat
```

### 1 tela para olhar

Olhe o bloco do serviço que falhou.

### Decisão seguinte

- Se vir erro de `KafkaTemplate` → problema de configuração Kafka/Spring
- Se vir erro de `issuer-uri`, `token-uri`, `Unable to resolve Configuration` → problema OAuth2 no Spring
- Se vir erro de datasource / JDBC / `Connection refused` → problema de banco

Depois da identificação:

- OAuth2 Spring → vá para o **Passo 12**
- Banco/Kafka → vá para o **Passo 13**

---

## Passo 7 — Ver se o Traefik está com problema de rota/provider

### 1 comando

```bash
cd /home/ezava/dsv/projetos/microserice-template && docker compose logs --tail=120 traefik | cat
```

### 1 tela para olhar

Olhe por:

- `providerName=docker`
- `middleware ... does not exist`
- `404 page not found`
- `502 Bad Gateway`

### Decisão seguinte

- Se aparecer `providerName=docker` / `client version 1.24 is too old` → recrie o `traefik`
- Se aparecer middleware inexistente → revisar `api-gateway/dynamic/routes.yml`
- Se aparecer `Bad Gateway` para `bff-service` → volte ao **Passo 6**

### Comando de correção mais comum

```bash
cd /home/ezava/dsv/projetos/microserice-template && sudo docker rm -f traefik && sudo docker compose up -d --no-deps --force-recreate traefik
```

Depois volte ao **Passo 2**.

---

## Passo 8 — Ver falha de build do Angular

### 1 comando

```bash
cd /home/ezava/dsv/projetos/microserice-template/frontend-angular && npm install --legacy-peer-deps
```

### 1 tela para olhar

Olhe a saída do `npm`:

- dependência faltando
- erro de TypeScript
- erro de pacote Angular

### Decisão seguinte

- Se instalar com sucesso → rode `npm start` de novo e volte ao **Passo 4**
- Se falhar por dependência → corrigir `package.json` / lockfile

---

## Passo 9 — Validar se o token está indo para o backend

### 1 comando

```bash
echo "Abra o DevTools > Network e clique em uma chamada /catalogo/api/produtos"
```

### 1 tela para olhar

No navegador, DevTools > Network > request de:

- `/catalogo/api/produtos`
- `/ordem/api/ordens`

Olhe o header:

```http
Authorization: Bearer ...
```

### Decisão seguinte

- Se existir `Authorization` e a resposta for **200** → fluxo OK
- Se não existir `Authorization` → vá para o **Passo 11**
- Se existir `Authorization`, mas a resposta for **401/403** → vá para o **Passo 12**

---

## Passo 10 — Resolver erro Microsoft / Entra ID (`AADSTS...`)

### 1 comando

```bash
echo "No portal Entra ID, abra App registrations > sua SPA > Authentication / API permissions / Expose an API"
```

### 1 tela para olhar

Portal do Entra ID.

### Decisão seguinte por erro

- `AADSTS50011` → corrigir redirect URI para `http://localhost:4200`
- `invalid_scope` → corrigir scope em `frontend-angular/src/environments/environment.ts`
- `consent_required` → adicionar permissão da API e dar `Grant admin consent`

Depois volte ao **Passo 5**.

---

## Passo 11 — Login voltou, mas Angular continua deslogado / sem token

### 1 comando

```bash
sed -n '1,220p' /home/ezava/dsv/projetos/microserice-template/frontend-angular/src/app/app.module.ts | cat
```

### 1 tela para olhar

Olhe:

- `MsalRedirectComponent` no `bootstrap`
- `MSALInterceptorConfigFactory`
- `protectedResourceMap`

### Decisão seguinte

- Se `MsalRedirectComponent` não estiver no bootstrap → corrigir
- Se `/catalogo/api/produtos` e `/ordem/api/ordens` não estiverem protegidos no interceptor → corrigir
- Se estiver tudo certo → verificar `environment.ts` e volte ao **Passo 10**

---

## Passo 12 — Token existe, mas backend rejeita (`401/403`)

### 1 comando

```bash
cd /home/ezava/dsv/projetos/microserice-template && docker compose logs --tail=120 bff-service catalogo-servico ordem-servico pagamento-servico | cat
```

### 1 tela para olhar

Olhe nos logs Spring por:

- `issuer`
- `JWT`
- `invalid token`
- `JwtDecoder`
- `Bearer`

### Decisão seguinte

- Se issuer estiver errado → corrigir `issuer-uri` nos `application.yml`
- Se o scope/audience não bater → corrigir App Registration / scope da API
- Se só o BFF falhar → revisar `bff-service/src/main/resources/application.yml`

Depois volte ao **Passo 2**.

---

## Passo 13 — Problema de infraestrutura (Postgres / Kafka)

### 1 comando

```bash
cd /home/ezava/dsv/projetos/microserice-template && docker compose logs --tail=120 postgres kafka | cat
```

### 1 tela para olhar

Olhe:

- `postgres` saudável
- `kafka` saudável
- erros de porta
- erro de imagem/tag
- erro de bootstrap

### Decisão seguinte

- Se `5432` em conflito → confirmar uso de `5433:5432`
- Se Kafka não sobe → validar imagem `confluentinc/cp-kafka:7.6.1`
- Se ambos subirem → voltar ao **Passo 6**

---

## Passo 14 — Validação funcional final do CRUD

### 1 comando

```bash
echo "No navegador: criar 1 produto e 1 ordem; editar; excluir; observar DevTools > Network"
```

### 1 tela para olhar

Tela do navegador + aba Network.

### Resultado esperado

- chamadas para:
  - `/catalogo/api/produtos`
  - `/ordem/api/ordens`
- respostas:
  - `200`
  - `201`
  - `204`

### Decisão seguinte

- Se tudo isso acontecer → fluxo completo OK
- Se falhar em uma operação específica → voltar ao **Passo 9** ou **12**

---

# Fluxo-resumo de decisão em 30 segundos

| Se acontecer | Vá para |
|---|---|
| `docker compose ps` mostra serviço parado | Passo 6 |
| `/api/health` dá `502` | Passo 7 |
| `/catalogo/api/produtos` dá `404` | Passo 7 |
| `/catalogo/api/produtos` dá `401` sem login | OK, vá para Passo 4 |
| Login Microsoft falha | Passo 10 |
| Login volta mas app segue deslogada | Passo 11 |
| Login OK mas API dá `401` | Passo 12 |
| Angular nem sobe | Passo 8 |
| Banco/Kafka falham | Passo 13 |

---

# 5 comandos mais usados do runbook

```bash
cd /home/ezava/dsv/projetos/microserice-template && docker compose ps
```

```bash
curl -i http://localhost/api/health
```

```bash
curl -i http://localhost/catalogo/api/produtos
```

```bash
cd /home/ezava/dsv/projetos/microserice-template && docker compose logs --tail=120 traefik bff-service catalogo-servico ordem-servico pagamento-servico | cat
```

```bash
cd /home/ezava/dsv/projetos/microserice-template/frontend-angular && npm start
```

---

# Anexo rápido — erro → causa provável → correção exata

| Erro / Sintoma | Causa provável | Correção exata |
|---|---|---|
| `AADSTS50011` | redirect URI incompatível | configurar `http://localhost:4200` no Angular e no Entra ID |
| `invalid_scope` | scope inválido | ajustar `api://SEU_API_CLIENT_ID/access_as_user` |
| `consent_required` | permissão da API ausente | adicionar permissão e aplicar consentimento |
| `502 Bad Gateway` | BFF indisponível | revisar logs do `bff-service` |
| `404 page not found` | rota Traefik incorreta | revisar `api-gateway/dynamic/routes.yml` |
| `401` sem login | rota protegida | comportamento esperado |
| `401` com login | token inválido ou issuer incorreto | revisar `issuer-uri`, scope e token |
| erro de `KafkaTemplate` | bean Kafka ausente | revisar configuração Kafka dos serviços |
| erro `providerName=docker` no Traefik | container antigo / provider Docker ativo | recriar `traefik` sem provider Docker |

---

# Observações operacionais

- Em desenvolvimento, o frontend deve falar com o gateway via proxy, e não diretamente com `:8081` ou `:8083`.
- O endpoint `GET /api/health` deve continuar público.
- As rotas `/catalogo/api/*` e `/ordem/api/*` devem responder `401` sem token — isso é sinal de proteção funcionando.
- Se necessário, mantenha este runbook junto ao `README.md` principal como documento de suporte operacional.

