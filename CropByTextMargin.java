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
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.TextMarginFinder;

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
            try {
            //Rectangle rect = Rectangle.new(finder.getLlx(), finder.getLly(),
            //    finder.getWidth(), finder.getHeight());
			//System.out.printf("%f %f %f %f\n", finder.getLlx(), finder.getLly(), finder.getWidth(), finder.getHeight());
			//System.out.printf("%f\n", finder.getWidth());
            } catch (NullPointerException e) {
				System.out.printf("%d\n", i);
            }
        }
        return new Rectangle(81, 54, 81+359+10, 54+547+10);
	}

    public void manipulatePdf(String src, String dest)
        throws IOException, DocumentException {

        PdfReader reader = new PdfReader(src);
        Rectangle rect = estimateCropRegion(reader);
        Rectangle pagesizeIn = reader.getPageSize((int)(reader.getNumberOfPages()/2));
		System.out.printf("page size: %f, %f\n", pagesizeIn.getWidth(), pagesizeIn.getHeight());
        Rectangle pagesizeOut = new Rectangle(rect.getWidth(), rect.getHeight());
		System.out.printf("new page size: %f, %f\n", pagesizeOut.getWidth(), pagesizeOut.getHeight());

		float x = -rect.getLeft();
		float y = -rect.getBottom();

		Document document = new Document(pagesizeOut);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        PdfImportedPage page = null;
		int n = reader.getNumberOfPages();
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
        TextMarginFinder finder;
		for (int i = 1; i <= n; i++) {
            finder = parser.processContent(i, new TextMarginFinder());
            float llx = x; float lly = y;
            try {
            	llx = -finder.getLlx()+5;
            	lly = -finder.getLly()+5;
            } catch (NullPointerException e) {
				System.out.printf("%d\n", i);
            }

			page = writer.getImportedPage(reader, i);
			document.newPage();
			writer.getDirectContent().addTemplate(page, llx, lly);
		}
        document.close();
    }

    public static void main(String[] args)
        throws IOException, DocumentException {
        new CropByTextMargin().manipulatePdf(args[0], args[1]);
    }
}
