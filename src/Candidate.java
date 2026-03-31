public class Candidate {
    private int id;
    private double[] skills;
    private double workload;

    public Candidate(int id, double[] skills, double workload) {
        this.id = id;
        this.skills = skills;
        this.workload = workload;
    }

    public int getId() {
        return id;
    }


    public double[] getSkills() {
        return skills;
    }

    public double getWorkload() {
        return workload;
    }
    
}
