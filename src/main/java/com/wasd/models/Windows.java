package com.wasd.models;

public class Windows extends Requirement {
    
    private String osVersion;

    public Windows() {
        super();
        this.osVersion = "Windows 10";
    }

    public Windows(String memory, String processor, String graphics, String storage, String additionalNotes, String osVersion) {
        super(memory, processor, graphics, storage, additionalNotes);
        this.osVersion = osVersion;
    }

    public String getOsVersion() {
        return osVersion;
    }
    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nosVersion=" + osVersion;
    }
}
