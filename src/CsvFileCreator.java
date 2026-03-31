import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CsvFileCreator extends FileCreator {
    private final Locale swedishLocale = new Locale("sv", "SE");
    public CsvFileCreator(String filename) {
        super(filename + ".csv");
    }

   @Override
public void writeToFile(List<Candidate> pool) throws IOException {
    if (pool == null || pool.isEmpty()) return;

    try (FileWriter writer = new FileWriter(getFilename())) {

        StringBuilder header = new StringBuilder("ID; Workload");
        int numSkills = pool.get(0).getSkills().length;
        for (int i = 1; i <= numSkills; i++) {
            header.append(";Skill_").append(i);
        }
        writer.write(header.append("\n").toString());

        for (Candidate c : pool) {
            StringBuilder sb = new StringBuilder();

            sb.append(c.getId()).append(";")
              .append(String.format(swedishLocale, "%.4f", c.getWorkload())).append(";");

            double[] skills = c.getSkills();
            for (int i = 0; i < skills.length; i++) {
                sb.append(String.format(swedishLocale, "%.4f", skills[i]));
                if (i < skills.length - 1) sb.append(";");
            }

            sb.append("\n");
            writer.write(sb.toString());
        }
    }
}
}