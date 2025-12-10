package model;

public class Dokter {
    private int id;
    private String nama;
    private String spesialis;
    private String[] hariPraktek;

    public Dokter(int id, String nama, String spesialis, String[] hariPraktek) {
        this.id = id;
        this.nama = nama;
        this.spesialis = spesialis;
        this.hariPraktek = hariPraktek;
    }

    public int getId() { return id; }
    public String getNama() { return nama; }
    public String getSpesialis() { return spesialis; }
    public String[] getHariPraktek() { return hariPraktek; }

    @Override
    public String toString() {
        return nama + " (" + spesialis + ")";
    }
}
