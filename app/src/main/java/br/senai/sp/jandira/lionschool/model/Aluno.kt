package br.senai.sp.jandira.lionschool.model

data class Aluno(
    val Aluno:String,
    val Foto:String,
    val Matricula:String,
    val Sexo:String,
    val Status:String,
    val Curso:String,
    val Disciplinas: List<Disciplina>
)


