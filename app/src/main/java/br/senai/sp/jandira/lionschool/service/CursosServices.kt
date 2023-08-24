package br.senai.sp.jandira.lionschool.service

import br.senai.sp.jandira.lionschool.model.CursosList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CursosServices {
    //https://api-lionschool.onrender.com/v1/lion-school/cursos/
    @GET("login/gestante")
    fun getCursos(@Query("email") email: String, @Query("senha") senha: String): Call<CursosList>


}