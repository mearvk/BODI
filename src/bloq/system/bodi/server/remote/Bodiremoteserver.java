package bloq.system.bodi.server.remote;

import bloq.system.bodi.Bndi;

import static bloq.system.bodi.server.remote.Bodiprotocol.*;

import java.util.Collection;

import java.util.Objects;

/**
 * Hello transactional service based pay-as-you-attend internet (Bodi)
 * 
 * @author Max Rupplin
 */

//reserve keyword "fortune" at root for bodi and her creator Max R .mr .ok ss;; 

//reserve keyword "harmony adodo aday" for harmond adu^& it formerly lt. serving us marine corps;

//reserve keyword "adaysru aday at hit ado" reserve please for now with forwarding address only. 

public class Bodiremoteserver extends Baseserver         
{                       
    public Integer hash = 0x008808ef;
    
    public Bodiprotocolhandler protocolhandler = new Bodiprotocolhandler();
    
    public Boolean running = true;         
    
    /**
     * 
     * @param args 
     */
    public static void main(String...args)
    {
        Bodiremoteserver server;
        
        server = new Bodiremoteserver("localhost", 8888);
        
        server.start();
        
        server.attend();                       
    }       
    
    /**
     * 
     * @param host
     * @param port 
     */
    public Bodiremoteserver(String host, Integer port)
    {      
        super(host, port);
        
        if(host==null || port==null) throw new SecurityException("//bodi/connect");
        
        this.host = host;
        
        this.port = port;        
        
        Bndi.setcontext("//bodi/server/remote/bodiconnections");
        
        Bndi.setcontext("//bodi/server/remote/netconnections");
    }
    
    /**
     * 
     * @param port 
     */
    public Bodiremoteserver(Integer port)
    {
        super("localhost", port);
        
        if(port==null) throw new SecurityException("//bodi/connect");
        
        this.port = port;        
        
        Bndi.setcontext("//bodi/server/remote/bodiconnections");
        
        Bndi.setcontext("//bodi/server/remote/netconnections");        
    }
    
