package views;

import data.Banco;
import utils.Funcoes;
import controllers.FuncionarioDao;

/**
 * Classe responsável pela interface de login do sistema.
 * Gerencia a autenticação de usuários e redirecionamento para os menus específicos
 * baseado no cargo do funcionário.
 */
public class LoginView {
    
    /**
     * Exibe o menu inicial do sistema com opções de login.
     * Controla o loop principal do menu de entrada.
     * 
     * @param banco Objeto Banco para operações de autenticação
     */
    public static void menuInicial(Banco banco) {
        int opcao;
        do {
            Funcoes.limpaTela();
            Funcoes.cabecalhoMenu("Menu de Login");
            System.out.println("1. Fazer login");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = Funcoes.lerInt();
            switch (opcao) {
                case 1:
                    telaLogin(banco);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    Funcoes.pressEnterToContinue();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    Funcoes.pressEnterToContinue();
            }
        } while (opcao != 0);
    }

    /**
     * Processa o login do usuário.
     * Solicita credenciais, autentica no banco e redireciona para o menu
     * apropriado baseado no cargo do funcionário.
     * 
     * Cargos disponíveis:
     * - 0: Vendedor -> VendedorView
     * - 1: Estoquista -> EstoqueView  
     * - 2: Gerente -> Não implementado
     * - 3: Administrador -> Não implementado
     * 
     * @param banco Objeto Banco para autenticação
     */
    public static void telaLogin(Banco banco) {
        int id_cargo;
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("Tela de Login");
        System.out.println("Digite seu login:");
        String login = Funcoes.lerLogin();
        if (login == null) {
            System.out.println("Login inválido. Tente novamente.");
            Funcoes.pressEnterToContinue();
            return;
        }
        System.out.println("Digite sua senha:");
        String senha = Funcoes.lerSenha();
        if (senha == null) {
            System.out.println("Senha inválida. Tente novamente.");
            Funcoes.pressEnterToContinue();
            return;
        }
        id_cargo = FuncionarioDao.autenticarFuncionario(banco, login, senha);
        if (id_cargo == -1) {
            System.out.println("Login ou senha incorretos. Tente novamente.");
            Funcoes.pressEnterToContinue();
            return;
        } else if (id_cargo == 0) {
            int id_funcionario = FuncionarioDao.getIdFuncionario(banco, login, senha);
            VendedorView.menuVendedor(banco, id_funcionario);
        } else if (id_cargo == 1) {
            EstoqueView.menuEstoque(banco);
        } else if (id_cargo == 2) {
            // Aqui você pode redirecionar para a tela de gerente de funcionário
        } else if (id_cargo == 3) {
            // Aqui você pode redirecionar para a tela de administrador 
        } else {
            System.out.println("Cargo não reconhecido. Acesso negado.");
            Funcoes.pressEnterToContinue();
        }
    }
}