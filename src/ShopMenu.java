import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopMenu extends Menu {
    public ShopMenu(ArrayList<User> users, User userInUse, int index) {
        super(users, userInUse, index);
        userArrayList = users;
        RegistryMenu.userInUse = userInUse;
        userInUseIndex = index;
    }

    public static boolean run() {
        Pattern[] patterns = new Pattern[6];
        patterns[0] = Pattern.compile("exit");
        patterns[1] = Pattern.compile("show current menu");
        patterns[2] = Pattern.compile("buy card (?<troopOrSpellName>[\\S\\s]+)");
        patterns[3] = Pattern.compile("sell card (?<cardName>[\\S]+)");
        patterns[5] = Pattern.compile("back");
        input = scanner.next();
        while (!input.equals("exit")) {
            boolean ejra = false;
            input += scanner.nextLine();
            if (input.matches(String.valueOf(patterns[0]))) {
                Matcher matcher = getCommandMatcher(input, String.valueOf(patterns[0]));
                matcher.find();
                return false;
            }
            else if (input.matches(String.valueOf(patterns[1]))) {
                Matcher matcher = getCommandMatcher(input, String.valueOf(patterns[1]));
                matcher.find();
                ejra = true;
                System.out.println("Shop Menu");
            }
            else if (input.matches(String.valueOf(patterns[2]))) {
                Matcher matcher = getCommandMatcher(input, String.valueOf(patterns[2]));
                matcher.find();
                buyCard(matcher);
                ejra = true;
            }
            else if (input.matches(String.valueOf(patterns[3]))) {
                Matcher matcher = getCommandMatcher(input, String.valueOf(patterns[3]));
                matcher.find();
                sellCard(matcher);
                ejra = true;
            }
            else if (input.matches(String.valueOf(patterns[5]))) {
                Matcher matcher = getCommandMatcher(input, String.valueOf(patterns[5]));
                matcher.find();
                System.out.println("Entered main menu!");
                return true;
            }
            if (!ejra) {
                System.out.println("Invalid command!");
            }
            input = scanner.next();
        }
        return false;

    }
    public static void buyCard(Matcher matcher) {
        String tempTroopOrSpellName = matcher.group("troopOrSpellName");
        if (checkCardsName(tempTroopOrSpellName)) {
            System.out.println("Invalid card name!");
            return;
        }
        for (Card card : userInUse.getCardArrayList()) {
            if (card.getName().equals(tempTroopOrSpellName)) {
                System.out.println("You have this card!");
                return;
            }
        }
        if (tempTroopOrSpellName.equals("Fireball")) {
            if (userInUse.getGold()-80<0) {
                System.out.printf("Not enough gold to buy %s!\n",tempTroopOrSpellName);
            }
            else {
                System.out.printf("Card %s bought successfully!\n",tempTroopOrSpellName);
                userInUse.buyCard(tempTroopOrSpellName);
            }
        }
        if (tempTroopOrSpellName.equals("Archer")) {
            if (userInUse.getGold()-80<0) {
                System.out.printf("Not enough gold to buy %s!\n",tempTroopOrSpellName);
            }
            else {
                System.out.printf("Card %s bought successfully!\n",tempTroopOrSpellName);
                userInUse.buyCard(tempTroopOrSpellName);
            }
        }
        if (tempTroopOrSpellName.equals("Dragon")) {
            if (userInUse.getGold()-160<0) {
                System.out.printf("Not enough gold to buy %s!\n",tempTroopOrSpellName);
            }
            else {
                System.out.printf("Card %s bought successfully!\n",tempTroopOrSpellName);
                userInUse.buyCard(tempTroopOrSpellName);
            }
        }
        if (tempTroopOrSpellName.equals("Wizard")) {
            if (userInUse.getGold()-140<0) {
                System.out.printf("Not enough gold to buy %s!\n",tempTroopOrSpellName);
            }
            else {
                System.out.printf("Card %s bought successfully!\n",tempTroopOrSpellName);
                userInUse.buyCard(tempTroopOrSpellName);
            }
        }
    }
    public static void sellCard(Matcher matcher) {
        String tempTroopOrSpellName = matcher.group("cardName");
        if (checkCardsName(tempTroopOrSpellName)) {
            System.out.println("Invalid card name!");
            return;
        }
        boolean vojood = false;
        for (Card card : userInUse.getCardArrayList()) {
            if (card.getName().equals(tempTroopOrSpellName)) {
                vojood = true;
            }
        }
        if (!vojood) {
            System.out.println("You don't have this card!");
            return;
        }
        for (Card card : userInUse.getBattleDeck()) {
            if (card.getName().equals(tempTroopOrSpellName)) {
                System.out.println("You cannot sell a card from your battle deck!");
                return;
            }
        }
        userInUse.sellCard(tempTroopOrSpellName);
        if (tempTroopOrSpellName.equals("Fireball")) {
            System.out.printf("Card %s sold successfully!\n",tempTroopOrSpellName);
            userInUse.setGold(userInUse.getGold()+60);
        }
        if (tempTroopOrSpellName.equals("Archer")) {
            System.out.printf("Card %s sold successfully!\n",tempTroopOrSpellName);
            userInUse.setGold(userInUse.getGold()+60);
        }
        if (tempTroopOrSpellName.equals("Dragon")) {
            System.out.printf("Card %s sold successfully!\n",tempTroopOrSpellName);
            userInUse.setGold(userInUse.getGold()+120);
        }
        if (tempTroopOrSpellName.equals("Wizard")) {
            System.out.printf("Card %s sold successfully!\n",tempTroopOrSpellName);
            userInUse.setGold(userInUse.getGold()+105);
        }
    }
}