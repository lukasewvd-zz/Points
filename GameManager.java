import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

@SuppressWarnings("fallthrough")

class GameManager {
    ArrayList<Player> players;
    Table[] tables;
    
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
        System.out.println("How many tabels: ");
        amtTables = in.nextInt();
        System.out.println("Min players for board game: ");
        minPlayers = in.nextInt();
        System.out.println("Max players for board game: ");
        maxPlayers = in.nextInt();
        
        if(amtTables >= minPlayers && amtTables <= maxPlayers) {
            amtPlayers = players.size();
            System.out.println("\nMaximum players for this tournament: " + (amtTables * amtTables));
            System.out.println("# of players in file: " + amtPlayers + "\n");
            if((amtTables * amtTables) > amtPlayers) {
                while(players.size() != (amtTables * amtTables)) {
                    players.add(new Player("NO PLAYER"));
                    System.out.println("Adding EMPTY PLAYER to fill tables");
                }
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
				case '4': clearScreen(); break;
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
    
    void nextRound() {
        if(roundNumber == 1 && tables[0].placementSet && tables[1].placementSet &&
        tables[2].placementSet && tables[3].placementSet) {
            roundNumber++;
            tableGenerator();
        } else {
            System.out.println("Some matches need placements, these have: ");
            showAllRegisterdMatches();
        }
    }
    
    void tableGenerator() {
        
        int skipper = 0;
        if(roundNumber == 1) {
            for(int i = 0; i < amtTables; i++) {
                Player[] playersOnTable = new Player[amtTables];
                for(int j = 0; j < playersOnTable.length; j++) {
                    playersOnTable[j] = players.get(skipper);
                    skipper++;
                }
                tables[i] = new Table(playersOnTable);
            }
        }
        System.out.println(tables[0].players[0].name);
        System.out.println(tables[0].players[1].name);
        System.out.println(tables[0].players[2].name);
        System.out.println(tables[0].players[3].name);
        System.out.println(tables[3].players[2].name);
    }
    
    void showMatchesforCurrentRound() {
        for(int i = 0; i < tables.length; i++) {
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
            }
        }
    }
    
    void registerPlacement() {
        Scanner in = new Scanner(System.in);
        int table = -1;
        System.out.println("\nWhich match (MatchID): ");
        table = in.nextInt();
        tables[table - 1].setPlacement();
    }
    
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
   }
}