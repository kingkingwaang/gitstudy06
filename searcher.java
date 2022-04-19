package prcatie_1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jsoup.Jsoup;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class searcher {
	// ���絵 ���� ������ ���� ���� �������� �������
	// ���絵�� 0�̸�(������ ��� �ܾ �ؽø��� Ű�� ���� ��): ���θ޽����� ���
	private String input_file;
	private String input_String;
	private String xml_location = "C:\\Users\\�̹�ȿ\\Desktop\\opensw\\SimpleIR\\prcatie_1\\src\\prcatie_1\\collection.xml";
	
	

	public searcher(String file) throws Exception {
		this.input_file = file;
	}


	public void calcSim(String Query) throws Exception {


		input_String = Query;
		KeywordExtractor ke = new KeywordExtractor();
		KeywordList k1 = ke.extractKeyword(input_String, true);
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

		FileInputStream filestream = new FileInputStream(input_file);

		ObjectInputStream objectinputStream = new ObjectInputStream(filestream);
		Object object = objectinputStream.readObject();
		objectinputStream.close();
		HashMap indexhash = (HashMap) object;
		Iterator<String> it = indexhash.keySet().iterator();
		HashMap selecthash = new HashMap();
		while (it.hasNext()) {
			String ikey = it.next();
			String ivalue = (String) indexhash.get(ikey);
			if (hashmap.containsKey(ikey)) {
				selecthash.put(ikey, ivalue);
			}
		}
		Iterator<String> it2 = selecthash.keySet().iterator();
		Iterator<String> it3 = hashmap.keySet().iterator();
		double[] result = new double[5];
		double[] Aw = new double[5];
		double[] Bv = new double[5];
		while (it2.hasNext()) {
			String skey = it2.next();
			String hkey = it3.next();
			int hvalue = Integer.parseInt((String) hashmap.get(skey));
			String svalue = (String) selecthash.get(skey);
			String[] selectString = svalue.split(" ");
			int ab = 1;
			int loop = 5;
			for (int b = 0; b < loop; b++) {
				String rs[] = selectString[ab].split(":");
				Double weight = Double.parseDouble(rs[1]);
				result[b] += weight * hvalue;
				Aw[b] += weight * weight;
				Bv[b] += hvalue * hvalue;
				ab++;
			}
		}
		
		
		String[] resultnum = new String[3];
		Map<String, Double> hm = new HashMap();
		for(int t = 0; t < 5; t++) {
			System.out.println(Aw[t] + " " + Bv[t]);
			Aw[t] = Math.sqrt(Aw[t]);
			Bv[t] = Math.sqrt(Bv[t]);
			result[t] = result[t]/ Aw[t] * Bv[t];
			if(Double.isNaN(result[t])) {
				result[t] = 0.0;
			}
			System.out.println(result[t]);
		}
		System.out.println();
		for(int f = 0; f < 5; f++) {
			String tmp2 = Integer.toString(f);
			hm.put(tmp2, (double) result[f]);
		}
		
		List<Map.Entry<String, Double>> list = new LinkedList<>(hm.entrySet());
		 
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                if (o1.getValue() > o2.getValue()) {
                    return -1;
                }
                else if (o1.getValue() < o2.getValue()) {
                    return 1;
                }
 
                return o1.getKey().compareTo(o2.getKey());
            }
        });
 
        // ���������� ���� LinkedHashMap�� ���
        Map<String, Double> sortedMap = new LinkedHashMap<>();
        for(Iterator<Map.Entry<String, Double>> iter = list.iterator(); iter.hasNext();){
            Map.Entry<String, Double> entry = iter.next();
            sortedMap.put(entry.getKey(), entry.getValue());
            
        }
        double[] resultvalue = new double[3];
        Iterator<String> it4 = sortedMap.keySet().iterator();
        for(int c = 0; c< 3; c++) {
        	resultnum[c] = it4.next();
        	resultvalue[c] = sortedMap.get(resultnum[c]);
        }
        
        File dir = new File(xml_location);
        org.jsoup.nodes.Document xml = Jsoup.parse(dir, "UTF-8");
        if(resultvalue[0]==0 &&resultvalue[1]==0 && resultvalue[2]==0) {
        	System.out.println("��� ���絵�� 0�Դϴ�.");
        }
        else {
            for(int g = 0; g < 3; g++) {
                org.jsoup.nodes.Element Htitle = xml.getElementById(resultnum[g]).selectFirst("title");
                System.out.println(Htitle.text());
            	}
        }
		System.out.println("5���� ����Ϸ�");
	}
}
