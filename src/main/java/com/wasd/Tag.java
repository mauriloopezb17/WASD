package com.wasd;

import java.util.ArrayList;

public class Tag {

    private ArrayList<String> tags;

    public Tag(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<String> getTags() {
        return tags;
    }
    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Tag:" +
                "\ntags=" + tags;
    }
    
}
