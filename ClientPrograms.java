//package com.client;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

public class ClientPrograms {

	

	public static void main(String[] args) throws IOException {

		Scanner sc=new Scanner(System.in);
		
		String method=args[0];
		String uri=args[1];
		/*String method=sc.nextLine();
		String uri=sc.nextLine();*/
		String response ="",statusCode="";
		int port=80;
		//String temp=uri.substring(0, )
		
		int counter=0,test=0,blah=0,a=0,count=0,status = 0;
		try{
			if(uri.startsWith("https"))
				System.exit(1);
			if(!uri.startsWith("http"))
				System.exit(1);
			
			//if(uri.charAt(uri.lastIndexOf(':')+1)!='/')
				
			//port=(String) uri.subSequence(uri.lastIndexOf(':')+1, uri.indexOf("/", uri.lastIndexOf(':')));
		
			//boolean addressFlag=false;
			URL url = new URL(uri);
			if(url.getPort()!=-1)
				port=url.getPort();
			else
				port=80;
			//System.out.println(port);
	        InetAddress[] inetAddresses=InetAddress.getAllByName(url.getHost());
	        Socket s=new Socket();
	        
	        
	        label:for(int k=0;k<inetAddresses.length;k++)
	        {
	        	try{
	        		s=new Socket(inetAddresses[k], port);
	        		if(s.isConnected())
	        			break;
	        	}
	        	catch(ConnectException e){
	        		continue label;
	        	}
	        	/*catch(Exception e){
	        		System.exit(1);
	        	}*/
	        }
			
	        /*System.out.println(url.getHost());
	        System.out.println(url.getDefaultPort());*/
	        
	        //Socket s=new Socket(url.getHost(),url.getDefaultPort());
	        String host=url.getHost();
	        
	        String headerOne="";
	        if(url.getPort()!=-1)
	        	headerOne=uri.substring(host.length()+(port+"").length()+1+uri.indexOf('/')+2, uri.length());
	        else
	        headerOne=uri.substring(host.length()+uri.indexOf('/')+2, uri.length());
	        if(headerOne.equals(""))
	        	headerOne+="/";
	        //System.out.println(headerOne);
	        
	        String header=method+" "+headerOne+" HTTP/1.1\nHost: "+host+"\nUser-Agent: curl/7.37.1\nAccept: */*\nConnection: close\r\n";
	                
	        BufferedReader input =new BufferedReader(new InputStreamReader(s.getInputStream(),"cp1256"));
	        
	
			PrintStream sout=new PrintStream(s.getOutputStream());
			String st;
			//System.out.print("Client : "+header);
			
			sout.println(header);
			String inputLine="";
			
			
			
			int chunk=100;
			//String body="";
			String location="";
			String prev=inputLine;
		
			boolean flag=false,check=true,chunkFlag=false,headerFlag=true,last=false;
			one:while (true)
			{
				inputLine = input.readLine();
				
				while(headerFlag){
					response+=inputLine+"\n";
					
					if(inputLine.contains("chunked"))
						chunkFlag=true;
					inputLine = input.readLine();
					prev=inputLine;
					chunk--;
					if(inputLine.isEmpty()){
						if(chunkFlag)
							inputLine=input.readLine();
						headerFlag=false;
					}
				}
				//input.read()
				statusCode=response.substring(response.indexOf('.')+3, response.indexOf('.')+6);
				//if(count<5){
				switch(statusCode){
				
				case "200":
					status=0;
					//count=10;
					break;
				case "301":
					count++;
					if(count>5)
						System.exit(1);
					location=response.subSequence(response.indexOf("Location")+10, response.indexOf("\n", response.indexOf("Location"))).toString();
					//System.out.println(location);
					if(location.startsWith("https"))
						System.exit(1);
					if(!location.startsWith("http"))
						System.exit(1);
					url=new URL(location);
					if(url.getPort()!=-1)
						port=url.getPort();
					else
						port=80;
					s=new Socket(url.getHost(),port);
			        host=url.getHost();
			        //System.out.println("\n\nPORT\n"+url.getPort()+"\n\n");
			        if(url.getPort()!=-1)
			        	headerOne=location.substring(host.length()+(port+"").length()+1+location.indexOf('/')+2, location.length());
			        else
			        headerOne=location.substring(host.length()+location.indexOf('/')+2, location.length());
			        //headerOne=location.substring(host.length()+location.indexOf('/')+2, location.length());
			        if(headerOne.equals(""))
			        	headerOne+="/";
			        
			        header=method+" "+headerOne+" HTTP/1.1\nHost: "+host+"\nUser-Agent: curl/7.37.1\nAccept: */*\nConnection: close\r\n";
			                
			        input =new BufferedReader(new InputStreamReader(s.getInputStream(),"cp1256"));
			        
	
					sout=new PrintStream(s.getOutputStream());
					sout.println(header);
					inputLine="";
					prev=inputLine;
					headerFlag=true;
					/*inputLine=input.readLine();
					inputLine=input.readLine();*/
					response="";
					/*if(count<5)
						continue one;
					else
						status=1;*/
					break;
					
				case "302":
					//System.out.println(count);
					count++;
					if(count>5)
						System.exit(1);
					location=response.subSequence(response.indexOf("Location")+10, response.indexOf("\n", response.indexOf("Location"))).toString();
					if(location.startsWith("https"))
						System.exit(1);
					if(!location.startsWith("http"))
						System.exit(1);
					url=new URL(location);
					if(url.getPort()!=-1)
						port=url.getPort();
					else
						port=80;
					s=new Socket(url.getHost(),port);
			        host=url.getHost();
			        if(url.getPort()!=-1)
			        	headerOne=location.substring(host.length()+(port+"").length()+1+location.indexOf('/')+2, location.length());
			        else
			        headerOne=location.substring(host.length()+location.indexOf('/')+2, location.length());
			        //headerOne=location.substring(host.length()+location.indexOf('/')+2, location.length());
			        if(headerOne.equals(""))
			        	headerOne+="/";
			        
			        header=method+" "+headerOne+" HTTP/1.1\nHost: "+host+"\nUser-Agent: curl/7.37.1\nAccept: */*\nConnection: close\r\n";
			                
			        input =new BufferedReader(new InputStreamReader(s.getInputStream(),"cp1256"));
			        
	
					sout=new PrintStream(s.getOutputStream());
					sout.println(header);
					inputLine="";
					prev=inputLine;
					headerFlag=true;
					response="";
					/*if(count<5)
						continue one;
					else
						status=1;*/
					break;
				
				case "303":
					count++;
					if(count>5)
						System.exit(1);
					location=response.subSequence(response.indexOf("Location")+10, response.indexOf("\n", response.indexOf("Location"))).toString();
					if(location.startsWith("https"))
						System.exit(1);
					if(!location.startsWith("http"))
						System.exit(1);
					url=new URL(location);
					if(url.getPort()!=-1)
						port=url.getPort();
					else
						port=80;
					s=new Socket(url.getHost(),port);
			        host=url.getHost();
			        if(url.getPort()!=-1)
			        	headerOne=location.substring(host.length()+(port+"").length()+1+location.indexOf('/')+2, location.length());
			        else
			        headerOne=location.substring(host.length()+location.indexOf('/')+2, location.length());
			        //headerOne=location.substring(host.length()+location.indexOf('/')+2, location.length());
			        if(headerOne.equals(""))
			        	headerOne+="/";
			        
			        header=method+" "+headerOne+" HTTP/1.1\nHost: "+host+"\nUser-Agent: curl/7.37.1\nAccept: */*\nConnection: close\r\n";
			                
			        input =new BufferedReader(new InputStreamReader(s.getInputStream(),"cp1256"));
			        
	
					sout=new PrintStream(s.getOutputStream());
					sout.println(header);
					inputLine="";
					prev=inputLine;
					headerFlag=true;
					response="";
					/*if(count<5)
						continue one;
					else
						status=1;*/
					break;
				
				case "307":
					//status=3;
					count++;
					if(count>5)
						System.exit(1);
					location=response.subSequence(response.indexOf("Location")+10, response.indexOf("\n", response.indexOf("Location"))).toString();
					if(location.startsWith("https"))
						System.exit(1);
					if(!location.startsWith("http"))
						System.exit(1);
					url=new URL(location);
					if(url.getPort()!=-1)
						port=url.getPort();
					else
						port=80;
					s=new Socket(url.getHost(),port);
			        host=url.getHost();
			        if(url.getPort()!=-1)
			        	headerOne=location.substring(host.length()+(port+"").length()+1+location.indexOf('/')+2, location.length());
			        else
			        headerOne=location.substring(host.length()+location.indexOf('/')+2, location.length());
			        //headerOne=location.substring(host.length()+location.indexOf('/')+2, location.length());
			        if(headerOne.equals(""))
			        	headerOne+="/";
			        
			        header=method+" "+headerOne+" HTTP/1.1\nHost: "+host+"\nUser-Agent: curl/7.37.1\nAccept: */*\nConnection: close\r\n";
			                
			        input =new BufferedReader(new InputStreamReader(s.getInputStream(),"cp1256"));
			        
	
					sout=new PrintStream(s.getOutputStream());
					sout.println(header);
					inputLine="";
					prev=inputLine;
					headerFlag=true;
					response="";
					/*if(count<5)
						continue one;
					else
						status=1;*/
					break;
					
				default:
					/*if(statusCode.charAt(0)=='4')
						status=4;*/
					//count=10;
					status=Integer.parseInt(statusCode.charAt(0)+"");
					break;
				}
				//}
				if(inputLine==null)
					break;
				
				while(chunkFlag){
					if(inputLine==null)
						break one;
					a=Integer.parseInt(inputLine, 16)+2;
					if(a<0){
						//System.out.println("\nbreakone\n+"+a);
						break one;
					}
					while(a>0){
						inputLine=(char)input.read()+"";
						a-=inputLine.length();
						System.out.print(inputLine);
					}
					inputLine=input.readLine();
					
				}
					if(!flag)
					chunk--;
					
					if(inputLine==null)
						break one;
					if(check){
					System.out.print(inputLine);
					prev=inputLine;
					}
			}
		}
		/*catch(ConnectException e){
			continue label;
		}*/
		catch(Exception e){
			//System.out.println(e);
			status=1;
		}
		//System.out.println();
		/*System.out.println("\n\n\nResponse:\n"+response+"\n\n");
		System.out.println("\n\n\nLAST: "+status);*/
		//System.out.println("\nLAST\n"+status);
		System.exit(status);
    }
	
}
