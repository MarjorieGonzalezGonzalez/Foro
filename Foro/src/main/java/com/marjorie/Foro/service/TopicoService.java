package com.marjorie.Foro.service;

import com.marjorie.Foro.model.Topico;
import com.marjorie.Foro.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TopicoService {
    @Autowired
    private TopicoRepository topicoRepository;

    public List<Topico> findAll() {
        return topicoRepository.findAll();
    }

    public Optional<Topico> findById(Long id) {
        return topicoRepository.findById(id);
    }

    public Topico save(Topico topico) {
        return topicoRepository.save(topico);
    }

    public Topico update(Long id, Topico topicoDetails) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if (optionalTopico.isPresent()) {
            Topico topico = optionalTopico.get();
            topico.setTitulo(topicoDetails.getTitulo());
            topico.setMensaje(topicoDetails.getMensaje());
            return topicoRepository.save(topico);
        }
        return null;
    }

    public void deleteById(Long id) {
        topicoRepository.deleteById(id);
    }
}
