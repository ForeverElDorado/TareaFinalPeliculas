/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author aghsk
 */
public class SAX {

    SAXParser parser;
    ManejadorSAX sh;
    File ficheroXML;

    int abrir_XML_SAX(File fichero) {
        try {
            //Se crea un objeto SAXParser para interpetrar el documento XML 
            SAXParserFactory factory = SAXParserFactory.newInstance();
            parser = factory.newSAXParser();

            //Se crea una instancia del manejador que sera el que recorra el 
            //documento XML secuencialmente
            sh = new ManejadorSAX();

            ficheroXML = fichero;

            return 0;
        } catch (Exception e) {
            return -1;
        }

    }

    String recorrerSAX() {
        try {
            sh.cadena_resultado = "";
            parser.parse(ficheroXML, sh);
            return sh.cadena_resultado;
        } catch (SAXException ex) {
            return "Error al parsear con SAX";
        } catch (IOException ex) {
            return "Error al parsear con SAX";
        }
    }

}

class ManejadorSAX extends DefaultHandler {

    String cadena_resultado = "";

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        for (int i = start; i < length + start; i++) {
            cadena_resultado = cadena_resultado + ch[i];
        }
        cadena_resultado = cadena_resultado.trim() + "\n";
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("Pelicula")) {
            cadena_resultado = cadena_resultado + "-------------------------\n";
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("Pelicula")) {
            cadena_resultado = cadena_resultado + "Codigo IMBD: " + attributes.getValue(attributes.getQName(0).trim());
        } else if (qName.equals("Titulo")) {
            cadena_resultado = cadena_resultado + "Titulo: ".trim();

        } else if (qName.equals("Peliculas")) {
            cadena_resultado = "----------------------\n Se van a mostrar las Películas de este documento \n----------------------\n";

        } else if (qName.equals("Director")) {
            cadena_resultado = cadena_resultado + "Director: ".trim();

        }/* else if (qName.equals("Reparto")) {
            //ALOMEJOR PONER DOS VECES SI HAY DOS ACTORES
            cadena_resultado = cadena_resultado + "Actor: " + attributes.getValue(attributes.getQName(0).trim());

        }*/ else if (qName.equals("Genero")) {
            cadena_resultado = cadena_resultado + "Género: ".trim();

        } else if (qName.equals("estrenada_en")) {
            cadena_resultado = cadena_resultado + "Estrenada en: ".trim();

//        } else if (qName.equals("Nominaciones")) {
//            //ALOMEJOR PONER DOS VECES SI HAY DOS ACTORES
//            cadena_resultado = cadena_resultado + "Nominado a: " + attributes.getValue(attributes.getQName(0).trim());
//
//        }
        }
    }

}
