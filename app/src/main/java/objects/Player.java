package objects;


import java.util.List;
import java.util.Stack;

public class Player {
    private String playerName;
    private int score;
    private Stack<Card> cardPack;

    public Player() { }

    public Player(String playerName, List<Card> cardPack) {
        this.setPlayerName(playerName);
        this.score = 0;
        this.initPlayerPack(cardPack);
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    private void setPlayerName(String playerName) {
            this.playerName = playerName;
    }

    private void initPlayerPack(List<Card> pack) {
        this.cardPack = new Stack<Card>();
        for (Card card: pack) {
            this.cardPack.push(card);
        }
    }

    public Card popCard() {
        return this.cardPack.pop();
    }

    public boolean packIsEmpty() {
        return this.cardPack.isEmpty();
    }

    public void scoreIncrease() {
        this.score++;
    }
}
