package com.company.SearchEngine;

public class SearchResult implements ISearchResult{
    private String id;
    private int rank;

    public SearchResult() {
        this.id = "";
        this.rank = 0;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int getRank() {
        return this.rank;
    }

    @Override
    public void setRank(int rank) {
        this.rank = rank;
    }
}
