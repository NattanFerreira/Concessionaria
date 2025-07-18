package views;

import views.utils.TelaUtils;

public class GerenciaViews {

    public static void menuGerente() {
        TelaUtils.limpaTela();
        TelaUtils.cabecalhoMenu("MENU PRINCIPAL");
        System.out.println("1. Cadastrar Funcionário");
        System.out.println("2. Remover Funcionário");
        System.out.println("3. Atualizar Funcionário");
        System.out.println("4. Listar Funcionário");
        System.out.println("5. Buscar Funcionário");
        System.out.println("0. Sair");
    }

    public static void menuCadastrarFuncionario() {
        TelaUtils.limpaTela();
        TelaUtils.cabecalhoMenu("CADASTRAR FUNCIONÁRIO");
    }

    public static void menuRemoverFuncionario() {
        TelaUtils.limpaTela();
        TelaUtils.cabecalhoMenu("REMOVER FUNCIONÁRIO");
    }

    public static void menuAtualizarFuncionario() {
        TelaUtils.limpaTela();
        TelaUtils.cabecalhoMenu("ATUALIZAR FUNCIONÁRIO");
    }

    public static void menuListarFuncionarios() {
        TelaUtils.limpaTela();
        TelaUtils.cabecalhoMenu("FUNCIONÁRIOS CADASTRADOS");
    }

    public static void menuBuscarFuncionario() {
        TelaUtils.limpaTela();
        TelaUtils.cabecalhoMenu("BUSCAR FUNCIONÁRIO");
        System.out.println("1. Buscar por Nome");
        System.out.println("2. Buscar por ID");
    }
}
