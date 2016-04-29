import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.io.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import javax.swing.JTable;
import javax.swing.table.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.HeadlessException;
import java.io.IOException;
import java.util.zip.*;
                             

public class Zipper extends JFrame implements ActionListener
{
JLabel lb,lb1,lb2,lb3; 
File fname;
Font f;
Thread th;

javax.swing.JTextField ZipOutDir;
JButton bt,bt1,bt2,bt3;

progress ob=new progress();

BufferedInputStream input = null;
ZipOutputStream output = null;
File SelectedZipFile = null, delFile = null, CreatedZipFile = null;
String pathArray[] = null;
long totalZipSize = 0;
long curZipSize = 0;
int b = 0;
String CurAdd;

String data[][]={};
String col[]={"Name","Type","Size","Location"};
DefaultTableModel modeltable = new DefaultTableModel(data,col);

JTable table = new JTable(modeltable);
JScrollPane pane = new JScrollPane(table);

String zipdata[][] = {};
String zipcol[] = {"Name", "Size", "Location"};
DefaultTableModel Ziptable = new DefaultTableModel(zipdata,zipcol);   


JTable tab=new JTable(Ziptable);
JScrollPane pane1=new JScrollPane(tab);


public void dir(File[] list, ZipOutputStream zos, String PrevAdd) {

        for (int i = 0; i < list.length; i++) {

            if (list[i].isDirectory()) {
                try {
                    String cur = PrevAdd + "/" + list[i].getName();
                    zos.putNextEntry(new ZipEntry(cur + "\\"));
                    dir(list[i].listFiles(), zos, cur);
                } catch (IOException ex) {
                    System.out.println("folder not created");
                }

            } else {
                try {
                    input = new BufferedInputStream(new FileInputStream(list[i]));
                    long curFiletotalSize = list[i].length();
                    long curFileincSize = 0;
                    byte data[] = new byte[1024];
                    int s;
                    //curZipSize+=fname.length();
                    String cur = PrevAdd + "/" + list[i].getName();
                    zos.putNextEntry(new ZipEntry(cur));
                    int d;
                    ob.pb1.setValue(0);
                    ob.pb1perc.setText("");
                    while ((s = input.read(data, 0, 1024)) != -1) {
                        curFileincSize += s;
                        zos.write(data, 0, s);
                        d = (int) (((float) curFileincSize / (float) curFiletotalSize) * 100);
                        ob.pb1.setValue(d);
                        ob.pb1perc.setText("" + d);
                        curZipSize += s;
                        b = (int) (((float) curZipSize / (float) totalZipSize) * 100);
                        ob.pb2.setValue(b);
                        ob.pb2perc.setText(b + "%");
                    }
                    input.close();

                    zos.closeEntry();
                    zos.flush();

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(rootPane, "folder problem");
                }
            }
        }
    }


public long DirSize(File[] folder) 
       {
        long dirsize = 0;
        for (int i = 0; i < folder.length; i++)
          {
            if (folder[i].isDirectory())
                {
                   DirSize(folder[i].listFiles());
                 }
            else {
                dirsize += folder[i].length();
                 }
                } 
        return dirsize;
    }

public Zipper()
{
super.setLayout(null);
setTitle("Welcome To HNET Zipper");

 JMenuBar menuBar = new JMenuBar();

    // File Menu, F - Mnemonic
    JMenu fileMenu = new JMenu("File");
    fileMenu.setMnemonic(KeyEvent.VK_F);
    menuBar.add(fileMenu);


JMenu aboutMenu = new JMenu("About");
    aboutMenu.setMnemonic(KeyEvent.VK_A);
    menuBar.add(aboutMenu);



    // File->New, N - Mnemonic
    JMenuItem newMenuItem = new JMenuItem("New", KeyEvent.VK_N);
    newMenuItem.setAccelerator(KeyStroke.getKeyStroke("control N"));
    newMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewMenuItemActionPerformed(evt);
            }
        });

    fileMenu.add(newMenuItem);


