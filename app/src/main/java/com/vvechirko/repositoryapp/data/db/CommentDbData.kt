package com.vvechirko.repositoryapp.data.db

import com.vvechirko.repositoryapp.data.DataSource
import com.vvechirko.repositoryapp.data.entity.CommentEntity
import io.reactivex.Completable
import io.reactivex.Observable

class CommentDbData(val dao: CommentDao) : DataSource<CommentEntity> {

    private val TABLE_NAME = "comments"

    override fun getAll(): Observable<List<CommentEntity>> {
        return dao.getAll().toObservable()
    }

    override fun saveAll(list: List<CommentEntity>): Observable<List<CommentEntity>> {
        return Completable.fromCallable { dao.insertAll(list) }
                .andThen(Observable.just(list))
    }

    override fun removeAll(list: List<CommentEntity>): Completable {
        return Completable.fromCallable { dao.deleteAll(list) }
    }

    override fun removeAll(): Completable {
        return Completable.fromCallable { dao.deleteAll() }
    }

    override fun getAll(query: DataSource.Query<CommentEntity>): Observable<List<CommentEntity>> {
        return dao.rawQuery(DbData.sqlWhere(TABLE_NAME, query.params)).toObservable()
    }
}