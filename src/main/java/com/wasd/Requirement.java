package com.wasd;
import java.io.*;

public class Requirement implements Serializable {
    private String memory;
    private String processor;
    private String graphics;
    private String storage;
    private String additionalNotes;

    public Requirement() {
            this.memory = "8 GB";
            this.processor = "Intel Core i7";
            this.graphics = "Intel HD Graphics 6000";
            this.storage = "256 GB";
            this.additionalNotes = "1080p / 60 Hz / Low Quality";
        }

    public Requirement(String memory, String processor, String graphics, String storage, String additionalNotes) {
        this.memory = memory;
        this.processor = processor;
        this.graphics = graphics;
        this.storage = storage;
        this.additionalNotes = additionalNotes;
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

    public String getAdditionalNotes() {
        return additionalNotes;
    }
    public void setAditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    @Override
    public String toString() {
        return "Requirement:" +
                "\nmemory=" + memory +
                "\nprocessor=" + processor +
                "\ngraphics=" + graphics + 
                "\nstorage=" + storage +
                "\naditionalNotes=" + additionalNotes;
    }

    public String getPlatform(){
        return " ";
    }

    public String getFormattedText(){
        return " ";
    }
}
