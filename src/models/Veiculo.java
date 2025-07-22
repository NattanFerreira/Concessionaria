package models;

/**
 * Classe abstrata que representa um veículo genérico no sistema da concessionária.
 * Serve como classe base para todos os tipos específicos de veículos (Carro, Motocicleta, Caminhão).
 * 
 * Esta classe define os atributos e métodos comuns a todos os veículos,
 * implementando o conceito de herança da POO.
 */
public abstract class Veiculo {
    protected int id;
    protected String modelo;
    protected String numChassi;
    protected double quilometragem;
    protected double preco;
    protected String cor;
    protected int anoFabricacao;
    protected String[] status = {"Disponível", "Vendido", "Reservado"};
    protected int idStatus; // 1 "Disponível", 2 "Vendido" , 3 "Reservado"

    public Veiculo(String modelo, String numChassi, double quilometragem, double preco, String cor, int anoFabricacao, int idStatus) {
        this.modelo = modelo;
        this.numChassi = numChassi;
        this.quilometragem = quilometragem;
        this.preco = preco;
        this.cor = cor;
        this.anoFabricacao = anoFabricacao;
        this.idStatus = idStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNumChassi() {
        return numChassi;
    }

    public void setNumChassi(String numChassi) {
        this.numChassi = numChassi;
    }

    public double getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(double quilometragem) {
        this.quilometragem = quilometragem;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(int anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    @Override
    public String toString() {
        return  "ID: " + this.id +
                "\nModelo: " + this.modelo +
                "\nChassi: " + this.numChassi +
                "\nPreço: R$" + String.format("%.2f", this.preco) +
                "\nCor: " + this.cor +
                "\nAno: " + this.anoFabricacao +
                "\nStatus: " + this.status[this.idStatus - 1];
    }

    /**
     * Método abstrato para exibir uma mensagem de cadastrar veículo.
     */
    public void cadastrarVeiculo() {
        System.out.println("Veículo cadastrado");
    }

    /**
     * Método abstrato para exibir uma mensagem de remover veículo.
     */
    public void removerVeiculo() {
        System.out.println("Veículo removido");
    }

    /**
     * Método abstrato para exibir uma mensagem de atualizar veículo.
     */
    public void atualizarVeiculo() {
        System.out.println("Veículo atualizado");
    }
}

