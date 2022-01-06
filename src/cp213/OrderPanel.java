package cp213;

import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.print.PrinterJob;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The GUI for the Order class.
 *
 * @author your name here
 * @author Abdul-Rahman Mawlood-Yunis
 * @author David Brown
 * @version 2021-11-01
 */
@SuppressWarnings("serial")
public class OrderPanel extends JPanel {

    /**
     * Implements an ActionListener for the 'Print' button. Prints the current
     * contents of the Order to a system printer or PDF.
     */
    private class PrintListener implements ActionListener {

	@Override
	public void actionPerformed(final ActionEvent e) {
	    final PrinterJob printJob = PrinterJob.getPrinterJob();
	    printJob.setPrintable(order);

	    if (printJob.printDialog()) {
		try {
		    printJob.print();
		} catch (final Exception printException) {
		    System.err.println(printException);
		}
	    }
	}
    }

    /**
     * Implements a FocusListener on a JTextField. Accepts only positive integers,
     * all other values are reset to 0. Adds a new MenuItem to the Order with the
     * new quantity, updates an existing MenuItem in the Order with the new
     * quantity, or removes the MenuItem from the Order if the resulting quantity is
     * 0.
     */
    private class QuantityListener implements FocusListener {
    	JTextField field = null;
    	MenuItem item = null;
    	JLabel subtotal = null;
    	JLabel tax = null;
    	JLabel total = null;
    public QuantityListener(JTextField field, MenuItem item, JLabel subtotal, JLabel tax, JLabel total) {
    	this.field = field;
    	this.item = item;
    	this.subtotal = subtotal;
    	this.tax = tax;
    	this.total = total;
    }

	@Override
	public void focusGained(final FocusEvent evt) {
		this.field.selectAll();
	}

	@Override
	public void focusLost(final FocusEvent evt) {
		String input = this.field.getText();
		  try {
			  int num = Integer.parseInt(input);
			  if (num >= 0) {
				  order.update(this.item, num);
				  subtotal.setText(order.getSubTotal().toString());
				  tax.setText(String.format("%.2f", order.getTaxes()));
				  total.setText(String.format("%.2f",order.getTotal()));
			  }
			  else {
				  this.field.setText("0");
			  }

		  } catch(NumberFormatException e){
			  this.field.setText("0"); 
		  } 

	}
    }

    // Attributes
    private Menu menu = null;
    private final Order order = new Order();
    private final JButton printButton = new JButton("Print");
    private final JLabel subtotalLabel = new JLabel("0");
    private final JLabel taxLabel = new JLabel("0");
    private final JLabel totalLabel = new JLabel("0");

    /**
     * Displays the menu in a GUI.
     *
     * @param menu The menu to display.
     */
    public OrderPanel(final Menu menu) {
	this.menu = menu;
	this.layoutView();
    }

    /**
     * Uses the GridLayout to place the labels and buttons.
     */
    private void layoutView() {
	this.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
	// Number of rows of GridLayout must be updated to accommodate all MenuItems,
	// totals, and Print button
	this.setLayout(new GridLayout(menu.size() + 5, 3));
	this.add(new JLabel("Item"));
	this.add(new JLabel("Price"));
	this.add(new JLabel("Quantity"));

	for (int i = 0; i < menu.size(); i++) {
		this.add(new JLabel(menu.getItem(i).getName()));
		this.add(new JLabel(menu.getItem(i).getPrice().toString()));
		JTextField quantity = new JTextField("0", 30);
		quantity.addFocusListener(new QuantityListener(quantity, menu.getItem(i), subtotalLabel, taxLabel, totalLabel));
		this.add(quantity);
	}
	this.add(new JLabel("Subtotal: "));
	this.add(new JLabel(""));
	this.add(subtotalLabel);
	this.add(new JLabel("Tax: "));
	this.add(new JLabel(""));
	this.add(taxLabel);
	this.add(new JLabel("Total: "));
	this.add(new JLabel(""));
	this.add(totalLabel);
	this.add(new JLabel(""));


	// Register the PrinterListener with the print button.
	this.printButton.addActionListener(new PrintListener());
	this.add(this.printButton);
	return;
    }
}