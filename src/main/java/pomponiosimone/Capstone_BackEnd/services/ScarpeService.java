package pomponiosimone.Capstone_BackEnd.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
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
import pomponiosimone.Capstone_BackEnd.payloads.ScarpaDTO;
import pomponiosimone.Capstone_BackEnd.repositories.ScarpeRepository;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ScarpeService {
    @Autowired
    private ScarpeRepository scarpeRepository;
    @Autowired
    private Cloudinary cloudinaryUploader;

    public Page<Scarpa> findAll(int page, int size, String sortBy) {
        if (page > 10) page = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.scarpeRepository.findAll(pageable);
    }
    //Save
    public Scarpa saveSneakers (ScarpaDTO body) {
        List<Taglia> taglie = body.taglie().stream()
                .map(tagliaDTO -> new Taglia(tagliaDTO.quantita(), tagliaDTO.taglia()))
                .collect(Collectors.toList());
        if (this.scarpeRepository.findByNome(body.nome()).isPresent()) {
            throw new BadRequestException("nome" + body.nome() + "già in uso!!");

        } else {
            Scarpa scarpa = new Scarpa(body.descrizione(), body.immagine(), body.marca(), body.nome(), body.prezzo(), taglie);
            return this.scarpeRepository.save(scarpa);
        }
    }

    // Get All Marca
     public Page<Scarpa> findMarca(@PathVariable String marca, int page, int size, String sortBy) {
         if (page > 10) page = 10;

         Pageable pageable = PageRequest.of( page, size, Sort.by(sortBy));
         return this.scarpeRepository.findByMarca(marca, pageable);
     }
            //Img Upload

            public void uploadImg (MultipartFile file, UUID scarpaId) throws IOException {
                String url = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
                System.out.println("URL: " + url);
                Scarpa scarpe = scarpeRepository.findById(scarpaId).orElseThrow(() -> new RuntimeException("Cliente non trovato"));
                scarpe.setImmagine(url);
                scarpeRepository.save(scarpe);
            }

        }