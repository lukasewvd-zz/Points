import java.util.Scanner;

class Table {
    
    Player[] players;
    boolean placementSet;
    int amtSetPRun;
    
    Table(Player[] players) {
        this.players = players;
        placementSet = false;
        amtSetPRun = 0;
    }
    
    public void setPlacement() {
        System.out.println("");
        
        amtSetPRun++;
    }
    
    //Fill Array with placements 1th on 0, 2nd on 1, etc.
    
    private void playerSelecter(int place) {
        Scanner in = new Scanner(System.in);
        int selection = -1;
        
    }
}