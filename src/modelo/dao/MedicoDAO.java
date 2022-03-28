package modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.entidade.Medico;

public class MedicoDAO extends AbstratoDAO {
	public boolean cadastro(Medico dadosMed) {
		boolean resultado;
		
		try {
			PreparedStatement stmt = conexao.prepareStatement("insert into medico (nome, matricula, especialidade, salario) values (?, ?, ?, ?)");
			
			stmt.setString(1, dadosMed.getNome());
			stmt.setInt(2, dadosMed.getMatricula());
			stmt.setString(3, dadosMed.getEspecialidade());
			stmt.setFloat(4, dadosMed.getSalario());

			int resposta = stmt.executeUpdate();
			
			if(resposta == 1) {
				resultado = true;
			} else {
				resultado = false;
			}		
		} catch (SQLException e) {
			System.out.println("Erro ao fazer operação com o banco de dados!");
			resultado = false;
		}
		
		return resultado;
	}
	
	public Medico busca(int matricula) {
		Medico medico = null;

		try {
			PreparedStatement stmt = conexao.prepareStatement("select * from medico where matricula = ?");
			
			stmt.setInt(1, matricula);
			ResultSet resposta = stmt.executeQuery();

			if (resposta.next()) {
				medico = new Medico();
				
				medico.setId(resposta.getInt("id"));
				medico.setNome(resposta.getString("nome"));
				medico.setMatricula(resposta.getInt("matricula"));
				medico.setEspecialidade(resposta.getString("especialidade"));
				medico.setSalario(resposta.getFloat("salario"));
				
			} else {
				System.out.println("Esta matricula não existe!");
			}

			resposta.close();
		} catch (SQLException e) {
			System.out.println("Erro ao fazer busca com no banco de dados!");
			//e.printStackTrace();
		}

		return medico;
	}
}

