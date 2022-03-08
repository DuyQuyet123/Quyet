package quiet.com.ShopQA.Controler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControler {
	
	@ExceptionHandler(Exception.class)
	public String exception(Exception ex) {
		ex.printStackTrace();
		return"client/exception";
	}
	
}
