package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa uma venda no sistema da concessionária.
 * Controla o processo de vendas, desde a criação do carrinho até a finalização.
 */
public class Venda {
    private int id;
    private double valorTotal;
    private int idFuncionario;
    
    /** Status da venda: 1-Aberta, 2-Concluída */
    private int id_status;

    private String[] status = {"Aberta", "Concluída"};
    private List<Veiculo> veiculos = new ArrayList<>();

    /**
     * Construtor da classe Venda.
     * 
     * @param valorTotal Valor total da venda
     * @param idFuncionario ID do funcionário responsável
     * @param id_status Status da venda (1-Aberta, 2-Concluída)
     * @param veiculo Lista de veículos da venda
     */
    public Venda(double valorTotal, int idFuncionario, int id_status, List<Veiculo> veiculo) {
        this.valorTotal = valorTotal;
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
        sb.append("Funcionário ID: ").append(idFuncionario).append("\n");
        sb.append("Status: ").append(status[id_status - 1]).append("\n");
        sb.append("Veículos Vendidos:\n");
        for (Veiculo veiculo : veiculos) {
            sb.append(veiculo.toString()).append("\n");
        }
        return sb.toString();
    }
}