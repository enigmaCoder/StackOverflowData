/*
 * Created By Satyam Sarkar on 6/25/2019
 * This Class is created to serve as a HTTP Client Manager for Creating Custom Client Requests with Necessary Client Interceptors
 * and Logging all HTTP Client requests made over Network
 */

package com.ascensia.stackoverflowdata.network

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import timber.log.Timber


class Service {

    /**
     * Returns
     * @return Observable<Any> of Service Class
     * @param serviceClass of RetroFit Calls Interface after configuring @code serviceClass on
     * @param retrofitBuilder as passed with
     * Okhttp Logging Interceptor for Logging all Retrofit Network Calls made using okhttp client
     * Creates an additional Interceptor Class to add any Custom Interceptor like
     * @see RetryInterceptor Retry Interceptor
     * @see KeepAliveInterceptor Keep Alive Interceptor
     * to support Certificate Pinnning and TrustStore verification
     */
    fun createService(serviceClass: Class<out Any>,retrofitBuilder: Retrofit.Builder): Any {
        val logInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            Timber.tag("OKHttp").i(it)
        })
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient: OkHttpClient.Builder? = OkHttpClient().newBuilder()
        val newCookieJar = object : CookieJar {
            private val cookieStore = HashMap<String,List<Cookie>>()
            override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                cookieStore[url.host()] = cookies
            }
            override fun loadForRequest(url: HttpUrl): List<Cookie> {
                return cookieStore[url.host()] ?: ArrayList()
            }
        }
        okHttpClient?.addInterceptor(logInterceptor)
        okHttpClient?.cookieJar(newCookieJar)
        okHttpClient?.let { retrofitBuilder.client(it.build()) }
        return retrofitBuilder.build().create(serviceClass)
        }
}
