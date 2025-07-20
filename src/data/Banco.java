package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Banco {
    private Connection db = null;
    private Statement statement = null;

    private static final String DATABASE_URL = "jdbc:sqlite:src/data/data.db";

    // Construtor público para permitir criação de instâncias
    public Banco() {
        try {
            // Carrega o driver SQLite explicitamente
            Class.forName("org.sqlite.JDBC");
            this.db = DriverManager.getConnection(DATABASE_URL);
            this.statement = this.db.createStatement();
            this.statement.setQueryTimeout(5);

            // Chama o método para inicializar o banco (criar tabelas se não existirem)
            inicializarBanco();

        } catch (SQLException e) {
            System.out.println("Erro na conexão");
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver SQLite não encontrado");
            System.out.println(e);
        }
    }

    /**
     * Método privado para inicializar o banco de dados criando as tabelas
     * necessárias
     * caso elas não existam.
     */
    private void inicializarBanco() {
        System.out.println("=== Verificando e inicializando banco de dados ===");

        try {
            // Lendo o arquivo schema.sql
            String schemaPath = "src/resources/schema.sql";
            String schema = Files.readString(Paths.get(schemaPath));

            // Executando cada comando SQL separadamente
            String[] comandos = schema.split(";");

            for (String comando : comandos) {
                comando = comando.trim();
                if (!comando.isEmpty()) {
                    try {
                        this.queryInsup(comando);
                    } catch (Exception e) {
                        // Se a tabela já existe, apenas continua
                        if (!e.getMessage().contains("already exists")) {
                            System.err.println("Aviso: " + e.getMessage());
                        }
                    }
                }
            }

            System.out.println("✓ Banco de dados inicializado com sucesso!");

        } catch (IOException e) {
            System.err.println("❌ Erro ao ler schema.sql: " + e.getMessage());
            System.err.println("Continuando sem inicializar tabelas...");
        } catch (Exception e) {
            System.err.println("❌ Erro ao inicializar banco: " + e.getMessage());
        }
    }

    public void disconnect() { // funcao para desconectar o banco
        try {
            if (db != null) {
                db.close();
                statement.close();
            }
        } catch (SQLException e) {
            System.out.println("Erro na hora de fechar conexão");
            System.out.println(e);
        }
    }

    /**
     * Retorna uma conexão com o banco de dados.
     * 
     * @return Connection para o banco de dados
     * @throws SQLException se houver erro na conexão
     */
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver SQLite não encontrado", e);
        }
        return DriverManager.getConnection(DATABASE_URL);
    }

    public void queryInsup(String query) { // funcao para querys de insert e update
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Erro na query");
            System.out.println(e);
        }
    }

    public ResultSet querySelect(String query) { // funcoes para query select
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(query);
            return rs;
        } catch (SQLException e) {
            System.out.println("Erro na query");
            System.out.println(e);
            return rs;
        }
    }
}