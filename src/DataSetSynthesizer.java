import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataSetSynthesizer {
    private static final Random random = new Random();

    public static List<Candidate> generatePool(int size, int numSkills) {
        List<Candidate> pool = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            double[] skills = new double[numSkills];
            for (int s = 0; s < numSkills; s++) {
                skills[s] = Math.max(0, Math.min(1, 0.5 + random.nextGaussian() * 0.15));
            }
            double workload = Math.max(0, Math.min(1, 0.5 + random.nextGaussian() * 0.2));
            pool.add(new Candidate(i, skills, workload));
        }
        return pool;
    }
}