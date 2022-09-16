package com.realfinance.sofa.common.filestore.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.ftp.Ftp;
import com.realfinance.sofa.common.filestore.FileStore;
import com.realfinance.sofa.common.filestore.FileStoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FastByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Objects;

/**
 * 文件存储到FTP实现
 * 使用需要引入 commons-net和hutool-extra
 */
public class FtpFileStore implements FileStore {

    private static final Logger log = LoggerFactory.getLogger(FtpFileStore.class);

    private String host;
    private int port;
    private String username;
    private String password;

    @Override
    public String upload(String dest, String name, InputStream in) throws FileStoreException {
        Objects.requireNonNull(dest);
        Objects.requireNonNull(name);
        Objects.requireNonNull(in);
        try (Ftp ftp = getFtp()) {
            String prefix = FileUtil.getPrefix(name);
            String suffix = FileUtil.getSuffix(name);
            String fileName = prefix + System.currentTimeMillis() + "." + suffix;
            boolean upload = ftp.upload(dest, fileName, in);
            if (!upload) {
                throw new FileStoreException("文件上传失败");
            }
            return Path.of(dest,fileName).toString();
        } catch (IOException e) {
            log.error("文件上传失败",e);
            throw new FileStoreException("文件上传失败");
        }
    }

    @Override
    public String upload(String dest, String name, Resource resource) throws FileStoreException {
        Objects.requireNonNull(dest);
        Objects.requireNonNull(name);
        Objects.requireNonNull(resource);
        try (Ftp ftp = getFtp()) {
            String prefix = FileUtil.getPrefix(name);
            String suffix = FileUtil.getSuffix(name);
            String fileName = prefix + System.currentTimeMillis() + "." + suffix;
            boolean upload = ftp.upload(dest, fileName, resource.getInputStream());
            if (!upload) {
                throw new FileStoreException("文件上传失败");
            }
            return Path.of(dest,fileName).toString();
        } catch (IOException e) {
            log.error("文件上传失败",e);
            throw new FileStoreException("文件上传失败");
        }
    }

    @Override
    public String upload(String name, Resource resource) throws FileStoreException {
        return null;
    }

    @Override
    public Resource download(String id) throws FileStoreException {
        Objects.requireNonNull(id);
        String fileName = FileUtil.getName(id);
        String dir = StrUtil.removeSuffix(id, fileName);
        try (Ftp ftp = getFtp()) {
            FastByteArrayOutputStream out = new FastByteArrayOutputStream();
            ftp.download(dir,fileName,out);
            return new ByteArrayResource(out.toByteArray());
        } catch (IOException e) {
            log.error("文件下载失败",e);
            throw new FileStoreException("文件下载失败");
        }
    }

    @Override
    public void download(String id, OutputStream out) throws FileStoreException {
        Objects.requireNonNull(id);
        Objects.requireNonNull(out);
        String fileName = FileUtil.getName(id);
        String dir = StrUtil.removeSuffix(id, fileName);
        try (Ftp ftp = getFtp()) {
            ftp.download(dir,fileName,out);
        } catch (IOException e) {
            log.error("文件下载失败",e);
            throw new FileStoreException("文件下载失败");
        }
    }

    @Override
    public void remove(String id) throws FileStoreException {
        Objects.requireNonNull(id);
        try (Ftp ftp = getFtp()) {
            if (ftp.existFile(id)) {
                ftp.delFile(id);
            }
        } catch (IOException e) {
            log.error("文件删除失败",e);
            throw new FileStoreException("文件删除失败");
        }
    }

    private Ftp getFtp() {
        return new Ftp(host,port,username,password);
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
