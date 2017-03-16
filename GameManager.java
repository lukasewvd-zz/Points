import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("fallthrough")

class GameManager {
    ArrayList<Player> players;
    Table[] tables;
    int[] points;
    Player[][] allPlacements;
    
    int roundNumber;
    String filename;
    int minPlayers;
    int maxPlayers;
    int amtTables;
    int amtPlayers;
    int playersPerTable;
    
    GameManager(String filename) {
        players = new ArrayList<Player>();
        
        roundNumber = 1;
        this.filename = filename;
        minPlayers = 0;
        maxPlayers = 0;
        amtTables = 0;
        amtPlayers = 0;
        playersPerTable = 0;
        
        readFromFile();
        setSettings();
        tableGenerator();
        showMenu();
    }
    
    void setSettings() {
        Scanner in = new Scanner(System.in);
        System.out.println("\n*** TOURNAMENT SETTINGS ***");
        System.out.print("How many tabels: ");
        amtTables = in.nextInt();
        playersPerTable = amtTables;
        System.out.print("Min players for board game: ");
        minPlayers = in.nextInt();
        System.out.print("Max players for board game: ");
        maxPlayers = in.nextInt();
        pointDistrubution();
        
        if(players.size() > (amtTables * playersPerTable)) {
            System.out.println("\nERROR: More Tables needed");
            System.out.println("Maximum players for this tournament: " + (amtTables * playersPerTable));
            System.out.println("# of players in file: " + players.size());
            System.exit(0);
        }
        
        if(amtTables >= minPlayers && amtTables <= maxPlayers) {
            amtPlayers = players.size();
            System.out.println("\nMaximum players for this tournament: " + (amtTables * playersPerTable));
            System.out.println("# of players in file: " + amtPlayers + "\n");
            
            while(players.size() != (amtTables * playersPerTable)) {
                players.add(new Player("NO PLAYER"));
                System.out.println("Adding EMPTY PLAYER to fill tables");
            }
            //Establishing tables
            tables = new Table[amtTables];
        } else {
            System.out.println("There needs to be a table amount " + 
            "between minimum players and maximum players.");
            System.exit(0);
        }
        
    }
         
    void showMenu() {
        Scanner in = new Scanner(System.in);
        char selection = '0';
        
	    while (true) {
	        System.out.println("\n\n\t\t***Menu***\n");
	        System.out.println("Current round: #" + roundNumber);
			System.out.println("1. Show matches for current round");
			System.out.println("2. Register result for a match");
			System.out.println("3. Print results for all registerd matches");
            System.out.println("4. Print all eliminated players");
			System.out.println("5. Next round -->");
			System.out.println("q. Exit");
			
	        System.out.println("\nChoose a number between 1-5: ");
	        selection = in.next().charAt(0);
			
			switch(selection) {
				case '1': clearScreen(); showMatchesforCurrentRound(); break;
				case '2': clearScreen(); showMatchesforCurrentRound(); registerPlacement(); clearScreen(); break;
				case '3': clearScreen(); showAllRegisterdMatches(); break;
				case '4': clearScreen(); printScoreBoard(); break;
                case '5': clearScreen(); nextRound(); break;
				case 'q': System.out.println("\nClosing..."); System.exit(0);
				default: System.out.println("\nUse a number between 1-5!\n");				
					
		 	}
		}
    }
    
    void readFromFile() {
        File file = new File(filename);
                        
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNext()) {
                players.add(new Player(sc.nextLine()));
            }
            sc.close();
        }
        catch (FileNotFoundException ex) {
            System.out.println("Could not find the file!");
        }
    }
    
    void pointDistrubution() {
        Scanner in = new Scanner(System.in);
        points = new int[playersPerTable];
        System.out.println("Setting points: ");
        for(int i = 0; i < points.length; i++) {
            System.out.print("How many points for #" + (i + 1) + ": ");
            points[i] = in.nextInt();
        }
    }
    
    void nextRound() {
        Scanner in = new Scanner(System.in);
        char selection;
        for(int i = 0; i < tables.length; i++) {
            if(!tables[i].placementSet) {
                System.out.println("Not all tabels have been\n" + 
                "registerd, these have: ");
                showAllRegisterdMatches();
                return;
            }
        }
        showAllRegisterdMatches();
        System.out.print("\nAre the above results correct (y/n): ");
        selection = in.next().charAt(0);
        switch(selection) {
            case 'y': 
                roundNumber++; 
                for(int i = 0; i < tables.length; i++) {
                    tables[i].setPlacement(points);
                }
                tableGenerator();
                clearScreen(); 
                return;
            case 'n': return;
            default: System.out.println("INVALID INPUT: Returning to menu...");				
                
        }
    }
    
    void tableGenerator() {
        int skipper = 0;
        if(roundNumber == 1) {
            for(int i = 0; i < amtTables; i++) {
                Player[] playersOnTable = new Player[playersPerTable];
                for(int j = 0; j < playersOnTable.length; j++) {
                    playersOnTable[j] = players.get(skipper);
                    skipper++;
                }
                tables[i] = new Table(playersOnTable);
            }
        } else {
            getAllPlacements();
            for(int i = 0; i < allPlacements.length; i++) {
                tables[i] = new Table(allPlacements[i]);
            }
        }
    }
    
    void getAllPlacements() {
        allPlacements = new Player[amtTables][playersPerTable];
        for(int i = 0; i < tables.length; i++) {
            for(int j = 0; j < tables[i].placements.length; j++) {
                allPlacements[i][j] = tables[j].placements[i];
            }
        }
    }
    
    void showMatchesforCurrentRound() {
        for(int i = 0; i < tables.length; i++) {
            System.out.println("");
            System.out.println("Table: #" + (i + 1));
            for(int j = 0; j < tables[i].players.length; j++) {
                System.out.println(tables[i].players[j].name);
            }
        }
    }
    
    void showAllRegisterdMatches() {
        for(int i = 0; i < tables.length; i++) {
            if(tables[i] == null) {
                return;
            }
            
            if(tables[i].placementSet == true) {
                System.out.println("");
                System.out.println("Result - Table: #" + (i + 1));
                for(int j = 0; j < tables[i].placements.length; j++) {
                    System.out.println("#" + (j + 1) + " - " + tables[i].placements[j].name);
                }
            }
        }
    }
    
    void printScoreBoard() {
        System.out.println("Score Board: ");
        Collections.sort(players, new PointComparator());
		for (Player player : players) {
			System.out.println(player.name + ": " + player.points +"p");
		}
    }
    
    void registerPlacement() {
        Scanner in = new Scanner(System.in);
        int table = -1;
        System.out.println("\nWhich table: ");
        table = in.nextInt();
        tables[table - 1].registerPlacement();
    }
    
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
   }
}