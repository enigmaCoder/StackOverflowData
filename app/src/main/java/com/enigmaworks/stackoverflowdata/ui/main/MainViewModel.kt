package com.ascensia.stackoverflowdata.ui.main

import androidx.lifecycle.ViewModel
import com.ascensia.stackoverflowdata.network.NetworkCall
import com.ascensia.stackoverflowdata.network.StackResponse
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MainViewModel : ViewModel() {

    companion object{
        private val observerData = { block: (StackResponse)-> Unit ->
            object : Observer<StackResponse> {
                override fun onSubscribe(d: Disposable) {
                    Timber.d("Subscribed For Events")
                }

                override fun onNext(t: StackResponse) {
                    block.invoke(t)
                }

                override fun onError(e: Throwable) {
                    Timber.d("Error Received For Events ${e.localizedMessage}")
                }

                override fun onComplete() {
                    Timber.d("Completion Received For Events")
                }
            }
        }
    }

    var searchSettingText: String = ""

    fun subscribeForStackResponse(block: (StackResponse)-> Unit){
        NetworkCall.getInstance().initiateRetroFitClient(NetworkCall.baseUrl)?.getAllStackData()
            ?.observeOn(AndroidSchedulers.mainThread())?.doOnDispose {
                subscribeForStackResponse(block)
            }?.doOnError {
                subscribeForStackResponse(block)
            }?.subscribe(observerData(block))
    }
}