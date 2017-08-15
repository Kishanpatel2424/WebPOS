package Items;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import com.sun.xml.internal.txw2.Document;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
/**
 * Servlet implementation class Cashier
 */
@WebServlet("/Cashier")
public class Cashier extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Cashier() {
        super();
        // TODO Auto-generated constructor stub
    }

    
   
   ArrayList<ItemsDescription> itemList = new ArrayList<ItemsDescription>();
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		Connection MyConn =null;
		Statement myStmt =null;
		PreparedStatement insertActor;
		
		response.setContentType("text/html");
		
		ResultSet results = null;
		
		
		String iName;
		double iPrice;
		double iQty = 0;
		double Amount = 0;
		double change, due = 0;
		int DeleteIndex=0;
		double tax=0;
		
		boolean Search 	= request.getParameter("Search") != null;
		boolean Tender 	= request.getParameter("Tender") != null;
		boolean Clear	= request.getParameter("Clear")  !=null;
		boolean DeleteI = request.getParameter("Delete") !=null;
		
		
		boolean Find = request.getAttribute("Find") !=null;
		boolean Process = request.getParameter("Process") !=null;
		boolean approved=false;
		
		String iCode=null;
		String Q=null;
		iCode = request.getParameter("iCode");
		Q = request.getParameter(("Quantity"));
		String PaymentType="";
		if(request.getParameter("ItemCode") !=null){
			System.out.println(request.getParameter("ItemCode")+" Value from Search");
			
			iCode = request.getParameter("ItemCode");
			Q="1";
			Search = true;
		}
		int result;
		
		
		if(Clear){
			itemList.clear();
			Total(-1); 
			System.out.println(TotalTax);
			 request.getSession().setAttribute("Clear", "Clear");
			 request.setAttribute("TotalTax", TotalTax);
			 request.getRequestDispatcher("/Reg.jsp").forward(request, response);
				 
			 approved=false;
		}
		
		
		if(DeleteI){
			if(request.getParameter("DeleteIndex")!=null && request.getParameter("DeleteIndex")!=""){
				String I = request.getParameter("DeleteIndex");
				DeleteIndex = Integer.parseInt(I);
				
				if(DeleteIndex>0 && DeleteIndex<=itemList.size()){
					double a =itemList.get(DeleteIndex-1).getiPrice()*itemList.get(DeleteIndex-1).getiQty();
					System.out.println(a);
					itemList.remove(DeleteIndex-1);
					Total(-a);
					
					System.out.println(TotalTax+"After delete "+a);
				}
				request.setAttribute("TotalTax", TotalTax);
				request.getRequestDispatcher("/Reg.jsp").forward(request, response);
				
			}
		}
		
		if(Process){
			System.out.println("Inside Process");
			if(request.getParameter("Amount") != null){
				Amount = Double.parseDouble(request.getParameter("Amount"));
				
				if(Integer.parseInt(request.getParameter("Type")) == 1){
					PaymentType = "Cash";
				}
				else {
					PaymentType = "Card";
				}
				
					if(Amount == (TotalTax)){
						due =0;
						
						System.out.println("Amount Paid "+TotalTax);
						request.setAttribute("due", due);
						request.setAttribute("TotalTax", TotalTax);
						
						 
						 approved=true;
					}
					
					if(Amount > TotalTax){
						due=0;
						change= Math.round((Amount-TotalTax)*100);
						due=change/100;
						
						System.out.println(due);
						 request.setAttribute("due", due);
						 request.setAttribute("TotalTax", TotalTax);
						 
						 approved=true;
					}
					if(Amount<TotalTax){
						due = TotalTax-Amount;
						TotalTax = due;
						 request.setAttribute("Due", due);
						 request.getRequestDispatcher("/PayPage.jsp").forward(request, response);
					}
			}
		}
		
		
		if(Tender){
			request.setAttribute("Tender", "Tender");
			request.setAttribute("TotalTax", TotalTax);
			request.getRequestDispatcher("/PayPage.jsp").forward(request, response);
		}
			try{
				
				
					MyConn = ConnectionManager.getConnection();
					 myStmt = MyConn.createStatement();
					
					ItemsDescription item =null;
				
					//Search and display Begains
				if(Search){
					
					try{
						 if(request.getParameter("iCode") == "" && request.getParameter("iCode") == null && iCode==null ){
							request.setAttribute("BarCodeEmpty","BarCodeEmpty");
							request.getRequestDispatcher("/Reg.jsp").forward(request, response);
						}
					else //if(request.getParameter("iCode") != null &&request.getParameter("iCode")!="" )
					{
							  
						
							   
							  iQty = Double.parseDouble(Q);
						System.out.println(iCode+" Value from Search to Reg"+Q+" "+iQty);
							  String Check = ("SELECT COUNT(ItemCode) FROM Item WHERE ItemCode ="+iCode);
							  results = myStmt.executeQuery(Check);
								 
								while(results.next()){
							        	if(results.getInt(1)<=0){
							        		System.out.println("Item Dose not Exist with BarCode "+iCode);
							        		request.setAttribute("NotExist","NotExist");
							        		request.getRequestDispatcher("/Reg.jsp").forward(request, response);
							        	// AddItem = false;
							        	}	  
						
						String sqlStatement = ("select * From Item where ItemCode ="+iCode);
						
						//Statement
						
						 results = myStmt.executeQuery(sqlStatement);
						 
							  item = new ItemsDescription();
							
							 while(results.next()){
								 iCode = results.getString("ItemCode");
								 iName = results.getString("ItemName");
								 iPrice = results.getDouble("ItemPrice");
								
								 item.setiCode(iCode);
								 item.setiName(iName);
								 item.setiPrice(iPrice);
								 item.setiQty(iQty);
	
								 Total(iPrice*iQty);
								 
								 
								itemList.add(item);
								
								
								
								
								  request.getSession().setAttribute("item", item);
						          request.getSession().setAttribute("itemList", itemList);
						          request.setAttribute("TotalTax", TotalTax);
						          request.getRequestDispatcher("/Reg.jsp").forward(request, response);
						          
						         
						          results.close();
						            
						          MyConn.close();
						          
							 }	
							
						 }
					}
						
					}
					catch (NullPointerException e) {
			            System.out.print("Caught the NullPointerException"+e);

							request.setAttribute("EnterItem", "EnterItem");
							 request.getRequestDispatcher("/Reg.jsp").forward(request, response);
					}
					
				}
				
				
				//Search and display Ends
				
				/*if(Find == false){
					int size=0;
					String SearchList = request.getParameter("SearchList");
					//System.out.println(SearchList+" Value to be searched");
					
					String SearchSql = "SELECT * FROM Item where ItemName LIKE '%" + SearchList + "%' OR Department Like '%"+SearchList+"%'";
							
					results = myStmt.executeQuery(SearchSql);
					
					while(results.next()){
						size++;
						al.add(results.getString("ItemCode"));
						al.add(results.getString("ItemName"));
						al.add(results.getDouble("ItemPrice"));
						
						alResult.add(al);
						System.out.println(results.getString("ItemCode")+" "+results.getString("ItemName"));
						
					}
					System.out.println("Search Value size"+size);
					request.getRequestDispatcher("/Reg.jsp").forward(request, response);
					results.close();
				}*/
				
				
				if(approved){
					
					System.out.println("Payment Approved");
					tax = (TotalTax-Total);
					
					int InvoiceNumber = 0;
					
					Calendar cal = Calendar.getInstance();
					java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
					
					insertActor = MyConn.prepareStatement("INSERT INTO Invoice(InvoiceTotal,ChangeDue,PaymentType,Tax,Date,Status) VALUES(?,?,?,?,?,?)");
					insertActor.setDouble(1,TotalTax);
					insertActor.setDouble(2, due);
					insertActor.setString(3,PaymentType);
					insertActor.setDouble(4, tax);
					insertActor.setTimestamp(5, timestamp);
					insertActor.setString(6,"Open");
					System.out.println(PaymentType+" Payment Type");
					result =insertActor.executeUpdate();
					
					insertActor = MyConn.prepareStatement("SELECT MAX(InvoiceNumber) from Invoice");
					ResultSet rs = insertActor.executeQuery();
					while(rs.next()){
						InvoiceNumber = rs.getInt(1);
						
					}
					
					System.out.println("Invoice created total "+TotalTax);
					
					try{
						
						for(ItemsDescription addItem: itemList){
							
							String code = addItem.iCode;
							String name = addItem.iName;
							double price = addItem.iPrice;
							double Quantity = addItem.iQty;
							double QtyTax = (price*Quantity)*1.0635;
							double ItemTax = QtyTax-(price*Quantity);
							
							insertActor = MyConn.prepareStatement("INSERT INTO InvoiceDetail(InvoiceNumber, ItemCode, ItemName,ItemPrice,Quantity,Tax,ItemTotal,Date) VALUES (?,?,?,?,?,?,?,?)");
							
							insertActor.setInt(1, InvoiceNumber);
							insertActor.setString(2, code);
							insertActor.setString(3, name);
							insertActor.setDouble(4, price);
							insertActor.setDouble(5, Quantity);
							insertActor.setDouble(6, ItemTax);
							insertActor.setDouble(7, QtyTax);
							insertActor.setTimestamp(8, timestamp);
							
							
							System.out.println("Item add to InvoideDetail with Inv# "+InvoiceNumber+" Item"+name+" Qty"+Quantity+" TTl"+QtyTax);
							result =insertActor.executeUpdate();
							System.out.println(result+" Execute result");
							
							
							
						}
						if(result>0){
							itemList.clear();
							Total(-1);
							String maxInv = "select max(InvoiceNumber) from InvoiceDetail";
							int maxInvNum = 0;
							results = myStmt.executeQuery(maxInv);
							 while(results.next()){
								 maxInvNum = results.getInt(1);
							 }
							 	Map<String, Object> map = new HashMap<String,Object>();
								//map.put("maxInvNum", maxInvNum);
								JasperReport jasperRreort = JasperCompileManager.compileReport("/Users/kishanpatel/Desktop/Csc 400/InsertDataWebApplication/Reports/Invoice_1.jrxml");
								
								JRDataSource dataSource = new JREmptyDataSource();
								
								JasperPrint jasperPrint = JasperFillManager.fillReport(jasperRreort, map,MyConn);
								
								File outDir = new File("/Users/kishanpatel/Desktop/Csc 400/InsertDataWebApplication");
								outDir.mkdir();
								
								JasperExportManager.exportReportToPdfFile(jasperPrint, "/Users/kishanpatel/Desktop/Csc 400/InsertDataWebApplication/Reports/Invoice_1.pdf");
								JasperExportManager.exportReportToPdfFile(jasperPrint, "/Users/kishanpatel/Desktop/Csc 400/InsertDataWebApplication/WebContent/Invoice_1.pdf");
								
								
								System.out.println("Done! Creating Peciept");
							request.getRequestDispatcher("/Reg.jsp").forward(request, response);
					 	
						}
						results.close();
					 	MyConn.close();
						
						
						///Passing Variables
							
					}
					
					catch (NullPointerException e) {
			           
					}
					
				}
				 
			}
			catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			catch (SQLException exc){
				  
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally  {
				ConnectionManager.closeConnection(MyConn);
			}
			
	}
	static double Total = 0;
	static double Tax=0;
	static double TaxP=1.0635;
	static double TotalTax=0;
	public static double Total (double iPrice){
		
		if(iPrice!=-1){
		Total+=iPrice;
		Tax = Math.round(Total*TaxP*100);
		TotalTax=Tax/100;
		
		
		}
		else if(iPrice>=-1 && iPrice<0)
		{
			Total=0;
			//System.out.println(Total);
			return Total=0;
		}
		return Total;
	}
	
	

}
