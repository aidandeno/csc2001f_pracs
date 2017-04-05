
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Date;

/* NB FIRST SET CLASSPATH AS BELOW (one line Unix command):

 export CLASSPATH=$CLASSPATH:/usr/share/java/mysql-connector-java.jar

 */
public class JDBCExample
{
    static Connection connection = null;
    private Map<String, Command> commands = null;

    public JDBCExample()
    {
        commands = new HashMap<String, Command>();
        commands.put("Show", new Show());
        commands.put("Help", new Help());
        commands.put("Quit", new Quit());

        System.out.println("------------ MySQL JDBC Connection Testing ------------");

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }

        System.out.println("MySQL JDBC Driver Registered!");

        try
        {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/grgvic001", "grgvic001", "paekepha");
        }
        catch (SQLException e)
        {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

        if (connection != null)
        {
            System.out.println("You made it, take control of your database now!");
        }
        else
        {
            System.out.println("Failed to make connection!");
        }

        /*try {
         Statement st = connection.createStatement();
         String SQL = "SELECT * FROM offices";
         ResultSet rs = st.executeQuery(SQL); 
            
         while (rs.next()) {
         int officeCode = rs.getInt("officeCode");
         String city = rs.getString("city");
         String phone = rs.getString("phone");
         System.out.println(Integer.toString(officeCode)+city+phone);
         }
         }
         catch (SQLException e) {
         System.out.println("SQL incorrect. See output console.");
         e.printStackTrace();               
         }*/
    }

    public void run()
    {
        Scanner input = new Scanner(System.in);
        commands.get("help").execute("");

        while (true)
        {
            System.out.print(">");
            Scanner line = new Scanner(input.nextLine());

            String keyword = (line.hasNext() ? line.next().trim().toLowerCase() : "");
            String argument = (line.hasNext() ? line.next() : "");

            if (commands.containsKey(keyword))
            {
                commands.get(keyword).execute(argument);
            }
            else
            {
                System.out.println("Sorry, that command is not recognised. Type 'help' for assistance.");
            }
        }
    }

    public static void main(String[] args)
    {
        (new JDBCExample()).run();
    }

