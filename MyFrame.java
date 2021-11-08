package gouvo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 *
 * @author gouvo
 */
public class MyFrame extends JFrame{
    
    private ImageIcon icon1;
    private JLabel l1, pathLabel;
    private JPanel p1,p2,p3;
    private JTextArea area;
    private JScrollPane scrollpane;
    private JButton newOrder,saveOrders,saveAsOrders,loadOrders,statistics,about,clearbtn,exit,info;
    private JMenuBar mb;
    private JMenu menu;
    private JMenuItem inewOrder,isaveOrders,isaveAsOrders,iloadOrders,istatistics,iabout,iclearbtn,iexit; // Όλα τα συστατικά για το menu
    
    private String fileName,tempFileName=null;
    
    private boolean saving = false; // Το boolean αυτό θα χρησιμοποιηθεί για την αποθήκευση των αρχείων,
                                    // ώστε να γίνεται έλεγχος για το αν ο χρήστης έχει ανοίξει κάποιο αρχείο.
    public ArrayList<Orders> ordersList;
    
    String myAppID = "18390193"; // Είναι το String που εμπεριέχει τον ΑΜ μου. Αν το appID είναι αυτό, τότε δεν εμφανίζεται στο κεντρικό παράθυρο.


   
    public MyFrame(){
        icon1 = new ImageIcon("Desktop.png"); // Η εικόνα της επιφάνειας εργασίας μου κατά την διάρκεια
        
        pathLabel = new JLabel("Opened File Path: ");
        l1 = new JLabel();
        p1 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();
        
        area = new JTextArea(10,50);  
        scrollpane = new JScrollPane(area);             // Με το scrollpane μπορούμε να κάνουμε ευκολότερη την πλοήγηση 
                                                        //όσο αναφορά το κεντρικό παράθυρο και τις παραγγελίες
        ordersList = new ArrayList<Orders>();
        
        newOrder = new JButton("New Order");
        saveOrders = new JButton("Save");
        saveAsOrders = new JButton("Save As");
        loadOrders = new JButton("Load Orders");
        statistics = new JButton("Statistics");
        about = new JButton("About");
        clearbtn = new JButton("Clear");
        exit = new JButton("Exit");
        info = new JButton("Information");
        
        mb = new JMenuBar();
        menu = new JMenu("Menu");
        inewOrder = new JMenuItem("New Order");  
        isaveOrders = new JMenuItem("Save");  
        isaveAsOrders = new JMenuItem("Save As");  
        iloadOrders = new JMenuItem("Load Orders");  
        istatistics = new JMenuItem("Statistics");  
        iabout = new JMenuItem("About");  
        iclearbtn = new JMenuItem("Clear");
        iexit = new JMenuItem("Exit");  
    }
        
