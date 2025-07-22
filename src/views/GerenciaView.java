package views;

import utils.Funcoes;

/**
 * Classe responsável pela interface do gerente no sistema da concessionária.
 * Fornece menus e funcionalidades específicas para usuários com perfil de gerente.
 * 
 * Esta classe implementa as interfaces de gerenciamento de funcionários,
 * permitindo operações CRUD (Create, Read, Update, Delete) sobre a equipe.
 * 
 * Funcionalidades disponíveis:
 * - Cadastro de novos funcionários
 * - Remoção de funcionários
 * - Atualização de dados funcionais
 * - Listagem de funcionários
 * - Busca por critérios específicos
 */
public class GerenciaView {

    /**
     * Exibe o menu principal do gerente.
     * Apresenta todas as opções disponíveis para gerenciamento de funcionários.
     * 
     * Opções disponíveis:
     * - Cadastrar funcionário
     * - Remover funcionário
     * - Atualizar dados de funcionário
     * - Listar todos os funcionários
     * - Buscar funcionário por critérios
     * - Sair do sistema
     */
    public static void menuGerente() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("MENU PRINCIPAL");
        System.out.println("1. Cadastrar Funcionário");
        System.out.println("2. Remover Funcionário");
        System.out.println("3. Atualizar Funcionário");
        System.out.println("4. Listar Funcionário");
        System.out.println("5. Buscar Funcionário");
        System.out.println("0. Sair");
    }

    /**
     * Exibe a interface para cadastro de novos funcionários.
     * Apresenta formulário para entrada de dados pessoais e funcionais.
     * 
     * Dados típicos solicitados:
     * - Nome completo
     * - Usuário de login
     * - Senha de acesso
     * - Cargo/função
     */
    public static void menuCadastrarFuncionario() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("CADASTRAR FUNCIONÁRIO");
    }

    /**
     * Exibe a interface para remoção de funcionários.
     * Permite exclusão de registros funcionais do sistema.
     * 
     * Funcionalidades típicas:
     * - Busca do funcionário a ser removido
     * - Confirmação de exclusão
     * - Validação de permissões
     */
    public static void menuRemoverFuncionario() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("REMOVER FUNCIONÁRIO");
    }

    /**
     * Exibe a interface para atualização de dados funcionais.
     * Permite modificação de informações de funcionários existentes.
     * 
     * Campos típicos editáveis:
     * - Dados pessoais
     * - Informações de acesso
     * - Cargo/função
     * - Status do funcionário
     */
    public static void menuAtualizarFuncionario() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("ATUALIZAR FUNCIONÁRIO");
    }

    /**
     * Exibe a interface para listagem de funcionários.
     * Apresenta todos os funcionários cadastrados no sistema.
     * 
     * Informações típicas exibidas:
     * - ID do funcionário
     * - Nome completo
     * - Cargo/função
     * - Status ativo/inativo
     * - Data de cadastro
     */
    public static void menuListarFuncionarios() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("FUNCIONÁRIOS CADASTRADOS");
    }

    /**
     * Exibe a interface para busca de funcionários.
     * Permite localizar funcionários por diferentes critérios.
     * 
     * Critérios de busca disponíveis:
     * - Busca por nome (parcial ou completo)
     * - Busca por ID (identificação única)
     * - Busca por cargo (pode ser expandido)
     */
    public static void menuBuscarFuncionario() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("BUSCAR FUNCIONÁRIO");
        System.out.println("1. Buscar por Nome");
        System.out.println("2. Buscar por ID");
    }
}
