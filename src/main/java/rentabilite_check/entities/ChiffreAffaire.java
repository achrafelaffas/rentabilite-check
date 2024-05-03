package rentabilite_check.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="ChiffreAffaire")
public class ChiffreAffaire {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int idChiffreAffaire;
    private double valeur;
    private Date annee;
    private double prixUnitaire;
    private int quantite;

    @ManyToOne
    private Projet projet;

    public ChiffreAffaire(Date annee, double prixUnitaire, int quantite) {
        this.annee = annee;
        this.prixUnitaire = prixUnitaire;
        this.quantite = quantite;
    }

    public ChiffreAffaire() {
    }

    public int getIdChiffreAffaire() {
        return idChiffreAffaire;
    }

    public void setIdChiffreAffaire(int idChiffreAffaire) {
        this.idChiffreAffaire = idChiffreAffaire;
    }

    public double getValeur() {
        return valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }

    public Date getAnnee() {
        return annee;
    }

    public void setAnnee(Date annee) {
        this.annee = annee;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
