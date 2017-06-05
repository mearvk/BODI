package bloq.system.bodi.server.remote;

import java.io.Serializable;

/**
 *
 * @author Max Rupplin
 */
public class Serializedcarrier implements Serializable
{
    public Object object = null;
    
    public Class _class = null;
    
    /**
     * 
     */
    public Serializedcarrier()
    {
        
    }
    
    /**
     * 
     * @param _class
     * @param object 
     */
    public Serializedcarrier(Class _class, Object object)
    {
        this._class = _class;
        
        this.object = object;
    }
}