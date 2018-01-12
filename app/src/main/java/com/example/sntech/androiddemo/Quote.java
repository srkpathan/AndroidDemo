package com.example.sntech.androiddemo;

import java.io.Serializable;

/**
 * Created by sntech on 1/12/2018.
 */

public class Quote implements Serializable {

    private String quoteText;
    private String quoteAuthor;

    public Quote(String quoteText, String quoteAuthor) {
        this.quoteText = quoteText;
        this.quoteAuthor = quoteAuthor;
    }

    public String getQuoteText() {
        return quoteText;
    }

    public String getQuoteAuthor() {
        return quoteAuthor;
    }
}
