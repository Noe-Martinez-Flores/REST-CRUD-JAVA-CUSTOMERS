package mx.edu.utez.newProyect.model;

import mx.edu.utez.newProyect.database.ConnectionMySQL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoCustomers {
    private Connection con;
    private CallableStatement cstm;
    private ResultSet rs;


    public List<Customers> findAll (){
        List<Customers> listCustomers = new ArrayList<>();
        try{
            con = ConnectionMySQL.getConnection();
            cstm = con.prepareCall("SELECT * FROM customers;");
            rs = cstm.executeQuery();

            while (rs.next()){
                Customers customers = new Customers();
                customers.setCustomerNumber(rs.getInt("customerNumber"));
                customers.setCustomerName(rs.getString("customerName"));
                customers.setContactLastName(rs.getString("customerName"));
                customers.setContactFirstName(rs.getString("contactFirstName"));
                customers.setPhone(rs.getString("phone"));
                customers.setAddressLine1(rs.getString("addressLine1"));
                customers.setAddressLine2(rs.getString("addressLine2"));
                customers.setCity(rs.getString("city"));
                customers.setState(rs.getString("state"));
                customers.setPostalCode(rs.getString("postalCode"));
                customers.setCountry(rs.getString("country"));
                customers.setSalesRepEmployeeNumber(rs.getInt("salesRepEmployeeNumber"));
                customers.setCreditLimit(rs.getDouble("creditLimit"));
                listCustomers.add(customers);
            }

        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }
        return listCustomers;

    }

    public Customers findCustomer(int id){
        Customers customers = null;

        try{
            con = ConnectionMySQL.getConnection();
            cstm = con.prepareCall("SELECT * FROM customers WHERE customerNumber = ?;");
            cstm.setInt(1, id);
            rs = cstm.executeQuery();

            if(rs.next()){
                customers = new Customers();
                customers.setCustomerNumber(rs.getInt("customerNumber"));
                customers.setCustomerName(rs.getString("customerName"));
                customers.setContactLastName(rs.getString("customerName"));
                customers.setContactFirstName(rs.getString("contactFirstName"));
                customers.setPhone(rs.getString("phone"));
                customers.setAddressLine1(rs.getString("addressLine1"));
                customers.setAddressLine2(rs.getString("addressLine2"));
                customers.setCity(rs.getString("city"));
                customers.setState(rs.getString("state"));
                customers.setPostalCode(rs.getString("postalCode"));
                customers.setCountry(rs.getString("country"));
                customers.setSalesRepEmployeeNumber(rs.getInt("salesRepEmployeeNumber"));
                customers.setCreditLimit(rs.getDouble("creditLimit"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            closeConnection();
        }
        return customers;
    }

    public boolean save(Customers customers, boolean isCreate){
        boolean flag = false;

        try{
            con = ConnectionMySQL.getConnection();
            if(isCreate){
                cstm = con.prepareCall("INSERT INTO customers (customerNumber , customerName , contactLastName , contactFirstName , phone , addressLine1 , addressLine2 , city ,state, postalCode , country , salesRepEmployeeNumber, creditLimit )VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?);");

                cstm.setInt(1, customers.getCustomerNumber());
                cstm.setString(2, customers.getCustomerName());
                cstm.setString(3, customers.getContactLastName());
                cstm.setString(4, customers.getContactFirstName());
                cstm.setString(5, customers.getPhone());
                cstm.setString(6, customers.getAddressLine1());
                cstm.setString(7, customers.getAddressLine2());
                cstm.setString(8, customers.getCity());
                cstm.setString(9, customers.getState());
                cstm.setString(10, customers.getPostalCode());
                cstm.setString(11, customers.getCountry());
                cstm.setInt(12, customers.getSalesRepEmployeeNumber());
                cstm.setDouble(13, customers.getCreditLimit());
            } else {
                cstm = con.prepareCall("UPDATE customers SET customerName = ?, contactLastName = ?, contactFirstName  = ?, phone = ?  , addressLine1 = ?, addressLine2  = ?, city = ?,state = ?, postalCode = ?, country = ?, salesRepEmployeeNumber = ? , creditLimit = ? WHERE customerNumber = ?;");


                cstm.setString(1, customers.getCustomerName());
                cstm.setString(2, customers.getContactLastName());
                cstm.setString(3, customers.getContactFirstName());
                cstm.setString(4, customers.getPhone());
                cstm.setString(5, customers.getAddressLine1());
                cstm.setString(6, customers.getAddressLine2());
                cstm.setString(7, customers.getCity());
                cstm.setString(8, customers.getState());
                cstm.setString(9, customers.getPostalCode());
                cstm.setString(10, customers.getCountry());
                cstm.setInt(11, customers.getSalesRepEmployeeNumber());
                cstm.setDouble(12, customers.getCreditLimit());
                cstm.setInt(13, customers.getCustomerNumber());
            }

            flag = cstm.executeUpdate() == 1;
        }catch(SQLException e){
            System.out.printf("Ha sucedido algún error: %s", e.getMessage());
        }finally{
            closeConnection();
        }
        return flag;
    }

    public boolean delete (int id){
        boolean flag = false;
        try{
            con = ConnectionMySQL.getConnection();
            cstm = con.prepareCall("DELETE FROM customers WHERE customerNumber = ?;");
            cstm.setInt(1, id);
            flag = cstm.executeUpdate() == 1;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return flag;
    }

    public static void main (String []args){
        DaoCustomers db = new DaoCustomers();
        List<Customers> list = db.findAll();
        for (Customers customers : list){
            System.out.println(customers.getCustomerNumber());
            System.out.println(customers.getCustomerName());
            System.out.println(customers.getContactLastName());
            System.out.println(customers.getContactFirstName());
            System.out.println(customers.getPhone());
            System.out.println(customers.getAddressLine1());
            System.out.println(customers.getAddressLine2());
            System.out.println(customers.getCity());
            System.out.println(customers.getState());
            System.out.println(customers.getPostalCode());
            System.out.println(customers.getCountry());
            System.out.println(customers.getSalesRepEmployeeNumber());
            System.out.println(customers.getCreditLimit());
        }
    }


    public void closeConnection() {
        try {
            if (con != null) {
                con.close();
            }
            if (cstm != null) {
                cstm.close();
            }
            if (rs != null) {
                rs.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
