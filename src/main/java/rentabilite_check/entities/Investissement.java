package rentabilite_check.entities;


import jakarta.persistence.*;

@Entity
@Table(name="investissement")
public class Investissement {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private float montantINV;
    private int dureeINV;
    private float vr;

    @ManyToOne
    private Projet projet;

    public Investissement(float montantINV, int dureeINV, float vr) {
        this.montantINV = montantINV;
        this.dureeINV = dureeINV;
        this.vr = vr;
    }

    public Investissement() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getMontantINV() {
        return montantINV;
    }

    public void setMontantINV(float montantINV) {
        this.montantINV = montantINV;
    }

    public int getDureeINV() {
        return dureeINV;
    }

    public void setDureeINV(int dureeINV) {
        this.dureeINV = dureeINV;
    }

    public float getVr() {
        return vr;
    }

    public void setVr(float vr) {
        this.vr = vr;
    }
}
