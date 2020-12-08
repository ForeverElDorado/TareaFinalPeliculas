/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author aghsk
 */
public class Xpath {

    Document doc;
    XPath xpath;

    public int abrir_XML(File fichero) {
        //doc representara el arbol DOM
        doc = null;

        try {
            //Se crea un objeto DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //Indica que el modelo DOM no debe contemplar los comentarios 
            //que tenga el XML
            factory.setIgnoringComments(true);
            //Ignora los espacios en blanco que tenga el documento
            factory.setIgnoringElementContentWhitespace(true);
            //Se crea un objeto DocumentBuilder para cargar en él la
            //estructura de arbol DOM a partir del XML seleccionado.
            DocumentBuilder builder = factory.newDocumentBuilder();
            //Interpreta (parsear) el documento XML(file) y genera el
            //DOM equivalente
            doc = builder.parse(fichero);
            //Ahora doc apunta al arbol DOM listo para ser recorrido 

            xpath = XPathFactory.newInstance().newXPath();
            return 0;

        } catch (Exception e) {
            return -1;
        }
    }

    //PARA LASS CONSULTAS
    String Ejecutar_XPath(String consulta) {
        String salida = "";

        try {

            XPathExpression exp = xpath.compile(consulta);

            Object result = exp.evaluate(doc, XPathConstants.NODESET);

            NodeList listaNodos = (NodeList) result;

            for (int i = 0; i < listaNodos.getLength(); i++) {

                Node nodo = listaNodos.item(i);
                //ahora hacemos un if por posible nombre del nodo
                //Para Autor
                if ((nodo.getNodeName().equals("Director"))) {
                    salida = salida + "\nDirector: " + listaNodos.item(i).getFirstChild().getNodeValue();
                } //Para Titulo
                else if (nodo.getNodeName().equals("Titulo")) {
                    salida = salida + "\n" + "El Titulo es: " + listaNodos.item(i).getFirstChild().getNodeValue();
                } else if (nodo.getNodeName().equals("Genero")) {
                    salida = salida + "\n" + "Genero es: " + listaNodos.item(i).getFirstChild().getNodeValue();
                }
            }
            return salida;
        } catch (Exception e) {
            return "error en la ejecucion de la consulta";
        }

    }
}
