package rentabilite_check.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="chargesFixes")
public class ChargesFixes {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idCF;
    private String nom;
    private double montant;
    private Date annee;
    @ManyToOne
    private Projet projet;

    public ChargesFixes(String nom, double montant, Date annee) {
        this.nom = nom;
        this.montant = montant;
        this.annee = annee;
    }

    public ChargesFixes() {
    }

    public int getIdCF() {
        return idCF;
    }

    public void setIdCF(int idCF) {
        this.idCF = idCF;
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