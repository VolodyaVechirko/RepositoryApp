package com.vvechirko.repositoryapp.data

import io.reactivex.Completable
import io.reactivex.Observable

interface DataSource<T : Any> {

    fun getAll(): Observable<List<T>>

    fun getAll(query: Query<T>): Observable<List<T>>

    fun saveAll(list: List<T>): Completable

    fun removeAll(list: List<T>): Completable

    fun removeAll(): Completable

    fun query(): Query<T> {
        return Query(this)
    }

    class Query<T : Any> constructor(private val dataSource: DataSource<T>) {

        val params: MutableMap<String, String> = mutableMapOf()

        fun has(property: String): Boolean {
            return params[property] != null
        }

        fun get(property: String): String? {
            return params[property]
        }

        fun where(property: String, value: String): Query<T> {
            params[property] = value
            return this
        }

        fun findAll(): Observable<List<T>> {
            return dataSource.getAll(this)
        }

        fun findFirst(): Observable<T> {
            return dataSource.getAll(this)
                    .filter { it.isNotEmpty() }
                    .map { it.first() }
        }
    }
}