import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.FileReader;
import java.io.FileWriter;

public class SettingsPane extends JFrame {
    // Create the text format
    String textFormatter = "%n %c vittorie = %v sconfitte = %s tag = %t";
    JTextField textFormat;
    public SettingsPane(){
        // Config the frame
        setSize(500, 400);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLayout(null);

        // Get text formatter from json config file
        JSONParser jsonParser = new JSONParser();
        try(FileReader reader = new FileReader("config.json")){
            Object obj = jsonParser.parse(reader);
            JSONObject string = (JSONObject) obj;
            textFormatter = (String) string.get("TextFormatter");
        } catch(Exception ex){
            ex.printStackTrace();
        }

        // Create new textfield
        textFormat = new JTextField(textFormatter);
        JButton closeBtn = new JButton("Chiudi");
        JButton applyBtn = new JButton("Applica");

        // Set UI bounds
        textFormat.setBounds(10, 10, 400, 20);
        closeBtn.setBounds(395, 300, 80, 30);

        // Create a tooltip text
        textFormat.setToolTipText("%n: nome, %c: cognome, %v: vittorie, %s: sconfitte, %t: tag");

        // Add actions
        closeBtn.addActionListener(e -> setVisible(false));
        applyBtn.addActionListener(this::applySettings);

        // Add elements to the frame
        add(textFormat);
        add(closeBtn);
        add(applyBtn);
    }

    @SuppressWarnings("unchecked")
    public void applySettings(ActionEvent event){
        // Create new json file
        JSONObject textFormatToJson = new JSONObject();
        // Put the text formatter into the json object
        textFormatToJson.put("TextFormatter", textFormat.getText());

        // Write the json object into the file
        try(FileWriter file = new FileWriter("config.json")){
            file.write(textFormatToJson.toJSONString());
            file.flush();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
