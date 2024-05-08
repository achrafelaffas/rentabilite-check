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

function updateAugBFR(idaUGbFR) {
    axios.get(`get-augbfr-a-modifier?idaUGbFR=${idaUGbFR}`).then(
        res => {
            const augmentationbfr = res.data;
            console.log(augmentationbfr)
            document.getElementById("nomUpdate").value = augmentationbfr.nom;
            document.getElementById("montantUpdate").value = augmentationbfr.montant;
            document.getElementById("anneeUpdate").value = augmentationbfr.annee;

            axios.get("get-projets").then(
                res => {
                    const projets = res.data;
                    const selectElement = document.getElementById("projetsUpdate");
                    selectElement.innerHTML = '';
                    projets.forEach(projet => {
                        const option = document.createElement("option");
                        option.value = projet.idProjet
                        option.text = projet.nom
                        selectElement.append(option)
                    });
                }
            )

            $('#updateAugBFRModal').modal('show');
        }).catch(err => console.log(err))
}