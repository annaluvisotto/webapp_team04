const formSignup = document.getElementById("signup");
const dataNascita = document.getElementById("data");
const erroreData = document.getElementById("errore-data");
const password = document.getElementById("password");
const errorePassword = document.getElementById("errore-formato");
const confermaPassword = document.getElementById("passwordRipetuta");
const erroreConferma = document.getElementById("errore-conferma");
const btnReset = document.getElementById("res");
const formPassword = document.getElementById("cambiopw");
const formRecensione = document.getElementById("formRecensione");
const rimuoviUtenti = document.getElementById("rimuoviUtenti");

function controlloPassword() {
    //controllo formato password
    let valida = true;
    const pattern = /id_04/;
    if (!pattern.test(password.value) || password.value.length !== 8) {
        errorePassword.innerHTML = "La password deve contenere 'id_04' e dev'essere lunga 8 caratteri";
        valida = false;
    } else {
        errorePassword.innerHTML = "";
    }

    //controllo corrispondenza tra le 2 password
    if (password.value !== confermaPassword.value) {
        erroreConferma.innerHTML = "Le due password non coincidono";
        valida = false;
    } else {
        erroreConferma.innerHTML = "";
    }

    return valida;
}

function isMaggiorenne(nascita, oggi) {
    if (nascita.getFullYear() > (oggi.getFullYear() - 18)) {
        return false;
    } else {
        if (nascita.getFullYear() === (oggi.getFullYear() - 18)) {
            if (nascita.getMonth() > oggi.getMonth()) {
                return false;
            } else {
                if (nascita.getMonth() === oggi.getMonth()) {
                    return nascita.getDate() <= oggi.getDate();
                } else {
                    return true;
                }
            }
        } else {
            return true;
        }
    }
}

//gestione "submit" registrazione
if (formSignup) {
    formSignup.addEventListener("submit", function (e) {
        let formValido = true;

        //controllo formato data GG/MM/AAAA
        const formatoData = /^\d{2}\/\d{2}\/\d{4}$/;
        if (!formatoData.test(dataNascita.value)) {
            erroreData.innerHTML = "Formato della data non valido (GG/MM/AAAA)";
            formValido = false;
        }
        //controllo utente maggiorenne (solo se il formato della data è corretto)
        else {
            const dataSpezzata = dataNascita.value.split('/');
            const giorno = parseInt(dataSpezzata[0], 10);
            const mese = parseInt(dataSpezzata[1], 10);
            const anno = parseInt(dataSpezzata[2], 10);
            const nascita = new Date(anno, mese - 1, giorno);
            const oggi = new Date();
            if (!isMaggiorenne(nascita, oggi)) {
                erroreData.innerHTML = "Devi essere maggiorenne per poterti registrare";
                formValido = false;
            } else {
                erroreData.innerHTML = "";
            }
        }

        if (controlloPassword() === false) {
            formValido = false;
        }

        if (formValido === false) {
            e.preventDefault();
            e.stopPropagation();
        }

    });
}


//gestione "reset" registrazione
if (btnReset) {
    btnReset.addEventListener("click", function () {
        erroreData.innerHTML = "";
        errorePassword.innerHTML = "";
        erroreConferma.innerHTML = "";
    });
}


//gestione cambio password
if (formPassword) {
    formPassword.addEventListener("submit", function (e) {
        if (controlloPassword() === false) {
            e.preventDefault();
            e.stopPropagation();
        }
    });
}

document.addEventListener("DOMContentLoaded", function() {
    const parametriURL = new URLSearchParams(window.location.search);
    const tipoSuccesso = parametriURL.get("success");
    if (tipoSuccesso === "upgrade") {
        alert("Upgrade completato! Benvenuto nel tuo nuovo piano");
    } else if (tipoSuccesso === "password") {
        alert("Password aggiornata con successo!");
    }
});


//gestione rimozione utenti prova disablitati
if (rimuoviUtenti) {
    rimuoviUtenti.addEventListener("submit", async function (e) {
        e.preventDefault();
        try {
            const response = await fetch("/elimina_disabilitati", {method: 'POST'});
            if (!response.ok) {
                throw new Error("Errore nell'eliminazione degli user prova disabilitati");
            }
            const text = await response.text(); //uso .text anzichè .json perchè il controller torna una stringa
            const utentiRimossi = document.getElementById("utentiRimossi");
            const numero = `Sono stati rimossi <span class="winx-badge-counter">${text}</span> utenti prova`;
            utentiRimossi.innerHTML = numero;
        } catch (error) {
            console.log(error.message);
        }
    })
}


//gestione contatti
const btnInvia = document.getElementById('btn-invia');
const redirectUrl = /*[[@{/index}]]*/ '/index';

