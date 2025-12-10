# Kütüphane Yönetim Sistemi

Modern ve kullanıcı dostu bir kütüphane yönetim sistemi. JavaFX ile geliştirilmiş grafik arayüze sahip, PostgreSQL veritabanı kullanan bir uygulamadır.

## Özellikler

- 🔐 **Güvenli Giriş Sistemi**: Admin girişi ile sistem erişimi
- 📚 **Kitap Yönetimi**: 
  - Kitap ekleme
  - Kitap listeleme
  - Kitap silme
  - Kitap bilgilerini görüntüleme
- 👥 **Kullanıcı Yönetimi**:
  - Kullanıcı ekleme
  - Kullanıcı listeleme
- 🎨 **Modern UI**: JavaFX ile geliştirilmiş kullanıcı dostu arayüz
- 💾 **Veritabanı Entegrasyonu**: PostgreSQL ile güvenli veri saklama

## Gereksinimler

- Java JDK 11 veya üzeri (JDK 22 önerilir)
- PostgreSQL veritabanı
- JavaFX SDK (JDK 11+ ile birlikte gelir, JDK 17+ için ayrı indirme gerekebilir)
- PostgreSQL JDBC Driver (postgresql-42.7.8.jar projede mevcut)

## Kurulum

### 1. Veritabanı Kurulumu

PostgreSQL veritabanınızda aşağıdaki veritabanını oluşturun:

```sql
CREATE DATABASE kutuphaneSistemi;
```

### 2. Veritabanı Bağlantı Ayarları

`src/DBConnection.java` dosyasında veritabanı bağlantı bilgilerinizi güncelleyin:

```java
private static final String URL = "jdbc:postgresql://localhost:5432/kutuphaneSistemi";
private static final String USER = "postgres";
private static final String PASS = "şifreniz";
```

### 3. Tabloları Oluşturma

Veritabanında aşağıdaki tabloları oluşturun:

#### Admin Tablosu
```sql
CREATE TABLE admin (
    adminid INTEGER PRIMARY KEY,
    adsoyad VARCHAR(100) NOT NULL,
    sifre VARCHAR(50) NOT NULL
);
```

#### Kullanıcı Tablosu
```sql
CREATE TABLE kullanici (
    kullaniciid INTEGER PRIMARY KEY,
    adsoyad VARCHAR(100) NOT NULL
);
```

#### Kitap Tablosu
```sql
CREATE TABLE kitap (
    kitapid INTEGER PRIMARY KEY,
    kitapadi VARCHAR(200) NOT NULL,
    yazaradi VARCHAR(100) NOT NULL,
    yayinyili INTEGER,
    sayfasayisi INTEGER,
    stok INTEGER,
    raf VARCHAR(50),
    teslimtarihi DATE
);
```

### 4. Örnek Admin Kullanıcısı Ekleme

```sql
INSERT INTO admin (adminid, adsoyad, sifre) VALUES (1, 'Melisa', '1234');
```

## Çalıştırma

### IntelliJ IDEA ile

1. Projeyi IntelliJ IDEA'da açın
2. `src/LibraryApp.java` dosyasını ana sınıf olarak ayarlayın
3. JavaFX modülünü projeye ekleyin (gerekirse)
4. Projeyi çalıştırın

### Komut Satırı ile

```bash
# Projeyi derleyin
javac --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -cp postgresql-42.7.8.jar src/*.java

# Uygulamayı çalıştırın
java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -cp ".:postgresql-42.7.8.jar" LibraryApp
```

## Kullanım

1. Uygulamayı başlattığınızda giriş ekranı açılır
2. Admin kullanıcı adı ve şifresi ile giriş yapın (varsayılan: Melisa / 1234)
3. Admin panelinde:
   - **Kitap Yönetimi**: Kitapları ekleyebilir, listeleyebilir ve silebilirsiniz
   - **Kullanıcı Yönetimi**: Kullanıcıları ekleyebilir ve listeleyebilirsiniz

## Proje Yapısı

```
LibrarySystem/
├── src/
│   ├── LibraryApp.java          # Ana JavaFX uygulaması
│   ├── Main.java                 # Eski konsol uygulaması
│   ├── DBConnection.java         # Veritabanı bağlantı sınıfı
│   ├── Kitap.java                # Kitap model sınıfı
│   ├── Kullanıcı.java            # Kullanıcı model sınıfı
│   ├── Admin.java                # Admin model sınıfı
│   ├── KitapVeriTabaniOluştur.java    # Kitap veritabanı işlemleri
│   ├── KullanıcıVeriTabaniOluştur.java # Kullanıcı veritabanı işlemleri
│   └── AdminVeriTabaniOluştur.java    # Admin veritabanı işlemleri
├── postgresql-42.7.8.jar         # PostgreSQL JDBC driver
└── README.md                      # Bu dosya
```

## Özellikler Detayı

### Kitap Yönetimi
- Kitap ID, Adı, Yazar, Yayın Yılı, Sayfa Sayısı, Stok, Raf Konumu ve Teslim Tarihi bilgileri ile kitap ekleme
- Tüm kitapları listeleme
- Kitap silme işlemi

### Kullanıcı Yönetimi
- Kullanıcı ID ve Ad Soyad ile kullanıcı ekleme
- Tüm kullanıcıları listeleme

## Teknolojiler

- **Java**: Programlama dili
- **JavaFX**: Grafik kullanıcı arayüzü
- **PostgreSQL**: İlişkisel veritabanı yönetim sistemi
- **JDBC**: Veritabanı bağlantısı

## Geliştirici Notları

- Veritabanı bağlantı bilgileri `DBConnection.java` dosyasında merkezi olarak yönetilmektedir
- Tüm veritabanı işlemleri PreparedStatement kullanılarak SQL injection saldırılarına karşı korunmaktadır
- UI tasarımı modern ve kullanıcı dostu olacak şekilde tasarlanmıştır

## Lisans

Bu proje eğitim amaçlı geliştirilmiştir.

## İletişim

Sorularınız veya önerileriniz için issue açabilirsiniz.

