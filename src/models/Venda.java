package models;

import java.util.ArrayList;
import java.util.List;
import models.Veiculo;
import java.util.Date;

public class Venda {
    private int id;
    private double valorTotal;
    private String dataVenda;
    private int idFuncionario;
    private int id_status; // 1 - Pendente, 2 - Concluída
    private String[] status = {"Pendente", "Concluída"};
    private List<Veiculo> veiculos = new ArrayList<>();

    public Venda(int id, double valorTotal, String dataVenda, int idFuncionario, int id_status, List<Veiculo> veiculo) {
        this.id = id;
        this.valorTotal = valorTotal;
        this.dataVenda = dataVenda;
        this.idFuncionario = idFuncionario;
        this.id_status = id_status;
        this.veiculos = veiculo;
    }
    
    public Venda() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public int getId_status() {
        return id_status;
    }

    public void setId_status(int id_status) {
        this.id_status = id_status;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Venda ID: ").append(id).append("\n");
        sb.append("Valor Total: R$ ").append(valorTotal).append("\n");
        sb.append("Data da Venda: ").append(dataVenda).append("\n");
        sb.append("Funcionário ID: ").append(idFuncionario).append("\n");
        sb.append("Status: ").append(status[id_status - 1]).append("\n");
        sb.append("Veículos Vendidos:\n");
        for (Veiculo veiculo : veiculos) {
            sb.append(veiculo.toString()).append("\n");
        }
        return sb.toString();
    }
}