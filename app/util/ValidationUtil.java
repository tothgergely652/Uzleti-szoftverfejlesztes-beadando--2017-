package util;

public class ValidationUtil {

	public static boolean isEmpty(String input){
		return (input == null || input.trim().isEmpty());
	}
	
	public static boolean isNotEmpty(String input){
		return !isEmpty(input);
	}
	
	public static boolean isInteger(String input){
		boolean isNumber = false;
		if (ValidationUtil.isNotEmpty(input)){
			try {
				Integer.valueOf(input);
				isNumber = true;
			} catch (NumberFormatException nfe){
				//Ha idejut a kód, akkor a string nem szám
			}
		} 
		return isNumber;
	}
	
	public static boolean isTaxNumber(String input){
		/*Kritérium:
		 * -első 3 karakter csak szám lehet
		 * -11 karakter hosszú
		 */
		boolean isTaxNum = false;
		if (ValidationUtil.isNotEmpty(input)){
			isTaxNum = true;
			if(input.length() >= 3) 
				if(!isInteger(input.substring(0, 3))) isTaxNum = false; //az első 3 kararakter szám
			if(input.length() != 11) isTaxNum = false; //11 karakter hosszú a számlaszám
		}
		return isTaxNum;
	}
	
	public static boolean isHouseNumber(String input){
		/*Kritérium:
		 * -legalább egy számot tartalmaznia kell
		 */
		boolean isHouseNum = false;
		if (ValidationUtil.isNotEmpty(input)){
			for(int i=0; i<input.length(); i++){
				if(input.charAt(i) >= '0' && input.charAt(i) <= '9') isHouseNum = true;
			}
		}
		return isHouseNum;
	}
	
	public static boolean isIdCard(String input){
		/*Kritérium:
		 * -első 6 karakter csak szám lehet
		 * -utolsó 2 karakter csak betű lehet
		 * -8 karakter hosszú
		 */
		boolean isIdNum = false;
		if (ValidationUtil.isNotEmpty(input)){
			if(input.length() == 8){
				isIdNum = true;
				if(!isInteger(input.substring(0, 6))) isIdNum = false; //az első 6 karakter szám
				if(isInteger(input.substring(6, 8))) isIdNum = false; //az utolsó 2 karakter betű
			}
		}
		return isIdNum;
	}
	
	public static boolean isBirthDate(String input){
		/*Kritérium:
		 * -év: 1900..2017
		 * -hónap: 1..12
		 * -nap: 1..28 vagy 30 vagy 31 - hónaptól függően (szökőévet nem vesszük figyelembe
		 * -egységek közötti szeparátor: -
		 */
		boolean isBirthday = false;
		if (ValidationUtil.isNotEmpty(input)){
			if(input.length() == 10){
				int year=0, month=0, day=0;
				boolean y=false,m=false,d=false,sep1=true,sep2=true;
				if(isInteger(input.substring(0, 4))){ //év
					y = true;
					year = Integer.valueOf(input.substring(0, 4));
					if(year < 1900 || year > 2017) y = false;
				}
				if(input.charAt(4) != '-') sep1 = false;
				if(isInteger(input.substring(5, 7))){ //hónap
					m = true;
					month = Integer.valueOf(input.substring(5, 7));
					if(month < 1 || month > 12) m = false;
				}
				if(input.charAt(7) != '-') sep2 = false;
				if(isInteger(input.substring(8, 10))){ //nap
					d = true;
					day = Integer.valueOf(input.substring(8, 10));
					if(month==1 && (day < 1 || day > 31)) d = false; //január
					if(month==2 && (day < 1 || day > 28)) d = false; //február
					if(month==3 && (day < 1 || day > 31)) d = false; //március
					if(month==4 && (day < 1 || day > 30)) d = false; //április
					if(month==5 && (day < 1 || day > 31)) d = false; //május
					if(month==6 && (day < 1 || day > 30)) d = false; //június
					if(month==7 && (day < 1 || day > 31)) d = false; //július
					if(month==8 && (day < 1 || day > 31)) d = false; //augusztus
					if(month==9 && (day < 1 || day > 30)) d = false; //szeptember
					if(month==10 && (day < 1 || day > 31)) d = false; //október
					if(month==11 && (day < 1 || day > 30)) d = false; //november
					if(month==12 && (day < 1 || day > 31)) d = false; //december
				}
				//ha minden feltételnek megfelelt:
				if(y && m && d && sep1 && sep2) isBirthday = true;
			}
		}
		return isBirthday;
	}
}