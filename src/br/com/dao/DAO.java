package br.com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe de conexão com o banco MySQL (JDBC).
 * Todas as classes Manter... HERDAM desta classe (extends DAO)
 * e usam os métodos abrirBanco() e fecharBanco().
 */
public class DAO {

    Connection con;          // objeto de conexão
    PreparedStatement pst;   // objeto que prepara e executa a query
    ResultSet rs;            // objeto que recebe os resultados

    public void abrirBanco() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // driver do MySQL (mysql-connector-j)
            String url = "jdbc:mysql://localhost/academia"; // servidor e nome do banco
            String user = "root";  // usuário do banco
            String senha = "";     // COLOQUE AQUI A SENHA DO SEU MySQL
            con = DriverManager.getConnection(url, user, senha);
            System.out.println("Conectado ao banco de dados");
        } catch (ClassNotFoundException ex) {
            System.out.println("Classe não encontrada, adicione o driver nas bibliotecas.");
            throw new SQLException("Driver do MySQL não encontrado. Adicione o mysql-connector-j nas bibliotecas do projeto.");
        }
    }

    public void fecharBanco() throws Exception {
        if (pst != null) {
            pst.close(); // fecha a query
        }
        if (con != null) {
            con.close(); // fecha a conexão com o banco
        }
        System.out.println("Conexão fechada\n");
    }
}
