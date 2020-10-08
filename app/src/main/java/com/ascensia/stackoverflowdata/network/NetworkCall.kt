package com.ascensia.stackoverflowdata.network

import com.ascensia.stackoverflowdata.ui.main.querySearchObservable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import timber.log.Timber
import java.lang.Exception
import java.util.concurrent.TimeUnit

class NetworkCall: INetwork {

    companion object{
        @Volatile private var networkCallInstance: NetworkCall? = null
        fun getInstance(): NetworkCall {
            synchronized(NetworkCall::class) {
                return networkCallInstance
                    ?: NetworkCall()
            }
        }
        val baseUrl = "https://api.stackexchange.com"
        var retrofitInstance: IRetrofit? = null
    }

    override fun initiateRetroFitClient(baseUrl: String): INetwork?{
        val retrofit = Retrofit.Builder().baseUrl(baseUrl).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        retrofitInstance = Service().createService(IRetrofit::class.java, retrofit) as IRetrofit
        return this
    }

    override fun getAllStackData(): Observable<StackResponse>? {
        return querySearchObservable.debounce(300,TimeUnit.MILLISECONDS)?.switchMap { queryMap ->
            retrofitInstance?.getAllStackUserData(queryMap)?.subscribeOn(Schedulers.io())?.switchMap { response ->
                Observable.create<StackResponse> { stackResEmitter ->
                    if(response.isSuccessful) {
                        val respData = response.body()?.string()
                        respData?.let {
                            try {
                                val modelObj = Json{ ignoreUnknownKeys = true }.decodeFromString(StackResponse.serializer(), it)
                                stackResEmitter.onNext(modelObj)
                            } catch (e: Exception) {
                                Timber.d(e.stackTraceToString())
                            }
                        }
                    }else{
                        Timber.d(response.body()?.string())
                    }
                }

            }
        }
    }
}