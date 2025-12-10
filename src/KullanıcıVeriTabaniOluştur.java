import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KullanıcıVeriTabaniOluştur {

    public void kullaniciEkle(Kullanıcı k) {
        String sql = "INSERT INTO kullanici (adsoyad, kullaniciid) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, k.getAdSoyad());
            ps.setInt(2, k.getKullaniciİd());
            ps.executeUpdate();

            System.out.println("Kullanıcı eklendi: " + k.getAdSoyad());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Kullanıcı> tumKullanicilar() {
        List<Kullanıcı> liste = new ArrayList<>();
        String sql = "SELECT * FROM kullanici";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Kullanıcı k = new Kullanıcı(
                        rs.getString("adsoyad"),
                        rs.getInt("kullaniciid")
                );
                liste.add(k);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }
}
