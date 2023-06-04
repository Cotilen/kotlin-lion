package br.senai.sp.jandira.lionschool.service

import br.senai.sp.jandira.lionschool.model.ConclusaoList
import br.senai.sp.jandira.lionschool.model.CursosList
import retrofit2.Call
import retrofit2.http.GET

interface ConclusaoService {
    //https://api-lionschool.onrender.com/v1/lion-school/conclusao/
    @GET("conclusao/alunos")
    fun getConclusao(): Call<ConclusaoList>
}