package nvlu.com.jslib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlToJavascript {
	public static void main(String[] arge){
		if(arge.length != 1){
			System.out.println("usage: java XmlToJavascript fileName");
			return;
		}
		String xmlFileName = arge[0];
		//String xmlFileName = "appeal.xml";
		try{
			File file = new File(xmlFileName);
			FileInputStream xmlFileInput = new FileInputStream(file);
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFileInput);
			NodeList moduleNodes = document.getElementsByTagName("module");
			Node moduleNode = moduleNodes.item(0);
			String moduleName = moduleNode.getAttributes().getNamedItem("name").getNodeValue();

			File parentDir = new File(moduleName);
			parentDir.mkdir();
			NodeList nodes = document.getElementsByTagName("struct");
            for(int i=0;i<nodes.getLength();i++){
            	creatStructJs(nodes.item(i),moduleName);
            }
            
		}catch(FileNotFoundException e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void creatStructJs(Node node,String module)throws FileNotFoundException,IOException {		
		NamedNodeMap node_map;
		node_map = node.getAttributes();
		String structName = node_map.getNamedItem("name").getNodeValue();
		int flag = structName.indexOf(".");
		structName = structName.substring(flag+1);
		File file = new File(module,structName+".js");
		FileOutputStream xmlStream = new FileOutputStream(file);
		PrintWriter xmlOutput = new PrintWriter(xmlStream);
		
		xmlOutput.println("var "+structName+"_pk = 0;");
		xmlOutput.println("function "+structName+"(){");
		xmlOutput.println("\t"+"this.pk = "+structName+"_pk++;");
		NodeList variableNodes = node.getChildNodes();
		for(int i=0;i<variableNodes.getLength();i++){
			NamedNodeMap variable_name_map = variableNodes.item(i).getAttributes();
			if(variable_name_map!=null ){
				String variable_name = variable_name_map.getNamedItem("name").getNodeValue();
				xmlOutput.println("\t"+"this."+variable_name+"_label;");
				xmlOutput.println("\t"+"this."+variable_name+"_value;");
				xmlOutput.println();
				
				String firstLetter = variable_name.substring(0,1);
				firstLetter = firstLetter.toUpperCase();
				String firstUpperName = firstLetter + variable_name.substring(1);
				
				xmlOutput.println("\t"+"this.get"+firstUpperName+"_label = function(){");
				xmlOutput.println("\t\t"+"return this."+variable_name+"_label;");
				xmlOutput.println("\t"+"}");
				xmlOutput.println();
				
				xmlOutput.println("\t"+"this.set"+firstUpperName+"_label = function(newValue){");
				xmlOutput.println("\t\t"+"this."+variable_name+"_label=newValue;");
				xmlOutput.println("\t"+"}");
				xmlOutput.println();
				
				xmlOutput.println("\t"+"this.get"+firstUpperName+"_value = function(){");
				xmlOutput.println("\t\t"+"return this."+variable_name+"_value;");
				xmlOutput.println("\t"+"}");
				xmlOutput.println();
				
				xmlOutput.println("\t"+"this.set"+firstUpperName+"_value = function(newValue){");
				xmlOutput.println("\t\t"+"this."+variable_name+"_value=newValue;");
				xmlOutput.println("\t"+"}");
				xmlOutput.println();
				
			}
		}
		
		xmlOutput.print("}");
		xmlOutput.flush();
		xmlOutput.close();
		xmlStream.close();
		System.out.println(structName+".js");
	}
		
}
