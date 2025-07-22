package models;

/**
 * Classe que representa um funcionário do sistema da concessionária.
 * Controla informações de acesso e perfil de usuários do sistema.
 */
public class Funcionario {

    private int id;
    private String nome;
    private String senha;
    private String usuario;
    private String[] cargos = {"Vendedor", "Estoque", "Gerente", "Administrador"};
    private int idCargo;

    /**
     * Construtor padrão da classe Funcionario.
     */
    public Funcionario() {

    }

    /**
     * Construtor da classe Funcionario com parâmetros.
     * 
     * @param nome Nome do funcionário
     * @param usuario Nome de usuário para login
     * @param senha Senha de acesso
     * @param idCargo ID do cargo (0-Vendedor, 1-Estoquista, 2-Gerente, 3-Administrador)
     */
    public Funcionario(String nome, String usuario, String senha, int idCargo) {
        this.nome = nome;
        this.usuario = usuario;
        this.senha = senha;
        this.idCargo = idCargo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getLogin() {
        return usuario;
    }

    public void setLogin(String usuario) {
        this.usuario = usuario;
    }

    public int getIdCargo() {
        return idCargo;
    }

    public void setCargo(int idCargo) {
        this.idCargo = idCargo;
    }
    
    @Override
    public String toString() {
        return "Id: " + id + "\nNome: " + nome + "\nUsuario: " + usuario + "\nCargo: " + cargos[idCargo];
    }
}