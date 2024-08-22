package me.utku.easychatbe.generic;

import me.utku.easychatbe.exception.EntityNotFoundException;

import java.util.List;
import java.util.UUID;

public interface BaseService<T> {
    T getEntityById(UUID id) throws EntityNotFoundException;

    List<T> getAllEntities();

    T createEntity(T entityDto);

    T updateEntity(UUID id, T updateEntityDto) throws EntityNotFoundException;

    void deleteEntity(UUID id) throws EntityNotFoundException;

    boolean existsById(UUID id);
}
