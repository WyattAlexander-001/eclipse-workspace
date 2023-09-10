
public class Kata {
	public static void main(String[] args) {
		System.out.println(quadrant(1, 1));

	}

	public static int quadrant(int x, int y) {
		if (x > 0 && y > 0) {
			return 1;
		} else if (x < 0 && y > 0) {
			return 2;
		} else if (x < 0 && y < 0) {
			return 3;
		} else if (x > 0 && y < 0) {
			return 4;
		} else {
			return 0;
		}
	}
	
	//{"1:0","2:0","3:0","4:0","2:1","3:1","4:1","3:2","4:2","4:3"}
    public static int points(String[] games) {
        int x = 0;
        for(int i =0;i<games.length;i++){
          if(games[i].charAt(0) > games[i].charAt(2)){
            x+=3;
          }else if(games[i].charAt(0) == games[i].charAt(2)){
            x++;
          }else{
            x+=0;
          }
        }
        return x;
      }
}
