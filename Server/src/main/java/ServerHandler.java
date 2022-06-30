import com.fasterxml.jackson.databind.JsonNode;
import java.sql.*;

public class ServerHandler{
    private int CustID = -1;
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

    public boolean submit_order(JsonNode node){
        String sql;
        PreparedStatement stm = null;
        ResultSet rs = null;
        //Check Avaliability
        for (int i = 0; i < node.size(); i++) {
             int id = node.required(i).get("id").asInt();
             int quantity = node.required(i).get("quantity").asInt();
             sql = "SELECT * FROM Product WHERE PID =?";
            try {
                stm = con.prepareStatement(sql);
                stm.setString(1, String.valueOf(id));
                rs = stm.executeQuery();
                if (rs.next()) {
                    if (quantity > rs.getInt("Stock_Quantity")) {
                            return false;
                    }
                }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

            //Update Database
            for (int i = 0; i < node.size(); i++) {
                int id = node.required(i).get("id").asInt();
                int quantity = node.required(i).get("quantity").asInt();
                sql = "UPDATE Product SET Stock_Quantity=Stock_Quantity-?  WHERE PID = ?";
                try {
                    stm = con.prepareStatement(sql);
                    stm.setInt(1, quantity);
                    stm.setInt(2, id);
                    stm.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }


            }

        return true;

    }
    public  boolean Authentication(JsonNode n)
    {
        String email = (String)n.path("Email").asText();
        String pass = (String)n.path("Password_").asText();
        Connection con = connect_db();
        Statement stm = null;
        try{

            stm = con.createStatement();
            String sql = "SELECT Password_ ,CustID FROM Customer WHERE Email =?";
            PreparedStatement stm1 = con.prepareStatement(sql);
            stm1.setString(1,email);
            ResultSet rs = stm1.executeQuery();
            if(rs.next()) {
                String passcheck = rs.getString("Password_");
                if (passcheck.equals( pass)) return  true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if(stm!=null)
                    con.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
            try{
                if(con!=null)
                    con.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


}