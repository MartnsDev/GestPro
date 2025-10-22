# 📁 Estrutura do Projeto GestPro

## Organização de Arquivos

\`\`\`
gestpro/
├── app/                          # Páginas Next.js (App Router)
│   ├── page.tsx                  # Página de Login
│   ├── cadastro/
│   │   └── page.tsx              # Página de Cadastro
│   ├── esqueceu-senha/
│   │   └── page.tsx              # Página de Recuperação de Senha
│   ├── dashboard/
│   │   └── page.tsx              # Dashboard Principal
│   ├── styles/                   # CSS Modules organizados
│   │   ├── auth.module.css       # Estilos de autenticação
│   │   └── dashboard.module.css  # Estilos do dashboard
│   ├── layout.tsx                # Layout raiz
│   └── globals.css               # Estilos globais e tokens
│
├── components/                   # Componentes reutilizáveis
│   ├── auth/                     # Componentes de autenticação
│   │   ├── AuthLayout.tsx        # Layout compartilhado
│   │   ├── FormInput.tsx         # Input reutilizável
│   │   └── PhotoUpload.tsx       # Upload de foto
│   └── ui/                       # Componentes shadcn/ui
│
├── lib/                          # Utilitários e lógica
│   ├── api.ts                    # Funções de API
│   └── auth.ts                   # Gerenciamento de autenticação
│
├── public/                       # Arquivos estáticos
│   ├── favicon.png               # Favicon do site
│   ├── logo-gestpro.png          # Logo da marca
│   └── img-login-new.png         # Ilustração de login
│
└── docs/                         # Documentação
    └── ESTRUTURA.md              # Este arquivo
\`\`\`

## 🎨 Organização de Estilos

### CSS Modules

Todos os estilos estão organizados em CSS Modules para melhor manutenção:

- **`auth.module.css`**: Estilos compartilhados entre Login, Cadastro e Recuperação de Senha
- **`dashboard.module.css`**: Estilos específicos do Dashboard

### Vantagens dos CSS Modules

1. **Escopo Local**: Classes não conflitam entre componentes
2. **Fácil Manutenção**: Estilos organizados por funcionalidade
3. **Identificação Clara**: Nomes de classes descritivos e semânticos
4. **Performance**: CSS otimizado e tree-shaking automático

### Convenções de Nomenclatura

\`\`\`css
/* Containers principais */
.authContainer
.dashboardContainer

/* Seções */
.authHeader
.authContent
.authFooter

/* Componentes */
.inputGroup
.inputField
.btnPrimary
.btnSecondary

/* Estados */
.sidebarNavItemActive
.inputField:disabled
\`\`\`

## 🧩 Componentes Reutilizáveis

### AuthLayout
Layout compartilhado para todas as páginas de autenticação.

**Props:**
- `children`: Conteúdo do formulário
- `title`: Título do cabeçalho
- `subtitle`: Texto do rodapé
- `showIllustration`: Mostrar/ocultar ilustração lateral

### FormInput
Campo de input padronizado com ícone.

**Props:**
- `type`: Tipo do input (text, email, password)
- `placeholder`: Texto placeholder
- `value`: Valor controlado
- `onChange`: Função de mudança
- `icon`: Ícone Lucide
- `required`: Campo obrigatório
- `disabled`: Campo desabilitado

### PhotoUpload
Upload de foto com preview.

**Props:**
- `preview`: URL da imagem preview
- `onChange`: Função de mudança
- `disabled`: Upload desabilitado

## 📦 Estrutura de API

### lib/api.ts
Centraliza todas as chamadas HTTP ao backend.

**Funções principais:**
- `login(email, password)`: Login com credenciais
- `loginComGoogle()`: Redireciona para OAuth Google
- `cadastrar(nome, email, senha, foto)`: Cadastro de usuário
- `getUsuario()`: Busca dados do usuário autenticado
- `logout()`: Faz logout do usuário

### lib/auth.ts
Gerencia tokens JWT e autenticação.

**Funções principais:**
- `saveToken(token)`: Salva token no localStorage
- `getToken()`: Recupera token salvo
- `removeToken()`: Remove token (logout)

## 🔄 Fluxo de Autenticação

\`\`\`
1. Login/Cadastro
   ↓
2. Backend retorna JWT em cookie
   ↓
3. Frontend salva token como fallback
   ↓
4. Requisições incluem cookie automaticamente
   ↓
5. Dashboard carrega dados do usuário
\`\`\`

## 🎯 Próximos Passos

Para expandir o sistema:

1. **Adicionar novas páginas**: Criar em `app/[nome]/page.tsx`
2. **Criar novos estilos**: Adicionar em `app/styles/[nome].module.css`
3. **Componentes reutilizáveis**: Adicionar em `components/[categoria]/`
4. **Novas APIs**: Adicionar funções em `lib/api.ts`

## 📝 Boas Práticas

1. **Sempre use CSS Modules** para estilos de componentes
2. **Componentes pequenos e reutilizáveis** em vez de páginas grandes
3. **Centralize lógica de API** em `lib/api.ts`
4. **Tipos TypeScript** para todas as funções e componentes
5. **Comentários descritivos** em componentes complexos
