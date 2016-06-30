package com.vsc.vidasemcancer.Mappers;


import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vsc.vidasemcancer.entities.Post;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public final class PostMapper {


    public List<Post> mapPosts(String response) {
        Post[] postArray = new Post[0];
        try {
            postArray = new ObjectMapper().reader(Post[].class).readValue(response);
        } catch (IOException e) {
            Log.e("JSON", "Error mapping Posts");
        }
        return Arrays.asList(postArray);

    }

    public Spanned getSpanned(Post post) {
        return Html.fromHtml(post.getContent().getRendered());
    }


}
