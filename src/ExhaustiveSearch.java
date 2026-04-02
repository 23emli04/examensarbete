import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExhaustiveSearch implements Algorithm {
    private List<Person> bestTeam;
    private double maxScore = Double.NEGATIVE_INFINITY;

    @Override
    public void Compute(Task task, List<Person> pool, double alpha, double beta) {
        bestTeam = new ArrayList<>();
        maxScore = Double.NEGATIVE_INFINITY;
        int k = task.getTeamSize();
        generateCombinations(pool, new ArrayList<>(), 0, k, task, alpha, beta);
        
        System.out.println("Exhaustive Search klar. Bästa score: " + maxScore);
    }

    private void generateCombinations(List<Person> pool, List<Person> currentTeam, 
                                      int start, int k, Task task, double alpha, double beta) {
        if (currentTeam.size() == k) {
            evaluateTeam(currentTeam, task, alpha, beta);
            return;
        }
        for (int i = start; i < pool.size(); i++) {
            currentTeam.add(pool.get(i));
            generateCombinations(pool, currentTeam, i + 1, k, task, alpha, beta);
            currentTeam.remove(currentTeam.size() - 1); 
        }
    }

    private void evaluateTeam(List<Person> team, Task task, double alpha, double beta) {
        double coverage = calculateCoverage(team, task);
        double maxWorkload = calculateMaxWorkload(team);
        System.err.println(" | Coverage: " + String.format("%.4f", coverage) + 
                           " | Max Workload: " + String.format("%.4f", maxWorkload));
        double score = (beta * coverage) - (alpha * maxWorkload);

        if (score > maxScore) {
            maxScore = score;
            bestTeam = new ArrayList<>(team);
        }
    }
public double calculateCoverage(List<Person> team, Task task) {
    double totalCoverage = 0;
    for (Map.Entry<Skills, Double> entry : task.getRequiredSkills().entrySet()) {
        Skills skill = entry.getKey();
        double required = entry.getValue(); // t.ex. 0.8
        
        double teamSum = 0;
        for (Person p : team) {
            teamSum += p.getSkills().getOrDefault(skill, 0.0);
        }
        
        double skillCoverage = Math.min(teamSum, required) / required; 
        totalCoverage += skillCoverage;
    }
    return totalCoverage / task.getRequiredSkills().size();
}

    private double calculateMaxWorkload(List<Person> team) {
        return team.stream()
                   .mapToDouble(Person::getWorkload)
                   .max()
                   .orElse(0.0);
    }

    public List<Person> getBestTeam() {
        return bestTeam;
    }
}
