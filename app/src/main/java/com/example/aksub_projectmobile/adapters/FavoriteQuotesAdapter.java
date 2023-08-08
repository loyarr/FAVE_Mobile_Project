package com.example.aksub_projectmobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aksub_projectmobile.R;
import com.example.aksub_projectmobile.database.QuoteDB;
import com.example.aksub_projectmobile.models.FavoriteQuotes;

import java.util.List;

public class FavoriteQuotesAdapter extends RecyclerView.Adapter<FavoriteQuotesAdapter.ViewHolder> {
    List<FavoriteQuotes> favoriteQuotesList;
    Context context;
    QuoteDB quoteDB;

    public FavoriteQuotesAdapter(List<FavoriteQuotes> favoriteQuotesList, Context context) {
        this.favoriteQuotesList = favoriteQuotesList;
        this.context = context;
        this.quoteDB = new QuoteDB(context);
    }

    @NonNull
    @Override
    public FavoriteQuotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_quotes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteQuotesAdapter.ViewHolder holder, int position) {
        FavoriteQuotes quote = favoriteQuotesList.get(position);
        holder.tvName.setText(quote.getAuthor());
        holder.tvQuote.setText("\"" + quote.getQuote() + "\"");

        holder.removeButton.setOnClickListener(v -> {
            if(quoteDB.deleteQuote(quote.getId()) > 0){
                Toast.makeText(context, "Quote removed from favorites!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Quote failed to remove from favorites.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoriteQuotesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvQuote;
        Button removeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_character_name);
            tvQuote = itemView.findViewById(R.id.tv_quote);
            removeButton = itemView.findViewById(R.id.remove_btn);
        }
    }
}
