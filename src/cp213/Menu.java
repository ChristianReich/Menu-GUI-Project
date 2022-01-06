package cp213;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.math.BigDecimal;

/**
 * Stores a List of MenuItems and provides a method return these items in a
 * formatted String. May be constructed from an existing List or from a file
 * with lines in the format:
 *
 * <pre>
1.25 hot dog
10.00 pizza
...
 * </pre>
 *
 * @author your name here
 * @author Abdul-Rahman Mawlood-Yunis
 * @author David Brown
 * @version 2021-11-01
 */
public class Menu {

    /**
     * For testing. Reads contents of "menu.txt" from root of project.
     *
     * @param args Unused.
     */
    public static void main(String[] args) {
	try {
	    Menu menu = new Menu("menu.txt");
	    System.out.println(menu.toString());
	} catch (FileNotFoundException e) {
	    System.out.println("Cannot open menu file");
	}
	System.exit(0);
    }

    private List<MenuItem> items = new ArrayList<MenuItem>();

    /**
     * Constructor from a file of MenuItem strings. Each line in the file
     * corresponds to a MenuItem. You have to read the file line by line and add
     * each MenuItem to the ArrayList of items.
     *
     * @param filename The name of the file containing the menu items.
     * @throws FileNotFoundException Thrown if file not found or cannot be read.
     */
    public Menu(String filename) throws FileNotFoundException {

    	final File file = new File(filename);
    	Scanner fileIn = new Scanner(file);
    	while (fileIn.hasNext()) {
    		String line = fileIn.nextLine();
    		String[] varArray = line.split(" ");
    		BigDecimal price = new BigDecimal(varArray[0]);
    		String name = varArray[1];
    		if (varArray.length > 2) {
    			for (int i = 2; i <  varArray.length; i++) {
    				name += " " + varArray[i];
    			}
    		}
    		MenuItem a = new MenuItem(name, price);
    		items.add(a);
    	}

    	fileIn.close();
    }

    /**
     * Returns the List's i-th MenuItem.
     *
     * @param i Index of a MenuItem.
     * @return the MenuItem at index i
     */
    public MenuItem getItem(int i) {

	return items.get(i);
    }

    /**
     * Returns the Menu items as a String in the format:
     *
     * <pre>
    5) poutine      $ 3.75
    6) pizza        $10.00
     * </pre>
     *
     * where n) is the index + 1 of the MenuItems in the List.
     */
    @Override
    public String toString() {
    	String s = "";
    	for (int i = 1; i <= this.items.size(); i++) {
    		s += String.format("%d) %s\n", i, this.items.get(i - 1).toString());
    	}

	return s;
    }

    /**
     * Returns the number of MenuItems in the items List.
     *
     * @return Size of the items List.
     */
    public int size() {

	return items.size();
    }
}