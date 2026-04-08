import java.util.List;

public class ScoringFunction {

    public double competenceSum(List<Person> team, Task task) {
        double total = 0.0;
        int numSkills = task.getRequiredSkills().size();
        
        for (Skills s : task.getRequiredSkills().keySet()) {
            double teamSkillContribution = 0.0;
            double requiredLevel = task.getRequiredSkills().get(s);

            for (Person p : team) {
                double personLevel = p.getSkills().getOrDefault(s, 0.0);
                teamSkillContribution += Math.min(personLevel / requiredLevel, 1.0);
            }
            total += (teamSkillContribution / team.size());
        }
        return total / numSkills;
    }

    public double workloadMax(List<Person> team) {
        double max = 0.0;
        for (Person p : team) {
            max = Math.max(max, p.getWorkload());
        }
        return max;
    }

}
