package com.vsc.vidasemcancer.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vsc.vidasemcancer.R;
import com.vsc.vidasemcancer.entities.Post;

import java.io.IOException;


public class HomeFragment extends Fragment {

    private Post[] postList;
    private TextView mTextView;

    public HomeFragment() {

    }

    public HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_screen, container, false);


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mTextView = (TextView) this.getActivity().findViewById(R.id.home_text_view);
        if (mTextView != null) {
            Toast toast = Toast.makeText(this.getActivity().getApplicationContext(), "sss", Toast.LENGTH_LONG);
            toast.show();
        }

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this.getActivity().getApplicationContext());
        String url = "http://vidasemcancer.com/wp-json/wp/v2/posts";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.i(String.valueOf(Log.INFO), response.substring(0, 500));
                        try {
                            postList = new ObjectMapper().reader(Post[].class).readValue(response);
                            mTextView.setText(postList[0].getSlug());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
