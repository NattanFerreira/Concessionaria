package Models;

public class Carro extends Veiculo {
    private double cavaloPotencia;
    private int numeroPortas;
    private String tipoCombustivel;

    public Carro(String modelo, int numChassi, double quilometragem, double preco, String cor, int anoFabricacao,
                 String status, double cavaloPotencia, int numeroPortas, String tipoCombustivel) {
        super(modelo, numChassi, quilometragem, preco, cor, anoFabricacao, status);
        this.cavaloPotencia = cavaloPotencia;
        this.numeroPortas = numeroPortas;
        this.tipoCombustivel = tipoCombustivel;
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

    public String getTipoCombustível() {
        return tipoCombustivel;
    }

    public void setTipoCombustível(String tipoCombustível) {
        this.tipoCombustivel = tipoCombustível;
    }

    public String exibirCarro() {
        return super.exibirVeiculos() +
                "\nPotência: " + this.cavaloPotencia + "cv" +
                "\nNúmero de Portas: " + this.numeroPortas +
                "\nCombustíveç: " + this.tipoCombustivel;
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
