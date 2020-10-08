package com.ascensia.stackoverflowdata.network

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

interface IRetrofit {

    //https://api.stackexchange.com/2.2/search?order=desc&sort=activity&intitle=perl&site=stackoverflow
    @GET("/2.2/search?site=stackoverflow")
    fun getAllStackUserData(@QueryMap queryMap: Map<String,String>): Observable<Response<ResponseBody>>
}