import java.util.ArrayList;
import java.util.List;

public class BasicGreedy implements Algorithm {
    private List<Person> bestTeam;
    private double bestScore;
    private ScoringFunction scoringFunction;
    private double bestTeamCompetenceSum;
    private double bestTeamWorkload;
    public BasicGreedy() {
        this.scoringFunction = new ScoringFunction();
    }
    @Override
    public void Compute(Task task, List<Person> pool, double alpha, double beta) {

        List<Person> bestTeam = runGreedySubroutine(pool, task, alpha, beta);
        this.bestTeam = bestTeam;
        this.bestTeamCompetenceSum = setBestTeamCompetenceSum(task);
        this.bestTeamWorkload = setBestTeamWorkload();
        this.bestScore = calculateScore(bestTeam, task, alpha, beta);
    }

    private List<Person> runGreedySubroutine(List<Person> pool, Task task, double alpha, double beta) {
        List<Person> currentTeam = new ArrayList<>();
        List<Person> remainingPool = new ArrayList<>(pool);

        // Fortsätt tills teamet är fullt eller ingen finns kvar i poolen
        while (currentTeam.size() < task.getTeamSize() && !remainingPool.isEmpty()) {
            Person bestCandidate = null;
            // Starta på lägsta möjliga värde för att säkerställa att första kandidaten "vinner"
            double bestStepScore = Double.NEGATIVE_INFINITY;

            // INNERSTA LOOPEN: "giriga delen"
            for (Person candidate : remainingPool) {
                // Skapa ett tillfälligt team för att testa kandidaten tillsammans med de redan valda
                List<Person> testTeam = new ArrayList<>(currentTeam);
                testTeam.add(candidate);

                // Beräkna poäng för detta specifika scenario
                double score = calculateScore(testTeam, task, alpha, beta);

                // Om denna kandidat presterar bättre än de tidigare i samma runda, spara
                if (score > bestStepScore) {
                    bestStepScore = score;
                    bestCandidate = candidate;
                }
            }

            // När hela poolen sökts igenom, lägg till den person som gav bäst resultat i denna runda
            if (bestCandidate != null) {
                currentTeam.add(bestCandidate);
                remainingPool.remove(bestCandidate);
            } else {
                break;
            }
        }
        return currentTeam;
    }

    private double calculateScore(List<Person> team, Task task, double alpha, double beta) {
        return (beta * scoringFunction.competenceSum(team, task)) - (alpha * scoringFunction.workloadMax(team));
    }
    public double setBestTeamCompetenceSum(Task t){
        return scoringFunction.competenceSum(this.bestTeam,t);
    }
    public double setBestTeamWorkload(){
        return scoringFunction.workloadMax(bestTeam);
    }

    public List<Person> getBestTeam() { return bestTeam; }
    
    public double getBestScore() { return bestScore; }

    public double getBestTeamCompetenceSum() {
        return bestTeamCompetenceSum;
    }

    public double getBestTeamWorkload() {
        return bestTeamWorkload;
    }
}