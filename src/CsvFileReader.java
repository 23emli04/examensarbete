
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class CsvFileReader extends FileReader {

    public CsvFileReader(String file) throws FileNotFoundException {
        super(file);
   }
   public List<Person> getPoolOfPersons() {
        List<Person> pool = new ArrayList<>();
        try (Scanner scanner = new Scanner(this)) {
                            Map<Integer, Skills> skillIndexMap = new HashMap<>();
            if (scanner.hasNextLine()) {
                String headerLine = scanner.nextLine();
                String[] headers = headerLine.split(";");
                for (int i = 2; i < headers.length; i++) {
                    skillIndexMap.put(i, Skills.valueOf(headers[i]));
                }
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");
                int id = Integer.parseInt(parts[0]);
                double workload = Double.parseDouble(parts[1].replace(",", "."));
                Map<Skills, Double> skills = new EnumMap<>(Skills.class);
                for (int i = 2; i < parts.length; i++) {
                    Skills skill = skillIndexMap.get(i);
                    double level = Double.parseDouble(parts[i].replace(",", "."));
                    skills.put(skill, level);
                }
                pool.add(new Person(id, skills, workload));
            }
        } catch (Exception e) {
            System.err.println("Fel vid läsning av CSV: " + e.getMessage());
        }
        return pool;
   }

}
