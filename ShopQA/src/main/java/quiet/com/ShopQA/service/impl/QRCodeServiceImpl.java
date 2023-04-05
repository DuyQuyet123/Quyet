package quiet.com.ShopQA.service.impl;

import org.springframework.stereotype.Service;
import quiet.com.ShopQA.Ultils.QRGenerator;
import quiet.com.ShopQA.service.QRCodeService;

@Service
public class QRCodeServiceImpl implements QRCodeService {
    private static final String prefixPNGBase64 = "data:image/png;base64,";
    @Override
    public String generateQRCodeForMerchantInfo(String productUUID) {
        String local = "localhost:8088";
        String qrCodeContent = String.format("%s?test=%s", local, productUUID);
        return prefixPNGBase64 + QRGenerator.generateQRCode(qrCodeContent);
    }
}
