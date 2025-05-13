import java.time.LocalDate;
import java.util.ArrayList;

public class Player extends User {
    private ArrayList<Player> friends;
    private ArrayList<Game> wishlist;
    private ArrayList<Game> library;

    public Player() {
        super();
        this.friends = new ArrayList<>();
        this.wishlist = new ArrayList<>();
        this.library = new ArrayList<>();
    }

    public Player(int id, String realName, String country, String email, String password,
                  String username, String profilePicturePath, LocalDate registrationDate) {
        super(id, realName, country, email, password, username, profilePicturePath, registrationDate);
        this.friends = new ArrayList<>();
        this.wishlist = new ArrayList<>();
        this.library = new ArrayList<>();
    }

	public ArrayList<Player> getFriends() {
		return friends;
	}

	public void setFriends(ArrayList<Player> friends) {
		this.friends = friends;
	}

	public ArrayList<Game> getWishlist() {
		return wishlist;
	}

	public void setWishlist(ArrayList<Game> wishlist) {
		this.wishlist = wishlist;
	}

	public ArrayList<Game> getLibrary() {
		return library;
	}

	public void setLibrary(ArrayList<Game> library) {
		this.library = library;
	}
    
}
