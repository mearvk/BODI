package bloq.system.bodi.server.remote;

import bloq.system.bodi.Bndi;

import java.io.Serializable;

import java.util.ArrayList;

/**
 *
 * @author Max Rupplin
 */
public class Bodiconnection
{
    public Integer hash = 0x008808ef;
    
    /*-------------------------------------------------------------------------*/
    
    public ArrayList<String> lines = new ArrayList();
    
    public Bndi bodi;
    
    public Bodiremoteserver server;
    
    public Boolean verified = true;       
    
    public Boolean islive = true;
            
    public Networkcontext connection;            
    
    public Integer sessionid;
    
    public Integer userid;
    
    public Long ttl = 60*1000*100L;
    
    public Long day;       
    
    public Serializable object;
    
    public String host;                
    
    public String result;
    
    public String operation = ""; 
    
    public String cause;
    
    public String error = "";        
    
    public String context;
    
    public String tradecontext;
    
    public String key;    
    
    public String message;
    
    public String value;   
    
    public ArrayList<String> values;
    
   
    /**
     * 
     */
    public Bodiconnection()
    {
        this.sessionid = this.hashCode();
        
        this.day = System.currentTimeMillis();
    }
    
    /**
     * 
     * @param server
     * @param connection 
     */
    public Bodiconnection(Bodiremoteserver server, Networkcontext connection)
    {                
        if(server==null || connection==null) throw new SecurityException("//bodi/connect/exceptions");
        
        this.connection = connection;
        
        this.day = System.currentTimeMillis();
    }
         
    /**
     * 
     * @param connectioncontext
     * @return 
     */
    public Boolean processrequest(Bodiservercontext connectioncontext)
    {                
        StringBuffer buffer = connectioncontext.inputbuffer;      
        
        String protocol = this.stripforprotocoltoken(connectioncontext);        
        
        this.tryresetbodiconnection(connectioncontext);
        
        try
        {              
            if(this.gettimetolive()<=0) protocol = "//other"; //send an other reply for errant bodi request
            
            switch(protocol)
            {
                case "//close":

                    connectioncontext.bodicontext.processcloserequest(connectioncontext);

                    connectioncontext.bodicontext.processcloseresponse(connectioncontext); 

                    break;

                case "//handshake": 

                    connectioncontext.bodicontext.processhandshakerequest(connectioncontext);

                    connectioncontext.bodicontext.processhandshakeresponse(connectioncontext);                                 

                    break;                    
                    
                case "//list":
                    
                    connectioncontext.bodicontext.processlistrequest(connectioncontext);

                    connectioncontext.bodicontext.processlistresponse(connectioncontext);                                 

                    break;                                    

                case "//open":

                    connectioncontext.bodicontext.processopenrequest(connectioncontext);

                    connectioncontext.bodicontext.processopenresponse(connectioncontext); 

                    break;

                case "//pull":

                    connectioncontext.bodicontext.processpullrequest(connectioncontext);

                    connectioncontext.bodicontext.processpullresponse(connectioncontext); 

                    break;

                case "//put": 

                    connectioncontext.bodicontext.processputrequest(connectioncontext);

                    connectioncontext.bodicontext.processputresponse(connectioncontext); 

                    break;   

                case "//touch":                    

                    connectioncontext.bodicontext.processtouchrequest(connectioncontext);

                    connectioncontext.bodicontext.processtouchresponse(connectioncontext);
                    
                    break;
                    
                case "//trade":

                    connectioncontext.bodicontext.processtraderequest(connectioncontext);

                    connectioncontext.bodicontext.processtraderesponse(connectioncontext); 

                    break;

                default: 
                    
                    connectioncontext.bodicontext.processotherrequest(connectioncontext);

                    connectioncontext.bodicontext.processotherresponse(connectioncontext);                    

                    break;
            }        
        }
        catch(Exception e)
        {
            e.printStackTrace(System.err);
        }
        finally
        {
            //
        }        
        
        return this.object == null;
    }    
    
        
    /**
     *      * 
     * @param bodiservercontext
     * @return 
     */
    public Boolean tryresetbodiconnection(Bodiservercontext bodiservercontext)
    {        
        bodiservercontext.bodicontext.cause = "";
        
        bodiservercontext.bodicontext.message = "";
        
        bodiservercontext.bodicontext.result = "";
        
        bodiservercontext.bodicontext.cause = null;
        
        bodiservercontext.bodicontext.message = null;
        
        bodiservercontext.bodicontext.result = null;        
        
        return false | true;
    }

