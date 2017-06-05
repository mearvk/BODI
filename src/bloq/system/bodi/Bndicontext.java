/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bloq.system.bodi;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author Max Rupplin
 */
public class Bndicontext 
{
    protected final Integer hash = 0x888fe8;
    
    private final Map<String, Object> namemap = new HashMap();
    private final Map<Integer, Object> hashmap = new HashMap();
    private final Map<Object, Object> objectmap = new HashMap();    
    
    public String contextname;
    public File file;
    
    public Bndicontext(String contextname)
    {       
        this.contextname = contextname;
    }

    public Bndicontext(File file)
    {
        this.file = file;
    }
    
    public void put(Integer hashcode, Object object)
    {
        try
        {
            this.hashmap.put(hashcode, object);
        }   
        catch(Exception e)
        {
            System.err.println(e);
        }        
    }
    
    public void put(String name, Object object)
    {        
        try
        {
            this.namemap.put(name, object);            
        }                
        catch(Exception e)
        {
            System.err.println(e);
        }        
    }           
    
    public void put(Object key, Object value)
    {           
        try
        {
            this.objectmap.put(key, value);                     
        }                
        catch(Exception e)
        {
            System.err.println(e);
        }
    }     
    
    /**
     * Can equality produce errant result early such that we care? /ok /mr /ss
     * 
     * @param object
     * @return 
     */
    public Object softpull(Object object)
    {
        Map<Object, Object> map = this.objectmap;
        
        for(Entry entry : map.entrySet())
        {                     
            Object key = entry.getKey();
            Object value = entry.getValue();
            
            if(object instanceof Node && key instanceof Node)
            {
                Element n1 = (Element)object;
                Element n2 = (Element)key;                
                
                if(n1.isEqualNode(n2)) 
                {
                    return value;
                }                
            }
        }
        
        return null;
    }
    
    public Object pull(Integer hashcode)
    {
        try
        {
            return hashmap.get(hashcode);            
        }
        catch(Exception e)
        {
            System.err.println(e);
        }
        
        return null;
    }

    public Object pull(Object object)
    {
        try
        {            
            return objectmap.get(object);
        }
        catch(Exception e)
        {
            System.err.println(e);
        }
        
        return null;
    }    
    
    public Object pull(String name)
    {
        try
        {
            return namemap.get(name);            
        }
        catch(Exception e)
        {
            System.err.println(e);
        }
        
        return null;
    } 

    /**
     * 
     * @return 
     */
    public Map<Object,Object> pullall()
    {
        HashMap<Object, Object> allvals = new HashMap<Object, Object>();
    
        allvals.putAll(namemap);
        
        allvals.putAll(objectmap);
        
        allvals.putAll(hashmap);
        
        return allvals;
    }
    
    /**
     * 
     * @param name
     * @return 
     */
    public Class getclass(String name)
    {
        return this.pull(name).getClass();
    }

    /**
     * 
     * @return 
     */
    public Set<Object> keys()
    {
        Map<Object, Object> allvals = this.pullall();
        
        return allvals.keySet();
    }
    
    /**
     * 
     * @return 
     */
    public Collection<Object> values()
    {
        Map<Object, Object> allvals = this.pullall();
        
        return allvals.values();        
    }   
    
    /**
     * 
     * @param basecontext
     * @param depth
     * @return 
     */
    public Collection<Object> lists(String basecontext, Integer depth)
    {
        return null; 
    }    
    
    /**
     * 
     * @return 
     */
    public Collection<Object> pullallstringkeyedvalues()
    {
        return namemap.values();
    }    
    
    /**
     * 
     * @return 
     */
    public Set<String> pullallstringkeys()
    {
        return namemap.keySet();
    }
    
    /**
     * 
     * @return 
     */
    public Collection<Object> pullallobjectkeyedvalues()
    {
        return objectmap.values();
    }    
    
    /**
     * 
     * @return 
     */
    public Set<Object> pullallobjectkeys()
    {
        return objectmap.keySet();
    }

    /**
     * 
     * @return 
     */
    public Collection<Object> pullallhashcodekeyedvalues()
    {
        return hashmap.values();
    }    
    
    /**
     * 
     * @return 
     */
    public Set<Integer> pullAllKeys()
    {
        return hashmap.keySet();
    }     
}
