import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistryMenu extends Menu{
    public RegistryMenu(ArrayList<User> users , User userInUse,int index) {
        super(users,userInUse,index);
        userArrayList = users;
        RegistryMenu.userInUse = userInUse;
        userInUseIndex = index;
    }
    public void run() {
        Pattern[] patterns = new Pattern[6];
        patterns[0] = Pattern.compile("Exit");
        patterns[1] = Pattern.compile("show current menu");
        patterns[2] = Pattern.compile("register username (?<username>[\\S\\s]+) password (?<password>[\\S\\s]+)");
        patterns[3] = Pattern.compile("login username (?<username>[\\S\\s]+) password (?<password>[\\S\\s]+)");
        input = scanner.next();
        while (!input.equals("Exit")){
            boolean ejra = false;
            input += scanner.nextLine();
            if(input.matches(String.valueOf(patterns[0]))) {
                Matcher matcher = getCommandMatcher(input, String.valueOf(patterns[0]));
                matcher.find();
                return;
            }
            else if (input.matches(String.valueOf(patterns[1]))) {
                Matcher matcher = getCommandMatcher(input, String.valueOf(patterns[0]));
                matcher.find();
                ejra = true;
                System.out.println("Register/Login Menu");
            }
            else if (input.matches(String.valueOf(patterns[2]))) {
                Matcher matcher = getCommandMatcher(input,String.valueOf(patterns[2]));
                matcher.find();
                signin(matcher);
                ejra = true;
            }
            else if (input.matches(String.valueOf(patterns[3]))) {
                Matcher matcher = getCommandMatcher(input,String.valueOf(patterns[3]));
                matcher.find();
                if (login(matcher)) {
                    MainMenu mainMenu = new MainMenu(userArrayList,userInUse,userInUseIndex);
                    if (!mainMenu.run())
                        return;
                }
                ejra = true;
            }
            if(!ejra) {
                System.out.println("Invalid command!");
            }
            input = scanner.next();
        }

    }
    public static void signin(Matcher matcher) {
        String tempUsername = matcher.group("username");
        String tempPassword = matcher.group("password");
        if (isUsernameCorrect(tempUsername)) {
            System.out.println("Incorrect format for username!");
        }
        else if (!isPasswordCorrect(tempPassword)) {
            System.out.println("Incorrect format for password!");
        }
        else if (isUserValid(tempUsername)) {
            System.out.println("Username already exists!");
        }
        else {
            User user = new User(tempUsername,tempPassword);
            userArrayList.add(user);
            System.out.printf("User %s created successfully!\n",tempUsername);
        }
    }
    public static boolean login(Matcher matcher) {
        String tempUsername = matcher.group("username");
        String tempPassword = matcher.group("password");
        if (isUsernameCorrect(tempUsername)) {
            System.out.println("Incorrect format for username!");
        }
        else if (!isPasswordCorrect(tempPassword)) {
            System.out.println("Incorrect format for password!");
        }
        else if (!isUserValid(tempUsername)) {
            System.out.println("Username doesn't exist!");
        }
        else {
            for (int i = 0; i < userArrayList.size(); i++) {
                if (userArrayList.get(i).getUsername().equals(tempUsername)) {
                    if (!userArrayList.get(i).getPassword().equals(tempPassword)) {
                        System.out.println("Password is incorrect!");
                    }
                    else {
                        userInUse = userArrayList.get(i);
                        userInUseIndex = i;
                        System.out.printf("User %s logged in!\n",tempUsername);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static boolean isUsernameCorrect(String tempUsername) {
        for (int i = 0; i < tempUsername.length(); i++) {
            if (!((tempUsername.charAt(i) > 64 && tempUsername.charAt(i) < 91)
                    || (tempUsername.charAt(i) > 96 && tempUsername.charAt(i) < 123))) {
                return true;
            }
        }
        return false;
    }
    public static boolean isUserValid(String tempUsername) {
        for (User user : userArrayList) {
            if (user.getUsername().equals(tempUsername))
                return true;
        }
        return false;
    }
}