JMenuItem exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
     exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,InputEvent.ALT_MASK));
     exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitMenuItemActionPerformed(evt);
            }
        });
    fileMenu.add(exitMenuItem);


    // About->MyZipper, M - Mnemonic
  JMenuItem aboutMenuItem= new JMenuItem("About MyZipper", KeyEvent.VK_M);
  aboutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AboutMenuItemActionPerformed(evt);
            }
        });
    aboutMenu.add(aboutMenuItem); 




f=new Font("Verdana",Font.BOLD,40);
lb=new JLabel("Welcome To HNET-Zipper");
lb.setFont(f);

f=new Font("TimesRoman",Font.ITALIC,20);
lb1=new JLabel("Files Going to be Compressed");
lb1.setFont(f);

f=new Font("TimesRoman",Font.ITALIC,20);
lb2=new JLabel("Compressed Files");
lb2.setFont(f);

f=new Font("TimesRoman",Font.ITALIC,20);
lb3=new JLabel("Output");
lb3.setFont(f);


bt=new JButton("Add");
bt1=new JButton("Remove");
bt2=new JButton("Remove All");
bt3=new JButton("Compress");
ZipOutDir = new JTextField(10);


this.add(menuBar);
this.add(lb);
this.add(lb1);
this.add(lb2);
this.add(lb3);
this.add(bt);
bt.addActionListener(this);
this.add(bt1);
bt1.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveButtonActionPerformed(evt);
            }
        });
this.add(bt2);
bt2.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveAllButtonActionPerformed(evt);
            }
        });
this.add(bt3);
bt3.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CompressButtonActionPerformed(evt);
            }
        });


this.add(pane);
this.add(pane1);
this.add(ZipOutDir);
this.setResizable(false);

lb.setBounds(180,30,700,200);
lb1.setBounds(265,220,450,30);
lb2.setBounds(265,500,450,30);
lb3.setBounds(265,600,450,30);
menuBar.setBounds(0,0,1200,25);
bt.setBounds(700,280,100,25);
bt1.setBounds(700,320,100,25);
bt2.setBounds(700,360,100,25);
bt3.setBounds(700,530,100,25);

pane.setBounds(170,250,450,200);
pane1.setBounds(170,530,450,50);
ZipOutDir.setBounds(170,630,450,40);

}
public void actionPerformed(ActionEvent ae)
{
 Object o = ae.getSource();
	if(o==bt)
	{

        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jfc.setMultiSelectionEnabled(true);
        int returnVal = jfc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) 
        {
        File incoming[] = jfc.getSelectedFiles();
        for (int i = 0; i < incoming.length; i++) 
        {
        String type = FileSystemView.getFileSystemView().getSystemTypeDescription(incoming[i]);

        if (incoming[i].isDirectory()) 
        {

        modeltable.addRow(new Object[]{incoming[i].getName(), type, DirSize(incoming[i].listFiles()) / 1024 + " KB", incoming[i].getParent()});
        }
        else 
        {
        modeltable.addRow(new Object[]{incoming[i].getName(), type, incoming[i].length() / 1024 + " KB", incoming[i].getParent()});         
        } 
        }
        table.clearSelection();
        table.setRowSelectionInterval(0, 0);
        ZipOutDir.setText(table.getValueAt(0, 3) + "");

                                           
       }
}
}
private void RemoveButtonActionPerformed(ActionEvent evt)
{
 Object o = evt.getSource();
	if(o==bt1)
	{
        int RowRemove = table.getSelectedRow();
        modeltable.removeRow(RowRemove);

        }
}
private void RemoveAllButtonActionPerformed(ActionEvent evt)
{
 Object o = evt.getSource();
	if(o==bt2)
         {
           for (int i = table.getRowCount(); i > 0; i--) 
            {
             modeltable.removeRow(i - 1);
            }
         } 
}

