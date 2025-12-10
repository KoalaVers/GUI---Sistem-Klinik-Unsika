package View;

import database.Koneksi;
import model.Dokter;
import model.Pasien;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class KlinikApp extends JFrame {

    private JTextField tfNama, tfUmur, tfKeluhan;
    private JComboBox<Dokter> cbDokter;
    private JComboBox<String> cbHari;
    private DefaultTableModel modelTabel;
    private JTable table;

    private ArrayList<Dokter> listDokter = new ArrayList<>();

    public KlinikApp() {

        // ===== FRAME =====
        setTitle("Sistem Klinik UNSIKA");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(240, 250, 255));

        // ===== HEADER =====
        JPanel header = new JPanel();
        header.setBackground(new Color(0, 150, 199));
        header.setBorder(new EmptyBorder(15, 0, 15, 0));

        JLabel lblHeader = new JLabel("SISTEM KLINIK UNSIKA");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblHeader.setForeground(Color.WHITE);

        header.add(lblHeader);
        add(header, BorderLayout.NORTH);

        // ===== PANEL KIRI (FORM) =====
        JPanel panelKiri = new JPanel(new BorderLayout());
        panelKiri.setPreferredSize(new Dimension(330, 0));
        panelKiri.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelKiri.setOpaque(false);

        JPanel form = new JPanel(new GridLayout(6, 2, 8, 12));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));

        form.add(new JLabel("Nama Pasien:"));
        tfNama = new JTextField();
        form.add(tfNama);

        form.add(new JLabel("Umur:"));
        tfUmur = new JTextField();
        form.add(tfUmur);

        form.add(new JLabel("Keluhan:"));
        tfKeluhan = new JTextField();
        form.add(tfKeluhan);

        form.add(new JLabel("Pilih Dokter:"));
        cbDokter = new JComboBox<>();
        form.add(cbDokter);

        form.add(new JLabel("Hari Kunjungan:"));
        cbHari = new JComboBox<>(new String[]{"Senin", "Selasa", "Rabu", "Kamis", "Jumat"});
        form.add(cbHari);

        panelKiri.add(form, BorderLayout.CENTER);

        // ===== PANEL TOMBOL =====
        JPanel panelButton = new JPanel(new GridLayout(1, 4, 10, 10));
        panelButton.setBorder(new EmptyBorder(15, 5, 0, 5));
        panelButton.setOpaque(false);

        JButton btnTambah = createButton("Tambah Pasien", new Color(56, 182, 83));
        JButton btnEdit   = createButton("Edit Pasien",   new Color(0, 132, 255));
        JButton btnHapus  = createButton("Hapus Pasien",  new Color(220, 53, 69));
        JButton btnClear  = createButton("Clear Form",    new Color(108, 117, 125));

        panelButton.add(btnTambah);
        panelButton.add(btnEdit);
        panelButton.add(btnHapus);
        panelButton.add(btnClear);

        panelKiri.add(panelButton, BorderLayout.SOUTH);
        add(panelKiri, BorderLayout.WEST);

        // ===== TABEL PASIEN =====
        modelTabel = new DefaultTableModel(new String[]{"Nama", "Umur", "Keluhan", "Dokter", "Hari"}, 0);
        table = new JTable(modelTabel);

        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(28);

        styleTable(table);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        add(scroll, BorderLayout.CENTER);

        // Load DB
        loadDokterDariDB();
        loadPasienDariDB();

        // Event listeners
        btnTambah.addActionListener(e -> tambahPasien());
        btnEdit.addActionListener(e -> editPasien());
        btnHapus.addActionListener(e -> hapusPasien());
        btnClear.addActionListener(e -> clearForm());

        // Klik tabel → form terisi
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    tfNama.setText(modelTabel.getValueAt(row, 0).toString());
                    tfUmur.setText(modelTabel.getValueAt(row, 1).toString());
                    tfKeluhan.setText(modelTabel.getValueAt(row, 2).toString());
                    cbHari.setSelectedItem(modelTabel.getValueAt(row, 4).toString());
                }
            }
        });
    }

    // ===== BUTTON STYLE =====
    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        return btn;
    }

    // ===== TABLE STYLE =====
    private void styleTable(JTable table) {

        table.getTableHeader().setBackground(new Color(0, 160, 216));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));

        // Zebra stripe
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(
                    JTable t, Object val, boolean isSel, boolean hasFocus, int row, int col) {

                Component c = super.getTableCellRendererComponent(t, val, isSel, hasFocus, row, col);

                if (!isSel) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(235, 245, 255));
                }
                return c;
            }
        });
    }

    // ===== LOAD DOKTER =====
    private void loadDokterDariDB() {
        try (Connection conn = Koneksi.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM dokter");
            while (rs.next()) {
                Dokter d = new Dokter(
                        rs.getInt("id_dokter"),
                        rs.getString("nama"),
                        rs.getString("spesialis"),
                        rs.getString("hari_praktek").split(",")
                );
                listDokter.add(d);
                cbDokter.addItem(d);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal load dokter: " + e.getMessage());
        }
    }

    // ===== LOAD PASIEN =====
    private void loadPasienDariDB() {
        modelTabel.setRowCount(0);

        try (Connection conn = Koneksi.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM pasien");
            while (rs.next()) {
                modelTabel.addRow(new Object[]{
                        rs.getString("nama"),
                        rs.getInt("umur"),
                        rs.getString("keluhan"),
                        rs.getString("dokter_tujuan"),
                        rs.getString("hari_kunjungan")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal load pasien: " + e.getMessage());
        }
    }

    // ===== TAMBAH PASIEN =====
    private void tambahPasien() {
        try (Connection conn = Koneksi.getConnection()) {

            String sql = "INSERT INTO pasien (nama, umur, keluhan, dokter_tujuan, hari_kunjungan) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, tfNama.getText());
            ps.setInt(2, Integer.parseInt(tfUmur.getText()));
            ps.setString(3, tfKeluhan.getText());
            ps.setString(4, ((Dokter) cbDokter.getSelectedItem()).getNama());
            ps.setString(5, (String) cbHari.getSelectedItem());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Pasien berhasil ditambahkan!");
            loadPasienDariDB();
            clearForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menambah pasien: " + e.getMessage());
        }
    }

    // ===== EDIT =====
    private void editPasien() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih pasien dahulu!");
            return;
        }

        String namaLama = modelTabel.getValueAt(row, 0).toString();

        try (Connection conn = Koneksi.getConnection()) {

            String sql = "UPDATE pasien SET nama=?, umur=?, keluhan=?, dokter_tujuan=?, hari_kunjungan=? WHERE nama=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, tfNama.getText());
            ps.setInt(2, Integer.parseInt(tfUmur.getText()));
            ps.setString(3, tfKeluhan.getText());
            ps.setString(4, ((Dokter) cbDokter.getSelectedItem()).getNama());
            ps.setString(5, (String) cbHari.getSelectedItem());
            ps.setString(6, namaLama);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data pasien berhasil diubah!");
            loadPasienDariDB();
            clearForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal update: " + e.getMessage());
        }
    }

    // ===== HAPUS =====
    private void hapusPasien() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih pasien dahulu!");
            return;
        }

        String nama = modelTabel.getValueAt(row, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Hapus pasien " + nama + "?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        try (Connection conn = Koneksi.getConnection()) {

            String sql = "DELETE FROM pasien WHERE nama=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Pasien berhasil dihapus!");
            loadPasienDariDB();
            clearForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal hapus: " + e.getMessage());
        }
    }

    // ===== CLEAR =====
    private void clearForm() {
        tfNama.setText("");
        tfUmur.setText("");
        tfKeluhan.setText("");
        cbDokter.setSelectedIndex(0);
        cbHari.setSelectedIndex(0);
        table.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new KlinikApp().setVisible(true));
    }
}
