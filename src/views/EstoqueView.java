package views;

import utils.Funcoes;


public class EstoqueView {

    public static void TelaMenuEstoque() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("MENU PRINCIPAL");
        System.out.println("1. Adicionar Veículo");
        System.out.println("2. Remover Veículo");
        System.out.println("3. Atualizar Veículo");
        System.out.println("4. Listar Veículos");
        System.out.println("5. Buscar Veículo");
        System.out.println("0. Sair");
    }

    public static void TelaMenuAdicionarVeiculo() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("ADICIONAR VEÍCULO");
        System.out.println("1. Adicionar Carro");
        System.out.println("1. Adicionar Moto");
        System.out.println("1. Adicionar Caminhão");
        System.out.println("0. Voltar");
    }

    public static void TelaMenuAtualizarVeiculo() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("ATUALIZAR VEÍCULO");
        System.out.println("1. Atualizar Carro");
        System.out.println("2. Atualizar Moto");
        System.out.println("3. Atualizar Caminhão");
        System.out.println("4. Atualizar Veículo Geral");
        System.out.println("0. Voltar");
    }

    public static void TelaAtualizaDadosVeiculo() {
        System.out.println("1. Atualiza Modelo");
        System.out.println("2. Atualiza Numchassi");
        System.out.println("3. Atualiza Preço");
        System.out.println("4. Atualiza Ano de Frabricação");
        System.out.println("5. Atualiza Quilometragem");
        System.out.println("6. Atualiza Cor");
    }

    public static void TelaMenuAtualizaDadosCarro() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("ATUALIZAR DADOS DO CARRO");
        TelaAtualizaDadosVeiculo();
        System.out.println("7. Atualiza Potencia");
        System.out.println("8. Atualiza Número de Portas");
        System.out.println("9. Atualiza Combustivel");
        System.out.println("0. Voltar");
    }

    public static void TelaMenuAtualizaDadosMoto() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("ATUALIZAR DADOS DA MOTO");
        TelaAtualizaDadosVeiculo();
        System.out.println("7. Atualizar Cilindradas");
        System.out.println("0. Voltar");
    }

    public static void TelaMenuAtualizaDadosCaminhao() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("ATUALIZA DADOS DO CAMINHÃO");
        TelaAtualizaDadosVeiculo();
        System.out.println("7. Atualizar Quantidade de Eixos");
        System.out.println("8. Atualizar Capacidade de Carga");
        System.out.println("9. Atualizar Carroceria");
        System.out.println("0. Voltar");
    }

    public static void TelaMenuRemoverVeiculo() {
        Funcoes.limpaTela();
        Funcoes.cabecalhoMenu("REMOVER VEÍCULOS");
        System.out.println("1. Remover Carros");
        System.out.println("2. Remover Motos");
        System.out.println("3. Remover Caminhão");
        System.out.println("4. Remover Veículo Geral");
    }

    public static void TelaMenuConfirmaRemocao() {
        Funcoes.limpaTela();
        System.out.println("1. Confirmar remoção");
        System.out.println("2. Cancelar");
    }
}
