public class LinuxRequirements implements SystemRequirements {
    private String distro;          //"Ubuntu 22.04+"
    private String kernelVersion;   //"5.10+"
    private String processor;
    private String memory;
    private String graphics;
    private String storage;
    private String additionalNotes;

    public LinuxRequirements(String distro, String kernelVersion, String processor,
                             String memory, String graphics, String storage, String additionalNotes) {
        this.distro = distro;
        this.kernelVersion = kernelVersion;
        this.processor = processor;
        this.memory = memory;
        this.graphics = graphics;
        this.storage = storage;
        this.additionalNotes = additionalNotes;
    }

    public LinuxRequirements() {
        this.distro = "Ubuntu 22.04+ / SteamOS 3";
        this.kernelVersion = "5.10 or newer";
        this.processor = "Intel Core i5-9600K or AMD Ryzen 5 2600";
        this.memory = "16 GB RAM";
        this.graphics = "NVIDIA with Vulkan support / Mesa 21+";
        this.storage = "100 GB SSD";
        this.additionalNotes = "Proton compatibility may be required";
    }

    
    @Override
    public String getPlatform() {
        return "Linux";
    }

    @Override
    public String getFormattedText() {
        return "- Distro: " + distro + "\n"
             + "- Kernel: " + kernelVersion + "\n"
             + "- Processor: " + processor + "\n"
             + "- Memory: " + memory + "\n"
             + "- Graphics: " + graphics + "\n"
             + "- Storage: " + storage + "\n"
             + "- Additional Notes: " + additionalNotes;
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

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
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

	public void setAdditionalNotes(String additionalNotes) {
		this.additionalNotes = additionalNotes;
	}
    
}
