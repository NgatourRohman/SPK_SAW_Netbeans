/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spk_saw.models;

/**
 *
 * @author ngato
 */
public class Nilai {

    private int siswaId;
    private int kriteriaId;
    private double nilai;

    public Nilai(int siswaId, int kriteriaId, double nilai) {
        this.siswaId = siswaId;
        this.kriteriaId = kriteriaId;
        this.nilai = nilai;
    }

    public int getSiswaId() {
        return siswaId;
    }

    public int getKriteriaId() {
        return kriteriaId;
    }

    public double getNilai() {
        return nilai;
    }
}
