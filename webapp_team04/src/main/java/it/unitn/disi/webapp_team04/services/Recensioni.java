package it.unitn.disi.webapp_team04.services;

import it.unitn.disi.webapp_team04.pojos.Recensione;
import it.unitn.disi.webapp_team04.repositories.RecensioneRepository;
import it.unitn.disi.webapp_team04.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class Recensioni {
    private final RecensioneRepository recensioneRepository;
    private final UserRepository userRepository;

    public Recensioni(RecensioneRepository recensioneRepository, UserRepository userRepository) {
        this.recensioneRepository = recensioneRepository;
        this.userRepository = userRepository;
    }

    public Recensione addRecensione(String titolo, String testo, String username){
        Integer id_user = userRepository.getIdUser(username);
        Recensione r = new Recensione(titolo, testo, id_user, username);
        recensioneRepository.addRecensione(r);
        return r;
    }
}
