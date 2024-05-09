
function InsererProjet() {
    document.getElementById("AjouetProjetForm").addEventListener('submit', function (event) {
        event.preventDefault();
        const formData = new FormData(this);
        const jsonData = {};
        formData.forEach((value, key) => {
            jsonData[key] = value;
        });

        axios.post('ajouter-projet', jsonData).then(
            res => {
                document.getElementById("AjouetProjetForm").reset();
                document.querySelector('.modal .close').click();

            }
        ).catch(error => {
            console.error(error);
        });
    });
}

function updateProjet(idProjet) {
    axios.get(`get-projet-a-modifier?idProjet=${idProjet}`).then(
        res => {
            const projet = res.data;
            document.getElementById("nomUpdate").value = projet.nom;
            document.getElementById("dureeUpdate").value = projet.duree;
            document.getElementById("descriptionUpdate").value = projet.description;
            document.getElementById("impotSocieteUpdate").value = projet.impotSociete;
            document.getElementById("idProjetUpdate").value = projet.idProjet;
            $('#updateProjetModal').modal('show');
        }).catch(err => console.log(err))
}

function sendProjetUpdate() {
    document.getElementById("updateProjetForm").addEventListener('submit', function (event) {
        event.preventDefault();
        const formData = new FormData(this);
        const jsonData = {};
        formData.forEach((value, key) => {
            jsonData[key] = value;
        });
        axios.post('update-projet', jsonData).then(
            res => {
                document.getElementById("updateProjetForm").reset();
                document.querySelector('#updateProjetModal .close').click();
            }
        ).catch(error => {
            console.error(error);
        });
    });
}