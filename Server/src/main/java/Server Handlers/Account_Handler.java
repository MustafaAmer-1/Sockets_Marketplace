import java.sql.DriverManager;
import java.sql.*;
import com.fasterxml.jackson.databind.JsonNode;

public class Account_Handler {
    private static int cid=0;


    private static Connection connect_db() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/marketplace", "root", "1234");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return con;
    }
    public static boolean Register(JsonNode n)throws ClassNotFoundException
    {   String email = (String)n.path("email").asText();
        String name = (String)n.path("name").asText();
        int pass = (int)n.path("password").asInt();
        int age = (int)n.path("age").asInt();
        String gender = (String)n.path("gender").asText();
        String phonenumber = (String)n.path("phonenumbe").asText();
        Connection con = connect_db();
        PreparedStatement stm = null;
        try{

            cid++;
            String sql = "INSERT INTO Customer(Email,Password,Name,Age,Gender,Phone_number,CID) VALUES(?,?,?,?,?,?,?)" ;
            stm = con.prepareStatement(sql);
            stm.setString(1, email);
            stm.setInt(2, pass);
            stm.setString(3, name);
            stm.setInt(4, age);
            stm.setString(5, gender);
            stm.setString(6, phonenumber);
            stm.setInt(7, cid);
            int update = stm.executeUpdate();
            if(update>0) return true;
            else return false;
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

    public static boolean Authentication(JsonNode n)
    {
        String email = (String)n.path("email").asText();
        int pass = (int)n.path("password").asInt();
        Connection con = connect_db();
        Statement stm = null;
        try{

            stm = con.createStatement();
            String sql = "SELECT Password FROM Customer WHERE Email =?";
            PreparedStatement stm1 = con.prepareStatement(sql);
            stm1.setString(1,email);
            ResultSet rs = stm1.executeQuery();
            if(rs.next()) {
                int passcheck = rs.getInt("Password");
                if (passcheck == pass) return true;
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
