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
        Rectangle pagesizeOut = new Rectangle(pagesizeIn.getWidth() / 2, pagesizeIn.getHeight() / 2);
        Document document = new Document(pagesizeOut);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        PdfContentByte content = writer.getDirectContent();
        PdfImportedPage page = writer.getImportedPage(reader, 1);
		float x = (pagesizeOut.getRight() - pagesizeIn.getRight()) / 2;
		float y = (pagesizeOut.getTop() - pagesizeIn.getTop()) / 2;
        content.addTemplate(page, x, y);
		int n = reader.getNumberOfPages();
		//for (int i = 2; i < n; i++) {
		//	page = writer.getImportedPage(reader, i);
		//	content.addTemplate(page, x, y);
		//}
        document.close();
    }

    public static void main(String[] args)
        throws IOException, DocumentException {
        new ChangeSize().manipulatePdf(args[0], args[1]);
    }
}
