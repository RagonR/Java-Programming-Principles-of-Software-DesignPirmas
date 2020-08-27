
import java.util.*;
import edu.duke.*;

public class EarthQuakeClient {
    
    private String source;
    
    public EarthQuakeClient () {
        //source = "data/nov20quakedatasmall.atom";    
        source = "data/nov20quakedata.atom"; 
        //source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
    }
    
    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData, double magMin) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        //TODO
        for (QuakeEntry qe : quakeData) {
            if (qe.getMagnitude() > magMin) {
                answer.add(qe);
            }
        }
        return answer;              
    }
    
    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData, double distMax, Location from) {      
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // TODO
        for (QuakeEntry qe : quakeData) {
            //System.out.println(qe.getLocation().distanceTo(from));
            if (qe.getLocation().distanceTo(from) < distMax) {
                answer.add(qe);
            }
        }
        return answer;
    }
    
    public ArrayList<QuakeEntry> filterByDepth (ArrayList<QuakeEntry> quakeData, double minDepth, double maxDepth){   
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for (QuakeEntry qe : quakeData) {
            if (qe.getDepth() > minDepth && qe.getDepth() < maxDepth) {
                answer.add(qe);
            }
        }    
        return answer;
    } 
    
    public ArrayList<QuakeEntry> filterByPhrase (ArrayList<QuakeEntry> quakeData, String whereToSearch, String phrase){ 
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for (QuakeEntry qe : quakeData) {
            String title = qe.getInfo();
            switch (whereToSearch){
                case "end":
                    if(title.endsWith(phrase)){
                        answer.add(qe);
                    }
                    break;
                case "any":
                    if(title.contains(phrase)){
                        answer.add(qe);
                    }
                    break;
                case "start":
                    if(title.startsWith(phrase)){
                        answer.add(qe);
                    }
                    break;
                default :
                    break;
            }
        }
        return answer;
    }
    
    public void quakesByPhrase () {
        EarthQuakeParser parser = new EarthQuakeParser();
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("read data for " + list.size() + " quakes");
        String whereToSearch = "any";
        String phrase = "Creek";
        ArrayList<QuakeEntry> answer = filterByPhrase(list, whereToSearch, phrase);
        for (QuakeEntry qe : answer) {
           System.out.println(qe); 
        }
        System.out.println("Found " + answer.size() + " quakes that match " + phrase + " at " + whereToSearch);    
    }
    
    public ArrayList<QuakeEntry> getClosest(ArrayList<QuakeEntry> quakeData, Location current, int howMany){
        ArrayList<QuakeEntry> copy = new ArrayList<QuakeEntry>(quakeData);
        ArrayList<QuakeEntry> ret = new ArrayList<QuakeEntry>();
        for(int j=0; j < howMany; j++) {
            int minIndex = 0;
            for(int k=1; k < copy.size(); k++){
                QuakeEntry quake = copy.get(k);
                Location loc = quake.getLocation();
                if (loc.distanceTo(current) < 
                    copy.get(minIndex).getLocation().distanceTo(current)){
                    minIndex = k;   
                }
            }
            if(minIndex != 0){
                ret.add(copy.get(minIndex));
                copy.remove(minIndex);
            }
        }
        return ret;
    }
    
    public void dumpCSV(ArrayList<QuakeEntry> list){
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                              qe.getLocation().getLatitude(),
                              qe.getLocation().getLongitude(),
                            qe.getMagnitude(),
                              qe.getInfo());
        }
    }
    
    public void quakesOfDepth () {
        EarthQuakeParser parser = new EarthQuakeParser();
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("read data for " + list.size() + " quakes");
        
        ArrayList<QuakeEntry> listByDepth = filterByDepth(list, -8000.0, -5000.0);   
        for (QuakeEntry qe : listByDepth) {
           System.out.println(qe); 
        }
        System.out.println("number found: " + listByDepth.size());
    }
    
    /*public void indexOfLargest () {
        EarthQuakeParser parser = new EarthQuakeParser();
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("read data for " + list.size() + " quakes");
        double magMax = 0.0;
        int index = 0;
        int i = 0;
        for (QuakeEntry qe : list) {
            if (qe.getMagnitude() > magMax) {
                magMax = qe.getMagnitude();
                index = i;
            }
            i++;
        }
        System.out.println("earthquake is at location "+ index + " and has magnitude " + magMax);
    }*/
    
    
    public ArrayList<QuakeEntry> getLargest (ArrayList<QuakeEntry> quakeData, int howMany) {
        ArrayList<QuakeEntry> copy = new ArrayList<QuakeEntry>(quakeData);
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(int j=0; j < howMany; j++) {
            int minIndex = 0;
            double maxMag = 0;
            for(int k=1; k < copy.size(); k++){
                QuakeEntry quake = copy.get(k);
                if (quake.getMagnitude() > maxMag){
                    maxMag = quake.getMagnitude();
                    minIndex = k;   
                }
            }
            if(minIndex != 0){
                answer.add(copy.get(minIndex));
                copy.remove(minIndex);
            }
        }
        return answer;              
    }
    
    
    public void findLargestQuakes () {
        EarthQuakeParser parser = new EarthQuakeParser();
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("read data for " + list.size() + " quakes");
        ArrayList<QuakeEntry> listOfLargest = getLargest(list, 5);   
        for (QuakeEntry qe : listOfLargest) {
           System.out.println(qe); 
        }
        System.out.println("number found: " + listOfLargest.size());
    }
    
    public void bigQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("read data for " + list.size() + " quakes");
        ArrayList<QuakeEntry> listBig = filterByMagnitude(list, 5.0);   
        for (QuakeEntry qe : listBig) {
           System.out.println(qe); 
        }
        System.out.println("number found: " + listBig.size());
    }
    
    public void createCSV(){
        EarthQuakeParser parser = new EarthQuakeParser();
        ArrayList<QuakeEntry> list = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
    }
    
    public void findClosestQuakes(){
        EarthQuakeParser parser = new EarthQuakeParser();
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for " + list.size());
        
        int howMany = 3;
        
        Location Durham  = new Location(35.988, -78.907);
        System.out.println("Durham: ");
        outPrintForFindClosestQuakes(list, Durham, howMany);
        
        Location Bridgeport  = new Location(38.17, -118.82);
        System.out.println("Bridgeport: ");
        outPrintForFindClosestQuakes(list, Bridgeport, howMany);
        
        Location Jakarta  = new Location(-6.211,106.845);
        System.out.println("Jakarta: ");
        outPrintForFindClosestQuakes(list, Jakarta, howMany);
    }
    
    public void outPrintForFindClosestQuakes (ArrayList<QuakeEntry> list, Location jakarta, int howMany){
        ArrayList<QuakeEntry> close = getClosest(list, jakarta, howMany);
        for(int k=0; k < close.size(); k++){
        	QuakeEntry entry = close.get(k);
        	double distanceInMeters = jakarta.distanceTo(entry.getLocation());
        	System.out.printf("%4.2f\t %s\n", distanceInMeters/1000, entry);
        }
        System.out.println("number found: " + close.size());
    }
        
    public void closeToMe() {
        EarthQuakeParser parser = new EarthQuakeParser();
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("# quakes read: " + list.size());
        
        int radius = 1000000;
        Location Durham  = new Location(35.988, -78.907);
        System.out.println("Durham: ");
        outPrintForCloseToMe(list, Durham, radius);
        
        Location Bridgeport  = new Location(38.17, -118.82);
        System.out.println("Bridgeport: ");
        outPrintForCloseToMe(list, Bridgeport, radius);        
    }
    
    public void outPrintForCloseToMe (ArrayList<QuakeEntry> list, Location Durham, int radius) {
        ArrayList<QuakeEntry> close = filterByDistanceFrom(list, radius, Durham);
        for (int k=0; k< close.size(); k++) {
            QuakeEntry entry = close.get(k);
            double distanceInMeters = Durham.distanceTo(entry.getLocation());
            System.out.println(distanceInMeters/1000 + " " + entry.getInfo());
        }
        System.out.println("Found " +close.size()+" quakes that match that criteria");
    }
}
