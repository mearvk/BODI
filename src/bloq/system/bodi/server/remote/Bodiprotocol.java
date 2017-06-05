package bloq.system.bodi.server.remote;

/**
 *
 * @author Max Rupplin
 */
public class Bodiprotocol extends Protocol
{        
    public static final String CLOSE = "//close";
    
    public static final String HANDSHAKE = "//handshake";
    
    public static final String LIST = "//list";
            
    public static final String OPEN = "//open";
    
    public static final String OTHER = "//other";
    
    public static final String PULL = "//pull";
    
    public static final String PUT = "//put";
    
    public static final String TOUCH = "//touch";
    
    public static final String TRADE = "//trade";
            
    public static final String value = "";
    
    /**
     * 
     */
    public Bodiprotocol()
    {
        super();
    }
    
    /**
     * 
     * @param comparator
     * @return 
     */
    public static Boolean isbodiprotocol(String comparator)
    {
        if(comparator.startsWith(CLOSE)) return true;
        
        if(comparator.startsWith(HANDSHAKE)) return true;
        
        if(comparator.startsWith(LIST)) return true;
        
        if(comparator.startsWith(OPEN)) return true;
        
        if(comparator.startsWith(OTHER)) return true;                
        
        if(comparator.startsWith(PULL)) return true;
        
        if(comparator.startsWith(PUT)) return true;                
        
        if(comparator.startsWith(TOUCH)) return true;
        
        if(comparator.startsWith(TRADE)) return true;                
        
        return false;
    }
}