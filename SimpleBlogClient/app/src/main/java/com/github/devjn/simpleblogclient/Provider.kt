package com.github.devjn.simpleblogclient

import com.github.devjn.simpleblogclient.api.BlogApi
import com.github.devjn.simpleblogclient.api.BlogService


object Provider {

    private var service: BlogService = BlogApi.createService(BlogService::class.java);

    val blogService: BlogService
        get() = service

    fun changeServiceAddress(address: String) {
        var address = address;
        if (!address.endsWith("/"))
            address += "/"
        BlogApi.changeApiBaseUrl(address)
        service = BlogApi.createService(BlogService::class.java);
    }

}