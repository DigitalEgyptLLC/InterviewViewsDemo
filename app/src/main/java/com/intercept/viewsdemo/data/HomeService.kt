package com.intercept.viewsdemo.data

import retrofit2.http.GET

interface HomeService {
    @GET("f653244bt86a4c82a593a119cc3h0dd9")
    suspend fun getHome(): List<HomeDataModel>
}