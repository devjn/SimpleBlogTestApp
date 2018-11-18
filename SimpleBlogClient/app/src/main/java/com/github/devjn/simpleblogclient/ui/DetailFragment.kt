package com.github.devjn.simpleblogclient.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.github.devjn.simpleblogclient.R
import com.github.devjn.simpleblogclient.data.Post

class DetailFragment : Fragment() {

    companion object {
        fun newInstance(post: Post) = DetailFragment().also { it.post = post }
    }

    private lateinit var post: Post

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_detail, container, false)
        activity?.actionBar?.title = post.title
        rootView.findViewById<TextView>(R.id.description).text = post.description
        return rootView
    }


}
