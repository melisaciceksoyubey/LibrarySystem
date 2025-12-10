import java.sql.*;

public class AdminVeriTabaniOluştur {
    public void adminEkle(Admin admin) {

        String sql = "INSERT INTO admin (adminid, adsoyad, sifre) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, admin.getAdminİd());
            ps.setString(2, admin.getAdSoyad());
            ps.setString(3, admin.getŞifre());

            ps.executeUpdate();
            System.out.println("Admin başarıyla eklendi: " + admin.getAdSoyad());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean adminGiris(String ad, String sifre) {
        String sql = "SELECT * FROM admin WHERE adsoyad = ? AND sifre = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ad);
            ps.setString(2, sifre);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