if (btnInvia) {
    btnInvia.addEventListener('click', function () {
        const nome = document.getElementById('uname').value.trim();
        const email = document.getElementById('mail').value.trim();
        const msg = document.getElementById('note').value.trim();

        if (!nome || !email || !msg) {
            alert("Compila tutti i campi prima di inviare.");
            return;
        }

        alert("Grazie! Il tuo messaggio è stato inviato con successo.");
        window.location.href = redirectUrl;
    });
}


//gestione inserimento allenamento (user pro)
document.querySelectorAll('.training-sub').forEach(form => {
    form.addEventListener('submit', async function (event) {
        event.preventDefault();
        const formData = new FormData(this);

        try {
            const response = await fetch(this.action, {method: 'POST', body: new URLSearchParams(formData)});
            if (response.ok) {
                const data = await response.json();

                if (data.alert) {
                    alert(data.alert);
                }
                window.location.href = data.redirect;
            }
        } catch (error) {
            console.error('Errore durante il salvataggio:', error);
        }
    });
});


//gestione allenamento (tutti gli user)
const MAX_ESERCIZI = 25;
const container = document.getElementById('esercizi');
const btnAdd = document.getElementById('addEx');

const form = document.getElementById('PersTraining');

if (container && btnAdd) {

    const ExOptions = document.querySelector('.select-esercizio').innerHTML;

    function updateCounter() {
        const total = container.querySelectorAll('.esercizio-item').length;
        btnAdd.disabled = total >= MAX_ESERCIZI;
    }

    function reindexExercises() {
        const items = container.querySelectorAll('.esercizio-item');
        items.forEach((item, index) => {
            item.setAttribute('data-index', index);
            /*item.querySelector('input[name*=".nome"]').name = `esercizi[${i}].nome`;
            item.querySelector('input[name*=".serie"]').name = `esercizi[${i}].serie`;
            item.querySelector('input[name*=".reps"]').name = `esercizi[${i}].reps`;*/
            const selectNome = item.querySelector('select[name*=".nome"]');
            if (selectNome) selectNome.name = `esercizi[${index}].nome`;

            const inputSerie = item.querySelector('input[name*=".serie"]');
            if (inputSerie) inputSerie.name = `esercizi[${index}].serie`;

            const inputReps = item.querySelector('input[name*=".reps"]');
            if (inputReps) inputReps.name = `esercizi[${index}].reps`;
        });
        updateCounter();
    }

    function validaEsercizio(item) {
        const fields = item.querySelectorAll('select, input');
        for (let field of fields) {
            if (!field.checkValidity()) {
                field.reportValidity();
                return false;
            }
        }
        return true;
    }

    btnAdd.addEventListener('click', (e) => {
        e.preventDefault();
        const items = container.querySelectorAll('.esercizio-item');
        const currentCount = items.length;
        if (currentCount >= MAX_ESERCIZI) return;
        const lastItem = items[items.length - 1];
        if (lastItem && !validaEsercizio(lastItem)) {
            return;
        }

        const index = currentCount;
        const div = document.createElement('div');
        div.className = 'card p-3 mb-3 bg-white border esercizio-item';
        div.setAttribute('data-index', index);

        div.innerHTML = `
            <div class="d-flex justify-content-end mb-2">
                <button type="button" class="btn btn-primary btn-remove">Rimuovi l'esercizio</button>
            </div>
            <div class="col-md-3">
                <label class="form-label small">Nome Esercizio</label>
                <select name="esercizi[${index}].nome" class="form-select select-esercizio" required>
                            <option value="" disabled selected>Seleziona l' esercizio</option>
                            ${ExOptions}
                        </select>
            </div>
            <div class="col-md-3">
                <label class="form-label small">Serie</label>
                <input type="number" name="esercizi[${index}].serie" class="form-control" placeholder="Numero serie" required>
            </div>
            <div class="col-md-3">
                <label class="form-label small">Ripetizioni</label>
                <input type="number" name="esercizi[${index}].reps" class="form-control" placeholder="Numero ripetizioni" required>
            </div>
    `;

        div.querySelector('.btn-remove').addEventListener('click', () => {
            div.remove();
            reindexExercises();
        });
        container.appendChild(div);
        updateCounter();
    });
}


if (form) {
    form.addEventListener('submit', async function (e) {
        e.preventDefault();

        if (!form.checkValidity()) {
            form.reportValidity();
            return;
        }

        const formData = new FormData(form);

        try {
            const response = await fetch(form.action, {
                method: 'POST',
                body: new URLSearchParams(formData)
            });

            const data = await response.json();

            if (data.error) {
                alert(data.error);
            } else {
                if (data.alert) {
                    alert(data.alert);
                }
                if (data.redirect) {
                    window.location.href = data.redirect;
                }
            }
        } catch (error) {
            console.error("Errore durante la richiesta:", error);
        }
    });
}

//gestione statistiche user
const canvasGrafico1 = document.getElementById('userStatsChart');

