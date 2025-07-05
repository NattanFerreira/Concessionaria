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
    
}