package objects;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import untils.MyLocationServices;
import untils.MyPreference;
import untils.MySignal;

public class Game {
    //Constants
    private final int PACK_SIZE = 52;
    private final int MAX_CARD_VALUE = 14;
    private final int CARD_SHAPES_AMOUNT = 4;

    //Game objects
    private ArrayList<Card> cardPack;
    private Player player1;
    private Player player2;

    public Game() {}

    public Game(String player1Name, String player2Name) {
        //Init card pack
        this.cardPack = initCardPack();
        this.shufflePack(this.cardPack);
        //init players
        this.initPlayers(player1Name,player2Name,this.cardPack);
        //Print players
        Log.d("pttt", "Player1Name:"+ player1.getPlayerName() + "\t player1Score: ("+player1.getScore()+")");
        Log.d("pttt", "Player2Name:"+ player2.getPlayerName() + "\t player1Score: ("+player1.getScore()+")");
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    private ArrayList<Card> initCardPack() {
        ArrayList<Card> cardPack = new ArrayList<Card>();
        String imgPrefix = "img_card_";
        for (int value = 2; value <= MAX_CARD_VALUE; value++) {
            String imgLongerPrefix = imgPrefix + value;
            char shape = 'a';

            for (int i = 0; i < CARD_SHAPES_AMOUNT; i++) {
                String imgName = imgLongerPrefix+"_"+ shape;
                Log.d("pttt", "imgName: [\""+imgName+"\"]");
                cardPack.add(new Card(imgName,value));
                shape++;
            }
        }
        return cardPack;
    }

    private void shufflePack(List<Card>cardPack) {
        Collections.shuffle(cardPack);
    }

    private void initPlayers(String player1Name, String player2Name,List <Card> cardPack) {
        //Split the card pack between players.
        List<Card> player1Pack= cardPack.subList(0,(PACK_SIZE)/2);
        List<Card> player2Pack = cardPack.subList((PACK_SIZE)/2,PACK_SIZE);
        this.printPack(player1Pack);
        this.printPack(player2Pack);
        //Create players
        this.player1 = new Player(player1Name,player1Pack);
        this.player2 = new Player(player2Name,player2Pack);
    }

    public Card playerNextCard(Player player){
        return player.popCard();
    }

    public Player clacWinner() {
        /**
         * Method check which player won and return the player.
         */
        if (this.player1.getScore()>this.player2.getScore())
            return this.player1;
        else if(this.player1.getScore()==this.player2.getScore())
            return null;
        else
            return this.player2;
    }

    public String roundWinner(Card player1Card,Card player2Card) {
        /**
         * param Card,Card.
         * Function receive players card, function increase the winner score
         * Function returns winner player name.
         */
        if(player1Card.getValue() - player2Card.getValue() > 0) {
            this.player1.scoreIncrease();
            Log.d("pttt", "roundWinner: " +player1.getPlayerName()+ " Wins!\nPlayer1 score:["+player1.getScore()+"]"
                    +"\t Player2 score:["+player2.getScore()+"]");
            return this.player1.getPlayerName();
        }
        else if (player1Card.getValue() - player2Card.getValue() == 0) {
            this.player1.scoreIncrease();
            this.player2.scoreIncrease();
            Log.d("pttt", "roundWinner: Tie!\nPlayer1 score:["+player1.getScore()+"]"
                    +"\t Player2 score:["+player2.getScore()+"]");
            return "tie";
        }
        else{
            this.player2.scoreIncrease();
            Log.d("pttt", "roundWinner: " +player2.getPlayerName()+ " Wins!\nPlayer1 score:["+player1.getScore()+"]"
                    +"\t Player2 score:["+player2.getScore()+"]");
            return this.player2.getPlayerName();
        }
    }

    public void saveRecord(String name, int score) {
        /**
         * Method gets name,score and set current location,
         * when location is detected (if location not found location will be (0,0)), method create new record
         * and save it to topScore if scoreboard record broke.
         */
        MyLocationServices.getInstance().setLastBestLocation(new MyLocationServices.CallBack_Location() {
            LatLng currentLocation;
            @Override
            public void locationReady(Location location) {
                currentLocation = new LatLng(location.getLatitude(),location.getLongitude());
                setRecord(name,score,currentLocation);
            }
            @Override
            public void onError(String error) {
                Log.d("pttt", "onError: "+error);
                currentLocation = new LatLng(0,0);
                setRecord(name,score,currentLocation);
            }
        });
    }

    private void setRecord(String name, int score,LatLng latlng) {
        TopScores topScores = loadTopScores(MyPreference.KEYS.TOP_SCORES_ARRAY);
        if (topScores == null) {
            Log.d("pttt", "saveRecord:Init topRecord");
            topScores = new TopScores();
        }
        Record record = new Record(name, latlng.latitude,latlng.longitude, score);
        Log.d("pttt", "saveRecord:Record "+record.toString());

        boolean isInserted = topScores.insertRecord(record);
        if (isInserted) {
            //Record inserted into topScores
            //Save topScores by json into shared preference
            saveTopScore(MyPreference.KEYS.TOP_SCORES_ARRAY,topScores);
            MySignal.getInstance().Toast("New Record!");
        }
        Log.d("pttt", "TopScores:\n"+topScores);
    }

    private TopScores loadTopScores(String key) {
        return (TopScores) MyPreference.getInstance().getTopScore(key);
    }

    private void saveTopScore(String key, TopScores topScores) {
        MyPreference.getInstance().putObject(key,topScores);
    }

    public void deleteTopScores(String key) {
        MyPreference.getInstance().deleteKey(key);
    }

    public boolean gameOver(){
        if((this.getPlayer1().packIsEmpty()) || (this.getPlayer2().packIsEmpty()))
            return true;
        else
            return false;
    }

    private void printPack(List<Card> pack) {
        int i=0;
        for (Card card: pack) {
            i++;
            Log.d("pttt", "Card "+i+", value:"+card.getValue()+"\tname: "+card.getName());
        }
    }
}