private void CompressButtonActionPerformed(ActionEvent evt)
{
 Object o = evt.getSource();
	if(o==bt3)
{
         ob.setVisible(true);
         th = new Thread(new Runnable()
{

         @Override
         public void run() 
{

           try 
{
          File temp = new File(table.getValueAt(0, 3).toString() + "/" + table.getValueAt(0, 0));

          if (temp.isDirectory()) 
{
          output = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream                    (ZipOutDir.getText() + "/" + temp.getName() + ".zip")));
          ob.mainFile.setText(temp + ".zip");
          CreatedZipFile = new File(ZipOutDir.getText() + "/" + temp.getName() + ".zip");
}
else 
{
          int last = temp.getName().lastIndexOf(".");
          output = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream                   (ZipOutDir.getText() + "/" + temp.getName().substring(0, last) + ".zip")));
          ob.mainFile.setText(temp.getName().substring(0, last) + ".zip");
          CreatedZipFile = new File(ZipOutDir.getText() + "/" + temp.getName().substring(0, last) + ".zip");
}
          ob.pb2.setValue(0);
          ob.totalFiles.setText(table.getRowCount() + "");
          ob.setVisible(true);
          for (int i = 0; i < table.getRowCount(); i++) 
{
                    File f = new File(table.getValueAt(i, 3) + "/" + table.getValueAt(i, 0));
                    if (f.isDirectory()) 
{
                            totalZipSize += DirSize(f.listFiles());
} 
else 
{
                     totalZipSize += f.length();
}
}
                    for (int i = 0; i < table.getRowCount(); i++) 
{
                    String address = table.getValueAt(i, 3).toString() + "\\" + table.getValueAt(i, 0).toString();

                    File fname = new File(table.getValueAt(i, 3).toString() + "/" + table.getValueAt(i, 0));
                    if (fname.isDirectory()) 
{

                    String add = table.getModel().getValueAt(i, 3).toString() + "\\" + table.getModel().getValueAt(i, 1).toString();
                    output.putNextEntry(new ZipEntry(fname.getName() + "\\"));
                    ob.currentFile.setText(table.getModel().getValueAt(i, 0).toString());
                    ob.reminFile.setText("" + (table.getRowCount() - i));
                    dir(fname.listFiles(), output, fname.getName());
} 
else 
{
                    input = new BufferedInputStream(new FileInputStream(fname));
                    ob.currentFile.setText(table.getModel().getValueAt(i, 0).toString());
                    ob.reminFile.setText("" + (table.getRowCount() - i));
                    long curFiletotalSize = fname.length();
                    long curFileincSize = 0;
                    byte data[] = new byte[1024];
                    int n;
                    output.putNextEntry(new ZipEntry(fname.getName()));
                    int a;
                    ob.pb1.setValue(0);
                    ob.pb1perc.setText("");
                    while ((n = input.read(data, 0, 1024)) != -1)
{
                    curFileincSize += n;
                    output.write(data, 0, n);
                    a = (int) (((float) curFileincSize / (float) curFiletotalSize) * 100);
                    ob.pb1.setValue(a);
                    ob.pb1perc.setText(a + "%");
                    curZipSize += n;
                    b = (int) (((float) curZipSize / (float) totalZipSize) * 100);
                    ob.pb2.setValue(b);
                    ob.pb2perc.setText(b + "%");
}
}
}
                   
                    Ziptable.addRow(new Object[]{ CreatedZipFile.getName(), CreatedZipFile.length() / 1024 + " KB", CreatedZipFile.getParent()});

                    input.close();
                    output.closeEntry();
                    output.flush();
                    output.close();
                    ob.setVisible(false);
                    JOptionPane.showMessageDialog(rootPane, "File Compressed successfully");
} catch (IOException ex) 
{
                    ex.printStackTrace();
} catch (Exception ex) 
{
                    ex.printStackTrace();
}
}
});
        if (table.getRowCount() > 0) 
{
            th.start();
}
}
}
private void NewMenuItemActionPerformed(ActionEvent evt)
{
          Zipper a =new Zipper();
          a.setVisible(true);
          a.setSize(850,730);
	  a.setResizable(false);
          a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          this.dispose();
}
private void ExitMenuItemActionPerformed(ActionEvent evt)
{
          System.exit(0);
}
private void AboutMenuItemActionPerformed(ActionEvent evt)
{
           About_1 ab =new About_1();
           ab.setVisible(true); 
           ab.setSize(415,400);
           ab.setResizable(false);
           
}

public static void main(String arg[])
{
Zipper a=new Zipper();
a.setSize(850,730);
a.setVisible(true);

a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
}
}