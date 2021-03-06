package de.cloud.fundamentals.distributor.persistence.repo;

import de.cloud.fundamentals.distributor.persistence.domain.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    Optional<ServiceEntity> findByCommand(String command);
}
