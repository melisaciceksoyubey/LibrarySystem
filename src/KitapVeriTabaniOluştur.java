import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KitapVeriTabaniOluştur {
    public void kitapSil(int kitapId) {
        String sql = "DELETE FROM kitap WHERE kitapid = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, kitapId);

            int sonuc = ps.executeUpdate();

            if (sonuc > 0) {
                System.out.println("Kitap başarıyla silindi. ID: " + kitapId);
            } else {
                System.out.println("Kitap bulunamadı. ID: " + kitapId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void kitapEkle(Kitap k) {
        String sql = "INSERT INTO kitap (kitapadi, kitapid, yazaradi, yayinyili, sayfasayisi, stok, raf, teslimtarihi) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, k.getKitapAdi());
            ps.setInt(2, k.getKitapİd());
            ps.setString(3, k.getYazarAdi());
            ps.setInt(4, k.getYayinYili());
            ps.setInt(5, k.getSayfaSayisi());
            ps.setInt(6, k.getStokSayisi());
            ps.setString(7, k.getRafKonumu());
            ps.setDate(8, java.sql.Date.valueOf(k.getTeslimTarihi()));

            ps.executeUpdate();
            System.out.println("Kitap eklendi: " + k.getKitapAdi());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Kitap> tumKitaplar() {
        List<Kitap> liste = new ArrayList<>();
        String sql = "SELECT * FROM kitap";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Kitap k = new Kitap(
                        rs.getString("kitapadi"),
                        rs.getString("raf"),
                        rs.getInt("stok"),
                        rs.getInt("kitapid"),
                        rs.getString("yazaradi"),
                        rs.getInt("yayinyili"),
                        rs.getInt("sayfasayisi"),
                        rs.getDate("teslimtarihi").toLocalDate()
                );
                liste.add(k);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return liste;
    }
}
