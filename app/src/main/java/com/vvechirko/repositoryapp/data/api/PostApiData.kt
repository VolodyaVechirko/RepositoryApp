package com.vvechirko.repositoryapp.data.api

import com.vvechirko.repositoryapp.data.ApiService
import com.vvechirko.repositoryapp.data.DataSource
import com.vvechirko.repositoryapp.data.entity.PostEntity
import io.reactivex.Completable
import io.reactivex.Observable

class PostApiData : DataSource<PostEntity> {

    private val api: Api = ApiService.create(Api::class.java)

    override fun getAll(): Observable<List<PostEntity>> {
        return api.getPosts(null)
    }

    override fun getAll(query: DataSource.Query<PostEntity>): Observable<List<PostEntity>> {
        return api.getPosts("")
    }

    override fun saveAll(list: List<PostEntity>): Completable {
        return Observable.fromIterable(list)
                .flatMap { api.updatePosts(it.id, it.toMap()) }
                .ignoreElements()
    }

    override fun removeAll(list: List<PostEntity>): Completable {
        return Observable.fromIterable(list)
                .flatMapCompletable { api.deletePost(it.id) }
    }

    override fun removeAll(): Completable {
        TODO("not implemented")
    }
}