import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileReader;
import java.io.FileWriter;

public class SettingsPane extends JFrame {
    // Create the text format
    int mainScreen = 1;
    int secondScreen = 0;
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
            mainScreen = (int) (long) string.get("MainScreen");
            secondScreen = (int) (long) string.get("SecondScreen");
        } catch(Exception ex){
            ex.printStackTrace();
        }
        // Create new labels
        JLabel textFormatTxt = new JLabel("Text format");

        // Create new text field
        textFormat = new JTextField(textFormatter);
        JButton closeBtn = new JButton("Chiudi");
        JButton applyBtn = new JButton("Applica");
        JButton configScreens = new JButton("Configura Schermi");

        // Set UI bounds
        configScreens.setBounds(10, 60, 200, 30);
        textFormatTxt.setBounds(10, 10,100,10);
        textFormat.setBounds(10, 30, 400, 20);
        closeBtn.setBounds(395, 300, 80, 30);
        applyBtn.setBounds(315, 300, 80, 30);

        // Create a tooltip text
        textFormat.setToolTipText("%n: nome, %c: cognome, %v: vittorie, %s: sconfitte, %t: tag");

        // Add actions
        configScreens.addActionListener(this::configureScreens);
        closeBtn.addActionListener(e -> setVisible(false));
        applyBtn.addActionListener(this::applySettings);

        // Add elements to the frame
        add(configScreens);
        add(textFormatTxt);
        add(textFormat);
        add(closeBtn);
        add(applyBtn);
    }

    private void configureScreens(ActionEvent event) {

        // Get all the graphics devices
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        // Create the frames
        JFrame[] frames = {new JFrame(gs[0].getDefaultConfiguration()),
                new JFrame(gs[1].getDefaultConfiguration())};

        // Config and show the frames
        int j = 0;
        for(JFrame frame: frames){
            frame.setSize(400, 200);
            frame.setTitle("Monitor #" + j);
            frame.add(new JLabel("Questo è il monitor #" + j));
            frame.setVisible(true);
            j++;
        }

        // Make a selection to get which one will be the main monitor
        String[] options = {"0", "1"};
        int result = JOptionPane.showOptionDialog(
                null,
                "Qual è il monitor principale?",
                "Selezione monitor",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        // Get the selected option and create the frames
        if(result == JOptionPane.YES_OPTION){
            mainScreen = 0;
            secondScreen = 1;
        } else if(result==JOptionPane.NO_OPTION){
            mainScreen = 1;
            secondScreen = 0;
        }else{
            return;
        }
        // Hide the info frames
        for(JFrame frame: frames){
            frame.setVisible(false);
        }
    }

    @SuppressWarnings("unchecked")
    public void applySettings(ActionEvent event){
        // Create new json file
        JSONObject textFormatToJson = new JSONObject();
        // Put the text formatter into the json object
        textFormatToJson.put("TextFormatter", textFormat.getText());
        textFormatToJson.put("MainScreen", mainScreen);
        textFormatToJson.put("SecondScreen", secondScreen);

        // Write the json object into the file
        try(FileWriter file = new FileWriter("config.json")){
            file.write(textFormatToJson.toJSONString());
            file.flush();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
