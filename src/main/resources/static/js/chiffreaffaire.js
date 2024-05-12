
function selectProjet(select) {
    var projetId = select.value;

    if(projetId != 0) {
        axios.get(`/chiffresaffaires?idProjet=${projetId}`)
            .then(res => {
                tbodyFormTable(res)
            })
            .catch(err => console.error(err));
    }
    else{
        reloadTbody()
    }
}



function AjouterChiffreAffaire() {
    event.preventDefault();
    var projetId = document.getElementById('projet').value;
    var prixUnitaire = document.getElementById('prix').value;
    var quantite = document.getElementById('quantite').value;
    var annee = document.getElementById('anneeca').value;
    var valeur = document.getElementById('valeur').value;

    var chiffreAffaire = {
        prixUnitaire: prixUnitaire,
        quantite: quantite,
        annee: annee,
        valeur: valeur
    };
    axios.post('/ajouter-CHIAFF?projetId=' + projetId, chiffreAffaire)
        .then(response => {
            document.getElementById('AjouetChiffreaffaire').reset();
            document.querySelector('#ajouterChiffreAffaireModal .close').click();
            reloadTbody();
        })
        .catch(error => {
            console.error('Une erreur s\'est produite:', error);
        });
}



function updateCHIAFF(idCHAFF,idProjet) {
    axios.get(`get-chiffreAFF-a-modifier?idChiffreAffaire=${idCHAFF}`)
        .then(res => {
            const chiffreaffaire = res.data;

            document.getElementById("Updateidprojet").value = idProjet;
            document.getElementById("idchaff").value = idCHAFF;
            document.getElementById("updateanneeca").value = chiffreaffaire.annee.split("T")[0];
            document.getElementById("updateprix").value =chiffreaffaire.prixUnitaire ;
            document.getElementById("updatequantite").value = chiffreaffaire.quantite;
            document.getElementById("updatemontant").value = chiffreaffaire.valeur;
            $('#updateChiffreAffaireeModal').modal('show');
        })
        .catch(err => console.error(err));
}


function sendCHAFFUpdate() {
    document.getElementById("UpdateCHAFFForm").addEventListener('submit', async function (event) {
        event.preventDefault();
        const formData = new FormData(this);
        const idProjet = formData.get('projet');
        const jsonData = {};
        formData.forEach((value, key) => {
            jsonData[key] = value;
        });

        jsonData["projet"] = await axios.get(`get-projet-a-modifier?idProjet=${idProjet}`).then(
            res => {
                return res.data;
            }
        );

        axios.post('update-CHAFF', jsonData).then(
            res => {
                document.getElementById("UpdateCHAFFForm").reset();
                document.querySelector('#updateChiffreAffaireeModal .close').click();
                reloadTbody();
            }
        ).catch(error => {
            console.error(error);
        });


    });


}


function reloadTbody() {
    axios.get(`get-all-chiffreaffaires`)
        .then(res => {
            tbodyFormTable(res)
        })
        .catch(err => console.error(err));
}

function tbodyFormTable(res){
    const CHIAFF = res.data;
    const tbodyElement = document.getElementById("tbodyChiffraAffair");
    tbodyElement.innerHTML = ''; // Effacer le contenu existant du tbody

    CHIAFF.forEach(cf => {
        const tr = document.createElement("tr");//<tr></tr>

        const idCell = document.createElement("th");//<th>7</th>
        idCell.textContent =cf.idChiffreAffaire;
        tr.appendChild(idCell);

        const anneeCell = document.createElement("td");
        anneeCell.textContent = cf.annee;
        tr.appendChild(anneeCell);

        const prixCell = document.createElement("td");
        prixCell.textContent = cf.prixUnitaire;
        tr.appendChild(prixCell);

        const quantiteCell = document.createElement("td");
        quantiteCell.textContent = cf.quantite;
        tr.appendChild(quantiteCell)

        const valeurCell = document.createElement("td");
        valeurCell.textContent = cf.valeur;
        tr.appendChild(valeurCell);

        const updateCell = document.createElement("td");
        updateCell.className="h4"
        const updateLink = document.createElement("a");
        updateLink.href = "#";
        updateLink.onclick = function () {
            updateCHIAFF(cf.idChiffreAffaire, cf.projet.idProjet);
        };
        updateLink.innerHTML = '<em class="icon ni ni-pen-alt-fill"></em>';
        updateCell.appendChild(updateLink);
        tr.appendChild(updateCell);

        const deleteCell = document.createElement("td");
        deleteCell.className="h4"
        const deleteLink = document.createElement("a");
        deleteLink.href = "#";
        deleteLink.onclick = function() {
            supprimerChiffreAFF(cf.idChiffreAffaire);
        };
        deleteLink.innerHTML = '<em class="icon ni ni-trash-fill"></em>';
        deleteCell.appendChild(deleteLink);
        tr.appendChild(deleteCell);

        tbodyElement.appendChild(tr);
    });
}

function supprimerChiffreAFF(idCHAFF){
    event.preventDefault();
    axios.get('/deleteCHIAFF?idChiffreAffaire='+ idCHAFF).then(
        res => {
            reloadTbody();
        }
    ).catch(error => {
        console.error(error);
    });

}


