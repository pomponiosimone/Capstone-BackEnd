package pomponiosimone.Capstone_BackEnd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import pomponiosimone.Capstone_BackEnd.entities.Cliente;

import java.util.Optional;
import java.util.UUID;

public interface ClientiRepository extends JpaRepository<Cliente, UUID> {
    Optional<Cliente> findById(UUID clienteId);
    Optional<Cliente> findByEmail(String email);
    boolean existsByEmail(String email);}