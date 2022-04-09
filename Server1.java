import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Server1 implements ActionListener{
    JPanel p1;
    JTextField t1;
    JButton b1;
    static JPanel a1;
    static JFrame f1 = new JFrame();
    static ServerSocket skt;
    static Socket s;
    static Box vertical = Box.createVerticalBox();
    static DataInputStream din;
     static DataOutputStream dout;
    Boolean typing;
    Server1() {
        f1.getContentPane();
        JTextArea textArea = new JTextArea(20, 20);
        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(0, 0, 0));
        p1.setBounds(0, 0, 450, 70);
        f1.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons\\icon.png"));
        Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l1 = new JLabel(i3);
        l1.setBounds(5, 17, 30, 30);
        p1.add(l1);

        l1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons\\saurav.png"));
        Image i5 = i4.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel l2 = new JLabel(i6);
        l2.setBounds(150, 5, 60, 60);
        p1.add(l2);

        JLabel l3 = new JLabel("Saurav");
        l3.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        l3.setForeground(Color.WHITE);
        l3.setBounds(220, 15, 100, 18);
        p1.add(l3);

        JLabel l4 = new JLabel("Active Now");
        l4.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
        l4.setForeground(Color.WHITE);
        l4.setBounds(220, 35, 100, 20);
        p1.add(l4);
        Timer t = new Timer( 1,new ActionListener() {   //using predefined function timer to set a time to change status frome active now to typing and vice versa
            public void actionPerformed(ActionEvent ae) {
                if (!typing) {
                    l4.setText("Active Now");
                }
            }
        });
        t.setInitialDelay(2000);

         a1 = new JPanel();  //panel used to create bubbles
        a1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        JScrollPane sp=new JScrollPane(a1); //adding scrollpane in panel a1
        sp.setBounds(5, 75, 440, 570);
        sp.setBorder(BorderFactory.createEmptyBorder());  //
        ScrollBarUI ui=new BasicScrollBarUI(){         //creating scrollbar for decrease button
             protected JButton createDecreaseBuitton(int orientation ){
                JButton button=super.createDecreaseButton(orientation);
                button.setBackground(new Color(7,94,84));
                button.setForeground(Color.WHITE);
                this.thumbColor=new Color(0,0,0);
                return button;
            }
            protected JButton createIncreaseBuitton(int orientation ){  //creating scrollbar for Increase button
                JButton button=super.createIncreaseButton(orientation);
                button.setBackground(new Color(7,94,84));
                button.setForeground(Color.WHITE);
                this.thumbColor=new Color(0,0,0);
                return button;
            }
        };
        sp.getVerticalScrollBar().setUI(ui); //in a scrollpane we are adding two scroll bar i.e increase and decrease button
        f1.add(sp);
       t1 = new JTextField();
        t1.setBounds(5, 655, 310, 40);
        t1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f1.add(t1);

       t1.addKeyListener(new KeyAdapter() {      //here adding keylistener whenever key is pressed it displays typing..
            public void keyPressed(KeyEvent ke) {
                l4.setText("typing...");

                t.stop();

                typing = true;
            }

            public void keyReleased(KeyEvent ke) {    //here adding keylistener whenever key is pressed it displays active now
                typing = false;

                if (!t.isRunning()) {
                    t.start();
                }
            }
        });
        b1 = new JButton("Send");
        b1.setBounds(320, 655, 123, 40);
        b1.setBackground(new Color(7, 94, 84));
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        b1.addActionListener(this);
        f1.add(b1);
        f1.getContentPane().setBackground(Color.WHITE);
        f1.setLayout(null);
        f1.setSize(450, 700);
        f1.setLocation(250, 100);
        f1.setUndecorated(true);
        f1.setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){
        try{
            String out = t1.getText();  //use to get the data of textfield int out

            JPanel p2 = formatLabel(out);   //adding data from textfield to panel

            a1.setLayout(new BorderLayout());  //is the default layout to set objects on window

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));  //to make distance between two bubble

            a1.add(vertical, BorderLayout.PAGE_START);
            dout.writeUTF(out);  //writing msgs to the other user(client)
            t1.setText(""); //after every msg set textfield empty
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public static JPanel formatLabel(String out){
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS)); //setting buuble on chats
        JLabel l1 = new JLabel("<html><p style = \"width : 150px\">"+out+"</p></html>");  //used to set width of words in buubles
        l1.setFont(new Font("Tahoma", Font.PLAIN, 16)); //set font
        l1.setBackground(new Color(37, 211, 102)); ///to set colour of bubble
        l1.setOpaque(true);  //to make speech bubble tarnsparent
        l1.setBorder(new EmptyBorder(15,15,15,50));  //to set distance from every side of data in bubble

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); //set date and time format

        JLabel l2 = new JLabel();
        l2.setText(sdf.format(cal.getTime()));  //taking time in label
        p3.add(l1);
        p3.add(l2);
        return p3;
    }

    public static void main(String[] args){
        new Server1();
        String msginput = "";
        try{
            ServerSocket skt = new ServerSocket(6001);  //setting port number to connect
            while(true){
                 s = skt.accept();
                 din = new DataInputStream(s.getInputStream());  //taking chats in Din variable
                 dout = new DataOutputStream(s.getOutputStream());

                while(true){
                    msginput = din.readUTF();  //reading msg that are recieved and storing it in msginput
                    JPanel p2 = formatLabel(msginput);
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(p2, BorderLayout.LINE_START);  //setting position of messages recieved
                    vertical.add(left);
                    f1.validate();
                }
            }
        }catch(Exception e){}
    }
}
