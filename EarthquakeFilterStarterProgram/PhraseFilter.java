
/**
 * Write a description of PhraseFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PhraseFilter implements Filter{
    private String whereToSearch;
    private String phrase;
    private String filterName;
    
    public PhraseFilter(String type, String word, String filterName) { 
        whereToSearch = type;
        phrase = word;
        this.filterName = filterName;
    } 

    public boolean satisfies(QuakeEntry qe) { 
        return whereToSearch == "end" && qe.getInfo().endsWith(phrase) || 
                whereToSearch == "any" && qe.getInfo().contains(phrase) ||
                whereToSearch == "start" && qe.getInfo().startsWith(phrase);
    }
    
    public String getName() {
        return filterName;
    }
}
