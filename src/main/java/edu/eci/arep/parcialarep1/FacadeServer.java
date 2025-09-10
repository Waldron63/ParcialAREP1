/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.eci.arep.parcialarep1;

import java.net.*;
import java.io.*;

public class FacadeServer {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_URL = "http://localhost:36000/";

    
    public static void main(String[] args) throws IOException, URISyntaxException {
        ServerSocket serverSocket = null;
        try { 
            serverSocket = new ServerSocket(45000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 45000.");
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
            if (path.startsWith("/cliente")){
                if(path.contains("e/")){
                    outputLine = httpConnection(request);
                }else{
                    outputLine = webServer();
                }
            }else{
                outputLine = errServer();
            }
            out.println(outputLine);
            out.close(); 
            in.close(); 
            clientSocket.close(); 
        }
        serverSocket.close(); 
    }
    
    public static String webServer(){
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                 + "\r\n"
                    +"<!DOCTYPE html>\n"
                    +"<html>\n"
                   + "<head>\n"
                        +"<title>Form Example</title>\n"
                        +"<meta charset=\"UTF-8\">\n"
                        +"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    +"</head>\n"
                    +"<body>\n"
                    +"<h1>Form with GET</h1>\n"
                    + "<h2>add</h2>\n"
        + "<form action=\"/cliente\">\n"
            + "<label for=\"add\">add:</label><br>\n"
            + "<input type=\"text\" id=\"add\" name=\"add\"><br><br>\n"
            + "<input type=\"button\" value=\"Submit\" onclick=\"loadAddInteger()\">\n"
        + "</form>\n"
        + "<div id=\"getrespinteger\"></div>\n"

    + "<h2>list</h2>\n"
    + "<form action=\"/cliente\">\n"
        + "<label for=\"list\">List:</label><br>\n"
        + "<input type=\"button\" id =\"list\" value=\"Submit\" onclick=\"loadlistinteger()\">\n"
    + "</form>\n"
    + "<div id=\"getresplist\"></div>\n"

    + "<h2>clear</h2>\n"
    + "<form action=\"/cliente\">\n"
        + "<label for=\"clear\">Clear:</label><br>\n"
        + "<input type=\"button\" id=\"clear\" value=\"Submit\" onclick=\"loadclearlist()\">\n"
    + "</form>\n"
    + "<div id=\"getrespclear\"></div>\n"

    + "<h2>stats</h2>\n"
    + "<form action=\"/cliente\">\n"
        + "<label for=\"stats\">Stats:</label><br>\n"
        + "<input type=\"button\" id=\"stats\" value=\"Submit\" onclick=\"loadstatlist()\">\n"
    + "</form>\n"
    + "<div id=\"getrespstats\"></div>\n"
    + "<script>\n"
    + "function loadAddInteger() {\n"
        + "let nameVar = document.getElementById(\"add\").value;\n"
                + "const xhttp = new XMLHttpRequest();\n"
        + "xhttp.onload = function () {\n"
            + "document.getElementById(\"getrespinteger\").innerHTML =\n"
                    + "this.responseText;\n"
        + "}\n"
        + "xhttp.open(\"GET\", \"/cliente/add?x=\" + nameVar);\n"
        + "xhttp.send();\n"
    + "}\n"

    + "function loadlistinteger() {\n"
        + "let nameVar = document.getElementById(\"list\").value;\n"
                + "const xhttp = new XMLHttpRequest();\n"
        + "xhttp.onload = function () {\n"
            + "document.getElementById(\"getresplist\").innerHTML =\n"
                    + "this.responseText;\n"
        + "}\n"
        + "xhttp.open(\"GET\", \"/cliente/list\");\n"
        + "xhttp.send();\n"
    + "}\n"

    + "function loadclearlist() {\n"
        + "let nameVar = document.getElementById(\"clear\").value;\n"
                + "const xhttp = new XMLHttpRequest();\n"
        + "xhttp.onload = function () {\n"
            + "document.getElementById(\"getrespclear\").innerHTML =\n"
                    + "this.responseText;\n"
        + "}\n"
        + "xhttp.open(\"GET\", \"/cliente/clear\");\n"
        + "xhttp.send();\n"
    + "}\n"

    + "function loadstatlist() {\n"
        + "let nameVar = document.getElementById(\"stats\").value;\n"
                + "const xhttp = new XMLHttpRequest();\n"
        + "xhttp.onload = function () {\n"
            + "document.getElementById(\"getrespstats\").innerHTML =\n"
                    + "this.responseText;\n"
        + "}\n"
        + "xhttp.open(\"GET\", \"/cliente/stats\");\n"
        + "xhttp.send();\n"
    + "}\n"
        + "</script>\n"
                    +"</body>\n"
                    +"</html>\n";
    }
    
    public static String errServer(){
        return "HTTP/1.1 400 ERR\r\n"
                + "Content-Type: text/html\r\n"
                 + "\r\n"
                    +"<!DOCTYPE html>\n"
                    +"<html>\n"
                   + "<head>\n"
                        +"<title>Error 404</title>\n"
                        +"<meta charset=\"UTF-8\">\n"
                        +"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    +"</head>\n"
                    +"<body>\n"
                        +"<h1>URL not found</h1>\n"
                    +"</body>\n"
                    +"</html>\n";
    }
  
    public static String httpConnection(URI path) throws IOException{
        URL obj = new URL(GET_URL + path);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        
        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
            return response.toString();
        } else {
            System.out.println("GET request not worked");
            return errServer();
        }
        //System.out.println("GET DONE");
    }
}