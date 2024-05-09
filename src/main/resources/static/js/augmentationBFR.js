function InsererAugBFR() {
    document.getElementById("AjouetAugBFRForm").addEventListener('submit', async function (event) {
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

        axios.post("ajouter-augmentation-bfr", jsonData).then(
            res => {
                document.getElementById("AjouetAugBFRForm").reset();
                document.querySelector('#ajouterAugBFRModal .close').click();
            }
        )
    });
}

function getProjets() {
    axios.get("get-projets").then(
        res => {
            const projets = res.data;
            // get the select element
            const selectElement = document.getElementById("projets");
            selectElement.innerHTML = '';
            // Create option elements
            projets.forEach(projet => {
                const option = document.createElement("option");
                option.value = projet.idProjet
                option.text = projet.nom
                selectElement.append(option)
            });
        }
    )
}

function updateAugBFR(idaUGbFR, idProjet) {
    axios.get(`get-augbfr-a-modifier?idaUGbFR=${idaUGbFR}`).then(
        res => {
            const bfr = res.data;
            document.getElementById('nomUpdate').value = bfr.nom;
            document.getElementById('montantUpdate').value = bfr.montant;
            document.getElementById('montantUpdate').value = bfr.montant;
            document.getElementById('anneeUpdate').value = bfr.annee.split("T")[0];
            document.getElementById('idaUGbFRUpdate').value = bfr.idaUGbFR

            axios.get("get-projets").then(
                res => {
                    const projets = res.data;
                    // get the select element
                    const selectElement = document.getElementById("projetsUpdate");
                    selectElement.innerHTML = '';
                    // Create option elements
                    projets.forEach(projet => {
                        const option = document.createElement("option");
                        option.value = projet.idProjet
                        option.text = projet.nom
                        selectElement.append(option)
                        if (projet.idProjet === idProjet) {
                            option.setAttribute('selected', true);
                        }
                    });
                }
            )

            $('#updateAugBFRModal').modal('show');
        }
    )
}

function sendBfrUpdate() {
    document.getElementById('updateAugBFRForm').addEventListener('submit', async function (event) {
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

        axios.post('update-bfr', jsonData).then(
            res => {
                document.getElementById("updateAugBFRForm").reset();
                document.querySelector('#updateAugBFRModal .close').click();
            }
        ).catch(error => {
            console.error(error);
        });
    })
}