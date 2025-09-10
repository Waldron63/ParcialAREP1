/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.eci.arep.parcialarep1;

import java.net.*;
import java.io.*;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.LinkedList;

public class HttpServer {
    
    public static LinkedList<Float> numbers = new LinkedList<>();
    
    public static void main(String[] args) throws IOException, URISyntaxException {
        ServerSocket serverSocket = null;
        try { 
             serverSocket = new ServerSocket(36000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 36000.");
            System.exit(1);
        }
        boolean istrue = true;
        while (istrue){
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                 System.exit(1);
            }
            PrintWriter out = new PrintWriter(
                             clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                             new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;
            boolean isFirstLine = true;
            String firstLine = "";
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Recibí: " + inputLine);
                if (isFirstLine){
                    firstLine = inputLine;
                    isFirstLine = false;
                }
                if (!in.ready()) {break; }
            }
            URI request = new URI(firstLine.split(" ")[1]);
            String path = request.getPath();
            String query = request.getQuery();
            if (path.contains("/add")){
                outputLine = addNumber(query);
            }else if(path.contains("/list")){
                outputLine = ListNumber();
            }else if(path.contains("/clear")){
                outputLine = clearNumber();
            }else if(path.contains("/stats")){
                outputLine = statsNumber(path);
            }else{
                outputLine = errPath();
            }
            out.println(outputLine);
            out.close(); 
            in.close(); 
            clientSocket.close(); 
        }
        serverSocket.close(); 
    }
    
    public static String addNumber(String query){
        String numberString = query.split("=")[1];
        try{
            float number = Float.valueOf(numberString);
            numbers.add(number);
            return "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: application/json\r\n"
                    + "\r\n"
                    + "{\n"
                    + "\"status\": \"OK\",\n"
                    + "\"added\": " + numberString + ",\n"
                    + "\"count\": " + numbers.size() + "\n"
                    + "}";
        }catch(Exception e){
            return errNumber();
        }
    }
    
    public static String ListNumber(){
        String listString = numbers.toString();
        System.out.println(listString);
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: application/json\r\n"
                + "\r\n"
                + "{\n"
                + "\"status\": \"OK\",\n"
                + "\"values\": " + listString + "\n"
                + "}";
    }
    
    public static String clearNumber(){
        numbers.clear();
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: application/json\r\n"
                + "\r\n"
                + "{\n"
                + "\"status\": \"OK\",\n"
                + "\"message\": \"list_cleared\"\n"
                + "}";
    }
    
    public static String statsNumber(String path){
        if (!numbers.isEmpty()){
            //media
            float total = 0;
            float cantTotal = numbers.size();
            for (float i : numbers){
                total += i;
            }
            float media = total / cantTotal;
            
            //desviacion estandar
            float desv = 0;
            float est;
            for (float j : numbers){
                est = j - media;
                est = (float) pow(est, 2);
                desv += est;
            }
            desv = desv / (cantTotal - 1);
            desv = (float) sqrt(desv);
            
            //encabezado
            return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: application/json\r\n"
                + "\r\n"
                + "{\n"
                + "\"status\": \"OK\",\n"
                + "\"mean\": " + media + ",\n"
                + "\"stddev\": " + desv + ",\n"
                + "\"count\":" + numbers.size() + "\n"
                + "}";
        }else{
            return errPath();
        }
    }
    
    public static String errPath(){
        return "HTTP/1.1 409 ERR\r\n"
                + "Content-Type: application/json\r\n"
                + "\r\n"
                + "{\n"
                + "\"status\": \"ERR\",\n"
                + "\"error\": \"empty_list\"n\n"
                + "}";
    }

    private static String errNumber() {
        return "HTTP/1.1 400 ERR\r\n"
                + "Content-Type: application/json\r\n"
                + "\r\n"
                + "{\n"
                + "\"status\": \"ERR\",\n"
                + "\"error\": \"invalid_number\"n\n"
                + "}";
    }
}