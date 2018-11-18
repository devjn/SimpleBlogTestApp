package com.github.devjn.simpleblogclient.api

import com.github.devjn.simpleblogclient.data.Post
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


/**
 * Created by @author Jahongir on 21-Sep-18
 * devjn@jn-arts.com
 * ImgurService
 */

interface BlogService {

    @GET("post_all")
    fun getAllPosts() : Single<List<Post>>

    @POST("post_put")
    fun newPost(@Query("title") title: String, @Query("desc") description: String = "") : Single<Any?>

    @POST("post_remove")
    fun removePost(@Query("id") id: Long) : Single<Any?>

}