    /**
     * 
     * @param connectioncontext
     * @return 
     */
    public Bodiconnection processlistrequest(Bodiservercontext connectioncontext)
    {
        Bodiconnection bodiconnection = connectioncontext.bodicontext;                           
        
        bodiconnection.operation = "//list";
        
        bodiconnection.getsessionid();
        
        bodiconnection.gettimetolive();                
        
        //
        bodiconnection.context = this.stripforcontext(connectioncontext);                
        
        return bodiconnection;
    }
    
    /**
     * 
     * @param connectioncontext
     * @return
     * @throws SecurityException 
     */
    public Bodiconnection processlistresponse(Bodiservercontext connectioncontext) throws SecurityException
    {
        if(connectioncontext==null) throw new SecurityException("//bodi//connect");
        
        if(connectioncontext.bodicontext==null) throw new SecurityException("//bodi/connect");
        
        if(connectioncontext.bodicontext.context==null) throw new SecurityException("//bodi/connect");
        
        connectioncontext.bodicontext.values = Bndi.listcontexts(connectioncontext.bodicontext.context);
        
        if(connectioncontext.bodicontext.values.size()>0)
        {
            connectioncontext.bodicontext.message = "subcontexts found";
        }
        else
        {
            connectioncontext.bodicontext.message = "no subcontexts listed";
        }
        
        return connectioncontext.bodicontext;
    }
    
    /**
     * 
     * @param connectioncontext
     * @return 
     */
    public Boolean processcloseresponse(Bodiservercontext connectioncontext)
    {
        this.islive = false;       
        
        if(Bndi.hascontextat(connectioncontext.getcontext(connectioncontext)))
        {
            try
            {
                Bndi.removecontext(connectioncontext.getcontext(connectioncontext));
                
                connectioncontext.bodicontext.result = "success";
                
                connectioncontext.bodicontext.message = "context closed";
            }
            catch(Exception e)
            {
                e.printStackTrace(System.err);
            }
        }
        else
        {
            connectioncontext.bodicontext.result = "failure";
        
            connectioncontext.bodicontext.cause = "no such open context";            
        }        
        
        return false;
    }
    
    /**
     * 
     * @param connectioncontext
     * @return 
     */
    public Boolean processhandshakeresponse(Bodiservercontext connectioncontext)
    {
        connectioncontext.bodicontext.result = "success";
        
        connectioncontext.bodicontext.message = "welcome";
        
        return false;
    }    
    
    /**
     * 
     * @param connectioncontext
     * @return 
     */
    public Boolean processopenresponse(Bodiservercontext connectioncontext)
    {                     
        String _context = connectioncontext.getcontext(connectioncontext);
        
        if( Bndi.hascontextat(_context ))
        {
            connectioncontext.bodicontext.result = "failure";
            
            connectioncontext.bodicontext.cause = "existing context locked";            
            
            return false;
        }
        else
        {
            Bndi.setcontext(_context);
            
            connectioncontext.bodicontext.result = "success";
        
            connectioncontext.bodicontext.message = "bodi context established";                        
            
            return true;
        }                
    } 
    
    /**
     * 
     * @param connectioncontext
     * @return 
     */
    public Boolean processotherresponse(Bodiservercontext connectioncontext)
    {
        connectioncontext.bodicontext.operation = "//other";
        
        connectioncontext.bodicontext.result = "rejection";
        
        connectioncontext.bodicontext.message = "unusual; please recheck";
        
        try
        {
            //no valid TTL 
            if(this.gettimetolive()<=0) 
            {
                connectioncontext.bodicontext.cause = "TTL expired";    
            }                        
            //no valid session issue
            else if(this.server.isvalidsessionid(connectioncontext.bodicontext)) 
            {
                connectioncontext.bodicontext.cause = "Session ID not valid";                
            }
            //unclear cause; tokens may be cause
            else 
            {
                connectioncontext.bodicontext.cause = "Unclear cause; check all tokens";     
            }
        }
        catch(Exception e)
        {
            //
        }
        finally
        {
            return false;
        }
    }
    
    /**
     * 
     * @param connectioncontext
     * @return 
     */
    public Boolean processpullresponse(Bodiservercontext connectioncontext)
    {                
        Bodiconnection bodiconnection = connectioncontext.bodicontext;
        
        Object object = Bndi.context(bodiconnection.context).pull(key);          
                
        if(object==null) 
        {
            bodiconnection.message = "unable to pull that key/context pair; sorry";
        
            bodiconnection.value = null;
        
            bodiconnection.result = "failure";
            
            return false;
        }
        else
        {
            Serializedcarrier carrier = new Serializedcarrier(object.getClass(), object);
            
            bodiconnection.message = "key/context pair found.";
            
            bodiconnection.object = carrier;
                          
            bodiconnection.value = carrier.object.toString();
        
            bodiconnection.result = "success";
            
            return true;
        }                        
    }    
    
