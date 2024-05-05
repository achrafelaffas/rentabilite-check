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
                getAllProjets();
            }
        ).catch(error => {
            console.error(error);
        });
    });
}

function getAllProjets() {
    axios.post('projets', jsonData).then(
        res => {
            document.getElementById("AjouetProjetForm").reset();
            document.querySelector('.modal .close').click();
        }
    ).catch(error => {
        console.error(error);
    });
}
