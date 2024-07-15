package com.marjorie.Foro.repository;
import com.marjorie.Foro.model.Topico;
import com.marjorie.Foro.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    List<Topico> findByAutor(Usuario autor);
}

