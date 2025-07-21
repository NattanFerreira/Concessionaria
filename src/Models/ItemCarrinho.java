package models;

import java.time.LocalDateTime;

public class ItemCarrinho {
    private int id;
    private String tipoVeiculo; 
    private int idVeiculo;
    private int idVendedor;
    private LocalDateTime dataAdicao;

    public ItemCarrinho() {
        this.dataAdicao = LocalDateTime.now();
    }

    public ItemCarrinho(String tipoVeiculo, int idVeiculo, int idVendedor) {
        this.tipoVeiculo = tipoVeiculo;
        this.idVeiculo = idVeiculo;
        this.idVendedor = idVendedor;
        this.dataAdicao = LocalDateTime.now();
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoVeiculo() {
        return tipoVeiculo;
    }

    public void setTipoVeiculo(String tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }

    public int getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(int idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public LocalDateTime getDataAdicao() {
        return dataAdicao;
    }

    public void setDataAdicao(LocalDateTime dataAdicao) {
        this.dataAdicao = dataAdicao;
    }

    @Override
    public String toString() {
        return "Item no carrinho\nID: " + id + "\nTipo do veículo: " + tipoVeiculo + "\nID do veículo: " + idVeiculo + 
               "\nID do vendedor: " + idVendedor + "\nData de adição: " + dataAdicao;
    }
}
