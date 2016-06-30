package com.vsc.vidasemcancer;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vsc.vidasemcancer.Interface.ServerCallback;
import com.vsc.vidasemcancer.Mappers.PostMapper;
import com.vsc.vidasemcancer.entities.Post;

import java.util.LinkedList;
import java.util.List;

/**
 * The type Rest operation.
 */
public class RestOperation {

    //Services URL
    private static final String GET_POSTS = "http://vidasemcancer.com/wp-json/wp/v2/posts";


    //Data
    private List<Post> postList = new LinkedList<>();
    private PostMapper postMapper = new PostMapper();


    /**
     * Gets posts.
     *
     * @param context  the context
     * @param callback the callback
     */
    public void getPosts(Context context, final ServerCallback callback) {

        getPosts(context, GET_POSTS, callback);
    }


    /**
     * Gets posts.
     *
     * @param context  the context
     * @param url      the url
     * @param callback the callback
     */
    public void getPosts(Context context, String url, final ServerCallback callback) {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        postList = postMapper.mapPosts(response);
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Add the request to the RequestQueue.
        VidaSemCancer.getInstance().addToRequestQueue(stringRequest);

    }

    /**
     * Gets post list.
     *
     * @return the post list
     */
    public List<Post> getPostList() {
        return postList;
    }

}
