package com.wasd.models;

import java.io.*;
import java.util.ArrayList;

import com.wasd.models.SystemComponent; 

public class Requirement implements Serializable {
    private String memory;
    private String processor;
    private String graphics;
    private String storage;
    private String descripcion;

    private ArrayList<SystemComponent> components = new ArrayList();

    public Requirement() {
            this.memory = "8 GB";
            this.processor = "Intel Core i7";
            this.graphics = "Intel HD Graphics 6000";
            this.storage = "256 GB";
            this.descripcion = "1080p / 60 Hz / Low Quality";
        }

    public Requirement(String memory, String processor, String graphics, String storage, String additionalNotes) {
        this.memory = memory;
        this.processor = processor;
        this.graphics = graphics;
        this.storage = storage;
        this.descripcion = additionalNotes;
    }

    public String getMemory() {
        return memory;
    }
    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getProcessor() {
        return processor;
    }
    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getGraphics() {
        return graphics;
    }
    public void setGraphics(String graphics) {
        this.graphics = graphics;
    }

    public String getStorage() {
        return storage;
    }
    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String additionalNotes) {
        this.descripcion = additionalNotes;
    }

    @Override
    public String toString() {
        return "Requirement:" +
                "\nmemory=" + memory +
                "\nprocessor=" + processor +
                "\ngraphics=" + graphics + 
                "\nstorage=" + storage +
                "\naditionalNotes=" + descripcion;
    }

    public void addComponent(SystemComponent c) {
        components.add(c);
    }

    public ArrayList<SystemComponent> getComponents() {
        return components;
    }

    public String getPlatform(){
        return " ";
    }

    public String getFormattedText(){
        return " ";
    }
}
