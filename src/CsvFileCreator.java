import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CsvFileCreator extends FileCreator {
    private final Locale swedishLocale = new Locale("sv", "SE");

    public CsvFileCreator(String filename) {
        super(filename + ".csv");
    }

    @Override
    public void writeToFile(List<Person> pool) throws IOException {
        if (pool == null || pool.isEmpty())
            return;

        try (FileWriter writer = new FileWriter(getFilename())) {
            StringBuilder header = new StringBuilder("ID;Workload");
            for (Skills skill : Skills.values()) {
                header.append(";").append(skill.name());
            }
            writer.write(header.append("\n").toString());
            for (Person p : pool) {
                StringBuilder sb = new StringBuilder();

                sb.append(p.getId()).append(";")
                  .append(String.format(swedishLocale, "%.4f", p.getWorkload()));
                  
                Map<Skills, Double> personSkills = p.getSkills();
                for (Skills s : Skills.values()) {
                    double level = personSkills.getOrDefault(s, 0.0);
                    sb.append(";").append(String.format(swedishLocale, "%.4f", level));
                }

                sb.append("\n");
                writer.write(sb.toString());
            }
        }
    }
}