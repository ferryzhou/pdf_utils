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

// sometimes text margin is not consistent
// use the smaller one
// first extract margin from every page
// then count frequency for each kind of margin
// pick the most popular 2
// if the first one is smaller, choose the first one
// else if the frequency is close, pick the second one
// actually, we consider more about width than height
// @author ferryzhou@gmail.com
// @since May 2012
public class CropByTextMargin {

	public Rectangle estimateCropRegion(PdfReader reader)
        throws IOException, DocumentException {

        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
        TextMarginFinder finder;
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            finder = parser.processContent(i, new TextMarginFinder());
            //Rectangle rect = Rectangle.new(finder.getLlx(), finder.getLly(),
            //    finder.getWidth(), finder.getHeight());
			System.out.printf("%f %f %f %f\n", finder.getLlx(), finder.getLly(),
                finder.getWidth(), finder.getHeight());
        }
	}

    public void manipulatePdf(String src, String dest)
        throws IOException, DocumentException {

        PdfReader reader = new PdfReader(src);
        Rectangle pagesizeIn = reader.getPageSize(1);
		System.out.printf("page size: %f, %f\n", pagesizeIn.getWidth(), pagesizeIn.getHeight());
        Rectangle pagesizeOut = new Rectangle(pagesizeIn.getWidth() * 3 / 4, pagesizeIn.getHeight() * 3 / 4);

		float x = (pagesizeOut.getRight() - pagesizeIn.getRight()) / 2;
		float y = (pagesizeOut.getTop() - pagesizeIn.getTop()) / 2;

		Document document = new Document(pagesizeOut);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        PdfImportedPage page = null;
		int n = reader.getNumberOfPages();
		for (int i = 1; i <= n; i++) {
			page = writer.getImportedPage(reader, i);
			document.newPage();
			writer.getDirectContent().addTemplate(page, x, y);
		}
        document.close();
    }

    public static void main(String[] args)
        throws IOException, DocumentException {
        new CropByTextMargin().manipulatePdf(args[0], args[1]);
    }
}
