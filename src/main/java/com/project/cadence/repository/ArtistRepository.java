package com.project.cadence.repository;

import com.project.cadence.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, String> {

    Optional<Artist> findByName(String name);

    boolean existsByName(String name);
}
