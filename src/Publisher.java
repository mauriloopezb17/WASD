import java.time.LocalDate;
import java.util.ArrayList;

public class Publisher extends User {
    private String companyName;
    private String iconPath;
    private String bannerPath;
    private String description;
    private ArrayList<Game> publishedGames;

    public Publisher() {
        super();
        this.companyName = "Unnamed Publisher";
        this.iconPath = "assets/publishers/default_icon.png";
        this.bannerPath = "assets/publishers/default_banner.png";
        this.description = "No description available.";
        this.publishedGames = new ArrayList<>();
    }

    public Publisher(int id, String realName, String country, String email, String password,
                     String username, String profilePicturePath, LocalDate registrationDate,
                     String companyName, String iconPath, String bannerPath, String description) {
        super(id, realName, country, email, password, username, profilePicturePath, registrationDate);
        this.companyName = companyName;
        this.iconPath = iconPath;
        this.bannerPath = bannerPath;
        this.description = description;
        this.publishedGames = new ArrayList<>();
    }

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public String getBannerPath() {
		return bannerPath;
	}

	public void setBannerPath(String bannerPath) {
		this.bannerPath = bannerPath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<Game> getPublishedGames() {
		return publishedGames;
	}

	public void setPublishedGames(ArrayList<Game> publishedGames) {
		this.publishedGames = publishedGames;
	}
    
}
