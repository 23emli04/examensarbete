
import java.util.Map;


public class Task {
    private int teamSize;
    private Map<Skills, Double> requiredSkills;
    public Task(int teamSize, Map<Skills, Double> requiredSkills) {
        this.teamSize = teamSize;
        this.requiredSkills = requiredSkills;
    }
    public int getTeamSize() {
        return teamSize;
    }
    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }
    public Map<Skills, Double> getRequiredSkills() {
        return requiredSkills;
    }
    public void setRequiredSkills(Map<Skills, Double> requiredSkills) {
        this.requiredSkills = requiredSkills;
    } 
    
}
