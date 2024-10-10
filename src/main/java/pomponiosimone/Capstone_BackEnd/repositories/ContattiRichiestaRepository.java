package pomponiosimone.Capstone_BackEnd.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import pomponiosimone.Capstone_BackEnd.entities.ContattoRichiesta;

import java.util.Optional;
import java.util.UUID;

public interface ContattiRichiestaRepository extends JpaRepository<ContattoRichiesta, UUID> {
    Optional<ContattoRichiesta> findById (UUID contattoId);
}
