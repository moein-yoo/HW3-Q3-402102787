
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Menu {
    static ArrayList<User> userArrayList = new ArrayList<>();
    static User userInUse;
    static int userInUseIndex;
    static Scanner scanner = new Scanner(System.in);
    static String input;
    public Menu(ArrayList<User> users , User userInUse,int index)  {
        userArrayList = users;
        RegistryMenu.userInUse = userInUse;
        userInUseIndex = index;
    }
    public static Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
    public static boolean isPasswordCorrect(String tempPassword) {
        boolean digLet=false,upLet=false,loLet=false,charLet=false,cor=true;
        if (tempPassword.charAt(0) >= '0' && tempPassword.charAt(0) <= '9')
            return false;
        if (tempPassword.length()<8 || tempPassword.length()>20)
            return false;
        for (int i = 0; i < tempPassword.length(); i++) {
            if (tempPassword.charAt(i)==' ')
                return false;
            if (tempPassword.charAt(i) >= '0' && tempPassword.charAt(i) <= '9')
                digLet = true;
            else if (tempPassword.charAt(i) >= 'A' && tempPassword.charAt(i) <= 'Z')
                upLet = true;
            else if (tempPassword.charAt(i) >= 'a' && tempPassword.charAt(i) <= 'z')
                loLet = true;
            else if (tempPassword.charAt(i)== '*' || tempPassword.charAt(i)=='&'
                    || tempPassword.charAt(i)== '%' || tempPassword.charAt(i)== '$'
                    || tempPassword.charAt(i)== '#' || tempPassword.charAt(i)== '^'
                    || tempPassword.charAt(i)== '@' || tempPassword.charAt(i)== '!')
                charLet = true;
        }
        if(!digLet || !upLet || !loLet || !charLet)
            return false;
        return true;
    }
    public static ArrayList<User> sortUserList(ArrayList<User> users) {
        ArrayList<User> userArrayList1 = new ArrayList<>(users);
        userArrayList1.sort((u1,u2) ->{
            int x = u1.getLevel()-u2.getLevel();
            if (x!=0)
                return -x;
            int y = u1.getExperience()-u2.getExperience();
            if (y!=0)
                return -y;
            return u1.getUsername().compareTo(u2.getUsername());
        });
        return userArrayList1;
    }
    static boolean checkCardsName(String cardName) {
        return (!cardName.equals("Fireball") && !cardName.equals("Archer")
                && !cardName.equals("Dragon") && !cardName.equals("Wizard"));
    }

    static boolean checkTroopName(Matcher matcher) {
        String troopName = matcher.group("troopName");
        if (!(troopName.equals("Wizard") || troopName.equals("Archer") || troopName.equals("Dragon"))) {
            System.out.println("Invalid troop name!");
            return true;
        }
        return false;
    }
    static boolean checkLineDirection(Matcher matcher) {
        String lineDirection = matcher.group("lineDirection");
        if (!(lineDirection.equals("left") || lineDirection.equals("middle") || lineDirection.equals("right"))) {
            System.out.println("Incorrect line direction!");
            return true;
        }
        return false;
    }
}
