package bloq.system.bodi.server.remote;

import java.util.LinkedList;

import java.util.Queue;

/**
 *
 * @author Max Rupplin
 */
public class Inputqueue 
{
    public Queue<Networkcontext> queue = new LinkedList();
    
    public void add(Networkcontext connection)
    {
        this.queue.add(connection);
    }
    
    public void remove(Networkcontext connection)
    {
        this.queue.remove(connection);
    }
    
    public Networkcontext peek()
    {
        return this.queue.peek();
    }
}
