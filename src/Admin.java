import java.time.LocalDate;
import java.util.List;

public class Admin {
    private String AdSoyad;
    private int Adminİd;
    private String şifre;
    public Admin() {
    }

    public String getAdSoyad() {
        return AdSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        AdSoyad = adSoyad;
    }

    public String getŞifre() {
        return şifre;
    }

    public void setŞifre(String şifre) {
        this.şifre = şifre;
    }

    public int getAdminİd() {
        return Adminİd;
    }

    public void setAdminİd(int adminİd) {
        Adminİd = adminİd;
    }

    public Admin(String adSoyad, String şifre, int adminİd) {
        AdSoyad = adSoyad;
        this.şifre = şifre;
        Adminİd = adminİd;
    }

    public void KitapEkle(List<Kitap> KitapListesi, Kitap Kitap) {
        KitapListesi.add(Kitap);
    }
    public void KitapSilme(List<Kitap> KitapListesi, Kitap Kitap) {
        KitapListesi.remove(Kitap);
    }
    public void KitapGuncelle(Kitap kitap, String yeniAd, String yeniYazar) {
        kitap.setKitapAdi(yeniAd);
        kitap.setYazarAdi(yeniYazar);
    }
}

