import data.Banco;
import utils.Funcoes;
import controllers.*;
import models.*;

public class Main {
    public static void main(String[] args) {
        Banco banco = new Banco();

        
        views.LoginView.telaLogin(banco);
        Funcoes.fechaScanner();
        banco.disconnect();
    }
}
