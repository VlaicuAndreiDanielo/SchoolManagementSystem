package java_examen;

class Student {
    private int id;
    private String nume;
    private double media;
    private boolean restanta;

    public Student(int id, String nume, double media, boolean restanta) {
        this.id = id;
        this.nume = nume;
        this.media = media;
        this.restanta = restanta;
    }

    public String getNume() {
        return nume;
    }

    public double getMedia() {
        return media;
    }

    public boolean areRestanta() {
        return restanta;
    }
}
