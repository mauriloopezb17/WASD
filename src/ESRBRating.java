import java.util.ArrayList;

public class ESRBRating {
    private String name;
    private String iconPath;
    private String abbreviation;
    private String description;
    private ArrayList<String> features;

    // Constructor
    public ESRBRating(String name, String iconPath, String abbreviation, String description) {
        this.name = name;
        this.iconPath = iconPath;
        this.abbreviation = abbreviation;
        this.description = description;
        this.features = new ArrayList<>();
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getIconPath() {
        return iconPath;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getFeatures() {
        return features;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFeatures(ArrayList<String> features) {
        this.features = features;
    }

    // Add a single feature to the list
    public void addFeature(String feature) {
        this.features.add(feature);
        
    
    }

	@Override
	public String toString() {
		return "ESRBRating [name=" + name + ", iconPath=" + iconPath + ", abbreviation=" + abbreviation
				+ ", description=" + description + ", features=" + features + "]";
	}
}
