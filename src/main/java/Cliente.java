/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.util.Scanner;
import javax.ws.rs.core.MediaType;

public class Cliente {

    public static void main(String[] args) {
        try {

            Client client = Client.create();
            WebResource webResource = null;
            ClientResponse response = null;
            int opcion = -1;
            Scanner lectura = new Scanner(System.in);
            System.out.println("Bienvenido sistema gestion empleados");
         
            do {
                System.out.println("1. Listar 2. Crear. 3. Borrar. 4.  Salir");
                opcion = lectura.nextInt();
                switch (opcion) {
                    case 1:
                        webResource = client.resource("http://localhost:8080/myapp/paciente");

                        response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

                        if (response.getStatus() != 200) {
                            throw new RuntimeException("Failed : HTTP error code : "
                                    + response.getStatus());
                        }

                        String output = response.getEntity(String.class);
                        System.out.println("Output from Server .... \n");
                        System.out.println(output);
                        break;

                    case 2:
                        webResource = client
                                .resource("http://localhost:8080/myapp/paciente");

                        ObjectMapper mapper = new ObjectMapper();
                        Paciente paciente = new Paciente();
                        
                        System.out.println("Ingrese el nombre");
                        String nombre = lectura.next();
                        paciente.setName(nombre);
                        
                        System.out.println("Ingrese la direccion");
                        String direccion = lectura.next();
                        paciente.setAddress(direccion);
                        
                        System.out.println("Ingrese el telefono");
                        String telefono = lectura.next();
                        paciente.setTelephone(telefono);
                        
                        System.out.println("Ingrese la fecha");
                        String fecha = lectura.next();
                        paciente.setDate(fecha);
                        
                        System.out.println("Ingrese el dato del contacto");
                        String contacto = lectura.next();
                        paciente.setContacto(contacto);
                        
                        String input = mapper.writeValueAsString(paciente);
                        //Luego se utilizara Jackson
                        //String input = "{\"empNo\":\"E11\",\"empName\":\"" + nombre + "\",\"position\":\"Salesman\"}";

                        response = webResource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, input);

                        if (response.getStatus() != 200) {
                            System.out.println(response.toString());
                            throw new RuntimeException("Failed : HTTP error code : "
                                    + response.getStatus());
                        }

                        output = response.getEntity(String.class);
                        System.out.println(output);
                        break;

                    case 3:
                        System.out.println("Indique el Nombre del paciente");
                        String nombre3 = lectura.next();
                        webResource = client.resource("http://localhost:8090/myapp/paciente/" + nombre3);

                        response = webResource.accept(MediaType.APPLICATION_JSON).delete(ClientResponse.class);

                        if (response.getStatus() != 204) {
                            throw new RuntimeException("Failed : HTTP error code : "
                                    + response.getStatus());
                        }

                        break;
                    default:
                        System.out.println("Opcion invalida");

                }

            } while (opcion != 4);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}
