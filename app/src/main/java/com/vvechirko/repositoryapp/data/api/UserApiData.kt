package com.vvechirko.repositoryapp.data.api

import com.vvechirko.repositoryapp.data.ApiService
import com.vvechirko.repositoryapp.data.DataSource
import com.vvechirko.repositoryapp.data.Repository
import com.vvechirko.repositoryapp.data.entity.UserEntity
import io.reactivex.Completable
import io.reactivex.Observable

class UserApiData : DataSource<UserEntity> {

    private val api: Api = ApiService.create(Api::class.java)

    override fun getAll(): Observable<List<UserEntity>> {
        return api.getUsers()
    }

    override fun getAll(query: DataSource.Query<UserEntity>): Observable<List<UserEntity>> {
        when {
            query.has(Repository.ID) -> query.get(Repository.ID)?.let { id ->
                return api.getUser(id).map { listOf(it) }
            } ?: throw IllegalArgumentException("Unsupported query $query for UserEntity")

            else -> throw IllegalArgumentException("Unsupported query $query for UserEntity")
        }
    }

    override fun saveAll(list: List<UserEntity>): Observable<List<UserEntity>> {
        TODO("not implemented")
    }

    override fun removeAll(list: List<UserEntity>): Completable {
        TODO("not implemented")
    }

    override fun removeAll(): Completable {
        TODO("not implemented")
    }
}