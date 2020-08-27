
/**
 * Write a description of DistanceFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DistanceFilter implements Filter{
    private Location place; 
    private double disMin; 
    private String filterName;
    
    public DistanceFilter(Location loca, double min, String filterName) { 
        disMin = min;
        place = loca;
        this.filterName = filterName;
    } 

    public boolean satisfies(QuakeEntry qe) { 
        return qe.getLocation().distanceTo(place) <= disMin; 
    } 
    
    public String getName() {
        return filterName;
    }
}
