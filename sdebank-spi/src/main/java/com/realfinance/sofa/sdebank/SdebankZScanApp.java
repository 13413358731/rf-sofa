package com.realfinance.sofa.sdebank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.realfinance.sofa.common.filestore.FileStore;
import com.realfinance.sofa.common.filestore.FileStoreException;
import com.realfinance.sofa.common.ocr.OcrException;
import com.realfinance.sofa.common.ocr.OcrService;
import com.realfinance.sofa.common.ocr.model.BusinessLicense;
import com.realfinance.sofa.common.ocr.model.IdCard;
import com.tchzt.base.Constant;
import com.tchzt.main.ClientMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * 顺德农商行影像平台接口
 * 实现了文件存储和OCR识别
 */
public class SdebankZScanApp implements FileStore, OcrService {

    private static final Logger log = LoggerFactory.getLogger(SdebankZScanApp.class);

    private final ObjectMapper objectMapper;

    private String charset = "GBK";

    /**
     * 环境码 0：生产 1：测试 2：阿里云生产 3：阿里云测试 4：阿里云预生效
     */
    private String operMode = "3";

    /**
     * 业务系统号 如:支票影像系统： BILL
     */
    private String busCode = "BILL";

    private String busType = "001001001";

    private String orgCode = "0102095";
    private String operCode = "0102095";

    /**
     * 文档版式 如:999000
     */
    private String paperType = "999000";

    /**
     * 是否共享 0：共享1：非共享
     */
    private String isShared = "1";

    /**
     * 临时文件根目录
     */
    private String tempRootPath = Path.of(System.getProperty("java.io.tmpdir"),"rffile").toString();

