package translation;

import javax.swing.*;
import java.awt.event.*;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Dennis
            LanguageCodeConverter langCon = new LanguageCodeConverter();
            CountryCodeConverter countCon = new CountryCodeConverter();
            EverythingTranslator trans = new EverythingTranslator();

            JComboBox<String> languageComboBox = new JComboBox<>();
            for (String lang : trans.getLanguageCodes()) {
                languageComboBox.addItem(langCon.fromLanguageCode(lang));
            }

            JList<String> countryList = new JList<>();
            DefaultListModel<String> countryListModel = new DefaultListModel<>();
            for (String lang : trans.getCountryCodes()) {
                countryListModel.addElement(countCon.fromCountryCode(lang));
            }
            countryList.setModel(countryListModel);
            // Dennis/

            JPanel countryPanel = new JPanel();
            JTextField countryField = new JTextField(10);
            countryField.setText("can");
            countryField.setEditable(false); // we only support the "can" country code for now
            countryPanel.add(new JLabel("Country:"));
            //countryPanel.add(countryField);

            JScrollPane countryScrollPane = new JScrollPane(countryList);
            countryPanel.add(countryScrollPane);

            JPanel languagePanel = new JPanel();
            JTextField languageField = new JTextField(10);
            languagePanel.add(new JLabel("Language:"));
            //languagePanel.add(languageField);
            languagePanel.add(languageComboBox);

            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);


            // adding listener for when the user clicks the submit button
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //String language = languageField.getText();
                    //String country = countryField.getText();
                    String language = langCon.fromLanguage(languageComboBox.getSelectedItem().toString());
                    String country = "";
                    if (countryList.isSelectionEmpty()) {
                        country = "No country selected";
                    } else {
                        country = countCon.fromCountry(countryList.getSelectedValue().toString());
                    }

                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later.
                    Translator translator = new JSONTranslator();

                    String result = translator.translate(country, language);
                    if (result == null) {
                        result = "no translation found!";
                    }
                    //resultLabel.setText(result);
                    resultLabelText.setText(country + " in " + language + ": " + result);

                }

            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(countryPanel);
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
