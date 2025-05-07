package com.bikash.person.service;

import com.bikash.person.exceptions.DatabaseOperationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageUploaderService {


    @Value("${upload.dir}")
    private  String uploadDirectory;

    public  String uploadImage(MultipartFile multipartFile) {
        String imageUrl = null;

        if(!multipartFile.isEmpty())
        {
            String fileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            File dir = new File(uploadDirectory);
            if (!dir.exists()) dir.mkdirs();
            Path filePath = Paths.get(uploadDirectory, fileName);
            try {
                Files.write(filePath,multipartFile.getBytes());
                imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/uploads/images/").path(fileName).toUriString();
            }catch (Exception e)
            {
                throw new DatabaseOperationException("Failed to save Image");
            }
        }
        return  imageUrl;
    }



}
