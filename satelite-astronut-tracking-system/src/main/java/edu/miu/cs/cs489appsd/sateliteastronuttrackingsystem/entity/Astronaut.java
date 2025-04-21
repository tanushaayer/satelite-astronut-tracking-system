package edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "astronauts")
@NoArgsConstructor
@Data
public class Astronaut {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "astronaut_id")
    private Long id;

    @Column(nullable = false, length = 20)
    private String firstName;

    @Column(nullable = false, length = 20)
    private String lastName;

   @Column(nullable = false)
    private int experienceYears;

    @ManyToMany
    @JoinTable(
            name = "astronaut_satellite",
            joinColumns = @JoinColumn(name = "astronaut_id"),
            inverseJoinColumns = @JoinColumn(name = "satellite_id")
    )
    private List<Satellite> satellites;
}
