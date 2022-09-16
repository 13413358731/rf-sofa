package com.realfinance.sofa.common.ocr;

import com.realfinance.sofa.common.ocr.model.BusinessLicense;
import com.realfinance.sofa.common.ocr.model.IdCard;
import org.springframework.core.io.Resource;

/**
 * OCR识别接口
 */
public interface OcrService {
    /**
     * 识别身份证
     * @param resource
     * @return
     * @throws OcrException
     */
    IdCard ocrIdCard(Resource resource) throws OcrException;

    /**
     * 识别营业执照
     * @param resource
     * @return
     * @throws OcrException
     */
    BusinessLicense ocrBusinessLicense(Resource resource) throws OcrException;
}