    public void prepareUI(){
        p1.setLayout(new FlowLayout(FlowLayout.CENTER));
        p1.add(newOrder);
        p1.add(saveOrders);
        p1.add(saveAsOrders);
        p1.add(loadOrders);
        p1.add(statistics);
        p1.add(about);
        p1.add(clearbtn);
        p1.add(exit);
        
        menu.add(inewOrder); 
        menu.add(isaveOrders); 
        menu.add(isaveAsOrders);  
        menu.add(iloadOrders);
        menu.add(istatistics);
        menu.add(iabout);
        menu.add(iclearbtn);
        menu.add(iexit);
        
        mb.add(menu);  
        this.setJMenuBar(mb);
        
        p2.setLayout(new FlowLayout(FlowLayout.CENTER));
        p2.add(scrollpane);             // Δεν χρειάζεται να τοποθετήσουμε το JTextArea διότι το έχουμε βάλει μέσα στο JScrollPane.
        p2.add(info);
        area.setEditable(false);
        p3.add(pathLabel);
        
        this.add(p1,BorderLayout.SOUTH); // Το πάνελ αυτό εμπεριέχει όλα τα κουμπιά.
        this.add(p2,BorderLayout.CENTER); // Το πάνελ αυτό εμπεριέχει το TextArea.
        this.add(p3,BorderLayout.NORTH); // Το πάνελ αυτό εμπεριέχει το String με το path του αρχείου που ανοίχτηκε (άν υπάρχει)
        this.setTitle("Order Handler");
        this.setVisible(true);
        this.setSize(700,300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        newOrder.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewOrder();
            }
        });
        
        saveOrders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFunction();
            }
        });
        
        saveAsOrders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAsFunction();
            }
        });
        
        loadOrders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(loadOrders);
                saving = true;
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    fileName = fc.getSelectedFile().getPath();

                    if (fileName != null && !fileName.isEmpty()) {
                        loadFromFile(fileName);             // Mε την κλήση αυτής της μεθόδου, θα φορτόσουμε τις παραγγελίες στο πρόγραμμά μας.
                        
                        pathLabel.setText("Opened File Path: "+fileName);
                        tempFileName = fileName;
                    }
                }
            }
        });
        
        statistics.addActionListener(new ActionListener(){      // Όλοι οι υπολογισμοί για τα στατιστικά γίνονται εδώ.
            @Override
            public void actionPerformed(ActionEvent e) {
                showsStatistics();
            }
        });
        
        about.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                aboutFunc();
            }
        });
        
        clearbtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                clearArea();
            }
        });
        
        exit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the program?","Exit program",JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION){ // Χρησιμοποιούμε το input του χρήστη 
                    System.exit(0);
                }
            }
        });
        
        info.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame information = new JFrame();
                information.setTitle("Information about the app");
                JLabel header = new JLabel("These are the shortcuts for the application:");
                JLabel i1 = new JLabel("Add a new order : N");
                JLabel i2 = new JLabel("Save the orders : S");
                JLabel i3 = new JLabel("Load orders from file : L");
                JLabel i4 = new JLabel("Display statistics : I");
                JLabel i5 = new JLabel("Display information about the developer : A");
                JLabel i6 = new JLabel("Clear all the orders : C");
                JLabel i7 = new JLabel("Exit the application : E");
                information.setLayout(new GridLayout(0,1));
                header.setFont(new Font ("TimesRoman", Font.BOLD | Font.ITALIC, 18));
                
                information.add(header);
                information.add(i1);
                information.add(i2);
                information.add(i3);
                information.add(i4);
                information.add(i5);
                information.add(i6);
                information.add(i7);
                information.setVisible(true);
                information.setLocationRelativeTo(null);
                information.setSize(400,250);
            }
            
        });
        
        inewOrder.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewOrder();
            }
        });
                
        inewOrder.setAccelerator(KeyStroke.getKeyStroke("N"));
                
        isaveOrders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFunction();
                
            }
        });
        
        isaveOrders.setAccelerator(KeyStroke.getKeyStroke("S"));
        
        isaveAsOrders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAsFunction();
            }
        });
        
        iloadOrders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(loadOrders);
                saving = true;

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    fileName = fc.getSelectedFile().getPath();

                    if (fileName != null && !fileName.isEmpty()) {
                        loadFromFile(fileName);             // Mε την κλήση αυτής της μεθόδου, θα φορτόσουμε τις παραγγελίες στο πρόγραμμά μας.
                        
                        pathLabel.setText("Opened File Path: "+fileName);
                        tempFileName = fileName;
                    }
                }
            }
        });
        
        iloadOrders.setAccelerator(KeyStroke.getKeyStroke("L"));
        
        istatistics.addActionListener(new ActionListener(){      // Όλοι οι υπολογισμοί για τα στατιστικά γίνονται εδώ.
            @Override
            public void actionPerformed(ActionEvent e) {
               showsStatistics();
            }
        });
        
        istatistics.setAccelerator(KeyStroke.getKeyStroke("I"));
        
        iabout.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                aboutFunc();
            }
        });
        
        iabout.setAccelerator(KeyStroke.getKeyStroke("A"));

        
        iclearbtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                clearArea();
            }
            
        });
        
        iclearbtn.setAccelerator(KeyStroke.getKeyStroke("C"));
        
        iexit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the program?","Exit program",JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION){ // Χρησιμοποιούμε το input του χρήστη 
                    System.exit(0);
                }
            }
        });
        
        iexit.setAccelerator(KeyStroke.getKeyStroke("E"));

    }
    
    public void addNewOrder(){
        JFrame orderFrame = new JFrame();  
        orderFrame.setVisible(true);
        orderFrame.setLocationRelativeTo(null);
        orderFrame.setTitle("Add New Order");
        JButton done = new JButton("Done");
        JButton cancel = new JButton("Cancel");
        
        l1 = new JLabel("Enter the details below.");
        JTextField appid = new JTextField(10);
        JLabel l2 = new JLabel ("AppID:");
        JTextField orderid = new JTextField(10);
        JLabel l3 = new JLabel ("OrderID:");
        JTextField orderdate = new JTextField(10);
        JLabel l4 = new JLabel ("Order Date");
        JTextField clientname = new JTextField(10);
        JLabel l5 = new JLabel ("Client Name:");
        JTextField itemname = new JTextField(10);
        JLabel l6 = new JLabel ("Item Name:");
        JTextField unitscount = new JTextField(10);
        JLabel l7 = new JLabel ("Number of Units:");
        JTextField netprice = new JTextField(10);
        JLabel l8 = new JLabel ("Net Price:");
        JTextField taxpercentage = new JTextField(10);
        JLabel l9 = new JLabel ("Tax Percentage:");
        
        GridLayout grid = new GridLayout(0,2); // Το μηδέν εδώ σημαίνει πως δεν θα αρχικοποιηθούν οι γραμμές για το Layout, αλλά 
                                                // θα χρησιμοποιηθούν όσες χρειαστούν.
        Orders temp = new Orders();
        orderFrame.setLayout(grid);
        orderFrame.add(l2);                    // Τοποθετούμε όλα τα συστατικά πάνω στο JFrame.
        orderFrame.add(appid);
        appid.setText(myAppID);
        orderFrame.add(l3);
        orderFrame.add(orderid);
        orderFrame.add(l4);
        orderFrame.add(orderdate);
        orderFrame.add(l5);
        orderFrame.add(clientname);
        orderFrame.add(l6);
        orderFrame.add(itemname);
        orderFrame.add(l7);
        orderFrame.add(unitscount);
        orderFrame.add(l8);
        orderFrame.add(netprice);
        orderFrame.add(l9);
        orderFrame.add(taxpercentage);
        orderFrame.add(done);
        orderFrame.add(cancel);
        orderFrame.setSize(300,300);
        
        done.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent z) {
                boolean go = true;                  // Η μεταβλητή αυτή θα ελέγχει εάν όλες οι προυποθέσεις τιρούνται
                // (γα τα δεδομένα που εισάγει ο χρήστης).
                String a = appid.getText();
                String b = orderid.getText();
                String c = orderdate.getText();
                String d = clientname.getText();
                String e = itemname.getText();
                String f = unitscount.getText();
                String g = netprice.getText();
                String h = taxpercentage.getText();
                if ((a.equals("")) || (b.equals("")) || (c.equals("")) || (d.equals("")) || (e.equals("")) 
                        || (f.equals("")) || (g.equals("")) || (h.equals(""))){
                    JOptionPane.showMessageDialog(null, "You must enter all the required values.","Error",JOptionPane.DEFAULT_OPTION);
                }else{
                    try{
                        int i = Integer.parseInt(f);
                        float j = Float.parseFloat(g);
                        float k = Float.parseFloat(h);
                        if(!a.equals(myAppID)){
                            JOptionPane.showMessageDialog(null, "The AppID should be the given AM.","Error",JOptionPane.DEFAULT_OPTION);
                            go = false;
                        }
                        temp.setAppID(myAppID);     //Σε όλες τις παραγγελίες, χρησιμοποιούμε το ΑΜ μου.
                        temp.setOrderID(b);
                        temp.setOrderDate(c);
                        temp.setClientName(d);
                        temp.setItemName(e);
                        temp.setUnitsCount(i);
                        temp.setNetPrice(j);
                        temp.setTaxPercentage(k);
                        go = true;
                        if(i<=0){
                            JOptionPane.showMessageDialog(null, "The number of units cannot be smaller or equal to 0!","Error",JOptionPane.DEFAULT_OPTION);
                            go = false;
                        }
                        if(j<=0){
                            JOptionPane.showMessageDialog(null, "The price per unit cannot be smaller or equal to 0!","Error",JOptionPane.DEFAULT_OPTION);            
                            go = false;
                        }
                        if(k<0){
                            JOptionPane.showMessageDialog(null, "The tax cannot be a negative number!","Error",JOptionPane.DEFAULT_OPTION);                                
                            go = false;
                        }
                    }catch(NumberFormatException v){
                        JOptionPane.showMessageDialog(null, "You have entered invalid types. Try again.","Error",JOptionPane.DEFAULT_OPTION);
                    }
                    if(go){
                        boolean check = checkIfValid(temp); // Αυτή η μέθοδος κάνει ακόμη έναν έλεγχο με βάση τα δεδομένα
                                                            // που εισήγαγε ο χρήστης. Ο έλεγχος αυτός γίνεται για λόγους ασφάλειας.
                        if (check){
                            ordersList.add(temp);
                            JOptionPane.showMessageDialog(null, "New order added!","Operation Completed.",JOptionPane.DEFAULT_OPTION);
                            area.append(temp.toString());
                            area.append("\n");
                            orderFrame.dispose();
                        }
                    }
                }
            }
               
        });
        cancel.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "The operation was canceled.","Operation Canceled",JOptionPane.WARNING_MESSAGE);
                orderFrame.dispose();
            }
        });
    }
    
    public void saveAsFunction(){
        if (ordersList.isEmpty()) {
            JOptionPane.showMessageDialog(null,"Nothing to save",        // Απεικονίζουμε ένα μήνυμα για το ότι
                    "Empy file Error",JOptionPane.ERROR_MESSAGE);       // η λίστα μας είναι κενή.
            return;
        }
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showSaveDialog(saveOrders);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String fileName = fc.getSelectedFile().getPath();
            File file = new File(fileName);
            file.delete();
            if (fileName != null && !fileName.isEmpty()) {
                saveOrdersList(fileName);
                saving = true;
                pathLabel.setText("Opened File Path: "+fileName);
                tempFileName = fileName;
            }
        }
    }
    
    public void saveFunction(){
        if (ordersList.isEmpty()) {
            JOptionPane.showMessageDialog(null,"Nothing to save",       // Απεικονίζουμε ένα μήνυμα για το ότι
                                                                        // η λίστα μας είναι κενή.
            "Empy file Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!saving){                // Ο έλεγχος αυτός γίνεται για να δούμε εάν έχει ανοιχτεί κάποιο αρχείο, να το αποθηκεύσουμε σε εκείνο
            
            final JFileChooser fc = new JFileChooser();                 // Με την χρήση του JFileChooser, ο χρήστης μπορεί να επιλέξει το
                                                                       // path που επιθυμεί να αποθηκεύσει τις παραγγελίες του.
            if (tempFileName==null){
                int returnVal = fc.showSaveDialog(saveOrders);
                if (returnVal == JFileChooser.APPROVE_OPTION) {             // Λαμβάνουμε την επιλογή του χρήστη.
                    fileName = fc.getSelectedFile().getPath();
                    File file = new File(fileName);
                    file.delete();
                    if (fileName != null && !fileName.isEmpty()) {
                        saveOrdersList(fileName);
                        pathLabel.setText("Opened File Path: "+fileName);
                    }
                }
            }else{
                File file = new File(tempFileName);
                file.delete();
                if (!tempFileName.isEmpty()) {
                    saveOrdersList(tempFileName);
                    pathLabel.setText("Opened File Path: "+tempFileName);
                }
            }
        }else{
            File file = new File(fileName);
            file.delete();
            if (tempFileName==null){ // Έλεγχοι για το ποιο θα είναι το path που θα αποθηκευτεί το αρχείο
                saveOrdersList(fileName);
                pathLabel.setText("Opened File Path: "+fileName);
            }else{
                File file1 = new File(tempFileName);
                file.delete();
                if (!tempFileName.isEmpty()) {
                    saveOrdersList(tempFileName);
                    pathLabel.setText("Opened File Path: "+tempFileName);
                }
            }
        }
    }
    
    private void saveOrdersList(String fileName) {
        try (BufferedWriter file = new BufferedWriter(new FileWriter(fileName, true))) {
            for (Orders order : ordersList) {
                file.write(order.toString1());                          // Με την μέθοδο αυτή θα αποθηκεύσουμε γραμμή προς γραμμή τα δεδομένα που υπήρχαν στο πρόγραμμα
                                                                       // με την μορφή που μας έχει ζητηθεί.
                file.newLine();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an error during the operation."+"\n"+"Operation Canceled...");
        }
        JOptionPane.showMessageDialog(null,ordersList.size() + " Orders were saved to " + fileName,
            "Save completed",JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void loadFromFile(String fileName) {
        boolean tempboolean = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) // Mε την χρήση του BufferedReader, θα διαβάσουμε χαρακτήρα προς χαρακτήρα όλο το αρχείο.
         {
            String line = "";
            String[] token;                             // Το token θα χρησιμοποιηθεί για τν χωρισμό τις λέξης με βάση κάποιο στοιχείο που θα δώσουμε εμείς.
            Orders order;
            while (reader.ready()) {
                line = reader.readLine();
                token = line.split(";");
                if (token.length == 8) {                // Εάν το πλήθος τον λέξεων που χωρίσαμε είναι 8, τότε θα δημιουργηθεί ένα αντικείμενο τύπου Orders και θα αποθηκευτεί
                    // στην λίστα
                    tempboolean = true;
                    if(tempboolean){
                        order = new Orders(token[0], token[1], token[2],token[3],token[4], Integer.parseInt(token[5]), Float.parseFloat(token[6]),Float.parseFloat(token[7]));
                        ordersList.add(order);
                        area.append(order.toString());
                        area.append("\n");
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null,"Error: File not found.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,"There was an error during the operation.");
        }
        
        if(tempboolean){
            JOptionPane.showMessageDialog(null,"File loaded successfully!",
                    "File Loaded",JOptionPane.DEFAULT_OPTION);
           
        }else{
            JOptionPane.showMessageDialog(null, "Incompatible file type.","Operatinon Canceled",JOptionPane.WARNING_MESSAGE);
        }
    }

    public static boolean isNullOrEmpty(String str) {
        if(str == null || str.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean checkIfValid(Orders x){  // Η μέθοδος αυτή χρησιμοποιεί την μέθοδο από πάνω για να ελέγξει εάν τα
                                            // δεδομένα που εισήγαγε ο χρήστης είναι έγκυρα
        boolean check2;
        if(!isNullOrEmpty(x.getAppID()) && !isNullOrEmpty(x.getClientName()) && !isNullOrEmpty(x.getOrderDate()) &&
        !isNullOrEmpty(x.getOrderID()) && !isNullOrEmpty(x.getItemName()) && x.getUnitsCount() >= 0
        && x.getNetPrice() >= 0.0 && x.getTaxPercentage()>=0.0){
            check2 = true;
        }else{
            check2 = false;
        }
        return check2;
    }
    
    public void showsStatistics(){
         int ordersCount = 0;
         float cost1 = 0;
         float cost2 = 0;
         String maxCostString="";
         float maxCostNumber=0;
         String minCostString="";
         float minCostNumber=99999999;
         //Orders order = new Order();
         for(Orders order : ordersList ){ // Έλεγχος για όλες τις παραγγελίες που είναι αποθηκευμένες στην λίστα
             ordersCount++;
             cost1 = cost1 +order.getNetPrice()*order.getUnitsCount();     // Υπολογισμοί για το κόστος (με φόρο και χωρίς)
             cost2 = cost2 + (order.getNetPrice()+(order.getNetPrice()*(order.getTaxPercentage()/100)))*order.getUnitsCount() ;
             float temp2 = order.getNetPrice()+(order.getNetPrice()*(order.getTaxPercentage()/100))*order.getUnitsCount() ;
             if (temp2 > maxCostNumber){
                 maxCostNumber = temp2;
                 maxCostString = order.getOrderID();
             }
             if (temp2 < minCostNumber){
                 minCostNumber = temp2;
                 minCostString = order.getOrderID();
             }
         }
         JFrame stats = new JFrame();
         stats.setTitle("Statistics");
         JPanel statsp1 = new JPanel();
         JLabel statsl1 = new JLabel();
         statsl1.setText("<html>Number of orders: "+ordersCount+"<br>"+"Cost without tax: "+cost1+"<br>"+"Cost with tax: "+ cost2
                 +"<br>"+"OrderID with maximum total cost: "+maxCostString+"<br>"+"OrderID with minimum total cost: "+minCostString);
         statsp1.add(statsl1);
         stats.add(statsp1);
         stats.setVisible(true);
         stats.setSize(320,200);
         stats.setLocationRelativeTo(null);
    }
    
    public void aboutFunc(){
        JFrame aboutJFrame = new JFrame();
                JLabel aboutl1 = new JLabel();
                aboutl1.setFont(new Font("Verdana", Font.PLAIN, 18));
                String s1= "<html>Ονοματεπώνυμο: Νεκτάριος-Δημήτριος Γκουβούσης<br>"
                        +"Αριθμός Μητρώου: 18390193\n<br>"+
                        "Χρόνος Ολοκλήρωσης Εργασίας: 10 με 11 ώρες. Προσπαθούσα να εκτελέσω την εργασία σε πολλά Java αρχεία για να έχει καλύτερη μοορφή"
                        + " ο κώδικας. Αντιμετώπισα ένα πρόβλημα με την προσθήκη των αποτελεσμάτων στο ArrayList με το πάτημα του κουμπιού Done."
                        + "<br><br> Το πρόγραμμα αυτό χρησιμοποιείται για την διαχείριση παραγγελιών, είτε από αρχείο,είτε από εκχωρήσεις "
                        + "του χρήστη. Μπορεί να αποθηκεύει και να ανακτά τα αρχεία αυτά. Το περιβάλλον GUI είναι πολύ <br>"
                        + "απλό και εύκολα κατανοητό από την μερία του χρήστη.";
                aboutl1.setText(s1);
                aboutl1.setIcon(icon1);
                aboutJFrame.add(aboutl1);
                aboutJFrame.setVisible(true);
                aboutJFrame.setTitle("Information");
                aboutJFrame.setSize(1200,800);
                aboutJFrame.setLocationRelativeTo(null);
    }
    
    public void clearArea(){
        area.setText("");
        pathLabel.setText("Opened File Path: ");
        fileName = "";
        ordersList.clear();
        saving = false;
        tempFileName = null;
    }
}