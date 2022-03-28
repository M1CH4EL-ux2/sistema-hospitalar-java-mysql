package modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import modelo.entidade.Consulta;
import modelo.entidade.Paciente;
import modelo.entidade.Medico;

public class ConsultaDAO extends AbstratoDAO {

	public boolean cadastrar(Consulta consulta) {
		boolean resultado;

		try {
			PreparedStatement stmt = conexao.prepareStatement(
					"insert into consulta (id_medico, id_paciente, horario, valor) values (?, ?, ?, ?)");

			stmt.setInt(1, consulta.getMedico().getId());
			stmt.setInt(2, consulta.getPaciente().getId());
			stmt.setObject(3, consulta.getHorario());
			stmt.setFloat(4, consulta.getValor());

			if (stmt.executeUpdate() == 1) {
				resultado = true;
			} else {
				resultado = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			resultado = false;
		}

		return resultado;
	}

	public boolean atualizar(int matricula, String cpf, Object antigoHorario, Object novoHorario) {
		boolean result;

		try {
			PreparedStatement stmt = conexao.prepareStatement(
					"update consulta set horario = ? where id_medico = (select id from medico where matricula = ?) "
							+ " and id_paciente = (select id from paciente where cpf = ?) and horario = ?");

			stmt.setObject(1, novoHorario);
			stmt.setInt(2, matricula);
			stmt.setString(3, cpf);
			stmt.setObject(4, antigoHorario);

			if (stmt.executeUpdate() == 1) {
				result = true;
			} else {
				result = false;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao fazer operação no banco de dado!");
			result = false;
		}

		return result;
	}

	public boolean remover(int matricula, String cpf, Object horario) {
		boolean sucesso;

		try {
			PreparedStatement stmt = conexao.prepareStatement("delete from consulta where id_medico = (select id from medico where matricula = ?) and "
					+ " id_paciente = (select id from paciente where cpf = ?) and horario = ?");
			
			stmt.setInt(1, matricula);
			stmt.setString(2, cpf);
			stmt.setObject(3, horario);
			
			sucesso = stmt.executeUpdate() == 1;
			
		} catch (SQLException e) {
			System.out.println("Erro ao fazer operação no banco de dados!");
			sucesso = false;
			// e.printStackTrace();
		}

		return sucesso;
	}

	public List<Consulta> listar() {
		List<Consulta> consultas = new ArrayList<Consulta>();

		try {
			PreparedStatement stmt = conexao.prepareStatement("select c.horario, c.valor, m.*, p.* from consulta "
					+ "c inner join medico m on m.id = c.id_medico inner join " + "paciente p on p.id = c.id_paciente;");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Medico medico = new Medico();
				medico.setId(rs.getInt("m.id"));
				medico.setNome(rs.getString("m.nome"));
				medico.setMatricula(rs.getInt("m.matricula"));
				medico.setEspecialidade(rs.getString("m.especialidade"));
				medico.setSalario(rs.getFloat("m.salario"));

				Paciente paciente = new Paciente();
				paciente.setId(rs.getInt("p.id"));
				paciente.setNome(rs.getString("p.nome"));
				paciente.setCpf(rs.getString("p.cpf"));
				paciente.setDoenca(rs.getString("p.doenca"));

				Consulta consulta = new Consulta();
				consulta.setMedico(medico);
				consulta.setPaciente(paciente);
				consulta.setValor(rs.getFloat("c.valor"));
				consulta.setHorario(rs.getObject("c.horario", LocalDateTime.class));

				consultas.add(consulta);
				
			}
		} catch (SQLException e) {
			System.out.println("Erro ao fazer listagem!");
		}

		return consultas;
	}

}
