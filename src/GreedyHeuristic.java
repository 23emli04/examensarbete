import java.util.ArrayList;
import java.util.List;

public class GreedyHeuristic implements Algorithm {
    private List<Person> bestTeam;
    private double bestScore;
    private ScoringFunction scoringFunction;
    public GreedyHeuristic() {
        this.scoringFunction = new ScoringFunction();
    }

    public GreedyHeuristic(ScoringFunction scoringFunction) {
        this.scoringFunction = scoringFunction;
    }

    @Override
    public void Compute(Task task, List<Person> pool, double alpha, double beta) {
        List<Person> currentTeam = new ArrayList<>();
        List<Person> remainingPool = new ArrayList<>(pool);
        int targetSize = task.getTeamSize();
        while (currentTeam.size() < targetSize && !remainingPool.isEmpty()) {
            Person bestCandidate = null;
            double bestStepScore = Double.NEGATIVE_INFINITY;
            for (Person candidate : remainingPool) {
                List<Person> testTeam = new ArrayList<>(currentTeam);
                testTeam.add(candidate);
                double currentScore = calculateScore(testTeam, task, alpha, beta);
                if (currentScore > bestStepScore) {
                    bestStepScore = currentScore;
                    bestCandidate = candidate;
                }
            }
            if (bestCandidate != null) {
                currentTeam.add(bestCandidate);
                remainingPool.remove(bestCandidate);
                bestScore = bestStepScore;
            }
        }
        this.bestTeam = currentTeam;
    }

    private double calculateScore(List<Person> team, Task task, double alpha, double beta) {
        return (beta * scoringFunction.competenceSum(team, task)) - (alpha * scoringFunction.workloadMax(team));
    }

    public List<Person> getBestTeam() { return bestTeam; }
    
    public double getBestScore() { return bestScore; }
}