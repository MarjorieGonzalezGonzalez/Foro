package com.marjorie.Foro.controller;


import com.marjorie.Foro.model.Topico;
import com.marjorie.Foro.repository.TopicoRepository;
import com.marjorie.Foro.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    private TopicoService topicoService;

    @GetMapping
    public List<Topico> getAllTopicos() {
        return topicoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topico> getTopicoById(@PathVariable(value = "id") Long id) {
        Optional<Topico> topico = topicoService.findById(id);
        return topico.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Topico createTopico(@Valid @RequestBody Topico topico) {
        return topicoService.save(topico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topico> updateTopico(@PathVariable(value = "id") Long id, @Valid @RequestBody Topico topicoDetails) {
        Topico updatedTopico = topicoService.update(id, topicoDetails);
        return updatedTopico != null ? ResponseEntity.ok(updatedTopico) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopico(@PathVariable(value = "id") Long id) {
        topicoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