    public SdebankZScanApp() {
        this.objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    @Override
    public String upload(String dest, String name, InputStream in) throws FileStoreException {
        Objects.requireNonNull(dest);
        Objects.requireNonNull(name);
        Objects.requireNonNull(in);
        Path tempFile = createTempFile(name,in);
        try {
            String xml = getUploadXml(dest,name,tempFile.toString(),null);
            String res = ClientMethod.commonService(xml);
            Document doc = readXml(res);
            checkRstCode(doc);
            String batchId = Optional.of(doc)
                    .map(Document::getRootElement)
                    .map(e -> e.element("File"))
                    .map(e -> e.elementText("BatchId"))
                    .orElseThrow(() -> new FileStoreException("上传文件失败"));
            return batchId;
        } catch (FileStoreException e) {
            throw e;
        } catch (Exception e) {
            log.error("上传文件失败",e);
            throw new FileStoreException(e);
        } finally {
            deleteFile(tempFile);
        }
    }

    @Override
    public String upload(String dest, String name, Resource resource) throws FileStoreException {
        Objects.requireNonNull(dest);
        Objects.requireNonNull(name);
        Objects.requireNonNull(resource);
        try {
            return upload(dest, name, resource.getInputStream());
        } catch (IOException e) {
            throw new FileStoreException(e);
        }
    }

    @Override
    public String upload(String name, Resource resource) throws FileStoreException {
        return null;
    }

    @Override
    public Resource download(String id) throws FileStoreException {
        Objects.requireNonNull(id);
        Path tempPath = null;
        try {
            String xml = getDownloadXml(id);
            String res = ClientMethod.commonService(xml);
            Document doc = readXml(res);
            checkRstCode(doc);
            tempPath = Optional.of(doc)
                    .map(Document::getRootElement)
                    .map(e -> e.element("File"))
                    .map(file -> Path.of(tempRootPath,
                            file.elementText("BatchId"),
                            file.elementText("CurrentLastFolderPath"),
                            file.elementText("FileSelfId")))
                    .orElseThrow(() -> new FileStoreException("下载文件失败"));
            return new ByteArrayResource(Files.readAllBytes(tempPath));
        } catch (FileStoreException e) {
            throw e;
        } catch (Exception e) {
            log.error("下载文件失败",e);
            throw new FileStoreException(e);
        } finally {
            deleteFile(tempPath);
        }
    }

    @Override
    public void download(String id, OutputStream out) throws FileStoreException {
        Objects.requireNonNull(id);
        Objects.requireNonNull(out);
        Path tempPath = null;
        try {
            String xml = getDownloadXml(id);
            String res = ClientMethod.commonService(xml);
            Document doc = readXml(res);
            checkRstCode(doc);
            tempPath = Optional.of(doc)
                    .map(Document::getRootElement)
                    .map(e -> e.element("File"))
                    .map(file -> Path.of(tempRootPath,
                            file.elementText("BatchId"),
                            file.elementText("CurrentLastFolderPath"),
                            file.elementText("FileSelfId")))
                    .orElseThrow(() -> new FileStoreException("下载文件失败"));
            Files.copy(tempPath,out);
        } catch (FileStoreException e) {
            throw e;
        } catch (Exception e) {
            log.error("下载文件失败",e);
            throw new FileStoreException(e);
        } finally {
            deleteFile(tempPath);
        }
    }

    @Override
    public void remove(String id) throws FileStoreException {
        // TODO: 2021/3/9 未实现删除文件
        Objects.requireNonNull(id);
        throw new FileStoreException("删除文件失败");
    }

    @Override
    public IdCard ocrIdCard(Resource resource) throws OcrException {
        JsonNode idCard = null;
        try {
            idCard = ocr("1", resource.getInputStream());
        } catch (IOException e) {
            throw new OcrException(e);
        }
        if (!idCard.get("complete").asBoolean()) {
            throw new OcrException("身份证不完整");
        }
        if (idCard.get("border_covered").asBoolean(true)) {
            throw new OcrException("身份证边缘有遮挡");
        }
        if (idCard.get("head_covered").asBoolean(true)) {
            throw new OcrException("身份证头像有遮挡");
        }
        if (idCard.get("head_blurred").asBoolean(true)) {
            throw new OcrException("身份证头像模糊不清");
        }
        try {
            return objectMapper.treeToValue(idCard,IdCard.class);
        } catch (JsonProcessingException e) {
            log.error("OcrResult JSON转换失败", e);
            throw new OcrException("读取OcrResult失败");
        }
    }

    @Override
    public BusinessLicense ocrBusinessLicense(Resource resource) throws OcrException {
        JsonNode businessLicense = null;
        try {
            businessLicense = ocr("4", resource.getInputStream());
        } catch (IOException e) {
            throw new OcrException(e);
        }
        try {
            return objectMapper.treeToValue(businessLicense,BusinessLicense.class);
        } catch (JsonProcessingException e) {
            log.error("OcrResult JSON转换失败", e);
            throw new OcrException("读取OcrResult失败");
        }
    }

    /**
     * OCR调用
     * @param ocrTrade
     * @param in
     * @return
     * @throws OcrException
     */
    protected JsonNode ocr(String ocrTrade, InputStream in) throws OcrException {
        Objects.requireNonNull(ocrTrade);
        Objects.requireNonNull(in);
        Path tempFile = createTempFile(null,in);
        try {
            LocalDateTime now = LocalDateTime.now();
            String dest = String.format("/ocr/%s/%d/%d", ocrTrade,now.getYear(),now.getMonthValue());
            String xml = getUploadXml(dest, tempFile.toFile().getName(), tempFile.toString(), ocrTrade);
            String res = ClientMethod.commonService(xml);
            Document doc = readXml(res);
            checkRstCode(doc);
            String ocrResultJson = Optional.of(doc)
                    .map(Document::getRootElement)
                    .map(e -> e.element("File"))
                    .map(e -> e.elementText("OcrResult"))
                    .orElseThrow(() -> new OcrException("ZScanApp返回信息缺少OcrResult"));
            JsonNode ocrResult = objectMapper.readTree(ocrResultJson);
            String errorCode = Optional.of(ocrResult)
                    .map(e -> e.get("error_code"))
                    .map(JsonNode::asText)
                    .orElse("");
            if (!"0".equals(errorCode)) {
                throw new OcrException("Ocr识别失败，errorCode：" + errorCode);
            }
            return ocrResult;
        } catch (OcrException e) {
            throw e;
        } catch (Exception e) {
            log.error("Ocr识别失败，OcrTrade：" + ocrTrade,e);
            throw new OcrException(e);
        } finally {
            deleteFile(tempFile);
        }
    }

    /**
     * 上传报文
     * @param dest 目标路径
     * @param name 附件名称
     * @param tempFilePath 临时文件绝对路径
     * @param ocrTrade ocr类型 null则报文上不拼接
     * @return
     */
    protected String getUploadXml(String dest, String name, String tempFilePath, String ocrTrade) {
        Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding(charset);
        Element root = doc.addElement("ZScanApp");
        root.addElement("TradeType").addText("0");
        root.addElement("OperMode").addText(operMode);

        Element batch = root.addElement("Batch");
        batch.addElement("BusCode").addText(busCode);
        batch.addElement("BusType").addText(busType);
        batch.addElement("OrgCode").addText(orgCode);
        batch.addElement("OperCode").addText(operCode);

        Element folder = batch.addElement("Folder");

        Element file = folder.addElement("File");

        file.addElement("FileName").addText(name);
        file.addElement("FileSequence").addText("1"); // 暂时没有作用
        file.addElement("CurrentLastFolderPath").addText(dest);
        file.addElement("PaperType").addText(paperType);
        file.addElement("LocalTempPath").addText(tempFilePath);
        file.addElement("IsShared").addText(isShared);
        if (ocrTrade != null && !ocrTrade.isEmpty()) {
            file.addElement("OcrTrade").addText(ocrTrade);
        }
        return doc.asXML();
    }

    /**
     * 下载报文
     * @param id 上传时返回的ID
     * @return
     */
    protected String getDownloadXml(String id) {
        Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding(charset);
        Element root = doc.addElement("ZScanApp");
        root.addElement("TradeType").addText("3");
        root.addElement("OperMode").addText(operMode);
        root.addElement("OrgCode").addText(orgCode);
        root.addElement("OperCode").addText(operCode);
        root.addElement("LocalTempRootPath").addText(tempRootPath);

        Element batch = root.addElement("Batch");
        batch.addElement("BatchId").addText(id);
        return doc.asXML();
    }

    /**
     * 创建临时文件
     * @param name
     * @param in
     * @return
     */
    protected Path createTempFile(String name, InputStream in) {
        try {
            Path dir = Path.of(tempRootPath);
            if (!Files.exists(dir)) {
                Files.createDirectory(dir);
            }
            Path tempFile = Files.createTempFile(dir,name, ".temp");
            Files.copy(in,tempFile,StandardCopyOption.REPLACE_EXISTING);
            return tempFile;
        } catch (Exception e) {
            log.error("创建本地临时文件时出错", e);
            throw new FileStoreException("创建本地临时文件失败");
        }
    }

    /**
     * 删除临时文件
     * @param file
     */
    private void deleteFile(Path file) {
        if (file == null) {
            return;
        }
        try {
            Files.deleteIfExists(file);
        } catch (Exception e) {
            if (log.isWarnEnabled()) {
                log.warn("删除临时文件失败：" + file);
            }
        }
    }

    /**
     * 检查接口返回的RstCode
     * @param doc
     */
    protected void checkRstCode(Document doc) {
        String rstCode = Optional.of(doc)
                .map(Document::getRootElement)
                .map(e -> e.element("RstCode"))
                .map(Element::getText)
                .orElse(null);
        if (!Objects.equals(Constant.ReturnStatus.SUCCESS.code(), rstCode)) {
            throw new FileStoreException("调用ZScanApp失败，RstCode：" + rstCode);
        }
    }

    /**
     * 读取xml
     * @param xml
     * @return
     */
    protected Document readXml(String xml) throws DocumentException {
        try {
            return DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            log.error("解析xml失败",e);
            throw e;
        }
    }

    // ----------Setter--------------------//

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setOperMode(String operMode) {
        this.operMode = operMode;
    }

    public void setBusCode(String busCode) {
        this.busCode = busCode;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public void setOperCode(String operCode) {
        this.operCode = operCode;
    }

    public void setPaperType(String paperType) {
        this.paperType = paperType;
    }

    public void setIsShared(String isShared) {
        this.isShared = isShared;
    }

    public void setTempRootPath(String tempRootPath) {
        this.tempRootPath = tempRootPath;
    }
}
