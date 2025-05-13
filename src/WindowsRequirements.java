public class WindowsRequirements implements SystemRequirements {
    private String os;
    private String processor;
    private String memory;
    private String graphics;
    private String storage;
    private String additionalNotes;

    public WindowsRequirements(String os, String processor, String memory,
                               String graphics, String storage, String additionalNotes) {
        this.os = os;
        this.processor = processor;
        this.memory = memory;
        this.graphics = graphics;
        this.storage = storage;
        this.additionalNotes = additionalNotes;
    }
    
    public WindowsRequirements() {
        this.os = "Windows 10 64-Bit / Windows 11";
        this.processor = "Intel Core i5-10400 or Ryzen 5 3600";
        this.memory = "16 GB RAM";
        this.graphics = "NVIDIA GTX 1660 or AMD RX 5600 XT";
        this.storage = "100 GB SSD";
        this.additionalNotes = "DirectX 12 compatible GPU required";
    }


    @Override
    public String getPlatform() {
        return "Windows";
    }

    @Override
    public String getFormattedText() {
        return "- OS: " + os + "\n"
             + "- Processor: " + processor + "\n"
             + "- Memory: " + memory + "\n"
             + "- Graphics: " + graphics + "\n"
             + "- Storage: " + storage + "\n"
             + "- Additional Notes: " + additionalNotes;
    }

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
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
