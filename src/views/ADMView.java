package views;

import utils.Funcoes;

/**
 * Classe responsável pela interface do administrador do sistema da concessionária.
 * Fornece acesso completo a todas as funcionalidades administrativas e de gestão.
 * 
 * Funcionalidades principais:
 * - Gerenciamento completo de funcionários (CRUD + relatórios)
 * - Gerenciamento de veículos (integração com estoque)
 * - Sistema de relatórios gerenciais avançados
 * - Configurações e administração do sistema
 */
public class ADMView {
    
    /**
     * Exibe o menu principal do administrador.
     * Interface central que fornece acesso a todas as funcionalidades administrativas.
     * 
     * Funcionalidades disponíveis:
     * 1. Gerenciar Funcionários - Acesso completo ao sistema de RH
     * 2. Gerenciar Veículos - Integração com o sistema de estoque
     * 3. Relatórios Gerais - Dashboard executivo e análises
     * 4. Configuração do Sistema - Parâmetros e configurações globais
     * 0. Sair - Logout do sistema
     * 
     * Esta interface serve como hub central para todas as operações
     * administrativas, permitindo navegação intuitiva entre os subsistemas.
     */
    public static void TelaMenuADM() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("MENU PRINCIPAL");
        System.out.println("1. Gerenciar Funcionarios");
        System.out.println("2. Gerenciar Veículos");
        System.out.println("3. Relatórios Gerais");
        System.out.println("4. Configuração do Sistema");
        System.out.println("0. Sair");
    }

    /**
     * Exibe o menu de gerenciamento de funcionários.
     * Interface especializada para operações de recursos humanos.
     * 
     * Funcionalidades disponíveis:
     * 1. Cadastrar Funcionário - Registro de novos colaboradores
     * 2. Remover Funcionário - Exclusão de registros funcionais
     * 3. Atualizar Funcionário - Modificação de dados pessoais/funcionais
     * 4. Listar Funcionário - Visualização completa da equipe
     * 5. Buscar Funcionário - Sistema de busca com múltiplos critérios
     * 6. Gerenciar Cargos - Administração de hierarquia e funções
     * 0. Voltar - Retorna ao menu principal
     * 
     * Este menu centraliza todas as operações relacionadas à gestão
     * de pessoas, permitindo controle completo sobre a equipe.
     */
    public static void TelaMenuGerenciarFuncionarios() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("GERENCIAR FUNCIONÁRIOS");
        System.out.println("1. Cadastrar Funcionário");
        System.out.println("2. Remover Funcionário");
        System.out.println("3. Atualizar Funcionário");
        System.out.println("4. Listar Funcionário");
        System.out.println("5. Buscar Funcionário");
        System.out.println("6. Gerenciar Cargos");
        System.out.println("0. Voltar");
    }

    /**
     * Exibe o menu de filtro de funcionários por cargo.
     * Interface para segmentação da equipe por função organizacional.
     * 
     * Categorias funcionais disponíveis:
     * 1. Vendedores - Equipe responsável pela venda direta
     * 2. Equipe de Estoque - Funcionários de gerenciamento de inventário
     * 3. Gerentes - Liderança e supervisão de setores
     * 0. Voltar - Retorna ao menu anterior
     * 
     * Esta funcionalidade permite visualização segmentada da equipe,
     * facilitando análises por função e gestão hierárquica.
     */
    public static void TelaMenuFiltrarFuncPorCargo() {
        Funcoes.limpaTela();
        System.out.println("1. Vendedores");
        System.out.println("2. Equipe de Estoque");
        System.out.println("3. Gerentes");
        System.out.println("0. Voltar");
    }

    /**
     * Exibe o menu de busca de funcionários.
     * Interface especializada para localização de colaboradores por diferentes critérios.
     * 
     * Critérios de busca disponíveis:
     * 1. Buscar por Nome - Localização por nome completo ou parcial
     * 2. Buscar por ID - Busca direta por identificador único
     * 3. Buscar por Cargo - Filtro por função organizacional
     * 0. Voltar - Retorna ao menu anterior
     * 
     * Sistema otimizado para localização rápida de funcionários,
     * essencial para operações administrativas eficientes.
     */
    public static void TelaMenuBuscarFuncionario() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("BUSCAR FUNCIONÁRIO");
        System.out.println("1. Buscar por Nome");
        System.out.println("2. Buscar por ID");
        System.out.println("3. Buscar por Cargo");
        System.out.println("0. Voltar");
    }

    /**
     * Exibe o menu de relatórios de vendas.
     * Interface para dashboard executivo e análises de performance comercial.
     * 
     * Relatórios disponíveis:
     * 1. Vendas por Período - Análise temporal de vendas (diário/mensal/anual)
     * 2. Vendas por Vendedor - Performance individual da equipe de vendas
     * 3. Veículos mais Vendidos - Ranking de produtos por popularidade
     * 4. Receita Total - Análise financeira consolidada
     * 5. Comissões - Cálculo e relatório de comissões da equipe
     * 6. Relatório Personalizado - Configuração customizada de métricas
     * 0. Voltar - Retorna ao menu anterior
     * 
     * Sistema completo de business intelligence para tomada de decisões
     * estratégicas baseadas em dados de vendas e performance.
     * 
     * Funcionalidades analíticas:
     * - Comparações temporais
     * - Métricas de performance individual
     * - Análise de produtos
     * - Indicadores financeiros
     * - Relatórios configuráveis
     */
    public static void TelaMenuRelatorioVendas() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("Relatórios de Venda");
        System.out.println("1. Vendas por Período");
        System.out.println("2. Vendas por Vendedor");
        System.out.println("3. Veículos mais Vendidos");
        System.out.println("4. Receita Total");
        System.out.println("5. Comissões");
        System.out.println("6. Relatório Personalizado");
        System.out.println("0. Voltar");
    }


}
