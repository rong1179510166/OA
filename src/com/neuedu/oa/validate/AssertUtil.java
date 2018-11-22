package com.neuedu.oa.validate;

import static com.neuedu.oa.validate.AssertUtil.assertMatch;

import java.util.regex.Pattern;

import com.neuedu.oa.exception.OAException;

/**
 * 参数验证
 * @author Administrator
 *
 */
public class AssertUtil {
	static public String $(String message,String s) {
		return assertNotBlank(message, s);
	}
	static public void assertMatch(String message,String target,String regex) {
		Pattern pattern=Pattern.compile(regex);
		if(!pattern.matcher(target).matches()) {
			throw new OAException(message);
		}
	}
	public static void main(String[] args) {
	}
	static public void assertBetween(String message,String s,int min,int max) {
		if(s.length()<min||s.length()>max) {
			throw new OAException(message+"必须大于"+min+"位且小于"+max+"位");
		}
	}
	static public String assertNotBlank(String message,String s) {
		if(s==null||(s=s.trim()).length()==0) {
			throw new OAException(message+"不能为空");
		}
		return s;
	}
	static public void assertTrue(String message,boolean b) {
		if(!b) {
			throw new OAException(message);
		}
	}
	static public void assertFalse(String message,boolean b) {
		if(b) {
			throw new OAException(message);
		}
	}
	
}
