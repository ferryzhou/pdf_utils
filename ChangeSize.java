import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

public class ChangeSize {
    
    public void manipulatePdf(String src, String dest)
        throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        Rectangle pagesizeIn = reader.getPageSize(1);
        Rectangle pagesizeOut = new Rectangle(pagesizeIn.getWidth() * 3 / 4, pagesizeIn.getHeight() * 3 / 4);
		float x = (pagesizeOut.getRight() - pagesizeIn.getRight()) / 2;
		float y = (pagesizeOut.getTop() - pagesizeIn.getTop()) / 2;
 
		Document document = new Document(pagesizeOut);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        PdfImportedPage page = null;
		int n = reader.getNumberOfPages();
		for (int i = 1; i < n; i++) {
			page = writer.getImportedPage(reader, i);
			document.newPage();
			writer.getDirectContent().addTemplate(page, x, y);
		}
        document.close();
    }

    public static void main(String[] args)
        throws IOException, DocumentException {
        new ChangeSize().manipulatePdf(args[0], args[1]);
    }
}
