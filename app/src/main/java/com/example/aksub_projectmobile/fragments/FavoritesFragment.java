package com.example.aksub_projectmobile.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aksub_projectmobile.R;
import com.example.aksub_projectmobile.adapters.FavoriteQuotesAdapter;
import com.example.aksub_projectmobile.database.QuoteDB;
import com.example.aksub_projectmobile.models.FavoriteQuotes;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {
    RecyclerView rvQuotes;
    FavoriteQuotesAdapter favoriteQuotesAdapter;
    QuoteDB quoteDB;
    List<FavoriteQuotes> favoriteQuotesList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        quoteDB = new QuoteDB(getActivity());
        favoriteQuotesList = quoteDB.getAllFavoriteQuotes();
        favoriteQuotesAdapter = new FavoriteQuotesAdapter(favoriteQuotesList, getActivity());

        rvQuotes = view.findViewById(R.id.rv_quotes);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvQuotes.setLayoutManager(linearLayoutManager);
        rvQuotes.setAdapter(favoriteQuotesAdapter);

        return view;
    }
}