package com.trendistra.trendistashop.controllers.admin;

import com.trendistra.trendistashop.entities.product.Size;
import com.trendistra.trendistashop.services.impl.product.SizeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/sizes")
@CrossOrigin
@Tag(name = "Sizes")
public class SizeController {
    @Autowired
    private SizeService sizeService;
    @PostMapping
    public ResponseEntity<Size> createSize(@RequestBody Size size) {
        return new ResponseEntity<>(sizeService.createSize(size), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Size>> getAllSizes() {
        return ResponseEntity.ok(sizeService.getAllSizes());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Size> getSizeById(@PathVariable UUID id) {
        return ResponseEntity.ok(sizeService.getSizeById(id));
    }
    public ResponseEntity<Size> updateSize(@PathVariable UUID id, @RequestBody Size updatedSize) {
        return ResponseEntity.ok(sizeService.updateSize(id, updatedSize));
    }

    // Delete a Size
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSize(@PathVariable UUID id) {
        sizeService.deleteSize(id);
        return ResponseEntity.noContent().build();
    }
}
