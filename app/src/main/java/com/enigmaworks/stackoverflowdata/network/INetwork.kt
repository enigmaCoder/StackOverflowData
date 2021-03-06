package com.enigmaworks.stackoverflowdata.network

import io.reactivex.Observable

interface INetwork {

    fun initiateRetroFitClient(baseUrl: String) : INetwork?

    fun getAllStackData(): Observable<StackResponse>?
}