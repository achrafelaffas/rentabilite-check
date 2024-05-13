async function insererInvestissement() {

    const formAjout = document.getElementById('AjoutInvestissementForm');
    formAjout.addEventListener('submit', insert);

    async function insert(event) {
        event.preventDefault();
        const formData = new FormData(this);
        const idProjet = formData.get('projet');
        const jsonData = {};
        formData.forEach((value, key) => {
            jsonData[key] = value;
        });

        jsonData['projet'] = await axios.get(`get-projet-a-modifier?idProjet=${idProjet}`).then(res => {
            return res.data
        });

        await axios.post('ajouter-investissement', jsonData).then(async res => {
            event.target.reset();
            document.getElementById("AjoutInvestissementForm").reset();
            document.querySelector('#AjoutInvestissementModal .close').click();
            formAjout.removeEventListener('submit', insert);
            await updateTableView();
        }).catch(err => {
            console.log(err);
        });
    }
}

async function projetsSelect(selectid, idp) {
    const select = document.getElementById(selectid);
    const projets = await getProjetsList();

    if (select && projets) {
        select.innerHTML = '';
        projets.forEach(projet => {
            const option = document.createElement("option");
            option.value = projet.idProjet;
            option.text = projet.nom;
            if (idp && idp === projet.idProjet) {
                option.setAttribute('selected', "");
            }
            select.append(option);
        });
    }
}

async function getProjetsList() {
    return await axios.get('get-projets').then(res => {
        return res.data;
    });
}

async function updateInvestissement(id, idp) {
    const investissement = await getInvestissement(id);
    document.getElementById('dureeINVUpdate').value = investissement.dureeINV;
    document.getElementById('tauxActualisationUpdate').value = investissement.tauxActualisation;
    document.getElementById('intituleUpdate').value = investissement.intitule;
    document.getElementById('montantINVUpdate').value = investissement.montantINV;
    document.getElementById('id').value = investissement.id;
    await projetsSelect('projetUpdate', investissement.projet.idProjet);
    $('#UpdateInvestissementModal').modal('show');
}

async function applyupdateInvestissement() {
    const formUpdate = document.getElementById('UpdateInvestissementForm');
    formUpdate.removeEventListener('submit', update);
    formUpdate.addEventListener('submit', update);

    async function update(event) {
        event.preventDefault();
        const updareFormData = new FormData(this);
        const jsonData = {};
        updareFormData.forEach((value, key) => {
            jsonData[key] = value;
        });
        const projets = await getProjetsList();
        projets.forEach((p) => {
            if (jsonData["projet"] === p.idProjet.toLocaleString()) {
                jsonData["projet"] = p;
            }
        });
        axios.post('update-investissement', jsonData).then(async res => {
            event.target.reset();
            document.getElementById("UpdateInvestissementForm").reset();
            document.querySelector('#UpdateInvestissementModal .close').click();
            await updateTableView();
        }).catch(err => {
            console.log(err)
        });
    }
}

async function getInvestissement(id) {
    return await axios.post(`get-investissement?id=${id}`).then(res => {
        return res.data;
    })
}

async function updateTableView(investissements) {
    if (!investissements) {
        investissements = await axios.get('get-all-investissements').then(res => {
            return res.data;
        });
    }
    const tbody = document.getElementById('tbody');
    tbody.innerHTML = '';
    investissements.forEach((inv) => {
        const tr = document.createElement('tr');
        const id = document.createElement('td');
        id.innerText = inv.id;
        const montant = document.createElement('td');
        montant.innerText = inv.montantINV;
        const duree = document.createElement('td');
        duree.innerText = inv.dureeINV;
        const tauxActualisation = document.createElement('td');
        tauxActualisation.innerText = inv.tauxActualisation;
        const intitule = document.createElement('td');
        intitule.innerText = inv.intitule;
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
                                                      onclick='updateInvestissement(${inv.id},${inv.projet.idProjet})'>
                                                        <em class="icon ni ni-pen-alt-fill"></em>
                                                        <span>Modifier</span>
                                                    </a>
                                                </li>
                                                <li>
                                                    <a href="supprimer-investissement?id=${inv.id}">
                                                        <em class="icon ni ni-trash-fill"></em>
                                                        <span>Supprimer</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </li>
                            </ul>`;


        tr.append(id);
        tr.append(montant);
        tr.append(duree);
        tr.append(tauxActualisation);
        tr.append(intitule);
        tr.append(actions);
        tbody.append(tr);
    });
}

async function filterInvestByProjet() {
    const idp = document.getElementById('filterInvByProjet').value;
    if (idp === '0') {
        await updateTableView();
    } else {
        const investissements = await axios.get(`find-investissement-by-projet?idProjet=${idp}`).then(res => {
            return res.data;
        });
        await updateTableView(investissements);
    }
}

