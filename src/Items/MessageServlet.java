package Items;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
@WebServlet("/MessageServlet")
public class MessageServlet extends HttpServlet {
	
	public MessageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws IOException, ServletException
    {
    	JasperReport jasperRreort;
		try {
			
			/*JasperReport jasperReport = JasperCompileManager.compileReport("/Users/kishanpatel/Desktop/Csc 400/InsertDataWebApplication/Reports/report.jrxml");
		    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,new HashMap(), new JREmptyDataSource());
		    JasperExportManager.exportReportToPdfFile(jasperPrint, "sample.pdf");*/
			jasperRreort = JasperCompileManager.compileReport("/Users/kishanpatel/Desktop/Csc 400/InsertDataWebApplication/Reports/Invoice_1.jrxml");
		
		Map<String, Object> parameter = new HashMap<String,Object>();
		
		JRDataSource dataSource = new JREmptyDataSource();
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperRreort, parameter, dataSource);
		
		File outDir = new File("/Users/kishanpatel/Desktop/Csc 400/InsertDataWebApplication");
		outDir.mkdir();
		
		JasperExportManager.exportReportToPdfFile(jasperPrint, "/Users/kishanpatel/Desktop/Csc 400/InsertDataWebApplication/Reports/Report.pdf");
		System.out.println("Done!");
    }
    catch (JRException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
}