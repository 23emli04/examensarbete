import java.util.ArrayList;
import java.util.List;

public class ExhaustiveSearch implements Algorithm {
    private List<Person> bestTeam;
    private double bestScore = Double.NEGATIVE_INFINITY;
    private ScoringFunction scoringFunction;

    public ExhaustiveSearch() {
        this.scoringFunction = new ScoringFunction();
    }

    public ExhaustiveSearch(ScoringFunction scoringFunction) {
        this.scoringFunction = scoringFunction;
    }

    @Override
    public void Compute(Task task, List<Person> pool, double alpha, double beta)
     {
         List<List<Person>> allTeams = new ArrayList<>();
         generateTeams(pool, task.getTeamSize(), 0, new ArrayList<>(), allTeams);
         System.out.println("Generated");
        evaluateTeams(allTeams, task, alpha, beta);
    }

    private void generateTeams(List<Person> pool, int teamSize, int start, List<Person> current, List<List<Person>> allTeams) {
        if (current.size() == teamSize) {
            allTeams.add(new ArrayList<>(current));
            return;
        }
        for (int i = start; i < pool.size(); i++) {
            current.add(pool.get(i));
            generateTeams(pool, teamSize, i + 1, current, allTeams);
            current.remove(current.size() - 1);
        }
    }

    private void evaluateTeams(List<List<Person>> allTeams, Task task, double alpha, double beta) {
        for (List<Person> team : allTeams) {
            double score = calculateScore(team, task, alpha, beta);
            if (score > bestScore) {
                bestScore = score;
                bestTeam = team;
            }
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
