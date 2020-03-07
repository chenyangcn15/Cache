/**
 * @author Chen
 * @return 
 *
 */
import java.util.*;

class LFUCache {
    private int capacity;
    private int min;
    private HashMap<Integer, Integer> cache;
    private HashMap<Integer, LinkedHashSet<Integer>> freqlist;
    private HashMap<Integer, Integer> counts;
    
    public LFUCache(int capacity) {
        this.capacity = capacity;
        min = -1;
        cache = new HashMap<Integer, Integer>();
        freqlist = new HashMap<Integer, LinkedHashSet<Integer>>();
        freqlist.put(1, new LinkedHashSet<>());
        counts = new HashMap<Integer, Integer>();
    }
    
    public int get(int key) {
        // return cache.getOrDefault(key, -1);
        if(!cache.containsKey(key))
            return -1;
        if(counts.get(key) == min && freqlist.get(counts.get(key)).size() == 0)
            min++;
        int count = counts.get(key);
        freqlist.get(count).remove(key);
        counts.put(key, count+1);  
        if(!freqlist.containsKey(count+1))
            freqlist.put(count+1, new LinkedHashSet<>());
        freqlist.get(count+1).add(key);
        return cache.get(key);        
    }
    
    public void put(int key, int value) {
        if(capacity <= 0)
            return;
        if(!cache.containsKey(key))
        {
            if(cache.size() >= capacity)
            {
                int evicted = freqlist.get(min).iterator().next();
                // System.out.println(min);
                cache.remove(evicted);
                counts.remove(evicted);
                freqlist.get(min).remove(evicted);
            }
            cache.put(key, value);
            counts.put(key, 1);
            freqlist.get(1).add(key);
            min = 1;
        }
        else
        {
            cache.put(key, value);
            get(key);
            // freqlist.get(counts.get(key)).remove(key);
            // counts.put(key, counts.get(key)+1);        
            // freqlist.get(counts.get(key)).add(key);
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */

public class solution {
	public static void main(String[] args)
	{
//		Scanner sc = new Scanner(System.in);
		LFUCache obj = new LFUCache(3);
		obj.put(2,2);
		obj.put(1,1);
		System.out.println(obj.get(2));
		System.out.println(obj.get(1));
		System.out.println(obj.get(2));
		obj.put(3,3);
		obj.put(4,4);
		System.out.println(obj.get(3));
		System.out.println(obj.get(2));
		System.out.println(obj.get(1));
		System.out.println(obj.get(4));
	}

}