    /**
     * Attend new connections and Bodi requests
     */
    public void attend() 
    {                                  
        while(running)
        {                                                
            Bodiservercontext bodiservercontext = null;
            
            Networkcontext networkcontext = this.pollqueuednetworkconnections();                         
                        
            //
            
            try
            {                
                if( this.tryvalidatenetworkconnection(networkcontext) ) //
                {                                                            
                    
                    bodiservercontext = new Bodiservercontext(this, networkcontext, this.pollstoredbodisessions(networkcontext)); //we care to use only existing bodisessions or handshakes                     
                    
                    if( this.tryvalidatebodiconnection(bodiservercontext) ) 
                    {                                                                                              
                        
                        if(bodiservercontext.inputstring.startsWith(CLOSE)) //closes a persistent context
                        {
                            bodiservercontext = new Bodiservercontext(this, CLOSE, bodiservercontext);

                            bodiservercontext.processprotocol(bodiservercontext);

                            bodiservercontext.processrequest(bodiservercontext);     

                            bodiservercontext.processsesponse(bodiservercontext);         
                            
                            //
                            
                            this.trystorecontextstobodhi(bodiservercontext, networkcontext);
                        }

                        else if (bodiservercontext.inputstring.startsWith(HANDSHAKE)) //request a session token
                        {
                            bodiservercontext = new Bodiservercontext(this, HANDSHAKE, bodiservercontext);

                            bodiservercontext.processprotocol(bodiservercontext);

                            bodiservercontext.processrequest(bodiservercontext);     

                            bodiservercontext.processsesponse(bodiservercontext);     
                            
                            //
                            
                            this.trystorecontextstobodhi(bodiservercontext, networkcontext); 
                        }                    
                       
                        else if(bodiservercontext.inputstring.startsWith(LIST)) //lists contexts
                        {
                            bodiservercontext = new Bodiservercontext(this, LIST, bodiservercontext);

                            bodiservercontext.processprotocol(bodiservercontext);

                            bodiservercontext.processrequest(bodiservercontext);     

                            bodiservercontext.processsesponse(bodiservercontext);         
                            
                            //
                            
                            this.trystorecontextstobodhi(bodiservercontext, networkcontext);
                        }                        

                        else if(bodiservercontext.inputstring.startsWith(OPEN)) //open a persistent context
                        {
                            bodiservercontext = new Bodiservercontext(this, OPEN, bodiservercontext);

                            bodiservercontext.processprotocol(bodiservercontext);

                            bodiservercontext.processrequest(bodiservercontext);     

                            bodiservercontext.processsesponse(bodiservercontext);      
                            
                            //
                            
                            this.trystorecontextstobodhi(bodiservercontext, networkcontext);
                        }

                        else if(bodiservercontext.inputstring.startsWith(PULL)) //pull a value/object
                        {
                            bodiservercontext = new Bodiservercontext(this, PULL, bodiservercontext);

                            bodiservercontext.processprotocol(bodiservercontext);

                            bodiservercontext.processrequest(bodiservercontext);     

                            bodiservercontext.processsesponse(bodiservercontext);         
                            
                            //
                            
                            this.trystorecontextstobodhi(bodiservercontext, networkcontext);
                        }

                        else if(bodiservercontext.inputstring.startsWith(PUT)) //put a value/object
                        {
                            bodiservercontext = new Bodiservercontext(this, PUT, bodiservercontext);

                            bodiservercontext.processprotocol(bodiservercontext);                         

                            bodiservercontext.processrequest(bodiservercontext);     

                            bodiservercontext.processsesponse(bodiservercontext);                              
                            
                            //
                            
                            this.trystorecontextstobodhi(bodiservercontext, networkcontext);
                        }

                        else if(bodiservercontext.inputstring.startsWith(TOUCH)) //touch a context
                        {
                            bodiservercontext = new Bodiservercontext(this, TOUCH, bodiservercontext);

                            bodiservercontext.processprotocol(bodiservercontext);

                            bodiservercontext.processrequest(bodiservercontext);     

                            bodiservercontext.processsesponse(bodiservercontext);           
                            
                            //
                            
                            this.trystorecontextstobodhi(bodiservercontext, networkcontext);
                        }                        
                        
                        else if(bodiservercontext.inputstring.startsWith(TRADE)) //trade one value/object
                        {
                            bodiservercontext = new Bodiservercontext(this, TRADE, bodiservercontext);

                            bodiservercontext.processprotocol(bodiservercontext);

                            bodiservercontext.processrequest(bodiservercontext);     

                            bodiservercontext.processsesponse(bodiservercontext);         
                            
                            //
                            
                            this.trystorecontextstobodhi(bodiservercontext, networkcontext);
                        }                                                
                        
                        else
                        {
                            bodiservercontext = new Bodiservercontext(this, OTHER, "", bodiservercontext.networkcontext, new Bodiconnection());

                            bodiservercontext.processsesponse(bodiservercontext);                                                                 
                        }                                                
                    }  
                    else
                    {
                        bodiservercontext = new Bodiservercontext(this, OTHER, "", bodiservercontext.networkcontext, new Bodiconnection());

                        bodiservercontext.processsesponse(bodiservercontext);                                                                 
                    }                    
                }                                                   
            }
            catch(SecurityException exception) 
            {                                                
                exception.printStackTrace(System.err);
                
                //
            }            
            catch(NullPointerException exception)
            {
                exception.printStackTrace(System.err);
                
                //
            }            
            catch(InvalidSessionException exception)
            {
                exception.printStackTrace(System.err);
                
                //
            }
            catch(Exception exception)
            {
                exception.printStackTrace(System.err);
                
                //                                
            }              
            finally
            {                
                //
                
                this.tryflushnetworkcontext(networkcontext);     
                
                //
                
                this.tryflushbodicontext(bodiservercontext);
                
                //
                
                this.sleepmillis(500L);
                
                //
                
                System.gc();
                
                //
            }
        }
    }      

    
    /**
     * That network context is non-null, has a value to be read in the InputQueue and is still connected to a client.
     * 
     * @param networkcontext The network context containing the network and the associated the input queue.
     * 
     * @return TRUE is underlying network context is not null, has some ready input and the network connection (socket) is not disconnected but connected and open; FALSE otherwise.
     */
    public Boolean tryvalidatenetworkconnection(Networkcontext networkcontext)
    {
        return networkcontext!=null && networkcontext.inputqueueisready() && networkcontext.issocketconnected();
    }
    
    /**
     * We care that only valid Bodiconnections get introduced into the server and her routing procedure.
     * 
     * @param bodiservercontext The server context containing the network packet and the bodicontext for inspection.
     * 
     * @return TRUE if Bodiconnection is valid by these requirements; FALSE otherwise.
     */
    public Boolean tryvalidatebodiconnection(Bodiservercontext bodiservercontext)
    {
        return 
            
            bodiservercontext!=null && 
                
            bodiservercontext.bodicontext!=null &&                             
            
            bodiservercontext.packet!=null &&                    
            
            (                
                bodiservercontext.packet.startsWith("//close") ||
                
                bodiservercontext.packet.startsWith("//handshake") ||
                
                bodiservercontext.packet.startsWith("//list") ||                                
                
                bodiservercontext.packet.startsWith("//open") ||
                
                bodiservercontext.packet.startsWith("//pull") ||
                
                bodiservercontext.packet.startsWith("//put") ||
                
                bodiservercontext.packet.startsWith("//touch") ||
                
                bodiservercontext.packet.startsWith("//trade")
            );
    }    
    
