package me.utku.easychatbe.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

/**
 * This abstract class is the base class for all entities in the project.
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Instant createdAt = Instant.now();
    private boolean isVisible = true;
}
