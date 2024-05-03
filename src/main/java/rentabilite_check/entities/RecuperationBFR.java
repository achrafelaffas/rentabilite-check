package rentabilite_check.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="recuperationBFR")
public class RecuperationBFR {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idRecBFR;
    private String nom;
    private double montant;
    private Date annee;

    @ManyToOne
    private Projet projet;

    public RecuperationBFR(String nom, double montant, Date annee) {
        this.nom = nom;
        this.montant = montant;
        this.annee = annee;
    }

    public RecuperationBFR() {
    }

    public int getIdRecBFR() {
        return idRecBFR;
    }

    public void setIdRecBFR(int idRecBFR) {
        this.idRecBFR = idRecBFR;
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
