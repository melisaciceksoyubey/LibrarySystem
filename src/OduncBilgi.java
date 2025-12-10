import java.time.LocalDate;

public class OduncBilgi {
    private int oduncId;
    private int kullaniciId;
    private String kullaniciAdi;
    private int kitapId;
    private String kitapAdi;
    private String yazarAdi;
    private LocalDate alisTarihi;
    private LocalDate teslimTarihi;
    private String durum;
    
    public OduncBilgi(int oduncId, int kullaniciId, String kullaniciAdi, int kitapId, 
                      String kitapAdi, String yazarAdi, LocalDate alisTarihi, 
                      LocalDate teslimTarihi, String durum) {
        this.oduncId = oduncId;
        this.kullaniciId = kullaniciId;
        this.kullaniciAdi = kullaniciAdi;
        this.kitapId = kitapId;
        this.kitapAdi = kitapAdi;
        this.yazarAdi = yazarAdi;
        this.alisTarihi = alisTarihi;
        this.teslimTarihi = teslimTarihi;
        this.durum = durum;
    }
    
    public int getOduncId() { return oduncId; }
    public int getKullaniciId() { return kullaniciId; }
    public String getKullaniciAdi() { return kullaniciAdi; }
    public int getKitapId() { return kitapId; }
    public String getKitapAdi() { return kitapAdi; }
    public String getYazarAdi() { return yazarAdi; }
    public LocalDate getAlisTarihi() { return alisTarihi; }
    public LocalDate getTeslimTarihi() { return teslimTarihi; }
    public String getDurum() { return durum; }
}

