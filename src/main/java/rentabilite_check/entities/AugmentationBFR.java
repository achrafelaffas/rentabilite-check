package rentabilite_check.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="augmentationBFR")
public class AugmentationBFR {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idaUGbFR;
    private String nom;
    private double montant;
    private Date annee;

    @ManyToOne
    private Projet projet;

    public AugmentationBFR(String nom, double montant, Date annee) {
        this.nom = nom;
        this.montant = montant;
        this.annee = annee;
    }

    public AugmentationBFR() {
    }

    public int getIdaUGbFR() {
        return idaUGbFR;
    }

    public void setIdaUGbFR(int idaUGbFR) {
        this.idaUGbFR = idaUGbFR;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Date getAnnee() {
        return annee;
    }

    public void setAnnee(Date annee) {
        this.annee = annee;
    }
}
