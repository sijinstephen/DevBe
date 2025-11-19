package com.example.demo.repository;

import com.example.demo.model.Defaults;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DefaultsRepo extends CrudRepository<Defaults, Long> {

    // If you know id = 1 is your singleton defaults row:
    default Optional<Defaults> getSingleton() {
        return findById(1L);
    }
}
