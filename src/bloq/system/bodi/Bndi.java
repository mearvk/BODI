package bloq.system.bodi;

import java.util.HashMap;
import java.util.Map;

/**
 * Bloq Native Directory Interface (BNDI)
 * 
 * @see http://github.com/mearvk/Bloq/wiki
 * @version 1.00
 * @author Max Rupplin
 */
public class Bndi 
{
    protected final Integer hash = 0x888fe8;
        
    public static Map<String, Bndicontext> contexts = new HashMap();
    
    public static void main(String[] args)
    {        
        Object one = new Object(); 
        Object two = new Object();
        Object three = new Object();
        
        Bndi.setcontext("//apml/tests/");
        
        Bndi.context("//apml/tests/").put("one",one);
        Bndi.context("//apml/tests/").put("two",two);
        Bndi.context("//apml/tests/").put("three",three);
        
        Object one1 = Bndi.context("//apml/tests/").pull(one.hashCode());
        Object one2 = Bndi.context("//apml/tests/").pull(one);
        Object one3 = Bndi.context("//apml/tests/").pull("one");
        
        Object two1 = Bndi.context("//apml/tests/").pull(two.hashCode());
        Object two2 = Bndi.context("//apml/tests/").pull(two);
        Object two3 = Bndi.context("//apml/tests/").pull("two");

        Object three1 = Bndi.context("//apml/tests/").pull(three.hashCode());
        Object three2 = Bndi.context("//apml/tests/").pull(three);
        Object three3 = Bndi.context("//apml/tests/").pull("three");     

        if(one==one1) System.err.println("Test complete: one==one1");
        if(one==one2) System.err.println("Test complete: one==one2");
        if(one==one3) System.err.println("Test complete: one==one3");
        
        if(two==two1) System.err.println("Test complete: two==two1");
        if(two==two2) System.err.println("Test complete: two==two2");
        if(two==two3) System.err.println("Test complete: two==two3");   
        
        if(three==three1) System.err.println("Test complete: three==three1");
        if(three==three2) System.err.println("Test complete: three==three2");
        if(three==three3) System.err.println("Test complete: three==three3");           
    }
    
    /**
     * 
     * @param context
     * @return 
     */
    private static Map<String, Bndicontext> getcontexts(String context)
    {
        return Bndi.contexts;
    }       
   
    /**
     * 
     * @param context
     * @return 
     */
    public static void setcontext(String context)
    {                   
        if(contexts.get(context)==null) Bndi.contexts.put(context, new Bndicontext(context));
    }          
       
    public static Bndicontext context(String context)
    {     
        return contexts.get(context);
        //return contexts.get(context) == null ? Bndi.setcontext(context) : Bndi.contexts.get(context);
    }  
}

