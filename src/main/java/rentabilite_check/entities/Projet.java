package rentabilite_check.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="projet")
public class Projet {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int idProjet;

    private String nom;
    private String description;
    private float tauxActualisation;
    private float impotSociete;
    private int duree;
    private float van;
    private float ip;
    private float drci;

    @OneToMany(cascade=CascadeType.ALL,mappedBy="projet")
    private List<ChiffreAffaire> chiffres;

    @OneToMany(cascade=CascadeType.ALL,mappedBy="projet")
    private List<ChargesVariables> chargesvariables;

    @OneToMany(cascade=CascadeType.ALL,mappedBy="projet")
    private List<ChargesFixes> chargesfixes;

    @OneToMany(cascade=CascadeType.ALL,mappedBy="projet")
    private List<RecuperationBFR> recBFRs;

    @OneToMany(cascade=CascadeType.ALL,mappedBy="projet")
    private List<AugmentationBFR> augBFRs;

    @ManyToOne
    private User user;

    @OneToMany(cascade=CascadeType.ALL,mappedBy="projet")
    private List<Investissement> invesstissements;
    public Projet(String nom, int duree) {
        this.nom = nom;
        this.duree = duree;
    }

    public Projet() {
    }

    public int getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(int idProjet) {
        this.idProjet = idProjet;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getTauxActualisation() {
        return tauxActualisation;
    }

    public void setTauxActualisation(float tauxActualisation) {
        this.tauxActualisation = tauxActualisation;
    }

    public float getImpotSociete() {
        return impotSociete;
    }

    public void setImpotSociete(float impotSociete) {
        this.impotSociete = impotSociete;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public float getVan() {
        return van;
    }

    public void setVan(float van) {
        this.van = van;
    }

    public float getIp() {
        return ip;
    }

    public void setIp(float ip) {
        this.ip = ip;
    }

    public float getDrci() {
        return drci;
    }

    public void setDrci(float drci) {
        this.drci = drci;
    }

    public List<Investissement> getInvesstissements() {
        return invesstissements;
    }

    public void setInvesstissements(List<Investissement> invesstissements) {
        this.invesstissements = invesstissements;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<AugmentationBFR> getAugBFRs() {
        return augBFRs;
    }

    public void setAugBFRs(List<AugmentationBFR> augBFRs) {
        this.augBFRs = augBFRs;
    }

    public List<RecuperationBFR> getRecBFRs() {
        return recBFRs;
    }

    public void setRecBFRs(List<RecuperationBFR> recBFRs) {
        this.recBFRs = recBFRs;
    }

    public List<ChargesFixes> getChargesfixes() {
        return chargesfixes;
    }

    public void setChargesfixes(List<ChargesFixes> chargesfixes) {
        this.chargesfixes = chargesfixes;
    }

    public List<ChargesVariables> getChargesvariables() {
        return chargesvariables;
    }

    public void setChargesvariables(List<ChargesVariables> chargesvariables) {
        this.chargesvariables = chargesvariables;
    }

    public List<ChiffreAffaire> getChiffres() {
        return chiffres;
    }

    public void setChiffres(List<ChiffreAffaire> chiffres) {
        this.chiffres = chiffres;
    }

    public float calculerVAN() {
        // TODO: implement
        return 0;
    }

    public float calculerIP() {
        // TODO: implement
        return 0;
    }

    public float calculerDRCI() {
        // TODO: implement
        return 0;
    }

    public float calculerTotalCAF() {
        // TODO: implement
        return 0;
    }
}
