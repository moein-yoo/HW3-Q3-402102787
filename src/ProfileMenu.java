import java.util.ArrayList;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileMenu extends Menu {
    public ProfileMenu (ArrayList<User> users , User userInUse, int index) {
        super(users,userInUse,index);
        userArrayList = users;
        RegistryMenu.userInUse = userInUse;
        userInUseIndex = index;
    }
    public static boolean run() {
        Pattern[] patterns = new Pattern[10];
        patterns[0] = Pattern.compile("Exit");
        patterns[1] = Pattern.compile("show current menu");
        patterns[2] = Pattern.compile("change password old password (?<oldPassword>[\\S\\s]+) new password (?<newPassword>[\\S\\s]+)");
        patterns[3] = Pattern.compile("Info");
        patterns[4] = Pattern.compile("remove from battle deck (?<troopOrSpellName>[\\S\\s]+)");
        patterns[5] = Pattern.compile("back");
        patterns[6] = Pattern.compile("add to battle deck (?<troopOrSpellName>[\\S\\s]+)");
        patterns[7] = Pattern.compile("show battle deck");
        input = scanner.next();
        while (!input.equals("Exit")){
            boolean ejra = false;
            input += scanner.nextLine();
            if(input.matches(String.valueOf(patterns[0]))) {
                Matcher matcher = getCommandMatcher(input, String.valueOf(patterns[0]));
                matcher.find();
                return false;
            }
            else if (input.matches(String.valueOf(patterns[1]))) {
                Matcher matcher = getCommandMatcher(input, String.valueOf(patterns[1]));
                matcher.find();
                ejra = true;
                System.out.println("Profile Menu");
            }
            else if (input.matches(String.valueOf(patterns[2]))) {
                Matcher matcher = getCommandMatcher(input, String.valueOf(patterns[2]));
                matcher.find();
                changePassword(matcher);
                ejra = true;
            }
            else if (input.matches(String.valueOf(patterns[3]))) {
                Matcher matcher = getCommandMatcher(input, String.valueOf(patterns[3]));
                matcher.find();
                System.out.printf("username: %s\n",userInUse.getUsername());
                System.out.printf("password: %s\n",userInUse.getPassword());
                System.out.printf("level: %d\n",userInUse.getLevel());
                System.out.printf("experience: %d\n",userInUse.getExperience());
                System.out.printf("gold: %d\n",userInUse.getGold());
                ArrayList<User> userArrayList1 = sortUserList(userArrayList);
                int t = 1;
                for (User user : userArrayList1) {
                    if (user.getUsername().equals(userInUse.getUsername())) {
                        System.out.printf("rank: %d\n",t);
                        break;
                    }
                    t++;
                }
                //username: <username>
                //password: <password>
                //level: <level>
                //experience: <experience>
                //gold: <gold>
                //rank: <rank>
                ejra = true;
            }
            else if (input.matches(String.valueOf(patterns[4]))) {
                Matcher matcher = getCommandMatcher(input, String.valueOf(patterns[4]));
                matcher.find();
                ejra = true;
                removeFromBattleDeck(matcher);
            }
            else if (input.matches(String.valueOf(patterns[5]))) {
                Matcher matcher = getCommandMatcher(input, String.valueOf(patterns[5]));
                matcher.find();
                System.out.println("Entered main menu!");
                return true;
            }
            else if (input.matches(String.valueOf(patterns[6]))) {
                Matcher matcher = getCommandMatcher(input,String.valueOf(patterns[6]));
                matcher.find();
                addToBattleDeck(matcher);
                ejra = true;
            }
            else if (input.matches(String.valueOf(patterns[7]))) {
                Matcher matcher = getCommandMatcher(input,String.valueOf(patterns[7]));
                matcher.find();
                showBattleDeck();
                ejra = true;
            }
            if(!ejra) {
                System.out.println("Invalid command!");
            }
            input = scanner.next();
        }
        return false;
    }
    static void changePassword(Matcher matcher) {
        String tempOldPassword = matcher.group("oldPassword");
        String tempNewPassword = matcher.group("newPassword");
        if (!userInUse.getPassword().equals(tempOldPassword)) {
            System.out.println("Incorrect password!");
        }
        else if (!isPasswordCorrect(tempNewPassword)) {
            System.out.println("Incorrect format for new password!");
        }
        else {
            System.out.println("Password changed successfully!");
            userInUse.setPassword(tempNewPassword);
        }
    }
    static boolean deleteAccount(Matcher matcher) {
        String tempPassword = matcher.group("currentPassword");
        if (userInUse.getPassword().equals(tempPassword)) {
            userArrayList.remove(userInUse);
            System.out.println("account deleted!");
            return true;
        }
        else {
            System.out.println("password is incorrect!");
            return false;
        }
    }
    static void removeFromBattleDeck(Matcher matcher) {
        String tempTroopOrSpellName = matcher.group("troopOrSpellName");
        if (checkCardsName(tempTroopOrSpellName)) {
            System.out.println("Invalid card name!");
            return;
        }
        int cardIndex = 0;
        boolean isCardValid = false;
        for (Card card : userInUse.getBattleDeck()) {
            if (card.getName().equals(tempTroopOrSpellName)) {
                isCardValid = true;
                break;
            }
            cardIndex++;
        }
        if (!isCardValid) {
            System.out.println("This card isn't in your battle deck!");
            return;
        }
        if (userInUse.getBattleDeck().size()==1) {
            System.out.println("Invalid action: your battle deck will be empty!");
            return;
        }
        userInUse.removeFromBattleDeck(cardIndex);
        System.out.printf("Card %s removed successfully!\n",tempTroopOrSpellName);
    }
    static void addToBattleDeck(Matcher matcher) {
        String tempTroopOrSpellName = matcher.group("troopOrSpellName");
        if (checkCardsName(tempTroopOrSpellName)) {
            System.out.println("Invalid card name!");
            return;
        }
        int cardIndex = 0;
        boolean isCardValid = false;
        for (Card card : userInUse.getCardArrayList()) {
            if (card.getName().equals(tempTroopOrSpellName)) {
                isCardValid = true;
                break;
            }
            cardIndex++;
        }
        if (!isCardValid) {
            System.out.println("You don't have this card!");
            return;
        }
        for (Card card : userInUse.getBattleDeck()) {
            if (card.getName().equals(tempTroopOrSpellName)) {
                System.out.println("This card is already in your battle deck!");
                return;
            }
        }
        if (userInUse.getBattleDeck().size()==3) {
            System.out.println("Invalid action: your battle deck is full!");
            return;
        }
        Card card = new Card(tempTroopOrSpellName);
        userInUse.addToBattleDeck(card);
        System.out.printf("Card %s added successfully!\n",tempTroopOrSpellName);
    }
    static void showBattleDeck() {
        userInUse.getBattleDeck().sort(Comparator.comparing(Card::getName));
        for (Card card : userInUse.getBattleDeck()) {
            System.out.println(card.getName());
        }
    }
}
