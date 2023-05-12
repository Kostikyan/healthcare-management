package com.healthcaremanagement.controller;

import com.healthcaremanagement.entity.Appointment;
import com.healthcaremanagement.entity.Doctor;
import com.healthcaremanagement.entity.Patient;
import com.healthcaremanagement.repository.AppointmentRepository;
import com.healthcaremanagement.repository.DoctorRepository;
import com.healthcaremanagement.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;

    @GetMapping
    public String appointmentPage(ModelMap modelMap){
        List<Appointment> all = appointmentRepository.findAll();
        modelMap.addAttribute("appointments", all);
        return "appointments";
    }

    @GetMapping("/addAppointment")
    public String addAppointmentPage(ModelMap modelMap){
        List<Doctor> doctors = doctorRepository.findAll();
        List<Patient> patients = patientRepository.findAll();
        modelMap.addAttribute("doctors", doctors);
        modelMap.addAttribute("patients", patients);
        return "addAppointment";
    }

    @PostMapping("/addAppointment")
    public String addAppointment(@ModelAttribute Appointment appointment,
                                 @RequestParam("patient.id") int patientId,
                                 @RequestParam("doctor.id") int doctorId){
        if(appointment == null){
            return "redirect:/appointments";
        }
        appointment.setPatientId(patientId);
        appointment.setDoctorId(doctorId);
        appointmentRepository.save(appointment);
        return "redirect:/appointments";
    }

    @GetMapping("/remove")
    public String removeAppointment(@RequestParam("id") int id){
        appointmentRepository.deleteById(id);
        return "redirect:/appointments";
    }
}
