package com.example.equipment.service;

import com.example.equipment.model.Equipment;
import com.example.equipment.model.MaintenanceLog;
import com.example.equipment.repository.EquipmentRepository;
import com.example.equipment.repository.MaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceService {
    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    public List<MaintenanceLog> getHistory(Long equipmentId) {
        return maintenanceRepository.findByEquipmentId(equipmentId);
    }

    @Transactional
    public MaintenanceLog addLog(MaintenanceLog log) {
        // business rules: when maintenance record added, equipment status->Active and lastCleanedDate->maintenanceDate
        Long eqId = log.getEquipment().getId();
        Optional<Equipment> optional = equipmentRepository.findById(eqId);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Equipment not found");
        }
        Equipment eq = optional.get();
        eq.setStatus("Active");
        eq.setLastCleanedDate(log.getMaintenanceDate());
        equipmentRepository.save(eq);
        return maintenanceRepository.save(log);
    }
}
