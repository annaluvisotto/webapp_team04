package it.unitn.disi.REST_service_team04.controllers;

import it.unitn.disi.REST_service_team04.pojos.Esercizio;
import it.unitn.disi.REST_service_team04.pojos.Programma;
import it.unitn.disi.REST_service_team04.services.ProgrammiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// restituisce i dati JSON tramite http

@RestController
public class RESTController {
    private final ProgrammiService progs;

    @Autowired
    public RESTController(ProgrammiService progs) {
        this.progs = progs;
    }

    @GetMapping("/Default-programs")
    public List<Programma> getProgrammiPredef() {
        return progs.getProgrammiPredef();
    }

    @GetMapping("/programs/{id}")
    public Programma getProgrammaById(@PathVariable int id) {
        return progs.getProgrammaById(id);
    }

    @PostMapping("/kcal")
    public float calorie(@RequestBody List<Esercizio> listaEs) {
        return progs.calcCalorie(listaEs);
    }
}
