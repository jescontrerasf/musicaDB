package com.example.musicaDB.Repository;

import com.example.musicaDB.Model.Artista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface artistaRepository extends JpaRepository<Artista, Long> {

}
