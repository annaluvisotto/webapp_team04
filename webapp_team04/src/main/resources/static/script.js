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
        const dataSpezzata = dataNascita.value.split('/');
        if (dataSpezzata.length !== 3 || dataSpezzata[0].length !== 2 || dataSpezzata[1].length !== 2 || dataSpezzata[2].length !== 4) {
            erroreData.innerHTML = "Formato della data non valido (GG/MM/AAAA)";
            formValido = false;
        }
        //controllo utente maggiorenne (solo se il formato della data è corretto)
        else {
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
async function caricaRecensioni(){
    try{
        const response = await fetch("/carosello_recensioni");
        if (!response.ok) {
            throw new Error("Errore nel loading del file JSON del carosello");
        }
        const json = await response.json();
        let recensioniHTML = '';
        const carosello = document.querySelector('#caroselloRecensioni .carousel-inner');

        json.forEach(function(recensione, index) {
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
    } catch{
        console.log(error.message);
    }
}

caricaRecensioni();




