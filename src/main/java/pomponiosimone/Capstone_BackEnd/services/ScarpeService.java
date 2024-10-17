package pomponiosimone.Capstone_BackEnd.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import pomponiosimone.Capstone_BackEnd.entities.Scarpa;
import pomponiosimone.Capstone_BackEnd.entities.Taglia;

import pomponiosimone.Capstone_BackEnd.exceptions.BadRequestException;
import pomponiosimone.Capstone_BackEnd.exceptions.NotFoundException;
import pomponiosimone.Capstone_BackEnd.payloads.ScarpaDTO;
import pomponiosimone.Capstone_BackEnd.repositories.ScarpeRepository;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ScarpeService {
    @Autowired
    private ScarpeRepository scarpeRepository;
    @Autowired
    private Cloudinary cloudinaryUploader;

    public Page<Scarpa> findAll(int page, int size, String sortBy) {
        if (page > 12) page = 12;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return this.scarpeRepository.findAll(pageable);
    }

    //Save
    public Scarpa saveSneakers(ScarpaDTO body) {
        List<Taglia> taglie = body.taglie().stream()
                .map(tagliaDTO -> {
                    if (tagliaDTO.quantità() == null || tagliaDTO.taglia() == null) {
                        throw new BadRequestException("Taglia o quantità non possono essere null");
                    }
                    return new Taglia(tagliaDTO.quantità(), tagliaDTO.taglia());
                })
                .collect(Collectors.toList());

        if (this.scarpeRepository.findByNome(body.nome()).isPresent()) {
            throw new BadRequestException("Il nome " + body.nome() + " è già in uso!");
        } else {
            Scarpa scarpa = new Scarpa(body.descrizione(), body.immagine(), body.marca(), body.nome(), body.prezzo(), taglie);
            return this.scarpeRepository.save(scarpa);
        }
    }

    // Get All Marca
    public Page<Scarpa> findMarca(@PathVariable String marca, int page, int size, String sortBy) {
        if (page > 10) page = 10;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.scarpeRepository.findByMarca(marca, pageable);
    }

    //Find by id
    public Scarpa findScarpaById(UUID scarpaId) {
        return this.scarpeRepository.findById(scarpaId).orElseThrow(() -> new NotFoundException(scarpaId));
    }
//Put Shoes

    public Scarpa modificaScarpa(UUID scarpaId, Scarpa body) throws BadRequestException {
        Scarpa scarpaFound = this.findScarpaById(scarpaId);
        scarpaFound.setImmagine((body.getImmagine()));
        scarpaFound.setNome(body.getNome());
        scarpaFound.setMarca(body.getMarca());
        scarpaFound.setPrezzo(body.getPrezzo());
        scarpaFound.setDescrizione(body.getDescrizione());

        List<Taglia> taglieEsistenti = scarpaFound.getTaglie();
        Map<UUID, Taglia> taglieMap = taglieEsistenti.stream()
                .collect(Collectors.toMap(Taglia::getId, Function.identity()));

        List<Taglia> taglieAggiornate = new ArrayList<>(taglieEsistenti);


        for (Taglia taglia : body.getTaglie()) {
            if (taglia.getId() != null && taglieMap.containsKey(taglia.getId())) {

                Taglia tagliaEsistente = taglieMap.get(taglia.getId());
                tagliaEsistente.setQuantità(taglia.getQuantità());
                tagliaEsistente.setTaglia(taglia.getTaglia());
            } else {

                Taglia nuovaTaglia = new Taglia(taglia.getQuantità(), taglia.getTaglia());
                nuovaTaglia.setScarpa(scarpaFound);
                taglieAggiornate.add(nuovaTaglia);
            }
        }
        taglieAggiornate.sort(Comparator.comparingInt(Taglia::getTaglia));

        scarpaFound.setTaglie(taglieAggiornate);
        return this.scarpeRepository.save(scarpaFound);
    }

    //Delete shoes
    public void findByIdAndRemoveShoes(UUID scarpaId) {
        Scarpa found = this.findScarpaById(scarpaId);
        this.scarpeRepository.delete(found);
    }
    //Img Upload

    public void uploadImg(MultipartFile file, UUID scarpaId) throws IOException {
        String url = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        System.out.println("URL: " + url);
        Scarpa scarpe = scarpeRepository.findById(scarpaId).orElseThrow(() -> new RuntimeException("Cliente non trovato"));
        scarpe.setImmagine(url);
        scarpeRepository.save(scarpe);
    }


    //Filtro descrizione
    public Page<Scarpa> findByDescrizione(String descrizione, int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return this.scarpeRepository.findByDescrizioneContaining(descrizione, pageable);
    }
}