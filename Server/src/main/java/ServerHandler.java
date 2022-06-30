import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.*;
import DataUtility.Item;
import java.util.ArrayList;

public class ServerHandler{
    private int CustID = -1;
    Connection con;

    ServerHandler(){
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
        Statement stm = null;
        try{

            stm = con.createStatement();
            String sql = "SELECT Password_ ,CustID FROM Customer WHERE Email =?";
            PreparedStatement stm1 = con.prepareStatement(sql);
            stm1.setString(1,email);
            ResultSet rs = stm1.executeQuery();
            if(rs.next()) {
                String passcheck = rs.getString("Password_");
                if (passcheck.equals(pass)){
                    this.CustID = rs.getInt("CustID");
                    return  true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
        return false;
    }

    public  boolean Register(JsonNode n)
    { 
        String email = (String)n.path("Email").asText();
        String name = (String)n.path("Name_").asText();
        String pass = (String)n.path("Password_").asText();
        String DOB = (String)n.path("DOB").asText();
        String gender = (String)n.path("Gender").asText();
        String phonenumber = (String)n.path("Phone_number").asText();
        PreparedStatement stm = null;
        PreparedStatement stm1 = null;
        try{
            String sql = "INSERT INTO Customer(Email,Password_,Name_, DOB,Gender,Phone_number,Balance) VALUES(?,?,?,?,?,?,?)" ;
            stm = con.prepareStatement(sql);
            stm.setString(1, email);
            stm.setString(2, pass);
            stm.setString(3, name);
            stm.setString(4, DOB);
            stm.setString(5, gender);
            stm.setString(6, phonenumber);
            stm.setInt(7, 0);
            String sql1 = "INSERT INTO Cart() VALUES();" ;
            stm1 = con.prepareStatement(sql1);
            int update = stm.executeUpdate();
            if(update>0) {
                stm1.executeUpdate();
                return true;
            }
            else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        return false;
}

public  JsonNode getAllItems()
    {
        Statement stm = null;
        int Stock_Quantity=0;
        String ImageURL ="";
        float price=0;
        String Pname="";
        String CatName ="";
        JsonNode jsonNode = null;
        try{

            stm = con.createStatement();
            String sql = "SELECT CatName,Pname,Price,Stock_Quantity,ImageURL FROM Product , Category WHERE Product.CatID =Category.CatID";
            PreparedStatement stm1 = con.prepareStatement(sql);
            ResultSet rs = stm1.executeQuery();
            ArrayList<Item> arr= new ArrayList<>();
            int i=0;
            while(rs.next()) {
                Pname = rs.getString("Pname");
                price = rs.getFloat("Price");
                Stock_Quantity= rs.getInt("Stock_Quantity");
                CatName= rs.getString("CatName");
                ImageURL=rs.getString("ImageURL");
                Item arr1 = new Item(Pname,price,Stock_Quantity,ImageURL,CatName);
                arr.add(arr1);
            }
            ObjectMapper mapper = new ObjectMapper();

            try {
                String jsonnode = mapper.writeValueAsString(arr);
                JsonNode json = mapper.readTree(jsonnode);
                return json ;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return jsonNode;
    }

    public  JsonNode get_cart(){
        int Product_Quantity;
        String ImageURL;
        float price;
        String Pname;
        String CatName;
        JsonNode response = null;
        
        try{


            String sql = "SELECT CatName,Pname,Price,product_Quantity,ImageURL " +
                         "FROM Product , Category , Consists_of " +
                         "WHERE Product.CatID =Category.CatID AND Product.PID = Consists_of.PID AND Consists_of.CID = ?";


            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1 , CustID);

            ResultSet rs = stm.executeQuery(sql);
            ArrayList<Item> arr= new ArrayList<>();
            int i=0;
            while(rs.next()) {
                Pname = rs.getString("Pname");
                price = rs.getFloat("Price");
                Product_Quantity = rs.getInt("Product_Quantity");
                CatName= rs.getString("CatName");
                ImageURL=rs.getString("ImageURL");
                Item item = new Item(Pname,price,Product_Quantity,ImageURL,CatName);
                arr.add(item);
            }
            ObjectMapper mapper = new ObjectMapper();

            try {
                String node = mapper.writeValueAsString(arr);
                response = mapper.readTree(node);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return response ;
    }
}

