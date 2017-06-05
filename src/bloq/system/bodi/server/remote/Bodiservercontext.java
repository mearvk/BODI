package bloq.system.bodi.server.remote;

/**
 * Represents a single connection to a Bodi server
 * 
 * @author Max Rupplin
 */
public class Bodiservercontext
{   
    public Bodiconnection bodicontext;
    
    public Bodiremoteserver bodiserver;
    
    public Networkcontext networkcontext;
    
    public String protocol;
    
    public String packet;
    
    public StringBuffer inputbuffer; 
    
    public String inputstring;
    
    public String key;
    
    public String value;
    
    public String context;    
    
    /**
     * 
     */
    public Bodiservercontext()
    {
        
    }
    
    /**
     * 
     * @param bodiserver
     * @param protocol
     * @param bodiservercontext
     * @throws Exception 
     */
    public Bodiservercontext(Bodiremoteserver bodiserver, String protocol, Bodiservercontext bodiservercontext) throws Exception
    {
        if(bodiserver==null || protocol==null || bodiservercontext==null) throw new SecurityException("//bodi/connect");                 
        
        this.bodiserver = bodiserver;
        
        this.protocol = new StringBuffer(protocol).toString();
        
        this.inputbuffer = new StringBuffer(bodiservercontext.networkcontext.inqueue);               
        
        this.inputstring = new StringBuffer(bodiservercontext.networkcontext.inqueue).toString();  
        
        this.networkcontext = bodiservercontext.networkcontext;
        
        this.bodicontext = bodiservercontext.bodicontext;
        
        this.packet = new StringBuffer(bodiservercontext.networkcontext.inqueue).toString();        
    }    
    
    /**
     * Quick fill for primary loop on Bodiserver; not a total Constructor for BRS at this time. /mr /ok /ss
     * 
     * @param bodiserver
     * @param networkcontext
     * @param bodicontext
     * @throws Exception 
     */
    public Bodiservercontext(Bodiremoteserver bodiserver, Networkcontext networkcontext, Bodiconnection bodicontext) throws Exception
    {                
        //bodicontext==null ok /mr /ok
        
        if(bodiserver==null || networkcontext==null /* || bodicontext==null */) throw new SecurityException("//bodi/connect");  
        
        this.bodiserver = bodiserver;                
        
        this.networkcontext = networkcontext;
        
        this.bodicontext = bodicontext;
        
        this.packet = networkcontext.inqueue.toString();
        
        this.inputstring = networkcontext.inqueue.toString();
        
        //             
    }
    
    /**
     * 
     * @param bodiserver
     * @param protocol
     * @param input
     * @param network
     * @param bodiconnection
     * @throws Exception 
     */
    public Bodiservercontext(Bodiremoteserver bodiserver, String protocol, String input, Networkcontext network, Bodiconnection bodiconnection) throws Exception
    {
        if( bodiserver==null || protocol==null || input==null || network==null || bodiconnection==null) throw new SecurityException("//bodi/connect");
            
        this.protocol = protocol;
        
        this.inputstring = input;
        
        this.inputbuffer = network.inqueue;
        
        this.bodiserver = bodiserver;
        
        this.networkcontext = network;
        
        this.bodicontext = bodiconnection;
        
        this.bodicontext.operation = protocol;
        
        this.packet = new StringBuffer(network.inqueue).toString();
        
        //
        
        if( protocol.startsWith("//other") )
        {                       
            this.bodicontext.cause = "unrecognized protocol"; 
                    
            this.bodicontext.message = "unable to complete request";          
        }
    }
    
    /**
     * 
     * @param parameterization
     * @return 
     */
    public String getcontext(Bodiservercontext parameterization)
    {
        return Protocolstripper.stripforcontext(parameterization);
    }
    
    /**
     * 
     * @param connectioncontext
     * @throws Exception 
     */
    public void processprotocol(Bodiservercontext connectioncontext) throws Exception
    {
        connectioncontext.bodiserver.protocolhandler.parseprotocol(connectioncontext);
    }

    /**
     * 
     * @param connectioncontext
     * @throws Exception 
     */
    public void processrequest(Bodiservercontext connectioncontext) throws Exception
    {
        connectioncontext.bodicontext.processrequest(connectioncontext);
    }
    
    /**
     * 
     * @param connectioncontext
     * @throws Exception 
     */
    public void processsesponse(Bodiservercontext connectioncontext) throws Exception
    {
        connectioncontext.networkcontext.processresponse(connectioncontext);
    }
}
