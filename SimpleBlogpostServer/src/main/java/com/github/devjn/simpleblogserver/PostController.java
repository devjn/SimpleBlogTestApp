package com.github.devjn.simpleblogserver;

import java.util.List;

import com.github.devjn.simpleblogserver.data.Post;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import com.github.devjn.simpleblogserver.data.MyObjectBox;

public class PostController {

	private final BoxStore store = MyObjectBox.builder().name("blogpost-blog-db").build();
	private final Box<Post> box = store.boxFor(Post.class);

	public void createPost(String title, String description) {
		Post post = new Post(title, description);
		box.put(post);
	}
	
	public Post getPost(long id) {
		return box.get(id);
	}
	
	public void removePost(long id) {
		box.remove(id);
	}
	
	public List<Post> getAllPosts() {
		return box.getAll();
	}

}
