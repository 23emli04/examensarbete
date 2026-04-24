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
    public static List<Person> generateExponentialPool(int size, double lambda) {
        List<Person> pool = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Map<Skills, Double> skillMap = new EnumMap<>(Skills.class);

            for (Skills s : Skills.values()) {
                // Generera exponentialfördelat värde: -ln(1-u) / lambda
                double rawValue = -Math.log(1 - random.nextDouble()) / lambda;

                // Clampa värdet till max 1.0 då din modell kräver p_jf ∈ [0,1] [cite: 124]
                double skillLevel = Math.min(1.0, rawValue);
                skillMap.put(s, skillLevel);
            }

            // Generera arbetsbelastning (workload)
            // Kan också vara exponentialfördelad för att simulera ojämn belastning [cite: 134, 149]
            double rawWorkload = -Math.log(1 - random.nextDouble()) / lambda;
            double workload = Math.min(1.0, rawWorkload);

            pool.add(new Person(i, skillMap, workload));
        }
        return pool;
    }

}