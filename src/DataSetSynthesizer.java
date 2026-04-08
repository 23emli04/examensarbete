import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DataSetSynthesizer {
    private static final Random random = new Random();

    public static List<Person> generatePool(int size) {
        List<Person> pool = new ArrayList<>();
        
        for (int i = 0; i < size; i++) {
            Map<Skills, Double> skillMap = new EnumMap<>(Skills.class);
            
            for (Skills s : Skills.values()) {
                double skillLevel = Math.max(0, Math.min(1, 0.5 + random.nextGaussian() * 0.1));
                skillMap.put(s, skillLevel);
            }
            
            double workload = Math.max(0, Math.min(1, 0.5 + random.nextGaussian() * 0.2));
                        pool.add(new Person(i, skillMap, workload));
        }
        return pool;
    }
}