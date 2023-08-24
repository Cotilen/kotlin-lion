package br.senai.sp.jandira.lionschool.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
    private val URL_BASE = " http://10.0.2.2:3000/"
    private val retrofitFactory = Retrofit
        .Builder()
        .baseUrl(URL_BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getCursosService(): CursosServices{
        return retrofitFactory.create(CursosServices::class.java)
    }
    fun getConclusaoService(): ConclusaoService{
        return retrofitFactory.create(ConclusaoService::class.java)
    }

    fun getAlunosService(): AlunosService{
        return retrofitFactory.create(AlunosService::class.java)
    }

    fun getAlunoService(): AlunoService{
        return retrofitFactory.create(AlunoService::class.java)
    }
}