import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        // Parametrar för de fyra experimenten enligt tabellen
        int[] poolSizes = {20,30,40,50,60,70,80,90,100,110,120};
        int iterations = 1;

        // Definiera uppgiften (Task) - samma för alla experiment
        Map<Skills, Double> requiredSkills = new EnumMap<>(Skills.class);
        requiredSkills.put(Skills.C_PLUS_PLUS, 0.7);
        requiredSkills.put(Skills.PYTHON, 0.5);
        requiredSkills.put(Skills.JAVA, 0.9);
        requiredSkills.put(Skills.TEAMWORK, 0.8);
        requiredSkills.put(Skills.CREATIVITY, 0.6);

        ExhaustiveSearch exhaustiveSearch = new ExhaustiveSearch();
        BasicGreedy greedyHeuristic = new BasicGreedy();
        ScoringFunction scorer = new ScoringFunction();

        CsvFileCreator resultsCsv = new CsvFileCreator("Result_Experiments_5");
        resultsCsv.writeResultHeader();

        // 1. Warmup för JVM
        System.out.println("Värmer upp JVM...");
        List<Person> warmupPool = getDataSet(20);
        for(int i = 0; i < 10; i++) {
            exhaustiveSearch.Compute(new Task(5, requiredSkills), warmupPool, 0.5, 0.5);
            greedyHeuristic.Compute(new Task(5, requiredSkills), warmupPool, 0.5, 0.5);
        }
        // 2. Kör Experiment 1 till 4
        for (int exp = 0; exp < 11; exp++) {
            int currentPoolSize = poolSizes[exp];
            int currentTeamSize = 5;
            Task currentTask = new Task(currentTeamSize, requiredSkills);
            List<Person> pool = getDataSet(currentPoolSize);

            System.out.printf("%n--- Startar Experiment %d (Pool: %d, Team: %d) ---%n",
                    (exp + 1), currentPoolSize, currentTeamSize);

            // Stegvis variation: Alpha 0.0 -> 1.0 med inkrement 0.1

                double alpha = 0.5;
                double beta = 0.5;

                double totalEsTime = 0, totalGreedyTime = 0;
                double optComp = 0, optWork = 0, greedyComp = 0, greedyWork = 0;

                System.out.printf("Testar Alpha: %.1f, Beta: %.1f... ", alpha, beta);

                for (int iter = 0; iter < iterations; iter++) {

                    // --- Exhaustive Search ---
                    // OBS: Denna kommer ta lång tid för Exp 3 & 4!
                    long esStart = System.nanoTime();
                    exhaustiveSearch.Compute(currentTask, pool, alpha, beta);
                    long esEnd = System.nanoTime();
                    totalEsTime += (esEnd - esStart) / 1_000_000.0;

                    if (iter == 0) {
                        List<Person> optTeam = exhaustiveSearch.getBestTeam();
                        optComp = scorer.competenceSum(optTeam, currentTask);
                        optWork = scorer.workloadMax(optTeam);
                    }

                    // --- Greedy ---
                    int greedySubRounds = 10000;
                    long greedyStart = System.nanoTime();
                    for(int j = 0; j < greedySubRounds; j++) {
                        greedyHeuristic.Compute(currentTask, pool, alpha, beta);
                    }
                    long greedyEnd = System.nanoTime();

                    double avgTimeThisIteration = ((greedyEnd - greedyStart) / 10000000.0) / greedySubRounds;
                    totalGreedyTime += avgTimeThisIteration;
                    if (iter == 0) {
                        List<Person> greedyTeam = greedyHeuristic.getBestTeam();
                        greedyComp = scorer.competenceSum(greedyTeam, currentTask);
                        greedyWork = scorer.workloadMax(greedyTeam);
                    }


                double finalAvgEsTime = totalEsTime / iterations;
                double finalAvgGreedyTime = totalGreedyTime / iterations;
                    double greedyScore = greedyHeuristic.getBestScore();
                    double esScore = exhaustiveSearch.getBestScore();
                    double approx = greedyScore/esScore;
                // Spara till CSV - Se till att ordningen matchar din CsvFileCreator.writeResults
                resultsCsv.writeResults(
                        currentPoolSize,
                        currentTeamSize,
                        alpha,
                        beta,
                        optComp,
                        optWork,
                        greedyComp,
                        greedyWork,
                        finalAvgEsTime,
                        finalAvgGreedyTime,
                        greedyScore,
                        esScore,
                        approx
                );

                System.out.println("Klar.");
            }
        }

        System.out.println("%nAlla experiment färdiga. Resultat sparade i CSV.");
    }

    public static List<Person> getDataSet(int size) throws FileNotFoundException {
        CsvFileReader fileReader = new CsvFileReader("candidate_normal_pool.csv");
        return fileReader.getPoolOfPersons(size);
    }
}