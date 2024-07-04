package me.utku.easychatbe.controller;

import me.utku.easychatbe.dto.GenericResponse;
import me.utku.easychatbe.service.BaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public abstract class CrudController<T> implements BaseController<T> {
    private final BaseService<T> entityService;

    protected CrudController(BaseService<T> entityService) {
        this.entityService = entityService;
    }

    @Override
    @GetMapping
    public ResponseEntity<GenericResponse<List<T>>> getAll() {
        List<T> entityList = entityService.getAllEntities();
        return new GenericResponse<>(HttpStatus.OK.value(), "Entities fetched successfully", entityList).toResponseEntity();
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<T>> getById(@PathVariable UUID id) {
        T entity = entityService.getEntityById(id);
        return new GenericResponse<>(HttpStatus.OK.value(), "Entity fetched successfully", entity).toResponseEntity();
    }

    @Override
    @PostMapping
    public ResponseEntity<GenericResponse<T>> create(@RequestBody T data) {
        T entity = entityService.createEntity(data);
        return new GenericResponse<>(HttpStatus.CREATED.value(), "Entity created successfully", entity).toResponseEntity();
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<GenericResponse<T>> update(@PathVariable UUID id, @RequestBody T updateData) {
        T entity = entityService.updateEntity(id, updateData);
        return new GenericResponse<>(HttpStatus.OK.value(), "Entity updated successfully", entity).toResponseEntity();
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Boolean>> delete(@PathVariable UUID id) {
        entityService.deleteEntity(id);
        return new GenericResponse<>(HttpStatus.OK.value(), "Entity deleted successfully", true).toResponseEntity();
    }
}
