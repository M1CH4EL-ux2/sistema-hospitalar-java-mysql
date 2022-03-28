package modelo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstratoDAO {
	
	protected Connection conexao;
	
	final static String URL = "jdbc:mysql://localhost/hospital";
	final static String USER = "root";
	final static String PASSWORD = "123456";
	
	public AbstratoDAO() {
		try {
			conexao = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void fecharConexao() {
		try {
			conexao.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

