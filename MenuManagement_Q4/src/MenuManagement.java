import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class MenuManagement {

    private List<MenuItem> menuItems;

    public MenuManagement() {
        this.menuItems = new ArrayList<>();
    }

    // Add a new item to the menu
    public void addItem(MenuItem item) {
        menuItems.add(item);
    }

    // Sort by category
    public void sortByCategory() {
        menuItems.sort(Comparator.comparing(MenuItem::getCategory));
    }

    // Sort by price
    public void sortByPrice() {
        menuItems.sort(Comparator.comparingDouble(MenuItem::getPrice));
    }

    // Sort by name
    public void sortByName() {
        menuItems.sort(Comparator.comparing(MenuItem::getName));
    }

    // Display all items
    public void displayMenu() {
        for (MenuItem item : menuItems) {
            System.out.println(item);
        }
    }

    public static void main(String[] args) {
        MenuManagement menuManagement = new MenuManagement();
        Scanner scanner = new Scanner(System.in);

        // Add sample items
        menuManagement.addItem(new MenuItem("Burger", "Main Course", 5.99));
        menuManagement.addItem(new MenuItem("Salad", "Appetizer", 3.49));
        menuManagement.addItem(new MenuItem("Coffee", "Beverage", 2.99));
        menuManagement.addItem(new MenuItem("Pizza", "Main Course", 8.99));
        menuManagement.addItem(new MenuItem("Soup", "Appetizer", 4.99));

        // Display menu and provide options for sorting
        while (true) {
            System.out.println("\nMenu Management System");
            System.out.println("1. Display Menu");
            System.out.println("2. Sort by Category");
            System.out.println("3. Sort by Price");
            System.out.println("4. Sort by Name");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("\nMenu Items:");
                    menuManagement.displayMenu();
                    break;
                case 2:
                    menuManagement.sortByCategory();
                    System.out.println("Sorted by Category:");
                    menuManagement.displayMenu();
                    break;
                case 3:
                    menuManagement.sortByPrice();
                    System.out.println("Sorted by Price:");
                    menuManagement.displayMenu();
                    break;
                case 4:
                    menuManagement.sortByName();
                    System.out.println("Sorted by Name:");
                    menuManagement.displayMenu();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
