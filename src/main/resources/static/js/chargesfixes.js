
    function selectProjet(select) {
    var projetId = select.value;
    window.location.href = '/chargesfixes/' + projetId;
}


    function updateCF(idCF) {
        axios.get(`get-chargefixe-a-modifier?idCF=${idCF}`)
            .then(res => {
                const chargeFx = res.data;
                const projetId = chargeFx.projet ? chargeFx.projet.idProjet : null;

                document.getElementById("Updateidprojet").value = projetId;
                document.getElementById("idcf").value = idCF;
                document.getElementById("updatenom").value = chargeFx.nom;
                document.getElementById("updatemontant").value = chargeFx.montant;
                document.getElementById("updateannee").value = chargeFx.annee;
                $('#updateChargeFixeModal').modal('show');
            })
            .catch(err => console.error(err));
    }




    function sendCFUpdate() {
        document.getElementById("UpdateCHFXForm").addEventListener('submit', function (event) {
            event.preventDefault();
            const formData = new FormData(this);
            const jsonData = {};
            formData.forEach((value, key) => {
                jsonData[key] = value;
            });
            axios.post('update-CF', jsonData).then(
                res => {
                    document.getElementById("UpdateCHFXForm").reset();
                    document.querySelector('#updateChargeFixeModal .close').click();
                }
            ).catch(error => {
                console.error(error);
            });
        });
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
            })
            .catch(error => {
                console.error('Une erreur s\'est produite:', error);
            });
    }

