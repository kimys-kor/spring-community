package com.community.api.service;

import com.community.api.model.ImgFile;
import com.community.api.repository.ImgFileRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ImgFileService {

    private final ImgFileRepository imgFileRepository;

    @Value("${key.postImgPath}")
    private String postImgPath;

    @Value("${key.postImgUrl}")
    private String postImgUrl;

    public ImgFile save(ImgFile imgFile) {
        return imgFileRepository.save(imgFile);
    }

    public ImgFile findByIdEquals(int id) {
        return imgFileRepository.findByIdEquals(id);
    }

    public String saveFile(MultipartFile file) {
        LocalDateTime dateTimeNow = LocalDateTime.now();
        ImgFile imgFile = new ImgFile();
        String fileNameDateTime = dateTimeNow.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        try {
            String origFileName = file.getOriginalFilename();
            String fileName = fileNameDateTime + origFileName;

            if (!postImgPath.endsWith(File.separator)) {
                postImgPath += File.separator;
            }

            File directory = new File(postImgPath);

            if (!directory.exists()) {
                boolean created = directory.mkdirs();  // Create the directory if it doesn't exist
                if (!created) {
                    throw new RuntimeException("Failed to create directory: " + postImgPath);
                }
            }

            String filePath = postImgPath + fileName;
            file.transferTo(new File(filePath));

            imgFile.setOrigFileName(origFileName);
            imgFile.setFilePath(filePath);
            imgFile.setFileName(fileName);
            imgFile.setUploadDate(dateTimeNow);
            imgFile = save(imgFile);

            return "http://localhost:8080/"+fileName;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("File saving failed", e);
        }
    }


}
