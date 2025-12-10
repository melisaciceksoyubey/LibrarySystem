import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OduncVeriTabaniOluştur {
    
    public void kitapVer(int kullaniciId, int kitapId, LocalDate teslimTarihi) {
        String sql = "INSERT INTO odunc (kullaniciid, kitapid, alistarihi, teslimtarihi, durum) VALUES (?, ?, CURRENT_DATE, ?, 'Ödünç Verildi')";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, kullaniciId);
            ps.setInt(2, kitapId);
            ps.setDate(3, java.sql.Date.valueOf(teslimTarihi));
            
            ps.executeUpdate();
            
            // Stok azalt
            String stokSql = "UPDATE kitap SET stok = stok - 1 WHERE kitapid = ?";
            try (PreparedStatement stokPs = conn.prepareStatement(stokSql)) {
                stokPs.setInt(1, kitapId);
                stokPs.executeUpdate();
            }
            
            System.out.println("Kitap ödünç verildi. Kullanıcı ID: " + kullaniciId + ", Kitap ID: " + kitapId);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void kitapTeslimAl(int oduncId) {
        String sql = "UPDATE odunc SET durum = 'Teslim Edildi', teslimedilditarihi = CURRENT_DATE WHERE oduncid = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, oduncId);
            ps.executeUpdate();
            
            // Kitap ID'yi al
            String kitapIdSql = "SELECT kitapid FROM odunc WHERE oduncid = ?";
            int kitapId = 0;
            try (PreparedStatement kitapPs = conn.prepareStatement(kitapIdSql)) {
                kitapPs.setInt(1, oduncId);
                ResultSet rs = kitapPs.executeQuery();
                if (rs.next()) {
                    kitapId = rs.getInt("kitapid");
                }
            }
            
            // Stok artır
            if (kitapId > 0) {
                String stokSql = "UPDATE kitap SET stok = stok + 1 WHERE kitapid = ?";
                try (PreparedStatement stokPs = conn.prepareStatement(stokSql)) {
                    stokPs.setInt(1, kitapId);
                    stokPs.executeUpdate();
                }
            }
            
            System.out.println("Kitap teslim alındı. Ödünç ID: " + oduncId);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<OduncBilgi> kullaniciKitaplari(int kullaniciId) {
        List<OduncBilgi> liste = new ArrayList<>();
        String sql = "SELECT o.*, k.kitapadi, k.yazaradi, u.adsoyad " +
                     "FROM odunc o " +
                     "JOIN kitap k ON o.kitapid = k.kitapid " +
                     "JOIN kullanici u ON o.kullaniciid = u.kullaniciid " +
                     "WHERE o.kullaniciid = ? AND o.durum = 'Ödünç Verildi'";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, kullaniciId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                OduncBilgi ob = new OduncBilgi(
                    rs.getInt("oduncid"),
                    rs.getInt("kullaniciid"),
                    rs.getString("adsoyad"),
                    rs.getInt("kitapid"),
                    rs.getString("kitapadi"),
                    rs.getString("yazaradi"),
                    rs.getDate("alistarihi").toLocalDate(),
                    rs.getDate("teslimtarihi").toLocalDate(),
                    rs.getString("durum")
                );
                liste.add(ob);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return liste;
    }
    
    public List<OduncBilgi> tumOduncKitaplar() {
        List<OduncBilgi> liste = new ArrayList<>();
        String sql = "SELECT o.*, k.kitapadi, k.yazaradi, u.adsoyad " +
                     "FROM odunc o " +
                     "JOIN kitap k ON o.kitapid = k.kitapid " +
                     "JOIN kullanici u ON o.kullaniciid = u.kullaniciid " +
                     "ORDER BY o.alistarihi DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                OduncBilgi ob = new OduncBilgi(
                    rs.getInt("oduncid"),
                    rs.getInt("kullaniciid"),
                    rs.getString("adsoyad"),
                    rs.getInt("kitapid"),
                    rs.getString("kitapadi"),
                    rs.getString("yazaradi"),
                    rs.getDate("alistarihi").toLocalDate(),
                    rs.getDate("teslimtarihi").toLocalDate(),
                    rs.getString("durum")
                );
                liste.add(ob);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return liste;
    }
    
    public boolean kitapVerilmisMi(int kitapId) {
        String sql = "SELECT COUNT(*) FROM odunc WHERE kitapid = ? AND durum = 'Ödünç Verildi'";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, kitapId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public String kitapKonumu(int kitapId) {
        String sql = "SELECT u.adsoyad, o.durum " +
                     "FROM odunc o " +
                     "JOIN kullanici u ON o.kullaniciid = u.kullaniciid " +
                     "WHERE o.kitapid = ? AND o.durum = 'Ödünç Verildi' " +
                     "LIMIT 1";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, kitapId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getString("adsoyad") + " - Ödünç Verildi";
            }
            
            // Eğer ödünç verilmemişse raf konumunu al
            String rafSql = "SELECT raf FROM kitap WHERE kitapid = ?";
            try (PreparedStatement rafPs = conn.prepareStatement(rafSql)) {
                rafPs.setInt(1, kitapId);
                ResultSet rafRs = rafPs.executeQuery();
                if (rafRs.next()) {
                    return "Raf: " + rafRs.getString("raf");
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return "Bilinmiyor";
    }
}

