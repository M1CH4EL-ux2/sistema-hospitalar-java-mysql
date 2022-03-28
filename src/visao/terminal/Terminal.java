package visao.terminal;

import java.util.List;
import java.util.Scanner;
import modelo.dao.ConsultaDAO;
import modelo.dao.MedicoDAO;
import modelo.dao.PacienteDAO;
import modelo.entidade.Consulta;
import modelo.entidade.Medico;
import modelo.entidade.Paciente;
import java.time.LocalDateTime;

public class Terminal {

	public static void main(String[] args) {

		Scanner teclado = new Scanner(System.in);

		while (true) {
			System.out.println("\n--------------------------------------");
			System.out.println("1 - Sair do programa.");
			System.out.println("2 - Cadastrar novo m�dico.");
			System.out.println("3 - Cadastrar novo paciente.");
			System.out.println("4 - Buscar m�dico por matr�cula.");
			System.out.println("5 - Buscar paciente por cpf.");
			System.out.println("6 - Cadastrar nova consulta.");
			System.out.println("7 - Remover consulta cadastrada.");
			System.out.println("8 - Atualizar horario de consulta cadastrada.");
			System.out.println("9 - Gerar relat�rio de consultas.");
			System.out.print("Escolha uma op��o: ");
			int input = teclado.nextInt();
			teclado.nextLine();

			if (input == 1) {
				System.out.println("Programa encerrado!");
				break;
			} else if (input == 2) {
				Medico Medico = new Medico();

				System.out.println("Digite o nome: ");
				Medico.setNome(teclado.nextLine());

				System.out.println("Digite a matr�cula: ");
				Medico.setMatricula(teclado.nextInt());
				teclado.nextLine();
				
				System.out.println("Digite a especialidade: ");
				Medico.setEspecialidade(teclado.nextLine());

				System.out.println("Digite o sal�rio | ex: 4000: ");
				Medico.setSalario(teclado.nextFloat());

				MedicoDAO medicoDAO = new MedicoDAO();
				
				boolean response = medicoDAO.cadastro(Medico);
				
				if (response) {
					System.out.println("O m�dico foi cadastrado com sucesso");
				} else {
					System.out.println("N�o foi poss�vel cadastrar o m�dico.");
				}

				medicoDAO.fecharConexao();
				
			} else if (input == 3) {
				Paciente Paciente = new Paciente();
				PacienteDAO pacienteDAO = new PacienteDAO();

				System.out.println("Digite o nome: ");
				Paciente.setNome(teclado.nextLine());

				System.out.println("Digite o CPF | ex: 123.456.789-11: ");
				Paciente.setCpf(teclado.nextLine());

				System.out.println("Digite a doen�a do paciente: ");
				Paciente.setDoenca(teclado.nextLine());

				if (pacienteDAO.cadastrar(Paciente)) {
					System.out.println("Paciente cadastrado com sucesso!");
				} else {
					System.out.println("N�o foi poss�vel cadastrar o paciente.");
				}

				pacienteDAO.fecharConexao();
				
			} else if (input == 4) {
				System.out.println("Digite a matr�cula do m�dico: ");
				int matricula = teclado.nextInt();

				MedicoDAO medicoDAO = new MedicoDAO();
				Medico medico = medicoDAO.busca(matricula);

				if (medico != null) {
					System.out.println(medico);
				} else {
					System.out.println("M�dico n�o encontrado.");
				}

				medicoDAO.fecharConexao();
				
			} else if (input == 5) {
				System.out.println("Digite o cpf do paciente | ex: 123.456.789-11: ");
				String cpf = teclado.nextLine();

				PacienteDAO pacienteDAO = new PacienteDAO();
				Paciente paciente = pacienteDAO.buscar(cpf);

				if (paciente != null)
					System.out.println(paciente);
				else {
					System.out.println("O paciente de CPF " + cpf + " n�o foi encontrado no sistema");
				}

				pacienteDAO.fecharConexao();

			} else if (input == 6) {
				MedicoDAO medicoDAO = new MedicoDAO();
				PacienteDAO pacienteDAO = new PacienteDAO();
				ConsultaDAO consultaDAO = new ConsultaDAO();

				System.out.println("Digite a matr�cula do m�dico: ");
				Medico medico = medicoDAO.busca(teclado.nextInt());
				teclado.nextLine();

				if (medico == null) {
					System.out.println("O m�dico n�o foi encontrado no sistema!");
				} else {
					System.out.println("Digite o cpf do paciente | ex: 123.456.789-11: ");
					Paciente paciente = pacienteDAO.buscar(teclado.nextLine());

					if (paciente == null)
						System.out.println("Paciente n�o encontrado!");
					else {
						Consulta consulta = new Consulta();
						consulta.setPaciente(paciente);
						consulta.setMedico(medico);

						System.out.println("Digite o horario | ex dd/mm/aaaa hh:mm:ss: ");
						consulta.setHorario(DateUtil.stringToDate(teclado.nextLine(), LocalDateTime.class));

						System.out.println("Digite o valor: ");
						consulta.setValor(teclado.nextFloat());

						boolean response = consultaDAO.cadastrar(consulta);

						if (response) {
							System.out.println("Consulta cadastrada!");
						} else {
							System.out.println("Consulta n�o cadastrada!");
						}
					}
				}

				medicoDAO.fecharConexao();
				pacienteDAO.fecharConexao();
				consultaDAO.fecharConexao();

			} else if (input == 7) {
				System.out.println("Digite a matr�cula do medico: ");
				int matricula = teclado.nextInt();
				teclado.nextLine();
				
				System.out.println("Digite o cpf do paciente: ");
				String cpf = teclado.nextLine();
				//teclado.nextLine();

				System.out.println("Digite o horario | ex dd/mm/aaaa hh:mm:ss: ");
				LocalDateTime horario = DateUtil.stringToDate(teclado.nextLine(), LocalDateTime.class);

				ConsultaDAO consultaDAO = new ConsultaDAO();
				
				boolean response = consultaDAO.remover(matricula, cpf, horario);
				
				if (response) {
					System.out.println("Consulta removida com sucesso!");
				} else {
					System.out.println("N�o foi poss�vel remover a consulta.");
				}	
				
				consultaDAO.fecharConexao();
				
			} else if (input == 8) {
				System.out.println("Digite a matr�cula do medico: ");
				int matricula = teclado.nextInt();
				teclado.nextLine();

				System.out.println("Digite o cpf do paciente: ");
				String cpf = teclado.nextLine();

				System.out.println("Digite o hor�rio antigo da consulta | ex dd/mm/aaaa hh:mm:ss: ");
				LocalDateTime horarioAntigo = DateUtil.stringToDate(teclado.nextLine(), LocalDateTime.class);
				teclado.nextLine();
				
				System.out.println("Digite o novo hor�rio da consulta | ex dd/mm/aaaa hh:mm:ss: ");
				LocalDateTime horarioNovo = DateUtil.stringToDate(teclado.nextLine(), LocalDateTime.class);

				ConsultaDAO consultaDAO = new ConsultaDAO();

				boolean response =  consultaDAO.atualizar(matricula, cpf, horarioAntigo, horarioNovo);
				
				if (response) {
					System.out.println("Hor�rio atualizado com sucesso!");
				} else {
					System.out.println("O horario n�o foi atualizado!");
				}
				
				consultaDAO.fecharConexao();
				
			} else if (input == 9) {
				ConsultaDAO consultaDAO = new ConsultaDAO();

				List<Consulta> consultas = consultaDAO.listar();

				if (consultas.isEmpty()) {
					System.out.println("N�o foi encontrado consultas no sistema!");
				} else {
					for (Consulta c : consultas) {
						System.out.println(c);
					}
				}
				
				consultaDAO.fecharConexao();
				
			} else {
				System.out.println("Por favor digite uma op��o v�lida...");
			}
		}
		
		teclado.close();

	}
}
