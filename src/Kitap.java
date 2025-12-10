import java.time.LocalDate;

public class Kitap {
    private String KitapAdi;
    private int Kitapİd;
    private String YazarAdi;
    private int YayinYili;
    private int SayfaSayisi;
    private LocalDate TeslimTarihi;
    private int StokSayisi;
    private String RafKonumu;

    public Kitap(String kitapAdi, String rafKonumu, int stokSayisi, int kitapİd, String yazarAdi, int yayinYili, int sayfaSayisi, LocalDate teslimTarihi) {
        this.KitapAdi = kitapAdi;
        this.RafKonumu = rafKonumu;
        this.StokSayisi = stokSayisi;
        this.Kitapİd = kitapİd;
        this.YazarAdi = yazarAdi;
        this.YayinYili = yayinYili;
        this.SayfaSayisi = sayfaSayisi;

        this.TeslimTarihi = teslimTarihi;
    }

    public String getRafKonumu() {
        return RafKonumu;
    }

    public int getStokSayisi() {
        return StokSayisi;
    }

    public String getKitapAdi() {
        return KitapAdi;
    }

    public String getYazarAdi() {
        return YazarAdi;
    }

    public int getSayfaSayisi() {
        return SayfaSayisi;
    }




    public LocalDate getTeslimTarihi() {
        return TeslimTarihi;
    }

    public int getYayinYili() {
        return YayinYili;
    }

    public int getKitapİd() {
        return Kitapİd;
    }

    public void setRafKonumu(String rafKonumu) {
        RafKonumu = rafKonumu;
    }

    public void setStokSayisi(int stokSayisi) {
        StokSayisi = stokSayisi;
    }

    public void setTeslimTarihi(LocalDate teslimTarihi) {
        TeslimTarihi = teslimTarihi;
    }



    public void setSayfaSayisi(int sayfaSayisi) {
        SayfaSayisi = sayfaSayisi;
    }

    public void setYayinYili(int yayinYili) {
        YayinYili = yayinYili;
    }

    public void setYazarAdi(String yazarAdi) {
        YazarAdi = yazarAdi;
    }

    public void setKitapİd(int kitapİd) {
        Kitapİd = kitapİd;
    }

    public void setKitapAdi(String kitapAdi) {
        KitapAdi = kitapAdi;
    }
}
