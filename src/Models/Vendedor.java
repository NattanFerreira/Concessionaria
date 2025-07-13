package models;
public class Vendedor {
    private static int proximoId = 1; //Variavel auxiliar para incrementar o id do proximo objeto que for criado
    private int id;
    private String nome, senha, login;

    

    public Vendedor() {
        this.id = proximoId;
        proximoId++;
    }

    public Vendedor(String nome, String login, String senha) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.id = proximoId;
        proximoId++;
    }

    public void logar(String login, String senha){
        if(login.equals(this.login) && senha.equals(this.senha))
            System.out.println("Login realizado com sucesso");
        else System.out.println("Credenciais erradas");
    }//deveria ser um metodo boolean com retorno do autenticador true ou false

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
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    @Override
    public String toString() {
        return "Vendedor [id=" + id + ", nome=" + nome + ", senha=" + senha + ", login=" + login + "]";
    }
    
    public void atualizarVendedor(){
        
    }

    public static void setProximoId(int proximoId) {
        Vendedor.proximoId = proximoId;
    }
}
