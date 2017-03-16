import java.util.Scanner;

class Table {
    
    Player[] players;
    Player[] placements;
    boolean placementSet;
    int amtSetPRun;
    
    Table(Player[] players) {
        this.players = players;
        placements = new Player[players.length];
        placementSet = false;
        amtSetPRun = 0;
    }
    
    public void registerPlacement() {
        Scanner in = new Scanner(System.in);
        int selectedPlayer = -1;
        System.out.println("");
        for(int i = 0; i < placements.length; i++) {
            for(int j = 0; j < players.length; j++) {
                System.out.println("Who got: #" + (i + 1));
                printAllPlayers();
                selectedPlayer = in.nextInt();
                while(selectedPlayer < 0 || selectedPlayer > players.length) {
                    System.out.println("Use correct numbers: ");
                    selectedPlayer = in.nextInt();
                }
                placements[i] = players[selectedPlayer - 1];
                break;
            }
        }
        placementSet = true;
    }
    
    void setPlacement(int[] pointDistrubution) {
        for(int i = 0; i < placements.length; i++) {
            placements[i].points += pointDistrubution[i];
        }
    }
    
    void printAllPlayers() {
        for(int i = 0; i < players.length; i++) {
            System.out.println((i + 1)  + ". - " + players[i].name);
        }
    }
}