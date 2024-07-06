package me.utku.easychatbe.service;

import java.util.List;
import java.util.UUID;

/**
 * Base service interface for CRUD operations
 * @param <I> Input Model
 * @param <R> Response DTO
 */
public interface BaseService <I,R> {
    R getEntityById(UUID id);
    List<R> getAllEntities();
    R createEntity(I entity);
    R updateEntity(UUID id, I updateEntity);
    void deleteEntity(UUID id);
}
