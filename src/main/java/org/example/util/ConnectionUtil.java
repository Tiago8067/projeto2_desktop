package org.example.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;

@NoArgsConstructor
public class ConnectionUtil {
    private static Connection conn = null;

    public Connection criarConexao() {

        if (conn != null) {
            return conn;
        } else {

            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                System.out.println("Oops! Can't find class org.postgresql.Driver");
                System.exit(-1);
            }

            try {
                conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/projeto2_desktop", "postgres", "1234");
                //conn.setAutoCommit(false);
            } catch (Exception e) {
                System.out.println("ERRO " + e.getMessage());
                //javax.swing.JOptionPane.showMessageDialog(null,e.getMessage(),"ERRO", javax.swing.JOptionPane.ERROR_MESSAGE);
                System.exit(-2);
            }

            return conn;
        }
    }
}
