package me.utku.easychatbe.controller;

import me.utku.easychatbe.dto.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

/**
 * Base controller interface for CRUD operations
 * @param <I> Input Model
 * @param <R> Response DTO
 */
public interface BaseController <I,R> {
    ResponseEntity<GenericResponse<List<R>>> getAll();
    ResponseEntity<GenericResponse<R>> getById(@PathVariable UUID id);
    ResponseEntity<GenericResponse<R>> create(@RequestBody I data);
    ResponseEntity<GenericResponse<R>> update(@PathVariable UUID id, @RequestBody I updateData);
    ResponseEntity<GenericResponse<Boolean>> delete(@PathVariable UUID id);
}
