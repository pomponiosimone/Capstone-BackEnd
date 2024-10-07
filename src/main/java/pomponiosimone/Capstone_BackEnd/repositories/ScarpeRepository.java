package pomponiosimone.Capstone_BackEnd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pomponiosimone.Capstone_BackEnd.entities.Scarpa;

import java.util.Optional;
import java.util.UUID;

public interface ScarpeRepository extends JpaRepository<Scarpa, UUID> {
    Optional<Scarpa> findByNome (String nome);}
