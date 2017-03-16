class Player {
    String name;
    int points;
    
    Player(String name) {
                this.name = name;
                points = 0;
    }
    
    public String getName() {
        return name;
    }
    
    public void setScore(int add) {
        points += add;
    }
}