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
    JButton active_orders, all_orders , all_users ;
    Connection con;
    String[] user_emails = {"amer@gmail.com" , "moatsm@gmail.com"};;
    String from;
    PreparedStatement pst;


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
        active_orders = new JButton("Active Users Order History");
        all_users = new JButton("All Users");
        all_orders = new JButton("All Users Order History");
        l0.setBounds(175, 50, 350, 40);
        active_users.setBounds(50, 150, 180, 100);
        active_users.addActionListener(this);
        all_users.setBounds(50, 280, 180, 100);
        all_users.addActionListener(this);
        all_orders.setBounds(250, 280, 180, 100);
        all_orders.addActionListener(this);
        active_orders.setBounds(250, 150, 180, 100);
        active_orders.addActionListener(this);
        setTitle("Adminstrator");
        setLayout(null);
        setVisible(true);
        setSize(500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(l0);
        add(active_users);
        add(active_orders);
        add(all_orders);
        add(all_users);

    }


        public void actionPerformed (ActionEvent ae){
            if (ae.getSource() == all_users) {
                showTableAllUsers();
            }
            else if(ae.getSource() == active_users){
                showTableActiveUsers();
            }
            else if (ae.getSource() == active_orders) {
                showTableAllOrders();
            }
            else if(ae.getSource() == all_orders){
                showTableAllOrders();
            }
        }

    public void showTableActiveUsers() {
        String[] columnNames = {
                "Customer ID", "Name", "Email", "Gender" , "Balance" , "Phone Number" , "Date of Birth"};
        frame1 = new JFrame("Active Users List");
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
        String DOB = "";
        try {
            String sql = "select * from Customer WHERE Email IN (";
            for(int i = 0; i < user_emails.length; i++){
                sql += "\"" + user_emails[i] + "\"";
                if(i < user_emails.length - 1)
                    sql += ", ";
            }
            sql += ")";
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
                DOB = rs.getString("DOB");
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

    public void showTableAllUsers() {
        String[] columnNames = {
                "Customer ID", "Name", "Email", "Gender" , "Balance" , "Phone Number" , "Date of Birth"};
        frame1 = new JFrame("Users List");
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
        String DOB = "";
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
                DOB = rs.getString("DOB");
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

    public void showTableActiveOrders() {
        frame1 = new JFrame("Orders History of Active Users");
        frame1.setLayout(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"OID", "Customer Name", "Customer Email", "Date of Order" , "Total Price"});
        table = new JTable();
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        while (table.getRowCount()>0)
        {
            table.remove(0);
        }
        JScrollPane scroll = new JScrollPane(table);
        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        int oid;
        String cname = "";
        String email = "";
        Date date;
        Float price;

        try {
            for(int j=0 ; j < user_emails.length; j++) {
                pst = con.prepareStatement("select Orders.OID , Customer.Name_ , Customer.Email , Orders.Date , Orders.Total_Cost  " +
                        "from Orders , Customer WHERE Customer.Email = ? and Orders.CustID = Customer.CustID");
                pst.setString(1 , user_emails[j]);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    oid = rs.getInt("OID");
                    cname = rs.getString("Name_");
                    email = rs.getString("Email");
                    date = rs.getDate("Date");
                    price = rs.getFloat("Total_Cost");

                    model.addRow(new Object[]{oid,cname, email, date, price});
                }

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        frame1.add(scroll);
        frame1.setVisible(true);
        frame1.setSize(400, 300);
    }

    public void showTableAllOrders() {
        frame1 = new JFrame("Orders History of Active Users");
        frame1.setLayout(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"OID", "Customer Name", "Customer Email", "Date of Order" , "Total Price"});
        table = new JTable();
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        while (table.getRowCount()>0)
        {
            table.remove(0);
        }
        JScrollPane scroll = new JScrollPane(table);
        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        int oid;
        String cname = "";
        String email = "";
        Date date;
        Float price;

        try {
                pst = con.prepareStatement("select Orders.OID , Customer.Name_ , Customer.Email , Orders.Date , Orders.Total_Cost  " +
                        "from Orders , Customer WHERE Orders.CustID = Customer.CustID");
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    oid = rs.getInt("OID");
                    cname = rs.getString("Name_");
                    email = rs.getString("Email");
                    date = rs.getDate("Date");
                    price = rs.getFloat("Total_Cost");

                    model.addRow(new Object[]{oid,cname, email, date, price});
                }


        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        frame1.add(scroll);
        frame1.setVisible(true);
        frame1.setSize(400, 300);
    }


        public static void main (String[] args){

            new Adminstrator_Interface();

        }

}