    /**
     * 
     * @param connectioncontext
     * @return 
     */
    public Boolean processputresponse(Bodiservercontext connectioncontext)
    {
        this.islive = true;
        
        String context = this.stripforcontext(connectioncontext);
        
        String key = connectioncontext.bodicontext.key;
        
        String value = connectioncontext.bodicontext.value;        
        
        return true;
    }    

    /**
     * 
     * @param connectioncontext
     * @return 
     */
    public Boolean processtouchresponse(Bodiservercontext connectioncontext)
    {
        String _context = this.stripforcontext(connectioncontext);
        
        if(Bndi.hascontextat(_context))
        {
            connectioncontext.bodicontext.result = "success";
            
            connectioncontext.bodicontext.message = "touched";
        }
        else
        {
            connectioncontext.bodicontext.result = "failure";
            
            connectioncontext.bodicontext.cause = "no such context";
        }
        
        return true | false;
    }     
    
    /**
     * 
     * @param connectioncontext
     * @return 
     */
    public Boolean processtraderesponse(Bodiservercontext connectioncontext)
    {
        String context1 = connectioncontext.bodicontext.context;
        
        Object abject1 = Bndi.context(context).pull("");
                
        Object abject2 = Bndi.context(context).pull("");
        
        return false;
    }        
    
    /**
     * 
     * @param connectioncontext
     * @return
     * @throws Exception 
     */
    public Bodiconnection processtouchrequest(Bodiservercontext connectioncontext) throws Exception
    {
        Bodiconnection bodiconnection = connectioncontext.bodicontext;        
        
        bodiconnection.operation = "//touch";
        
        bodiconnection.getsessionid();
        
        bodiconnection.gettimetolive();                        
        
        return bodiconnection;
    }
    
/**
     * New handshakes should return new Bodiconnection instances with unique sessionid values
     * 
     * Existing Bodiconnections should return updated TTLs possibly more.
     * 
     * @param connectioncontext
     * @return 
     */
    public Bodiconnection processhandshakerequest(Bodiservercontext connectioncontext) throws Exception
    {
        Bodiconnection bodiconnection = connectioncontext.bodicontext;
        
        bodiconnection.operation = "//handshake";
        
        bodiconnection.getsessionid();
        
        bodiconnection.gettimetolive();                        
        
        return bodiconnection;
    }
    
    /**
     * 
     * @param connectioncontext
     * @return
     * @throws Exception 
     */
    public Bodiconnection processcloserequest(Bodiservercontext connectioncontext) throws Exception
    {               
        Bodiconnection bodiconnection = connectioncontext.bodicontext;                           
        
        bodiconnection.operation = "//close";
        
        bodiconnection.getsessionid();
        
        bodiconnection.gettimetolive();                       
        
        return bodiconnection;
    }
    
    /**
     * 
     * @param connectioncontext
     * @return
     * @throws Exception 
     */
    public Bodiconnection processputrequest(Bodiservercontext connectioncontext) throws Exception
    {
        Bodiconnection bodiconnection = connectioncontext.bodicontext;                                                       
        
        bodiconnection.operation = "//put";
        
        bodiconnection.getsessionid();
        
        bodiconnection.gettimetolive();                
        
        //---------------------------------------------------------------------//
        
        bodiconnection.key = this.stripforkey(connectioncontext);
        
        bodiconnection.context = this.stripforcontext(connectioncontext);
        
        bodiconnection.value = this.stripforvalue(connectioncontext);
                        
        try
        {
            if( Bndi.hascontextat(context) )
            {
                Bndi.context(bodiconnection.context).put(key, value);
                
                bodiconnection.result = "success";
                
                bodiconnection.message = "key/value pair established";
            }
            else
            {
                Bndi.setcontext(context);
                
                Bndi.context(bodiconnection.context).put(key, value);
                
                bodiconnection.result = "success";
                
                bodiconnection.message = "context and key/value pair established";
            }
        }
        catch(Exception e)
        {
            e.printStackTrace(System.err);
            
            bodiconnection.result = "failure";
            
            bodiconnection.cause = "error inserting context and/or key/value pair";
        }
        
        return bodiconnection;
    }
    
