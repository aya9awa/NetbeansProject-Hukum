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
public class hkonsul {
    private Connection Koneksidb;
    private String username="root";
    private String password="";
    private String dbname="pbo_2310010293";
    private String urlKoneksi="jdbc:mysql://localhost/"+dbname;
    public String CEK_JAWAB, CEK_TGL, CEK_STS=null;
    public boolean duplikasi=false;
    
    public hkonsul(){
        try {
            Driver dbdriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(dbdriver);
            Koneksidb=DriverManager.getConnection(urlKoneksi,username,password);
            System.out.print("Database Terkoneksi");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }
    
    public void simpanhkons01(String id, String jawab, String tgl, String sts){
        try {
            String sqlsimpan="insert into hkonsul (idhkons, jawab, tgl, status) VALUES"
                    + " ('"+id+"', '"+jawab+"', '"+tgl+"', '"+sts+"')";
            String sqlcari="select*from hkonsul where id_kons='"+id+"'";
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Hasil Konsultasi sudah terdaftar ");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.executeUpdate(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
     public void simpanhkons02(String id, String jawab, String tgl, String sts){
        try {
            String sqlsimpan="insert into hkonsul(idhkons, jawab, tgl, status)"
                    + " value (?, ?, ?, ?)";
            String sqlcari= "select*from hkonsul where idhkons=?";
            PreparedStatement cari=Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, id);
            ResultSet data = cari.executeQuery();
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID sudah terdaftar");
                this.duplikasi=true;
                this.CEK_JAWAB=data.getString("jawab");           
                this.CEK_TGL=data.getString("tgl");                      
                this.CEK_STS=data.getString("status");                      
            } else {
                this.duplikasi=false;
                this.CEK_JAWAB=null;
                this.CEK_TGL=null;
                this.CEK_STS=null;
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, id );
                perintah.setString(2, jawab);
                perintah.setString(3, tgl);
                perintah.setString(4, sts);
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
     }
     
     public void ubahHKonsultasi(String id, String jawab, String tgl, String sts){
        try {
            String sqlubah="UPDATE hkonsul SET jawab=?, tgl=?, status=? WHERE idhkons=?";        
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlubah);
                perintah.setString(1, jawab);
                perintah.setString(2, tgl);
                perintah.setString(3, sts);
                perintah.setString(4, id);
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        }
     
      public void hapusHKonsultasi(String id){
        try {
            String sqlhapus="delete from hkonsul where idhkons=?";        
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlhapus);
                perintah.setString(1, id );
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public void tampilDataHKonsultasi(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            modeltabel.addColumn("ID Hasil Konsultasi");
            modeltabel.addColumn("Jawaban");
            modeltabel.addColumn("Tanggal");
            modeltabel.addColumn("Status");
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



