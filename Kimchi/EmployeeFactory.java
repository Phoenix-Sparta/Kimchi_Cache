package Kimchi;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmployeeFactory {

    public static String[] getEmployees(int numEmployees) throws IllegalArgumentException {
        if (numEmployees < 1 || numEmployees > 1000) {
            throw new IllegalArgumentException("Argument 'numEmployees' must be between 1 and 1000");
        }

        ArrayList<String> result = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/employees.csv"))) {
            bufferedReader.readLine();

            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                result.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        Collections.shuffle(result);
        return result.subList(0, numEmployees).toArray(new String[0]);
    }
}