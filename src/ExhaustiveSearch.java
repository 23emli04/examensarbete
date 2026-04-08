import java.util.ArrayList;
import java.util.List;

public class ExhaustiveSearch implements Algorithm {
    private List<Person> bestTeam;
    private double bestScore = Double.NEGATIVE_INFINITY;
    private ScoringFunction scoringFunction;

    public ExhaustiveSearch() {
        this.scoringFunction = new ScoringFunction();
    }

    @Override
    public void Compute(Task task, List<Person> pool, double alpha, double beta) {
        this.bestScore = Double.NEGATIVE_INFINITY;
        this.bestTeam = null;
        generateAndEvaluate(pool, task.getTeamSize(), 0, new ArrayList<>(), task, alpha, beta);
    }

    private void generateAndEvaluate(List<Person> pool, int teamSize, int start, List<Person> current, Task task, double alpha, double beta) {
        if (current.size() == teamSize) {
            double score = calculateScore(current, task, alpha, beta);
            if (score > bestScore) {
                bestScore = score;
                bestTeam = new ArrayList<>(current);
            }
            return;
        }
        for (int i = start; i < pool.size(); i++) {
            current.add(pool.get(i));
            generateAndEvaluate(pool, teamSize, i + 1, current, task, alpha, beta);
            current.remove(current.size() - 1);
        }
    }
    public List<Person> getBestTeam() {
        return bestTeam;
    }
    public double getBestScore() {
        return bestScore;
    }
    private double calculateScore(List<Person> team, Task task, double alpha, double beta) {
        double score;
        score = beta * scoringFunction.competenceSum(team, task) - alpha * scoringFunction.workloadMax(team);
        return score;

        }

  

}
