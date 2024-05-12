package rentabilite_check.entities;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name="ChargesVariables")
public class ChargesVariables {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idCV;
    private String nom;
    private double montant;
    private Date annee;

    @ManyToOne
    private Projet projet;

    public ChargesVariables(String nom, double montant, Date annee) {
        this.nom = nom;
        this.montant = montant;
        this.annee = annee;
    }

    public ChargesVariables() {
    }

    public int getIdCV() {
        return idCV;
    }

    public void setIdCV(int idCV) {
        this.idCV = idCV;
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

    public java.sql.Date getAnnee() {
        return annee;
    }

    public void setAnnee(Date annee) {
        this.annee = annee;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }
}
