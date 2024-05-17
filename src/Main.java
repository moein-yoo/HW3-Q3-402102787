import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        ArrayList<User> userArrayList = new ArrayList<>();
        User userInUse = new User("a","b");
        int userInUseIndex = 0;
        RegistryMenu registryMenu = new RegistryMenu(userArrayList,userInUse,userInUseIndex);
        registryMenu.run();
    }
}