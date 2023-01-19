
package fi.tuni.prog3.round7.xmlcountries;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.jdom2.Document;
import org.jdom2.Element;

import org.jdom2.input.DOMBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;




import org.xml.sax.SAXException;



public class CountryData {
    
    public static List<Country> readFromXmls(String areaFile, String populationFile, String gdpFile) {
        ArrayList<Country> countries = new ArrayList<>();
        
        Document areaDoc = getDOMParsedDocument(areaFile);
        Document populationDoc = getDOMParsedDocument(populationFile);
        Document gdpDoc = getDOMParsedDocument(gdpFile);
        
        Element areaRootNode = areaDoc.getRootElement();
        Element populationRootNode = populationDoc.getRootElement();
        Element gdpRootNode = gdpDoc.getRootElement();
        
        List<Element> areaElements = areaRootNode.getChild("data").getChildren();
        List<Element> populationElements = populationRootNode.getChild("data").getChildren();
        List<Element> gdpElements = gdpRootNode.getChild("data").getChildren();
        
        for(int i = 0; i < areaElements.size(); i++) {
            
            String name = areaElements.get(i).getChildren().get(0).getValue();
            
            double area = Double.parseDouble(areaElements.get(i).getChildren().get(2).getValue());
            long population = Long.parseLong(populationElements.get(i).getChildren().get(2).getValue());  
            double gdp = Double.parseDouble(gdpElements.get(i).getChildren().get(2).getValue());
              
            countries.add(new Country(name, area, population, gdp));
        }
        
          
        return countries;
    }
    
    public static void writeToXml(List<Country> countries, String countryFile) throws IOException {
        
        Element root = new Element("countries");
        Document doc = new Document();
        
        
        
        for(int i = 0; i < countries.size(); i++) {
            
            Element country = new Element("country");
            
            Element child1 = new Element("name");
            child1.addContent(countries.get(i).getName());
            Element child2 = new Element("area");
            child2.addContent(String.valueOf(countries.get(i).getArea()));
            Element child3 = new Element("population");
            child3.addContent(String.valueOf(countries.get(i).getPopulation()));
            Element child4 = new Element("gdp");
            child4.addContent(String.valueOf(countries.get(i).getGdp()));
            
            country.addContent(child1);
            country.addContent(child2);
            country.addContent(child3);
            country.addContent(child4);
            
            root.addContent(country);
        }
        
        doc.setRootElement(root);
        
        XMLOutputter outter=new XMLOutputter();
        outter.setFormat(Format.getPrettyFormat());
        outter.output(doc, new FileWriter(new File(countryFile)));
    }
    
    private static Document getDOMParsedDocument(final String fileName) {
        Document document = null;
        try
        {
          DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
          //If want to make namespace aware.
              //factory.setNamespaceAware(true);
          DocumentBuilder documentBuilder = factory.newDocumentBuilder();
          org.w3c.dom.Document w3cDocument = documentBuilder.parse(fileName);
          document = (Document) new DOMBuilder().build(w3cDocument);

        } 
        catch (IOException | SAXException | ParserConfigurationException e) 
        {
        }
        return document;
      }
    
}
