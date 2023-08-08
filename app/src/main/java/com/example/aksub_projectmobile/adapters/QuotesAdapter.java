package com.example.aksub_projectmobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aksub_projectmobile.database.QuoteDB;
import com.example.aksub_projectmobile.models.FavoriteQuotes;
import com.example.aksub_projectmobile.models.TopQuoteResponse;
import com.example.aksub_projectmobile.R;

import java.util.List;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.ViewHolder>{

    List<TopQuoteResponse> quoteList;
    Context context;
    QuoteDB quoteDB;

    public QuotesAdapter(List<TopQuoteResponse> quoteList, Context context){
        this.quoteList = quoteList;
        this.context = context;
        this.quoteDB = new QuoteDB(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quotes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TopQuoteResponse quote = quoteList.get(position);
        holder.tvName.setText(quote.getAuthor());
        holder.tvQuote.setText("\"" + quote.getQuote() + "\"");

        holder.favoriteButton.setOnClickListener(v -> {
            FavoriteQuotes favoriteQuotes = new FavoriteQuotes(
                    0,
                    quote.getAuthor(),
                    quote.getQuote()
            );
            if(quoteDB.insertQuote(favoriteQuotes) != -1){
                Toast.makeText(context, "Quote added to favorites!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Failed to add quote to favorites.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return quoteList.size();
    }

    public void setQuoteList(List<TopQuoteResponse> quoteList) {
        this.quoteList = quoteList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvQuote;
        ImageButton favoriteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_character_name);
            tvQuote = itemView.findViewById(R.id.tv_quote);
            favoriteButton = itemView.findViewById(R.id.favorite_btn);
        }
    }
}

