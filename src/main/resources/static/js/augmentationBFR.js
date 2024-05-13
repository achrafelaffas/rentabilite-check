function AjouterAugmentationBfr() {

    const formAjout = document.getElementById("AjouetAugBFRForm");
    formAjout.addEventListener('submit', insert);

    async function insert(event) {
        event.preventDefault();
        const formData = new FormData(this);
        const idProjet = formData.get('projet');
        const jsonData = {};

        formData.forEach((value, key) => {
            jsonData[key] = value;
        });

        jsonData["projet"] = await axios.get(`get-projet-a-modifier?idProjet=${idProjet}`).then(res => {
            return res.data;
        });

        axios.post("ajouter-augmentation-bfr", jsonData).then(async res => {
            document.getElementById("AjouetAugBFRForm").reset();
            document.querySelector('#ajouterAugBFRModal .close').click();
            formAjout.removeEventListener("submit", insert);
            await updateTableView();
        });
    }
}

function fillProjetsSelect(selector, idp) {
    axios.get("get-projets").then(res => {
        const projets = res.data;
        const selectElement = document.getElementById(selector);
        selectElement.innerHTML = '';
        projets.forEach(projet => {
            const option = document.createElement("option");
            option.value = projet.idProjet;
            option.text = projet.nom;
            if (idp && idp === projet.idProjet) {
                option.setAttribute('selected', "");
            }
            selectElement.append(option);
        });
    })
}

function getAugmentationBfr(idaUGbFR, idProjet) {
    axios.get(`get-augbfr-a-modifier?idaUGbFR=${idaUGbFR}`).then(async res => {
        const bfr = res.data;
        document.getElementById('nomUpdate').value = bfr.nom;
        document.getElementById('montantUpdate').value = bfr.montant;
        document.getElementById('montantUpdate').value = bfr.montant;
        document.getElementById('anneeUpdate').value = bfr.annee.split("T")[0];
        document.getElementById('idaUGbFRUpdate').value = bfr.idaUGbFR

        const selectElement = document.getElementById("projetsUpdate");
        selectElement.innerHTML = '';
        fillProjetsSelect('projetsUpdate', bfr.projet.idProjet);
        $('#updateAugBFRModal').modal('show');
    })
}


function updateAugmentationBfr() {
    const formUpdate = document.getElementById('updateAugBFRForm');
    formUpdate.removeEventListener("submit", update);
    formUpdate.addEventListener('submit', update);

    async function update(event) {
        event.preventDefault();

        const formData = new FormData(this);
        const idProjet = formData.get('projet');
        const jsonData = {};
        formData.forEach((value, key) => {
            jsonData[key] = value;
        });
        jsonData["projet"] = await axios.get(`get-projet-a-modifier?idProjet=${idProjet}`).then(res => {
            return res.data;
        });

        axios.post('update-bfr', jsonData).then(async res => {
            document.getElementById("updateAugBFRForm").reset();
            document.querySelector('#updateAugBFRModal .close').click();
            messageSuccess("Votre BFR est été modifier avec succès");
            await updateTableView();
        }).catch(error => {
            console.error(error);
        });
    }
}


async function updateTableView(bfrs) {
    if (!bfrs) {
        bfrs = await axios.get('get-bfrs').then(res => {
            return res.data;
        });
    }

    const body = document.getElementById('body');
    body.innerHTML = '';

    bfrs.forEach((bfr) => {
        const tr = document.createElement('tr');

        const tdnom = document.createElement('td');
        tdnom.innerText = bfr.nom;

        const tdmontant = document.createElement('td');
        tdmontant.innerText = bfr.montant;

        const tdannee = document.createElement('td');
        tdannee.innerText = bfr.annee;

        const tdidaUGbFR = document.createElement('td');
        tdidaUGbFR.innerText = bfr.idaUGbFR;

        const actions = document.createElement('td');
        actions.innerHTML = `<ul class="nk-tb-actions gx-1">
                                <li>
                                    <div class="drodown">
                                        <a href="#"
                                           class="dropdown-toggle btn btn-sm btn-icon btn-trigger"
                                           data-bs-toggle="dropdown"><em
                                                class="icon ni ni-more-h"></em></a>
                                        <div class="dropdown-menu dropdown-menu-end">
                                            <ul class="link-list-opt no-bdr">
                                                <li>
                                                    <a href="#"
                                                      onclick='getAugmentationBfr(${bfr.idaUGbFR},${bfr.projet.idProjet})'>
                                                        <em class="icon ni ni-pen-alt-fill"></em>
                                                        <span>Modifier</span>
                                                    </a>
                                                </li>
                                                <li>
                                                    <a href="supprimer-aug?idaUGbFR=${bfr.idaUGbFR}">
                                                        <em class="icon ni ni-trash-fill"></em>
                                                        <span>Supprimer</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </li>
                            </ul>`;

        tr.append(tdidaUGbFR);
        tr.append(tdnom);
        tr.append(tdmontant);
        tr.append(tdannee);
        tr.append(actions);
        body.append(tr);
    });
}


function messageSuccess(message) {
    const Toast = Swal.mixin({
        toast: true,
        position: "top",
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
        didOpen: (toast) => {
            toast.onmouseenter = Swal.stopTimer;
            toast.onmouseleave = Swal.resumeTimer;
        }
    });
    Toast.fire({
        icon: "success", title: message
    });
}


async function filterByProjet() {
    const idProjet = document.getElementById("filterBfrByProjet").value;
    if (idProjet === '0') {
        await updateTableView();
    } else {
        const augmentationBfr = await axios.get(`get-bfrs-by-projet?idProjet=${idProjet}`).then(res => {
            return res.data;
        });
        await updateTableView(augmentationBfr);
    }
}