    /**
     * 
     * @param connectioncontext
     * @return
     * @throws Exception 
     */
    public Bodiconnection processpullrequest(Bodiservercontext connectioncontext) throws Exception
    {
        Bodiconnection bodiconnection = connectioncontext.bodicontext;                                      
        
        bodiconnection.operation = "//pull";
        
        bodiconnection.getsessionid();
        
        bodiconnection.gettimetolive();                
        
        //---------------------------------------------------------------------//
        
        bodiconnection.key = this.stripforkey(connectioncontext);
        
        bodiconnection.context = this.stripforcontext(connectioncontext);             
        
        return bodiconnection;
    }
    
    /**
     * Will connect a persistent context to a Bodiremoteserver without key/value pair
     * 
     * @param connectioncontext
     * @return
     * @throws Exception 
     */
    public Bodiconnection processopenrequest(Bodiservercontext connectioncontext) throws Exception
    {        
        Bodiconnection bodiconnection = connectioncontext.bodicontext;                                                  
        
        bodiconnection.operation = "//open";
        
        bodiconnection.getsessionid();
        
        bodiconnection.gettimetolive();                                        
        
        return bodiconnection;
    }
    
    /**
     * 
     * @param connectioncontext
     * @return
     * @throws Exception 
     */
    public Bodiconnection processotherrequest(Bodiservercontext connectioncontext) throws Exception
    {
        return connectioncontext.bodicontext;
    }
    
    /**
     * 
     * @param connectioncontext
     * @return
     * @throws Exception 
     */
    public Bodiconnection processtraderequest(Bodiservercontext connectioncontext) throws Exception
    {
        Bodiconnection bodiconnection = connectioncontext.bodicontext;                                                       
        
        bodiconnection.operation = "//trade";
        
        bodiconnection.getsessionid();
        
        bodiconnection.gettimetolive();                
        
        bodiconnection.getrequestedobject("context", "key");
        
        //bodiconnection.getresult();
        
        return bodiconnection;
    }
    
    /**
     * 
     * @param connectioncontext
     * @return
     * @throws Exception 
     */
    public Bodiconnection other(Bodiservercontext connectioncontext) throws Exception
    {
        Bodiconnection bodiconnection = connectioncontext.bodicontext;                      
        
        bodiconnection.operation = "//other";
        
        bodiconnection.getsessionid();
        
        bodiconnection.gettimetolive();                
        
        bodiconnection.getrequestedobject("context", "key");
        
        //bodiconnection.getresult();
        
        return bodiconnection;
    }   

    /**
     * 
     * @param connectioncontext
     * @return 
     */
    public String stripforkey(Bodiservercontext connectioncontext)
    {
        return Protocolstripper.stripforkey(connectioncontext);
    }
    
    /**
     * 
     * @param connectioncontext
     * @return 
     */
    public String stripforvalue(Bodiservercontext connectioncontext)
    {
        return Protocolstripper.stripforvalue(connectioncontext);
    }
    
    /**
     * 
     * @param connectioncontext
     * @return 
     */
    public String stripforcontext(Bodiservercontext connectioncontext)
    {
        return Protocolstripper.stripforcontext(connectioncontext);
    }     
    
    /**
     * 
     * @param connectioncontext
     * @return 
     */
    public String stripforprotocoltoken(Bodiservercontext connectioncontext)
    {
        return Protocolstripper.stripforprotocoltoken(connectioncontext);
    }
    
    /**
     * 
     * @param connectioncontext
     * @return 
     */
    private Boolean checkconnection(Bodiservercontext connectioncontext)            
    {
        if(connectioncontext.bodicontext==null) return false;
        
        if(connectioncontext.bodicontext.ttl<=0) return false;
        
        if(this.server.isvalidsession(connectioncontext.bodicontext)==null) return false;            
        
        return true;
    }    
          
    /**
     * 
     * @param context
     * @param key
     * @return 
     */
    protected Serializable getrequestedobject(String context, String key)
    {
        Serializedcarrier bodicarrier = new Serializedcarrier();
                
        try
        {   
            Object object = Bndi.context(context).pull(key);
        
            Class _class = Bndi.context(context).getclass(key);
            
            bodicarrier.object = object;
        
            bodicarrier._class = _class;
        }
        catch(Exception e)
        {
            //
        }                        
        
        return this.object = bodicarrier;
    }
    
    /**
     * 
     * @return 
     */
    protected String getresult()
    {
        return this.result = "";
    }    
       
    /**
     * 
     * @return 
     */
    protected Integer getsessionid() 
    {
        if(this.sessionid==null || this.sessionid==0)
        {
            return this.hashCode();
        }
        
        return this.sessionid;
    }
    
