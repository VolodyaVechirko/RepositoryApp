package com.vvechirko.repositoryapp.data.db

import com.vvechirko.repositoryapp.data.DataSource
import com.vvechirko.repositoryapp.data.db.DbData.sqlWhere
import com.vvechirko.repositoryapp.data.entity.UserEntity
import io.reactivex.Completable
import io.reactivex.Observable

class UserDbData(val dao: UserDao) : DataSource<UserEntity> {

    private val TABLE_NAME = "users"

    override fun getAll(): Observable<List<UserEntity>> {
        return dao.getAll().toObservable()
    }

    override fun saveAll(list: List<UserEntity>): Observable<List<UserEntity>> {
        return Completable.fromCallable { dao.insertAll(list) }
                .andThen(Observable.just(list))
    }

    override fun removeAll(list: List<UserEntity>): Completable {
        return Completable.fromCallable { dao.deleteAll(list) }
    }

    override fun removeAll(): Completable {
        return Completable.fromCallable { dao.deleteAll() }
    }

    override fun getAll(query: DataSource.Query<UserEntity>): Observable<List<UserEntity>> {
        return dao.rawQuery(sqlWhere(TABLE_NAME, query.params)).toObservable()
    }
}