import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class DomesticFlight extends JFrame
{
	JComboBox CBFrom, CBTo, CBClass, CBAdult, CBChildren, CBInfant;
	JLabel LFrom, LTo, LBookingDate, LClass, LAdult, LChildren, LInfant, LBookingDetails, LPassengerDetails, LDate, LImg1, LImg2, LNotes;
	JTextField TFBookingDate;
	Icon img1, img2;
	JButton BFindFlight;
	JPanel PPanel1, PPanel2;

	LoginPage type1;

	public DomesticFlight(LoginPage type1)
	{
		Container c =getContentPane();
		c.setLayout(new BorderLayout());
		String[] sItem1={"Dhaka"};
		String[] sItem2 ={ "Chittagong", "Rajshahi ", "Barishal", "Sylhet", "Mymanshingh", "Kolkata", "Lucknow", "Mumbai", "Goa" };
		String[] sItem3={"Economic","Business"};

		this.type1 = type1;
		PPanel1 = new JPanel(null);
		PPanel1.setPreferredSize(new Dimension(500,200));

			}
	public static void main(String args[])
	{
		LoginPage type1=null;
		new DomesticFlight(type1);
	}
}

class button3 implements ActionListener
{
	DomesticFlight type;
	LoginPage type1;
	button3(DomesticFlight type, LoginPage type1)
	{
		this.type = type;
		this.type1 = type1;
	}
	public void actionPerformed(ActionEvent e)
	{
		String sFrom = (String)type.CBFrom.getSelectedItem();
		String sTo = (String)type.CBTo.getSelectedItem();
		String sClass = (String)type.CBClass.getSelectedItem();
		String sBookingDate = type.TFBookingDate.getText();
		Integer iPrice=0;
		String sTime="";


		Integer iAdult = Integer.parseInt((String)type.CBAdult.getSelectedItem());
		Integer iChildren = Integer.parseInt((String)type.CBChildren.getSelectedItem());
		Integer iInfant = Integer.parseInt((String)type.CBInfant.getSelectedItem());

		int i = 0;

		if(sClass.equals("Economic"))
		{
			try{
				while(i<20)
				{
					if(type1.row1[i][1].equals(sTo))
					{
						iPrice = Integer.parseInt((String)type1.row1[i][2]);
						sTime = (String)type1.row1[i][3];
						break;
					}
					i++;
				}
			}catch(Exception e1)
			{
				JOptionPane.showMessageDialog(null, "You have no rights to access");
				System.exit(0);
			}
		}
		else
		{
			try
			{
				while(i<20)
				{
					if(type1.row1[i][1].equals(sTo))
					{
						iPrice = Integer.parseInt((String)type1.row3[i][2]);
						sTime = (String)type1.row3[i][3];
						break;
					}
					i++;
				}
			}catch(Exception e1)
			{
				JOptionPane.showMessageDialog(null, "You have no rights to access it");
				System.exit(0);
			}
		}
		type.setTitle(iPrice + " " + sTime);

		iPrice = (iPrice*iAdult)+(iPrice*(iChildren/2));

		int iCount=0;
		int iSeatCount=0;

		String[] sTempFrom=new String[1250];
		String[] sTempTo=new String[1250];
		String[] sTempClass=new String[1250];
		String[] sTempBookingDate=new String[1250];
		String[] sTempTime=new String[1250];
		Integer[] iTempAdult=new Integer[1250];
		Integer[] iTempChildren=new Integer[1250];
		Integer[] iTempInfant=new Integer[1250];
		Integer[] iTempPrice=new Integer[1250];

		try
		{
//read from data
			Save2 save1;
			ObjectInputStream OIS1 = new ObjectInputStream(new FileInputStream("save2"));
			do
			{
				save1 = (Save2)OIS1.readObject();
				sTempFrom[iCount] = save1.sFrom;
				sTempTo[iCount] = save1.sTo;
				sTempClass[iCount] = save1.sClass;
				sTempBookingDate[iCount] = save1.sBookingDate;
				sTempTime[iCount] = save1.sTime;
				iTempAdult[iCount] = save1.iAdult;
				iTempChildren[iCount] = save1.iChildren;
				iTempInfant[iCount] = save1.iInfant;
				iTempPrice[iCount] = save1.iPrice;

				iCount++;
				if(save1.sBookingDate.equals(sBookingDate))
					if(save1.sTo.equals(sTo))
						iSeatCount=iSeatCount + save1.iAdult + save1.iChildren + save1.iInfant;
			}while(save1!=null);
			OIS1.close();

		}
		catch(Exception e1)
		{
		}

		iSeatCount = iSeatCount + iAdult + iChildren + iInfant;

		if(iSeatCount > 60)
		{
			JOptionPane.showMessageDialog(null,"Seats are full. Sorry!");
		}
		else
		{
			int iChoice = JOptionPane.showConfirmDialog(null,"Seats available. Do you want to Book now?");
			if(iChoice == JOptionPane.YES_OPTION)
			{
				new PrintTicket1(sFrom, sTo, sClass, iAdult, iChildren, iInfant, sBookingDate, iPrice, sTime);
			try
			{
//write into data
				Save2 save2=new Save2(sFrom, sTo, sClass, iAdult, iChildren, iInfant, sBookingDate, iPrice, sTime);
				ObjectOutputStream OOS1 = new ObjectOutputStream(new FileOutputStream("save2"));
				for(i=0;i<iCount;i++)
				{
					Save2 temp1=new Save2(sTempFrom[i], sTempTo[i], sTempClass[i], iTempAdult[i], iTempChildren[i], iTempInfant[i], sTempBookingDate[i], iTempPrice[i], sTempTime[i]);
					OOS1.writeObject(temp1);
System.out.println(temp1);
				}
				OOS1.writeObject(save2);
				OOS1.close();
			}catch(Exception e1)
			{
				System.out.println(e1);
			}
			}
			else
			{
			}
		}
	}
}


class Save2 implements Serializable
{
	
	}
	public String toString()
	{
		return sFrom+" "+sTo+" "+sClass+" "+iAdult+" "+iChildren+" "+iInfant+" "+sBookingDate+" "+iPrice+" "+sTime;
	}
}