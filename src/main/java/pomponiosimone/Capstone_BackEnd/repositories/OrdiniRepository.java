package pomponiosimone.Capstone_BackEnd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pomponiosimone.Capstone_BackEnd.entities.Ordine;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrdiniRepository extends JpaRepository<Ordine, UUID> {
    Optional<Ordine> findById(UUID uuid);
    @Query("SELECT o FROM Ordine o WHERE FUNCTION('DATE', o.dataOrdine) = :dataOrdine")
    Optional<List<Ordine>> findByDataOrdine(@Param("dataOrdine") Date dataOrdine);
}
