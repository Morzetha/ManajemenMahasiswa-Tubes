public class Student {
    private String nim;
    private String nama;
    private String kelas;
    private String angkatan;
    private String jurusan;
    private String gender;


    public Student(String nim, String nama, String kelas, String angkatan, String jurusan, String gender) {
        this.nim = nim;
        this.nama = nama;
        this.kelas = kelas;
        this.angkatan = angkatan;
        this.jurusan = jurusan;
        this.gender = gender;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getAngkatan() {
        return angkatan;
    }

    public void setAngkatan(String angkatan) {
        this.angkatan = angkatan;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
