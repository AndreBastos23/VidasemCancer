package com.vsc.vidasemcancer.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vsc.vidasemcancer.Adapters.PostAdapter;
import com.vsc.vidasemcancer.Interface.ServerCallback;
import com.vsc.vidasemcancer.R;
import com.vsc.vidasemcancer.RestOperation;


public class HomeFragment extends Fragment {


    private RestOperation restOperation;
    private ListView listView;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;


    public HomeFragment() {

    }

    public HomeFragment getInstance() {
        return new HomeFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_posts_list, container, false);
        restOperation = new RestOperation();
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(Bundle.EMPTY);

        final ProgressDialog progressDialog = new ProgressDialog(this.getActivity(), 0);
        progressDialog.setTitle("Carregando seus posts");
        progressDialog.show();

        mRecyclerView = (RecyclerView) this.getActivity().findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        restOperation.getPosts(this.getActivity().getApplicationContext(), new ServerCallback() {
            @Override
            public void onSuccess(String response) {
                PostAdapter postAdapter = new PostAdapter(getActivity().getApplicationContext(), restOperation.getPostList());
                mRecyclerView.setAdapter(postAdapter);
                progressDialog.dismiss();
            }
        });


    }
}
