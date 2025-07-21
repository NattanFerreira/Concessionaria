package models;

public class Motocicleta extends Veiculo {
    private int cilindrada;

    public Motocicleta(String modelo, int numChassi, double quilometragem, double preco, String cor, int anoFabricacao, int idStatus, 
                       int cilindrada) {
        super(modelo, numChassi, quilometragem, preco, cor, anoFabricacao, idStatus);
        this.cilindrada = cilindrada;
    }

    public int getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(int cilindrada) {
        this.cilindrada = cilindrada;
    }

    public String exibirMotocicleta() {
        return super.exibirVeiculos() + "\nCilindrada: " + this.cilindrada + "cc";
    }

    public void cadastrarMotocicleta() {
        System.out.println("Motocicleta cadastrada");
    }

    public void atualizarMotocicleta() {
        System.out.println("Dados da motocicleta atualizados");
    }

    public void removerMotocicleta() {
        System.out.println("Motocicleta removida");
    }
}
