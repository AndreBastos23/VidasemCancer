package com.vsc.vidasemcancer;


import android.content.Context;
import android.util.Log;

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
    private static final String GET_POSTS = "http://vidasemcancer.com/wp-json/wp/v2/posts?orderby=date";
    private static final String SEARCH_POSTS = "http://vidasemcancer.com/wp-json/wp/v2/posts?search=";

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
                        if (postList.isEmpty()) {
                            callback.noResults();
                        } else {
                            callback.onSuccess(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // Add the request to the RequestQueue.
        VidaSemCancer.getInstance().addToRequestQueue(stringRequest);

    }

    public void search(Context context, String query, final ServerCallback callback) {
        Log.i("PROCURA", "A procurar por -- " + query + " -- ");
        String[] terms = query.split(" +");
        String url = SEARCH_POSTS;
        for (int i = 0; i < terms.length; i++) {
            url += terms[i];
            if (i < terms.length - 1) {
                url += (",");
            }
        }

        Log.i("PROCURA", url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                postList = postMapper.mapPosts(response);
                if (postList.isEmpty()) {
                    Log.i("PROCURA", "Sucesso");
                    callback.noResults();
                } else {
                    Log.i("PROCURA", "Fracasso");
                    callback.onSuccess(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("PROCURA", "erro a procurar");
                Log.e("PROCURA", error.getMessage());
            }
        });
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
