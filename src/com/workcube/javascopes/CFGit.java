/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.workcube.javascopes;

import com.allaire.cfx.CustomTag;
import com.allaire.cfx.Request;
import com.allaire.cfx.Response;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author javascope/omur
 */
public class CFGit implements CustomTag {

    @Override
    public void processRequest(Request rqst, Response rspns) throws Exception {
        Process prc = null;
        String gitDir = rqst.getAttribute("git-dir");
        String repURL = rqst.getAttribute("repo-url");
        String gitBranch = rqst.getAttribute("branch");
        String gitUsername = rqst.getAttribute("username");
        String gitPass = rqst.getAttribute("passwrd");
        String sepBranch = rqst.getAttribute("sepBranch");
        boolean isWindows = false;
        rspns.write("<b>The sent values:</b><br>");
        rspns.write("<br>git-dir ---> " + gitDir);
        rspns.write("<br>repo-url ---> " + repURL);
        rspns.write("<br>branch ---> " + gitBranch);
        rspns.write("<br>username ---> " + gitUsername);
        rspns.write("<br>passwrd ---> " + gitPass);
        try {
            
            if(gitDir.indexOf(":") != -1 || gitDir.indexOf("\\") != -1)
                isWindows = true;
            
            File bbDir = new File(gitDir + (isWindows ? "\\bitbucket\\" : "/bitbucket/"));
            if(!bbDir.exists())
                bbDir.mkdir();
            if(sepBranch.equals("1")){
                bbDir = new File(gitDir + (isWindows ? "\\" + gitBranch + "\\" : "/" + gitBranch + "/"));
                if(!bbDir.exists())
                    bbDir.mkdir();
                rspns.write("<br>Separate dir is created for current branch ---> " + bbDir.getAbsolutePath());
                gitDir = bbDir.getAbsolutePath();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(bbDir.getAbsolutePath() + (isWindows ? "\\bitbucket.bat" : "/bitbucket.sh")));
            writer.write("echo 'starting script'\n");
            writer.write("git init\n");
            writer.write("git pull https://" + gitUsername + ":" + gitPass + "@" + repURL.substring(8) + " " + gitBranch + "\n");
            writer.write("git checkout origin/" + gitBranch + "\n");
            writer.write("echo 'ending script'\n");
            writer.flush();
            writer.close();
            
            String[] cmd = { isWindows ? "cmd" : "sh", "/c", bbDir.getAbsolutePath() + (isWindows ? "\\bitbucket.bat" : "/bitbucket.sh") };
            System.out.println(bbDir.getAbsolutePath() + (isWindows ? "\\bitbucket.bat" : "/bitbucket.sh"));
            ProcessBuilder pBuilder = new ProcessBuilder();
            pBuilder.command(cmd);
            pBuilder.directory(new File(gitDir));
            prc = pBuilder.start();
            rspns.write("<br>shell script is executed.");
            prc.waitFor(); 
            BufferedReader reader=new BufferedReader(new InputStreamReader(prc.getInputStream())); 
            rspns.write("<br>reader is initialised.");
            String line; 
            while((line = reader.readLine()) != null) { 
                rspns.write("<br>" + line);
            } 
        } catch (IOException e) {
         // TODO Auto-generated catch block
         rspns.write("<br>Git IOException ---> " + e.getMessage());
        } catch (InterruptedException e) {
         // TODO Auto-generated catch block
         rspns.write("<br>Git InterruptedException ---> " + e.getMessage());
        }
    }
    
}
