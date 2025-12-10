public class Kullanıcı {
    private String AdSoyad;
    private int Kullaniciİd;

    public Kullanıcı(String adSoyad, int kullaniciİd) {
        this.AdSoyad = adSoyad;
        this.Kullaniciİd = kullaniciİd;
    }

    public int getKullaniciİd() {
        return Kullaniciİd;
    }

    public void setKullaniciİd(int kullaniciİd) {
        Kullaniciİd = kullaniciİd;
    }

    public String getAdSoyad() {
        return AdSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        AdSoyad = adSoyad;
    }
}
