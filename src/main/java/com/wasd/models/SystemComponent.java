package com.wasd.models;

import java.io.Serializable;

public class SystemComponent implements Serializable {

    private int idComponent;
    private String name;
    private String typeName;

    public SystemComponent() {
    }

    public SystemComponent(int idComponent, String name, String typeName) {
        this.idComponent = idComponent;
        this.name = name;
        this.typeName = typeName;
    }

    public int getIdComponent() {
        return idComponent;
    }
    public void setIdComponent(int idComponent) {
        this.idComponent = idComponent;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    
    @Override
    public String toString() {
        return "Component=" +
            "\nidComponent=" + idComponent +
            "\nname='" + name +
            "\ntypeName='" + typeName;
    }
}

