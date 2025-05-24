package com.wasd;

import java.lang.reflect.Array;
import java.util.ArrayList;

import com.wasd.gui.MainWindow;
import com.wasd.models.Game;
import com.wasd.models.Player;

public class Main 
{
    public static void main( String[] args )
    {
        System.out.println( "Testing WASD" );
        ArrayList<Game> games = new ArrayList<>();
        games.add(new Game("Counter-Strike 2"));
        games.add(new Game("Half-Life"));
        games.add(new Game("Portal"));
        games.add(new Game("Doom Eternal"));
        games.add(new Game("The Witcher 3"));

        ArrayList<Game> recommendedGames = new ArrayList<>();
        recommendedGames.add(new Game("Counter-Strike 2"));
        recommendedGames.add(new Game("Half-Life"));
        recommendedGames.add(new Game("Portal"));

        Player player = new Player("Pancake99");
        player.setLibrary(recommendedGames);
        
        ArrayList<Player> friends = new ArrayList<>();
        friends.add(new Player("CoBine17"));
        friends.add(new Player("Tommy999"));
        friends.add(new Player("Willirex"));

        player.setFriends(friends);

        new MainWindow(games, recommendedGames, player);
        //new SecondaryWindow("Test", null, player);
    }
}
