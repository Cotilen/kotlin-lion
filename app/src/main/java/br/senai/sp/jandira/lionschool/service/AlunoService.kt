package br.senai.sp.jandira.lionschool.service

import br.senai.sp.jandira.lionschool.model.AlunoList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AlunoService {

    @GET("alunos/{matricula}")
    fun getAluno(@Path("matricula")matricula:String): Call<AlunoList>
}