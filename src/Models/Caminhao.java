package Models;

public class Caminhao extends Veiculo {
    private int eixo;
    private double capacidadeCarga;
    private double altura;
    private String tipoCarroceria;

    public Caminhao(String modelo, int numChassi, double quilometragem, double preco, String cor, int anoFabricacao,
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

    public String exibirCaminhao() {
        return super.exibirVeiculos() +
                "\nEixos: " + eixo +
                "\nCapacidade de Carga: " + capacidadeCarga + "t" +
                "\nAltura: " + altura + "m" +
                "\nCarroceria: " + tipoCarroceria;
    }

    public void cadastrarCaminhao() {
        System.out.println("Caminhão cadastrado com sucesso.");
    }

    public void atualizarCaminhao() {
        System.out.println("Dados do caminhão atualizados.");
    }

    public void removerCaminhao() {
        System.out.println("Caminhão removido.");
    }
}
