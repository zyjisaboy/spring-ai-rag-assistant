package com.zyjisaboy.springairagassistant.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class DocumentService {

    private final Path uploadDir;

    public DocumentService(@Value("${app.upload-dir:/Users/zhaoyongjie/work/temp/spring-ai-rag-assistant}") String uploadDir) {
        this.uploadDir = Paths.get(uploadDir).toAbsolutePath().normalize();
        // 启动时创建上传目录
        try {
            Files.createDirectories(this.uploadDir);
        } catch (IOException e) {
            throw new RuntimeException("无法创建上传目录: " + this.uploadDir, e);
        }
    }

    /**
     * 保存上传文件到本地目录
     * @return 保存后的文件路径
     */
    public Path saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("上传文件不能为空");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isBlank()) {
            throw new IllegalArgumentException("文件名不能为空");
        }

        // 防止路径穿越：只取文件名部分，去掉目录和..
        String safeFilename = Paths.get(originalFilename).getFileName().toString();
        if (safeFilename.contains("..") || safeFilename.isBlank()) {
            throw new IllegalArgumentException("文件名不合法");
        }

        // 校验文件类型
        String lowerName = safeFilename.toLowerCase();
        if (!lowerName.endsWith(".pdf") && !lowerName.endsWith(".txt")) {
            throw new IllegalArgumentException("目前仅支持 PDF 和 TXT 文件");
        }

        Path targetPath = uploadDir.resolve(safeFilename);

        // 如果文件已存在，加时间戳避免覆盖
        if (Files.exists(targetPath)) {
            String nameWithoutExt = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
            String ext = originalFilename.substring(originalFilename.lastIndexOf('.'));
            targetPath = uploadDir.resolve(nameWithoutExt + "_" + System.currentTimeMillis() + ext);
        }

        Files.copy(file.getInputStream(), targetPath);
        return targetPath;
    }
}
