package controllers;

import models.ItemCarrinho;
import models.Carro;
import models.Motocicleta;
import models.Caminhao;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoService {
    private final CarrinhoDao carrinhoDao;
    private final CarroDao carroDao;
    private final MotocicletaDao motocicletaDao;
    private final CaminhaoDao caminhaoDao;

    public CarrinhoService() {
        this.carrinhoDao = new CarrinhoDao();
        this.carroDao = new CarroDao();
        this.motocicletaDao = new MotocicletaDao();
        this.caminhaoDao = new CaminhaoDao();
    }

    public boolean adicionarCarroAoCarrinho(int idCarro, int idVendedor) {
        if (carrinhoDao.veiculoJaNoCarrinho("Carro", idCarro, idVendedor)) {
            System.out.println("Este carro já está no seu carrinho!");
            return false;
        }

        Carro carro = carroDao.buscarCarroPorId(idCarro);
        if (carro == null) {
            System.out.println("Carro não encontrado!");
            return false;
        }

        ItemCarrinho item = new ItemCarrinho("Carro", idCarro, idVendedor);
        carrinhoDao.adicionarItemCarrinho(item);
        System.out.println("Carro adicionado ao carrinho com sucesso!");
        return true;
    }

    public boolean adicionarMotocicletaAoCarrinho(int idMotocicleta, int idVendedor) {
        if (carrinhoDao.veiculoJaNoCarrinho("Motocicleta", idMotocicleta, idVendedor)) {
            System.out.println("Esta motocicleta já está no seu carrinho!");
            return false;
        }

        Motocicleta motocicleta = motocicletaDao.buscarMotocicletaPorId(idMotocicleta);
        if (motocicleta == null) {
            System.out.println("Motocicleta não encontrada!");
            return false;
        }

        ItemCarrinho item = new ItemCarrinho("Motocicleta", idMotocicleta, idVendedor);
        carrinhoDao.adicionarItemCarrinho(item);
        System.out.println("Motocicleta adicionada ao carrinho com sucesso!");
        return true;
    }

    public boolean adicionarCaminhaoAoCarrinho(int idCaminhao, int idVendedor) {
        if (carrinhoDao.veiculoJaNoCarrinho("Caminhao", idCaminhao, idVendedor)) {
            System.out.println("Este caminhão já está no seu carrinho!");
            return false;
        }

        Caminhao caminhao = caminhaoDao.buscarCaminhaoPorId(idCaminhao);
        if (caminhao == null) {
            System.out.println("Caminhão não encontrado!");
            return false;
        }

        ItemCarrinho item = new ItemCarrinho("Caminhao", idCaminhao, idVendedor);
        carrinhoDao.adicionarItemCarrinho(item);
        System.out.println("Caminhão adicionado ao carrinho com sucesso!");
        return true;
    }

    public List<String> carregarCarrinhoComDetalhes(int idVendedor) {
        List<ItemCarrinho> itensCarrinho = carrinhoDao.listarItensCarrinho(idVendedor);
        List<String> detalhesCarrinho = new ArrayList<>();

        for (ItemCarrinho item : itensCarrinho) {
            String detalhes = obterDetalhesVeiculo(item);
            if (detalhes != null) {
                detalhesCarrinho.add(detalhes);
            }
        }

        return detalhesCarrinho;
    }

    private String obterDetalhesVeiculo(ItemCarrinho item) {
        switch (item.getTipoVeiculo()) {
            case "Carro":
                Carro carro = carroDao.buscarCarroPorId(item.getIdVeiculo());
                if (carro != null) {
                    return String.format("CARRINHO ID: %d | CARRO - ID: %d | Modelo: %s | Preço: R$ %.2f | Cor: %s | Ano: %d",
                            item.getId(), carro.getId(), carro.getModelo(), carro.getPreco(), carro.getCor(), carro.getAnoFabricacao());
                }
                break;
            case "Motocicleta":
                Motocicleta moto = motocicletaDao.buscarMotocicletaPorId(item.getIdVeiculo());
                if (moto != null) {
                    return String.format("CARRINHO ID: %d | MOTOCICLETA - ID: %d | Modelo: %s | Preço: R$ %.2f | Cor: %s | Ano: %d | Cilindrada: %d",
                            item.getId(), moto.getId(), moto.getModelo(), moto.getPreco(), moto.getCor(), moto.getAnoFabricacao(), moto.getCilindrada());
                }
                break;
            case "Caminhao":
                Caminhao caminhao = caminhaoDao.buscarCaminhaoPorId(item.getIdVeiculo());
                if (caminhao != null) {
                    return String.format("CARRINHO ID: %d | CAMINHÃO - ID: %d | Modelo: %s | Preço: R$ %.2f | Cor: %s | Ano: %d | Eixos: %d",
                            item.getId(), caminhao.getId(), caminhao.getModelo(), caminhao.getPreco(), caminhao.getCor(), caminhao.getAnoFabricacao(), caminhao.getEixo());
                }
                break;
        }
        return null;
    }

    public boolean removerItemDoCarrinho(int idItemCarrinho, int idVendedor) {
        ItemCarrinho item = carrinhoDao.buscarItemPorId(idItemCarrinho);
        
        if (item == null) {
            System.out.println("Item não encontrado no carrinho!");
            return false;
        }

        if (item.getIdVendedor() != idVendedor) {
            System.out.println("Você não tem permissão para remover este item!");
            return false;
        }

        carrinhoDao.removerItemCarrinho(idItemCarrinho);
        System.out.println("Item removido do carrinho com sucesso!");
        return true;
    }

    public double calcularValorTotalCarrinho(int idVendedor) {
        List<ItemCarrinho> itens = carrinhoDao.listarItensCarrinho(idVendedor);
        double total = 0.0;

        for (ItemCarrinho item : itens) {
            switch (item.getTipoVeiculo()) {
                case "Carro":
                    Carro carro = carroDao.buscarCarroPorId(item.getIdVeiculo());
                    if (carro != null) {
                        total += carro.getPreco();
                    }
                    break;
                case "Motocicleta":
                    Motocicleta moto = motocicletaDao.buscarMotocicletaPorId(item.getIdVeiculo());
                    if (moto != null) {
                        total += moto.getPreco();
                    }
                    break;
                case "Caminhao":
                    Caminhao caminhao = caminhaoDao.buscarCaminhaoPorId(item.getIdVeiculo());
                    if (caminhao != null) {
                        total += caminhao.getPreco();
                    }
                    break;
            }
        }

        return total;
    }

    public int obterQuantidadeItensCarrinho(int idVendedor) {
        return carrinhoDao.contarItensCarrinho(idVendedor);
    }

    public void limparCarrinho(int idVendedor) {
        carrinhoDao.limparCarrinho(idVendedor);
        System.out.println("Carrinho limpo com sucesso!");
    }

    public List<ItemCarrinho> listarItensCarrinho(int idVendedor) {
        return carrinhoDao.listarItensCarrinho(idVendedor);
    }
}
