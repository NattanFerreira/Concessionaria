import data.Banco;
import utils.Funcoes;
import controllers.*;
import models.*;

/**
 * Classe principal do sistema de gerenciamento de concessionária.
 * Responsável por inicializar o banco de dados, inserir dados de teste
 * e iniciar a aplicação.
 */
public class Main {
    
    /**
     * Método principal que inicializa o sistema.
     * Cria a conexão com o banco, insere dados de teste e inicia o menu de login.
     */
    public static void main(String[] args) {
        Banco banco = new Banco();
        views.LoginView.menuInicial(banco);
        
        Funcoes.fechaScanner();
        banco.disconnect();
    }
}
