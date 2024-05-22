function getTotalCashFlow(idProjet) {
    axios.get(`/projet/${idProjet}/cashflows`)
        .then(res => {
            console.log(res)
            const totalCashFlow = res.data.reduce((acc, curr) => acc + curr, 0);  // assuming res.data is an array of cash flows
            const tdTotalCashFlow = document.getElementById(`totalCashFlow_${idProjet}`);
            const tdVAN = document.getElementById(`van_${idProjet}`);
            const tdIP = document.getElementById(`ip_${idProjet}`);
            const tdDRCI = document.getElementById(`drci_${idProjet}`);
            axios.get(`get-projet-a-modifier?idProjet=${idProjet}`).then(ress => {
                let projet = ress.data;
                let capital = projet.captial;
                console.log("capital :"+capital);



                let van = totalCashFlow - capital;
                console.log("van :"+capital);
                let ip = 1 + (van / capital);
                console.log("ip :"+capital);


                // Assuming DRSI is calculated as the number of periods it takes to break even
                // You would need the specific cash flows to calculate this properly
                let cumulativeCashFlow = 0;
                let drci = 0;
                for (let i = 0; i < res.data.length; i++) {
                    cumulativeCashFlow += res.data[i];
                    if (cumulativeCashFlow >= capital) {
                        drci = i + 1; // assuming i is zero-based index
                        break;
                    }
                }

                // Update the DOM with the calculated values
                tdTotalCashFlow.innerHTML = `
                    <p>totalCashFlow: ${totalCashFlow.toFixed(2)}</p>
                    <p>VAN: ${van.toFixed(2)}</p>
                    <p>IP: ${ip.toFixed(2)}</p>
                    <p>DRCI: ${drci}</p>
                `;
                tdIP.innerHTML=`<p>${ip.toFixed(2)}</p>`
                tdVAN.innerHTML=`<p>${van.toFixed(2)}</p>`
                tdDRCI.innerHTML=`<p>${drci}</p>`

                console.log(projet.duree);
                console.log(drci);

                if (van > 0 && ip > 1 && drci < projet.duree) {
                    tdTotalCashFlow.innerHTML += "<p class=\"badge badgedot bg-success\">Le projet est Rentable</p>";
                } else {
                    tdTotalCashFlow.innerHTML += "<p class=\"badge badge-dot bg-danger\">Le projet est non Rentable</p>";
                }

                axios.post('/insertVANIPDRCI', null, {
                    params: {
                        idProjet: idProjet,
                        van: van,
                        ip: ip,
                        drci: drci
                    }
                }).then(async () => {
                    //await updateTableView();
                }).catch(error => {
                    console.error("Error inserting VAN, IP, and DRCI:", error);
                });


            });
        })
        .catch(error => {
            console.error(error);
        });
}



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
        document.getElementById("capitalUpdate").value = projet.captial;
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

        const tdcapital = document.createElement('td');
        tdimpotSociete.innerText = p.captial

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
        tr.append(tdcapital);
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
