package Server.Service;

import Server.ATMServer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;

public class XmlReader {

    public static void readXml(String filePath){
        String xml = load(filePath);
        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            Document document;
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            StringReader sr = new StringReader(xml);
            InputSource is = new InputSource(sr);
            document = dbBuilder.parse(is);
            Element root = document.getDocumentElement();
            findMethod(root.getChildNodes());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void findMethod(NodeList elementList){
        for (int i = 0; i < elementList.getLength(); i++) {
            Node elementNode = elementList.item(i);
            //System.out.println("allnode: "+elementNode.getNodeName());
            if (elementNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) elementNode;
                String name = element.getNodeName();
                if(name.equalsIgnoreCase("aop")){
                    readMethod(element.getChildNodes());
                }
                else{
                    findMethod(element.getChildNodes());
                }
            }
        }
    }

    private static void readMethod(NodeList elementList){
        String methodName1 = "";
        String methodName2 = "" ;
        String methodName3 = "" ;
        String methodName4 = "" ;
        for (int i = 0; i < elementList.getLength(); i++) {
            Node elementNode = elementList.item(i);
            if (elementNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) elementNode;
                String name = element.getNodeName();
                if( name.equals("aspect")){
                    String aspectName = element.getFirstChild().getTextContent() ;
                    ATMServer.aspect = aspectName ;
                }
                else if(name.equals("method1")){
                    if(methodName1==null||methodName1.length()==0)methodName1 = element.getFirstChild().getTextContent();
                    ATMServer.method1 = methodName1 ;
                }
                else if( name.equals("method2")){
                    if(methodName2==null||methodName2.length()==0)methodName2 = element.getFirstChild().getTextContent();
                    ATMServer.method2 = methodName2 ;
                }
                else if( name.equals("method3")){
                    if(methodName3==null||methodName3.length()==0)methodName3 = element.getFirstChild().getTextContent();
                    ATMServer.method3 = methodName3 ;
                }
                else if(name.equals("type")){
                    String type = element.getFirstChild().getTextContent();
                    ATMServer.type = type ;
                }
                else if( name.equals("method4")){
                    if(methodName4==null||methodName4.length()==0)methodName4 = element.getFirstChild().getTextContent();
                    ATMServer.method4 = methodName4 ;
                }
            }
        }
    }

    private static String load(String path){
        try{
            File file = new File(path);
            FileReader reader = new FileReader(file);
            BufferedReader bReader = new BufferedReader(reader);
            StringBuilder sb = new StringBuilder();
            String s = "";
            while ((s =bReader.readLine()) != null) {
                sb.append(s + "\n");
                //System.out.println(s);
            }
            bReader.close();
            return sb.toString();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
