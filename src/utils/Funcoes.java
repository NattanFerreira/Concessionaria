package utils;

import java.io.IOException;
import java.util.Scanner;

public class Funcoes {
    public static Scanner input = new Scanner(System.in);

    public static void fechaScanner() {
        input.close();
    }

    public static String lerLogin() {
        String login;
        login = input.nextLine().trim(); // Remove espaços em branco no início e no final
        if (login.contains(" ")) {
            System.out.println("O login não pode conter espaços em branco.");
            return null;
        }
        return login;
    }

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

    public static int lerInt() {
        try {
            String inputStr = input.nextLine().trim();
            return Integer.parseInt(inputStr);
        } catch (NumberFormatException e) {
            System.out.println("Por favor, digite um número inteiro válido.");
            return -1;
        }
    }

    public static String lerString() {
        String inputStr = input.nextLine().trim();
        if (inputStr.isEmpty()) {
            System.out.println("Entrada inválida. Tente novamente.");
            return null;
        }
        return inputStr;
    }

    public static double lerDouble() {
        try {
            String inputStr = input.nextLine().trim();
            return Double.parseDouble(inputStr);
        } catch (NumberFormatException e) {
            System.out.println("Por favor, digite um número decimal válido.");
            return -1;
        }
    }

    public static void cabecalhoMenu(String texto) {
        System.out.println("\n--- " + texto + " ---");
    }

    public static void pressEnterToContinue() {
        System.out.println("Pressione Enter para continuar...");
        input.nextLine();
    }
}
