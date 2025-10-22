# 🛒 GestPro Frontend

Interface do **GestPro**, um sistema completo de gestão para mercados e lojas, desenvolvido com **Next.js 14+** (App Router) e integração com o backend em **Spring Boot 3**.

> 🔗 Repositório do backend: [GestPro Backend](https://github.com/MartnsDev/GestPro/tree/main/backend)

---

## 🚀 Tecnologias Utilizadas

### ⚛️ Frontend
- [Next.js 14+ (App Router)](https://nextjs.org/)
- [TypeScript](https://www.typescriptlang.org/)
- [Tailwind CSS](https://tailwindcss.com/)
- [shadcn/ui](https://ui.shadcn.com/)
- [Lucide Icons](https://lucide.dev/)

### 🖥️ Backend (integração)
- Java 17+
- Spring Boot 3.x
- Spring Security + JWT
- OAuth2 (Login com Google)
- MySQL

---

## 📋 Pré-requisitos

Certifique-se de ter instalado:

- **Node.js 18+**
- **pnpm** (ou npm/yarn)
- Backend em execução (porta padrão `8080`)

---

## ⚙️ Configuração e Execução

### 1️⃣ Clonar o repositório
```bash
git clone https://github.com/MartnsDev/GestPro.git
cd GestPro/frontend
2️⃣ Instalar dependências
bash
Copiar código
pnpm install
3️⃣ Configurar variáveis de ambiente
Copie o arquivo de exemplo:

bash
Copiar código
cp .env.local.example .env.local
Edite o arquivo .env.local e configure a URL da API:

bash
Copiar código
NEXT_PUBLIC_API_URL=http://localhost:8080
4️⃣ Rodar o servidor de desenvolvimento
bash
Copiar código
pnpm dev
Acesse o app em:
👉 http://localhost:3000

📁 Estrutura de Pastas
bash
Copiar código
frontend/
├── app/                     # Páginas (App Router)
│   ├── page.tsx            # Tela de Login
│   ├── cadastro/           # Página de Cadastro
│   ├── dashboard/          # Painel principal
│   └── esqueceu-senha/     # Recuperação de senha
├── components/              # Componentes reutilizáveis
│   ├── auth/               # Formulários de autenticação
│   └── ui/                 # Componentes do shadcn/ui
├── lib/                     # Funções utilitárias
│   ├── api.ts              # Comunicação com o backend
│   └── auth.ts             # Funções de autenticação JWT
├── public/                  # Arquivos estáticos
└── styles/                  # Estilos globais e customizados
🔐 Autenticação
O sistema suporta dois métodos de login:

Email e senha

Login com Google (OAuth2)

A autenticação utiliza JWT tokens armazenados em cookies HTTP-only, garantindo segurança e persistência entre sessões.

🎨 Design System
Cores principais:

Verde: #10b981

Azul escuro: #0a1929

Componentes: shadcn/ui

Totalmente responsivo (desktop, tablet e mobile)

Ícones: Lucide React

📡 Principais Endpoints da API
Autenticação
Método	Endpoint	Descrição
POST	/auth/login	Login com email e senha
POST	/auth/cadastro	Cadastro de novo usuário
GET	/oauth2/authorization/google	Login com Google
POST	/auth/esqueceu-senha	Solicitar redefinição de senha
POST	/auth/redefinir-senha	Redefinir senha
POST	/auth/logout	Logout do usuário

Usuário
Método	Endpoint	Descrição
GET	/api/usuario	Retorna dados do usuário autenticado

🧩 Próximos Passos
 Implementar módulo de Produtos

 Implementar módulo de Estoque

 Implementar módulo de Vendas

 Implementar módulo de Clientes

 Implementar módulo de Relatórios

 Adicionar testes unitários e de integração

 Adicionar notificações em tempo real

 Suporte a múltiplas lojas

📜 Licença
Este projeto está sob a licença MIT.
Sinta-se à vontade para usar, modificar e contribuir.

Feito com 💚 por Matheus Martins (MartnsDev)
