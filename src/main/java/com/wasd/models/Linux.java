package com.wasd.models;

public class Linux extends Requirement {

    private String distro;
    private String kernelVersion;

    public Linux() {
        super();
        this.distro = "Ubuntu";
        this.kernelVersion = "5.4.0-62-generic";
    }

    public Linux(String memory, String processor, String graphics, String storage, String additionalNotes, String distro, String kernelVersion) {
        super(memory, processor, graphics, storage, additionalNotes);
        this.distro = distro;
        this.kernelVersion = kernelVersion;
    }

    public String getDistro() {
        return distro;
    }
    public void setDistro(String distro) {
        this.distro = distro;
    }

    public String getKernelVersion() {
        return kernelVersion;
    }
    public void setKernelVersion(String kernelVersion) {
        this.kernelVersion = kernelVersion;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\ndistro=" + distro +
                "\nkernelVersion=" + kernelVersion;
    }

    @Override
    public String getPlatform() {
        return "Linux";
    }

} 
