package models;

public class Funcionario {

    private int id;
    private String nome, senha, usuario, cargo;

    // Construtor vazio é útil para criar um objeto antes de preenchê-lo
    public Funcionario() {

    }

    public Funcionario(String nome, String usuario, String senha, String cargo) {
        this.nome = nome;
        this.usuario = usuario;
        this.senha = senha;
        this.cargo = cargo;
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

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    
    @Override
    public String toString() {
        // Corrigido para "Funcionario"
        return "Funcionario [id=" + id + ", nome=" + nome + ", usuario=" + usuario + ", cargo=" + cargo + "]";
    }
}