Il progetto ha lo scopo di ideare e sviluppare una web app per un centro fitness e benessere. La web app ha diverse pagine dinamiche, garantisce possibilità di registrazione, login e logout e organizza in modo differente le sezioni pubbliche da quelle ad accesso privato, queste ultime concedono funzionalità esclusive legate alla gestione degli allenamenti. 

La web app offre servizi propri ma ha anche un collegamento diretto ad un servizio Rest per l’accesso alle informazioni base degli allenamenti proposti dal centro benessere. 
Abbiamo scelto di dare un tocco personale al progetto, partendo dal front end, che ci avrebbe permesso di testare in modo agevole le componenti del back end, dandogli un tema. 

Dopodichè abbiamo creato il servizio Rest, necessario a far funzionare molte delle funzionalità della web app, diviso in classi pojos, controllers, repositories e services, 
inizialmente con i tre endpoint richiesti, ai quali alle fine ne è stato aggiunto un quarto derivante dalle scelte di progettazione del database. 

Infine ci siamo dedicate alla web app vera e propria partendo dallo scheletro delle pagine html e cercando di gestirne i componenti per renderli dinamici e funzionanti. 
La web app è stata divisa in classi pojos, controllers, repositories e services come il servizio Rest; poi l’aggiunta di configuration e rowmappers (solo per alcuni tipi di dati) per gestire permessi e conversione nei pojos.
