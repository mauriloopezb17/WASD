public class MacRequirements implements SystemRequirements {
    private String macOSVersion;     //"macOS 12 Monterey or later"
    private String architecture;     //"x86_64 or Apple Silicon"
    private String processor;
    private String memory;
    private String graphics;
    private String storage;
    private String additionalNotes;

    public MacRequirements(String macOSVersion, String architecture, String processor,
                           String memory, String graphics, String storage, String additionalNotes) {
        this.macOSVersion = macOSVersion;
        this.architecture = architecture;
        this.processor = processor;
        this.memory = memory;
        this.graphics = graphics;
        this.storage = storage;
        this.additionalNotes = additionalNotes;
    }
    
    public MacRequirements() {
        this.macOSVersion = "macOS 12 Monterey or later";
        this.architecture = "Apple M1 / Intel i7 (x86_64)";
        this.processor = "Apple M1 or Intel i7 Quad-Core";
        this.memory = "16 GB RAM";
        this.graphics = "Integrated Apple GPU or Radeon Pro 560X";
        this.storage = "100 GB SSD";
        this.additionalNotes = "Rosetta 2 required for Intel-based games";
    }

    
    @Override
    public String getPlatform() {
        return "macOS";
    }

    @Override
    public String getFormattedText() {
        return "- macOS Version: " + macOSVersion + "\n"
             + "- Architecture: " + architecture + "\n"
             + "- Processor: " + processor + "\n"
             + "- Memory: " + memory + "\n"
             + "- Graphics: " + graphics + "\n"
             + "- Storage: " + storage + "\n"
             + "- Additional Notes: " + additionalNotes;
    }

	public String getMacOSVersion() {
		return macOSVersion;
	}

	public void setMacOSVersion(String macOSVersion) {
		this.macOSVersion = macOSVersion;
	}

	public String getArchitecture() {
		return architecture;
	}

	public void setArchitecture(String architecture) {
		this.architecture = architecture;
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
