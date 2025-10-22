# GestPro - Sistema de Gestão para Mercados

Sistema completo de gestão para mercados e lojas, desenvolvido com Next.js (frontend) e Spring Boot (backend).

## 🚀 Tecnologias

### Frontend
- Next.js 14+ (App Router)
- TypeScript
- Tailwind CSS
- shadcn/ui

### Backend
- Java 17+
- Spring Boot 3.x
- Spring Security
- JWT Authentication
- OAuth2 (Google Login)
- MySQL

## 📋 Pré-requisitos

- Node.js 18+ e pnpm
- Java 17+
- MySQL 8+

## 🔧 Configuração

### Frontend

1. Clone o repositório
2. Instale as dependências:
\`\`\`bash
pnpm install
\`\`\`

3. Configure as variáveis de ambiente:
\`\`\`bash
cp .env.local.example .env.local
\`\`\`

Edite `.env.local` e configure:
\`\`\`
NEXT_PUBLIC_API_URL=http://localhost:8080
\`\`\`

4. Execute o projeto:
\`\`\`bash
pnpm dev
\`\`\`

O frontend estará disponível em `http://localhost:3000`

### Backend

1. Configure o banco de dados MySQL
2. Configure as variáveis de ambiente no `application.properties`:
   - `spring.datasource.url`
   - `spring.datasource.username`
   - `spring.datasource.password`
   - `jwt.secret`
   - Credenciais do Google OAuth2

3. Execute o backend:
\`\`\`bash
./mvnw spring-boot:run
\`\`\`

O backend estará disponível em `http://localhost:8080`

## 📁 Estrutura do Projeto

\`\`\`
gestpro/
├── app/                    # Páginas Next.js
│   ├── page.tsx           # Login
│   ├── cadastro/          # Cadastro
│   ├── dashboard/         # Dashboard
│   └── esqueceu-senha/    # Recuperação de senha
├── components/            # Componentes React
│   └── ui/               # Componentes shadcn/ui
├── lib/                   # Utilitários
│   ├── api.ts            # Funções de API
│   └── auth.ts           # Funções de autenticação
└── public/               # Arquivos estáticos
\`\`\`

## 🔐 Autenticação

O sistema suporta dois métodos de autenticação:

1. **Login tradicional**: Email e senha
2. **Login com Google**: OAuth2

A autenticação é gerenciada via JWT tokens armazenados em cookies HTTP-only.

## 🎨 Design System

O projeto utiliza um design system consistente com:
- Cores principais: Verde (#10b981) e Azul escuro (#0a1929)
- Componentes shadcn/ui
- Responsivo para desktop, tablet e mobile

## 📝 Endpoints da API

### Autenticação
- `POST /auth/login` - Login com email/senha
- `POST /auth/cadastro` - Cadastro de novo usuário
- `GET /oauth2/authorization/google` - Iniciar login com Google
- `POST /auth/esqueceu-senha` - Solicitar código de recuperação
- `POST /auth/redefinir-senha` - Redefinir senha
- `POST /auth/logout` - Logout

### Usuário
- `GET /api/usuario` - Obter dados do usuário autenticado

## 🚧 Próximos Passos

- [ ] Implementar módulo de Produtos
- [ ] Implementar módulo de Estoque
- [ ] Implementar módulo de Vendas
- [ ] Implementar módulo de Clientes
- [ ] Implementar módulo de Relatórios
- [ ] Implementar módulo de Configurações
- [ ] Adicionar testes unitários e de integração
- [ ] Implementar sistema de notificações em tempo real
- [ ] Adicionar suporte a múltiplas lojas

## 📄 Licença

Este projeto está sob a licença MIT.
