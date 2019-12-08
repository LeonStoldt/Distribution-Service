package de.cloud.fundamentals.distributor.rest;

import de.cloud.fundamentals.distributor.persistence.domain.ServiceEntity;
import de.cloud.fundamentals.distributor.persistence.repo.ServiceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ServiceController {

    private final ServiceRepository serviceRepository;

    public ServiceController(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @PostMapping("/service")
    public ResponseEntity addService(@RequestBody ServiceEntity serviceEntity) {
        serviceRepository.save(serviceEntity);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/service")
    public ResponseEntity updateService(@RequestBody ServiceEntity serviceEntity) {
        Optional<ServiceEntity> optionalService = serviceRepository.findByCommand(serviceEntity.getCommand());
        if (optionalService.isPresent()) {
            ServiceEntity oldServiceEntity = optionalService.get();
            oldServiceEntity.setUrl(serviceEntity.getUrl());
            serviceRepository.save(oldServiceEntity);
            return new ResponseEntity<>(oldServiceEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/service")
    public ResponseEntity deleteService(@RequestBody ServiceEntity serviceEntity) {
        if (serviceRepository.findById(serviceEntity.getId()).isPresent()) {
            serviceRepository.delete(serviceEntity);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
