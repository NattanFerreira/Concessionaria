package models;

/**
 * Classe que representa um carro no sistema da concessionária.
 * Herda de Veiculo e adiciona características específicas de carros.
 */
public class Carro extends Veiculo {
    private int cavaloPotencia;
    private int numeroPortas;
    private int idTipoCombustivel;
    private String[] tipoCombustivel = {"Gasolina", "Etanol", "Diesel", "Elétrico"};

    /**
     * Construtor da classe Carro.
     * 
     * @param modelo Modelo do carro
     * @param numChassi Número do chassi
     * @param quilometragem Quilometragem do carro
     * @param preco Preço de venda
     * @param cor Cor do carro
     * @param anoFabricacao Ano de fabricação
     * @param idStatus Status do veículo (0-Disponível, 1-Vendido, 2-Reservado)
     * @param cavaloPotencia Potência do motor em cavalos
     * @param numeroPortas Número de portas
     * @param idTipoCombustivel Tipo de combustível (0-Gasolina, 1-Etanol, 2-Diesel, 3-Elétrico)
     */
    public Carro(String modelo, String numChassi, double quilometragem, double preco, String cor, 
                int anoFabricacao, int idStatus, int cavaloPotencia, int numeroPortas, int idTipoCombustivel) {
        super(modelo, numChassi, quilometragem, preco, cor, anoFabricacao, idStatus);
        this.cavaloPotencia = cavaloPotencia;
        this.numeroPortas = numeroPortas;
        this.idTipoCombustivel = idTipoCombustivel;
    }

    public int getCavaloPotencia() {
        return cavaloPotencia;
    }

    public void setCavaloPotencia(int cavaloPotencia) {
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

    @Override
    public String toString() {
        return  "Tipo do veículo: Carro" +
                "\n" + super.toString() +
                "\nPotência: " + this.cavaloPotencia + "cv" +
                "\nNúmero de Portas: " + this.numeroPortas +
                "\nCombustíveç: " + this.tipoCombustivel[idTipoCombustivel];
    }

    /**
     * Método para exibir uma mensagem de cadastrar carro.
     */
    public void cadastrarCarro() {
        System.out.println("Carro cadastrado com sucesso.");
    }

    /**
    * Método para exibir uma mensagem de atualizar carro.
    */
    public void atualizarCarro() {
        System.out.println("Dados do carro atualizados.");
    }

    /**
    * Método para exibir uma mensagem de remover carro.
    */
    public void removerCarro() {
        System.out.println("Carro removido.");
    }
}