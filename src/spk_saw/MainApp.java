/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package spk_saw;

/**
 *
 * @author ngato
 */
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatLaf;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.SwingUtilities;
import spk_saw.views.DashboardForm;

public class MainApp {

    public static void main(String[] args) {
        try {
            // Daftarkan folder tempat file theme .properties berada
            FlatLaf.registerCustomDefaultsSource("themes");

            // Gunakan FlatLightLaf dan aktifkan theme dari folder custom
            UIManager.setLookAndFeel(new FlatDarkLaf()); // FlatLightLaf (untuk darkmode)

        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("Gagal menerapkan FlatLaf: " + e.getMessage());
        }

        // Tampilkan Dashboard utama
        SwingUtilities.invokeLater(() -> {
            new DashboardForm().setVisible(true);
        });
    }
}
