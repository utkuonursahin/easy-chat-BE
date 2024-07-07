package me.utku.easychatbe.service;

import me.utku.easychatbe.exception.EntityNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * Base service interface for CRUD operations
 * @param <I> Input Model
 * @param <R> Response DTO
 */
public interface BaseService <I,R> {
    R getEntityById(UUID id) throws EntityNotFoundException;
    List<R> getAllEntities();
    R createEntity(I entity);
    R updateEntity(UUID id, I updateEntity) throws EntityNotFoundException;
    void deleteEntity(UUID id) throws EntityNotFoundException;
}
