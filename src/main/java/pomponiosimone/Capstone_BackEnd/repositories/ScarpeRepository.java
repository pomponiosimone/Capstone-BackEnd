package pomponiosimone.Capstone_BackEnd.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pomponiosimone.Capstone_BackEnd.entities.Scarpa;


import java.util.Optional;
import java.util.UUID;

public interface ScarpeRepository extends JpaRepository<Scarpa, UUID> {
    Optional<Scarpa> findByNome (String nome);
    Page<Scarpa> findByMarca(String marca, Pageable pageable);
    Page<Scarpa>  findByDescrizioneContaining(String descrizione, Pageable pageable);
}
