
    function selectProjet(select) {
        var projetId = select.value;

        if(projetId != 0) {
            axios.get(`/cfx?idProjet=${projetId}`)
                .then(res => {
                    tbodyFormTable(res)
                })
                .catch(err => console.error(err));
        }
        else{
            reloadTbody()
        }
    }



    function AjouterChargeFixe() {
        event.preventDefault();
        var projetId = document.getElementById('projet').value;
        var nom = document.getElementById('nom').value;
        var montant = document.getElementById('montant').value;
        var annee = document.getElementById('annee').value;

        var chargeFixe = {
            nom: nom,
            montant: montant,
            annee: annee
        };
        axios.post('/ajouter-CHfix?projetId=' + projetId, chargeFixe)
            .then(response => {
                document.getElementById('AjouetCHFXForm').reset();
                document.querySelector('#ajouterCHFModal .close').click();
                reloadTbody();
            })
            .catch(error => {
                console.error('Une erreur s\'est produite:', error);
            });
    }











    function updateCF(idCF,idProjet) {
        axios.get(`get-chargefixe-a-modifier?idCF=${idCF}`)
            .then(res => {
                const chargeFx = res.data;

                document.getElementById("Updateidprojet").value = idProjet;
                document.getElementById("idcf").value = idCF;
                document.getElementById("updatenom").value = chargeFx.nom;
                document.getElementById("updatemontant").value = chargeFx.montant;
                document.getElementById("updateannee").value = chargeFx.annee.split("T")[0];
                $('#updateChargeFixeModal').modal('show');
            })
            .catch(err => console.error(err));
    }


    function sendCFUpdate() {
        document.getElementById("UpdateCHFXForm").addEventListener('submit', async function (event) {
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
            console.log(jsonData)

            axios.post('update-CF', jsonData).then(
                res => {
                    document.getElementById("UpdateCHFXForm").reset();
                    document.querySelector('#updateChargeFixeModal .close').click();
                    reloadTbody();
                }
            ).catch(error => {
                console.error(error);
            });


        });


    }


    function reloadTbody() {
        axios.get(`get-all-chargefixes`)
            .then(res => {
                tbodyFormTable(res)
            })
            .catch(err => console.error(err));
    }

    function tbodyFormTable(res){
        const chargesFixes = res.data;
        const tbodyElement = document.getElementById("tbodyChargeFixe");
        tbodyElement.innerHTML = ''; // Effacer le contenu existant du tbody

        chargesFixes.forEach(cf => {
            const tr = document.createElement("tr");//<tr></tr>

            const idCell = document.createElement("th");//<th>7</th>
            idCell.textContent = cf.idCF;
            tr.appendChild(idCell);

            const nomCell = document.createElement("td");
            nomCell.textContent = cf.nom;
            tr.appendChild(nomCell);

            const montantCell = document.createElement("td");
            montantCell.textContent = cf.montant;
            tr.appendChild(montantCell);

            const anneeCell = document.createElement("td");
            anneeCell.textContent = cf.annee;
            tr.appendChild(anneeCell);

            const updateCell = document.createElement("td");
            updateCell.className="h4"
            const updateLink = document.createElement("a");
            updateLink.href = "#";
            updateLink.onclick = function () {
                updateCF(cf.idCF, cf.projet.idProjet);
            };
            updateLink.innerHTML = '<em class="icon ni ni-pen-alt-fill"></em>';
            updateCell.appendChild(updateLink);
            tr.appendChild(updateCell);

            const deleteCell = document.createElement("td");
            deleteCell.className="h4"
            const deleteLink = document.createElement("a");
            deleteLink.href = "#";
            deleteLink.onclick = function() {
                supprimerChargeFixe(cf.idCF);
            };
            deleteLink.innerHTML = '<em class="icon ni ni-trash-fill"></em>';
            deleteCell.appendChild(deleteLink);
            tr.appendChild(deleteCell);

            tbodyElement.appendChild(tr);
        });
    }

    function supprimerChargeFixe(idChargeFix){
        event.preventDefault();
        axios.get('/deleteCF?idCF='+ idChargeFix).then(
            res => {
                reloadTbody();
            }
        ).catch(error => {
            console.error(error);
        });

    }


