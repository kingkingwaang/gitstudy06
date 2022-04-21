package testing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;

public class Midterm {
	private String inputfile;
	private String inputs;
	
	public Midterm(String file, String sentence) throws Exception{
		inputfile = file;
		inputs = sentence;
	}
	
	public void sorting() throws Exception{
		KeywordExtractor ke = new KeywordExtractor();
		KeywordList k1 = ke.extractKeyword(inputs, true);
		StringBuilder sb = new StringBuilder();
		
		for (int a = 0; a < k1.size(); a++) {
			Keyword kwrd = k1.get(a);
			sb.append(kwrd.getString() + ":" + kwrd.getCnt() + "#");
		}
		HashMap hashmap = new HashMap();
		String dvdata[] = sb.toString().split("#");
		int i = 0;

		while (i < dvdata.length) {

			String sdata = dvdata[i];
			String inputstr[] = sdata.split(":");
			hashmap.put(inputstr[0], inputstr[1]);

			i++;

		}
		
		inputfile = "¶ó¸é ¹Ð°¡·ç ´Þ°¿ ¹ä »ý¼±\r\n" + 
				"¶ó¸é ¹° ¼Ò±Ý ¹ÝÁ×\r\n" + 
				"Ã·ºÎ ºÀÁö¸é ÀÎ±â\r\n" + 
				"ÃÊ¹ä ÇÏ¸é ¹ä¹° Ã¤¼Ò ¼Ò±Ý\r\n" + 
				"ÃÊ¹ä Á¾·ù È°¾î";
		KeywordExtractor ke2 = new KeywordExtractor();
		KeywordList k2 = ke.extractKeyword(inputfile, true);
		StringBuilder s2 = new StringBuilder();
		
		for (int a = 0; a < k2.size(); a++) {
			Keyword kwrd = k2.get(a);
			s2.append(kwrd.getString() + ":" + kwrd.getCnt() + "#");
		}
		
		System.out.println(s2);
		HashMap hashmap2 = new HashMap();
		String dvdata2[] = sb.toString().split("#");
		int i2 = 0;

		while (i2 < dvdata.length) {

			String sdata = dvdata[i];
			String inputstr[] = sdata.split(":");
			hashmap2.put(inputstr[0], inputstr[1]);

			i2++;

		}
		
		HashMap selecthash = new HashMap();
		Iterator<String> it = hashmap2.keySet().iterator();
		while(it.hasNext()) {
			String ikey = it.next();
			String ivalue = (String) hashmap2.get(ikey);
		}
		
	}
}
