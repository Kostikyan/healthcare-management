package com.healthcaremanagement.repository;

import com.healthcaremanagement.entity.Appointment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    @Transactional
    void deleteAllByDoctorId(@Param("doctorId") int doctorId);

    @Transactional
    void deleteAllByPatientId(@Param("patientId") int patientId);
}