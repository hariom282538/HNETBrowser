package com.teamdev.jxbrowser.chromium.demo;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class About_1 extends JFrame 
{
JLabel lb1,lb3,lb4,lb6,lb7;
Font f;
Icon ic;

public About_1()
{
       
super.setLayout(null);
 
        f=new Font("Verdana",Font.BOLD,25);
        lb1=new JLabel("About HNET Zipper");
        lb1.setFont(f);
		
      
              

        f=new Font("papyrus",Font.BOLD,20);
        lb3=new JLabel("Version 1.0");
        lb3.setFont(f);
        
        
	f=new Font("Verdana",Font.BOLD,15);
        lb4=new JLabel("Coded By:- Hariom Vashisth");
        lb4.setFont(f);
        

        
        
	ic = new ImageIcon(getClass().getResource("Anything_Zip.png"));
        lb6 = new JLabel(ic);
        
        f=new Font("Times New Roman",Font.BOLD,15);
        
	lb7=new JLabel("<html><font color='red'><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Copyright &copy; 2013 by HNET Browser");
        lb7.setFont(f);


this.add(lb1);

this.add(lb3);
this.add(lb4);

this.add(lb6);
this.add(lb7);

lb1.setBounds(100,40,300,25);

lb3.setBounds(150,120,200,25);
lb4.setBounds(1,160,380,30);

lb6.setBounds(110,220,200,100);
lb7.setBounds(15,320,380,30);

}

public static void main(String a[])
{

 try 
     {

      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
      if ("Nimbus".equals(info.getName())) {
      javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
      } catch (Exception ex) 
	  {
            System.out.println(ex.toString());
          }
             
About_1 ab = new About_1();
ab.setVisible(true);
ab.setSize(415,400);
ab.setResizable(false);
ab.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
}