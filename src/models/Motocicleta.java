package models;

/**
 * Classe que representa uma motocicleta no sistema da concessionária.
 * Herda de Veiculo e adiciona características específicas de motocicletas.
 */
public class Motocicleta extends Veiculo {
    private int cilindrada;

    /**
     * Construtor da classe Motocicleta.
     * 
     * @param modelo Modelo da motocicleta
     * @param numChassi Número do chassi
     * @param quilometragem Quilometragem da motocicleta
     * @param preco Preço de venda
     * @param cor Cor da motocicleta
     * @param anoFabricacao Ano de fabricação
     * @param idStatus Status do veículo (0-Disponível, 1-Vendido, 2-Reservado)
     * @param cilindrada Cilindrada do motor em cm³
     */
    public Motocicleta(String modelo, String numChassi, double quilometragem, double preco, String cor, int anoFabricacao, int idStatus, 
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

    @Override
    public String toString() {
        return "Tipo do veículo: Motocicleta" +
                "\n" + super.toString() +
                "\nCilindrada: " + this.cilindrada + "cc";
    }

    /**
     * Método para exibir uma mensagem de cadastrar motocicleta.
     */
    public void cadastrarMotocicleta() {
        System.out.println("Motocicleta cadastrada");
    }

    /**
     * Método para exibir uma mensagem de atualizar motocicleta.
     */
    public void atualizarMotocicleta() {
        System.out.println("Dados da motocicleta atualizados");
    }

    /**
     * Método para exibir uma mensagem de remover motocicleta.
     */
    public void removerMotocicleta() {
        System.out.println("Motocicleta removida");
    }
}