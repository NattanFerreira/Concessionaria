package Models;

import java.util.Date;

public class Venda {
    private static int proximoId = 1; //Variavel auxiliar para incrementar o id automaticamente ao criar um objeto
    private int id;
    private double valorTotal;
    private Date dataVenda;

    public Venda() {
        this.id = proximoId;
        proximoId++;
    }

    public Venda(double valorTotal) {
        this.valorTotal = valorTotal;
        this.id = proximoId;
        proximoId++;
    }

    @Override
    public String toString() {
        return "Venda [id=" + id + ", valorTotal=" + valorTotal + ", dataVenda=" + dataVenda + "]";
    }

    public int getId() {
        return id;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    
    
}