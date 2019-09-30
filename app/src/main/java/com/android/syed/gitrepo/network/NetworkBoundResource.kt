package com.android.syed.gitrepo.network

import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.annotation.WorkerThread
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/*
abstract class NetworkBoundResource<ResultType, RequestType> {

    private lateinit var result: Observable<Resource<ResultType>>

    @MainThread
    protected fun NetworkBoundResource() {
        val source: Observable<Resource<ResultType>>
        if (shouldFetch()) {
            source = createCall()
                .subscribeOn(Schedulers.io())
                .doOnNext({ apiResponse -> saveCallResult(processResponse(apiResponse))})
                .observeOn(AndroidSchedulers.mainThread())
                */
/*.doOnError({ t -> onFetchFailed() })
                .onErrorResumeNext({ t ->
                    loadFromDb()
                        .toObservable()
                        .map({ data -> Resource.error(t.message, data) })

                })*//*

                //.observeOn(AndroidSchedulers.mainThread())
        } else {
            source = loadFromDb()
                .toObservable()
                .map(Function<ResultType, Resource<ResultType>> { Resource.success() })
        }

        result = Observable.concat(loadFromDb()
                .toObservable()
                .map(Function<ResultType, Any> { Resource.loading() })
                .take(1),
            source
        )
    }

    fun getAsObservable(): Observable<Resource<ResultType>> {
        return result
    }

    @WorkerThread
    protected abstract fun saveCallResult(@NonNull item: RequestType)

    protected fun onFetchFailed() {}

    @WorkerThread
    protected fun processResponse(response: Resource<RequestType>): RequestType {
        return response.data!!
    }

    @MainThread
    protected abstract fun shouldFetch(): Boolean

    @NonNull
    @MainThread
    protected abstract fun loadFromDb(): Flowable<ResultType>

    @NonNull
    @MainThread
    protected abstract fun createCall(): Observable<Resource<RequestType>>

}*/
