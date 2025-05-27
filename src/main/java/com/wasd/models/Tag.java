package com.wasd.models;

import java.io.Serializable;

public class Tag implements Serializable {
    private int idTag;
    private String nameTag;

    public Tag(String nameTag){
        this.nameTag = nameTag;
    }

    public Tag(int idTag, String nameTag) {
        this.idTag = idTag;
        this.nameTag = nameTag;
    }

    public int getIdTag() {
        return idTag;
    }

    public void setIdTag(int idTag) {
        this.idTag = idTag;
    }

    public String getNameTag() {
        return nameTag;
    }

    public void setNameTag(String nameTag) {
        this.nameTag = nameTag;
    }

    @Override
    public String toString() {
        return "Tag:" +
                "\nidTag=" + idTag +
                "\nnameTag=" + nameTag;
    }
}

