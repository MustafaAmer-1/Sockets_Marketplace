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

           try {
            // Setting Database Connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Market", "root", "");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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
        PreparedStatement pst;
        String[] columnNames = {
                "Customer ID", "Name", "Email", "Gender" , "Balance" , "Phone Number" , "Date of Birth"};
        frame1 = new JFrame("Users List");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setLayout(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        table = new JTable();
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        int custID = 0;
        String custName = "";
        String email = "";
        String gender = "";
        float Balance = 0;
        String pNo = "";
        Date DOB = null;
        try {
            String sql = "select * from Customer";
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            int i = 0;
            if (rs.next()) {
                custID = rs.getInt("CustID");
                custName = rs.getString("Name_");
                email = rs.getString("Email");
                gender = rs.getString("Gender");
                Balance = rs.getLong("Balance");
                pNo = rs.getString("Phone_number");
                DOB = rs.getDate("DOB");
                model.addRow(new Object[]{custID, custName, email, gender, Balance, pNo, DOB});
                i++;
            }
            if (i < 1) {
                JOptionPane.showMessageDialog(null, "No Record Found", "Error", JOptionPane.ERROR_MESSAGE);
            }
            if (i == 1) {
                System.out.println(i + " Record Found");
            } else {
                System.out.println(i + " Records Found");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        frame1.add(scroll);
        frame1.setVisible(true);
        frame1.setSize(800, 400);

    }

    public void showTableOrders() {

    }


        public static void main (String[] args){

            new Adminstrator_Interface();

        }

}
