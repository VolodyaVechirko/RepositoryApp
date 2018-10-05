package com.vvechirko.repositoryapp.data.api

import com.vvechirko.repositoryapp.data.ApiService
import com.vvechirko.repositoryapp.data.DataSource
import com.vvechirko.repositoryapp.data.Repository
import com.vvechirko.repositoryapp.data.entity.PostEntity
import io.reactivex.Completable
import io.reactivex.Observable

class PostApiData : DataSource<PostEntity> {

    private val api: Api = ApiService.create(Api::class.java)

    override fun getAll(): Observable<List<PostEntity>> {
        return api.getPosts(null)
    }

    override fun getAll(query: DataSource.Query<PostEntity>): Observable<List<PostEntity>> {
        return when {
            query.has(Repository.USER_ID) -> query.get(Repository.USER_ID)?.let { userId ->
                api.getPosts(userId)
            } ?: throw IllegalArgumentException("Unsupported query $query for PostEntity")

            query.has(Repository.ID) -> query.get(Repository.ID)?.let { id ->
                api.getPost(id).map { listOf(it) }
            } ?: throw IllegalArgumentException("Unsupported query $query for PostEntity")

            else -> throw IllegalArgumentException("Unsupported query $query for PostEntity")
        }
    }

    override fun saveAll(list: List<PostEntity>): Observable<List<PostEntity>> {
        return Observable.fromIterable(list)
                .flatMap { it ->
                    // update post or create new if id is empty
                    if (it.id.isEmpty()) api.createPosts(it.toMap())
                    else api.updatePosts(it.id, it.toMap())
                }
                .toList().toObservable()
    }

    override fun removeAll(list: List<PostEntity>): Completable {
        return Observable.fromIterable(list)
                .flatMapCompletable { api.deletePost(it.id) }
    }

    override fun removeAll(): Completable {
        TODO("not implemented")
    }
}