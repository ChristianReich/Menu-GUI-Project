package cp213;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Wraps around an Order object to ask for MenuItems and quantities.
 *
 * @author your name here
 * @author Abdul-Rahman Mawlood-Yunis
 * @author David Brown
 * @version 2021-11-01
 */
public class Cashier {

    private Menu menu = null;

    /**
     * Constructor.
     *
     * @param menu A Menu.
     */
    public Cashier(Menu menu) {
	this.menu = menu;
    }

    /**
     * Asks for commands and quantities. Prints a receipt when all orders have been
     * placed.
     *
     * @return the completed Order.
     */
    public Order takeOrder() {
    	boolean isValid = true;
    	boolean correct = false;
    	Order curOrder = new Order();
    	int command = 0;
    	
    	System.out.println("Welcome to WLU Foodorama!\n");
    	this.printIntro();
    	
    	Scanner keyboard = new Scanner(System.in);
    	
    	while (isValid) {
    		try {
    			System.out.println("Command: ");
    			command = keyboard.nextInt();
    			correct = true;
    		} catch(InputMismatchException e) {
    			keyboard.nextLine();
    			System.out.println("Not a valid number");
    			correct = false;
    		}
    		if (correct) {
    			isValid = this.runCommand(curOrder, command, keyboard);
    		}
    	}
    	keyboard.close();

	return curOrder;
    }
    
    public void printIntro() {
    	System.out.println("Menu:");
    	System.out.println(menu.toString());
    	System.out.println("Press 0 when done.\nPress any other key to see the menu again");
    }
    
    public Boolean runCommand(Order curOrder, int command, Scanner keyboard) {
    	boolean isValid = true;
    	if (command == 0){
    		isValid = false;
    		System.out.println("--------------------------------------");
    		System.out.println(curOrder.toString());
    	}
    	else if (command > 0 && command < menu.size()) {
    		int quantity = 0;
    		boolean correct = false;
    		try {
    			System.out.println("How many do you want? ");
    			quantity = keyboard.nextInt();
    			correct = true;
    		} catch(InputMismatchException e) {
    			keyboard.nextLine();
    			System.out.println("Not a valid number");
    		}
    		if (correct) {
    			curOrder.add(menu.getItem(command - 1), quantity);
    		}
    	}
    	else {
    		this.printIntro();
    	}
    return isValid;
    }
}