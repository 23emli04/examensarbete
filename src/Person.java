
import java.util.Map;

public class Person {
    private int id;
    private Map<Skills, Double> skills;
    private double workload;

    public Person(int id, Map<Skills, Double> skills, double workload) {
        this.id = id;
        this.skills = skills;
        this.workload = workload;
    }

    public int getId() {
        return id;
    }
    public Map<Skills, Double> getSkills() {
        return skills;
    }
 

    public double getWorkload() {
        return workload;
    }
        @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Person ID: ").append(id).append(", Workload: ").append(String.format("%.4f", workload)).append(", Skills: {");
        for (Map.Entry<Skills, Double> entry : skills.entrySet()) {
            sb.append(entry.getKey().name()).append(": ").append(String.format("%.4f", entry.getValue())).append(", ");
        }
        sb.append("}");
        return sb.toString();
    }
}
