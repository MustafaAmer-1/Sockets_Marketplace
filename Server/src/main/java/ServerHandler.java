import DataUtility.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.sql.*;
import DataUtility.Item;
import java.util.ArrayList;

public class ServerHandler{
    private int CustID = -1;
    Connection con;

    ServerHandler(){
        try {
            // Setting Database Connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Market", "root", "");
        }
        catch (ClassNotFoundException e) { e.printStackTrace(); }
        catch (SQLException e) { e.printStackTrace(); }
    }

    public boolean Deposit_cash(double amount){
        String sql;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //Updating Database with the new balance
            sql = "UPDATE Customer SET Balance = Balance + ?  WHERE CustID = ?";
            stm = con.prepareStatement(sql);
            stm.setDouble(1, amount);
            stm.setInt(2, CustID);
            stm.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
        return true;
    }

    public boolean withdraw_cash(double amount){
        String sql;
        PreparedStatement stm = null;
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
            stm.setDouble(1, amount);
            stm.setInt(2, CustID);
            stm.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
        System.out.println("Balance Updated Successfully");
        return true;
    }

    public boolean submit_order(JsonNode node){
        String sql;
        PreparedStatement stm = null;
        float total_price = 0;
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
                    else{
                        total_price += rs.getFloat("Price")*quantity;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        sql = "SELECT Balance FROM Customer WHERE CustID=?";
        try {
            stm = con.prepareStatement(sql);
            stm.setInt(1 , CustID);
            System.out.println(total_price);
            rs = stm.executeQuery();
            if(rs.next()){
                if(total_price > rs.getFloat("Balance")){
                    return false;
                }
                else{
                    sql = "UPDATE Customer SET Balance = Balance - ? WHERE CustID=?";
                    stm = con.prepareStatement(sql);
                    stm.setFloat(1 , total_price);
                    stm.setInt(2 , CustID);
                    stm.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

        sql = "INSERT INTO Orders (Date , CustID , Total_Cost) VALUES (? , ? , ?)";
        try {
            stm = con.prepareStatement(sql);
            stm.setString(1, java.time.LocalDate.now().toString());
            stm.setInt(2, CustID);
            stm.setFloat(3, total_price);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;

    }
    public  boolean Authentication(JsonNode n)
    {
        String email = (String)n.path("email").asText();
        String pass = (String)n.path("psw").asText();
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
        String email = (String)n.path("email").asText();
        String name = (String)n.path("username").asText();
        String pass = (String)n.path("psw").asText();
        String DOB = (String)n.path("dob").asText();
        String gender = (String)n.path("gender").asText();
        String phonenumber = (String)n.path("phone").asText();
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
        int pid = 0;
        JsonNode jsonNode = null;
        try{

            stm = con.createStatement();
            String sql = "SELECT CatName,Pname,Price,Stock_Quantity,ImageURL,PID FROM Product , Category WHERE Product.CatID =Category.CatID";
            PreparedStatement stm1 = con.prepareStatement(sql);
            ResultSet rs = stm1.executeQuery();
            ArrayList<Item> arr= new ArrayList<>();
            while(rs.next()) {
                Pname = rs.getString("Pname");
                price = rs.getFloat("Price");
                Stock_Quantity= rs.getInt("Stock_Quantity");
                CatName= rs.getString("CatName");
                ImageURL=rs.getString("ImageURL");
                pid = rs.getInt("PID");
                Item arr1 = new Item(pid,Pname,price,Stock_Quantity,ImageURL,CatName);
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
        int pid;
        int Product_Quantity;
        String ImageURL;
        float price;
        String Pname;
        String CatName;
        JsonNode response = null;
        
        try{


            String sql = "SELECT CatName,Pname,Price,product_Quantity,ImageURL, Product.PID " +
                         "FROM Product , Category , Consists_of " +
                         "WHERE Product.CatID =Category.CatID AND Product.PID = Consists_of.PID AND Consists_of.CID = ?";


            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1 , CustID);

            ResultSet rs = stm.executeQuery();
            ArrayList<Item> arr= new ArrayList<>();
            while(rs.next()) {
                pid = rs.getInt("PID");
                Pname = rs.getString("Pname");
                price = rs.getFloat("Price");
                Product_Quantity = rs.getInt("Product_Quantity");
                CatName= rs.getString("CatName");
                ImageURL=rs.getString("ImageURL");
                Item item = new Item(pid, Pname,price,Product_Quantity,ImageURL,CatName);
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

    public  JsonNode getUserInfo()
    {
        Statement stm = null;
        String pass ="";
        float balance=0;
        String email="";
        String name="";
        String phone = "";
        JsonNode jsonNode = null;
        try{

            stm = con.createStatement();
            String sql = "SELECT * FROM Customer WHERE CustID =?";
            PreparedStatement stm1 = con.prepareStatement(sql);
            stm1.setInt(1,CustID);
            ResultSet rs = stm1.executeQuery();
            if(rs.next()) {
                pass = rs.getString("Password_");
                balance = rs.getFloat("Balance");
                email= rs.getString("Email");
                name= rs.getString("Name_");
                phone= rs.getString("Phone_Number");
            }
            User u = new User(name,email,pass,phone,balance);
            ObjectMapper mapper = new ObjectMapper();
            try {
                String jsonnode = mapper.writeValueAsString (u);
                jsonNode = mapper.readTree(jsonnode);
                ((ObjectNode)jsonNode).remove("password");
                ((ObjectNode)jsonNode).remove("orders");
                return jsonNode ;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
return jsonNode;
    }

    public JsonNode search_item_category(String category){
        int pid;
        int Stock_Quantity;
        String ImageURL;
        float price;
        String Pname;
        String CatName;
        try{

            String sql = "SELECT Pname,Price,Stock_Quantity,ImageURL,CatName, PID " +
                    "FROM Product , Category WHERE Product.CatID =Category.CatID AND Category.CatName LIKE ?";
            PreparedStatement stm1 = con.prepareStatement(sql);
            stm1.setString (1 ,"%" +category+"%" );
            ResultSet rs = stm1.executeQuery();
            ArrayList<Item> arr= new ArrayList<>();
            while(rs.next()) {
                pid = rs.getInt("PID");
                Pname = rs.getString("Pname");
                price = rs.getFloat("Price");
                Stock_Quantity= rs.getInt("Stock_Quantity");
                CatName= rs.getString("CatName");
                ImageURL=rs.getString("ImageURL");
                Item arr1 = new Item(pid, Pname,price,Stock_Quantity,ImageURL,CatName);
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
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public JsonNode search_item_name(String name){
        int pid;
        int Stock_Quantity;
        String ImageURL;
        float price;
        String Pname;
        String CatName;
        try{

            String sql = "SELECT CatName,Pname,Price,Stock_Quantity,ImageURL, pid " +
                    "FROM Product , Category WHERE Product.CatID =Category.CatID AND Pname LIKE ?";
            PreparedStatement stm1 = con.prepareStatement(sql);
            stm1.setString (1 ,"%" +name+"%" );
            ResultSet rs = stm1.executeQuery();
            ArrayList<Item> arr= new ArrayList<>();
            while(rs.next()) {
                pid = rs.getInt("PID");
                Pname = rs.getString("Pname");
                price = rs.getFloat("Price");
                Stock_Quantity= rs.getInt("Stock_Quantity");
                CatName= rs.getString("CatName");
                ImageURL=rs.getString("ImageURL");
                Item arr1 = new Item(pid, Pname,price,Stock_Quantity,ImageURL,CatName);
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
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean set_cart(JsonNode node) {
        String sql;
        PreparedStatement stm = null;
        String sql1 = "DELETE FROM Consists_of WHERE CID =?";
        try {
    
            stm = con.prepareStatement(sql1);
            stm.setInt(1, CustID);
            stm.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        // Update consist of
        for (int i = 0; i < node.size(); i++) {
            int id = node.required(i).get("id").asInt();
            int quantity = node.required(i).get("quantity").asInt();
            sql = "INSERT INTO Consists_of(Product_Quantity, CID, PID) VALUES (?,?,?)";
            try {
                stm = con.prepareStatement(sql);
                stm.setInt(1, quantity);
                stm.setInt(2, CustID);
                stm.setInt(3, id);
                stm.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
    
        }
    
        return true;
    
    }

}

