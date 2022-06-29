import com.fasterxml.jackson.databind.JsonNode;
import java.sql.*;

public class ServerHandler{
    private int CustID = -1
    Connection con;

    ServerHandler(int CustID){
        this.CustID = CustID;
        try {
            // Setting Database Connection
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Market", "root", "");
        }
        catch (ClassNotFoundException e) { e.printStackTrace(); }
        catch (SQLException e) { e.printStackTrace(); }
    }


    public boolean withdraw_cash(JsonNode node){
        String sql;
        PreparedStatement stm = null;
        float amount = node.get("amount").asLong();
        ResultSet rs = null;
        float amnt = 0;
        try {
            // Ensuring Amount is Sufficient to withdraw
            sql = "SELECT Balance FROM Customer WHERE CustID = ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, CustID);
            rs = stm.executeQuery();
            rs.next();
            amnt = rs.getFloat("Balance");
            if(amnt < amount){
                System.out.println("Insufficient Balance");
                return false;
            }

            // Updating the Database with new balance
            sql = "UPDATE Customer SET Balance = Balance - ?  WHERE CustID = ?";
            stm = con.prepareStatement(sql);
            stm.setFloat(1, amount);
            stm.setInt(2, CustID);
            stm.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
        System.out.println("Balance Updated Successfully");
        return true;
    }
}