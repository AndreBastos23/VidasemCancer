package com.vsc.vidasemcancer.Mappers;


import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vsc.vidasemcancer.entities.Post;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public final class PostMapper {


    public List<Post> mapPosts(String response) {
        Log.i("MAPEADOR", "A mapear posts");

        if (response == null || response.length() == 0) {
            Log.i("MAPEADOR", "Resposta vazia");
            return new LinkedList<Post>();
        }

        Post[] postArray = new Post[0];
        try {
            postArray = new ObjectMapper().reader(Post[].class).readValue(response);
        } catch (IOException e) {
            Log.e("JSON", "Error mapping Posts");
        }

        Log.i("MAPEADOR", "Array de posts mapeado");
        return Arrays.asList(postArray);

    }

    public void clearString(List<Post> postList) {
        for (Post post : postList) {

            post.getTitle().setRendered(android.text.Html.fromHtml(post.getTitle().getRendered()).toString());

        }
    }


}
