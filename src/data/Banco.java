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

    private static final String DATABASE_URL = "jdbc:sqlite:c:/Users/pedro/OneDrive/Documentos/GitHub/Concessionaria/src/data/data.db";

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
            String schemaPath = "c:/Users/pedro/OneDrive/Documentos/GitHub/Concessionaria/src/resources/schema.sql";
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

    public ResultSet queryBusca(String query_busca) { // funcoes para query select
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(query_busca);
            return rs;
        } catch (SQLException e) {
            System.out.println("Erro na query");
            System.out.println(e);
            return rs;
        }
    }

    /**
     * Executa uma query de inserção com parâmetros e retorna o ID gerado.
     * 
     * @param sql        A query SQL de inserção
     * @param parametros Os parâmetros a serem inseridos na query
     * @return O ID gerado pela inserção, ou -1 se não foi gerado
     */
    public int queryInsertComRetorno(String sql, Object... parametros) {
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Define os parâmetros
            for (int i = 0; i < parametros.length; i++) {
                pstmt.setObject(i + 1, parametros[i]);
            }

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao executar inserção: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Executa uma query de atualização/inserção/exclusão com parâmetros.
     * 
     * @param sql        A query SQL
     * @param parametros Os parâmetros a serem inseridos na query
     * @return O número de linhas afetadas
     */
    public int queryUpdate(String sql, Object... parametros) {
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Define os parâmetros
            for (int i = 0; i < parametros.length; i++) {
                pstmt.setObject(i + 1, parametros[i]);
            }

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao executar atualização: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Executa uma query de consulta com parâmetros.
     * 
     * @param sql        A query SQL de consulta
     * @param parametros Os parâmetros a serem inseridos na query
     * @return O ResultSet com os resultados
     */
    public ResultSet querySelect(String sql, Object... parametros) {
        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // Define os parâmetros
            for (int i = 0; i < parametros.length; i++) {
                pstmt.setObject(i + 1, parametros[i]);
            }

            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Erro ao executar consulta: " + e.getMessage());
            return null;
        }
    }
}