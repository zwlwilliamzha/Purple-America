package com.purple.america.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;

public class ReadData {
	public static void readTxtFile(String filePath){
        try {
                String encoding="UTF-8";
                ArrayList<ArrayList<String>> data=new ArrayList<ArrayList<String>>();
                ArrayList<String> blockData=new ArrayList<String>();
                File file=new File(filePath);                
                if(file.isFile() && file.exists()){ 
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    int j=0;
                    while((lineTxt = bufferedReader.readLine()) != null){
                    	if("".equals(lineTxt)){
                    		j=0;
                    		data.add(blockData);
                    		blockData=new ArrayList<String>();
                    		
                    	}
                    	else{
                    		blockData.add(lineTxt);
                    		if(j==0)
                    		System.out.println(Double.valueOf(lineTxt.split("   ")[0].trim()));
                    	}
                    	
                    	j++;
                    	
                    }
                    read.close();
                    System.out.print(data.size());
                    
        }else{
            System.out.println("找不到指定的文件");
        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
     
    }
	
	public static void countNullLine(String filePath){
		try {
            String encoding="UTF-8";
            Hashtable<String,ArrayList<String>> mapData=new Hashtable<String,ArrayList<String>>();
            File file=new File(filePath);                
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file));//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;                
                int j=0;
                while((lineTxt = bufferedReader.readLine()) != null){
                	if("".equals(lineTxt)){
                		j++;
                		
                	}               	
                	
                }
                read.close();
                System.out.print(j);
                
    }else{
        System.out.println("找不到指定的文件");
    }
    } catch (Exception e) {
        System.out.println("读取文件内容出错");
        e.printStackTrace();
    }
	}
	public static void main(String argv[]){
        String filePath = System.getProperty("user.dir")+File.separator+"USA.txt";
        
        
        //readTxtFile(filePath);
        countNullLine(filePath);
    }

}
