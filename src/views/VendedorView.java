package views;

import utils.Funcoes;
import data.Banco;


public class VendedorView {
    public static void menuVendedor(Banco banco) {
        int opcao;
        do {
            telaMenuVendedor();
            opcao = Funcoes.lerInt();
            switch (opcao) {
                case 1:
                    
                    break;
                case 2:
                    
                    break;
                case 3:
                    
                    break;
                case 0:
                    System.out.println("Saindo do menu vendedor...");
                    Funcoes.pressEnterToContinue();
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    Funcoes.pressEnterToContinue();
            }
        } while (opcao != 0);
    }

    public static void telaMenuVendedor() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("MENU PRINCIPAL");
        System.out.println("1. Realizar Venda");
        System.out.println("2. Listar Carros a Venda");
        System.out.println("3. Listar Vendas");
        System.out.println("0. Sair");
    }

    public static void telaMenuBusca() {
        Funcoes.limpaTela();
        System.out.println("1. Buscar Carro");
        System.out.println("2. Buscar Moto");
        System.out.println("3. Buscar Caminhão");
        System.out.println("0. Voltar");
    }

    public static void telaBuscarVeiculo() {
        System.out.println("1. Buscar Modelo");
        System.out.println("2. Buscar Numchassi");
        System.out.println("3. Buscar Preço");
        System.out.println("4. Buscar Ano de Frabricação");
        System.out.println("5. Buscar Quilometragem");
        System.out.println("6. Buscar Cor");
    }

    public static void telaMenuBuscarCarro() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("BUSCAR CARRO");
        telaBuscarVeiculo();
        System.out.println("7. Buscar Potencia");
        System.out.println("8. Buscar Número de Portas");
        System.out.println("9. Buscar Combustivel");
        System.out.println("0. Voltar");
    }

    public static void telaMenuBuscarMoto() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("BUSCAR MOTO");
        telaBuscarVeiculo();
        System.out.println("7. Buscar Cilindradas");
        System.out.println("0. Voltar");
    }

    public static void telaMenuBuscarCaminhao() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("BUSCAR CAMINHÃO");
        telaBuscarVeiculo();
        System.out.println("7. Buscar Quantidade de Eixos");
        System.out.println("8. Buscar Capacidade de Carga");
        System.out.println("9. Buscar Carroceria");
        System.out.println("0. Voltar");
    }

    public static void telaMenuListarVeiculos() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("VEÍCULOS CADASTRADOS");
        System.out.println("1. Informe o Número da lista");
        System.out.println("2. Realizar Outra Busca");
        System.out.println("0. Voltar");
    }

    public static void telaMenuCompra() {
        Funcoes.limpaTela();
        System.out.println("1. Continuar Comprando");
        System.out.println("2. Finalizar Compra");
    }
}
