package modelo.entidade;

import java.time.LocalDateTime;

import visao.terminal.DateUtil;

public class Consulta {
	private Medico medico;
	private Paciente paciente;
	private float valor;
	private LocalDateTime horario;

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public LocalDateTime getHorario() {
		return horario;
	}

	public void setHorario(LocalDateTime horario) {
		this.horario = horario;
	}

	@Override
	public String toString() {
		return "Consulta [medico=" + medico + ", paciente=" + paciente + ", valor=" + valor + ", horario=" + DateUtil.dateToString(horario)
				+ "]";
	}

}
