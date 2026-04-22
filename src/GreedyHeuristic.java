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
        // 1. Get the fixed size required by the task
        int fixedSize = task.getTeamSize();

        // 2. According to Algorithm 1, we find the assignment for the possible value of L_max.
        // Since the task dictates the size, tau = fixedSize.
        int tau = fixedSize;

        // 3. A_tau = Greedy(X_tau, J)
        // We pass tau into the subroutine to ensure we pick exactly that many people.
        List<Person> teamTau = runGreedySubroutine(pool, task, tau, alpha, beta);

        // 4. Update the global state
        this.bestTeam = teamTau;
        this.bestScore = calculateScore(teamTau, task, alpha, beta);
    }

    private List<Person> runGreedySubroutine(List<Person> pool, Task task, int tau, double alpha, double beta) {
        List<Person> currentTeam = new ArrayList<>();
        List<Person> remainingPool = new ArrayList<>(pool);

        // Continue until the team reaches the FIXED size (tau)
        while (currentTeam.size() < tau && !remainingPool.isEmpty()) {
            Person bestCandidate = null;
            double bestStepScore = Double.NEGATIVE_INFINITY;

            for (Person candidate : remainingPool) {
                List<Person> testTeam = new ArrayList<>(currentTeam);
                testTeam.add(candidate);

                // Calculate score for this specific combination
                double score = calculateScore(testTeam, task, alpha, beta);

                if (score > bestStepScore) {
                    bestStepScore = score;
                    bestCandidate = candidate;
                }
            }

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
        // Core objective function from your requirement
        return (beta * scoringFunction.competenceSum(team, task)) - (alpha * scoringFunction.workloadMax(team));
    }

    public List<Person> getBestTeam() { return bestTeam; }
    
    public double getBestScore() { return bestScore; }
}