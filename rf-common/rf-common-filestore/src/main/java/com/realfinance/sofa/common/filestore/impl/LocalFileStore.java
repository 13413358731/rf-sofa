package com.realfinance.sofa.common.filestore.impl;

import com.realfinance.sofa.common.filestore.FileStore;
import com.realfinance.sofa.common.filestore.FileStoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

/**
 * 文件存储到本地的实现
 */
public class LocalFileStore implements FileStore {

    private static final Logger log = LoggerFactory.getLogger(LocalFileStore.class);

    private String rootPath2 = "D:\\桌面";

    private String rootPath = Path.of(System.getProperty("java.io.tmpdir"),"rffile").toString();
//    private String rootPath = Path.of("C:\\ProgramData","rffile").toString();

    @Override
    public String upload(String dest, String name, InputStream in) throws FileStoreException {
        Objects.requireNonNull(dest);
        Objects.requireNonNull(name);
        Objects.requireNonNull(in);
        try {
            Path dir = Path.of(rootPath, dest);
            if (!Files.exists(dir)) {
                Files.createDirectory(dir);
            }
            Path tempFile = Files.createTempFile(dir,name, ".temp");
            Files.copy(in,tempFile, StandardCopyOption.REPLACE_EXISTING);
            return Path.of(dest,tempFile.getFileName().toString()).toString();
        } catch (IOException e) {
            log.error("上传文件失败", e);
            throw new FileStoreException("上传文件失败");
        }
    }

    @Override
    public String upload(String dest, String name, Resource resource) throws FileStoreException {
        Objects.requireNonNull(dest);
        Objects.requireNonNull(name);
        Objects.requireNonNull(resource);
        try {
            Path dir = Path.of(rootPath2, dest);
            if (!Files.exists(dir)) {
                Files.createDirectory(dir);
            }
            Path tempFile = Files.createTempFile(dir,name, ".temp");
            try (InputStream in = resource.getInputStream()) {
                Files.copy(in,tempFile, StandardCopyOption.REPLACE_EXISTING);
            }
            return Path.of(dest,tempFile.getFileName().toString()).toString();
        } catch (IOException e) {
            log.error("上传文件失败", e);
            throw new FileStoreException("上传文件失败");
        }
    }

    @Override
    public String upload(String name, Resource resource) throws FileStoreException {
        Objects.requireNonNull(name);
        Objects.requireNonNull(resource);
        try {
            Path dir = Path.of(rootPath2);
            if (!Files.exists(dir)) {
                Files.createDirectory(dir);
            }
            Path tempFile = Files.createTempFile(dir,name, ".temp");
            try (InputStream in = resource.getInputStream()) {
                Files.copy(in,tempFile, StandardCopyOption.REPLACE_EXISTING);
            }
            return Path.of(tempFile.getFileName().toString()).toString();
        } catch (IOException e) {
            log.error("上传文件失败", e);
            throw new FileStoreException("上传文件失败");
        }
    }

    @Override
    public Resource download(String id) throws FileStoreException {
        Objects.requireNonNull(id);
        Path of = Path.of(rootPath,id);
        PathResource pathResource = new PathResource(of);
        if (!pathResource.exists()) {
            throw new FileStoreException("下载文件失败");
        }
        return pathResource;
    }

    @Override
    public void download(String id, OutputStream out) throws FileStoreException {
        Objects.requireNonNull(id);
        Objects.requireNonNull(out);
        Path of = Path.of(rootPath,id);
        try {
            Files.copy(of,out);
        } catch (IOException e) {
            log.error("文件下载失败",e);
            throw new FileStoreException("文件下载失败");
        }
    }

    @Override
    public void remove(String id) throws FileStoreException {
        Objects.requireNonNull(id);
        Path of = Path.of(rootPath,id);
        try {
            Files.deleteIfExists(of);
        } catch (IOException e) {
            log.error("文件删除失败",e);
            throw new FileStoreException("文件删除失败");
        }
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public static void main(String[] args) {
        LocalFileStore localFileStore = new LocalFileStore();
        String testacc1 = localFileStore.upload("testacc1", "test.jpg", new FileSystemResource("C:\\Users\\slim\\Pictures\\Saved Pictures\\src=http___a0.att.hudong.com_30_29_01300000201438121627296084016.jpg&refer=http___a0.att.hudong.jfif"));


    }
}
