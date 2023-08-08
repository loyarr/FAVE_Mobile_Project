package com.example.aksub_projectmobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aksub_projectmobile.R;
import com.example.aksub_projectmobile.adapters.QuotesAdapter;
import com.example.aksub_projectmobile.models.TopQuoteResponse;
import com.example.aksub_projectmobile.utils.APIInterface;
import com.example.aksub_projectmobile.utils.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuotesFragment extends Fragment {

    RecyclerView rvQuotes;
    ProgressBar progressBar;
    List<TopQuoteResponse> quoteList = new ArrayList<>();
    QuotesAdapter quotesAdapter;
    private int CURRENT_PAGE = 1;
    private boolean isLoading = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_quotes, container, false);

        rvQuotes = view.findViewById(R.id.rv_quotes);
        progressBar = view.findViewById(R.id.progress_bar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        quotesAdapter = new QuotesAdapter(quoteList, getActivity());
        rvQuotes.setLayoutManager(linearLayoutManager);
        rvQuotes.setAdapter(quotesAdapter);

        initScrollListener();
        loadQuoteData();

        return view;
    }

    private void initScrollListener() {
        rvQuotes.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if(!isLoading){
                    if(linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == quoteList.size() - 1){
                        CURRENT_PAGE++;
                        isLoading = true;
                        progressBar.setVisibility(View.VISIBLE);
                        loadQuoteData();
                    }
                }
            }
        });
    }

    private void loadQuoteData() {
        APIInterface apiInterface = RetrofitInstance.getRetrofitInstance().create(APIInterface.class);
        Call<TopQuoteResponse> call = apiInterface.getQuoteResponse(CURRENT_PAGE);
        call.enqueue(new Callback<TopQuoteResponse>() {
            @Override
            public void onResponse(Call<TopQuoteResponse> call, Response<TopQuoteResponse> response) {
                if(response.isSuccessful()){
                    TopQuoteResponse topQuoteResponse = response.body();
                    quoteList.add(topQuoteResponse);
                    quotesAdapter.setQuoteList(quoteList);
                }else{
                    Toast.makeText(getActivity(), "Request Error :: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
                isLoading = false;
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TopQuoteResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Request Error :: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                isLoading = false;
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}

