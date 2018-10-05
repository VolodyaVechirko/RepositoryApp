package com.vvechirko.repositoryapp.data.api

import com.vvechirko.repositoryapp.data.ApiService
import com.vvechirko.repositoryapp.data.DataSource
import com.vvechirko.repositoryapp.data.Repository
import com.vvechirko.repositoryapp.data.entity.CommentEntity
import io.reactivex.Completable
import io.reactivex.Observable

class CommentApiData : DataSource<CommentEntity> {

    private val api: Api = ApiService.create(Api::class.java)

    override fun getAll(): Observable<List<CommentEntity>> {
        return api.getComments(null)
    }

    override fun getAll(query: DataSource.Query<CommentEntity>): Observable<List<CommentEntity>> {
        return when {
            query.has(Repository.POST_ID) -> query.get(Repository.POST_ID)?.let { userId ->
                api.getComments(userId)
            } ?: throw IllegalArgumentException("Unsupported query $query for CommentEntity")

            query.has(Repository.ID) -> query.get(Repository.ID)?.let { id ->
                api.getComment(id).map { listOf(it) }
            } ?: throw IllegalArgumentException("Unsupported query $query for CommentEntity")

            else -> throw IllegalArgumentException("Unsupported query $query for CommentEntity")
        }
    }

    override fun saveAll(list: List<CommentEntity>): Observable<List<CommentEntity>> {
        return Observable.fromIterable(list)
                .flatMap { it ->
                    // update post or create new if id is empty
                    if (it.id.isEmpty()) api.createComment(it.toMap())
                    else api.updateComment(it.id, it.toMap())
                }
                .toList().toObservable()
    }

    override fun removeAll(list: List<CommentEntity>): Completable {
        return Observable.fromIterable(list)
                .flatMapCompletable { api.deleteComment(it.id) }
    }

    override fun removeAll(): Completable {
        TODO("not implemented")
    }
}