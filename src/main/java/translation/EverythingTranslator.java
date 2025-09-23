package translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EverythingTranslator implements Translator {

    private List<String> langCodes = new ArrayList<>();
    private List<String> countCodes = new ArrayList<>();

    public EverythingTranslator() {
        this("country-codes.txt", "language-codes.txt");
    }

    /**
     * Overloaded constructor that allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resources file can't be loaded properly
     */
    public EverythingTranslator(String filename1, String filename2) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename1).toURI()));

            Iterator<String> iterator = lines.iterator();
            iterator.next(); // skip the first line
            while (iterator.hasNext()) {
                String line = iterator.next();
                String[] parts = line.split("\t");

                countCodes.add(parts[2].trim().toLowerCase());
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename2).toURI()));

            Iterator<String> iterator = lines.iterator();
            iterator.next(); // skip the first line
            while (iterator.hasNext()) {
                String line = iterator.next();
                String[] parts = line.split("\t");

                langCodes.add(parts[1].trim().toLowerCase());
            }

        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    public List<String> getLanguageCodes() {return langCodes;}

    @Override
    public List<String> getCountryCodes() {return countCodes;}

    @Override
    public String translate(String countryCode, String languageCode) {return "Not Implemented";}
}
