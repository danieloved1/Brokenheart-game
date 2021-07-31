import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private ArrayList<Player> players;
    private int score;

    public Game() {
        this.players=new ArrayList<>();
        Seats seat=Seats.North;
        for (int i = 0; i < Seats.NUM_SEATS; i++) {
            players.add(new Player(String.format("daniel %d",i),seat));
            seat=seat.next();

        }
        this.score = 0;
    }

    public void play(){
        int roundNumber=1;

        Scanner scanner = new Scanner(System.in);
        scanner=new Scanner(System.in);
        System.out.println("for Bot Players press 1 for humans players press 2");
        int p=scanner.nextInt();
        while (score<100){
            Round round=new Round(players,roundNumber,p);
            round.deal();
            round.play();
            roundNumber++;
            for (int i = 0; i <Seats.NUM_SEATS ; i++) {
                this.players.get(i).setScore(round.getScore()[i]);
                if(round.getScore()[i]>score){
                    score=round.getScore()[i];
                }
            }
        }
        int winner=100;
        int playerWin=0;
        for (int i = 0; i < Seats.NUM_SEATS; i++) {
            if (players.get(i).getScore()<winner){
                winner=players.get(i).getScore();
                playerWin=i;
            }
        }
        System.out.println("the winner is"+players.get(playerWin)+"");
    }

    @Override
    public String toString() {
        return "Game{" +
                "players=" + players +
                '}';
    }
}
