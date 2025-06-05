/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package spk_saw;

/**
 *
 * @author ngato
 */
import javax.swing.SwingUtilities;
import spk_saw.views.DashboardForm;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DashboardForm().setVisible(true);
        });
    }
}
