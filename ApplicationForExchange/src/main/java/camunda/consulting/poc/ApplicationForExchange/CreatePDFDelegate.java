package camunda.consulting.poc.ApplicationForExchange;

import java.io.File;
import java.io.FileNotFoundException;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.itextpdf.io.IOException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class CreatePDFDelegate implements JavaDelegate
	{
    public static final String DEST = "./pdf/Exchange.pdf";
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		 File file = new File(DEST);
	        file.getParentFile().mkdirs();
	        new CreatePDFDelegate().createPdf(DEST, execution);
	        execution.setVariable("pdfDoc", file);
	}
	   
    
    public void createPdf(String dest, DelegateExecution execution ) throws IOException, FileNotFoundException {
        
    	Integer district = (Integer) execution.getVariable("DistrictID");
    	
    	//Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
        
        // Initialize document
        Document document = new Document(pdf);

        //Add paragraph to the document
        document.add(new Paragraph("Rotary District " + district + "\r\n" + 
        		"\r\n" + 
        		"New Generations Service Exchange Program\r\n" + 
        		""));

        //Close document
        document.close();
        
        
        
        
}


	
}
