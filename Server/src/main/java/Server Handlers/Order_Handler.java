import java.sql.*;
import java.sql.DriverManager;
import com.fasterxml.jackson.databind.JsonNode;

public class Order_Handler {
    public static boolean submit_order(JsonNode node){
        String sql;
        PreparedStatement stm = null;
        Connection con = null;
        ResultSet rs = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/marketplace", "root", "1234");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
                            //System.out.println("Insuffcient Quantity of " + rs.getString("Pname"));
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


        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //System.out.println("Order made Successfully");
        return true;

    }
}
