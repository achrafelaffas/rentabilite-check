package rentabilite360.entities;
import jakarta.persistence.*;
import java.sql.Date;
@Entity
@Table(name = "parametres")
public class Parametre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parametre")
    private int idParametre;
    private Date annee;
    @Column(name = "chiffre_affaire")
    private double chiffreAffaire;
    @Column(name = "charges_variable")
    private double chargesVariable;
    @Column(name = "charges_fixe")
    private double chargesFixe;
    @Column(name = "augmentation_bfr")
    private double augmentationBfr;
    @Column(name = "recuperation_bfr")
    private double recuperationBfr;
    @ManyToOne
    @JoinColumn(name = "id_projet")
    private Projet projet;
}
