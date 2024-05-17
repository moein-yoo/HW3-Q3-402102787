import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameControl extends Menu{
    public GameControl (ArrayList<User> users , User userInUse,int index) {
        super(users,userInUse,index);
        userArrayList = users;
        RegistryMenu.userInUse = userInUse;
        userInUseIndex = index;
    }
    static User userInUse2 = new User("","");
    static boolean whichTurn = true;
    static int userInUseIndex2 = 0;
    //static String[][][] rowInfo = new String[3][16][4];
    static ArrayList<String>[][] rowInfo = new ArrayList[3][16];
    private void setRowInfo(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 16; j++) {
                rowInfo[i][j] = new ArrayList<>();
            }
        }
        userInUse2.setHitPointOfKala(0,2200*userInUse2.getLevel());
        userInUse2.setHitPointOfKala(1,3400 * userInUse2.getLevel());
        userInUse2.setHitPointOfKala(2,2200*userInUse2.getLevel());
        userInUse.setHitPointOfKala(0,2200*userInUse.getLevel());
        userInUse.setHitPointOfKala(1,3400 * userInUse.getLevel());
        userInUse.setHitPointOfKala(2,2200*userInUse.getLevel());
    }
    static int turnsCount = 0;
    public boolean run(User user2,int userIn2,int turn) {
        turnsCount = turn;
        userInUse2 = user2;
        userInUseIndex2 = userIn2;
        userInUse.setSoldierPlace(new int[]{1,1,1});
        userInUse2.setSoldierPlace(new int[]{15,15,15});
        setRowInfo();
        System.out.println("Battle started with user " + userInUse2.getUsername());
        Pattern[] patterns = new Pattern[10];
        patterns[0] = Pattern.compile("Exit");
        patterns[1] = Pattern.compile("show the hitpoints left of my opponent");
        patterns[2] = Pattern.compile("show line info (?<lineDirection>[\\S]+)");
        patterns[3] = Pattern.compile("number of cards to play");
        patterns[4] = Pattern.compile("number of moves left");
        patterns[5] = Pattern.compile("move troop in line (?<lineDirection>[\\S]+) and row (?<rowNumber>[\\d]+) (?<direction>[\\S]+)");
        patterns[6] = Pattern.compile("deploy troop (?<troopName>[\\S\\s]+) in line (?<lineDirection>[\\S\\s]+) and row (?<rowNumber>[\\d]+)");
        patterns[7] = Pattern.compile("deploy spell Fireball in line (?<lineDirection>[\\S]+)");
        patterns[8] = Pattern.compile("next turn");
        patterns[9] = Pattern.compile("^show current menu$");
        input = scanner.next();
        while (!input.equals("Exit")) {
            boolean ejra = false;
            input += scanner.nextLine();
            if (input.matches(String.valueOf(patterns[0]))) {
                Matcher matcher = getCommandMatcher(input, String.valueOf(patterns[0]));
                matcher.find();
                return false;
            }
            else if (input.matches(String.valueOf(patterns[9]))) {
                Matcher matcher = getCommandMatcher(input, String.valueOf(patterns[9]));
                matcher.find();
                ejra = true;
                System.out.println("Game Menu");
            }
            else if (input.matches(String.valueOf(patterns[1]))) {
                Matcher matcher = getCommandMatcher(input, String.valueOf(patterns[9]));
                matcher.find();
                ejra = true;
                showHitPoints();
            }
            else if (input.matches(String.valueOf(patterns[2]))) {
                Matcher matcher = getCommandMatcher(input, String.valueOf(patterns[2]));
                matcher.find();
                ejra = true;
                showLineInfo(matcher);
            }
            else if (input.matches(String.valueOf(patterns[3]))) {
                Matcher matcher = getCommandMatcher(input, String.valueOf(patterns[3]));
                matcher.find();
                ejra = true;
                cardsToPlay();
            }
            else if (input.matches(String.valueOf(patterns[4]))) {
                Matcher matcher = getCommandMatcher(input, String.valueOf(patterns[4]));
                matcher.find();
                ejra = true;
                soldiersToMove();
            }
            else if (input.matches(String.valueOf(patterns[5]))) {
                Matcher matcher = getCommandMatcher(input, String.valueOf(patterns[5]));
                matcher.find();
                ejra = true;
                moveTroop(matcher);
            }
            else if (input.matches(String.valueOf(patterns[6]))) {
                Matcher matcher = getCommandMatcher(input, String.valueOf(patterns[6]));
                matcher.find();
                ejra = true;
                deployTroops(matcher);
            }
            else if (input.matches(String.valueOf(patterns[7]))) {
                Matcher matcher = getCommandMatcher(input, String.valueOf(patterns[7]));
                matcher.find();
                ejra = true;
                deploySpell(matcher);
            }
            else if (input.matches(String.valueOf(patterns[8]))) {
                Matcher matcher = getCommandMatcher(input, String.valueOf(patterns[8]));
                matcher.find();
                ejra = true;
                nextTurn();
                if (turnsCount==0) {
                    userArrayList.set(userInUseIndex,userInUse);
                    userArrayList.set(userInUseIndex2,userInUse2);
                    return true;
                }
            }
            if(!ejra) {
                System.out.println("Invalid command!");
            }
            input = scanner.next();
        }
        return false;
    }

    public static void showHitPoints() {
        if (whichTurn) {
            System.out.printf("middle castle: %d\n", userInUse2.getHitPointOfKala()[1] > 0 ? userInUse2.getHitPointOfKala()[1] : -1);
            System.out.printf("left castle: %d\n", userInUse2.getHitPointOfKala()[0] > 0 ? userInUse2.getHitPointOfKala()[0] : -1);
            System.out.printf("right castle: %d\n", userInUse2.getHitPointOfKala()[2] > 0 ? userInUse2.getHitPointOfKala()[2] : -1);
        }
        else {
            System.out.printf("middle castle: %d\n", userInUse.getHitPointOfKala()[1] > 0 ? userInUse.getHitPointOfKala()[1] : -1);
            System.out.printf("left castle: %d\n", userInUse.getHitPointOfKala()[0] > 0 ? userInUse.getHitPointOfKala()[0] : -1);
            System.out.printf("right castle: %d\n", userInUse.getHitPointOfKala()[2] > 0 ? userInUse.getHitPointOfKala()[2] : -1);
        }
    }
    public static void showLineInfo(Matcher matcher) {
        String tempLineDirection = matcher.group("lineDirection");
        switch (tempLineDirection) {
            case "left" :
                System.out.println("left line:");
                for (int i = 1; i < 16; i++) {
                    for (String s : rowInfo[0][i]) {
                        String[] ss = s.split(" ");
                        System.out.printf("row %d: %s: %s\n", i, ss[0], ss[1]);
                    }
                }
                break;
            case "middle" :
                System.out.println("middle line:");
                for (int i = 1; i < 16; i++) {
                    for (String s : rowInfo[1][i]) {
                        String[] ss = s.split(" ");
                        System.out.printf("row %d: %s: %s\n", i, ss[0], ss[1]);
                    }
                }
                break;
            case "right" :
                System.out.println("right line:");
                for (int i = 1; i < 16; i++) {
                    for (String s : rowInfo[2][i]) {
                        String[] ss = s.split(" ");
                        System.out.printf("row %d: %s: %s\n", i, ss[0], ss[1]);
                    }
                }
                break;
            default:
                System.out.println("Incorrect line direction!");
        }
    }
    public static void cardsToPlay() {
        if (whichTurn) {
            if (!userInUse.isCardLeftNotPlayed())
                System.out.println("You can play 1 cards more!");
            else
                System.out.println("You can play 0 cards more!");
        }
        else {
            if (!userInUse2.isCardLeftNotPlayed())
                System.out.println("You can play 1 cards more!");
            else
                System.out.println("You can play 0 cards more!");
        }
    }
    public static void soldiersToMove(){
        if (whichTurn) {
            System.out.printf("You have %d moves left!\n", userInUse.getMovesLeft());
        }
        else {
            System.out.printf("You have %d moves left!\n", userInUse2.getMovesLeft());
        }
    }
    public static void moveTroop(Matcher matcher) {
        String lineDirection = matcher.group("lineDirection");
        int rowNumber = Integer.parseInt(matcher.group("rowNumber"));
        String tempDirection = matcher.group("direction");
        if(checkLineDirection(matcher)) return;
        if ((rowNumber>15 || rowNumber<1)) {
            System.out.println("Invalid row number!");
            return;
        }
        if (!(tempDirection.equals("upward") || tempDirection.equals("downward"))) {
            System.out.println("you can only move troops upward or downward!");
            return;
        }
        if (whichTurn) {
//            if (userInUse.getMovesLeft()==0) {
//                System.out.println("You are out of moves");
//                return;
//            }
            switch (lineDirection) {
                case "left" :
                    movingPart(rowNumber, tempDirection, lineDirection, 0, userInUse);
                    break;
                case "middle" :
                    movingPart(rowNumber, tempDirection, lineDirection, 1, userInUse);
                    break;
                case "right" :
                    movingPart(rowNumber, tempDirection, lineDirection, 2, userInUse);
                    break;
            }
        }
        else {
//            if (userInUse2.getMovesLeft()==0) {
//                System.out.println("You are out of moves");
//                return;
//            }
            switch (lineDirection) {
                case "left" :
                    movingPart(rowNumber, tempDirection, lineDirection, 0, userInUse2);
                    break;
                case "middle" :
                    movingPart(rowNumber, tempDirection, lineDirection, 1, userInUse2);
                    break;
                case "right" :
                    movingPart(rowNumber, tempDirection, lineDirection, 2, userInUse2);
                    break;
            }
        }
    }
    public static void movingPart(int rowNumber, String tempDirection, String lineDirection, int lineDirectionIndex, User user) {
        boolean doesTroopExist = false;
        for (String s : rowInfo[lineDirectionIndex][rowNumber]) {
            String[] ss = s.split(" ");
            if (ss[1].equals(user.getUsername()) && Integer.parseInt(ss[2])>0) {
                doesTroopExist = true;
                break;
            }
        }
        if (!doesTroopExist) {
            System.out.println("You don't have any troops in this place!");
            return;
        }
        if (user.getMovesLeft()==0) {
            System.out.println("You are out of moves!");
            return;
        }
        switch (tempDirection) {
            case "upward" :
                if (rowNumber==15) {
                    System.out.println("Invalid move!");
                    return;
                }
/*
                for (int j = 0;j < rowInfo[lineDirectionIndex][rowNumber].size();++j){
                    String s = rowInfo[lineDirectionIndex][rowNumber].get(j);
                    String[] ss = s.split(" ");
                    if (ss[1].equals(user.getUsername())) {
                        rowInfo[lineDirectionIndex][rowNumber+1].add(s);
                        rowInfo[lineDirectionIndex][rowNumber].remove(s);
                        System.out.printf("%s moved successfully to row %d in line %s\n",ss[0],rowNumber+1,lineDirection);
                        user.setMovesLeft(user.getMovesLeft()-1);
                        return;
                    }
                }
*/
                int tempp = 0;
                for (String s : rowInfo[lineDirectionIndex][rowNumber]) {
                    String[] ss = s.split(" ");
                    if (ss[1].equals(user.getUsername())) {
                        rowInfo[lineDirectionIndex][rowNumber+1].add(s);
                        rowInfo[lineDirectionIndex][rowNumber].remove(s);
//                        ss[2] = String.valueOf(-1);
//                        s = ss[0] + " " + ss[1] + " " + ss[2] + " " + ss[3];
//                        rowInfo[lineDirectionIndex][rowNumber].set(tempp,s);
                        System.out.printf("%s moved successfully to row %d in line %s\n",ss[0],rowNumber+1,lineDirection);
                        user.setMovesLeft(user.getMovesLeft()-1);
                        return;
                    }
                    tempp++;
                }
                break;
            case "downward" :
                if (rowNumber==1) {
                    System.out.println("Invalid move!");
                    return;
                }
                tempp = 0;
                for (String s : rowInfo[lineDirectionIndex][rowNumber]) {
                    String[] ss = s.split(" ");
                    if (ss[1].equals(user.getUsername())) {
                        rowInfo[lineDirectionIndex][rowNumber-1].add(s);
                        rowInfo[lineDirectionIndex][rowNumber].remove(s);
//                        ss[2] = String.valueOf(-1);
//                        s = ss[0] + " " + ss[1] + " " + ss[2] + " " + ss[3];
//                        rowInfo[lineDirectionIndex][rowNumber].set(tempp,s);
                        System.out.printf("%s moved successfully to row %d in line %s\n",ss[0],rowNumber+1,lineDirection);
                        user.setMovesLeft(user.getMovesLeft()-1);
                        return;
                    }
                    tempp++;
                }
                break;
            default:
                System.out.println("you can only move troops upward or downward!");
        }
    }

    public static void deployTroops(Matcher matcher) {
        String troopName = matcher.group("troopName");
        String lineDirection = matcher.group("lineDirection");
        int rowNumber = Integer.parseInt(matcher.group("rowNumber"));
        if(checkTroopName(matcher)) return;
        boolean t = false;
        if (whichTurn) {
            for (Card card : userInUse.getBattleDeck()) {
                if (card.getName().equals(troopName)) {
                    t = true;
                    break;
                }
            }
            if (!t) {
                System.out.printf("You don't have %s card in your battle deck!\n", troopName);
                return;
            }
            if(checkLineDirection(matcher)) return;
            if (rowNumber>15 || rowNumber<1) {
                System.out.println("Invalid row number!");
                return;
            }
            if (rowNumber > 4) {
                System.out.println("Deploy your troops near your castles!");
                return;
            }
            if (userInUse.isCardLeftNotPlayed()) {
                System.out.println("You have deployed a troop or spell this turn!");
                return;
            }
            userInUse.setCardLeftNotPlayed(true);
            int tempHitPoint = 0,tempReductionHitPoint = 0;
            if (troopName.equals("Archer")) {
                tempHitPoint = 1900;
                tempReductionHitPoint = 800;
            } else if (troopName.equals("Wizard")) {
                tempHitPoint = 3300;
                tempReductionHitPoint = 1400;
            }
            else {
                tempHitPoint = 3200;
                tempReductionHitPoint = 1100;
            }
            String tempAddition = troopName + " " + userInUse.getUsername() + " " + tempHitPoint + " " + tempReductionHitPoint;
            System.out.printf("You have deployed %s successfully!\n",troopName);
            switch (lineDirection) {
                case "left":
                    rowInfo[0][rowNumber].add(tempAddition);
                    break;
                case "middle":
                    rowInfo[1][rowNumber].add(tempAddition);
                    break;
                case "right":
                    rowInfo[2][rowNumber].add(tempAddition);
            }
        }
        else {
            for (Card card : userInUse2.getBattleDeck()) {
                if (card.getName().equals(troopName)) {
                    t = true;
                    break;
                }
            }
            if(!t) {
                System.out.printf("You don't have %s card in your battle deck!\n",troopName);
                return;
            }
            if(checkLineDirection(matcher)) return;
            if (rowNumber>15 || rowNumber<1) {
                System.out.println("Invalid row number!");
                return;
            }
            if (rowNumber < 12) {
                System.out.println("Deploy your troops near your castles!");
                return;
            }
            if (userInUse2.isCardLeftNotPlayed()) {
                System.out.println("You have deployed a troop or spell this turn!");
                return;
            }
            userInUse2.setCardLeftNotPlayed(true);
            int tempHitPoint = 0,tempReductionHitPoint = 0;
            if (troopName.equals("Archer")) {
                tempHitPoint = 1900;
                tempReductionHitPoint = 800;
            } else if (troopName.equals("Wizard")) {
                tempHitPoint = 3300;
                tempReductionHitPoint = 1400;
            }
            else {
                tempHitPoint = 3200;
                tempReductionHitPoint = 1100;
            }
            String tempAddition = troopName + " " + userInUse2.getUsername() + " " + tempHitPoint + " " + tempReductionHitPoint;
            System.out.printf("You have deployed %s successfully!\n",troopName);
            switch (lineDirection) {
                case "left" :
                    rowInfo[0][rowNumber].add(tempAddition);
                    break;
                case "middle" :
                    rowInfo[1][rowNumber].add(tempAddition);
                    break;
                case "right" :
                    rowInfo[2][rowNumber].add(tempAddition);
            }
        }
    }
    public static void deploySpell(Matcher matcher) {
        String lineDirection = matcher.group("lineDirection");
        if(checkLineDirection(matcher)) return;
        if (whichTurn) {
            boolean vojoodFireball = false;
            for (Card card : userInUse.getBattleDeck()) {
                if (card.getName().equals("Fireball")) {
                    vojoodFireball = true;
                }
            }
            if(!vojoodFireball) {
                System.out.println("You don't have Fireball card in your battle deck!");
                return;
            }
            if (userInUse.isCardLeftNotPlayed()) {
                System.out.println("You have deployed a troop or spell this turn!");
                return;
            }
//            userInUse.setCardLeftNotPlayed(true);
//            System.out.println("You have deployed Fireball successfully!");
            switch (lineDirection) {
                case "left" :
                    if (userInUse2.getHitPointOfKala()[0]<=0) {
                        System.out.println("This castle is already destroyed!");
                        return;
                    }
                    userInUse.setCardLeftNotPlayed(true);
                    System.out.println("You have deployed Fireball successfully!");
                    userInUse2.setHitPointOfKala(0,userInUse2.getHitPointOfKala()[0]-1400);
                    break;
                case "middle" :
                    if (userInUse2.getHitPointOfKala()[1]<=0) {
                        System.out.println("This castle is already destroyed!");
                        return;
                    }
                    userInUse.setCardLeftNotPlayed(true);
                    System.out.println("You have deployed Fireball successfully!");
                    userInUse2.setHitPointOfKala(1,userInUse2.getHitPointOfKala()[1]-1400);
                    break;
                case "right" :
                    if (userInUse2.getHitPointOfKala()[2]<=0) {
                        System.out.println("This castle is already destroyed!");
                        return;
                    }
                    userInUse.setCardLeftNotPlayed(true);
                    System.out.println("You have deployed Fireball successfully!");
                    userInUse2.setHitPointOfKala(2,userInUse2.getHitPointOfKala()[2]-1400);
                    break;
            }
        }
        else {
            boolean vojoodFireball = false;
            for (Card card : userInUse2.getBattleDeck()) {
                if (card.getName().equals("Fireball")) {
                    vojoodFireball = true;
                }
            }
            if(!vojoodFireball) {
                System.out.println("You don't have Fireball card in your battle deck!");
                return;
            }
            if (userInUse2.isCardLeftNotPlayed()) {
                System.out.println("You have deployed a troop or spell this turn!");
                return;
            }
//            userInUse2.setCardLeftNotPlayed(true);
//            System.out.println("You have deployed Fireball successfully!");
            switch (lineDirection) {
                case "left" :
                    if (userInUse.getHitPointOfKala()[0]<=0) {
                        System.out.println("This castle is already destroyed!");
                        return;
                    }
                    userInUse2.setCardLeftNotPlayed(true);
                    System.out.println("You have deployed Fireball successfully!");
                    userInUse.setHitPointOfKala(0,userInUse.getHitPointOfKala()[0]-1400);
                    break;
                case "middle" :
                    if (userInUse.getHitPointOfKala()[1]<=0) {
                        System.out.println("This castle is already destroyed!");
                        return;
                    }
                    userInUse2.setCardLeftNotPlayed(true);
                    System.out.println("You have deployed Fireball successfully!");
                    userInUse.setHitPointOfKala(1,userInUse.getHitPointOfKala()[1]-1400);
                    break;
                case "right" :
                    if (userInUse.getHitPointOfKala()[2]<=0) {
                        System.out.println("This castle is already destroyed!");
                        return;
                    }
                    userInUse2.setCardLeftNotPlayed(true);
                    System.out.println("You have deployed Fireball successfully!");
                    userInUse.setHitPointOfKala(2,userInUse.getHitPointOfKala()[2]-1400);
                    break;
            }
        }
    }
    public static void nextTurn() {
        if (whichTurn) {
            whichTurn = false;
            System.out.printf("Player %s is now playing!\n",userInUse2.getUsername());
        }
        else {
            turnsCount--;
            whichTurn = true;
            userInUse.setCardLeftNotPlayed(false);
            userInUse2.setCardLeftNotPlayed(false);
            userInUse.setMovesLeft(3);
            userInUse2.setMovesLeft(3);
            for (int i = 0; i < 3; i++) {
                for (int j = 1; j < 16; j++) {
                    int sarbaz1 = 0,sarbaz2 = 0,hitPointDamage1 = 0,hitPointDamage2 = 0,hitPointDamage;
                    for (String s : rowInfo[i][j]) {
                        String[] ss = s.split(" ");
                        if (ss[1].equals(userInUse.getUsername())) {
                            sarbaz1++;
                            hitPointDamage1 += Integer.parseInt(ss[3]);
                        }
                        if (ss[2].equals(userInUse2.getUsername())) {
                            sarbaz2++;
                            hitPointDamage2 += Integer.parseInt(ss[3]);
                        }
                    }
                    if (j==1) {
                        int t = 0;
                        for (String s : rowInfo[i][j]) {
                            String[] ss = s.split(" ");
                            if (ss[1].equals(userInUse2.getUsername()) && Integer.parseInt(ss[2]) > 0) {
                                userInUse.setHitPointOfKala(i,
                                        userInUse.getHitPointOfKala()[i]-Integer.parseInt(ss[3]));

                                ss[2] = String.valueOf(Integer.parseInt(ss[2]) - 450*userInUse.getLevel());
                                s = ss[0] + " " + ss[1] + " " + ss[2] + " " + ss[3];
                                rowInfo[i][j].set(t,s);
                            }
                            t++;
                        }
                    }
                    if (j==15) {
                        int t = 0;
                        for (String s : rowInfo[i][j]) {
                            String[] ss = s.split(" ");
                            if (ss[1].equals(userInUse.getUsername()) && Integer.parseInt(ss[2]) > 0) {
                                userInUse2.setHitPointOfKala(i,
                                        userInUse2.getHitPointOfKala()[i]-Integer.parseInt(ss[3]));
                                ss[2] = String.valueOf(Integer.parseInt(ss[2]) - 450*userInUse2.getLevel());
                                s = ss[0] + " " + ss[1] + " " + ss[2] + " " + ss[3];
                                rowInfo[i][j].set(t,s);
                            }
                            t++;
                        }
                    }
                    if (j!=1 && j!=15 && (sarbaz2==0 || sarbaz1==0))
                        continue;
                    else if ((j==1 && sarbaz2==0) || (j==15 && sarbaz1==0))
                        continue;
                    else if (j==1 && sarbaz2!=0) {// && sarbaz1==0
                        for (String s : rowInfo[i][j]) {
                            String[] ss = s.split(" ");
                            int hitDamage = Integer.parseInt(ss[3]);
//                            userInUse.setHitPointOfKala(i,
//                                    userInUse.getHitPointOfKala()[i]-hitDamage);
                        }
                    }
                    else if (j==15 && sarbaz1!=0) {// && sarbaz2==0
                        for (String s : rowInfo[i][j]) {
                            String[] ss = s.split(" ");
                            int hitDamage = Integer.parseInt(ss[3]);
//                            userInUse2.setHitPointOfKala(i,
//                                    userInUse2.getHitPointOfKala()[i]-hitDamage);
                        }
                    }
                    else {
                        if (hitPointDamage1>hitPointDamage2) {
                            hitPointDamage = hitPointDamage1-hitPointDamage2;
                            //hitPointDamage /= sarbaz2;
                            int t = 0;
                            for (String s : rowInfo[i][j]) {
                                String[] ss = s.split(" ");
                                if (ss[1].equals(userInUse2.getUsername())) {
                                    ss[2] = String.valueOf(Integer.parseInt(ss[2]) - hitPointDamage);
                                    s = ss[0] + " " + ss[1] + " " + ss[2] + " " + ss[3];
                                    rowInfo[i][j].set(t,s);
                                }
                                t++;
                            }
                        }
                        if (hitPointDamage2>hitPointDamage1) {
                            hitPointDamage = hitPointDamage2-hitPointDamage1;
                            //hitPointDamage /= sarbaz1;
                            int t = 0;
                            for (String s : rowInfo[i][j]) {
                                String[] ss = s.split(" ");
                                if (ss[1].equals(userInUse.getUsername())) {
                                    ss[2] = String.valueOf(Integer.parseInt(ss[2]) - hitPointDamage);
                                    s = ss[0] + " " + ss[1] + " " + ss[2] + " " + ss[3];
                                    rowInfo[i][j].set(t,s);
                                }
                                t++;
                            }
                        }
                    }
                    for (String s : rowInfo[i][j]) {
                        String[] ss = s.split(" ");
                        if (Integer.parseInt(ss[2]) <= 0) {
                            //rowInfo[i][j].remove(s);
                        }
                    }
                }
            }
            for (int i = 0; i < 3; i++) {
                if (userInUse.getHitPointOfKala()[i]<0) {
                    userInUse.setHitPointOfKala(i,0);
                }
                if (userInUse2.getHitPointOfKala()[i]<0) {
                    userInUse2.setHitPointOfKala(i,0);
                }
            }
            int[] kala1 = userInUse.getHitPointOfKala();
            int[] kala2 = userInUse2.getHitPointOfKala();
            if (kala1[0] <= 0 && kala1[1] <= 0 && kala1[2] <= 0) {
                if (kala2[0] <= 0 && kala2[1] <= 0 && kala2[2] <= 0) {
                    System.out.println("Game has ended. Result: Tie");
                    return;
                }
                else {
                    System.out.println("Game has ended. Winner: " + userInUse2.getUsername());
                    int experience = kala2[0] + kala2[1] + kala2[2];
                    while (userInUse2.getExperience() + experience > userInUse2.getLevel()*userInUse2.getLevel()*150) {
                        experience -= userInUse2.getLevel()*userInUse2.getLevel()*150;
                        userInUse2.setLevel(userInUse2.getLevel()+1);
                    }
                    userInUse2.setExperience(userInUse2.getExperience()+ experience);
                    if (kala1[0]<=0)
                        userInUse2.setGold(userInUse2.getGold()+20);
                    if (kala1[1]<=0)
                        userInUse2.setGold(userInUse2.getGold()+20);
                    if (kala1[2]<=0)
                        userInUse2.setGold(userInUse2.getGold()+20);
                    return;
                }
            }
            else if (kala2[0] <= 0 && kala2[1] <= 0 && kala2[2] <= 0) {
                System.out.println("Game has ended. Winner: " + userInUse.getUsername());
                int experience = kala1[0] + kala1[1] + kala1[2];
                while (userInUse.getExperience() + experience > userInUse.getLevel()*userInUse.getLevel()*150) {
                    experience -= userInUse.getLevel()*userInUse.getLevel()*150;
                    userInUse.setLevel(userInUse.getLevel()+1);
                }
                userInUse.setExperience(userInUse.getExperience()+ experience);
                if (kala2[0]<=0)
                    userInUse.setGold(userInUse.getGold()+20);
                if (kala2[1]<=0)
                    userInUse.setGold(userInUse.getGold()+20);
                if (kala2[2]<=0)
                    userInUse.setGold(userInUse.getGold()+20);
                return;
            }
            else if (turnsCount==0) {
                int kalaKurali1 = kala1[0] + kala1[1] + kala1[2];
                int kalaKurali2 = kala2[0] + kala2[1] + kala2[2];
                int experience = kala1[0] + kala1[1] + kala1[2];
                while (userInUse.getExperience() + experience >= userInUse.getLevel()*userInUse.getLevel()*150) {
                    experience -= userInUse.getLevel()*userInUse.getLevel()*150;
                    userInUse.setLevel(userInUse.getLevel()+1);
                }
                userInUse.setExperience(userInUse.getExperience()+ experience);
                if (kala2[0]<=0)
                    userInUse.setGold(userInUse.getGold()+20);
                if (kala2[1]<=0)
                    userInUse.setGold(userInUse.getGold()+20);
                if (kala2[2]<=0)
                    userInUse.setGold(userInUse.getGold()+20);
                experience = kala2[0] + kala2[1] + kala2[2];
                while (userInUse2.getExperience() + experience >= userInUse2.getLevel()*userInUse2.getLevel()*150) {
                    experience -= userInUse2.getLevel()*userInUse2.getLevel()*150;
                    userInUse2.setLevel(userInUse2.getLevel()+1);
                }
                userInUse2.setExperience(userInUse2.getExperience()+ experience);
                if (kala1[0]<=0)
                    userInUse2.setGold(userInUse2.getGold()+20);
                if (kala1[1]<=0)
                    userInUse2.setGold(userInUse2.getGold()+20);
                if (kala1[2]<=0)
                    userInUse2.setGold(userInUse2.getGold()+20);
                if (kalaKurali1 > kalaKurali2) {
                    System.out.println("Game has ended. Winner: " + userInUse.getUsername());
                    return;
                }
                else if (kalaKurali2 > kalaKurali1) {
                    System.out.println("Game has ended. Winner: " + userInUse2.getUsername());
                    return;
                }
                else {
                    System.out.println("Game has ended. Result: Tie");
                    return;
                }
            }
            System.out.printf("Player %s is now playing!\n",userInUse.getUsername());
        }
    }

}

