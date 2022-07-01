import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class Adminstrator_Interface extends JFrame implements ActionListener {


    static JTable table;
    JFrame frame1;
    JLabel l0, l1, l2;
    JButton active_users;
    JButton orders;
    Connection con;
    String[] user_emails;
    String from;


    Adminstrator_Interface() {


        l0 = new JLabel("System Control");
        l0.setForeground(Color.red);
        l0.setFont(new Font("Serif", Font.BOLD, 20));
        active_users = new JButton("Active Users");
        orders = new JButton("Orders History");
        l0.setBounds(175, 50, 350, 40);
        active_users.setBounds(50, 150, 180, 100);
        active_users.addActionListener(this);
        orders.setBounds(250, 150, 180, 100);
        orders.addActionListener(this);
        setTitle("Adminstrator");
        setLayout(null);
        setVisible(true);
        setSize(500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(l0);
        add(active_users);
        add(orders);

    }


        public void actionPerformed (ActionEvent ae){
            if (ae.getSource() == active_users) {

                showTableUsers();

            }

            else if (ae.getSource() == orders) {

                showTableOrders();

            }


        }


    public void showTableUsers() {

    }

    public void showTableOrders() {

    }


        public static void main (String[] args){

            new Adminstrator_Interface();

        }

}
