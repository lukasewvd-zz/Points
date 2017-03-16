import java.util.*;

class PointComparator implements Comparator<Player> {
	
	@Override
	public int compare(Player player1, Player player2) {
		int player1Points = player1.points;
		int player2Points = player2.points;
 
		if (player1Points < player2Points) {
			return 1;
		} else if (player1Points > player2Points) {
			return -1;
		} else {
			return 0;
		}
	}
}