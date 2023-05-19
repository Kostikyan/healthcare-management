package com.healthcaremanagement.controller;

import com.healthcaremanagement.security.CurrentUser;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
public class InputEndpoint {
    @Value("${healthcare.management.upload.image.path}")
    private String profilePictureUploadPath;

    @GetMapping("/")
    public String mainPage(ModelMap modelMap,
                           @AuthenticationPrincipal CurrentUser currentUser) {

        if(currentUser != null){
            modelMap.addAttribute("user", currentUser.getUser());
        }

        return "index";
    }

    @GetMapping(value = "/getProfilePic",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("imageName") String imageName) throws IOException {
        File file = new File(profilePictureUploadPath + imageName);
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            return IOUtils.toByteArray(fis);
        }
        return null;
    }
}
