package com.vvechirko.repositoryapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.vvechirko.repositoryapp.data.Repository
import com.vvechirko.repositoryapp.data.entity.CommentEntity
import com.vvechirko.repositoryapp.data.entity.PostEntity
import com.vvechirko.repositoryapp.data.entity.UserEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val TAG = "RepositoryTest"

    val userRepo = Repository.of<UserEntity>()
    val postRepo = Repository.of<PostEntity>()
    val commentRepo = Repository.of<CommentEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        getAllUsers()
//        getUserPosts("2")
//        getPostsComments("1")
//        getUsersWithPosts()

        getUserWithPosts("2")

        getPostWithComments("2")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { Log.d(TAG, "User with Posts - $it") },
                        { it.printStackTrace() }
                )

//        getPostsWithComments()
        getPostWithComments("7")
                .flatMapCompletable {
                    removePostsWithComments(listOf(it))
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { Log.d(TAG, "Posts With Comments removed") },
                        { it.printStackTrace() }
                )
    }

    fun getAllUsers() {
        Repository.of<UserEntity>().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { Log.d(TAG, "getAll UserEntity size - ${it.size}") },
                        { it.printStackTrace() }
                )
    }

    fun getUserPosts(userId: String) {
        Repository.of<PostEntity>().query()
                .where(Repository.USER_ID, userId).findAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { Log.d(TAG, "getAll PostEntity size - ${it.size}") },
                        { it.printStackTrace() }
                )
    }

    fun getPostsComments(postId: String) {
        Repository.of<CommentEntity>().query()
                .where(Repository.POST_ID, postId).findAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { Log.d(TAG, "getAll CommentEntity size - ${it.size}") },
                        { it.printStackTrace() }
                )
    }

    fun getUsersWithPosts() {
        userRepo.getAll()
                .subscribeOn(Schedulers.io())
                .takeLast(1)
                .flatMap { users ->
                    Observable.fromIterable(users)
                            .flatMap({ user ->
                                postRepo.query()
                                        .where(Repository.USER_ID, user.id).findAll()
                                        .takeLast(1)
                            }, { us, pl -> Pair(us, pl) })
                }.toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { Log.d(TAG, "Users with Posts size - ${it.size}") },
                        { it.printStackTrace() }
                )
    }

    fun getUserWithPosts(userId: String) {
        Repository.of<UserEntity>().query()
                .where(Repository.ID, userId).findFirst()
                .subscribeOn(Schedulers.io())
                .takeLast(1)
                .flatMap({ user ->
                    postRepo.query()
                            .where(Repository.USER_ID, user.id).findAll()
                            .takeLast(1)
                }, { us, pl -> Pair(us, pl) })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { Log.d(TAG, "User with Posts - $it") },
                        { it.printStackTrace() }
                )
    }

    fun getPostWithComments(postId: String): Observable<PostEntity> {
        return postRepo.query()
                .where(Repository.ID, postId).findFirst()
                .subscribeOn(Schedulers.io())
                .takeLast(1)
                .flatMap({ post ->
                    commentRepo.query()
                            .where(Repository.POST_ID, post.id).findAll()
                            .takeLast(1)
                }, { ps, cl -> ps.comments = cl; ps })
    }

    fun removePostsWithComments(list: List<PostEntity>): Completable {
        return Observable.fromIterable(list)
                .subscribeOn(Schedulers.io())
                .flatMap { post ->
                    commentRepo.removeAll(post.comments)
                            .andThen(Observable.just(post))
                }.toList()
                .flatMapCompletable { posts -> postRepo.removeAll(posts) }
    }

    fun getPostsWithComments(): Observable<List<PostEntity>> {
        return postRepo.getAll()
                .subscribeOn(Schedulers.io())
                .takeLast(1)
                .flatMap { posts -> Observable.fromIterable(posts)
                        .flatMap({ post ->
                            commentRepo.query()
                                    .where(Repository.POST_ID, post.id).findAll()
                                    .takeLast(1)
                        }, { ps, cl -> ps.comments = cl; ps })
                }.toList().toObservable()
                .doOnNext { Log.d(TAG, "get Posts With Comments size - ${it.size}") }
    }
}
