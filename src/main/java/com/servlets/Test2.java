package com.servlets;

import java.io.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@WebServlet("/Test2")
public class Test2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String filePath = "/home/christ-tt0478/Joeho/demo.txt";
		Argon2 argon2 = Argon2Factory.create();
		char[] password = request.getParameter("password").toCharArray();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            String output = "";
	            while ((line = reader.readLine()) != null) {
	                output += line;
	            }
	            if (argon2.verify(output, password)) {
	            	out.println("Correct Password");
	            }
	            else {
	            	out.println("Incorrect Password");
	            }
	        } catch (FileNotFoundException e) {
	            out.println("File not found.");
	        } catch (IOException e) {
	            out.println("Error reading the file.");
	        }
	}

}
