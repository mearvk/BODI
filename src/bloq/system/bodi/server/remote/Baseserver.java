package bloq.system.bodi.server.remote;

import java.io.BufferedReader;

import java.io.BufferedWriter;

import java.io.IOException;

import java.io.InputStreamReader;

import java.io.OutputStreamWriter;

import java.net.InetAddress;

import java.net.ServerSocket;

import java.net.SocketException;

import java.util.ArrayList;

/**
 *
 * New trade era of Service, Protocol, Method [first touch /mr /ok /ss]
 * 
 * @author Max Rupplin
 */
public abstract class Baseserver extends Thread
{
    public Integer hash = 0x008808ef;
    
    public String host = "localhost";
    
    public InetAddress address;
    
    public Integer port;
    
    public ServerSocket serversocket;    
    
    public Boolean doread;
    
    public Boolean dowrite;
    
    public Boolean isdonereading;
    
    public Boolean isdonewriting;       
    
    public Boolean running = true;
    
    public ArrayList<Networkcontext> connections = new ArrayList();
    
    public Inputqueue connectionqueue = new Inputqueue();
    
    /**
     * 
     * @param host
     * @param port 
     */
    public Baseserver(String host, Integer port)
    {
        if(host==null || port==null) throw new SecurityException("//bodi/connect");
        
        this.host = host;
        
        this.port = port;        
        
        this.setName("Basicserverthread");
        
        try
        {
            this.address = InetAddress.getByName(host);
        }
        catch(Exception e)
        {
            e.printStackTrace(System.err);
            
            return;
        }        
        
        try
        {
            this.serversocket = new ServerSocket(this.port, 4096, this.address);
        }
        catch(Exception e)
        {
            e.printStackTrace(System.err);

            return;
        }
        finally
        {
            System.out.println("Serversocket created on port "+this.port);
        }           
    }
    
    /**
     * 
     * @param port 
     */
    public Baseserver(Integer port)
    {
        if(port==null) throw new SecurityException("//bodi/connect");
        
        this.port = port;      
        
        this.setName("Basicserverthread");
        
        try
        {
            this.address = InetAddress.getByName(host);
        }
        catch(Exception e)
        {
            e.printStackTrace(System.err);
            
            return;
        }        
        
        try
        {
            this.serversocket = new ServerSocket(this.port, 4096, this.address);
        }
        catch(Exception e)
        {
            e.printStackTrace(System.err);

            return;
        }
        finally
        {
            System.out.println("Serversocket created on port "+this.port);
        }                        
    }    
    
    /**
     * 
     */
    @Override
    public void run()
    {
        try
        {
            while(running)
            {                                                    
                Networkcontext connection;
                
                connection = new Networkcontext(this);

                connection.socket = this.serversocket.accept(); 
                
                connection.remoteaddress = connection.socket.getRemoteSocketAddress().toString();
                
                System.out.println("> New remote connection established...");
                
                try
                {
                    connection.inputstream = connection.socket.getInputStream();

                    connection.reader = new BufferedReader(new InputStreamReader(connection.inputstream));            
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.err);

                    return;
                }
                finally
                {
                    System.out.println(">   Related reader established...");
                }

                try
                {
                    connection.outputstream = connection.socket.getOutputStream();

                    connection.writer = new BufferedWriter(new OutputStreamWriter(connection.outputstream));            
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.err);

                    return;
                }   
                finally
                {
                    System.out.println(">   Related writer established...");
                }

                try
                {                        
                    connection.thread = new Listenerthread(connection);

                    connection.thread.start();                
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.err);

                    return;
                }
                finally
                {
                    System.out.println(">   Related I/O listener thread established...");
                }

                this.connections.add(connection);                                
            }
        }
        catch(SocketException se)
        {
            se.printStackTrace(System.err);
        }        
        catch(IOException ioe)
        {
            ioe.printStackTrace(System.err);
        }
    }
    
    /**
     * 
     * @param bytes 
     */
    public abstract void write(byte[] bytes);
    
    /**
     * 
     * @return 
     */
    public abstract byte[] read();   
}