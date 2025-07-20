package views;

import utils.Funcoes;

public class GerenciaView {

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

    public static void menuCadastrarFuncionario() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("CADASTRAR FUNCIONÁRIO");
    }

    public static void menuRemoverFuncionario() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("REMOVER FUNCIONÁRIO");
    }

    public static void menuAtualizarFuncionario() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("ATUALIZAR FUNCIONÁRIO");
    }

    public static void menuListarFuncionarios() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("FUNCIONÁRIOS CADASTRADOS");
    }

    public static void menuBuscarFuncionario() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("BUSCAR FUNCIONÁRIO");
        System.out.println("1. Buscar por Nome");
        System.out.println("2. Buscar por ID");
    }
}
