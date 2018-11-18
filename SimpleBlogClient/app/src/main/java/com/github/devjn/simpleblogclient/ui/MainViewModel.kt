package com.github.devjn.simpleblogclient.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableInt
import android.util.Log
import android.view.View
import android.widget.Toast
import com.github.devjn.simpleblogclient.App
import com.github.devjn.simpleblogclient.Provider
import com.github.devjn.simpleblogclient.data.Post
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val progressBarVisibility = ObservableInt(View.GONE)
    val posts = MutableLiveData<List<Post>>()


    init {
        requestAllPosts()
    }

    fun requestAllPosts() = Provider.blogService.getAllPosts()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { progressBarVisibility.set(View.VISIBLE) }
        .doFinally { progressBarVisibility.set(View.GONE) }
        .subscribe({ it ->
            posts.value = it
        }, { e ->
            Toast.makeText(App.appContext, "Exception during data request. Is server address correct?", Toast.LENGTH_SHORT).show()
            Log.e("BlogPost", "Exception during data request", e)
        }).disposeOnClear()

    fun createNewPost(title: String, description: String) = Provider.blogService.newPost(title, description)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ it -> Log.i("BlogPost", "Answer : $it") }
            , { e -> Log.e("BlogPost", "Exception during data request", e) }).disposeOnClear()

    fun deletePost(id: Long) = Provider.blogService.removePost(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ it -> Log.i("BlogPost", "Answer : $it") }
            , { e -> Log.e("BlogPost", "Exception during data request", e) }).disposeOnClear()

    private fun Disposable.disposeOnClear() {
        compositeDisposable.add(this)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}
