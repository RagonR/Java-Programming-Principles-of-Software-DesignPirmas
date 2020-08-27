
/**
 * Write a description of DepthFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DepthFilter implements Filter{
    private double depMin; 
    private double depMax; 
    private String filterName;
    
    public DepthFilter(double min, double max, String filterName) { 
        depMin = min;
        depMax = max;
        this.filterName = filterName;
    } 

    public boolean satisfies(QuakeEntry qe) { 
        return qe.getDepth() >= depMin && qe.getDepth() <= depMax; 
    } 
    
    public String getName() {
        return filterName;
    }
}
