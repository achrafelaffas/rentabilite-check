package rentabilite360.entities;
import jakarta.persistence.*;
@Entity
@Table(name = "investissements")
public class Investissement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inv")
    private int idInv;
    @Column(name = "montant_inv")
    private double montantInv;
    @Column(name = "duree_inv")
    private int dureeInv;
    private float VR;

    @ManyToOne
    @JoinColumn(name = "id_projet")
    private Projet projet;
}
