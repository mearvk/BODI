package bloq.system.bodi.server.remote;

/**
 *
 * @author Max Rupplin
 */
class Listenerthread extends Thread
{
    
    public Networkcontext connection;
    
    public Boolean running = true;
    
    public Inputlistenerthread inputlistenerthrread;
    
    public Outputlistenerthread outputlistenerthread;
        
    /**
     * 
     * @param connection 
     */
    public Listenerthread(Networkcontext connection)
    {
        if(connection==null) throw new SecurityException("//bodi/connect");
        
        this.connection = connection;
                
        //
        
        this.setName("Listenerthread");
        
        //
        
        this.inputlistenerthrread = new Inputlistenerthread(this);
        
        this.inputlistenerthrread.start();        
                
        //
        
        this.outputlistenerthread = new Outputlistenerthread(this);                
        
        this.outputlistenerthread.start();
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
                    this.inputlistenerthrread.checkinputqueue();                                   
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.err);
                }

                try
                {
                    this.outputlistenerthread.checkoutputqueue();
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.err);
                }     
            }            
            
            this.sleepmillis(500l);
        }
        catch(Exception e)
        {
            e.printStackTrace(System.err);
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
