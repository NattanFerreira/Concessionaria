package views;

import utils.Funcoes;


public class VendedorView {
    public static void menuVendedor() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("MENU PRINCIPAL");
        System.out.println("1. Realizar Venda");
        System.out.println("2. Listar Carros a Venda");
        System.out.println("3. Listar Vendas");
        System.out.println("0. Sair");
    }

    public static void menuBusca() {
        Funcoes.limpaTela();
        System.out.println("1. Buscar Carro");
        System.out.println("2. Buscar Moto");
        System.out.println("3. Buscar Caminhão");
        System.out.println("4. Buscar Veículo");
        System.out.println("0. Voltar");
    }

    public static void buscarVeiculo() {
        System.out.println("1. Buscar Modelo");
        System.out.println("2. Buscar Numchassi");
        System.out.println("3. Buscar Preço");
        System.out.println("4. Buscar Ano de Frabricação");
        System.out.println("5. Buscar Quilometragem");
        System.out.println("6. Buscar Cor");
    }

    public static void menuBuscarCarro() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("BUSCAR CARRO");
        buscarVeiculo();
        System.out.println("7. Buscar Potencia");
        System.out.println("8. Buscar Número de Portas");
        System.out.println("9. Buscar Combustivel");
        System.out.println("0. Voltar");
    }

    public static void menuBuscarMoto() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("BUSCAR MOTO");
        buscarVeiculo();
        System.out.println("7. Buscar Cilindradas");
        System.out.println("0. Voltar");
    }

    public static void menuBuscarCaminhao() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("BUSCAR CAMINHÃO");
        buscarVeiculo();
        System.out.println("7. Buscar Quantidade de Eixos");
        System.out.println("8. Buscar Capacidade de Carga");
        System.out.println("9. Buscar Carroceria");
        System.out.println("0. Voltar");
    }

    public static void menuBuscarVeiculoGeral() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("BUSCAR VEÍCULO");
        buscarVeiculo();
        System.out.println("0. Voltar");
    }

    public static void menuListarVeiculos() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("VEÍCULOS CADASTRADOS");
        System.out.println("1. Informe o Número da lista");
        System.out.println("2. Realizar Outra Busca");
        System.out.println("0. Voltar");
    }

    public static void menuCompra() {
        Funcoes.limpaTela();
        System.out.println("1. Continuar Comprando");
        System.out.println("2. Finalizar Compra");
    }

    public static void menuFinalizaCompra() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("FINALIZAR COMPRA");
        System.out.println("1. Confirmar");
        System.out.println("2. Cancelar");
        System.out.println("3. Voltar");
    }
}
