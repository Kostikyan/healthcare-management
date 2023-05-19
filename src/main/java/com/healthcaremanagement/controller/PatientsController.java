package com.healthcaremanagement.controller;

import com.healthcaremanagement.entity.Patient;
import com.healthcaremanagement.repository.AppointmentRepository;
import com.healthcaremanagement.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/patients")
public class PatientsController {


    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping
    public String patientsPage(ModelMap modelMap) {
        List<Patient> all = patientRepository.findAll();
        modelMap.addAttribute("patients", all);
        return "patients";
    }

    @GetMapping("/add")
    public String addPatientPage() {
        return "addPatient";
    }

    @PostMapping("/add")
    public String addPatient(@ModelAttribute Patient patient) throws ParseException {
        if (patient == null) {
            return "redirect:/patients";
        }

        patientRepository.save(patient);
        return "redirect:/patients";
    }

    @GetMapping("/remove")
    public String removePatient(@RequestParam("id") int id){
        appointmentRepository.deleteAllByPatientId(id);
        patientRepository.deleteById(id);
        return "redirect:/patients";
    }
}
