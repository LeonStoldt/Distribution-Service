package de.cloud.fundamentals.distributor.persistence.repo;

import de.cloud.fundamentals.distributor.bo.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    Optional<Service> findByCommand(String command);
}
