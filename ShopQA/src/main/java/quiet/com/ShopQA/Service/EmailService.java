package quiet.com.ShopQA.Service;

import java.util.Map;

public interface EmailService {
	
	void sendSimpleMessage(String to, String subject, String text);

	void sendTemplateMessage(String to, String subject, Map<String, Object> model, String templateName);
}
