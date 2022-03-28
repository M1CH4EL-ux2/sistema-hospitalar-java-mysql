package modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.entidade.Paciente;

public class PacienteDAO extends AbstratoDAO {
	public boolean cadastrar(Paciente umPaciente) {
		boolean resultado;

		try {
			PreparedStatement stmt = conexao
					.prepareStatement("insert into paciente (nome, cpf, doenca) values (?, ?, ?)");

			stmt.setString(1, umPaciente.getNome());
			stmt.setString(2, umPaciente.getCpf());
			stmt.setString(3, umPaciente.getDoenca());

			if (stmt.executeUpdate() == 1) {
				resultado = true;
			} else {
				resultado = false;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao fazer operação no banco de dados!");
			// e.printStackTrace();
			resultado = false;
		}

		return resultado;
	}

	public Paciente buscar(String cpf) {
		Paciente paciente = null;

		try {
			PreparedStatement stmt = conexao.prepareStatement("select * from paciente where cpf = ?");

			stmt.setString(1, cpf);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				paciente = new Paciente();
				paciente.setId(rs.getInt("id"));
				paciente.setNome(rs.getString("nome"));
				paciente.setCpf(rs.getString("cpf"));
				paciente.setDoenca(rs.getString("doenca"));
			}

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return paciente;
	}
}