    /**
     * Clear me
     * 
     * @param bodiservercontext 
     */
    public void tryflushbodicontext(Bodiservercontext bodiservercontext)
    {
        if(bodiservercontext==null) return; //throw new SecurityException("//bodi/connect");
        
        bodiservercontext.bodicontext.value = "";
        
        bodiservercontext.bodicontext.result = "";
        
        bodiservercontext.bodicontext.message = "";
        
        bodiservercontext.bodicontext.value = null;
        
        bodiservercontext.bodicontext.result = null;
        
        bodiservercontext.bodicontext.message = null;        
    }
    
    /**
     * Clear cyclical buffers 
     * 
     * @param networkcontext 
     */
    public void tryflushnetworkcontext(Networkcontext networkcontext)
    {                
        try
        { 
            networkcontext.inqueue.delete(0, networkcontext.inqueue.length());                         
        }
        catch(Exception e){} 
    }
    
    /**
     * Store session objects with a Bodi carekeeper
     * 
     * @param connectioncontext
     * @param connection
     * @return
     * @throws Exception 
     */    
    public Boolean trystorecontextstobodhi(Bodiservercontext connectioncontext, Networkcontext connection) throws Exception
    {
        if(connectioncontext==null) return false;
        
        if(connection==null) return false;
        
        if(connection.socket==null) return false;
        
        Bndi.context("//bodi/server/remote/bodiconnections").put(connectioncontext.bodicontext.sessionid.toString(), connectioncontext.bodicontext);
        
        Bndi.context("//bodi/server/remote/netconnections").put(connection.socket.getInetAddress().toString(), connection);
        
        return true;
    }
    
    /**
     * Returns the Collection of Bodiconnections from the Bodi server context.
     * 
     * @return The Collection of Bodiconnections from the Bodi server context.
     */
    public Collection<Bodiconnection> getbodiconnections()
    {
        Collection<Object> objects = Bndi.context("//bodi/server/remote/bodiconnections").values();
        
        Collection<Bodiconnection> _connections = (Collection<Bodiconnection>)(Collection<?>)objects;
        
        return _connections;
    }
    
    /**
     * Returns the next network connection from InputQueue from BasicServer class to Bodiremoteserver
     * 
     * @return That particular Networkcontext or null if none found queued
     */
    public Networkcontext pollqueuednetworkconnections()
    {        
        Networkcontext connection = this.connectionqueue.peek();        
        
        if(connection!=null) this.connectionqueue.remove(connection);
        
        return connection;
    }    
    
    /**
     * Talk to system; have it rest millis number of milliseconds before returning to task.
     * 
     * @param millis Rest this many milliseconds and no longer.
     */
    public void sleepmillis(Long millis) 
    {
        try
        {
            Thread.currentThread().sleep(millis);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }    
    
    /**
     * 
     * @param networkcontext
     * @return
     * @throws Exception 
     */
    public Bodiconnection pollstoredbodisessions(Networkcontext networkcontext) throws Exception
    {
        if(networkcontext==null) throw new SecurityException("//bodi/connect");
        
        if(networkcontext.inqueue==null) throw new SecurityException("//bodi/connect");        
                    
        String[] tokens = networkcontext.inqueue.toString().split(" ");                
        
        for(String token : tokens)
        {          
            if(token.trim().startsWith("//sessionid"))
            {
                for(Bodiconnection existingconnection : this.getbodiconnections())
                {
                    token = token.trim().replace("//sessionid=", "");
                    
                    if(token==null || existingconnection.sessionid==null) continue;                        
                    
                    int receivedsessionid = Integer.parseInt(token);
                    
                    int existingsessionid = existingconnection.sessionid;
                    
                    if(receivedsessionid == existingsessionid)
                    {
                        if(existingconnection.ttl>0)
                            
                        return existingconnection;
                        
                        else return null;                            
                    }
                }
            }
        }
        
        if(networkcontext.inqueue.toString().startsWith("//handshake")) 
        {
            return new Bodiconnection();
        }
        
        else return null;
    }
    
    /**
     * 
     * @param connection
     * @return 
     */
    public Boolean isvalidsessionid(Bodiconnection connection)
    {
        if(connection==null) return false;
        
        if(connection.sessionid==null) return false;
        
        for(Bodiconnection existingconnection : this.getbodiconnections())
        {
            if(Objects.equals(existingconnection.sessionid, connection.sessionid))
            {
                return true;
            }
        }
        
        return false;
    }  
    
    /**
     * 
     * @param connection
     * @return 
     */
    public Boolean isvalidsession(Bodiconnection connection)
    {
        if(connection==null) return false;
        
        if(connection.sessionid==null) return false;
        
        if(connection.ttl<=0) return false;
        
        for(Bodiconnection existingconnection : this.getbodiconnections())
        {
            if(Objects.equals(existingconnection.sessionid, connection.sessionid))
            {                
                return connection.ttl > 0;                
            }
        }
        
        return false;
    }    

    /**
     * 
     * @param bytes 
     */
    @Override
    public void write(byte[] bytes)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return 
     */
    @Override
    public byte[] read()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}