package models;

import java.util.Date;

/**
 * Representa uma transação de venda realizada na concessionária.
 * O registro não inclui um cliente, focando no funcionário que realizou
 * a venda, o veículo vendido, o valor e a data.
 */
public class Venda {
    private int id;
    private double valorTotal;
    private Date dataVenda;
    private int idVeiculo;
    private int idFuncionario;

    /**
     * Construtor padrão.
     */
    public Venda() {
    }

    /**
     * Cria uma nova instância de Venda, definindo a data atual como a data da transação.
     * @param valorTotal O valor pelo qual o veículo foi vendido.
     * @param idVeiculo O ID do veículo vendido.
     * @param idFuncionario O ID do funcionário que realizou a venda.
     */
    public Venda(double valorTotal, int idVeiculo, int idFuncionario) {
        this.valorTotal = valorTotal;
        this.idVeiculo = idVeiculo;
        this.idFuncionario = idFuncionario;
        this.dataVenda = new Date(); // Define a data da venda como o momento da criação do objeto
    }

    @Override
    public String toString() {
        return "Venda [id=" + id + 
               ", valorTotal=" + valorTotal + 
               ", dataVenda=" + dataVenda + 
               ", idVeiculo=" + idVeiculo + 
               ", idFuncionario=" + idFuncionario + "]";
    }

    // --- Getters e Setters ---

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

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public int getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(int idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }
}