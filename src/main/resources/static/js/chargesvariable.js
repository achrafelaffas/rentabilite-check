function selectProjet(select) {
    var projetId = select.value;

    if (projetId != 0) {
        axios.get(`/cvariable?idProjet=${projetId}`)
            .then(res => {
                tbodyFormTable(res);
            })
            .catch(err => console.error(err));
    } else {
        reloadTbody();
    }
}

function AjouterChargeVariable() {
    event.preventDefault();
    var projetId = document.getElementById('idprojet').value;
    var nom = document.getElementById('nom').value;
    var montant = document.getElementById('montant').value;
    var annee = document.getElementById('annee').value;

    var chargeVariable = {
        nom: nom,
        montant: montant,
        annee: annee
    };
    axios.post('/ajouter-CHV?projetId=' + projetId, chargeVariable)
        .then(response => {
            document.getElementById('AjouetChargeVariable').reset();
            document.querySelector('#ajouterCHVModal .close').click();
            reloadTbody();
        })
        .catch(error => {
            console.error('Une erreur s\'est produite:', error);
        });
}

function updateCV(idCV, idProjet) {
    axios.get(`get-chargevariable-a-modifier?idCV=${idCV}`)
        .then(res => {
            const chargeVar = res.data;

            document.getElementById("Updateidprojet").value = idProjet;
            document.getElementById("idCV").value = idCV;
            document.getElementById("updatenom").value = chargeVar.nom;
            document.getElementById("updatemontant").value = chargeVar.montant;
            document.getElementById("updateannee").value = chargeVar.annee.split("T")[0];
            $('#updateChargeVariableModal').modal('show');
        })
        .catch(err => console.error(err));
}

function sendCVUpdate() {
    document.getElementById("UpdateCHVForm").addEventListener('submit', async function(event) {
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
        console.log(jsonData);

        axios.post('update-CV', jsonData).then(
            res => {
                document.getElementById("UpdateCHVForm").reset();
                document.querySelector('#updateChargeVariableModal .close').click();
                reloadTbody();
            }
        ).catch(error => {
            console.error(error);
        });
    });
}

function reloadTbody() {
    axios.get(`get-all-chargevariables`)
        .then(res => {
            tbodyFormTable(res);
        })
        .catch(err => console.error(err));
}

function tbodyFormTable(res) {
    const chargesVariables = res.data;
    const tbodyElement = document.getElementById("tbodyChargeVariable");
    tbodyElement.innerHTML = ''; // Effacer le contenu existant du tbody

    chargesVariables.forEach(cv => {
        const tr = document.createElement("tr");//<tr></tr>

        const idCell = document.createElement("th");//<th>7</th>
        idCell.textContent = cv.idCV;
        tr.appendChild(idCell);

        const nomCell = document.createElement("td");
        nomCell.textContent = cv.nom;
        tr.appendChild(nomCell);

        const montantCell = document.createElement("td");
        montantCell.textContent = cv.montant;
        tr.appendChild(montantCell);

        const anneeCell = document.createElement("td");
        anneeCell.textContent = cv.annee;
        tr.appendChild(anneeCell);

        const updateCell = document.createElement("td");
        updateCell.className = "h4";
        const updateLink = document.createElement("a");
        updateLink.href = "#";
        updateLink.onclick = function() {
            updateCV(cv.idCV, cv.projet.idProjet);
        };
        updateLink.innerHTML = '<em class="icon ni ni-pen-alt-fill"></em>';
        updateCell.appendChild(updateLink);
        tr.appendChild(updateCell);

        const deleteCell = document.createElement("td");
        deleteCell.className = "h4";
        const deleteLink = document.createElement("a");
        deleteLink.href = "#";
        deleteLink.onclick = function() {
            supprimerChargeVariable(cv.idCV);
        };
        deleteLink.innerHTML = '<em class="icon ni ni-trash-fill"></em>';
        deleteCell.appendChild(deleteLink);
        tr.appendChild(deleteCell);

        tbodyElement.appendChild(tr);
    });
}


function supprimerChargeVariable(idChargeVariable){
    event.preventDefault();
    console.log(idChargeVariable)

    axios.get('/deleteCV?idCV='+ idChargeVariable).then(
        res => {
            reloadTbody();
        }
    ).catch(error => {
        console.error(error);
    });

}