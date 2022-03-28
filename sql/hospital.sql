drop database if exists hospital;
create database hospital;

use hospital;

create table medico(
	id int not null primary key auto_increment unique,
    nome varchar(150) not null,
    matricula int not null,
    especialidade varchar(50) not null,
    salario decimal(6, 2) not null
);

create table paciente(
	id int not null primary key auto_increment unique,
    nome varchar(150) not null,
    cpf char(15) not null unique,
    doenca varchar(50)
);

create table consulta(
	id_medico int not null,
    id_paciente int not null,
    horario datetime not null,
    valor decimal(5, 2) not null,
    primary key(id_medico, id_paciente, horario),
    foreign key(id_medico) references medico(id) 
		on update cascade on delete restrict,
    foreign key(id_paciente) references paciente(id) 
		on update cascade on delete restrict
);