package com.devsuperior.demo.controllers;

import com.devsuperior.demo.dto.CityDTO;
import com.devsuperior.demo.service.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/cities")
public class CityController {

    private CityService service;

    public CityController(CityService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CityDTO>> findAll() throws Exception {
        List<CityDTO> result = service.findAll();

        return ResponseEntity.status(OK).body(result);
    }

    @PostMapping
    public ResponseEntity<CityDTO> insert(@RequestBody CityDTO dto) throws Exception {
        dto = service.save(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
