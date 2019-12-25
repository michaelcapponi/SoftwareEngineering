
import com.oracle.util.Checksums;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author biar
 */
public class App {
    static String db = "test.db";
    static String url = "jdbc:sqlite:" + db;

    public static void createNewDatabase(Connection conn) {

        try {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("Il nome del driver è " + meta.getDriverName());
                System.out.println("E' stato creato il datadase " + db);
            }	
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create a new table in the test database
     *
     * @param conn
     */
    public static void createNewTable(Connection conn) {

            // SQL statement for creating a new table
            String sql = "CREATE TABLE IF NOT EXISTS warehouses (\n"
                            + "	id integer PRIMARY KEY,\n"
                            + "	name text NOT NULL,\n"
                            + "	capacity real\n"
                            + ");";


            Statement stmt;
            try {
                    stmt = conn.createStatement();
                    // create a new table
                    System.out.println("E' stata creata la tabella warehouses");
                    stmt.execute(sql);
            } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }
    }	

    /**
     * Inserimento di un record nella tabella warehouses
     *
     * @param conn
     * @param name
     * @param capacity
     */
    public static void insert(Connection conn, String name, double capacity) {
        String sql = "INSERT INTO warehouses(name,capacity) VALUES(?,?)";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setDouble(2, capacity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    } 

    /**
     * Seleziona tutti i record della tabella warehouses
     *
     * @param conn
     */
    public static void selectAll(Connection conn) {
        String sql = "SELECT id, name, capacity FROM warehouses";

        try {
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" + 
                                rs.getString("name") + "\t" +
                                rs.getDouble("capacity"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

        /**
     * Update data of a warehouse specified by the id
     *
     * @param conn
     * @param id
     * @param name name of the warehouse
     * @param capacity capacity of the warehouse
     * 
     */
    
    public static void update(Connection conn, int id, String name, double capacity) {
        String sql = "UPDATE warehouses SET name = ? , "
                + "capacity = ? "
                + "WHERE id = ?";

        try (
            PreparedStatement pstmt = conn.prepareStatement(sql)){ 

            // set the corresponding param
            pstmt.setString(1, name);
            pstmt.setDouble(2, capacity);
            pstmt.setInt(3, id);
            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Restituisce i record la cui capacità è maggiore di quella fornita in input
     * 
     * @param conn
     * @param capacity 
     */
    public static void getCapacityGreaterThan(Connection conn, double capacity){
        String sql = "SELECT id, name, capacity "
                        + "FROM warehouses WHERE capacity > ?";

        try {
            PreparedStatement pstmt  = conn.prepareStatement(sql); 

            // set the value
            pstmt.setDouble(1,capacity);
            //
            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" + 
                                rs.getString("name") + "\t" +
                                rs.getDouble("capacity"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main (String[] args) {

            Connection conn = SQLiteJDBCDriverConnection.connect(url);
            createNewDatabase(conn);
            createNewTable(conn);
            insert(conn, "Materiali grezzi", 4000);
            insert(conn, "Semilavorati", 1000);
            insert(conn, "Prodotti finiti", 6000);
            update(conn, 1, "Materiali grezzi", 5000);
            selectAll(conn);
            SQLiteJDBCDriverConnection.closeConnection(conn);
    }
}
