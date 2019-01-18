import java.sql.*;
import java.util.Scanner;
public class SqlBanking
{
	public static void main(String[] args) 
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			Connection conn=DriverManager.getConnection
					("jdbc:mysql://127.0.0.1:3306/fundtransfer", "root", "1234");
			Statement st1=conn.createStatement();
			Statement st2=conn.createStatement();
			Scanner s=new Scanner(System.in);
			String sql1="select * from sbi";
			String sql2="select * from pnb";
			String sql3="select * from ifsccode";
			System.out.println(" ## MENU ## ");
			System.out.println("1:SBI TO SBI"+"\n2:SBI TO PNB"+"\n3.PNB TO PNB"+"\n4.PNB TO SBI");
			int choice=s.nextInt();
			switch(choice)
			{
			case 1:
				System.out.println("your name-");
				String sname=s.next();
				System.out.println("your id-");
				int sid=s.nextInt();
				System.out.println("amount-to-transfer");
				int fund=s.nextInt();
				System.out.println("receiver id-");
				int rid=s.nextInt();
				System.out.println("receiver name-");
				String rname=s.next();
				ResultSet rs=st1.executeQuery("select * from sbi");
				while(rs.next())
				{
					System.out.println("check1");
					int sqsid=rs.getInt("id");
					String sqsname=rs.getString("name");
					int sqsbal=rs.getInt("amount");
					if(sname.equalsIgnoreCase(sqsname)&&sid==sqsid)
					{
						if(sqsbal<fund)
						{
							System.out.println("insufficient balance:");	
						}
						else
						{
							ResultSet rs1=st1.executeQuery(sql1);
							while(rs1.next())
							{
								int sqrid=rs1.getInt("id");
								String sqrname=rs1.getString("name");
								int sqrbal=rs1.getInt("amount");
									if(rname.equalsIgnoreCase(sqrname)&&rid==sqrid)
									{
									
									 String query="update sbi set amount=? where id=?";
									 sqrbal=sqrbal+fund; 
									 PreparedStatement ps=conn.prepareStatement(query);
									 ps.setInt(2,sqrid);
									 ps.setInt(1,sqrbal);
									 System.out.println("ok");
									 ps.executeUpdate();
									 System.out.println("//");
									 sqsbal=sqsbal-fund;
									 ps=conn.prepareStatement(query);
									 ps.setInt(2,sqsid);
									 ps.setInt(1, sqsbal);
									 ps.executeUpdate();
									 System.out.println("record updated");
									 System.out.println("fund transferred");
									 System.out.println("recev.balance="+sqrbal+"transfer balance="+sqsbal);
									 conn.close();
									}
							}
						}
					}
				}
									break;
			case 2:
				System.out.println("Enter  source ifsc code:");
				String ifsc=s.next();
				System.out.println("enter bank name:");
				String bank=s.next();
				System.out.println("source name-");
				String sname1=s.next();
				System.out.println("source id-");
				int sid1=s.nextInt();
				System.out.println("amount-to-transfer");
				int fund1=s.nextInt();
				System.out.println("receiver id-");
				int rid1=s.nextInt();
				System.out.println("receiver name-");
				String rname1=s.next();
				boolean valid = false;
				ResultSet vs1=st1.executeQuery(sql3);
				while(vs1.next())
				{
					if(ifsc.equalsIgnoreCase(vs1.getString("ifsc"))&&bank.equalsIgnoreCase(vs1.getString("bankname"))) {
						valid=true;
						System.out.println("valid:");
					}
				}
				ResultSet rs1=st1.executeQuery(sql1);
				while(rs1.next())
				{
					int sqsid=rs1.getInt("id");
					String sqsname=rs1.getString("name");
					int sqsbal=rs1.getInt("amount");
			if(valid) {
					if(sname1.equalsIgnoreCase(sqsname)&&sid1==sqsid)
					{
						if(sqsbal<fund1)
						{
							System.out.println("insufficient balance:");	
						}
						else
						{
							ResultSet rs2=st2.executeQuery(sql2);
							while(rs2.next())
							{
								int sqrid=rs2.getInt("id");
								String sqrname=rs2.getString("name");
								int sqrbal=rs1.getInt("amount");
									if(rname1.equalsIgnoreCase(sqrname)&&rid1==sqrid)
									{
									 sqrbal=sqrbal+fund1;
									 PreparedStatement ps=conn.prepareStatement("update pnb set amount=? where id=?");
									 ps.setInt(2,sqrid);
									 ps.setInt(1,sqrbal);
									 ps.executeUpdate();
									 sqsbal=sqsbal-fund1;
									 ps=conn.prepareStatement("update sbi set amount=? where id=?");
									 ps.setInt(2,sqsid);
									 ps.setInt(1, sqsbal);
									 System.out.println("check2");
									 ps.executeUpdate();
									 System.out.println("record updated");
									 System.out.println("fund transferred");
									 conn.close();
							
									}
							}
						}
					}
			}
			else
					System.out.println("unathorised bank or ifsc code:");
				}
				
			break;
			case 3:
				System.out.println("your name-");
				String sname2=s.next();
				System.out.println("your id-");
				int sid2=s.nextInt();
				System.out.println("amount-to-transfer");
				int fund2=s.nextInt();
				System.out.println("receiver id-");
				int rid2=s.nextInt();
				System.out.println("receiver name-");
				String rname2=s.next();
				ResultSet rs3=st1.executeQuery(sql2);
				while(rs3.next())
				{
					int sqsid=rs3.getInt("id");
					String sqsname=rs3.getString("name");
					int sqsbal=rs3.getInt("amount");
					if(sname2.equalsIgnoreCase(sqsname)&&sid2==sqsid)
					{
						if(sqsbal<fund2)
						{
							System.out.println("insufficient balance:");	
						}
						else
						{
							ResultSet rs4=st1.executeQuery(sql2);
							while(rs4.next())
							{
								System.out.println("ss");
								int sqrid=rs4.getInt("id");
								String sqrname=rs4.getString("name");
								int sqrbal=rs4.getInt("amount");
									if(rname2.equalsIgnoreCase(sqrname)&&rid2==sqrid)
									{
									 sqrbal=sqrbal+fund2;
									 PreparedStatement ps=conn.prepareStatement("update pnb set amount=? where id=?");
									 ps.setInt(2,sqrid);
									 ps.setInt(1,sqrbal);
									 ps.executeUpdate();
									 sqsbal=sqsbal-fund2;
									 ps=conn.prepareStatement("update pnb set amount=? where id=?");
									 ps.setInt(2,sqsid);
									 ps.setInt(1, sqsbal);
									 ps.executeUpdate();
									 System.out.println("record updated");
									 System.out.println("fund transferred");
									 conn.close();
									}
							}
						}
					}
				}
			break;
				
			case 4:
				boolean valid1=true;
				System.out.println("Enter  source ifsc code:");
				String ifsc1=s.next();
				System.out.println("enter bank name:");
				String bank1=s.next();
				System.out.println("your name-");
				String sname3=s.next();
				System.out.println("your id-");
				int sid3=s.nextInt();
				System.out.println("amount-to-transfer");
				int fund3=s.nextInt();
				System.out.println("receiver id-");
				int rid3=s.nextInt();
				System.out.println("receiver name-");
				String rname3=s.next();
				
			ResultSet vs2=st1.executeQuery(sql3);
				while(vs2.next())
				{
					if(ifsc1.equalsIgnoreCase(vs2.getString("ifsc"))&&bank1.equalsIgnoreCase(vs2.getString("bankname")))
					{
						valid1=true;
						System.out.println("valid");
					}
				}
				ResultSet rs5=st1.executeQuery(sql2);
				while(rs5.next())
				{
					int sqsid=rs5.getInt("id");
					String sqsname=rs5.getString("name");
					int sqsbal=rs5.getInt("amount");
				if(valid1) {
					if(sname3.equalsIgnoreCase(sqsname)&&sid3==sqsid)
					{
						if(sqsbal<fund3)
						{
							System.out.println("insufficient balance:");	
						}
						else
						{
							ResultSet rs6=st2.executeQuery(sql1);
							while(rs6.next())
							{
								
								int sqrid=rs6.getInt("id");
								String sqrname=rs6.getString("name");
								int sqrbal=rs6.getInt("amount");
									if(rname3.equalsIgnoreCase(sqrname)&&rid3==sqrid)
									{
									 sqrbal=sqrbal+fund3;
									 PreparedStatement ps=conn.prepareStatement("update sbi set amount=? where id=?");
									 ps.setInt(2,sqrid);
									 ps.setInt(1,sqrbal);
									 ps.executeUpdate();
									 sqsbal=sqsbal-fund3;
									 ps=conn.prepareStatement("update pnb set amount=? where id=?");
									 ps.setInt(2,sqsid);
									 ps.setInt(1, sqsbal);
									 ps.executeUpdate();
									 System.out.println("record updated");
									 System.out.println("fund transferred"+sqrbal+":"+sqsbal);
									 conn.close();
									}
							}
						}
					}}
				
		else
			System.out.println("UNATHORISED IFSC CODE OR BANK NAME:");
				}
			break;
			
			default:
					System.out.println("WRONG CHOICE ::");
					break;
			}
			
			
			conn.close();
		}
		
		
		
		catch (Exception e) {
			// TODO: handle exception
		}
}}
		