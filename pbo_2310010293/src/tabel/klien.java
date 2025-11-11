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
public class klien {
    private Connection Koneksidb;
    private String username="root";
    private String password="";
    private String dbname="pbo_2310010293";
    private String urlKoneksi="jdbc:mysql://localhost/"+dbname;
    public String CEK_NAMA, CEK_EMAIL, CEK_TELP=null;
    public boolean duplikasi=false;
    
    public klien(){
        try {
            Driver dbdriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(dbdriver);
            Koneksidb=DriverManager.getConnection(urlKoneksi,username,password);
            System.out.print("Database Terkoneksi");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }
    
    public void simpanklien01(String id, String nama, String email, String hp){
        try {
            String sqlsimpan="insert into klien (idklien, nama, email, no_tlp) VALUES"
                    + " ('"+id+"', '"+nama+"', '"+email+"', '"+hp+"')";
            String sqlcari="select*from klien where idklien='"+id+"'";
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID klien sudah terdaftar ");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.executeUpdate(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
     public void simpanklien02(String id, String nama, String email, String hp){
        try {
            String sqlsimpan="insert into klien(idklien, nama, email, no_tlp)"
                    + " value (?, ?, ?, ?)";
            String sqlcari= "select*from klien where idklien=?";
            PreparedStatement cari=Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, id);
            ResultSet data = cari.executeQuery();
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID sudah terdaftar");
                this.duplikasi=true;
                this.CEK_NAMA=data.getString("nama");           
                this.CEK_EMAIL=data.getString("email");                      
                this.CEK_TELP=data.getString("no_tlp");                      
            } else {
                this.duplikasi=false;
                this.CEK_NAMA=null;
                this.CEK_EMAIL=null;
                this.CEK_TELP=null;
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, id );
                perintah.setString(2, nama);
                perintah.setString(3, email);
                perintah.setString(4, hp);
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
     }
     
     public void ubahKlien(String id, String nama, String email, String hp){
        try {
            String sqlubah="UPDATE klien SET nama=?, email=?, no_tlp=? WHERE idklien=?";        
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlubah);
                perintah.setString(1, nama);
                perintah.setString(2, email);
                perintah.setString(3, hp);
                perintah.setString(4, id);
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        }
     
      public void hapusKlien(String id){
        try {
            String sqlhapus="delete from klien where idklien=?";        
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlhapus);
                perintah.setString(1, id );
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public void tampilDataKlien(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            modeltabel.addColumn("ID Klien");
            modeltabel.addColumn("Nama");
            modeltabel.addColumn("Email");
            modeltabel.addColumn("No Telepon");
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



