import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int antalKandidater = 100;
        int antalKompetenser = 5;
        List<Candidate> pool = DataSetSynthesizer.generatePool(antalKandidater, antalKompetenser);
        
        FileCreator csvCreator = new CsvFileCreator("candidate_pool");
        
        try {
            csvCreator.writeToFile(pool);
            System.out.println("Success! Filen sparad som: " + csvCreator.getFilename());
        } catch (IOException e) {
            System.err.println("Kunde inte skriva till filen: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ett oväntat fel uppstod: " + e.getMessage());
        }
    }
}