    public void showOrder(int orderIDrequest)
    {
        try
        {
            Statement st = connection.createStatement();
            String SQL = "SELECT * FROM orderdetails INNER JOIN orders ON orderdetails.orderID = orderID WHERE orderID = " + Integer.toString(orderID);
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next())
            {
                String orderID = Integer.toString(rs.getInt("OrderID"));
                String orderDate = rs.getDate("OrderDate").toString();
                String totalQuant = Integer.toString(rs.getInt("Total"));
                String style = Integer.toString(rs.getInt("Style"));
                String priceEach = Integer.
            }
        }
        catch (SQLException e)
        {

        }
    }

    public void showAll()
    {
        try
        {
            Statement st = connection.createStatement();
            String SQL = "SELECT orders.OrderID, OrderDate, sum.Total, products.Style, orderdetails.PriceEach, orderdetails.Quantity, products.BuyPrice, products.Department, products.Type, products.Colour, suppliers.SupplierID, suppliers.Name, suppliers.Discount, suppliers.Terms FROM orders JOIN (orderdetails JOIN (products JOIN (supplydetails JOIN suppliers ON supplydetails.SupplierID = suppliers.SupplierID) ON products.Style = supplydetails.Style) ON orderdetails.Style = products.Style) ON orders.OrderID = orderdetails.OrderID JOIN (SELECT SUM(PriceEach * Quantity) AS Total, OrderID FROM orderdetails GROUP BY OrderID) AS sum ON orders.OrderID = sum.OrderID;";
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next())
            {
                String orderID = Integer.toString(rs.getInt("OrderID"));
                String orderDate = rs.getDate("OrderDate").toString();
                String totalQuant = Integer.toString(rs.getInt("Total"));
                String style = Integer.toString(rs.getInt("Style"));

            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void insert()
    {
        Scanner scan = new Scanner(System.in);
        HashMap<Integer, Integer> products = new HashMap<>();

        String orderDate;
        int totalCost = 0;

        try
        {
            Statement st = connection.createStatement();
            System.out.println("Date of Order?");

            Date date = new Date(scan.nextLine());
            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            orderDate = simpleDate.format(date);
            
            int pid;
            int quantity;
            while (true)
            {
                System.out.println("Product ID?");
                pid = scan.nextInt();
                if (productExists(pid))
                {
                    System.out.println("How many?");
                    quantity = scan.nextInt();
                    products.put(pid, quantity);
                    break;
                }
                else
                {
                    System.out.println("ProductID does not exist\n");
                }

            }

            String userInput;
            do
            {
                System.out.println("Product ID?");
                userInput = scan.next();
                if (!userInput.equals(""))
                {
                    pid = Integer.parseInt(userInput);
                    if (productExists(pid))
                    {
                        System.out.println("How many?");
                        quantity = scan.nextInt();
                        products.put(pid, quantity);
                    }
                    else
                    {
                        System.out.println("ProductID does not exist\n");
                    }
                }
            }
            while (!userInput.equals(""));

            for (Map.Entry<Integer, Integer> entry : products.entrySet())
            {
                //Consider removing redundant priceEach
                
                //FIX 0
                String SQL = "INSERT INTO orderdetails (OrderID, Style, Quantity) VALUES (SELECT (MAX(ProductID) + 1) FROM ," + entry.getKey() + ", " + entry.getValue() + ")";
                st.executeUpdate(SQL);
                totalCost += entry.getKey()*entry.getValue();
            }
            
            //add totalCost to orders
            String SQL = "INSERT INTO orders VALUES (" + orderDate + "," + totalCost + ")";
            st.executeUpdate(SQL);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private boolean productExists(int pid) throws SQLException
    {
        Statement st = connection.createStatement();
        String SQL = "SELECT ProductID FROM products WHERE ProductID = " + Integer.toString(pid);
        ResultSet rs = st.executeQuery(SQL);
        return rs.next();
    }

    //******************************************************************
    private abstract class Command
    {
        public abstract String help();

        public abstract void execute(String argument) throws IllegalArgumentException;

    }

    private class ShowAllInfo extends Command
    {
        public String help()
        {
            return "showAllInfo";
        }

        public void execute(String argument) throws IllegalArgumentException
        {
            try
            {
                showAll();
            }
            catch (NumberFormatException numFormE)
            {
                throw new IllegalArgumentException("error");
            }
        }
    }

    private class Show extends Command
    {
        public String help()
        {
            return "Show <orderID>";
        }

        public void execute(String argument) throws IllegalArgumentException
        {
            try
            {
                showOrder(Integer.parseInt(argument));
            }
            catch (NumberFormatException numFormE)
            {
                throw new IllegalArgumentException("Show " + argument + " : argument not an integer.");
            }
        }
    }

    private class Insert extends Command
    {
        public String help()
        {
            return "Insert <orderID>";
        }

        public void execute(String argument) throws IllegalArgumentException
        {
            try
            {
                insert();
            }
            catch (NumberFormatException numFormE)
            {
                throw new IllegalArgumentException("Show " + argument + " : argument not an integer.");
            }
        }
    }

    private class Help extends Command
    {
        public String help()
        {
            return "help";
        }

        public void execute(String argument) throws IllegalArgumentException
        {

            Iterator<String> keywordIter = commands.keySet().iterator();

            if (keywordIter.hasNext())
            {
                System.out.print("Commands: " + commands.get(keywordIter.next()).help());
                while (keywordIter.hasNext())
                {
                    System.out.print(", " + commands.get(keywordIter.next()).help());
                }
                System.out.println(".");
            }
            else
            {
                System.out.println("No commands installed.");
            }
        }
    }

    private class Quit extends Command
    {
        public String help()
        {
            return "quit";
        }

        public void execute(String argument) throws IllegalArgumentException
        {
            try
            {
                connection.close();
            }
            catch (SQLException e)
            {

            }
            System.exit(0);
        }
    }
}
