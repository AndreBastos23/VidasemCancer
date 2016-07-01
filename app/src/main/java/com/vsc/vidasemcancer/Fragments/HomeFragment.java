package com.vsc.vidasemcancer.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vsc.vidasemcancer.Activities.SearchResultsActivity;
import com.vsc.vidasemcancer.Adapters.PostAdapter;
import com.vsc.vidasemcancer.Interface.NetworkChecker;
import com.vsc.vidasemcancer.Interface.ServerCallback;
import com.vsc.vidasemcancer.R;
import com.vsc.vidasemcancer.RestOperation;


public class HomeFragment extends Fragment {


    private RestOperation restOperation;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressDialog progressDialog;
    private TextView mTextView;

    public HomeFragment() {

    }

    public HomeFragment getInstance() {
        return new HomeFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_posts_list, container, false);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        mTextView = (TextView) rootView.findViewById(R.id.noResultsTextView);
        if (((NetworkChecker) getActivity()).isNetworkAvailable()) {

            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

                @Override
                public void onRefresh() {
                    restOperation.getPosts(getActivity().getApplicationContext(), new ServerCallback() {
                        @Override
                        public void onSuccess(String response) {
                            PostAdapter postAdapter = new PostAdapter(getActivity().getApplicationContext(), restOperation.getPostList());
                            mRecyclerView.setAdapter(postAdapter);
                            hasContent(true);
                            progressDialog.dismiss();
                        }

                        @Override
                        public void noResults() {
                            hasContent(false);
                            progressDialog.dismiss();
                        }

                    });
                    mSwipeRefreshLayout.setRefreshing(false);
                }


            });


            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(mLayoutManager);

            restOperation = new RestOperation();
            progressDialog = new ProgressDialog(this.getActivity(), 0);
            if (SearchResultsActivity.class.isInstance(this.getActivity())) {
                showSearchResults();
            } else {
                showPosts();
            }

            Log.i("HOME_FRAGMENT", "Feito");
            Log.i("HOME_FRAGMENT", restOperation.getPostList().size() + "");
        } else {
            hasContent(false);


            Log.i("HOME_FRAGMENT", "Não há conexão");
        }

        return rootView;
    }

    private void hasContent(boolean b) {
        mRecyclerView.setVisibility(b ? View.VISIBLE : View.GONE);
        mTextView.setVisibility(!b ? View.VISIBLE : View.GONE);
    }

    public void showPosts() {
        mSwipeRefreshLayout.setRefreshing(false);
        progressDialog.setTitle("Carregando seus posts");
        progressDialog.show();
        restOperation.getPosts(this.getActivity().getApplicationContext(), new ServerCallback() {
            @Override
            public void onSuccess(String response) {
                PostAdapter postAdapter = new PostAdapter(getActivity().getApplicationContext(), restOperation.getPostList());
                mRecyclerView.setAdapter(postAdapter);
                hasContent(true);
                progressDialog.dismiss();

            }

            @Override
            public void noResults() {
                Log.i("HOME_FRAGMENT", "Sem resultados");
                hasContent(false);
                progressDialog.dismiss();

            }
        });
    }

    public void showSearchResults() {
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setEnabled(false);
        String query = ((SearchResultsActivity) this.getActivity()).getQuery();
        progressDialog.setTitle("Procurando seus posts");
        progressDialog.show();
        restOperation.search(this.getActivity().getApplicationContext(), query, new ServerCallback() {
            @Override
            public void onSuccess(String response) {
                Log.i("HOME_FRAGMENT", "Dados recebidos");
                PostAdapter postAdapter = new PostAdapter(getActivity().getApplicationContext(), restOperation.getPostList());
                mRecyclerView.setAdapter(postAdapter);
                progressDialog.dismiss();
                hasContent(true);
            }

            @Override
            public void noResults() {
                Log.i("HOME_FRAGMENT", "Sem resultados");
                hasContent(false);
                progressDialog.dismiss();

            }
        });

    }
}
