package com.wasd.models;

public class Mac extends Requirement {
    
    private String macOsVersion;
    private String architecture;

    public Mac() {
        super();
        this.macOsVersion = "macOS 10.15.7";
        this.architecture = "x86_64";
    }

    public Mac(String memory, String processor, String graphics, String storage, String additionalNotes, String macOsVersion, String architecture) {
        super(memory, processor, graphics, storage, additionalNotes);
        this.macOsVersion = macOsVersion;
        this.architecture = architecture;
    }

    public String getMacOsVersion() {
        return macOsVersion;
    }
    public void setMacOsVersion(String macOsVersion) {
        this.macOsVersion = macOsVersion;
    }

    public String getArchitecture() {
        return architecture;
    }
    public void setArchitecture(String architecture) {
        this.architecture = architecture;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nMac OS Version: " + macOsVersion +
                "\nArchitecture: " + architecture;
    }

    @Override
    public String getPlatform() {
        return "Mac";
    }
}
