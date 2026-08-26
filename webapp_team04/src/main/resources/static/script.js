const formSignup = document.getElementById("signup");
const dataNascita = document.getElementById("data");
const erroreData = document.getElementById("errore-data");
const password = document.getElementById("password");
const errorePassword = document.getElementById("errore-formato");
const confermaPassword = document.getElementById("passwordRipetuta");
const erroreConferma = document.getElementById("errore-conferma");
const btnReset = document.getElementById("res");
const formPassword = document.getElementById("cambiopw");

function controlloPassword(){
    //controllo formato password
    let valida = true;
    const pattern = /id_04/;
    if(!pattern.test(password.value) || password.value.length !== 8){
        errorePassword.innerHTML = "La password deve contenere 'id_04' e dev'essere lunga 8 caratteri";
        valida = false;
    }
    else{
        errorePassword.innerHTML = "";
    }

    //controllo corrispondenza tra le 2 password
    if(password.value !== confermaPassword.value){
        erroreConferma.innerHTML = "Le due password non coincidono";
        valida = false;
    }
    else{
        erroreConferma.innerHTML = "";
    }

    return valida;
}

function isMaggiorenne(nascita, oggi){
    if(nascita.getFullYear() > (oggi.getFullYear() - 18)){
        return false;
    }
    else{
        if(nascita.getFullYear() === (oggi.getFullYear() - 18)){
            if(nascita.getMonth() > oggi.getMonth()){
                return false;
            }
            else{
                if(nascita.getMonth() === oggi.getMonth()){
                    return nascita.getDate() <= oggi.getDate();
                }
                else{
                    return true;
                }
            }
        }
        else{
            return true;
        }
    }
}

//gestione "submit" registrazione
if(formSignup){
    formSignup.addEventListener("submit", function (e){
        let formValido = true;

        //controllo formato data GG/MM/AAAA
        const dataSpezzata = dataNascita.value.split('/');
        if(dataSpezzata.length !== 3 || dataSpezzata[0].length !== 2 || dataSpezzata[1].length !== 2 || dataSpezzata[2].length !== 4){
            erroreData.innerHTML = "Formato della data non valido (GG/MM/AAAA)";
            formValido = false;
        }
        //controllo utente maggiorenne (solo se il formato della data è corretto)
        else{
            const giorno = parseInt(dataSpezzata[0], 10);
            const mese = parseInt(dataSpezzata[1], 10);
            const anno = parseInt(dataSpezzata[2], 10);
            const nascita = new Date(anno, mese-1, giorno);
            const oggi = new Date();
            if(!isMaggiorenne(nascita, oggi)){
                erroreData.innerHTML = "Devi essere maggiorenne per poterti registrare";
                formValido = false;
            }
            else{
                erroreData.innerHTML = "";
            }
        }

        if(controlloPassword() === false){
            formValido = false;
        }

        if(formValido === false){
            e.preventDefault();
            e.stopPropagation();
        }

    });
}


//gestione "reset" registrazione
if(btnReset){
    btnReset.addEventListener("click", function () {
        erroreData.innerHTML = "";
        errorePassword.innerHTML = "";
        erroreConferma.innerHTML = "";
    });
}


//gestione cambio password
if(formPassword){
    formPassword.addEventListener("submit", function (e) {
        if(controlloPassword() === false){
            e.preventDefault();
            e.stopPropagation();
        }
    });
}




