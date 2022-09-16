package com.realfinance.sofa.common.ocr;

import com.realfinance.sofa.common.ocr.model.BusinessLicense;
import com.realfinance.sofa.common.ocr.model.IdCard;
import org.springframework.core.io.Resource;

/**
 * 不支持OCR识别
 */
public class NotSupportedOcrService implements OcrService {
    @Override
    public IdCard ocrIdCard(Resource resource) throws OcrException {
        throw notSupportedException();
    }

    @Override
    public BusinessLicense ocrBusinessLicense(Resource resource) throws OcrException {
        throw notSupportedException();
    }

    private OcrException notSupportedException() {
        return new OcrException("不支持OCR识别");
    }
}
