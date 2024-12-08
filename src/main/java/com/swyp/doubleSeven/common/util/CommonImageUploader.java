package com.swyp.doubleSeven.common.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommonImageUploader {

    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        // Convert MultipartFile to File and upload
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
        return upload(uploadFile, dirName);
    }

    private String upload(File uploadFile, String dirName) {
        // Generate unique file name based on UUID to prevent overwriting
        String fileName = dirName + "/" + UUID.randomUUID().toString() + "_" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);

        // Remove local file after upload
        removeNewFile(uploadFile);

        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        // Upload file to S3 with public read access
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        // Delete local file after uploading to S3
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.error("파일 삭제 실패: " + targetFile.getName());
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            log.error("파일이 비어있거나 null입니다.");
            return Optional.empty();
        }

        // 파일 이름과 확장자 체크
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            log.error("파일 이름이 없습니다.");
            return Optional.empty();
        }

        // 임시 디렉토리에 파일을 생성
        File convertFile = new File(System.getProperty("java.io.tmpdir"), fileName); // 임시 디렉토리 사용
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        } else {
            log.error("임시 파일을 생성할 수 없습니다.");
            return Optional.empty();
        }
    }
}
