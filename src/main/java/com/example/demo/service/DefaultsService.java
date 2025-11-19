package com.example.demo.service;

import com.example.demo.model.Defaults;
import com.example.demo.repository.DefaultsRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DefaultsService {

    private final DefaultsRepo defaultsRepo;

    public DefaultsService(DefaultsRepo defaultsRepo) {
        this.defaultsRepo = defaultsRepo;
    }

    @Transactional(readOnly = true)
    public Optional<Defaults> getDefaultsById(Long id) {
        return defaultsRepo.findById(id);
    }

    @Transactional
    public Defaults saveDefaults(Defaults defaults) {
        return defaultsRepo.save(defaults);
    }

    @Transactional(readOnly = true)
    public Iterable<Defaults> getAllDefaults() {
        return defaultsRepo.findAll();
    }

    // Example convenience for “single row defaults” with id = 1
    @Transactional(readOnly = true)
    public Defaults getGlobalDefaults() {
        return defaultsRepo.findById(1L)
                .orElseThrow(() -> new IllegalStateException("Defaults row with id=1 not found"));
    }
}
