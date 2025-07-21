# Sistema de Gerenciamento de Concessionária

PROJETO FINAL DA DISCIPLINA DE PROGRAMAÇÃO ORIENTADA A OBJETOS

## 📋 Descrição do Projeto

Sistema completo para gerenciamento de uma concessionária de veículos, desenvolvido em Java utilizando conceitos de Programação Orientada a Objetos. O sistema permite o controle de estoque, vendas, funcionários e relatórios administrativos.

## 🚗 Funcionalidades

### Sistema de Autenticação

- Login com diferentes níveis de acesso (Admin, Gerente, Vendedor)
- Validação de credenciais e controle de sessão

### Gestão de Veículos

- **Carros**: Cadastro, busca, atualização e remoção
- **Motocicletas**: Gerenciamento completo do estoque
- **Caminhões**: Controle de veículos pesados
- Busca por múltiplos critérios (ID, modelo, chassi, preço, cor, ano, etc.)

### Sistema de Vendas

- Carrinho de compras para vendedores
- Adição/remoção de veículos no carrinho
- Finalização de vendas
- Controle de status dos veículos (Disponível, Vendido, Reservado)

### Gestão Administrativa

- Cadastro e gerenciamento de funcionários
- Relatórios de vendas
- Controle de comissões
- Relatórios personalizados

## 🏗️ Arquitetura do Sistema

### Estrutura MVC (Model-View-Controller)

```
src/
├── models/          # Entidades do sistema
│   ├── Veiculo.java     # Classe abstrata base
│   ├── Carro.java       # Modelo específico para carros
│   ├── Motocicleta.java # Modelo específico para motos
│   ├── Caminhao.java    # Modelo específico para caminhões
│   └── Funcionario.java # Modelo para funcionários
├── controllers/     # Camada de acesso a dados (DAO)
│   ├── CarroDao.java
│   ├── MotocicletaDao.java
│   ├── CaminhaoDao.java
│   ├── FuncionarioDao.java
│   └── VendaDao.java
├── views/          # Interface com o usuário
│   ├── LoginView.java
│   ├── EstoqueView.java
│   ├── VendedorView.java
│   ├── GerenciaView.java
│   └── ADMView.java
├── data/           # Gerenciamento do banco de dados
│   └── Banco.java
├── utils/          # Utilitários do sistema
│   └── Funcoes.java
└── resources/      # Recursos do sistema
    └── schema.sql
```

## 💾 Banco de Dados

O sistema utiliza **SQLite** para persistência de dados com as seguintes tabelas:

- **Funcionario**: Dados dos funcionários
- **Carro**: Informações específicas de carros
- **Motocicleta**: Dados de motocicletas
- **Caminhao**: Informações de caminhões
- **Venda**: Controle de vendas
- **VendaVeiculo**: Relacionamento entre vendas e veículos

## 🛠️ Tecnologias Utilizadas

- **Java SE**: Linguagem principal
- **SQLite**: Banco de dados
- **JDBC**: Conectividade com banco de dados
- **POO**: Paradigma de programação

## ⚙️ Configuração e Execução

### Pré-requisitos

- Java JDK 8 ou superior
- SQLite JDBC Driver (incluído em `lib/sqlite-jdbc.jar`)

### Como executar

1. **Clone o repositório**:

```bash
git clone <url-do-repositorio>
cd Concessionaria
```

2. **Compile o projeto**:

```bash
javac -cp "lib/sqlite-jdbc.jar" -d bin src/**/*.java
```

3. **Execute o sistema**:

```bash
java -cp "bin:lib/sqlite-jdbc.jar" Main
```

## 👥 Perfis de Usuário

### Administrador

- Gerenciamento completo do sistema
- Controle de funcionários
- Relatórios administrativos
- Configurações do sistema

### Gerente

- Gestão de funcionários
- Supervisão de vendas
- Relatórios gerenciais

### Vendedor

- Realização de vendas
- Consulta de estoque
- Gerenciamento do carrinho
- Finalização de vendas

## 🔍 Funcionalidades Detalhadas

### Gestão de Estoque

- Adicionar novos veículos
- Atualizar informações existentes
- Remover veículos do estoque
- Listar veículos por categoria
- Busca avançada com múltiplos filtros

### Sistema de Busca

- **Por ID**: Busca específica
- **Por Modelo**: Filtro por modelo do veículo
- **Por Chassi**: Identificação única
- **Por Faixa de Preço**: Busca por valores
- **Por Cor**: Filtro estético
- **Por Ano**: Período de fabricação
- **Por Quilometragem**: Estado de uso
- **Por Características Específicas**: Potência, número de portas, etc.

### Processo de Venda

1. Login do vendedor
2. Busca de veículos disponíveis
3. Adição ao carrinho
4. Revisão dos itens
5. Finalização da venda
6. Atualização do status dos veículos

## 📊 Relatórios Disponíveis

- Vendas por período
- Performance por vendedor
- Veículos mais vendidos
- Receita total
- Cálculo de comissões
- Relatórios personalizados

## 🔒 Segurança

- Sistema de autenticação por login/senha
- Controle de acesso baseado em perfis
- Validação de dados de entrada
- Tratamento de exceções

## 📝 Observações Técnicas

### Padrões Utilizados

- **DAO (Data Access Object)**: Para acesso aos dados
- **MVC (Model-View-Controller)**: Separação de responsabilidades
- **Singleton**: Para conexão com banco de dados

### Validações

- Campos obrigatórios
- Formatos de entrada
- Regras de negócio
- Integridade referencial

## 🤝 Contribuições

Este é um projeto acadêmico desenvolvido como trabalho final da disciplina de Programação Orientada a Objetos.

## 📄 Licença

Projeto acadêmico - todos os direitos reservados.

---

**Desenvolvido com 💻 e ☕ para a disciplina de POO**
