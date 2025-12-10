import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class LibraryApp extends JFrame {
    private AdminVeriTabaniOluştur adminDAO = new AdminVeriTabaniOluştur();
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public LibraryApp() {
        setTitle("Kütüphane Yönetim Sistemi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createLoginPanel(), "LOGIN");
        mainPanel.add(createAdminPanel(), "ADMIN");

        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Başlık
        JLabel titleLabel = new JLabel("Kütüphane Yönetim Sistemi");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // Alt başlık
        JLabel subtitleLabel = new JLabel("Giriş Yapın");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(127, 140, 141));
        gbc.gridy = 1;
        panel.add(subtitleLabel, gbc);

        // Kullanıcı adı
        JLabel usernameLabel = new JLabel("Kullanıcı Adı:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setPreferredSize(new Dimension(300, 40));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(usernameField, gbc);

        // Şifre
        JLabel passwordLabel = new JLabel("Şifre:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(300, 40));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(passwordField, gbc);

        // Hata mesajı
        JLabel errorLabel = new JLabel(" ");
        errorLabel.setForeground(new Color(231, 76, 60));
        errorLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(errorLabel, gbc);

        // Giriş butonu
        JButton loginButton = new JButton("Giriş Yap");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(52, 152, 219));
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(300, 45));
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 5;
        panel.add(loginButton, gbc);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Lütfen tüm alanları doldurun!");
                return;
            }

            if (adminDAO.adminGiris(username, password)) {
                errorLabel.setText(" ");
                cardLayout.show(mainPanel, "ADMIN");
            } else {
                errorLabel.setText("Kullanıcı adı veya şifre hatalı!");
            }
        });

        return panel;
    }

    private JPanel createAdminPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Üst menü
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(52, 73, 94));
        topPanel.setPreferredSize(new Dimension(0, 60));
        topPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel welcomeLabel = new JLabel("Admin Paneli");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.WHITE);
        topPanel.add(welcomeLabel, BorderLayout.WEST);

        JButton logoutButton = new JButton("Çıkış Yap");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 14));
        logoutButton.setBackground(new Color(231, 76, 60));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "LOGIN");
        });
        topPanel.add(logoutButton, BorderLayout.EAST);

        // Sol menü
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(44, 62, 80));
        leftPanel.setPreferredSize(new Dimension(200, 0));
        leftPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JButton kitapButton = new JButton("Kitap Yönetimi");
        JButton kullaniciButton = new JButton("Kullanıcı Yönetimi");

        styleMenuButton(kitapButton);
        styleMenuButton(kullaniciButton);

        leftPanel.add(kitapButton);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(kullaniciButton);

        // Merkez içerik
        CardLayout contentLayout = new CardLayout();
        JPanel contentPanel = new JPanel(contentLayout);
        contentPanel.setBackground(new Color(236, 240, 241));

        JPanel kitapPanel = createKitapManagementPanel(contentLayout, contentPanel);
        JPanel kullaniciPanel = createKullaniciManagementPanel();

        contentPanel.add(kitapPanel, "KITAP");
        contentPanel.add(kullaniciPanel, "KULLANICI");

        kitapButton.addActionListener(e -> contentLayout.show(contentPanel, "KITAP"));
        kullaniciButton.addActionListener(e -> contentLayout.show(contentPanel, "KULLANICI"));

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    private void styleMenuButton(JButton button) {
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setPreferredSize(new Dimension(180, 50));
        button.setMaximumSize(new Dimension(180, 50));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(52, 73, 94));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(52, 152, 219));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(52, 73, 94));
            }
        });
    }

    private JPanel createKitapManagementPanel(CardLayout contentLayout, JPanel contentPanel) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));

        // Başlık
        JLabel titleLabel = new JLabel("Kitap Yönetimi");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        mainContent.add(titleLabel);

        // Form paneli
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(236, 240, 241));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel formTitle = new JLabel("Yeni Kitap Ekle");
        formTitle.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(formTitle, gbc);

        // Form alanları
        JTextField kitapIdField = new JTextField(15);
        JTextField kitapAdiField = new JTextField(15);
        JTextField yazarField = new JTextField(15);
        JTextField yayinYiliField = new JTextField(15);
        JTextField sayfaSayisiField = new JTextField(15);
        JTextField stokField = new JTextField(15);
        JTextField rafField = new JTextField(15);
        JTextField teslimTarihiField = new JTextField(15);
        teslimTarihiField.setToolTipText("YYYY-MM-DD formatında girin (örn: 2024-12-31)");

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Kitap ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(kitapIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Kitap Adı:"), gbc);
        gbc.gridx = 1;
        formPanel.add(kitapAdiField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Yazar:"), gbc);
        gbc.gridx = 1;
        formPanel.add(yazarField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Yayın Yılı:"), gbc);
        gbc.gridx = 1;
        formPanel.add(yayinYiliField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Sayfa Sayısı:"), gbc);
        gbc.gridx = 1;
        formPanel.add(sayfaSayisiField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(new JLabel("Stok:"), gbc);
        gbc.gridx = 1;
        formPanel.add(stokField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(new JLabel("Raf Konumu:"), gbc);
        gbc.gridx = 1;
        formPanel.add(rafField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        formPanel.add(new JLabel("Teslim Tarihi (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        formPanel.add(teslimTarihiField, gbc);

        JLabel messageLabel = new JLabel(" ");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        formPanel.add(messageLabel, gbc);

        JButton ekleButton = new JButton("Kitap Ekle");
        ekleButton.setFont(new Font("Arial", Font.PLAIN, 14));
        ekleButton.setBackground(new Color(39, 174, 96));
        ekleButton.setForeground(Color.WHITE);
        ekleButton.setFocusPainted(false);
        ekleButton.setBorderPainted(false);
        ekleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ekleButton.setPreferredSize(new Dimension(150, 35));
        gbc.gridy = 10;
        formPanel.add(ekleButton, gbc);

        // Kitap listesi
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.setColumnIdentifiers(new Object[]{"ID", "Kitap Adı", "Yazar", "Stok", "Durum", "Konum", "İşlemler"});

        JTable kitapTable = new JTable(tableModel);
        kitapTable.setFont(new Font("Arial", Font.PLAIN, 12));
        kitapTable.setRowHeight(25);
        JScrollPane tableScroll = new JScrollPane(kitapTable);
        tableScroll.setPreferredSize(new Dimension(0, 300));

        JLabel listTitle = new JLabel("Kitap Listesi");
        listTitle.setFont(new Font("Arial", Font.BOLD, 18));
        listTitle.setBorder(new EmptyBorder(20, 0, 10, 0));

        // Ekle butonu işlevi
        ekleButton.addActionListener(e -> {
            try {
                int kitapId = Integer.parseInt(kitapIdField.getText());
                String kitapAdi = kitapAdiField.getText();
                String yazar = yazarField.getText();
                int yayinYili = Integer.parseInt(yayinYiliField.getText());
                int sayfaSayisi = Integer.parseInt(sayfaSayisiField.getText());
                int stok = Integer.parseInt(stokField.getText());
                String raf = rafField.getText();
                LocalDate teslimTarihi = LocalDate.parse(teslimTarihiField.getText());

                if (kitapAdi.isEmpty() || yazar.isEmpty() || raf.isEmpty()) {
                    messageLabel.setText("Lütfen tüm alanları doldurun!");
                    messageLabel.setForeground(new Color(231, 76, 60));
                    return;
                }

                Kitap yeniKitap = new Kitap(kitapAdi, raf, stok, kitapId, yazar, yayinYili, sayfaSayisi, teslimTarihi);
                KitapVeriTabaniOluştur kitapDAO = new KitapVeriTabaniOluştur();
                kitapDAO.kitapEkle(yeniKitap);

                messageLabel.setText("Kitap başarıyla eklendi!");
                messageLabel.setForeground(new Color(39, 174, 96));

                // Formu temizle
                kitapIdField.setText("");
                kitapAdiField.setText("");
                yazarField.setText("");
                yayinYiliField.setText("");
                sayfaSayisiField.setText("");
                stokField.setText("");
                rafField.setText("");
                teslimTarihiField.setText("");

                // Listeyi yenile
                refreshKitapListesi(tableModel, kitapTable);

            } catch (NumberFormatException ex) {
                messageLabel.setText("Lütfen sayısal alanları doğru formatta girin!");
                messageLabel.setForeground(new Color(231, 76, 60));
            } catch (Exception ex) {
                messageLabel.setText("Hata: " + ex.getMessage());
                messageLabel.setForeground(new Color(231, 76, 60));
            }
        });

        // Listeyi yenile butonu
        JButton refreshButton = new JButton("Listeyi Yenile");
        refreshButton.setFont(new Font("Arial", Font.PLAIN, 12));
        refreshButton.setBackground(new Color(52, 152, 219));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshButton.addActionListener(e -> refreshKitapListesi(tableModel, kitapTable));

        mainContent.add(formPanel);
        mainContent.add(Box.createVerticalStrut(20));
        mainContent.add(listTitle);
        mainContent.add(refreshButton);
        mainContent.add(tableScroll);

        refreshKitapListesi(tableModel, kitapTable);

        panel.add(mainContent, BorderLayout.CENTER);
        return panel;
    }

    private void refreshKitapListesi(DefaultTableModel tableModel, JTable kitapTable) {
        tableModel.setRowCount(0);
        KitapVeriTabaniOluştur kitapDAO = new KitapVeriTabaniOluştur();
        OduncVeriTabaniOluştur oduncDAO = new OduncVeriTabaniOluştur();
        List<Kitap> kitaplar = kitapDAO.tumKitaplar();

        for (Kitap kitap : kitaplar) {
            int kitapId = kitap.getKitapİd();
            boolean verilmisMi = oduncDAO.kitapVerilmisMi(kitapId);
            String durum = verilmisMi ? "Ödünç Verildi" : "Rafta";
            String konum = oduncDAO.kitapKonumu(kitapId);
            
            // Buton paneli
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            buttonPanel.setBackground(Color.WHITE);
            
            JButton oduncVerButton = new JButton("Ödünç Ver");
            oduncVerButton.setFont(new Font("Arial", Font.PLAIN, 10));
            oduncVerButton.setBackground(new Color(52, 152, 219));
            oduncVerButton.setForeground(Color.WHITE);
            oduncVerButton.setFocusPainted(false);
            oduncVerButton.setBorderPainted(false);
            oduncVerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            oduncVerButton.setEnabled(!verilmisMi && kitap.getStokSayisi() > 0);
            
            JButton silButton = new JButton("Sil");
            silButton.setFont(new Font("Arial", Font.PLAIN, 10));
            silButton.setBackground(new Color(231, 76, 60));
            silButton.setForeground(Color.WHITE);
            silButton.setFocusPainted(false);
            silButton.setBorderPainted(false);
            silButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            oduncVerButton.addActionListener(e -> {
                showOduncVerDialog(kitapId, kitap.getKitapAdi());
                refreshKitapListesi(tableModel, kitapTable);
            });
            
            silButton.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bu kitabı silmek istediğinizden emin misiniz?",
                    "Onay",
                    JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    kitapDAO.kitapSil(kitapId);
                    refreshKitapListesi(tableModel, kitapTable);
                }
            });
            
            buttonPanel.add(oduncVerButton);
            buttonPanel.add(silButton);

            Object[] row = {
                kitap.getKitapİd(),
                kitap.getKitapAdi(),
                kitap.getYazarAdi(),
                kitap.getStokSayisi(),
                durum,
                konum,
                buttonPanel
            };
            tableModel.addRow(row);
        }

        // Buton panelini tabloya eklemek için renderer
        kitapTable.getColumn("İşlemler").setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                return (Component) value;
            }
        });

        kitapTable.getColumn("İşlemler").setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                return (Component) value;
            }
        });
    }
    
    private void showOduncVerDialog(int kitapId, String kitapAdi) {
        JDialog dialog = new JDialog(this, "Kitap Ödünç Ver", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JLabel kitapLabel = new JLabel("Kitap: " + kitapAdi);
        kitapLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(kitapLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(new JLabel("Kullanıcı ID:"), gbc);
        
        JTextField kullaniciIdField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(kullaniciIdField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Teslim Tarihi (YYYY-MM-DD):"), gbc);
        
        JTextField teslimTarihiField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(teslimTarihiField, gbc);
        
        JLabel errorLabel = new JLabel(" ");
        errorLabel.setForeground(new Color(231, 76, 60));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(errorLabel, gbc);
        
        JButton verButton = new JButton("Ödünç Ver");
        verButton.setBackground(new Color(39, 174, 96));
        verButton.setForeground(Color.WHITE);
        verButton.setFocusPainted(false);
        verButton.setBorderPainted(false);
        verButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        verButton.addActionListener(e -> {
            try {
                int kullaniciId = Integer.parseInt(kullaniciIdField.getText());
                LocalDate teslimTarihi = LocalDate.parse(teslimTarihiField.getText());
                
                OduncVeriTabaniOluştur oduncDAO = new OduncVeriTabaniOluştur();
                oduncDAO.kitapVer(kullaniciId, kitapId, teslimTarihi);
                
                JOptionPane.showMessageDialog(dialog, "Kitap başarıyla ödünç verildi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
                
            } catch (NumberFormatException ex) {
                errorLabel.setText("Lütfen geçerli bir kullanıcı ID girin!");
            } catch (Exception ex) {
                errorLabel.setText("Hata: " + ex.getMessage());
            }
        });
        
        gbc.gridy = 4;
        panel.add(verButton, gbc);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }

    private JPanel createKullaniciManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));

        // Başlık
        JLabel titleLabel = new JLabel("Kullanıcı Yönetimi");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        mainContent.add(titleLabel);

        // Form paneli
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(236, 240, 241));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel formTitle = new JLabel("Yeni Kullanıcı Ekle");
        formTitle.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(formTitle, gbc);

        JTextField kullaniciIdField = new JTextField(15);
        JTextField adSoyadField = new JTextField(15);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Kullanıcı ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(kullaniciIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Ad Soyad:"), gbc);
        gbc.gridx = 1;
        formPanel.add(adSoyadField, gbc);

        JLabel messageLabel = new JLabel(" ");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(messageLabel, gbc);

        JButton ekleButton = new JButton("Kullanıcı Ekle");
        ekleButton.setFont(new Font("Arial", Font.PLAIN, 14));
        ekleButton.setBackground(new Color(39, 174, 96));
        ekleButton.setForeground(Color.WHITE);
        ekleButton.setFocusPainted(false);
        ekleButton.setBorderPainted(false);
        ekleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ekleButton.setPreferredSize(new Dimension(150, 35));
        gbc.gridy = 4;
        formPanel.add(ekleButton, gbc);

        // Kullanıcı listesi
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.setColumnIdentifiers(new Object[]{"ID", "Ad Soyad", "İşlemler"});

        JTable kullaniciTable = new JTable(tableModel);
        kullaniciTable.setFont(new Font("Arial", Font.PLAIN, 12));
        kullaniciTable.setRowHeight(25);
        
        // Buton renderer ve editor
        kullaniciTable.getColumn("İşlemler").setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                return (Component) value;
            }
        });

        kullaniciTable.getColumn("İşlemler").setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                return (Component) value;
            }
        });
        
        JScrollPane tableScroll = new JScrollPane(kullaniciTable);
        tableScroll.setPreferredSize(new Dimension(0, 300));

        JLabel listTitle = new JLabel("Kullanıcı Listesi");
        listTitle.setFont(new Font("Arial", Font.BOLD, 18));
        listTitle.setBorder(new EmptyBorder(20, 0, 10, 0));

        JButton refreshButton = new JButton("Listeyi Yenile");
        refreshButton.setFont(new Font("Arial", Font.PLAIN, 12));
        refreshButton.setBackground(new Color(52, 152, 219));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshButton.addActionListener(e -> refreshKullaniciListesi(tableModel, kullaniciTable));

        ekleButton.addActionListener(e -> {
            try {
                int kullaniciId = Integer.parseInt(kullaniciIdField.getText());
                String adSoyad = adSoyadField.getText();

                if (adSoyad.isEmpty()) {
                    messageLabel.setText("Lütfen ad soyad girin!");
                    messageLabel.setForeground(new Color(231, 76, 60));
                    return;
                }

                Kullanıcı yeniKullanici = new Kullanıcı(adSoyad, kullaniciId);
                KullanıcıVeriTabaniOluştur kullaniciDAO = new KullanıcıVeriTabaniOluştur();
                kullaniciDAO.kullaniciEkle(yeniKullanici);

                messageLabel.setText("Kullanıcı başarıyla eklendi!");
                messageLabel.setForeground(new Color(39, 174, 96));

                kullaniciIdField.setText("");
                adSoyadField.setText("");

                refreshKullaniciListesi(tableModel, kullaniciTable);

            } catch (NumberFormatException ex) {
                messageLabel.setText("Lütfen kullanıcı ID'sini doğru formatta girin!");
                messageLabel.setForeground(new Color(231, 76, 60));
            } catch (Exception ex) {
                messageLabel.setText("Hata: " + ex.getMessage());
                messageLabel.setForeground(new Color(231, 76, 60));
            }
        });

        mainContent.add(formPanel);
        mainContent.add(Box.createVerticalStrut(20));
        mainContent.add(listTitle);
        mainContent.add(refreshButton);
        mainContent.add(tableScroll);

        refreshKullaniciListesi(tableModel, kullaniciTable);

        panel.add(mainContent, BorderLayout.CENTER);
        return panel;
    }

    private void refreshKullaniciListesi(DefaultTableModel tableModel, JTable kullaniciTable) {
        tableModel.setRowCount(0);
        KullanıcıVeriTabaniOluştur kullaniciDAO = new KullanıcıVeriTabaniOluştur();
        List<Kullanıcı> kullanicilar = kullaniciDAO.tumKullanicilar();

        for (Kullanıcı kullanici : kullanicilar) {
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            buttonPanel.setBackground(Color.WHITE);
            
            JButton kitaplariGosterButton = new JButton("Kitapları Göster");
            kitaplariGosterButton.setFont(new Font("Arial", Font.PLAIN, 10));
            kitaplariGosterButton.setBackground(new Color(52, 152, 219));
            kitaplariGosterButton.setForeground(Color.WHITE);
            kitaplariGosterButton.setFocusPainted(false);
            kitaplariGosterButton.setBorderPainted(false);
            kitaplariGosterButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            JButton silButton = new JButton("Sil");
            silButton.setFont(new Font("Arial", Font.PLAIN, 10));
            silButton.setBackground(new Color(231, 76, 60));
            silButton.setForeground(Color.WHITE);
            silButton.setFocusPainted(false);
            silButton.setBorderPainted(false);
            silButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            int kullaniciId = kullanici.getKullaniciİd();
            
            kitaplariGosterButton.addActionListener(e -> {
                showKullaniciKitaplariDialog(kullaniciId, kullanici.getAdSoyad());
            });
            
            silButton.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bu kullanıcıyı silmek istediğinizden emin misiniz?",
                    "Onay",
                    JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    kullaniciDAO.kullaniciSil(kullaniciId);
                    refreshKullaniciListesi(tableModel, kullaniciTable);
                }
            });
            
            buttonPanel.add(kitaplariGosterButton);
            buttonPanel.add(silButton);
            
            Object[] row = {
                kullanici.getKullaniciİd(),
                kullanici.getAdSoyad(),
                buttonPanel
            };
            tableModel.addRow(row);
        }
    }
    
    private void showKullaniciKitaplariDialog(int kullaniciId, String kullaniciAdi) {
        JDialog dialog = new JDialog(this, kullaniciAdi + " - Ödünç Alınan Kitaplar", true);
        dialog.setSize(800, 500);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel(kullaniciAdi + " - Ödünç Alınan Kitaplar");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.setColumnIdentifiers(new Object[]{"Kitap Adı", "Yazar", "Alış Tarihi", "Teslim Tarihi", "Durum", "İşlem"});
        
        JTable kitaplarTable = new JTable(tableModel);
        kitaplarTable.setFont(new Font("Arial", Font.PLAIN, 12));
        kitaplarTable.setRowHeight(25);
        JScrollPane tableScroll = new JScrollPane(kitaplarTable);
        
        OduncVeriTabaniOluştur oduncDAO = new OduncVeriTabaniOluştur();
        List<OduncBilgi> kitaplar = oduncDAO.kullaniciKitaplari(kullaniciId);
        
        for (OduncBilgi odunc : kitaplar) {
            JButton teslimButton = new JButton("Teslim Al");
            teslimButton.setFont(new Font("Arial", Font.PLAIN, 10));
            teslimButton.setBackground(new Color(39, 174, 96));
            teslimButton.setForeground(Color.WHITE);
            teslimButton.setFocusPainted(false);
            teslimButton.setBorderPainted(false);
            teslimButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            int oduncId = odunc.getOduncId();
            teslimButton.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(
                    dialog,
                    "Kitabı teslim almak istediğinizden emin misiniz?",
                    "Onay",
                    JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    oduncDAO.kitapTeslimAl(oduncId);
                    JOptionPane.showMessageDialog(dialog, "Kitap başarıyla teslim alındı!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Listeyi yenile
                    tableModel.setRowCount(0);
                    List<OduncBilgi> yeniKitaplar = oduncDAO.kullaniciKitaplari(kullaniciId);
                    for (OduncBilgi yeniOdunc : yeniKitaplar) {
                        JButton yeniTeslimButton = new JButton("Teslim Al");
                        yeniTeslimButton.setFont(new Font("Arial", Font.PLAIN, 10));
                        yeniTeslimButton.setBackground(new Color(39, 174, 96));
                        yeniTeslimButton.setForeground(Color.WHITE);
                        yeniTeslimButton.setFocusPainted(false);
                        yeniTeslimButton.setBorderPainted(false);
                        yeniTeslimButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        
                        int yeniOduncId = yeniOdunc.getOduncId();
                        yeniTeslimButton.addActionListener(ev -> {
                            int confirm2 = JOptionPane.showConfirmDialog(
                                dialog,
                                "Kitabı teslim almak istediğinizden emin misiniz?",
                                "Onay",
                                JOptionPane.YES_NO_OPTION
                            );
                            if (confirm2 == JOptionPane.YES_OPTION) {
                                oduncDAO.kitapTeslimAl(yeniOduncId);
                                JOptionPane.showMessageDialog(dialog, "Kitap başarıyla teslim alındı!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                                dialog.dispose();
                            }
                        });
                        
                        tableModel.addRow(new Object[]{
                            yeniOdunc.getKitapAdi(),
                            yeniOdunc.getYazarAdi(),
                            yeniOdunc.getAlisTarihi(),
                            yeniOdunc.getTeslimTarihi(),
                            yeniOdunc.getDurum(),
                            yeniTeslimButton
                        });
                    }
                }
            });
            
            tableModel.addRow(new Object[]{
                odunc.getKitapAdi(),
                odunc.getYazarAdi(),
                odunc.getAlisTarihi(),
                odunc.getTeslimTarihi(),
                odunc.getDurum(),
                teslimButton
            });
        }
        
        kitaplarTable.getColumn("İşlem").setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                return (Component) value;
            }
        });

        kitaplarTable.getColumn("İşlem").setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                return (Component) value;
            }
        });
        
        panel.add(tableScroll, BorderLayout.CENTER);
        dialog.add(panel);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            LibraryApp app = new LibraryApp();
            app.setVisible(true);
        });
    }
}
