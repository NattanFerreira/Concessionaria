package views;

import views.utils.TelaUtils;

public class ADMView {
    public static void TelaMenuADM() {
        TelaUtils.limpaTela();
        TelaUtils.cabecalhoMenu("MENU PRINCIPAL");
        System.out.println("1. Gerenciar Funcionarios");
        System.out.println("2. Gerenciar Veículos");
        System.out.println("3. Relatórios Gerais");
        System.out.println("4. Configuração do Sistema");
        System.out.println("0. Sair");
    }

    public static void TelaMenuGerenciarFuncionarios() {
        TelaUtils.limpaTela();
        TelaUtils.cabecalhoMenu("GERENCIAR FUNCIONÁRIOS");
        System.out.println("1. Cadastrar Funcionário");
        System.out.println("2. Remover Funcionário");
        System.out.println("3. Atualizar Funcionário");
        System.out.println("4. Listar Funcionário");
        System.out.println("5. Buscar Funcionário");
        System.out.println("6. Gerenciar Cargos");
        System.out.println("0. Voltar");
    }

    public static void TelaMenuFiltrarFuncPorCargo() {
        TelaUtils.limpaTela();
        System.out.println("1. Vendedores");
        System.out.println("2. Equipe de Estoque");
        System.out.println("3. Gerentes");
        System.out.println("0. Voltar");
    }

    public static void TelaMenuBuscarFuncionario() {
        TelaUtils.limpaTela();
        TelaUtils.cabecalhoMenu("BUSCAR FUNCIONÁRIO");
        System.out.println("1. Buscar por Nome");
        System.out.println("2. Buscar por ID");
        System.out.println("3. Buscar por Cargo");
        System.out.println("0. Voltar");
    }

    public static void TelaMenuRelatorioVendas() {
        TelaUtils.limpaTela();
        TelaUtils.cabecalhoMenu("Relatórios de Venda");
        System.out.println("1. Vendas por Período");
        System.out.println("2. Vendas por Vendedor");
        System.out.println("3. Veículos mais Vendidos");
        System.out.println("4. Receita Total");
        System.out.println("5. Comissões");
        System.out.println("6. Relatório Personalizado");
        System.out.println("0. Voltar");
    }


}
