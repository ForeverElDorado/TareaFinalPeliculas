/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileOutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author aghsk
 */
public class DOM {

    //Objeto document donde se almacena el DOM del XML
    Document doc;

    public int abrir_XML_DOM(File fichero) {

        doc = null;
        try {
            //Aqui se crea un objeto DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //Ignora comentarios
            factory.setIgnoringComments(true);

            //Ignora espacios en blanco
            factory.setIgnoringElementContentWhitespace(true);

            //En este builder se carga la estructura de arbol del Dom a partir
            //del XML
            DocumentBuilder builder = factory.newDocumentBuilder();

            doc = builder.parse(fichero);
            //Ahora doc apunta al arbol DOM listo para recorrerse

            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    public String recorrerDOMyMostrar() {
        String salida = "";
        Node node;
        String datos_nodo[] = null;
        //Obtiene el primero nodo del DOM
        Node raiz = doc.getFirstChild();
        //Obtiene una lista de nodos con todos los nodos hijos del raiz.
        NodeList nodelist = raiz.getChildNodes();

        for (int i = 0; i < nodelist.getLength(); i++) {
            node = nodelist.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                //Es un nodo libro

                datos_nodo = procesarPelicula(node);

                salida = salida + "\n" + "Codigo IMB: " + datos_nodo[0];
                salida = salida + "\n" + "El titulo es: " + datos_nodo[1];
                salida = salida + "\n" + "El director es: " + datos_nodo[2];
                salida = salida + "\n" + "Actor: " + datos_nodo[3];
                salida = salida + "\n" + "Genero: " + datos_nodo[4];
                salida = salida + "\n" + "Estrenada en: " + datos_nodo[5];
                salida = salida + "\n" + "Nominación a: " + datos_nodo[6];

                salida = salida + "\n --------------";
            }
        }
        return salida;
    }

    private String[] procesarPelicula(Node n) {

        String datos[] = new String[7];
        Node ntemp = null;

        int contador = 1;

        datos[0] = n.getAttributes().item(0).getNodeValue();

        NodeList nodos = n.getChildNodes();

        for (int i = 0; i < nodos.getLength(); i++) {
            ntemp = nodos.item(i);

            if (ntemp.getNodeType() == Node.ELEMENT_NODE) {
                datos[contador] = ntemp.getFirstChild().getNodeValue();
                contador++;
            }
        }
        return datos;
    }

    public int añadirDOM(String titulo, String director, String actor, String genero, String estreno, String premio, String codigo) {
        try {
            //Se crea un nodo tipo Element con nombre titulo(<Titulo>)
            Node ntitulo = doc.createElement("Titulo");
            //Se crea un nodo tipo texto con el titulo del libro
            Node ntitulo_text = doc.createTextNode(titulo);
            //Se añade el nodo de texto con el titulo como hijo del elemento Titulo
            ntitulo.appendChild(ntitulo_text);

            Node ndirector = doc.createElement("Director");
            Node ndirector_text = doc.createTextNode(director);
            ndirector.appendChild(ndirector_text);

            Node nactor = doc.createElement("actor");
            //((Element) nactor).setAttribute("nombre", actor);
            Node nactor_text = doc.createTextNode(actor);
            nactor.appendChild(nactor_text);

            Node ngenero = doc.createElement("Genero");
            Node ngenero_text = doc.createTextNode(genero);
            ngenero.appendChild(ngenero_text);

            Node nestreno = doc.createElement("estrenada_en");
            Node nestreno_text = doc.createTextNode(estreno);
            nestreno.appendChild(nestreno_text);

            Node npremio = doc.createElement("nominacion");
            // ((Element) npremio).setAttribute("categoria", premio);
            Node npremio_text = doc.createTextNode(premio);
            npremio.appendChild(npremio_text);

            Node npelicula = doc.createElement("Pelicula");
            ((Element) npelicula).setAttribute("id_IMBD", codigo);

            //Se añade a libro el nodo autor y titulo creados antes
            npelicula.appendChild(ntitulo);
            npelicula.appendChild(ndirector);
            npelicula.appendChild(ngenero);
            npelicula.appendChild(nestreno);
            npelicula.appendChild(nactor);
            npelicula.appendChild(npremio);
            //Se obtiene el primer nodo del documento y a é se le añade como
            //hijo el nodo libre que ya tiene colgando todos sus hijos y atributos creados antes.
            Node raiz = doc.getFirstChild();
            raiz.appendChild(npelicula);

            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    public int guardarDOMcomoFILE() {
        try {
            //Crea un fichero llamado salida.xml
            File archivo_xml = new File("prueba.xml");
            //Especifica el formato de salida
            OutputFormat format = new OutputFormat(doc);
            //Especifica que la salida este indentada
            format.setIndenting(true);
            //Escribe el contenido en el FILE
            XMLSerializer serializer = new XMLSerializer(new FileOutputStream(archivo_xml), format);
            serializer.serialize(doc);

            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    private void cambiarTituloLibro(Node node, String tituloAntiguo, String tituloNuevo) {
        //Este metodo lee el titulo de un libro y lo intercambia por el titulo nuevo
        String titulo;//string referente al titulo sacado del nodo
        Node n;
        //Cogelos los nodos hijos (titulo y autor) para luego elegir el titulo
        NodeList nodos = node.getChildNodes();

        //cogemos el titulo
        n = nodos.item(1);//el titulo es el 1
        if (n.getNodeType() == Node.ELEMENT_NODE) {

            titulo = n.getFirstChild().getNodeValue();
            //Si el titulo cogido del archivo xml es igual al escrito en titulo antiguo se ejecuta este if
            if (tituloAntiguo.equals(titulo)) {
                n.getFirstChild().setNodeValue(tituloNuevo);
            }
        }
    }

    public void modificarDOMparaTituloLibro(String tituloDOM_Antiguo, String tituloDOM_nuevo) {
        //Este metodo recorre el DOM de libros XML y mira los titulos
        Node node;

        Node raiz = doc.getFirstChild();

        NodeList nodeList = raiz.getChildNodes();
        //recorre el DOM (es como el recorrer DOM pero con un if extra y añadidos
        for (int i = 0; i < nodeList.getLength(); i++) {
            node = nodeList.item(i);
            //Aqui se ejecuta cambiarTituloLibro con el node del for
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                cambiarTituloLibro(node, tituloDOM_Antiguo, tituloDOM_nuevo);
            }
        }

    }

}
