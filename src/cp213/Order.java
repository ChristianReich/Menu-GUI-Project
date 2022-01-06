package cp213;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Stores a HashMap of MenuItem objects and the quantity of each MenuItem
 * ordered. Each MenuItem may appear only once in the HashMap.
 *
 * @author your name here
 * @author Abdul-Rahman Mawlood-Yunis
 * @author David Brown
 * @version 2021-11-01
 */
public class Order implements Printable {

    /**
     * The current tax rate on menu items.
     */
    public static final BigDecimal TAX_RATE = new BigDecimal(0.13);
    private Map<MenuItem, Integer> items = new HashMap<MenuItem, Integer>();

    /**
     * Update the quantity of a particular MenuItem in an order.
     *
     * @param item     The MenuItem to purchase - the HashMap key.
     * @param quantity The number of the MenuItem to purchase - the HashMap value.
     */
    public void add(MenuItem item, int quantity) {

    	if (items.containsKey(item)) {
    		this.items.replace(item, items.get(item) + quantity);
    	}
    	else {
    		this.items.put(item, quantity);
    	}

    }

    /**
     * Calculates the total value of all MenuItems and their quantities in the
     * HashMap.
     *
     * @return the total price for the MenuItems ordered.
     */
    public BigDecimal getSubTotal() {
    	BigDecimal subTotal = BigDecimal.valueOf(0);
    	for (Entry<MenuItem, Integer> item : this.items.entrySet()) {
    		subTotal = subTotal.add(item.getKey().getPrice().multiply(BigDecimal.valueOf(item.getValue())));
    	}
	return subTotal;
    }

    /**
     * Calculates and returns the total taxes to apply to the subtotal of all
     * MenuItems in the order. Tax rate is TAX_RATE.
     *
     * @return total taxes on all MenuItems
     */
    public BigDecimal getTaxes() {

	return this.getSubTotal().multiply(TAX_RATE);
    }

    /**
     * Calculates and returns the total price of all MenuItems order, including tax.
     *
     * @return total price
     */
    public BigDecimal getTotal() {

	return this.getSubTotal().add(this.getTaxes());
    }

    /**
     * Returns a String version of a receipt for all the MenuItems in the order.
     */
    @Override
    public String toString() {

    	String s = "";
    	for (Entry<MenuItem, Integer> item : this.items.entrySet()) {
    		BigDecimal total = item.getKey().getPrice().multiply(BigDecimal.valueOf(item.getValue()));
    		s += String.format("%-14s %d @ $%5.2f = %6.2f\n", item.getKey().getName(), item.getValue(), item.getKey().getPrice(), total);
    	}
    	s += String.format("\nSubtotal:                   $%5.2f\n", this.getSubTotal());
    	s += String.format("Taxes:                      $%5.2f\n", this.getTaxes());
    	s += String.format("Total:                      $%5.2f\n", this.getTotal());

	return s;
    }

    /**
     * Replaces the quantity of a particular MenuItem in an Order with a new
     * quantity. If the MenuItem is not in the order, it is added. If quantity is 0
     * or negative, the MenuItem is removed from the Order.
     *
     * @param item The MenuItem to update
     * @param quantity The quantity to apply to item
     */
    public void update(MenuItem item, int quantity) {

    	this.items.put(item, quantity);
    	if (this.items.get(item) <= 0) {
    		this.items.remove(item);
    	}

    }

    /*
     * Implements the Printable interface print method. Prints lines to a Graphics2D
     * object using the drawString method. Prints the current contents of the Order.
     */
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
	int result = PAGE_EXISTS;

	if (pageIndex == 0) {
	    Graphics2D g2d = (Graphics2D) graphics;
	    g2d.setFont(new Font("MONOSPACED", Font.PLAIN, 12));
	    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

	    // your code here - write the contents of Order using drawString
    	int y = 10;
    	int lineHeight = graphics.getFontMetrics().getHeight();
	    for (Entry<MenuItem, Integer> item : this.items.entrySet()) {
    		BigDecimal total = item.getKey().getPrice().multiply(BigDecimal.valueOf(item.getValue()));
    		graphics.drawString(String.format("%-14s %3d @ $%5.2f = %6.2f\n", item.getKey().getName(), item.getValue(), item.getKey().getPrice(), total),20,y += lineHeight);
    	}
    	graphics.drawString(String.format("\nSubtotal:                   $%5.2f\n", this.getSubTotal()), 20, y += lineHeight);
    	graphics.drawString(String.format("Taxes:                      $%5.2f\n", this.getTaxes()), 20, y += lineHeight);
    	graphics.drawString(String.format("Total:                      $%5.2f\n", this.getTotal()), 20, y += lineHeight);

	} else {
	    result = NO_SUCH_PAGE;
	}
	return result;
    }
}