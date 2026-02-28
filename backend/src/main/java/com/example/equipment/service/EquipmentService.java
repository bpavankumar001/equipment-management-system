package com.example.equipment.service;

import com.example.equipment.model.Equipment;
import com.example.equipment.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService {
    @Autowired
    private EquipmentRepository equipmentRepository;

    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
    }

    public Optional<Equipment> findById(Long id) {
        return equipmentRepository.findById(id);
    }

    @Transactional
    public Equipment save(Equipment eq) {
        // status constraint rule: cannot be Active if lastCleanedDate older than 30 days
        if ("Active".equalsIgnoreCase(eq.getStatus()) && eq.getLastCleanedDate() != null) {
            long days = ChronoUnit.DAYS.between(eq.getLastCleanedDate(), LocalDate.now());
            if (days > 30) {
                throw new IllegalArgumentException("Cannot mark equipment Active when last cleaned more than 30 days ago");
            }
        }
        return equipmentRepository.save(eq);
    }

    @Transactional
    public void delete(Long id) {
        equipmentRepository.deleteById(id);
    }
}
