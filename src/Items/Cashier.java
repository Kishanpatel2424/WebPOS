package Items;


import java.io.File;

import java.io.IOException;
import java.io.PrintWriter;
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

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
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
	@SuppressWarnings("resource")
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		Connection MyConn =null;
		Statement myStmt =null;
		PreparedStatement insertActor;
		HttpSession session = request.getSession();
		
		PrintWriter pw = response.getWriter();
		
		ResultSet results = null;
		ItemsDescription item =null;
		item = new ItemsDescription();
		
		String iName;
		double iPrice;
		double iQty = 0;
		double Amount = 0;
		double change, due = 0;
		int DeleteIndex=0;
		double tax=0;
		double total = 0;
		
		boolean Search 	= request.getParameter("Search") != null;
		boolean Tender 	= request.getParameter("Tender") != null;
		boolean Clear	= request.getParameter("Clear")  !=null;
		boolean DeleteI = request.getParameter("Delete") !=null;
		boolean taxItem = request.getParameter("taxItem") !=null;
		boolean nonTaxItem = request.getParameter("nonTaxItem") !=null;
		
		
		
		boolean Find = request.getAttribute("Find") !=null;
		boolean Process = request.getParameter("Process") !=null;
		boolean approved=false;
		
		String id=null;
			
		String iCode=null;
		String Q=null;
		String PaymentType="";
		
		iCode = request.getParameter("iCode");
		Q = request.getParameter(("Quantity"));
		
		
		//this is from SearchList.jsp Page
			if(request.getParameter("ItemCode") != null){
			iCode = request.getParameter("ItemCode").toUpperCase();
			Q ="1";
			Search = true;
			}
		//System.out.println(request.getParameter("taxItemValue")+" Tax Value");
		//Check if tax item is pressed
		if(taxItem){
			iCode="Tax";
			Q = request.getParameter(("Quantity"));
			Search = true;
		}
		if(nonTaxItem){
			iCode ="NonTax";
			Q = request.getParameter(("Quantity"));
			Search= true;
		}
	//
		int result;
		
		
		if(Clear){
			itemList.clear();
			 Total(-1);
			 item.setTotal(0);
			System.out.println(Totalsum);
			 session.setAttribute("Clear", "Clear");
			 session.setAttribute("TotalTax", Totalsum);
			 request.getRequestDispatcher("/Reg.jsp").forward(request, response);
				 
			 approved=false;
		}
		
		
		if(DeleteI){
			if(request.getParameter("DeleteIndex")!=null && request.getParameter("DeleteIndex")!=""){
				String I = request.getParameter("DeleteIndex");
				DeleteIndex = Integer.parseInt(I);
				
				if(DeleteIndex>0 && DeleteIndex<=itemList.size()){
					//double a =itemList.get(DeleteIndex-1).getiPrice()*itemList.get(DeleteIndex-1).getiQty();
					double a = itemList.get(DeleteIndex-1).getItemTotalTax();
					System.out.println(a);
					itemList.remove(DeleteIndex-1);
					Total(-a);
double z = Math.round(Totalsum*100);
z = z/100d;
Totalsum = z;
					item.setTotal(Totalsum);
					
					System.out.println(item.getTotal()+" After delete "+a+" TotalSum value is "+z);
				}
				 session.setAttribute("Totalsum", Totalsum);
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
				
					if(Amount == (Totalsum)){
						due =0;
						
						System.out.println("Amount Paid "+Totalsum);
						session.setAttribute("due", due);
						session.setAttribute("TotalTax", Totalsum);
						
						 
						 approved=true;
					}
					
					if(Amount > Totalsum){
						due=0;
						change= Math.round((Amount-Totalsum)*100);
						due=change/100;
						
						System.out.println(due);
						 session.setAttribute("due", due);
						 request.getSession().setAttribute("TotalTax", Totalsum);
						 
						 approved=true;
					}
					if(Amount<Totalsum){
						due = Totalsum-Amount;
						Totalsum = due;
						 session.setAttribute("Due", due);
						 request.getRequestDispatcher("/PayPage.jsp").forward(request, response);
					}
			}
		}
		
		
		if(Tender){
			session.setAttribute("Tender", "Tender");
			session.setAttribute("TotalTax", Totalsum);
			request.getRequestDispatcher("/PayPage.jsp").forward(request, response);
		}
			try{
				
				
					MyConn = ConnectionManager.getConnection();
					 myStmt = MyConn.createStatement();
					
					//ItemsDescription item =null;
					
					//Search and display Begains
				if(Search){
					
					try{
						 if(request.getParameter("iCode") == "" && request.getParameter("iCode") == null && iCode==null ){
							session.setAttribute("BarCodeEmpty","BarCodeEmpty");
							request.getRequestDispatcher("/Reg.jsp").forward(request, response);
						}
					else //if(request.getParameter("iCode") != null &&request.getParameter("iCode")!="" )
					{
							  
						
							  iQty = Double.parseDouble(Q);
							 // System.out.println(iCode+" Value from Search to Reg"+Q+" "+iQty);
							  String Check = ("SELECT COUNT(ItemCode) FROM Item WHERE ItemCode =?");
							  
							  PreparedStatement ps = MyConn.prepareStatement(Check);
							  ps.setString(1, iCode);
							  results = ps.executeQuery();
							  
								 
								while(results.next()){
							        	if(results.getInt(1)<=0){
							        		//System.out.println("Item Dose not Exist with BarCode "+iCode);
							        		session.setAttribute("NotExist","NotExist");
							        		request.getRequestDispatcher("/Reg.jsp").forward(request, response);
							        	// AddItem = false;
							        	}	  
						
						 Check = ("select * From Item where ItemCode =?");
						
						//Statement
						ps = MyConn.prepareStatement(Check);
						ps.setString(1, iCode);
						results = ps.executeQuery();
						 
							 // item = new ItemsDescription();
							
							 while(results.next()){
								// iCode = results.getString("ItemCode");
								 iName = results.getString("ItemName");
								 iPrice=results.getDouble("ItemPrice");
								
								 if(iCode == "Tax")	
								 {
									 if(request.getParameter("taxItemValue").toString() == "" || request.getParameter("taxItemValue").toString() == null)
										 iPrice = 1.0;
									 else
										 iPrice = Double.parseDouble(request.getParameter("taxItemValue"));
									 	
									 	System.out.println(iCode+" Tax Item Price Overwritten"+iPrice);
									 	
								 }
								 else if (iCode == "NonTax")
								 {
									 if(request.getParameter("nonTaxItemValue").toString() == "" || request.getParameter("nonTaxItemValue").toString() == null)
										 iPrice = 1.0;
									 else
										 iPrice = Double.parseDouble(request.getParameter("nonTaxItemValue"));

									 	System.out.println(iCode+" Non Tax Item Price Overwritten"+iPrice);
									 	iCode = "NonTax";
									 	
								 }
								 
								 item.setiCode(iCode);
								 item.setiName(iName);
								 item.setiPrice(iPrice);
								 item.setiQty(iQty);
								 item.setItemTotalTax(iCode,iPrice, iQty);
								 item.setTax(iCode,iPrice, iQty);
								 Total(item.getItemTotalTax());
								 	item.setTotal(Totalsum);
								 TotalTax(item.getTax());
								 double z = Math.round(TotalsumTax*100);
								 z = z/100d;
								 TotalsumTax = z;
								 	item.setTotalTax(TotalsumTax);
								 //Total(iPrice*iQty);
								 System.out.println(item.getTotal()+" tax per item*qty"+total+" TAX "+item.getTotalTax());
								 
								 
								itemList.add(item);
								
								
								
								
								
								  request.getSession().setAttribute("item", item);
								  request.getSession().setAttribute("itemList", itemList);
								  session.setAttribute("Totalsum", Totalsum);
						          request.getRequestDispatcher("/Reg.jsp").forward(request, response);
						          
						         /*
						          results.close();
						            
						          MyConn.close();*/
						          
							 }	
							
						 }
					}
						
					}
					catch (NullPointerException e) {
			            System.out.print("Caught the NullPointerException"+e);

							session.setAttribute("EnterItem", "EnterItem");
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
					
					
					int InvoiceNumber = 0;
					
					Calendar cal = Calendar.getInstance();
					java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
					
					insertActor = MyConn.prepareStatement("INSERT INTO Invoice(InvoiceTotal,ChangeDue,PaymentType,Tax,Date,Status) VALUES(?,?,?,?,?,?)");
					insertActor.setDouble(1,Totalsum);
					insertActor.setDouble(2, due);
					insertActor.setString(3,PaymentType);
					insertActor.setDouble(4, TotalsumTax);
					insertActor.setTimestamp(5, timestamp);
					insertActor.setString(6,"Open");
					
					System.out.println(PaymentType+" Payment Type");
					result =insertActor.executeUpdate();
					
					insertActor = MyConn.prepareStatement("SELECT MAX(InvoiceNumber) from Invoice");
					ResultSet rs = insertActor.executeQuery();
					while(rs.next()){
						InvoiceNumber = rs.getInt(1);
						
					}
					
					System.out.println("Invoice created total from item.getTotalclass"+item.getTotal()+" from item.getTotalTax "+item.getTotalTax());
					
					try{
						
						for(ItemsDescription addItem: itemList){
							
							String code = addItem.iCode;
							String name = addItem.iName;
							
							
							
							item.setItemTotalTax(addItem.iCode,addItem.iPrice, addItem.iQty);//.for total with tax & qty per item
							item.setTax(addItem.iCode,addItem.iPrice, addItem.iQty);//. for total tax per item
							insertActor = MyConn.prepareStatement("INSERT INTO InvoiceDetail(InvoiceNumber, ItemCode, ItemName,ItemPrice,Quantity,Tax,ItemTotal,Date,Status) VALUES (?,?,?,?,?,?,?,?,?)");
							
							insertActor.setInt(1, InvoiceNumber);
							insertActor.setString(2, code);
							insertActor.setString(3, name);
							insertActor.setDouble(4, addItem.iPrice);
							insertActor.setDouble(5, addItem.iQty);
							insertActor.setDouble(6, item.getTax());
							insertActor.setDouble(7, item.getItemTotalTax());
							insertActor.setTimestamp(8, timestamp);
							insertActor.setString(9, "Open");
							
							System.out.println("Item add to InvoideDetail with Inv# "+InvoiceNumber+" Item"+name+" Qty"+addItem.iQty+" TTl"+item.getItemTotalTax()+" tax"+item.getTax());
							result =insertActor.executeUpdate();
							System.out.println(result+" Execute result");
							
							PreparedStatement ps = MyConn.prepareStatement("Update Item set QuantityOnHand =QuantityOnHand-? where ItemCode =?");
							ps.setDouble(1, addItem.iQty);
							ps.setString(2, code);
							
							ps.executeUpdate();
							
							
						}
						if(result>0){
							itemList.clear();
							Total(-1);
							TotalTax(-1);
							String maxInv = "select max(InvoiceNumber) from InvoiceDetail";
							int maxInvNum = 0;
							results = myStmt.executeQuery(maxInv);
							 while(results.next()){
								 maxInvNum = results.getInt(1);
							 }
							 request.getSession().setAttribute("maxInvNum",maxInvNum);
							 
							 	String realPath = getServletContext().getRealPath("/");
							 	
								System.out.println(realPath+" k.sdugf");
								/*String localPath="/Users/kishanpatel/Desktop/Csc 400/InsertDataWebApplication/";
								
							 	Map<String, Object> map = new HashMap<String,Object>();
								//map.put("maxInvNum", maxInvNum);
								JasperReport jasperRreort = JasperCompileManager.compileReport(localPath+"Reports/Invoice_1.jrxml");

								JRDataSource dataSource = new JREmptyDataSource();
								
								JasperPrint jasperPrint = (JasperFillManager.fillReport(jasperRreort, map,MyConn));
								
								File file = new File(localPath);
								file.mkdir();
								
								//JasperExportManager.exportReportToPdfFile(jasperPrint, "/Users/kishanpatel/Desktop/Csc 400/InsertDataWebApplication/Reports/Invoice_1.pdf");
								JasperExportManager.exportReportToPdfFile(jasperPrint, localPath+"/WebContent/Invoice_"+maxInvNum+".pdf");
								*/
								//To Open file after its created
								//Desktop.getDesktop().open(new File(realPath+"WebContent/Invoice_"+maxInvNum+".pdf"));
								System.out.println("Done! Creating Reciept");
								

								request.getRequestDispatcher("/Reg.jsp").forward(request, response);
								//response.getOutputStream().flush();
						}
						results.close();
					 	MyConn.close();
						
						
						///Passing Variables
							
					}
					
					catch (NullPointerException e) {
			           
					}
					
				}
				 
			}
			catch(SQLException e){
				e.printStackTrace();
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
		 catch (Exception e1) {
				// TODO Auto-generated catch block
			 
				e1.printStackTrace();
			} finally  {
				ConnectionManager.closeConnection(MyConn);
			}
			
		 
	}
	 static double Totalsum = 0;
	
	public static double Total (double iPrice){
		
		
			if(iPrice!=-1){
				Totalsum+=iPrice;
				
			}
				else if(iPrice>=-1 && iPrice<0)
				{
					Totalsum=0;
					//System.out.println(Total);
					return Totalsum=0;
				}
		
		return Totalsum;
	}
	static double TotalsumTax = 0;
	public static double TotalTax (double tax){
		
		
		if(tax!=-1){
			TotalsumTax+=tax;
			
		}
			else if(tax>=-1 && tax<0)
			{
				TotalsumTax=0;
				//System.out.println(Total);
				return TotalsumTax=0;
			}
	
	return TotalsumTax;
}
	

}
