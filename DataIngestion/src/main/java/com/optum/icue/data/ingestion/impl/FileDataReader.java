package com.optum.icue.data.ingestion.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.optum.icue.data.ingestion.DataReader;

public class FileDataReader implements DataReader {

    public void read() {
        // We need to provide file path as the parameter:
        // double backquote is to avoid compiler interpret words
        // like \test as \t (ie. as a escape sequence)
        File file = new File("C:\\Users\\pankaj\\Desktop\\test.txt");

        BufferedReader br;
        String st;
		try {
			br = new BufferedReader(new FileReader(file));
			 while ((st = br.readLine()) != null)
		            System.out.println(st);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
       
    }

    private void readFile(String filePath){
        if(null == filePath || filePath.isEmpty()){
            throw new IllegalArgumentException("FilePath can not be null");
        }



}
}
