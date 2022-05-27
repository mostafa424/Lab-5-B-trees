package com.company.SearchEngine;

import com.company.IBTree;

public class WikiDoc {
    private final int id;
    private final String url;
    private final String title;
    private final IBTree<String, Integer> wordRep;

    public WikiDoc(int id, String url, String title, IBTree<String, Integer> wordRep) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.wordRep = wordRep;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public IBTree<String, Integer> getWordRep() {
        return wordRep;
    }
}
