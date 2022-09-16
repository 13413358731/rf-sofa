package com.realfinance.sofa.common.filestore;

import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件储存接口
 */
public interface FileStore {

    String upload(String dest, String name, InputStream in) throws FileStoreException;

    /**
     * 上传文件
     * @param dest 目标目录
     * @param name 文件名
     * @param resource 文件资源
     * @return ID 用于下载
     * @throws FileStoreException
     */
    String upload(String dest, String name, Resource resource) throws FileStoreException;


    String upload(String name, Resource resource) throws FileStoreException;

    /**
     * 下载文件
     * @param id ID
     * @return
     * @throws FileStoreException
     */
    Resource download(String id) throws FileStoreException;

    /**
     * 下载文件
     * @param id ID
     * @param out 输出流
     * @throws FileStoreException
     */
    void download(String id, OutputStream out) throws FileStoreException;

    /**
     * 删除文件
     * @param id
     * @throws FileStoreException
     */
    void remove(String id) throws FileStoreException;
}
