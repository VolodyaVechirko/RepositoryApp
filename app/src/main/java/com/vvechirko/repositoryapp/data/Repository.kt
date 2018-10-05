package com.vvechirko.repositoryapp.data

import com.vvechirko.repositoryapp.App.Companion.isNetworkAvailable
import com.vvechirko.repositoryapp.data.api.ApiData
import com.vvechirko.repositoryapp.data.db.DbData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

object Repository {

    const val PAGE = "page"
    const val ID = "id"
    const val USER_ID = "userId"
    const val POST_ID = "postId"

    inline fun <reified T : Any> of(): Repo<T> {
        return Repo<T>(ApiData.of<T>(), DbData.of<T>())
    }

    fun clearDatabase(): Completable {
        return Completable.fromCallable { DbData.clearDb() }
                .subscribeOn(Schedulers.io())
    }
}

class Repo<T : Any>(val api: DataSource<T>, val db: DataSource<T>) : DataSource<T> {

    override fun getAll(): Observable<List<T>> {
        return Observable.concatArrayEager(
                // get items from db first
                db.getAll().subscribeOn(Schedulers.io()),
                // get items from api if Network is Available
                Observable.defer {
                    if (isNetworkAvailable())
                    // get new items from api
                        api.getAll().subscribeOn(Schedulers.io())
                                .flatMap { l ->
                                    // remove old items from db
                                    db.removeAll()
                                            // save new items from api to db
                                            .andThen(db.saveAll(l))
                                }
                    else
                    // or return empty
                        Observable.empty()
                }.subscribeOn(Schedulers.io())
        )
    }

    override fun saveAll(list: List<T>): Observable<List<T>> {
        return Observable.defer {
            if (isNetworkAvailable())
            // save items to api first
                api.saveAll(list).subscribeOn(Schedulers.io())
                        // save items to db
                        .flatMap { db.saveAll(list) }
            else
                Observable.error(IllegalAccessError("You are offline"))
        }
    }

    override fun removeAll(list: List<T>): Completable {
        return Completable.defer {
            if (isNetworkAvailable())
            // remove items from api first
                api.removeAll(list).subscribeOn(Schedulers.io())
                        // remove items from db
                        .andThen(db.removeAll(list))
            else
                Completable.error(IllegalAccessError("You are offline"))
        }
    }

    override fun removeAll(): Completable {
        return Completable.defer {
            if (isNetworkAvailable())
            // remove items from api first
                api.removeAll().subscribeOn(Schedulers.io())
                        // remove items from db
                        .andThen(db.removeAll())
            else
                Completable.error(IllegalAccessError("You are offline"))
        }
    }

    override fun getAll(query: DataSource.Query<T>): Observable<List<T>> {
        return Observable.concatArrayEager(
                // get items from db first
                db.getAll(query).subscribeOn(Schedulers.io()),
                // get items from api if Network is Available
                Observable.defer {
                    if (isNetworkAvailable())
                    // get new items from api
                        api.getAll(query).subscribeOn(Schedulers.io())
//                                .flatMap { applyPaging(it, query) }
                                .flatMap { l ->
                                    // get old items from db
                                    db.getAll(query)
                                            // remove old items from db
                                            .flatMapCompletable { old -> db.removeAll(old) }
                                            // save new items from api to db
                                            .andThen(db.saveAll(l))
                                }
                    else
                    // or return empty
                        Observable.empty()
                }.subscribeOn(Schedulers.io())
        )
    }

//    private fun applyPaging(data: List<T>, query: DataSource.Query<T>): Observable<List<T>> {
//        return Observable.fromIterable(data)
//                .map {
//                    if (it is Pagingable) {
//                        if (query.has(Repository.PAGE))
//                            it.applyPage(query.get(Repository.PAGE)?.toInt() ?: 0)
//                    }
//                    it
//                }
//                .toList()
//                .toObservable()
//    }
}

//interface Pagingable {
//
//    fun applyPage(page: Int)
//}