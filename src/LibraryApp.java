import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LibraryApp extends Application {
    private Stage primaryStage;
    private AdminVeriTabaniOluştur adminDAO = new AdminVeriTabaniOluştur();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Kütüphane Yönetim Sistemi");
        primaryStage.setScene(createLoginScene());
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private Scene createLoginScene() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f5f5f5;");

        Label titleLabel = new Label("Kütüphane Yönetim Sistemi");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label subtitleLabel = new Label("Giriş Yapın");
        subtitleLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #7f8c8d;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Kullanıcı Adı");
        usernameField.setPrefWidth(300);
        usernameField.setPrefHeight(40);
        usernameField.setStyle("-fx-font-size: 14px;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Şifre");
        passwordField.setPrefWidth(300);
        passwordField.setPrefHeight(40);
        passwordField.setStyle("-fx-font-size: 14px;");

        Button loginButton = new Button("Giriş Yap");
        loginButton.setPrefWidth(300);
        loginButton.setPrefHeight(45);
        loginButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-cursor: hand;");
        
        loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-cursor: hand;"));
        loginButton.setOnMouseExited(e -> loginButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-cursor: hand;"));

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12px;");
        errorLabel.setVisible(false);

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Lütfen tüm alanları doldurun!");
                errorLabel.setVisible(true);
                return;
            }

            if (adminDAO.adminGiris(username, password)) {
                errorLabel.setVisible(false);
                primaryStage.setScene(createAdminPanelScene());
            } else {
                errorLabel.setText("Kullanıcı adı veya şifre hatalı!");
                errorLabel.setVisible(true);
            }
        });

        root.getChildren().addAll(titleLabel, subtitleLabel, usernameField, passwordField, loginButton, errorLabel);

        return new Scene(root, 500, 500);
    }

    private Scene createAdminPanelScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ecf0f1;");

        // Üst menü
        HBox topMenu = new HBox(10);
        topMenu.setPadding(new Insets(15));
        topMenu.setStyle("-fx-background-color: #34495e;");

        Label welcomeLabel = new Label("Admin Paneli");
        welcomeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");

        Button logoutButton = new Button("Çıkış Yap");
        logoutButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;");
        logoutButton.setOnAction(e -> primaryStage.setScene(createLoginScene()));

        HBox.setHgrow(welcomeLabel, Priority.ALWAYS);
        topMenu.getChildren().addAll(welcomeLabel, logoutButton);

        // Sol menü
        VBox leftMenu = new VBox(10);
        leftMenu.setPadding(new Insets(20));
        leftMenu.setPrefWidth(200);
        leftMenu.setStyle("-fx-background-color: #2c3e50;");

        Button kitapButton = new Button("Kitap Yönetimi");
        Button kullaniciButton = new Button("Kullanıcı Yönetimi");
        
        styleMenuButton(kitapButton);
        styleMenuButton(kullaniciButton);

        leftMenu.getChildren().addAll(kitapButton, kullaniciButton);

        // Merkez içerik alanı
        StackPane centerPane = new StackPane();
        centerPane.setPadding(new Insets(20));

        // Kitap yönetim ekranı
        ScrollPane kitapPane = createKitapManagementPane();
        kitapPane.setVisible(true);

        // Kullanıcı yönetim ekranı
        ScrollPane kullaniciPane = createKullaniciManagementPane();
        kullaniciPane.setVisible(false);

        centerPane.getChildren().addAll(kitapPane, kullaniciPane);

        kitapButton.setOnAction(e -> {
            kitapPane.setVisible(true);
            kullaniciPane.setVisible(false);
        });

        kullaniciButton.setOnAction(e -> {
            kitapPane.setVisible(false);
            kullaniciPane.setVisible(true);
        });

        root.setTop(topMenu);
        root.setLeft(leftMenu);
        root.setCenter(centerPane);

        return new Scene(root, 1000, 700);
    }

    private void styleMenuButton(Button button) {
        button.setPrefWidth(180);
        button.setPrefHeight(50);
        button.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;"));
    }

    private ScrollPane createKitapManagementPane() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: white;");

        Label titleLabel = new Label("Kitap Yönetimi");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Kitap ekleme formu
        VBox formBox = new VBox(15);
        formBox.setPadding(new Insets(20));
        formBox.setStyle("-fx-background-color: #ecf0f1; -fx-background-radius: 10;");

        Label formTitle = new Label("Yeni Kitap Ekle");
        formTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);

        TextField kitapAdiField = new TextField();
        TextField yazarField = new TextField();
        TextField yayinYiliField = new TextField();
        TextField sayfaSayisiField = new TextField();
        TextField stokField = new TextField();
        TextField rafField = new TextField();
        DatePicker teslimTarihiPicker = new DatePicker();
        TextField kitapIdField = new TextField();

        formGrid.add(new Label("Kitap ID:"), 0, 0);
        formGrid.add(kitapIdField, 1, 0);
        formGrid.add(new Label("Kitap Adı:"), 0, 1);
        formGrid.add(kitapAdiField, 1, 1);
        formGrid.add(new Label("Yazar:"), 0, 2);
        formGrid.add(yazarField, 1, 2);
        formGrid.add(new Label("Yayın Yılı:"), 0, 3);
        formGrid.add(yayinYiliField, 1, 3);
        formGrid.add(new Label("Sayfa Sayısı:"), 0, 4);
        formGrid.add(sayfaSayisiField, 1, 4);
        formGrid.add(new Label("Stok:"), 0, 5);
        formGrid.add(stokField, 1, 5);
        formGrid.add(new Label("Raf Konumu:"), 0, 6);
        formGrid.add(rafField, 1, 6);
        formGrid.add(new Label("Teslim Tarihi:"), 0, 7);
        formGrid.add(teslimTarihiPicker, 1, 7);

        Button ekleButton = new Button("Kitap Ekle");
        ekleButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand; -fx-pref-width: 150; -fx-pref-height: 35;");

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-font-size: 12px;");

        // Kitap listesi - önce tanımlanmalı
        VBox kitapListBox = new VBox(10);

        ekleButton.setOnAction(e -> {
            try {
                int kitapId = Integer.parseInt(kitapIdField.getText());
                String kitapAdi = kitapAdiField.getText();
                String yazar = yazarField.getText();
                int yayinYili = Integer.parseInt(yayinYiliField.getText());
                int sayfaSayisi = Integer.parseInt(sayfaSayisiField.getText());
                int stok = Integer.parseInt(stokField.getText());
                String raf = rafField.getText();
                java.time.LocalDate teslimTarihi = teslimTarihiPicker.getValue();

                if (teslimTarihi == null) {
                    messageLabel.setText("Lütfen teslim tarihini seçin!");
                    messageLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12px;");
                    return;
                }

                Kitap yeniKitap = new Kitap(kitapAdi, raf, stok, kitapId, yazar, yayinYili, sayfaSayisi, teslimTarihi);
                KitapVeriTabaniOluştur kitapDAO = new KitapVeriTabaniOluştur();
                kitapDAO.kitapEkle(yeniKitap);

                messageLabel.setText("Kitap başarıyla eklendi!");
                messageLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 12px;");

                // Formu temizle
                kitapIdField.clear();
                kitapAdiField.clear();
                yazarField.clear();
                yayinYiliField.clear();
                sayfaSayisiField.clear();
                stokField.clear();
                rafField.clear();
                teslimTarihiPicker.setValue(null);

                // Kitap listesini yenile
                refreshKitapListesi(kitapListBox);

            } catch (NumberFormatException ex) {
                messageLabel.setText("Lütfen sayısal alanları doğru formatta girin!");
                messageLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12px;");
            } catch (Exception ex) {
                messageLabel.setText("Hata: " + ex.getMessage());
                messageLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12px;");
            }
        });

        formBox.getChildren().addAll(formTitle, formGrid, ekleButton, messageLabel);

        // Kitap listesi
        VBox listBox = new VBox(10);
        Label listTitle = new Label("Kitap Listesi");
        listTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        refreshKitapListesi(kitapListBox);

        ScrollPane listScroll = new ScrollPane(kitapListBox);
        listScroll.setFitToWidth(true);
        listScroll.setPrefHeight(400);

        listBox.getChildren().addAll(listTitle, listScroll);

        content.getChildren().addAll(titleLabel, formBox, listBox);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }

    private void refreshKitapListesi(VBox kitapListBox) {
        kitapListBox.getChildren().clear();
        KitapVeriTabaniOluştur kitapDAO = new KitapVeriTabaniOluştur();
        java.util.List<Kitap> kitaplar = kitapDAO.tumKitaplar();

        if (kitaplar.isEmpty()) {
            Label emptyLabel = new Label("Henüz kitap eklenmemiş.");
            emptyLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 14px;");
            kitapListBox.getChildren().add(emptyLabel);
        } else {
            for (Kitap kitap : kitaplar) {
                HBox kitapCard = new HBox(15);
                kitapCard.setPadding(new Insets(15));
                kitapCard.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 5; -fx-border-color: #bdc3c7; -fx-border-radius: 5;");

                VBox infoBox = new VBox(5);
                Label kitapInfo = new Label("ID: " + kitap.getKitapİd() + " | " + kitap.getKitapAdi() + " - " + kitap.getYazarAdi());
                kitapInfo.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
                Label detayInfo = new Label("Yayın Yılı: " + kitap.getYayinYili() + " | Sayfa: " + kitap.getSayfaSayisi() + " | Stok: " + kitap.getStokSayisi() + " | Raf: " + kitap.getRafKonumu());
                detayInfo.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");

                infoBox.getChildren().addAll(kitapInfo, detayInfo);

                Button silButton = new Button("Sil");
                silButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 12px; -fx-cursor: hand;");
                silButton.setOnAction(e -> {
                    KitapVeriTabaniOluştur kitapDAO = new KitapVeriTabaniOluştur();
                    kitapDAO.kitapSil(kitap.getKitapİd());
                    refreshKitapListesi(kitapListBox);
                });

                HBox.setHgrow(infoBox, Priority.ALWAYS);
                kitapCard.getChildren().addAll(infoBox, silButton);
                kitapListBox.getChildren().add(kitapCard);
            }
        }
    }

    private ScrollPane createKullaniciManagementPane() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: white;");

        Label titleLabel = new Label("Kullanıcı Yönetimi");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Kullanıcı ekleme formu
        VBox formBox = new VBox(15);
        formBox.setPadding(new Insets(20));
        formBox.setStyle("-fx-background-color: #ecf0f1; -fx-background-radius: 10;");

        Label formTitle = new Label("Yeni Kullanıcı Ekle");
        formTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);

        TextField kullaniciIdField = new TextField();
        TextField adSoyadField = new TextField();

        formGrid.add(new Label("Kullanıcı ID:"), 0, 0);
        formGrid.add(kullaniciIdField, 1, 0);
        formGrid.add(new Label("Ad Soyad:"), 0, 1);
        formGrid.add(adSoyadField, 1, 1);

        Button ekleButton = new Button("Kullanıcı Ekle");
        ekleButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand; -fx-pref-width: 150; -fx-pref-height: 35;");

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-font-size: 12px;");

        VBox kullaniciListBox = new VBox(10);

        ekleButton.setOnAction(e -> {
            try {
                int kullaniciId = Integer.parseInt(kullaniciIdField.getText());
                String adSoyad = adSoyadField.getText();

                if (adSoyad.isEmpty()) {
                    messageLabel.setText("Lütfen ad soyad girin!");
                    messageLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12px;");
                    return;
                }

                Kullanıcı yeniKullanici = new Kullanıcı(adSoyad, kullaniciId);
                KullanıcıVeriTabaniOluştur kullaniciDAO = new KullanıcıVeriTabaniOluştur();
                kullaniciDAO.kullaniciEkle(yeniKullanici);

                messageLabel.setText("Kullanıcı başarıyla eklendi!");
                messageLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 12px;");

                kullaniciIdField.clear();
                adSoyadField.clear();

                refreshKullaniciListesi(kullaniciListBox);

            } catch (NumberFormatException ex) {
                messageLabel.setText("Lütfen kullanıcı ID'sini doğru formatta girin!");
                messageLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12px;");
            } catch (Exception ex) {
                messageLabel.setText("Hata: " + ex.getMessage());
                messageLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12px;");
            }
        });

        formBox.getChildren().addAll(formTitle, formGrid, ekleButton, messageLabel);

        // Kullanıcı listesi
        VBox listBox = new VBox(10);
        Label listTitle = new Label("Kullanıcı Listesi");
        listTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        refreshKullaniciListesi(kullaniciListBox);

        ScrollPane listScroll = new ScrollPane(kullaniciListBox);
        listScroll.setFitToWidth(true);
        listScroll.setPrefHeight(400);

        listBox.getChildren().addAll(listTitle, listScroll);

        content.getChildren().addAll(titleLabel, formBox, listBox);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }

    private void refreshKullaniciListesi(VBox kullaniciListBox) {
        kullaniciListBox.getChildren().clear();
        KullanıcıVeriTabaniOluştur kullaniciDAO = new KullanıcıVeriTabaniOluştur();
        java.util.List<Kullanıcı> kullanicilar = kullaniciDAO.tumKullanicilar();

        if (kullanicilar.isEmpty()) {
            Label emptyLabel = new Label("Henüz kullanıcı eklenmemiş.");
            emptyLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 14px;");
            kullaniciListBox.getChildren().add(emptyLabel);
        } else {
            for (Kullanıcı kullanici : kullanicilar) {
                HBox kullaniciCard = new HBox(15);
                kullaniciCard.setPadding(new Insets(15));
                kullaniciCard.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 5; -fx-border-color: #bdc3c7; -fx-border-radius: 5;");

                Label kullaniciInfo = new Label("ID: " + kullanici.getKullaniciİd() + " | " + kullanici.getAdSoyad());
                kullaniciInfo.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

                HBox.setHgrow(kullaniciInfo, Priority.ALWAYS);
                kullaniciCard.getChildren().add(kullaniciInfo);
                kullaniciListBox.getChildren().add(kullaniciCard);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

