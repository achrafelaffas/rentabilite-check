function InsererProjet() {
    const formAjout = document.getElementById("AjouetProjetForm");
    formAjout.addEventListener('submit', insert);

    function insert(event) {
        event.preventDefault();
        const formData = new FormData(this);
        const jsonData = {};
        formData.forEach((value, key) => {
            jsonData[key] = value;
        });

        axios.post('ajouter-projet', jsonData).then(async res => {
            document.getElementById("AjouetProjetForm").reset();
            document.querySelector('.modal .close').click();
            formAjout.removeEventListener('submit', insert);
            await updateTableView();
        }).catch(error => {
            console.error(error);
        });
    }
}



function updateProjet(idProjet) {
    axios.get(`get-projet-a-modifier?idProjet=${idProjet}`).then(res => {
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

    const updateForm = document.getElementById("updateProjetForm");
    updateForm.addEventListener('submit', update);

    function update(event) {
        event.preventDefault();
        const formData = new FormData(this);
        const jsonData = {};
        formData.forEach((value, key) => {
            jsonData[key] = value;
        });
        axios.post('update-projet', jsonData).then(async res => {
            document.getElementById("updateProjetForm").reset();
            document.querySelector('#updateProjetModal .close').click();
            updateForm.removeEventListener('submit', update);
            await updateTableView();
        }).catch(error => {
            console.error(error);
        });
    }
}



async function updateTableView() {

    const projets = await axios.get('get-projets').then(res => {
        return res.data;
    });

    const body = document.getElementById('tbodyProjet');
    body.innerHTML = "";

    projets.forEach((p) => {
        const tr = document.createElement('tr');

        const tdnom = document.createElement('td');
        tdnom.innerText = p.nom;

        const tdimpotSociete = document.createElement('td');
        tdimpotSociete.innerText = p.impotSociete;

        const tdduree = document.createElement('td');
        tdduree.innerText = p.duree;

        const tdvan = document.createElement('td');
        tdvan.innerText = p.van;

        const tdip = document.createElement('td');
        tdip.innerText = p.ip;

        const tddrci = document.createElement('td');
        tddrci.innerText = p.drci;

        const tdidProjet = document.createElement('td');
        tdidProjet.innerText = p.idProjet;

        const tdRentabilite = document.createElement('td')
        tdRentabilite.innerHTML = '<span class="badge badge-dim bg-success"><span>Rentable</span></span>';

        const actions = document.createElement('td');
        actions.innerHTML = `
                                    <ul class="nk-tb-actions gx-1">
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
                                                              onclick='updateProjet(${p.idProjet})'>
                                                                <em class="icon ni ni-pen-alt-fill"></em>
                                                                <span>Modifier</span>
                                                            </a>
                                                        </li>
                                                        <li>
                                                            <a href="supprimer-projet?idProjet=${p.idProjet}">
                                                                <em class="icon ni ni-trash-fill"></em>
                                                                <span>Supprimer</span>
                                                            </a>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </li>
                                    </ul>`;

        tr.append(tdidProjet);
        tr.append(tdnom);
        tr.append(tdimpotSociete);
        tr.append(tdduree);
        tr.append(tdvan);
        tr.append(tdip);
        tr.append(tddrci);
        tr.append(tdRentabilite);
        tr.append(actions);
        body.append(tr);
    });
}