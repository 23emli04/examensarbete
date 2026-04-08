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
        List<List<Person>> allTeams = generateTeams(pool, task.getTeamSize());
                System.err.println("Generated " + allTeams.size() + " teams for evaluation.");
        evaluateTeams(allTeams, task, alpha, beta);
    }

    private List<List<Person>> generateTeams(List<Person> pool, int teamSize) {
        List<List<Person>> allTeams = new ArrayList<>();
        int n = pool.size();
        for (int i = 0; i < (1 << n); i++) {
            List<Person> team = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    team.add(pool.get(j));
                }
            }
            if (team.size() == teamSize) {
                allTeams.add(team);
            }
        }
        return allTeams;
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
