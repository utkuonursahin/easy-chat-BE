package me.utku.easychatbe.service;

import java.util.List;
import java.util.UUID;

public interface BaseService <T> {
    T getEntityById(UUID id);
    List<T> getAllEntities();
    T createEntity(T entity);
    T updateEntity(UUID id, T newEntity);
    void deleteEntity(UUID id);
}
