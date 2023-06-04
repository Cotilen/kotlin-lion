package br.senai.sp.jandira.lionschool.service

import br.senai.sp.jandira.lionschool.model.AlunosList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AlunosService {
    @GET("alunos")
    fun getAlunos():Call<AlunosList>

    @GET("alunos")
    fun getStatus(@Query("status") status: String):Call<AlunosList>

    @GET("alunos")
    fun getCurso(@Query("curso") curso: String):Call<AlunosList>

    @GET("alunos")
    fun getConclusao(@Query("conclusao") conclusao: String):Call<AlunosList>
}