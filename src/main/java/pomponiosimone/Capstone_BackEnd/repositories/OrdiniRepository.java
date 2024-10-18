package pomponiosimone.Capstone_BackEnd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pomponiosimone.Capstone_BackEnd.entities.Ordine;

import java.util.Optional;
import java.util.UUID;

public interface OrdiniRepository extends JpaRepository<Ordine, UUID> {
    Optional<Ordine> findById(UUID uuid);
}
