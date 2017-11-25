package utilities;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import domain.Comment;
import domain.TabooWord;
import services.TabooWordService;

/*
 * 
 * Clase utilizada para validaciones customizadas
 */
public class Validator {
	
	public static boolean isCorrectPhone(String phone) {
		String ph= phone==null || !phone.contains("+")?null : phone.substring(3, 4);
		return ph==null ? false : !ph.equals(" ");
	}
	
	
	
}
