package com.vvechirko.repositoryapp.data.db

import com.vvechirko.repositoryapp.data.DataSource
import com.vvechirko.repositoryapp.data.entity.PostEntity
import io.reactivex.Completable
import io.reactivex.Observable

class PostDbData(val dao: PostDao): DataSource<PostEntity> {

    private val TABLE_NAME = "posts"

    override fun getAll(): Observable<List<PostEntity>> {
        return dao.getAll().toObservable()
    }

    override fun saveAll(list: List<PostEntity>): Observable<List<PostEntity>> {
        return Completable.fromCallable { dao.insertAll(list) }
                .andThen(Observable.just(list))
    }

    override fun removeAll(list: List<PostEntity>): Completable {
        return Completable.fromCallable { dao.deleteAll(list) }
    }

    override fun removeAll(): Completable {
        return Completable.fromCallable { dao.deleteAll() }
    }

    override fun getAll(query: DataSource.Query<PostEntity>): Observable<List<PostEntity>> {
        return dao.rawQuery(DbData.sqlWhere(TABLE_NAME, query.params)).toObservable()
    }
}