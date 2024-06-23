package me.utku.easychatbe.controller;

import me.utku.easychatbe.dto.GenericResponse;
import me.utku.easychatbe.model.BaseModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface BaseController <T> {
    ResponseEntity<GenericResponse<List<T>>> getAll();
    ResponseEntity<GenericResponse<T>> getById(@PathVariable UUID id);
    ResponseEntity<GenericResponse<T>> create(@RequestBody BaseModel data);
    ResponseEntity<GenericResponse<T>> update(@PathVariable UUID id, @RequestBody BaseModel updateData);
    ResponseEntity<GenericResponse<Boolean>> delete(@PathVariable UUID id);
}
