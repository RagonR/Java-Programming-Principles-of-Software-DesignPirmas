import java.util.*;
import edu.duke.*;

public class EarthQuakeClient2 {
    private String source;
    public EarthQuakeClient2() {
        source = "data/nov20quakedata.atom";
    }

    public ArrayList<QuakeEntry> filter(ArrayList<QuakeEntry> quakeData, Filter f) { 
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe : quakeData) { 
            if (f.satisfies(qe)) { 
                answer.add(qe); 
            } 
        } 
        return answer;
    } 

    public void quakesWithFilter() { 
        EarthQuakeParser parser = new EarthQuakeParser(); 
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);         
        System.out.println("read data for "+list.size()+" quakes");
        
        //Magnitude and Depth filters
        System.out.println("Min and max magnitude filter: "); 
        Filter f1 = new MagnitudeFilter(4.0, 5.0, "Magnitude");        
        System.out.println("Min and max depth filter: "); 
        Filter f2 = new DepthFilter(-35000.0, -12000.0, "Depth"); 
        
        //Location and Prase filters
        /*System.out.println("Location filter: "); 
        Location Tokyo  = new Location(35.42, 139.43);
        Filter f1 = new DistanceFilter(Tokyo,  10000000, "Distance");
        System.out.println("Phrase filter: "); 
        String whereToSearch = "end";
        String phrase = "Japan";
        Filter f2 = new PhraseFilter(whereToSearch, phrase, "Phrase");*/
        
        ArrayList<QuakeEntry> answer  = filter(list, f1);
        answer = filter(answer, f2);
        for (QuakeEntry qe: answer) { 
            System.out.println(qe);
        }
        System.out.println("Quakes found: " + answer.size());
    }
    
    public void testMatchAllFilter () {
        EarthQuakeParser parser = new EarthQuakeParser(); 
        ArrayList<QuakeEntry> list  = parser.read(source);         
        System.out.println("read data for "+list.size()+" quakes");
        MatchAllFilter maf = new MatchAllFilter();
        Filter f1 = new MagnitudeFilter(0.0, 2.0, "Magnitude"); 
        maf.addFilter(f1);
        Filter f2 = new DepthFilter(-100000.0, -10000.0, "Depth");
        maf.addFilter(f2);
        String whereToSearch = "any";
        String phrase = "a";
        Filter f3 = new PhraseFilter(whereToSearch, phrase, "Phrase");
        maf.addFilter(f3);
        ArrayList<QuakeEntry> answer  = filter(list, maf);
        for (QuakeEntry qe: answer) { 
            System.out.println(qe);
        }
        System.out.println("Filters used are " + maf.getName());
        System.out.println("Quakes found: " + answer.size());
    }
    
    public void testMatchAllFilter2 () {
        EarthQuakeParser parser = new EarthQuakeParser(); 
        ArrayList<QuakeEntry> list  = parser.read(source);         
        System.out.println("read data for "+list.size()+" quakes");
        MatchAllFilter maf = new MatchAllFilter();
        Filter f1 = new MagnitudeFilter(0.0, 3.0, "Magnitude"); 
        maf.addFilter(f1);
        Location Oklahoma = new Location(36.1314, -95.9372);
        Filter f2 = new DistanceFilter(Oklahoma, 10000000, "Distance");
        maf.addFilter(f2);
        String whereToSearch = "any";
        String phrase = "Ca";
        Filter f3 = new PhraseFilter(whereToSearch, phrase, "Phrase");
        maf.addFilter(f3);
        ArrayList<QuakeEntry> answer  = filter(list, maf);
        for (QuakeEntry qe: answer) { 
            System.out.println(qe);
        }
        System.out.println("Filters used are " + maf.getName());
        System.out.println("Quakes found: " + answer.size());
    }
    
    public void testMinMagniFilter (){
        EarthQuakeParser parser = new EarthQuakeParser(); 
        ArrayList<QuakeEntry> list  = parser.read(source);         
        System.out.println("read data for "+list.size()+" quakes");
        System.out.println("Min magnitude filter: "); 
        Filter f = new MinMagFilter(4.0, "Magnitude Min"); 
        printOutFilters(list,f);
    }
    
        
    public void testMinMaxMagniFilter (){
        EarthQuakeParser parser = new EarthQuakeParser(); 
        ArrayList<QuakeEntry> list  = parser.read(source);         
        System.out.println("read data for "+list.size()+" quakes");
        System.out.println("Min and max magnitude filter: "); 
        Filter f1 = new MagnitudeFilter(4.0, 5.0, "Magnitude"); 
        printOutFilters(list,f1);
    }
        
    public void testMinMaxDephtFilter (){
        EarthQuakeParser parser = new EarthQuakeParser(); 
        ArrayList<QuakeEntry> list  = parser.read(source);         
        System.out.println("read data for "+list.size()+" quakes");
        System.out.println("Min and max depth filter: "); 
        Filter f2 = new DepthFilter(-10000.0, -5000.0, "Depth"); 
        printOutFilters(list,f2);
    }
            
    public void testLocationFilter (){
        EarthQuakeParser parser = new EarthQuakeParser(); 
        ArrayList<QuakeEntry> list  = parser.read(source);         
        System.out.println("read data for "+list.size()+" quakes");
        System.out.println("Location filter: "); 
        Location Durham  = new Location(35.988, -78.907);
        Filter f3 = new DistanceFilter(Durham, 1000000, "Distance");
        printOutFilters(list,f3);
    }
    
    public void testPraseFilter (){
        EarthQuakeParser parser = new EarthQuakeParser(); 
        ArrayList<QuakeEntry> list  = parser.read(source);         
        System.out.println("read data for "+list.size()+" quakes");
        System.out.println("Phrase filter: "); 
        String whereToSearch = "any";
        String phrase = "Creek";
        Filter f4 = new PhraseFilter(whereToSearch, phrase, "Phrase");
        printOutFilters(list,f4);
    }
    
    
    public void printOutFilters (ArrayList<QuakeEntry> list, Filter f) {
        ArrayList<QuakeEntry> answer  = filter(list, f);
        for (QuakeEntry qe: answer) { 
            System.out.println(qe);
        } 
    }
    
    public void createCSV() {
        EarthQuakeParser parser = new EarthQuakeParser();
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: "+list.size());
    }

    public void dumpCSV(ArrayList<QuakeEntry> list) {
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }
    }

}
