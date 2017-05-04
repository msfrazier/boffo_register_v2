package printer;

/**
 * Author Michael Mussler
 * Date updated: 5/01/2017
 * Printer class that takes ticket and formats it to the receipt.
 */
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

public class Printer  {

    public static void print(final String receipt) {
        Printable contentToPrint = new Printable() {

            // Set the Font type, font size and page layout of receipt.
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int page)
                    throws PrinterException {

                if (page > 0) {
                    return NO_SUCH_PAGE;
                }
                pageFormat.setOrientation(PageFormat.LANDSCAPE);
                Graphics2D g2d = (Graphics2D) graphics.create();

                // Set color of font.
                g2d.setPaint(Color.black);

                // Set the font type and size.
                g2d.setFont(new Font("Monospace", Font.BOLD, 11));
                g2d.translate(pageFormat.getImageableX(),
                        pageFormat.getImageableX());

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

    //Format the information from the Ticket given from Controller.
    public void printReciept() {
        // Format the time.
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");

    }
}
