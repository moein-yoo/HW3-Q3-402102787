import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private int gold = 80;
    private int level = 1;
    private int experience = 0;
    private boolean isCardLeftNotPlayed;
    private int movesLeft;
    private int[] hitPointOfKala = new int[3];
    private int[] hitPointOfSoldier = new int[3];
    private int[] soldierPlace = new int[3];
    private ArrayList<Card> battleDeck = new ArrayList<>();
    private ArrayList<Card> cardArrayList = new ArrayList<>();
    public User(String username,String password) {
        this.username = username;
        this.password = password;
        battleDeck.add(new Card("Archer"));
        battleDeck.add(new Card("Fireball"));
        cardArrayList.add(new Card("Archer"));
        cardArrayList.add(new Card("Fireball"));
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public ArrayList<Card> getBattleDeck() {
        return battleDeck;
    }

    public void setBattleDeck(ArrayList<Card> battleDeck) {
        this.battleDeck = battleDeck;
    }
    public void removeFromBattleDeck(int index) {
        battleDeck.remove(index);
    }
    public void addToBattleDeck(Card card) {
        battleDeck.add(card);
    }

    public ArrayList<Card> getCardArrayList() {
        return cardArrayList;
    }

    public void setCardArrayList(ArrayList<Card> cardArrayList) {
        this.cardArrayList = cardArrayList;
    }
    public void buyCard(String cardName) {
        cardArrayList.add(new Card(cardName));
    }
    public void sellCard(String cardName) {
        for (Card card : cardArrayList) {
            if (card.getName().equals(cardName)) {
                cardArrayList.remove(card);
                return;
            }
        }
    }

    public int[] getHitPointOfSoldier() {
        return hitPointOfSoldier;
    }

    public void setHitPointOfSoldier(int[] hitPointOfSoldier) {
        this.hitPointOfSoldier = hitPointOfSoldier;
    }

    public int[] getHitPointOfKala() {
        return hitPointOfKala;
    }

    public void setHitPointOfKala(int index,int hitPoint) {
        this.hitPointOfKala[index] = hitPoint;
    }

    public int[] getSoldierPlace() {
        return soldierPlace;
    }

    public void setSoldierPlace(int[] soldierPlace) {
        this.soldierPlace = soldierPlace;
    }

    public boolean isCardLeftNotPlayed() {
        return isCardLeftNotPlayed;
    }

    public void setCardLeftNotPlayed(boolean cardLeftNotPlayed) {
        isCardLeftNotPlayed = cardLeftNotPlayed;
    }

    public int getMovesLeft() {
        return movesLeft;
    }

    public void setMovesLeft(int movesLeft) {
        this.movesLeft = movesLeft;
    }
}
