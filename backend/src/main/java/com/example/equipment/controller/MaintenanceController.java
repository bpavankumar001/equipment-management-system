package com.example.equipment.controller;

import com.example.equipment.model.MaintenanceLog;
import com.example.equipment.service.MaintenanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MaintenanceController {
    @Autowired
    private MaintenanceService maintenanceService;

    @PostMapping("/api/maintenance")
    public ResponseEntity<?> add(@Valid @RequestBody MaintenanceLog log) {
        try {
            MaintenanceLog saved = maintenanceService.addLog(log);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/equipment/{id}/maintenance")
    public List<MaintenanceLog> history(@PathVariable Long id) {
        return maintenanceService.getHistory(id);
    }
}
