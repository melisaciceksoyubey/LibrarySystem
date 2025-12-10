import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        KullanıcıVeriTabaniOluştur kullanıcıVeriTabaniOluştur = new KullanıcıVeriTabaniOluştur();
        KitapVeriTabaniOluştur kitapVeriTabaniOluştur = new KitapVeriTabaniOluştur();
        AdminVeriTabaniOluştur adminVeriTabaniOluştur = new AdminVeriTabaniOluştur();


        System.out.println("\nTüm kullanıcılar:");
        kullanıcıVeriTabaniOluştur.tumKullanicilar().forEach(u ->
                System.out.println(u.getKullaniciİd() + " - " + u.getAdSoyad())
        );

        System.out.println("\nTüm kitaplar:");
        kitapVeriTabaniOluştur.tumKitaplar().forEach(b ->
                System.out.println(b.getKitapİd() + " - " + b.getKitapAdi())
        );


        if (adminVeriTabaniOluştur.adminGiris("Melisa", "1234")) {
            System.out.println("Admin girişi başarılı!");
        } else {
            System.out.println("Admin bilgileri hatalı!");
        }
    }
}
