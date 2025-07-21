package models;

public class Carro extends Veiculo {
    private double cavaloPotencia;
    private int numeroPortas;
    private int idTipoCombustivel;
    private String[] tipoCombustivel = {"Gasolina", "Etanol", "Diesel", "Elétrico"};

    public Carro(String modelo, String numChassi, double quilometragem, double preco, String cor, 
                int anoFabricacao, int idStatus, double cavaloPotencia, int numeroPortas, int idTipoCombustivel) {
        super(modelo, numChassi, quilometragem, preco, cor, anoFabricacao, idStatus);
        this.cavaloPotencia = cavaloPotencia;
        this.numeroPortas = numeroPortas;
        this.idTipoCombustivel = idTipoCombustivel;
    }

    public double getCavaloPotencia() {
        return cavaloPotencia;
    }

    public void setCavaloPotencia(double cavaloPotencia) {
        this.cavaloPotencia = cavaloPotencia;
    }

    public int getNumeroPortas() {
        return numeroPortas;
    }

    public void setNumeroPortas(int numeroPortas) {
        this.numeroPortas = numeroPortas;
    }

    public int getIdTipoCombustivel() {
        return idTipoCombustivel;
    }

    public void setIdTipoCombustivel(int idTipoCombustivel) {
        this.idTipoCombustivel = idTipoCombustivel;
    }

    public String exibirCarro() {
        return  "Tipo do veículo: Carro" +
                "\n" + super.exibirVeiculos() +
                "\nPotência: " + this.cavaloPotencia + "cv" +
                "\nNúmero de Portas: " + this.numeroPortas +
                "\nCombustíveç: " + this.tipoCombustivel[idTipoCombustivel];
    }

    public void cadastrarCarro() {
        System.out.println("Carro cadastrado com sucesso.");
    }

    public void atualizarCarro() {
        System.out.println("Dados do carro atualizados.");
    }

    public void removerCarro() {
        System.out.println("Carro removido.");
    }
}