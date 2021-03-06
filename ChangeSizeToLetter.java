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

public class ChangeSizeToLetter {
    
    public void manipulatePdf(String src, String dest)
        throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        Document document = new Document(PageSize.LETTER);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        PdfContentByte content = writer.getDirectContent();
        PdfImportedPage page = writer.getImportedPage(reader, 1);
        // adding the same page 16 times with a different offset
        Rectangle pagesizeIn = reader.getPageSize(1);
        Rectangle pagesizeOut = PageSize.LETTER;
		float x = (pagesizeOut.getRight() - pagesizeIn.getRight()) / 2;
		float y = (pagesizeOut.getTop() - pagesizeIn.getTop()) / 2;
        content.addTemplate(page, x, y);
        document.close();
    }

    public static void main(String[] args)
        throws IOException, DocumentException {
        new ChangeSizeToLetter().manipulatePdf(args[0], args[1]);
    }
}
