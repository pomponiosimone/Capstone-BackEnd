package pomponiosimone.Capstone_BackEnd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pomponiosimone.Capstone_BackEnd.entities.Scarpa;
import pomponiosimone.Capstone_BackEnd.entities.Taglia;
import pomponiosimone.Capstone_BackEnd.exceptions.BadRequestException;
import pomponiosimone.Capstone_BackEnd.payloads.ScarpaDTO;
import pomponiosimone.Capstone_BackEnd.repositories.ScarpeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScarpeService {
    @Autowired
    private ScarpeRepository scarpeRepository;
    public Scarpa saveSneakers (ScarpaDTO body) {
        List<Taglia> taglie = body.taglie().stream()
                .map(tagliaDTO -> new Taglia(tagliaDTO.numeroTaglia(), tagliaDTO.quantita()))
                .collect(Collectors.toList());
        if (this.scarpeRepository.findByNome(body.nome()).isPresent()) {
            throw new BadRequestException("nome" + body.nome() + "già in uso!!");

        } else {
            Scarpa scarpa = new Scarpa(body.descrizione(), body.immagine(), body.marca(), body.nome(), body.prezzo(), taglie);
     return this.scarpeRepository.save(scarpa);
       }

    }}