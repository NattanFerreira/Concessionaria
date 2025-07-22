package utils;

import java.io.IOException;
import java.util.Scanner;

/**
 * Classe utilitária com funções auxiliares para o sistema.
 * Fornece métodos para entrada de dados, validação e formatação de interface.
 */
public class Funcoes {
    public static Scanner input = new Scanner(System.in);

    public static void fechaScanner() {
        input.close();
    }

    /**
     * Lê um login do usuário com validação.
     * Remove espaços em branco e valida se não contém espaços.
     * 
     * @return Login válido ou null se inválido
     */
    public static String lerLogin() {
        String login;
        login = input.nextLine().trim(); // Remove espaços em branco no início e no final
        if (login.contains(" ")) {
            System.out.println("O login não pode conter espaços em branco.");
            return null;
        }
        return login;
    }

    /**
     * Lê uma senha do usuário com validação.
     * Valida se tem pelo menos 6 caracteres e não contém espaços.
     * 
     * @return Senha válida ou null se inválida
     */
    public static String lerSenha() {
        String pass;
        pass = input.nextLine().trim(); // Remover espaços em branco no início e no final
        if (pass.length() < 6) {
            System.out.println("A senha deve ter pelo menos 6 caracteres.");
            return null;
        } else if (pass.contains(" ")) {
            System.out.println("A senha não pode conter espaços em branco.");
            return null;
        }
        return pass;
    }

    /**
     * Limpa a tela do console.
     * Funciona em sistemas Windows e Unix-like.
     * Em caso de erro, simula limpeza com quebras de linha.
     */
    public static void limpaTela() {
        try {
            String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao tentar limpar a tela: " + e.getMessage());

            for (int i = 0; i < 50; ++i) {
                System.out.println();
            }
        }
    }

    /**
     * Lê um número inteiro do usuário com tratamento de erro.
     * 
     * @return Número inteiro válido ou -1 em caso de erro
     */
    public static int lerInt() {
        try {
            String inputStr = input.nextLine().trim();
            return Integer.parseInt(inputStr);
        } catch (NumberFormatException e) {
            System.out.println("Por favor, digite um número inteiro válido.");
            return -1;
        }
    }

    /**
     * Lê uma string do usuário com validação básica.
     * Remove espaços em branco e verifica se não está vazia.
     * 
     * @return String válida ou null se vazia
     */
    public static String lerString() {
        String inputStr = input.nextLine().trim();
        if (inputStr.isEmpty()) {
            System.out.println("Entrada inválida. Tente novamente.");
            return null;
        }
        return inputStr;
    }

    /**
     * Lê um número decimal do usuário com tratamento de erro.
     * 
     * @return Número decimal válido ou -1 em caso de erro
     */
    public static double lerDouble() {
        try {
            String inputStr = input.nextLine().trim();
            return Double.parseDouble(inputStr);
        } catch (NumberFormatException e) {
            System.out.println("Por favor, digite um número decimal válido.");
            return -1;
        }
    }

    /**
     * Exibe um cabeçalho formatado para menus.
     * 
     * @param texto Texto a ser exibido no cabeçalho
     */
    public static void cabecalhoMenu(String texto) {
        System.out.println("\n--- " + texto + " ---");
    }

    /**
     * Pausa a execução aguardando o usuário pressionar Enter.
     */
    public static void pressEnterToContinue() {
        System.out.println("Pressione Enter para continuar...");
        input.nextLine();
    }
}
