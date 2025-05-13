import java.time.LocalDate;
import java.util.ArrayList;

public class Game {
	private int id;
	private String name;
	private String iconPath;
	private String bannerPath;
	private ArrayList<String> promoArt;
	private Developer developer;
	private Publisher publisher;
	private String description;
	private LocalDate releaseDate;
	private int totalReviews;
	private int positiveReviews;
	private double internationalPrice;
	private ArrayList<String> tags;
	private boolean earlyAccess;
	private ESRBRating rating;
	private SystemRequirements win;
	private SystemRequirements linux;
	private SystemRequirements mac;

	// Default constructor
	public Game() {
		this.id = 0;
		this.name = "Untitled Game";
		this.iconPath = "assets/icons/default.png";
		this.bannerPath = "assets/banners/default.png";
		this.promoArt = new ArrayList<>();
		this.promoArt.add("assets/art/default1.png");

		this.developer = new Developer();
		this.publisher = new Publisher();

		this.description = "No description available.";
		this.releaseDate = LocalDate.now();
		this.totalReviews = 100;
		this.positiveReviews = 80;
		this.internationalPrice = 59.99;

		this.tags = new ArrayList<>();
		this.tags.add("Action");
		this.tags.add("Singleplayer");

		this.earlyAccess = false;
		this.rating = new ESRBRating("Mature", "assets/ratings/m.png", "M", "May contain blood, violence, etc.");

		this.win = new WindowsRequirements();
		this.linux = new LinuxRequirements();
		this.mac = new MacRequirements();
	}

	// Full constructor
	public Game(int id, String name, String iconPath, String bannerPath, ArrayList<String> promoArt,
				Developer developer, Publisher publisher, String description, LocalDate releaseDate,
				int totalReviews, int positiveReviews, double internationalPrice, ArrayList<String> tags,
				boolean earlyAccess, ESRBRating rating, SystemRequirements win,
				SystemRequirements linux, SystemRequirements mac) {
		this.id = id;
		this.name = name;
		this.iconPath = iconPath;
		this.bannerPath = bannerPath;
		this.promoArt = promoArt;
		this.developer = developer;
		this.publisher = publisher;
		this.description = description;
		this.releaseDate = releaseDate;
		this.totalReviews = totalReviews;
		this.positiveReviews = positiveReviews;
		this.internationalPrice = internationalPrice;
		this.tags = tags;
		this.earlyAccess = earlyAccess;
		this.rating = rating;
		this.win = win;
		this.linux = linux;
		this.mac = mac;
	}

	// Setters and Getters go here...

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public ArrayList<String> getPromoArt() {
		return promoArt;
	}

	public void setPromoArt(ArrayList<String> promoArt) {
		this.promoArt = promoArt;
	}

	public Developer getDeveloper() {
		return developer;
	}

	public void setDeveloper(Developer developer) {
		this.developer = developer;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public int getTotalReviews() {
		return totalReviews;
	}

	public void setTotalReviews(int totalReviews) {
		this.totalReviews = totalReviews;
	}

	public int getPositiveReviews() {
		return positiveReviews;
	}

	public void setPositiveReviews(int positiveReviews) {
		this.positiveReviews = positiveReviews;
	}

	public double getInternationalPrice() {
		return internationalPrice;
	}

	public void setInternationalPrice(double internationalPrice) {
		this.internationalPrice = internationalPrice;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	public boolean isEarlyAccess() {
		return earlyAccess;
	}

	public void setEarlyAccess(boolean earlyAccess) {
		this.earlyAccess = earlyAccess;
	}

	public SystemRequirements getWin() {
		return win;
	}

	public void setWin(SystemRequirements win) {
		this.win = win;
	}

	public SystemRequirements getLinux() {
		return linux;
	}

	public void setLinux(SystemRequirements linux) {
		this.linux = linux;
	}

	public SystemRequirements getMac() {
		return mac;
	}

	public void setMac(SystemRequirements mac) {
		this.mac = mac;
	}

	public ESRBRating getRating() {
		return rating;
	}

	public void setRating(ESRBRating rating) {
		this.rating = rating;
	}

	public int getNegativeReviews() {
		return totalReviews - positiveReviews;
	}

	public double getPositivePercentage() {
		return totalReviews == 0 ? 0 : ((double) positiveReviews / totalReviews) * 100;
	}
}
