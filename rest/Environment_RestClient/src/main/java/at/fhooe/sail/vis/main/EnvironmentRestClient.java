package at.fhooe.sail.vis.main;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Basic Rest Client-Class for testing Client and Server Communication.
 */
public class EnvironmentRestClient {
    /**
     *
     * @param _args
     * Main entry point that calls multiple endpoint on the EnvironmentRestServer
     */
    public static void main(String[] _args) {
        try {
            String charset = "UTF-8";
            String targetUrl = "http://localhost:8080/EnvironmentRestServer";

            System.out.println("http://localhost:8080/EnvironmentRestServer");
            executeGetRequest(targetUrl);
            System.out.println("------------------");

            targetUrl = "http://localhost:8080/EnvironmentRestServer/info/sensortypes";
            String query = String.format("output=%s",
                    URLEncoder.encode("XML", charset));
            System.out.println("http://localhost:8080/EnvironmentRestServer/info/sensortypes?output=XML");
            executeGetRequest(targetUrl,query);
            System.out.println("------------------");

            query = String.format("output=%s",
                    URLEncoder.encode("JSON", charset));
            System.out.println("http://localhost:8080/EnvironmentRestServer/info/sensortypes?output=JSON");
            executeGetRequest(targetUrl,query);
            System.out.println("------------------");

            targetUrl = "http://localhost:8080/EnvironmentRestServer/data/light";
            System.out.println("http://localhost:8080/EnvironmentRestServer/data/light?output=JSON");
            executeGetRequest(targetUrl,query);
            System.out.println("------------------");

            query = String.format("output=%s",
                    URLEncoder.encode("XML", charset));
            System.out.println("http://localhost:8080/EnvironmentRestServer/data/light?output=XML");
            executeGetRequest(targetUrl,query);
            System.out.println("------------------");

            targetUrl = "http://localhost:8080/EnvironmentRestServer/data/ALL";
            System.out.println("http://localhost:8080/EnvironmentRestServer/data/ALL?output=XML");
            executeGetRequest(targetUrl,query);
            System.out.println("------------------");

            query = String.format("output=%s",
                    URLEncoder.encode("JSON", charset));
            System.out.println("http://localhost:8080/EnvironmentRestServer/data/ALL?output=JSON");
            executeGetRequest(targetUrl,query);
            System.out.println("------------------");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Baisc Http get call method
     * @param targetURL target URL for the call
     * @param query query parameters for the call
     */
    public static void executeGetRequest(String targetURL, String query){
        try {
            URLConnection connection = new URL(targetURL + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            InputStream response = connection.getInputStream();
            try (Scanner scanner = new Scanner(response)) {
                String responseBody = scanner.useDelimiter("\\A").next();
                System.out.println(responseBody);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Basic http get call without query parameters
     * @param targetURL target URL for the call
     */
    public static void executeGetRequest(String targetURL){
        try {
            URLConnection connection = new URL(targetURL).openConnection();
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            InputStream response = connection.getInputStream();
            try (Scanner scanner = new Scanner(response)) {
                String responseBody = scanner.useDelimiter("\\A").next();
                System.out.println(responseBody);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}