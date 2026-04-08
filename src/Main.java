import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        int antalKandidater = 50;
        int teamSize = 5;
        double alpha = 2;
        double beta = 1;
        boolean generateNewPool = false;

        List<Person> pool;

        if (generateNewPool) {
            pool = DataSetSynthesizer.generatePool(antalKandidater);
            FileCreator csvCreator = new CsvFileCreator("candidate_pool");
            try {
                csvCreator.writeToFile(pool);
                System.out.println("Pool sparad till: " + csvCreator.getFilename());
            } catch (IOException e) {
                System.err.println("CSV-fel: " + e.getMessage());
            }
        } else {
            try {
                pool = new CsvFileReader("candidate_pool.csv").getPoolOfPersons();
                System.out.println("Pool läst från: candidate_pool.csv");
            } catch (IOException e) {
                System.err.println("CSV-fel: " + e.getMessage());
                return;
            }
        }
        System.out.println("Pool of candidates:" + pool);
        Map<Skills, Double> requirements = new EnumMap<>(Skills.class);
        requirements.put(Skills.JAVA, 0.3);
        requirements.put(Skills.PYTHON, 0.6);
        requirements.put(Skills.TEAMWORK, 0.9);

        Task task = new Task(teamSize, requirements);
        System.out.println("Kör Exhaustive Search...");
        long startTimeES = System.currentTimeMillis();
        ExhaustiveSearch es = new ExhaustiveSearch();
        es.Compute(task, pool, alpha, beta);

        long endTimeES = System.currentTimeMillis();
        System.out.println("Exhaustive Search tid: " + (endTimeES - startTimeES) + " ms");
        System.out.println("Best team ES:" + es.getBestTeam());
        System.out.println("best score ES: " + es.getBestScore());
        System.out.println("Kör Greedy Heuristic...");
        long startTimeGH = System.currentTimeMillis();
        GreedyHeuristic gh = new GreedyHeuristic();
        gh.Compute(task, pool, alpha, beta);
        long endTimeGH = System.currentTimeMillis();
        System.out.println("Greedy Heuristic tid: " + (endTimeGH - startTimeGH) + " ms");
        System.out.println("Best team GH:" + gh.getBestTeam());
        System.out.println("best score GH: " + gh.getBestScore());
    }
}
