/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javapeliculas.Peliculas;
import javapeliculas.Peliculas.Pelicula;
import javax.xml.bind.Marshaller;

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

        List<Peliculas.Pelicula> lPeliculas = misPeliculas.getPelicula();

        for (int i = 0; i < lPeliculas.size(); i++) {
            Pelicula pelicula_temp = lPeliculas.get(i);
            
            cadena_resultado = cadena_resultado + "\nCódigo IMBD: " + pelicula_temp.getIdIMBD();

            cadena_resultado = cadena_resultado + "\nTitulo: " + pelicula_temp.getTitulo();

            cadena_resultado = cadena_resultado + "\nDirector: " + pelicula_temp.getDirector();

            cadena_resultado = cadena_resultado + "\n------------------------";
        }

        return cadena_resultado;
    }

    public void modificarJAXBparaTituloPelicula(String tituloAntiguo, String tituloNuevo) {
        //PARA MODIFICAR TITULO
        List<Peliculas.Pelicula> lpeliculas = misPeliculas.getPelicula();
        for (int i = 0; i < lpeliculas.size(); i++) {
            // if (codIMBD.equals(peliculas.get(i).getIdIMBD())) {
            //CONDICION POR CAMPO
            if (tituloAntiguo.equals(lpeliculas.get(i).getTitulo())) {
                lpeliculas.get(i).setTitulo(tituloNuevo);
            }
            /*SI POR EJEMPLO SE QUISIESE CAMBIAR OTRO CAMPO
             if (CAMPOANTIGUO.equals(lpeliculas.get(i).getCAMPOACAMBIAR())) {
                lpeliculas.get(i).setTitulo(CAMPONUEVO);
                }
             */
        }

        // }
    }

    public void guardarEstructuraJAXB(String salida) {
        try {
            //Lo mismo que en la tarea anterior pero en vez de UNmarshaller --> Marshaller
            //Crea una instancia JAXB
            JAXBContext contexto = JAXBContext.newInstance(Peliculas.class);
            //Crea un oobjeto marshaller
            Marshaller u = contexto.createMarshaller();
            u.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            //Escribe en la salida el fichero
            u.marshal(misPeliculas, new FileWriter("TitulosNuevos.xml"));

        } catch (Exception e) {
            System.out.println("ERROR AL GUARDAR CON JAXB");
        }
    }

}
