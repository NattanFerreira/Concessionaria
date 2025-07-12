package Models;

public abstract class Veiculo {
    protected int id;
    protected String modelo;
    protected int numChassi;
    protected double quilometragem;
    protected double preco;
    protected String cor;
    protected int anoFabricacao;
    protected String[] status = {"Disponível", "Vendido"};
    protected int idStatus; // 1 "Disponível", 2 "Vendido"

    public Veiculo(String modelo, int numChassi, double quilometragem, double preco, String cor, int anoFabricacao, int idStatus) {
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

    public int getNumChassi() {
        return numChassi;
    }

    public void setNumChassi(int numChassi) {
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

    public String exibirVeiculos() {
        return "Modelo: " + this.modelo +
                "\nChassi: " + this.numChassi +
                "\nPreço: R$" + String.format("%.2f", this.preco) +
                "\nCor: " + this.cor +
                "\nAno: " + this.anoFabricacao +
                "\nStatus: " + this.status;
    }

    public void cadastrarVeiculo() {
        System.out.println("Veículo cadastrado");
    }

    public void removerVeiculo() {
        System.out.println("Veículo removido");
    }

    public void atualizarVeiculo() {
        System.out.println("Veículo atualizado");
    }
}

