package com.github.devjn.simpleblogserver;

import java.io.IOException;
import java.util.Map;

import com.google.gson.Gson;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response.Status;

public class Server extends NanoHTTPD {

	private final PostController postContorller;
	private final Gson gson;

	public Server() throws IOException {
		super(8080);
		gson = new Gson();
		postContorller = new PostController();
		start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
		System.out.println("\nRunning! Point your browsers to http://localhost:8080/ \n");
	}

	public static void main(String[] args) {
		try {
			new Server();
		} catch (IOException ioe) {
			System.err.println("Couldn't start server:\n" + ioe);
		}
	}

	/*
	 * Actually this is not a good design for a blog app, it would be better to use
	 * sql database and return only blog titles and only return the whole data when
	 * requested. But for such small app it is ok.
	 */
	@Override
	public Response serve(IHTTPSession session) {
		final Map<String, String> parms = session.getParms();
		
//		System.out.println("headers = " + session.getHeaders());
//		System.out.println("params = " + parms);
//		System.out.println("uri = " + session.getUri());

		switch (session.getUri()) {
		case Consts.POST_PUT:
			return handlePostPut(parms);

		case Consts.POST_GET:
			return handlePostGet(parms.get(Consts.PARM_ID));

		case Consts.POST_REMOVE:
			return handlePostRemove(parms.get(Consts.PARM_ID));

		case Consts.POST_GET_ALL:
			return handlePostGetAll();

		default:
			break;
		}

		return super.serve(session);
	}

	private Response handlePostPut(Map<String, String> parms) {
		String title = parms.get(Consts.PARM_TITLE);
		String description = parms.get(Consts.PARM_DESCRIPTION);

		if (title == null || title.isEmpty() || description == null)
			return newFixedLengthResponse(Status.BAD_REQUEST, NanoHTTPD.MIME_PLAINTEXT, "");

		postContorller.createPost(title, description);
		return newFixedLengthResponse(Status.OK, NanoHTTPD.MIME_PLAINTEXT, "");
	}

	private Response handlePostGet(String param_id) {
		try {
			long id = Long.parseLong(param_id);
			String post = gson.toJson(postContorller.getPost(id));
			return newFixedLengthResponse(Status.OK, NanoHTTPD.MIME_PLAINTEXT, post);
		} catch (NumberFormatException e) {
			return newFixedLengthResponse(Status.BAD_REQUEST, NanoHTTPD.MIME_PLAINTEXT, "Incorrect id");
		}
	}

	private Response handlePostRemove(String param_id) {
		try {
			long id = Long.parseLong(param_id);
			postContorller.removePost(id);
			return newFixedLengthResponse(Status.OK, NanoHTTPD.MIME_PLAINTEXT, "");
		} catch (NumberFormatException e) {
			return newFixedLengthResponse(Status.BAD_REQUEST, NanoHTTPD.MIME_PLAINTEXT, "Incorrect id");
		}
	}

	private Response handlePostGetAll() {
		String posts = gson.toJson(postContorller.getAllPosts());
		return newFixedLengthResponse(Status.OK, NanoHTTPD.MIME_PLAINTEXT, posts);
	}

}
