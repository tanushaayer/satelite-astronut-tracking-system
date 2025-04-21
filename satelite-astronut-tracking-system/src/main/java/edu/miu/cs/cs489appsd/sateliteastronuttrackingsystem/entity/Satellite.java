package edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.entity;

import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.common.OrbitType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "satellites")
@NoArgsConstructor
@Data
public class Satellite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Satellite_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    //must be a date only in the past
    @Column(nullable = false)
    private LocalDate launchDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrbitType orbitType;

    private boolean decommissioned;

    @ManyToMany(mappedBy = "satellites")
    private List<Astronaut> astronauts;
}
