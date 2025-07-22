package models;

/**
 * Classe que representa um caminhão no sistema da concessionária.
 * Herda de Veiculo e adiciona características específicas de caminhões.
 */
public class Caminhao extends Veiculo {
    private int eixo;
    private double capacidadeCarga;
    private double altura;
    private String tipoCarroceria;

    /**
     * Construtor da classe Caminhao.
     * 
     * @param modelo Modelo do caminhão
     * @param numChassi Número do chassi
     * @param quilometragem Quilometragem do caminhão
     * @param preco Preço de venda
     * @param cor Cor do caminhão
     * @param anoFabricacao Ano de fabricação
     * @param idStatus Status do veículo (0-Disponível, 1-Vendido, 2-Reservado)
     * @param eixo Número de eixos
     * @param capacidadeCarga Capacidade de carga em toneladas
     * @param altura Altura em metros
     * @param tipoCarroceria Tipo de carroceria
     */
    public Caminhao(String modelo, String numChassi, double quilometragem, double preco, String cor, int anoFabricacao,
                    int idStatus, int eixo, double capacidadeCarga, double altura, String tipoCarroceria) {
        super(modelo, numChassi, quilometragem, preco, cor, anoFabricacao, idStatus);
        this.eixo = eixo;
        this.capacidadeCarga = capacidadeCarga;
        this.altura = altura;
        this.tipoCarroceria = tipoCarroceria;
    }

    public int getEixo() {
        return eixo;
    }

    public void setEixo(int eixo) {
        this.eixo = eixo;
    }

    public double getCapacidadeCarga() {
        return capacidadeCarga;
    }

    public void setCapacidadeCarga(double capacidadeCarga) {
        this.capacidadeCarga = capacidadeCarga;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public String getTipoCarroceria() {
        return tipoCarroceria;
    }

    public void setTipoCarroceria(String tipoCarroceria) {
        this.tipoCarroceria = tipoCarroceria;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nEixos: " + eixo +
                "\nCapacidade de Carga: " + capacidadeCarga + "t" +
                "\nAltura: " + altura + "m" +
                "\nCarroceria: " + tipoCarroceria;
    }

    /**
    * Método para exibir uma mensagem de cadastrar caminhão.
    */
    public void cadastrarCaminhao() {
        System.out.println("Caminhão cadastrado com sucesso.");
    }

    /**
    * Método para exibir uma mensagem de atualizar caminhão.
    */
    public void atualizarCaminhao() {
        System.out.println("Dados do caminhão atualizados.");
    }

    /**
    * Método para exibir uma mensagem de remover caminhão.
    */
    public void removerCaminhao() {
        System.out.println("Caminhão removido.");
    }
}