    /**
     * 
     * @return 
     */
    protected Long gettimetolive()
    {
        Long now = System.currentTimeMillis();        
        
        Long day = this.day;
        
        Long ttl = this.ttl;
                
        //
        
        if( (this.ttl = ttl-(now-day))<0 )
        {
            return -1L;
        }      
        
        return this.ttl;
    }        
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString()
    {
        switch(this.operation)
        {
            case "//list":
                
                if(this.values!=null)
                {
                    String output = "//list //sessionid="+sessionid+" //result="+result+" //ttl="+ttl;
                    
                    if(message!=null)
                    {
                        output += " //message="+message;
                    }                                        

                    if(this.values!=null)
                    {
                        String temp = " //value={as list}\n\n";

                        for(String matchingcontext: this.values)
                        {
                            temp += matchingcontext+"\n";
                        }

                        output = output + temp;
                    }

                    return output;                    
                }
                
                else if(this.cause!=null)
                {
                    return "//list //sessionid="+sessionid+" //result="+result+" //cause="+cause+" //ttl="+ttl;
                }
                
                else if(this.message!=null)
                {
                    return "//list //sessionid="+sessionid+" //result="+result+" //message="+message+" //ttl="+ttl;
                }    
                
                else return "//list //message=new";
                
            case "//close":
                
                if(cause!=null)
                {
                    return "//close //sessionid="+sessionid+" //result="+result+" //cause="+cause+" //ttl="+ttl;
                }
                 
                if(message!=null)
                {
                    return "//close //sessionid="+sessionid+" //result="+result+" //message="+message+" //ttl="+ttl;
                }
                
                return "//close //sessionid="+sessionid+" //result="+result+" //ttl="+ttl;
                
            case "//handshake": 
                
                if(cause!=null)
                {
                    return "//handshake //sessionid="+sessionid+" //result="+result+" //cause="+cause+" //ttl="+ttl;
                }
                
                if(message!=null)
                {
                    return "//handshake //sessionid="+sessionid+" //result="+result+" //message="+message+" //ttl="+ttl;
                }
                
                return "//handshake //sessionid="+sessionid+" //result="+result+" //ttl="+ttl;
            
            case "//open":
                
                if(cause!=null)
                {
                    return "//open //sessionid="+sessionid+" //result="+result+" //cause="+cause+" //ttl="+ttl;
                }
               
                if(message!=null)
                {
                    return "//open //sessionid="+sessionid+" //result="+result+" //message="+message+" //ttl="+ttl;
                }                
                
                return "//open //sessionid="+sessionid+" //result="+result+" //ttl="+ttl;
            
            case "//pull": 
                
                if(message!=null)
                {
                    return "//pull //sessionid="+sessionid+" //result="+result+" //message="+message+" //object="+object+" //ttl="+ttl+" //value="+value;
                }
                
                if(cause!=null)
                {
                    return "//pull //sessionid="+sessionid+" //result="+result+" //cause="+cause+" //ttl="+ttl+" //value="+value;
                }
                
                return "//pull //sessionid="+sessionid+" //result="+result+" //ttl="+ttl;
            
            case "//put": 
                
                if(message!=null)
                {
                    return "//put //sessionid="+sessionid+" //result="+result+" //message="+message+" //ttl="+ttl;
                }
                
                if(cause!=null)
                {
                    return "//put //sessionid="+sessionid+" //result="+result+" //cause="+cause+" //ttl="+ttl;
                }
                
                return "//put //sessionid="+sessionid+" //result="+result+" //ttl="+ttl;
            
            case "//touch": 
                
                if(message!=null)
                {
                    return "//touch //sessionid="+sessionid+" //result="+result+" //message="+message+" //ttl="+ttl;
                }
                
                if(cause!=null)
                {
                    return "//touch //sessionid="+sessionid+" //result="+result+" //cause="+cause+" //ttl="+ttl;
                }
                
                return "//touch //sessionid="+sessionid+" //result="+result+" //ttl="+ttl;                
                
            case "//trade": 
                
                if(message!=null)
                {
                    return "//trade //sessionid="+sessionid+" //result="+result+" //message="+message+" //ttl="+ttl;
                }
                
                if(cause!=null)
                {
                    return "//trade //sessionid="+sessionid+" //result="+result+" //cause="+cause+" //ttl="+ttl;
                }
                
                return "//trade //sessionid="+sessionid+" //result="+result+" //ttl="+ttl;
            
            default: return "//other //result="+result+" //message="+message+" //cause="+cause;
        }
    }        
}
