package me.utku.easychatbe.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

/**
 * This abstract class is the base class for all entities in the project.
 */
@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @CreationTimestamp
    private Instant createdAt;
    private boolean isVisible = true;
}
