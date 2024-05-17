import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu extends Menu{
    public MainMenu (ArrayList<User> users , User userInUse,int index) {
        super(users,userInUse,index);
        userArrayList = users;
        RegistryMenu.userInUse = userInUse;
        userInUseIndex = index;
    }
    public boolean run() {
        Pattern[] patterns = new Pattern[10];
        patterns[0] = Pattern.compile("Exit");
        patterns[1] = Pattern.compile("^show current menu$");
        patterns[2] = Pattern.compile("start game turns count (?<turnsCount>[\\S\\s]+) username (?<username>[\\S\\s]+)");
        patterns[3] = Pattern.compile("(?<menuName>[\\S\\s]+) menu");
        patterns[4] = Pattern.compile("list of users");
        patterns[5] = Pattern.compile("scoreboard");
        patterns[6] = Pattern.compile("logout");
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
                System.out.println("Main Menu");
            }
            else if (input.matches(String.valueOf(patterns[2]))) {
                Matcher matcher = getCommandMatcher(input, String.valueOf(patterns[2]));
                matcher.find();
                ejra = true;
                startGame(matcher);
            }
            else if (input.matches(String.valueOf(patterns[3]))) {
                Matcher matcher = getCommandMatcher(input, String.valueOf(patterns[3]));
                matcher.find();
                ejra = true;
                if(!enterMenu(matcher))
                    return false;
                boolean t = false;
                for (User user : userArrayList) {
                    if(userInUse==user){
                        t = true;
                        break;
                    }
                }
                if(!t)
                    return true;
            }
            else if (input.matches(String.valueOf(patterns[4]))) {
                Matcher matcher = getCommandMatcher(input,String.valueOf(patterns[4]));
                matcher.find();
                userList();
                ejra = true;
            }
            else if (input.matches(String.valueOf(patterns[5]))) {
                Matcher matcher = getCommandMatcher(input,String.valueOf(patterns[5]));
                matcher.find();
                scoreboard();
                ejra = true;
            }
            else if (input.matches(String.valueOf(patterns[6]))) {
                Matcher matcher = getCommandMatcher(input,String.valueOf(patterns[6]));
                matcher.find();
                System.out.printf("User %s logged out successfully!\n",userInUse.getUsername());
                userArrayList.set(userInUseIndex,userInUse);
                return true;
            }
            if(!ejra) {
                System.out.println("Invalid command!");
            }
            input = scanner.next();
        }
        return false;
    }
    public static void startGame(Matcher matcher) {
        String tempUsername = matcher.group("username");
        int turnsCount = Integer.parseInt(matcher.group("turnsCount"));
        if (!(turnsCount>=5 && turnsCount<=30)) {
            System.out.println("Invalid turns count!");
            return;
        }
        if (RegistryMenu.isUsernameCorrect(tempUsername)) {
            System.out.println("Incorrect format for username!");
            return;
        }
        boolean t = false;
        int index = 0;
        for (User user : userArrayList) {
            if (user.getUsername().equals(tempUsername)) {
                t  = true;
                break;
            }
            index++;
        }
        if (!t) {
            System.out.println("Username doesn't exist!");
            return;
        }
        GameControl gameControl = new GameControl(userArrayList,userInUse,userInUseIndex);
        gameControl.run(userArrayList.get(index),index,turnsCount);
    }
    public static boolean enterMenu(Matcher matcher) {
        String menuName = matcher.group("menuName");
        if(menuName.equals("shop")) {
            System.out.println("Entered shop menu!");
            ShopMenu shopMenu = new ShopMenu(userArrayList,userInUse,userInUseIndex);
            return ShopMenu.run();
        }
        else if(menuName.equals("profile")) {
            System.out.println("Entered profile menu!");
            ProfileMenu profileMenu = new ProfileMenu(userArrayList,userInUse,userInUseIndex);
            return ProfileMenu.run();
        }
        else {
            System.out.println("invalid command");
        }
        return true;
    }
    public static void userList() {
        for (int i = 0; i < userArrayList.size(); i++) {
            System.out.printf("user %d: %s\n",i+1,userArrayList.get(i).getUsername());
        }
    }
    public static void scoreboard() {
        ArrayList<User> userArrayList1 = sortUserList(userArrayList);
        int t = 0;
        for (User user : userArrayList1) {
            t++;
            System.out.printf("%d- username: %s level: %d experience: %d\n", t, user.getUsername(), user.getLevel(), user.getExperience());
            if (t==5)
                return;
        }
    }

}
