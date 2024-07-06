package me.utku.easychatbe.controller;

import me.utku.easychatbe.dto.GenericResponse;
import me.utku.easychatbe.service.BaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Base controller class for CRUD implementations
 * @param <I> Input Model
 * @param <R> Response DTO
 */
@RestController
public abstract class CrudController<I,R> implements BaseController<I,R> {
    protected final BaseService<I,R> entityService;

    protected CrudController(BaseService<I,R> entityService) {
        this.entityService = entityService;
    }

    @Override
    @GetMapping
    public ResponseEntity<GenericResponse<List<R>>> getAll() {
        List<R> entityList = entityService.getAllEntities();
        return new GenericResponse<>(HttpStatus.OK.value(), "Entities fetched successfully", entityList).toResponseEntity();
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<R>> getById(@PathVariable UUID id) {
        R entity = entityService.getEntityById(id);
        return new GenericResponse<>(HttpStatus.OK.value(), "Entity fetched successfully", entity).toResponseEntity();
    }

    @Override
    @PostMapping
    public ResponseEntity<GenericResponse<R>> create(@RequestBody I data) {
        R entity = entityService.createEntity(data);
        return new GenericResponse<>(HttpStatus.CREATED.value(), "Entity created successfully", entity).toResponseEntity();
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<GenericResponse<R>> update(@PathVariable UUID id, @RequestBody I updateData) {
        R entity = entityService.updateEntity(id, updateData);
        return new GenericResponse<>(HttpStatus.OK.value(), "Entity updated successfully", entity).toResponseEntity();
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Boolean>> delete(@PathVariable UUID id) {
        entityService.deleteEntity(id);
        return new GenericResponse<>(HttpStatus.OK.value(), "Entity deleted successfully", true).toResponseEntity();
    }
}
