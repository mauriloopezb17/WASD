import java.time.LocalDate;

public abstract class User {
    protected int id;
    protected String realName;
    protected String country;
    protected String email;
    protected String password;
    protected String username;
    protected String profilePicturePath;
    protected LocalDate registrationDate;

    public User() {
        this.id = 0;
        this.realName = "Unnamed";
        this.country = "Unknown";
        this.email = "user@example.com";
        this.password = "password";
        this.username = "user";
        this.profilePicturePath = "assets/profiles/default.png";
        this.registrationDate = LocalDate.now();
    }

    public User(int id, String realName, String country, String email, String password,
                String username, String profilePicturePath, LocalDate registrationDate) {
        this.id = id;
        this.realName = realName;
        this.country = country;
        this.email = email;
        this.password = password;
        this.username = username;
        this.profilePicturePath = profilePicturePath;
        this.registrationDate = registrationDate;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProfilePicturePath() {
		return profilePicturePath;
	}

	public void setProfilePicturePath(String profilePicturePath) {
		this.profilePicturePath = profilePicturePath;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}
    
}
