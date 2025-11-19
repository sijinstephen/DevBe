package com.example.demo.controller;

import com.example.demo.model.Defaults;
import com.example.demo.service.DefaultsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/defaults")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")
public class DefaultsController {

    private final DefaultsService defaultsService;

    public DefaultsController(DefaultsService defaultsService) {
        this.defaultsService = defaultsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Defaults> getById(@PathVariable Long id) {
        return defaultsService.getDefaultsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Defaults> save(@RequestBody Defaults defaults) {
        Defaults saved = defaultsService.saveDefaults(defaults);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/global")
    public ResponseEntity<Defaults> getGlobalDefaults() {
        Defaults defaults = defaultsService.getGlobalDefaults();
        return ResponseEntity.ok(defaults);
    }
}
