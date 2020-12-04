/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.io.File;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javapeliculas.Peliculas;
import javapeliculas.Peliculas.Pelicula;

public class JAXB {

    Peliculas misPeliculas;

    public int abrir_XML_JAXB(File fichero) {

        try {
            //Crea una instancia JAXB
            JAXBContext contexto = JAXBContext.newInstance(Peliculas.class);
            //Crea un oobjeto Unmarshaller
            Unmarshaller u = contexto.createUnmarshaller();
            //Deserializa (unmarshal) el fichero
            misPeliculas = (Peliculas) u.unmarshal(fichero);
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    public String recorrerJAXB() {

        String cadena_resultado = "";

        List<Peliculas.Pelicula> lLibros = misPeliculas.getPelicula();

        for (int i = 0; i < lLibros.size(); i++) {
            Pelicula libro_temp = lLibros.get(i);
            cadena_resultado = cadena_resultado + "\nCódigo IMBD: " + libro_temp.getIdIMBD();

            cadena_resultado = cadena_resultado + "\nTitulo: " + libro_temp.getTitulo();

            cadena_resultado = cadena_resultado + "\n------------------------";
        }

        return cadena_resultado;
    }

    void modificarJAXBparaTituloLibro(String text, String text0) {
        
    }

}
