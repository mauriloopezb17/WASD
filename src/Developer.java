import java.time.LocalDate;
import java.util.ArrayList;

public class Developer extends User {
    private String studioName;
    private ArrayList<Game> developedGames;

    public Developer() {
        super();
        this.studioName = "Unnamed Studio";
        this.developedGames = new ArrayList<>();
    }

    public Developer(int id, String realName, String country, String email, String password,
                     String username, String profilePicturePath, LocalDate registrationDate,
                     String studioName) {
        super(id, realName, country, email, password, username, profilePicturePath, registrationDate);
        this.studioName = studioName;
        this.developedGames = new ArrayList<>();
    }

	public String getStudioName() {
		return studioName;
	}

	public void setStudioName(String studioName) {
		this.studioName = studioName;
	}

	public ArrayList<Game> getDevelopedGames() {
		return developedGames;
	}

	public void setDevelopedGames(ArrayList<Game> developedGames) {
		this.developedGames = developedGames;
	}
    
    
}
