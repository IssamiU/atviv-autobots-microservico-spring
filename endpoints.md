# Endpoints

## Autenticação

### Login
- **POST** `/api/auth/login`
  - Body:
    ```json
    {
      "nomeUsuario": "admin",
      "senha": "admin123"
    }
    ```
  - **Retorna:** JWT e perfil de administrador

- **POST** `/api/auth/login`
  - Body:
    ```json
    {
      "nomeUsuario": "gerente",
      "senha": "gerente123"
    }
    ```
  - **Retorna:** JWT e perfil de gerente

- **POST** `/api/auth/login`
  - Body:
    ```json
    {
      "nomeUsuario": "vendedor",
      "senha": "vendedor123"
    }
    ```
  - **Retorna:** JWT e perfil de vendedor

- **POST** `/api/auth/login`
  - Body:
    ```json
    {
      "nomeUsuario": "cliente",
      "senha": "cliente123"
    }
    ```
  - **Retorna:** JWT e perfil de cliente

---

## Permissões por Perfil

| Endpoint           | ADMIN | GERENTE | VENDEDOR      | CLIENTE   |
|--------------------|:-----:|:-------:|:-------------:|:---------:|
| /api/auth/login    |  ✅   |   ✅    |      ✅       |    ✅     |
| /usuario/**        |  ✅   |   ✅    |      ✅       |    ❌     |
| /empresa/**        |  ✅   |   ❌    |      ❌       |    ❌     |
| /mercadoria/**     |  ✅   |   ✅    |   ✅ (READ)   |    ❌     |
| /servico/**        |  ✅   |   ✅    |   ✅ (READ)   |    ❌     |
| /venda/**          |  ✅   |   ✅    | ✅ (LIMITED)  |    ✅     |
| /veiculo/**        |  ✅   |   ✅    |      ✅       |    ✅     |

- **✅**: acesso total (CRUD)
- **✅ (READ)**: apenas leitura
- **✅ (LIMITED)**: acesso restrito (ex: vendedor só vê/cria suas vendas)
- **❌**: sem acesso

---

## Usuários

### Listar todos os usuários
- **GET** `/usuario/usuarios`

### Buscar usuário por ID
- **GET** `/usuario/{id}`

### Cadastrar usuário
- **POST** `/usuario/cadastrar`
  - Body: Objeto `Usuario`

### Atualizar usuário
- **PUT** `/usuario/atualizar`
  - Body: Objeto `Usuario` com ID

### Excluir usuário
- **DELETE** `/usuario/excluir`
  - Body: Objeto `Usuario` com ID

---

## Empresas

### Listar todas as empresas
- **GET** `/empresa/empresas`

### Buscar empresa por ID
- **GET** `/empresa/{id}`

### Cadastrar empresa
- **POST** `/empresa/cadastrar`
  - Body: Objeto `Empresa`

### Atualizar empresa
- **PUT** `/empresa/{id}`
  - Body: Objeto `Empresa` com ID

### Excluir empresa
- **DELETE** `/empresa/{id}`

### Associar usuário a empresa
- **POST** `/empresa/{empresaId}/usuario/{usuarioId}`

### Listar usuários de uma empresa
- **GET** `/empresa/{empresaId}/usuarios`

### Remover usuário de uma empresa
- **DELETE** `/empresa/{empresaId}/usuario/{usuarioId}`

---

## Mercadorias

### Listar todas as mercadorias
- **GET** `/mercadoria/mercadorias`

### Buscar mercadoria por ID
- **GET** `/mercadoria/{id}`

### Cadastrar mercadoria
- **POST** `/mercadoria/cadastro`
  - Body: Objeto `Mercadoria`

### Atualizar mercadoria
- **PUT** `/mercadoria/atualizar`
  - Body: Objeto `Mercadoria` com ID

### Excluir mercadoria
- **DELETE** `/mercadoria/excluir`
  - Body: Objeto `Mercadoria` com ID

---

## Serviços

### Listar todos os serviços
- **GET** `/servico/servicos`

### Buscar serviço por ID
- **GET** `/servico/{id}`

### Cadastrar serviço
- **POST** `/servico/cadastro`
  - Body: Objeto `Servico`

### Atualizar serviço
- **PUT** `/servico/atualizar`
  - Body: Objeto `Servico` com ID

### Excluir serviço
- **DELETE** `/servico/excluir`
  - Body: Objeto `Servico` com ID

---

## Veículos

### Listar todos os veículos
- **GET** `/veiculo/veiculos`

### Buscar veículo por ID
- **GET** `/veiculo/{id}`

### Cadastrar veículo
- **POST** `/veiculo/cadastrar`
  - Body: Objeto `Veiculo`

### Atualizar veículo
- **PUT** `/veiculo/atualizar`
  - Body: Objeto `Veiculo` com ID

### Excluir veículo
- **DELETE** `/veiculo/excluir`
  - Body: Objeto `Veiculo` com ID

---

## Vendas

### Listar todas as vendas
- **GET** `/venda/vendas`

### Buscar venda por ID
- **GET** `/venda/{id}`

### Cadastrar venda
- **POST** `/venda/cadastro`
  - Body: Objeto `Venda`

### Atualizar venda
- **PUT** `/venda/atualizar`
  - Body: Objeto `Venda` com ID

### Excluir venda
- **DELETE** `/venda/excluir`
  - Body: Objeto `Venda` com ID

---

## Observações

- Todos os endpoints (exceto `/api/auth/login`) exigem o header `Authorization: Bearer {seu_token_jwt}`.
- Para cadastrar, atualizar ou excluir, envie o objeto completo no corpo da requisição (JSON).
- Consulte os controllers para detalhes de cada entidade e exemplos de JSON.

---