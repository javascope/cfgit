/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.workcube.javascopes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Macintosh
 */
public class BitbucketMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Process prc = null;
        try {
            
            File bbDir = new File(System.getProperty("user.home") + "\\bitbucket\\");
            if(!bbDir.exists())
                bbDir.mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(bbDir.getAbsolutePath() + "\\bitbucket.bat"));
            writer.write("echo 'starting script'\r\n");
            writer.write("git init\r\n");
            writer.write("git pull https://esmaruysal:esmauysal123@bitbucket.org/workcube/beta-catalyst.git master\r\n");
            writer.write("echo 'ending script'\r\n");
            writer.flush();
            writer.close();
            
            String[] cmd = { "cmd", "/c", bbDir.getAbsolutePath() + "\\bitbucket.bat" };
            System.out.println(bbDir.getAbsolutePath() + "\\bitbucket.bat");
            ProcessBuilder pBuilder = new ProcessBuilder();
            pBuilder.command(cmd);
            pBuilder.directory(new File("c:\\bitbucket\\"));
            prc = pBuilder.start();
            System.out.println("shell script is executed.");
            prc.waitFor(); 
            BufferedReader reader=new BufferedReader(new InputStreamReader(prc.getInputStream())); 
            System.out.println("reader is initialised.");
            String line; 
            while((line = reader.readLine()) != null) { 
                System.out.println(line);
            } 
        } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
        } catch (InterruptedException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
        }
    }
    
}
