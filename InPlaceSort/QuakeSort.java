import java.util.*;

public class QuakeSort {
    public int getSmallestMagnitude(ArrayList<QuakeEntry> quakes, int from) {
        int minIdx = from;
        for (int i = from +1; i < quakes.size(); i++) {
            if (quakes.get(i).getMagnitude() < quakes.get(minIdx).getMagnitude()) {
                minIdx = i;
            }
        }
        return minIdx;
    }
    
    public int getLargestDepth (ArrayList<QuakeEntry> quakes, int from) {
        int minIdx = from;
        for (int i = from +1; i < quakes.size(); i++) {
            if (quakes.get(i).getDepth() < quakes.get(minIdx).getDepth()) {
                minIdx = i;
            }
        }
        return minIdx;
    }
    
    public void sortByLargestDepth (ArrayList<QuakeEntry> in) {
        //count from 0 to < in.size()
        for(int i = 0; i < 70; i++) {
            /* find the index of the smallest quake*/
            int minIdx = getLargestDepth(in, i);
            /* swap the ith quake with the minIdxth quake */
            QuakeEntry qi = in.get(i);
            QuakeEntry qmin = in.get(minIdx);
            in.set(i, qmin);
            in.set(minIdx, qi);
        }
    }
    
    public void sortByMagnitude(ArrayList<QuakeEntry> in) {
        //count from 0 to < in.size()
        for(int i = 0; i < in.size(); i++) {
            /* find the index of the smallest quake*/
            int minIdx = getSmallestMagnitude(in, i);
            /* swap the ith quake with the minIdxth quake */
            QuakeEntry qi = in.get(i);
            QuakeEntry qmin = in.get(minIdx);
            in.set(i, qmin);
            in.set(minIdx, qi);
        }
    }
    
    public boolean checkInSortedOrder(ArrayList<QuakeEntry> quakes){
        for (int i = 0; i < quakes.size() - 1; i++) {
            if (quakes.get(i).getMagnitude() > quakes.get(i + 1).getMagnitude()) {
                return false;
            }
        }        
        return true;
    }
    
    public void sortByMagnitudeWithBubbleSortWithCheck(ArrayList<QuakeEntry> in){
        for (int i = 0; i < in.size() - 1; i++) {
            onePassBubbleSort(in, i);
            
            if (checkInSortedOrder(in)) {
                System.out.println("Number of passes = " + (i + 1));
                break;
            }
        }
        
    }
    
    public void sortByMagnitudeWithCheck(ArrayList<QuakeEntry> in){
        for(int i=0; i<in.size(); i++){
            int minIdx = getSmallestMagnitude(in,i);
            QuakeEntry temp = in.get(i);
            in.set(i, in.get(minIdx));
            in.set(minIdx, temp);
            
            if (checkInSortedOrder(in)) {
                System.out.println("Number of passes = " + (i + 1));
                break;
            }
        }
    }
    
    public void onePassBubbleSort (ArrayList<QuakeEntry> in, int numSorted) {
        for(int i=0; i<in.size()-1-numSorted; i++){
            QuakeEntry currQe = in.get(i);
            QuakeEntry nextQe = in.get(i+1);
            
            if(currQe.getDepth() > nextQe.getDepth()){
                in.set(i+1,currQe);
                in.set(i, nextQe);
            }
        }
    }
    
    public void sortByMagnitudeWithBubbleSort (ArrayList<QuakeEntry> in){
        for(int i=0; i<in.size()-1;i++){
            onePassBubbleSort(in, i);
        }
    }
    
    public void testSort(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/earthQuakeDataDec6sample2.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        //sortByMagnitude(list);
        //sortByLargestDepth(list);
        //sortByMagnitudeWithBubbleSort(list);
        //sortByMagnitudeWithCheck(list);
        sortByMagnitudeWithBubbleSortWithCheck(list);
        for(QuakeEntry qe: list) {
            System.out.println(qe);
        }
    }
    
}
