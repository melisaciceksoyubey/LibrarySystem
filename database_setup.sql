-- Kütüphane Yönetim Sistemi Veritabanı Kurulum Scripti

-- Veritabanını oluştur
CREATE DATABASE kutuphaneSistemi;

-- Veritabanına bağlan
\c kutuphaneSistemi;

-- Admin Tablosu
CREATE TABLE IF NOT EXISTS admin (
    adminid INTEGER PRIMARY KEY,
    adsoyad VARCHAR(100) NOT NULL,
    sifre VARCHAR(50) NOT NULL
);

-- Kullanıcı Tablosu
CREATE TABLE IF NOT EXISTS kullanici (
    kullaniciid INTEGER PRIMARY KEY,
    adsoyad VARCHAR(100) NOT NULL
);

-- Kitap Tablosu
CREATE TABLE IF NOT EXISTS kitap (
    kitapid INTEGER PRIMARY KEY,
    kitapadi VARCHAR(200) NOT NULL,
    yazaradi VARCHAR(100) NOT NULL,
    yayinyili INTEGER,
    sayfasayisi INTEGER,
    stok INTEGER,
    raf VARCHAR(50),
    teslimtarihi DATE
);

-- Ödünç Alma Tablosu
CREATE TABLE IF NOT EXISTS odunc (
    oduncid SERIAL PRIMARY KEY,
    kullaniciid INTEGER NOT NULL,
    kitapid INTEGER NOT NULL,
    alistarihi DATE NOT NULL DEFAULT CURRENT_DATE,
    teslimtarihi DATE NOT NULL,
    teslimedilditarihi DATE,
    durum VARCHAR(50) NOT NULL DEFAULT 'Ödünç Verildi',
    FOREIGN KEY (kullaniciid) REFERENCES kullanici(kullaniciid) ON DELETE CASCADE,
    FOREIGN KEY (kitapid) REFERENCES kitap(kitapid) ON DELETE CASCADE
);

-- Örnek Admin Kullanıcısı
INSERT INTO admin (adminid, adsoyad, sifre) VALUES (1, 'Melisa', '1234')
ON CONFLICT (adminid) DO NOTHING;

-- Örnek Kullanıcılar
INSERT INTO kullanici (kullaniciid, adsoyad) VALUES 
    (1, 'Ahmet Yılmaz'),
    (2, 'Ayşe Demir'),
    (3, 'Mehmet Kaya')
ON CONFLICT (kullaniciid) DO NOTHING;

-- Örnek Kitaplar
INSERT INTO kitap (kitapadi, kitapid, yazaradi, yayinyili, sayfasayisi, stok, raf, teslimtarihi) VALUES 
    ('Suç ve Ceza', 1, 'Fyodor Dostoyevski', 1866, 671, 5, 'A-101', '2024-12-31'),
    ('Savaş ve Barış', 2, 'Lev Tolstoy', 1869, 1225, 3, 'A-102', '2024-12-31'),
    ('1984', 3, 'George Orwell', 1949, 328, 8, 'B-201', '2024-12-31')
ON CONFLICT (kitapid) DO NOTHING;

