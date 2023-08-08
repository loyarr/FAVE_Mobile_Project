package com.example.aksub_projectmobile.models;

import com.google.gson.annotations.SerializedName;

public class TopQuoteResponse{

	@SerializedName("quote")
	private String quote;

	@SerializedName("author")
	private String author;

	public String getQuote(){
		return quote;
	}

	public String getAuthor(){
		return author;
	}
}