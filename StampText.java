
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Rectangle;

// java StampText a.pdf ao.pdf 2
public class StampText {
    
    public static void main(String[] args)
        throws DocumentException, IOException {
        stampPagenumber(args[0], args[1], args[2]);
    }

    public static void stampPagenumber(String src, String dest, String text)
        throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
		Rectangle size = reader.getPageSize(1); //72 pixels per inch
		double w = size.getRight();
		double h = size.getTop();
		System.out.println("right " + size.getRight() + " -- " + size.getRight() / 72);
		System.out.println("top " + size.getTop() + " -- " + size.getTop() / 72);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        int page = Integer.parseInt(text);
        for (int i = 0; i < reader.getNumberOfPages(); i++) {
			System.out.println(i + " right " + size.getRight() + " -- " + size.getRight() / 72);
			Rectangle tmpsize = reader.getPageSize(i+1); //72 pixels per inch
        	PdfContentByte canvas = stamper.getOverContent(i+1);
        	ColumnText.showTextAligned(canvas,
                Element.ALIGN_LEFT, new Phrase("- " + (page + i) + " -"), tmpsize.getRight() - (int)(w * 0.095), (int)(h * 0.035), 0);
			canvas.saveState();
		}
		stamper.close();
    }

}