if (canvasGrafico1) {
    const rootStyles = getComputedStyle(document.documentElement);
    const colore = rootStyles.getPropertyValue('--winx-pastel-pink').trim();
    const grafico = canvasGrafico1.getContext('2d');

    new Chart(grafico, {
        type: 'bar',
        data: {
            labels: nomi,
            datasets: [
                {
                    label: 'Esecuzioni',
                    data: executions,
                    backgroundColor: colore
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        stepSize: 1
                    },
                    title: {
                        display: true,
                        text: 'Esecuzioni'
                    }
                },
                x: {
                    title: {
                        display: true,
                        text: 'Allenamenti'
                    }
                }
            }
        }
    });
}


//gestione statistiche admin
const canvasGrafico2 = document.getElementById('adminStatsChart');

if(canvasGrafico2){
    const rootStyles = getComputedStyle(document.documentElement);
    const coloreBasic = rootStyles.getPropertyValue('--winx-pastel-pink').trim();
    const colorePro = rootStyles.getPropertyValue('--winx-pastel-blue').trim();
    const grafico = canvasGrafico2.getContext('2d');

    new Chart(grafico, {
        type: 'bar',
        data: {
            labels: nomi,
            datasets: [
                {
                    label: 'Media Utenti Basic',
                    data: datiBasic,
                    backgroundColor: coloreBasic,
                },
                {
                    label: 'Media Utenti Pro',
                    data: datiPro,
                    backgroundColor: colorePro,
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        stepSize: 1
                    },
                    title: {
                        display: true,
                        text: 'Numero Medio di Esecuzioni'
                    }
                },
                x: {
                    title: {
                        display: true,
                        text: 'Allenamenti'
                    }
                }
            }
        }
    });
}




//gestione invio recensione
if (formRecensione) {
    formRecensione.addEventListener("submit", async function (e) {
        e.preventDefault();
        let titolo = document.getElementById("titoloRecensione").value;
        let testo = document.getElementById("testoRecensione").value;
        try {
            const response = await fetch("/inserimento_recensione", {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    titolo: titolo,
                    testo: testo
                })
            });
            if (!response.ok) {
                throw new Error("Errore nel loading del file JSON della recensione");
            }
            const json = await response.json();

            const carosello = document.querySelector('#caroselloRecensioni .carousel-inner');

            const slideAttiva = carosello.querySelector('.active');
            if (slideAttiva) {
                slideAttiva.classList.remove('active');
            }

            const nuovaRecensione = `
                <div class="carousel-item active">
                    <div class="card mx-auto text-center" style="width: 95%; max-width: 1200px; border: 2px dashed var(--winx-accent); border-radius: 12px; box-shadow: 4px 4px 0px rgba(176, 224, 230, 0.4); background-color: var(--winx-card);">
                        <div class="card-body p-3">
                            <h6 class="fw-bold mb-2" style="color: var(--winx-text-dark); font-family: 'Playfair Display', serif; font-size: 1.1rem;">
                                "${json.titolo}"
                            </h6>
                            <p class="mb-1" style="color: var(--winx-text-main); font-family: 'Raleway', sans-serif; font-size: 0.9rem;">
                                ${json.testo}
                            </p>
                            <small class="text-muted fw-bold" style="font-size: 0.8rem;">
                                - ${json.username}
                            </small>
                        </div>
                    </div>
                </div>
            `;

            carosello.innerHTML += nuovaRecensione;
            e.target.reset();
        } catch (error) {
            console.log(error.message);
        }
    });
}


//gestione carosello recensioni
async function caricaRecensioni() {
    try {
        const response = await fetch("/carosello_recensioni");
        if (!response.ok) {
            throw new Error("Errore nel loading del file JSON del carosello");
        }
        const json = await response.json();
        let recensioniHTML = '';
        const carosello = document.querySelector('#caroselloRecensioni .carousel-inner');

        json.forEach(function (recensione, index) {
            let classeAttiva = (index === 0) ? 'active' : '';
            recensioniHTML += `
                <div class="carousel-item ${classeAttiva}">
                    <div class="card mx-auto text-center" style="width: 95%; max-width: 1200px; border: 2px dashed var(--winx-accent); border-radius: 12px; box-shadow: 4px 4px 0px rgba(176, 224, 230, 0.4); background-color: var(--winx-card);">
                        <div class="card-body p-3">
                            <h6 class="fw-bold mb-2" style="color: var(--winx-text-dark); font-family: 'Playfair Display', serif; font-size: 1.1rem;">
                                "${recensione.titolo}"
                            </h6>
                            <p class="mb-1" style="color: var(--winx-text-main); font-family: 'Raleway', sans-serif; font-size: 0.9rem;">
                                ${recensione.testo}
                            </p>
                            <small class="text-muted fw-bold" style="font-size: 0.8rem;">
                                - ${recensione.username}
                            </small>
                        </div>
                    </div>
                </div>
            `;
        });
        carosello.innerHTML = recensioniHTML;
    } catch {
        console.log(error.message);
    }
}

caricaRecensioni();




