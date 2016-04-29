import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileReader;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.*;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserFactory;


// The Mini Web Browser.
public class MiniBrowser extends JFrame
  implements HyperlinkListener
{

     static void renderSplashFrame(Graphics2D g, int frame) {
        final String[] comps = {"HNET", "Search Crawler", "Download manager"};
        g.setComposite(AlphaComposite.Clear);
      //  g.create();
        g.drawRect(380,304,15,5);
        //414,298
        g.setPaintMode();
        g.setColor(Color.BLACK);
        g.drawString("Loading "+comps[(frame/5)%3]+"...", 120, 150);
    }
     
  // These are the buttons for iterating through the page list.
  private JButton backButton, forwardButton;

  // Page location text field.
  private JTextField locationTextField;

  // Editor pane for displaying pages.
  private JEditorPane displayEditorPane;

  // Browser's list of pages that have been visited.
  private ArrayList pageList = new ArrayList();

  // Constructor for Mini Web Browser.
  public MiniBrowser()
  {
    // Set application title.
    super("HNET Browser");

    // Set window size.
    setSize(640, 480);

    // Handle closing events.
    addWindowListener(new WindowAdapter() {
            @Override
      public void windowClosing(WindowEvent e) {
        actionExit();
      }
    });
   ImageIcon exiticon=new ImageIcon(getClass().getResource("/Browser_images/Exit16.gif"));
   ImageIcon newicon=new ImageIcon(getClass().getResource("/Browser_images/Add16.gif"));
   ImageIcon printicon=new ImageIcon(getClass().getResource("/Browser_images/Print16.gif"));
   ImageIcon saveicon=new ImageIcon(getClass().getResource("/Browser_images/save.PNG"));
   ImageIcon findicon=new ImageIcon(getClass().getResource("/Browser_images/Find16.gif"));
   ImageIcon openicon=new ImageIcon(getClass().getResource("/Browser_images/open.gif"));
   ImageIcon cuticon=new ImageIcon(getClass().getResource("/Browser_images/cut.gif"));
    ImageIcon copyicon=new ImageIcon(getClass().getResource("/Browser_images/copy.gif"));
     ImageIcon pasteicon=new ImageIcon(getClass().getResource("/Browser_images/paste.gif"));
     ImageIcon forwardicon=new ImageIcon(getClass().getResource("/Browser_images/arrow2-hover.gif"));
    ImageIcon backicon=new ImageIcon(getClass().getResource("/Browser_images/back.gif"));
    ImageIcon helpicon=new ImageIcon(getClass().getResource("/Browser_images/Help16.gif"));
    ImageIcon infoicon=new ImageIcon(getClass().getResource("/Browser_images/Information16.gif"));
    ImageIcon abouticon=new ImageIcon(getClass().getResource("/Browser_images/About16.gif"));
    // Set up file menu.

    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    fileMenu.setMnemonic(KeyEvent.VK_F);
     JMenuItem newWindowMenuItem = new JMenuItem("New Window");
    newWindowMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
    newWindowMenuItem.setIcon(newicon);
    JMenuItem newOpenMenuItem = new JMenuItem("Open File");
     newOpenMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
    newOpenMenuItem.setIcon(openicon);
     JMenuItem fileExitMenuItem = new JMenuItem("Exit");
    fileExitMenuItem.setIcon(exiticon);
    JMenuItem filePrintMenuItem = new JMenuItem("Print");
    filePrintMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
    filePrintMenuItem.setIcon(printicon);
    JMenuItem editFindMenuItem = new JMenuItem("Find");
     editFindMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
     editFindMenuItem.setIcon(findicon);
     JMenuItem fileSaveMenuItem = new JMenuItem("Save");
     fileSaveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
       fileSaveMenuItem.setIcon(saveicon);
     fileSaveMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
       
      }
    });
     
    
      
     
     
     newWindowMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
       MiniBrowser browser = new MiniBrowser();
    browser.show();
      }
    });
   
    newOpenMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
           JFileChooser fc=new JFileChooser();
           URL fileURL = null;
           int result = fc.showOpenDialog( null );
         if ( result == JFileChooser.APPROVE_OPTION ) // user chose a file
         {
             try
         {
            // get the file as URL
            fileURL = fc.getSelectedFile().toURL();
             System.out.println("great url is created");
             showPage(fileURL,true);
         } // end try
         catch ( MalformedURLException malformedURLException )
         {
            System.err.println( "Could not create URL for the file" );
         } // end catch
         }
      }
    });
     fileExitMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        actionExit();
      }
    });
    filePrintMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
           try {

                            displayEditorPane.print();
                        } catch (PrinterException ex) {
                            Logger.getLogger(MiniBrowser.class.getName()).log(Level.SEVERE, null, ex);
                        }
      }
    });
    editFindMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        //
          MiniBrowser browser = new MiniBrowser();
         JDialog d1;
          d1=new JDialog(browser,"Find ... ");
      d1.setLocation(300,300);
      d1.setSize(300,100);
      d1.setLayout(new FlowLayout());
      d1.add(new Label("Enter the Text to Find"));
      JTextField t3=new JTextField(10);
      d1.add(t3);
      JButton b1=new JButton("Find");
      JButton b2=new JButton("Cancel");
      d1.add(b1);
      d1.add(b2);
    d1.setVisible(true);
 b1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
           //-------------------------very complex logic....problem in disposing object bcoz of inner class..local variable needs to be final
                            //according to its property.


      }
    });
      }


    });
          


    

   fileMenu.add(newWindowMenuItem);
   fileMenu.add(newOpenMenuItem);
       fileMenu.add(fileSaveMenuItem);
     fileMenu.add(filePrintMenuItem);
    fileMenu.add(fileExitMenuItem);
    JMenu toolsMenu = new JMenu("Tools");
    toolsMenu.setMnemonic(KeyEvent.VK_T);
    JMenuItem searchingMenuItem = new JMenuItem("Web Search");
    toolsMenu.add(searchingMenuItem);
    searchingMenuItem.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          new WebSearch();
        }
    });
    JMenuItem searchcrawlerMenuItem = new JMenuItem("Search Crawler");
    toolsMenu.add(searchcrawlerMenuItem);
       searchcrawlerMenuItem.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          new InvokeCrawler();
        }
    });
    JMenuItem downloadmanagerMenuItem = new JMenuItem("Download Manager");
    toolsMenu.add(downloadmanagerMenuItem);
    downloadmanagerMenuItem.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          new InvokeDownloadManager();
        }
    });
    JMenuItem calcMenuItem = new JMenuItem("Simple Calculator");
    toolsMenu.add(calcMenuItem);
    JMenuItem zipperMenuItem = new JMenuItem("Zipper");
    toolsMenu.add(zipperMenuItem);
    zipperMenuItem.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            new InvokeZipper();
        }
    });
    JMenu editMenu = new JMenu("Edit");
    editMenu.setMnemonic(KeyEvent.VK_E);
    TransferActionListener actionListener = new TransferActionListener();
    JMenuItem editCutMenuItem = new JMenuItem("Cut");
    editCutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
     editCutMenuItem.setIcon(cuticon);
     editCutMenuItem.setActionCommand((String)TransferHandler.getCutAction().
                 getValue(Action.NAME));
     editCutMenuItem.addActionListener(actionListener);
    editMenu.add(editCutMenuItem);
    JMenuItem editCopyMenuItem = new JMenuItem("Copy");
    editCopyMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
     editCopyMenuItem.setIcon(copyicon);
     editCopyMenuItem.setActionCommand((String)TransferHandler.getCopyAction().
                 getValue(Action.NAME));
        editCopyMenuItem.addActionListener(actionListener);
    editMenu.add(editCopyMenuItem);
     JMenuItem editPasteMenuItem = new JMenuItem("Paste");
    editPasteMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
     editPasteMenuItem.setIcon(pasteicon);
     editPasteMenuItem.setActionCommand((String)TransferHandler.getPasteAction().
                 getValue(Action.NAME));
        editPasteMenuItem.addActionListener(actionListener);
    editMenu.add(editPasteMenuItem);
    editMenu.add(editFindMenuItem);
      JMenuItem editselectallMenuItem = new JMenuItem("Select All");
    editselectallMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
    // editselectallMenuItem.setIcon(pasteicon);
    editselectallMenuItem.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
         displayEditorPane.selectAll();
        }
    });
    editMenu.add(editselectallMenuItem);
    JMenu helpMenu=new JMenu("Help");
    helpMenu.setMnemonic(KeyEvent.VK_H);
    JMenuItem helpUpdateItem=new JMenuItem("Check For Updates");
    helpUpdateItem.setIcon(helpicon);
    helpUpdateItem.addActionListener(new ActionListener()
            {
        public void actionPerformed(ActionEvent e) {
             try{
                  URL updateLink=new URL("http://www.hnetbrowser.tk");
                   showPage(updateLink, true);
                 
           }
           catch(Exception me)
           {
               System.err.print("error in opening Website"+me);
           }
        }
    });
    JMenuItem helpContactItem=new JMenuItem("Help Contents");
    helpContactItem.setIcon(infoicon);
    helpContactItem.addActionListener(new ActionListener()
            {
        public void actionPerformed(ActionEvent e) {
            try{
Runtime.getRuntime().exec("C://Program Files//CCleaner//CCleaner.exe");
           }
           catch(Exception me)
           {
               System.err.print("error in opening of media player"+me);
           }
        }
    });
    JMenuItem helpAboutItem=new JMenuItem("About");
    helpAboutItem.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        new InvokeAbout();
        }
    });
       helpAboutItem.setIcon(abouticon);
       helpMenu.add(helpUpdateItem);
     
       helpMenu.add(helpContactItem);
       helpMenu.add(helpAboutItem);
    menuBar.add(fileMenu);
    menuBar.add(editMenu);
    menuBar.add(toolsMenu);
    menuBar.add(helpMenu);
    setJMenuBar(menuBar);
    


    // Set up button panel.
    JPanel buttonPanel = new JPanel();
    backButton = new JButton("Back");
    backButton.setIcon(backicon);
    backButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        actionBack();
      }
    });
    backButton.setEnabled(false);
    buttonPanel.add(backButton);
    forwardButton = new JButton("Forward");
    forwardButton.setIcon(forwardicon);
    forwardButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        actionForward();
      }
    });
    forwardButton.setEnabled(false);
    buttonPanel.add(forwardButton);
    locationTextField = new JTextField(35);
    locationTextField.addKeyListener(new KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          actionGo();
        }
      }
    });
    buttonPanel.add(locationTextField);
    JButton goButton = new JButton("GO");
    goButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        actionGo();
      }
    });
    buttonPanel.add(goButton);

    // Set up page display.
    displayEditorPane = new JEditorPane();
    displayEditorPane.setContentType("text/html");
    displayEditorPane.setEditable(false);
    displayEditorPane.addHyperlinkListener(this);

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(buttonPanel, BorderLayout.NORTH);
    getContentPane().add(new JScrollPane(displayEditorPane),
      BorderLayout.CENTER);

     final SplashScreen splash = SplashScreen.getSplashScreen();
        if (splash == null) {
            System.out.println("SplashScreen.getSplashScreen() returned null");
            return;
        }
        Graphics2D g = splash.createGraphics();
        if (g == null) {
            System.out.println("g is null");
            return;
        }
        for(int i=0; i<100; i++) {
            renderSplashFrame(g, i);
            splash.update();
            try {
                Thread.sleep(90);
            }
            catch(InterruptedException e) {
            }
        }
        splash.close();
        toFront();

  }

  // Exit this program.
  private void actionExit() {
    System.exit(0);
  }

  // Go back to the page viewed before the current page.
  private void actionBack() {
    URL currentUrl = displayEditorPane.getPage();
    int pageIndex = pageList.indexOf(currentUrl.toString());
    try {
      showPage(
        new URL((String) pageList.get(pageIndex - 1)), false);
    }
    catch (Exception e) {}
  }

  // Go forward to the page viewed after the current page.
  private void actionForward() {
    URL currentUrl = displayEditorPane.getPage();
    int pageIndex = pageList.indexOf(currentUrl.toString());
    try {
      showPage(
        new URL((String) pageList.get(pageIndex + 1)), false);
    }
    catch (Exception e) {}
  }

  // Load and show the page specified in the location text field.
  private void actionGo() {
    URL verifiedUrl = verifyUrl(locationTextField.getText());
    if (verifiedUrl != null) {
      showPage(verifiedUrl, true);
    } else {
      showError("Invalid URL");
    }
  }

  // Show dialog box with error message.
  private void showError(String errorMessage) {
    JOptionPane.showMessageDialog(this, errorMessage,
      "Error", JOptionPane.ERROR_MESSAGE);
  }

  // Verify URL format.
  private URL verifyUrl(String url) {
    // Only allow HTTP URLs.
    if (!url.toLowerCase().startsWith("http://"))
      return null;

    // Verify format of URL.
    URL verifiedUrl = null;
    try {
      verifiedUrl = new URL(url);
    } catch (Exception e) {
      return null;
    }

    return verifiedUrl;
  }

  /* Show the specified page and add it to
     the page list if specified. */
  private void showPage(URL pageUrl, boolean addToList)
  {
    // Show hour glass cursor while crawling is under way.
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

    try {
      // Get URL of page currently being displayed.
      URL currentUrl = displayEditorPane.getPage();

      // Load and display specified page.
      displayEditorPane.setPage(pageUrl);

      // Get URL of new page being displayed.
      URL newUrl = displayEditorPane.getPage();

      // Add page to list if specified.
      if (addToList) {
        int listSize = pageList.size();
        if (listSize > 0) {
          int pageIndex =
            pageList.indexOf(currentUrl.toString());
          if (pageIndex < listSize - 1) {
            for (int i = listSize - 1; i > pageIndex; i--) {
              pageList.remove(i);
            }
          }
        }
        pageList.add(newUrl.toString());
      }

      // Update location text field with URL of current page.
      locationTextField.setText(newUrl.toString());

      // Update buttons based on the page being displayed.
      updateButtons();
    }
    catch (Exception e)
    {
      // Show error messsage.
      showError("Unable to load page");
    }
    finally
    {
      // Return to default cursor.
      setCursor(Cursor.getDefaultCursor());
    }
  }

  /* Update back and forward buttons based on
     the page being displayed. */
  private void updateButtons() {
    if (pageList.size() < 2) {
      backButton.setEnabled(false);
      forwardButton.setEnabled(false);
    } else {
      URL currentUrl = displayEditorPane.getPage();
      int pageIndex = pageList.indexOf(currentUrl.toString());
      backButton.setEnabled(pageIndex > 0);
      forwardButton.setEnabled(
        pageIndex < (pageList.size() - 1));
    }
  }

  // Handle hyperlink's being clicked.
  public void hyperlinkUpdate(HyperlinkEvent event) {
    HyperlinkEvent.EventType eventType = event.getEventType();
    if (eventType == HyperlinkEvent.EventType.ACTIVATED) {
      if (event instanceof HTMLFrameHyperlinkEvent) {
        HTMLFrameHyperlinkEvent linkEvent =
          (HTMLFrameHyperlinkEvent) event;
        HTMLDocument document =
          (HTMLDocument) displayEditorPane.getDocument();
        document.processHTMLFrameHyperlinkEvent(linkEvent);
      } else {
          showPage(event.getURL(), true);
      }
    }
  }

  // Run the Mini Browser.
  public static void main(String[] args) {

      
//For Liquid Look And feel
     /* try

		{
			File file=new File("C:/Windows/look.hta");

			if(file.exists())
			{
				  FileReader rt=new FileReader("C:/Windows/look.hta");
				   JTextField jt=new JTextField();
				  jt.read(rt,null);
				  rt.close();
				 UIManager.setLookAndFeel(jt.getText());

			}
			else{
			 UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");

			 }
		}

		catch(Exception e)
		{ System.out.println("Exception in UI"); }
 */

      /* try {
    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
    if ("Nimbus".equals(info.getName())) {
    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }*/
try
{
    UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
}
catch(Exception e)
{
    System.out.println("error in Look and feel");
}

    MiniBrowser browser = new MiniBrowser();
    browser.show();
    browser.setBounds(new java.awt.Rectangle(200, 100,700,550));
    
  }
}
class WebSearch extends NewJFrame
{
    public WebSearch()
    {
        new NewJFrame().setVisible(true);
    }
}
class InvokeCrawler extends SearchCrawler
{
    public InvokeCrawler()
    {
     SearchCrawler crawler = new SearchCrawler();
    crawler.show();
    }
}
class InvokeDownloadManager extends DownloadManager
{
    public InvokeDownloadManager()
    {        
        DownloadManager manager = new DownloadManager();
    manager.show();
    }
}
class InvokeAbout extends About
{
    public InvokeAbout()
    {
       new About().setVisible(true);
       
    }
}
class InvokeZipper extends Zipper
{
    public InvokeZipper()
    {
       Zipper z = new Zipper();
       z.main(col);
    }
}
