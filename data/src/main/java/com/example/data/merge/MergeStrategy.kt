package com.example.data.merge

import com.example.data.RequestResult

interface MergeStrategy<E> {
    fun merge(db : E, api : E) : E
}

internal class RequestResponseMergeStrategy<T : Any> : MergeStrategy<RequestResult<T>> {

    override fun merge(db: RequestResult<T>, api: RequestResult<T>): RequestResult<T> {
        return when {
            db is RequestResult.InProgress && api is RequestResult.InProgress -> merge(db, api)
            db is RequestResult.Success && api is RequestResult.InProgress -> merge(db, api)
            db is RequestResult.InProgress && api is RequestResult.Success -> merge(db, api)
            db is RequestResult.Success && api is RequestResult.Success -> merge(db, api)
            db is RequestResult.Success && api is RequestResult.Error -> merge(db, api)
            db is RequestResult.InProgress && api is RequestResult.Error -> merge(db, api)
            db is RequestResult.Error && api is RequestResult.InProgress -> merge(db, api)
            db is RequestResult.Error && api is RequestResult.Success -> merge(db, api)
            else -> error("Unimplemented branch right=$db & left=$api")
        }
    }

    private fun merge(cache: RequestResult.InProgress<T>, server: RequestResult.InProgress<T>): RequestResult<T> {
        return when {
            server.data != null -> RequestResult.InProgress(server.data)
            else -> RequestResult.InProgress(cache.data)
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun merge(cache: RequestResult.Success<T>, server: RequestResult.InProgress<T>): RequestResult<T> {
        return RequestResult.InProgress(cache.data)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun merge(cache: RequestResult.InProgress<T>, server: RequestResult.Success<T>): RequestResult<T> {
        return RequestResult.InProgress(server.data)
    }

    private fun merge(cache: RequestResult.Success<T>, server: RequestResult.Error<T>): RequestResult<T> {
        return RequestResult.Error(data = cache.data, error = server.error)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun merge(cache: RequestResult.Success<T>, server: RequestResult.Success<T>): RequestResult<T> {
        return RequestResult.Success(data = server.data)
    }

    private fun merge(cache: RequestResult.InProgress<T>, server: RequestResult.Error<T>): RequestResult<T> {
        return RequestResult.Error(data = server.data ?: cache.data, error = server.error)
    }

    private fun merge(cache: RequestResult.Error<T>, server: RequestResult.InProgress<T>): RequestResult<T> {
        return server
    }

    private fun merge(cache: RequestResult.Error<T>, server: RequestResult.Success<T>): RequestResult<T> {
        return server
    }
}