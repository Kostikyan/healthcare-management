package com.healthcaremanagement.controller;

import com.healthcaremanagement.entity.Doctor;
import com.healthcaremanagement.repository.AppointmentRepository;
import com.healthcaremanagement.repository.DoctorRepository;
import com.healthcaremanagement.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/doctors")
public class DoctorsController {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Value("${healthcare.management.upload.image.path}")
    private String profilePictureUploadPath;

    @GetMapping
    public String doctorsPage(ModelMap modelMap){
        List<Doctor> all = doctorRepository.findAll();
        modelMap.addAttribute("doctors", all);
        return "doctors";
    }

    @GetMapping("/add")
    public String addDoctorPage(){
        return "addDoctor";
    }

    @PostMapping("/add")
    public String addDoctor(@ModelAttribute Doctor doctor, @RequestParam("profileImage") MultipartFile multipartFile) throws IOException {
        if(multipartFile != null && !multipartFile.isEmpty()){
            String fileName = System.nanoTime() + "_" + multipartFile.getOriginalFilename();
            File file = new File(profilePictureUploadPath + fileName);
            multipartFile.transferTo(file);
            doctor.setProfilePic(fileName);
        }
        doctorRepository.save(doctor);
        return "redirect:/doctors";
    }

    @GetMapping("/remove")
    public String removeDoctor(@RequestParam("id") int id){

        // delete added profile picture
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if(doctor.isPresent()){
            Doctor get = doctor.get();
            String profilePic = get.getProfilePic();
            File file = new File(profilePictureUploadPath + profilePic);
            if(file.exists()) {
                file.delete();
            }
        }

        // delete all doctor appointments
        appointmentRepository.deleteAllByDoctorId(id);

        // delete doctor
        doctorRepository.deleteById(id);
        return "redirect:/doctors";
    }
}
