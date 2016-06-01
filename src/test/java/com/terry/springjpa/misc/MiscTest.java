package com.terry.springjpa.misc;

import static org.testng.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.annotations.Test;

public class MiscTest {

	@Test
	public void regexTest(){
		String url1 = "/unitedBoard/unitedBoardList.do?boardTypeIdx=1";
		String url2 = "/unitedBoard/unitedBoardList.do?boardTypeIdx=1&firstIdx=1";
		String url3 = "/unitedBoard/unitedBoardList.do?boardTypeIdx=1&firstIdx=1&secondIdx=2";
		String url4 = "/unitedBoard/unitedBoardList.do?firstIdx=1&boardTypeIdx=1";
		String url5 = "/unitedBoard/unitedBoardList.do?firstIdx=1?secondIdx=2&boardTypeIdx=1";
		String url6 = "/unitedBoard/unitedBoardList.do?firstIdx=1&boardTypeIdx=1&thirdIdx=3";
		String url7 = "/unitedBoard/unitedBoardList.do?firstIdx=1?secondIdx=2&boardTypeIdx=1&thirdIdx=3&fourthIdx=4";
		
		// String regex = "/unitedBoard/unitedBoardList.do(\\?boardTypeIdx=1(&.+)*|([\\?&].+)&boardTypeIdx=1(&.+)*)";
		String regex = "/unitedBoard/unitedBoardList.do(([\\?&].+)*([\\?&]boardTypeIdx=1)(&.+)*)";
		
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(url1);
		assertTrue(m.matches());
		m = p.matcher(url2);
		assertTrue(m.matches());
		m = p.matcher(url3);
		assertTrue(m.matches());
		m = p.matcher(url4);
		assertTrue(m.matches());
		m = p.matcher(url5);
		assertTrue(m.matches());
		m = p.matcher(url6);
		assertTrue(m.matches());
		m = p.matcher(url7);
		assertTrue(m.matches());
	}
}
