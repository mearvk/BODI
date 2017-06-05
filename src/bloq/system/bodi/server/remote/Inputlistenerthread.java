package bloq.system.bodi.server.remote;

/**
 *
 * @author Max Rupplin
 */
class Inputlistenerthread extends Thread
{
    public Boolean hasreadready = false;    
    
    public Boolean running = true;
    
    public Listenerthread parent;
    
    public String line = null;
    
    public Object readreadylock = new Object();
    
    /**
     * 
     * @param parent 
     */
    public Inputlistenerthread(Listenerthread parent)
    {
        if(parent==null) throw new SecurityException("//bodi/connect");
        
        this.parent = parent;
        
        this.setName("Inputlistenerthread");  
    }
    
    /**
     * 
     * @return 
     */
    public Boolean checkinputqueue()
    {                            
        try
        {                
           if(this.parent.connection.reader.ready() && this.hasreadready)
           {                                           
                //please use single line reads only for now
                String line = this.parent.connection.reader.readLine();
                   
                Networkcontext connection = this.parent.connection;                
                    
                connection.appendline(line);
                    
                connection.server.connectionqueue.add(connection);
                  
                connection.server.doread = true;
            }            
        }
        catch(Error e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            //
        }           
            
        return true;            
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
                try
                {
                    if(this.parent.connection.reader.ready())
                    {                        
                        this.hasreadready = true;                                                
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            
            this.sleepmillis(500l);
        }
        catch(Exception e)
        {
            //
        }
    }
    
    /**
     * 
     * @param millis
     * @throws Exception 
     */
    protected void sleepmillis(Long millis) throws Exception
    {
        Thread.currentThread().sleep(millis);
    }    
}