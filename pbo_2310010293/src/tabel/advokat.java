/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tabel;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.sql.ResultSetMetaData;

/**
 *
 * @author DIMZY
 */
public class advokat {
    private Connection Koneksidb;
    private String username="root";
    private String password="";
    private String dbname="pbo_2310010293";
    private String urlKoneksi="jdbc:mysql://localhost/"+dbname;
    public String CEK_NAMA, CEK_EMAIL, CEK_PW, CEK_TELP, CEK_DESK=null;
    public boolean duplikasi=false;
    
    public advokat(){
        try {
            Driver dbdriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(dbdriver);
            Koneksidb=DriverManager.getConnection(urlKoneksi,username,password);
            System.out.print("Database Terkoneksi");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }
    
    public void simpanadvokat01(String id, String nm, String email, String pw, String telp, String desk){
        try {
            String sqlsimpan="insert into advokat(id_advokat, nama, email, pass, no_tlp, desk) VALUES"
                    + " ('"+id+"', '"+nm+"', '"+email+"', '"+pw+"', '"+telp+"', '"+desk+"')";
            String sqlcari="select*from advokat where id_advokat='"+id+"'";
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Advokat sudah terdaftar ");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.executeUpdate(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
     public void simpanadvokat02(String id, String nm, String email, String pw, String telp, String desk){
        try {
            String sqlsimpan="insert into advokat(id_advokat, nama, email, pass, no_tlp, desk)"
                    + " value (?, ?, ?, ?, ?, ?)";
            String sqlcari= "select*from advokat where id_advokat=?";
            PreparedStatement cari=Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, id);
            ResultSet data = cari.executeQuery();
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID sudah terdaftar");
                this.duplikasi=true;
                this.CEK_NAMA=data.getString("nama");           
                this.CEK_EMAIL=data.getString("email");           
                this.CEK_PW=data.getString("pass");           
                this.CEK_TELP=data.getString("no_tlp");           
                this.CEK_DESK=data.getString("desk");           
            } else {
                this.duplikasi=false;
                this.CEK_NAMA=null;
                this.CEK_EMAIL=null;
                this.CEK_PW=null;
                this.CEK_TELP=null;
                this.CEK_DESK=null;
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, id );
                perintah.setString(2, nm);
                perintah.setString(3, email);
                perintah.setString(4, pw);
                perintah.setString(5, telp);
                perintah.setString(6, desk);
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
     }
     
     public void ubahAdvokat(String id, String nm, String email, String pw, String telp, String desk){
        try {
            String sqlubah="UPDATE advokat SET nama=?, email=?, pass=?, no_tlp=?, desk=? WHERE id_advokat=?";        
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlubah);
                perintah.setString(1, nm);
                perintah.setString(2, email);
                perintah.setString(3, pw);
                perintah.setString(4, telp);
                perintah.setString(5, desk);
                perintah.setString(6, id);
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        }
     
      public void hapusAdvokat(String id){
        try {
            String sqlhapus="delete from advokat where id_advokat=?";        
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlhapus);
                perintah.setString(1, id );
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public void tampilDataAdvokat(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            modeltabel.addColumn("ID Advokat");
            modeltabel.addColumn("Nama Advokat");
            modeltabel.addColumn("Email");
            modeltabel.addColumn("Password");
            modeltabel.addColumn("No Telp");
            modeltabel.addColumn("Deskripsi");
            while(data.next()){
                Object[] row = new Object[jumlahkolom];
                for(int i=1; i<=jumlahkolom; i++){
                    row[i-1]=data.getObject(i);
                }
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
        }
    }    
}



