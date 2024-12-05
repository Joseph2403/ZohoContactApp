package com.servlets;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import java.io.BufferedWriter;
import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		char[] password = request.getParameter("password").toCharArray();
		PrintWriter out = response.getWriter();
		 
        Argon2 argon2 = Argon2Factory.create();
        String filePath = "/home/christ-tt0478/Joeho/demo.txt";
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
        	String hash = argon2.hash(10, 65535, 1, password);
            writer.write(hash);
            out.println("Data has been written to the file.");
        } catch (IOException e) {
            out.println("Error writing to the file.");
        } finally {
            argon2.wipeArray(password);
        }
	}

}
