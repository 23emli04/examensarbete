import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
public class Main {
    public static void main(String[] args) {
        int antalKandidater = 10; 
        int teamSize = 5;
        double alpha = 0.5;
        double beta = 0.5;

        List<Person> pool = DataSetSynthesizer.generatePool(antalKandidater);
        
        Map<Skills, Double> requirements = new EnumMap<>(Skills.class);
        requirements.put(Skills.JAVA, 0.8);
        requirements.put(Skills.PYTHON, 0.6);
        requirements.put(Skills.TEAMWORK, 0.9);
        
        Task task = new Task(teamSize, requirements);

        FileCreator csvCreator = new CsvFileCreator("candidate_pool");
        try {
            csvCreator.writeToFile(pool);
            System.out.println("Pool sparad till: " + csvCreator.getFilename());
        } catch (IOException e) {
            System.err.println("CSV-fel: " + e.getMessage());
        }

        System.out.println("Kör Exhaustive Search...");
        long startTimeES = System.currentTimeMillis();
        
        ExhaustiveSearch es = new ExhaustiveSearch();
        es.Compute(task, pool, alpha, beta);
        
        long endTimeES = System.currentTimeMillis();
        System.out.println("ES klar på: " + (endTimeES - startTimeES) + " ms");
        System.out.println(es.getBestTeam().toString());
    }
}
