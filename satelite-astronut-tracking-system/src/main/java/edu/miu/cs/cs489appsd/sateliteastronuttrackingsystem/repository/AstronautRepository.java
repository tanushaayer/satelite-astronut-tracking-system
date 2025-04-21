package edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.repository;

import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.entity.Astronaut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AstronautRepository extends JpaRepository<Astronaut, Long> {
    Page<Astronaut> findAll(Pageable pageable);
}
