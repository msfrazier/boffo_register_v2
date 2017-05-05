package printer;

/**
 * Author Michael Mussler
 * Date updated: 5/04/2017
 * Printer class that takes ticket and formats it to the receipt.
 */
import administration.AdministrationObject;
import static com.sun.deploy.uitoolkit.ToolkitStore.dispose;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import transaction.Transaction;

public class Printer extends AdministrationObject {

    protected JTable jTable1;

    public static void print(final String receipt) {
        Printable contentToPrint = new Printable() {

            // Set the Font type, font size and page layout of receipt.
            @Override
            public int print(Graphics _graphics, PageFormat _pageFormat,
                    int _page)
                    throws PrinterException {

                if (page > 0) {
                    return NO_SUCH_PAGE;
                }
                _pageFormat.setOrientation(PageFormat.LANDSCAPE);
                Graphics2D g2d = (Graphics2D) _graphics.create();

                // Set color of font.
                g2d.setPaint(Color.black);

                // Set the font type and size.
                g2d.setFont(new Font("Monospace", Font.BOLD, 11));
                g2d.translate(_pageFormat.getImageableX(),
                        _pageFormat.getImageableX());

                g2d.drawString(receipt, 0, 0);

                return PAGE_EXISTS;
            }
        };
        // Sets the printer Job to print.
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(contentToPrint);

        try {
            job.print();
        } catch (PrinterException e) {
            System.err.println(e.getMessage());
        }
    }

    // Takes int Transaction and Admin objects.
    // Formats the data receved from controller.
    public void receiveData(Transaction _transaction,
            AdministrationObject _admin) throws Exception {
        // Format date and time.
        DefaultTableModel mod = (DefaultTableModel) jTable1.getModel();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        // Get current date time with Date().
        Date date = new Date();
        Date time = new Date();
        String Date = dateFormat.format(date);
        String Time = timeFormat.format(time);
        // Format the receip with all needed data.
        String Header
                = "   ****" + getStoreName() + "****       \n"
                + "       " + getStoreHours() + "          \n"
                + "       " + getPhoneNumber() + "            \n"
                + "Date: " + Date + "     Time: " + Time + "\n"
                + "---------------------------------\n"
                + "Name          Qty    Rate     Amt\n"
                + "---------------------------------\n";
//        double total = 0.00;

        String amt
                = "\n \n \nTotal Amount = " + _tranaction.Total + "\n"
                + "Tax =" + getTaxRate() + "\n"
                + "*********************************\n"
                + "Thank you. \n";

        String bill = Header;
        int i = 0;
        do {

            String name = "" + mod.getValueAt(i, 2);
            String qty = "" + mod.getValueAt(i, 3);
            String rate = "" + mod.getValueAt(i, 4);
            String amount = "" + mod.getValueAt(i, 6);

            if (name.length() > 12) {
                name = name.substring(0, 12) + "  ";
            } else {
                for (int j = name.length() - 12; j <= name.length(); j++);
                {
                    name = name + " ";
                }
            }

            if (qty.length() <= 5) {
                for (int j = 0; j <= qty.length() - 5; j++);
                {
                    qty = qty + " ";
                }
            }

            rate = rate;
            String items
                    = name + "\t" + qty + "\t" + rate + "\t" + amount + "\n";

            bill = bill + items;
            i++;

        } while (i <= mod.getRowCount() - 1);

        bill = bill + _transaction.Total;
        System.out.println(bill);
        print(bill);
        dispose();

    }

}
