import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FilenameUtils;

public class Finestra extends TagGenerator{
    // Config connection values
    String link = "jdbc:mysql://localhost:3306/ladder?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    String name = "root";
    String pass = "Luca";
    // Create variables
    JFrame lead;
    JFrame manager;
    JList<Object> people;
    JButton addPerson;
    List<String> persone = new ArrayList<>();
    Font customFont;
    JTextField nameInput;
    JTextField lastNameInput;
    JTextField tagInput;
    JTextField cognomePrimoSfidante;
    JTextField nomePrimoSfidante;
    JTextField nomeSecondoSfidante;
    JTextField cognomeSecondoSfidante;
    JLabel challNames;
    public static Finestra f;
    static SettingsPane settings;
    int width;
    int height;
    static List<String> results = new ArrayList<>();

    public Finestra(int mainmonitor, int secondmonitor){
        // Get all the screens
        GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = graphics.getScreenDevices();

        // Get screen dimension to scale the font
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double widthFont = screenSize.getWidth();
        double heightFont = screenSize.getHeight();

        // Create a new font
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:/Users/39353/Downloads/VCR_OSD_MONO_1.001.ttf")).deriveFont((float)((widthFont+heightFont)/28));
            graphics.registerFont(customFont);
        } catch(Exception ex){
            ex.printStackTrace();
        }
        // Select first screen
        GraphicsDevice firstScreen = gs[mainmonitor];
        // Create a new frame using the first screen configurations
        manager = new JFrame(firstScreen.getDefaultConfiguration());

        // Create all the UI things
        nameInput = new JTextField();
        lastNameInput = new JTextField();
        tagInput = new JTextField();
        addPerson = new JButton("Aggiungi");
        nomePrimoSfidante = new JTextField();
        cognomePrimoSfidante = new JTextField();
        cognomeSecondoSfidante = new JTextField();
        nomeSecondoSfidante = new JTextField();

        JLabel o = new JLabel("o");
        JLabel nameTxt = new JLabel("Nome");
        JLabel lastNameTxt = new JLabel("Cognome");
        JLabel tagTxt = new JLabel("Tag");
        JButton match = new JButton("Sfida");

        JButton settingsBtn = new JButton("Opzioni");
        JButton screenShotBtn = new JButton("Screenshot");
        JButton textBtn = new JButton("Testo");

        JButton aggiungiVittoria = new JButton("Add Vinci");
        JButton aggiungiPerdita = new JButton("Add Perdi");
        JButton rimuoviVittoria = new JButton("Rem Vinci");
        JButton rimuoviPerdita = new JButton("Rem Perdi");

        JLabel nomePrimoSfidanteTxt = new JLabel("Nome");
        JLabel cognomePrimoSfidanteTxt = new JLabel("Cognome");
        JLabel secondoSfidanteNomeTxt = new JLabel("Nome");
        JLabel secondoSfidanteCognomeTxt = new JLabel("Cognome");

        // Set UI bounds
        settingsBtn.setBounds(10, 300, 100, 30);
        screenShotBtn.setBounds(370, 300, 100, 30);
        textBtn.setBounds(260, 300, 100, 30);
        nameInput.setBounds(10, 30, 120, 20);
        lastNameInput.setBounds(150, 30, 120, 20);
        tagInput.setBounds(300, 30, 120, 20);
        o.setBounds(280, 35, 10, 10);
        addPerson.setBounds(10, 60, 100 ,30);
        tagTxt.setBounds(300, 10, 100, 10);
        nameTxt.setBounds(10, 10, 100, 10);
        lastNameTxt.setBounds(150, 10, 100, 10);
        match.setBounds(120, 60, 100, 30);
        aggiungiVittoria.setBounds(10, 190, 100, 30);
        aggiungiPerdita.setBounds(120, 190, 100, 30);
        rimuoviVittoria.setBounds(10, 220, 100, 30);
        rimuoviPerdita.setBounds(120, 220, 100, 30);
        nomePrimoSfidanteTxt.setBounds(10, 100, 120, 20);
        cognomePrimoSfidante.setBounds(10, 120, 120, 20);
        cognomePrimoSfidanteTxt.setBounds(150, 100, 120, 10);
        nomePrimoSfidante.setBounds(150, 120, 120, 20);
        secondoSfidanteNomeTxt.setBounds(10, 140, 120, 20);
        nomeSecondoSfidante.setBounds(10, 160, 120, 20);
        secondoSfidanteCognomeTxt.setBounds(150, 140, 120, 20);
        cognomeSecondoSfidante.setBounds(150, 160, 120, 20);
        // Create action listener for buttons
        screenShotBtn.addActionListener(this::screenshotFrame);
        textBtn.addActionListener(this::textList);
        addPerson.addActionListener(this::clickNewPerson);
        match.addActionListener(this::startMatch);
        aggiungiVittoria.addActionListener(this::clickAddWin);
        aggiungiPerdita.addActionListener(this::clickAddLoss);
        rimuoviVittoria.addActionListener(this::clickRemWin);
        rimuoviPerdita.addActionListener(this::clickRemLoss);
        settingsBtn.addActionListener(this::openSettings);

        // Add everything on the manager frame
        manager.add(settingsBtn);
        manager.add(textBtn);
        manager.add(tagTxt);
        manager.add(tagInput);
        manager.add(o);
        manager.add(aggiungiVittoria);
        manager.add(aggiungiPerdita);
        manager.add(rimuoviVittoria);
        manager.add(rimuoviPerdita);
        manager.add(cognomePrimoSfidanteTxt);
        manager.add(nomePrimoSfidanteTxt);
        manager.add(secondoSfidanteNomeTxt);
        manager.add(secondoSfidanteCognomeTxt);
        manager.add(nomePrimoSfidante);
        manager.add(cognomePrimoSfidante);
        manager.add(cognomeSecondoSfidante);
        manager.add(nomeSecondoSfidante);
        manager.add(match);
        manager.add(addPerson);
        manager.add(nameInput);
        manager.add(lastNameInput);
        manager.add(nameTxt);
        manager.add(lastNameTxt);
        manager.add(screenShotBtn);

        // Set frame size and configs
        manager.setSize(500, 400);
        manager.setLayout(null);
        manager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        manager.setVisible(true);

        // Get second screen
        GraphicsDevice secondScreen = gs[secondmonitor];
        // Get second screen's width and height
        width = secondScreen.getDisplayMode().getWidth();
        height = secondScreen.getDisplayMode().getHeight();
        // Create a frame using screen configuration
        lead = new JFrame(secondScreen.getDefaultConfiguration());
        // Make the frame fullscreen
        lead.setResizable(false);
        lead.setUndecorated(true);
        secondScreen.setFullScreenWindow(lead);
        // Set the background black
        lead.getContentPane().setBackground(Color.BLACK);

        // Create the UI
        challNames = new JLabel();
        people = new JList<>();

        // Clear the selection whenever clicked
        people.addListSelectionListener(e -> people.clearSelection());

        // Set font and make it invisible
        challNames.setFont(customFont);
        challNames.setVisible(false);
        // Set background black and change font
        people.setBackground(new Color(0, 0, 0));
        people.setFont(customFont);

        // Add UI to the frame
        lead.add(challNames);
        lead.add(people);
        // Get update the frame infos
        UpdateList();
    }

    private void openSettings(ActionEvent event) {
        // Open settings tab
        settings.setVisible(true);
    }

    private void textList(ActionEvent event) {
        // Create a new custom pane
        CustomPane pane = new CustomPane();
        // Try to connect to database
        try(Connection conn = DriverManager.getConnection(link, name, pass);
            Statement stmt = conn.createStatement()){
            String strSelect = "SELECT * FROM persone";
            ResultSet rset = stmt.executeQuery(strSelect);

            // Get all the infos and format it into an ArrayList
            while(rset.next()){
                String name = rset.getString("Name");
                String lastname = rset.getString("LastName");
                String tag = rset.getString("Tag");
                int wins = rset.getInt("Wins");
                int loss = rset.getInt("Loss");
                results.add(settings.textFormat.getText().replace("%n", name).replace("%c", lastname).replace("%t", tag).replace("%v", String.valueOf(wins)).replace("%s", String.valueOf(loss)));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        // Create the text
        StringBuilder text = new StringBuilder();
        for(String result: results){
            text.append(result).append("\n");
        }
        pane.textField.setText(text.toString());
    }

    public static void saveText(ActionEvent event){
        // Create  a new file chooser dialog
        JFileChooser fileChooser = new JFileChooser();
        // Set the extensions filters
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Text files", "txt");
        fileChooser.addChoosableFileFilter(txtFilter);
        // Save the file
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try{
                // Create new file if needed
                if(file.createNewFile()){
                    System.out.println("File creato1");
                }
                // Create the writer
                FileWriter writer = new FileWriter(file);
                // Create the string
                StringBuilder text = new StringBuilder();
                for(String result: results){
                    text.append(result).append("\n");
                }
                // Write the text into the file
                writer.write(text.toString());
                writer.close();
            } catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private void screenshotFrame(ActionEvent event) {
        // Create  a new file chooser dialog
        JFileChooser fileChooser = new JFileChooser();
        // Set the extensions filters
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter jpgFilter = new FileNameExtensionFilter("JPG Images", "jpg");
        FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("PNG Images", "png");
        fileChooser.addChoosableFileFilter(jpgFilter);
        fileChooser.addChoosableFileFilter(pngFilter);
        // Save the file
        if (fileChooser.showSaveDialog(manager) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try{
                // Get a screenshot of the frame
                ImageIO.write(getScreenshot(lead), FilenameUtils.getExtension(file.getName()), file);
            } catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public static BufferedImage getScreenshot(Component component){
        // Create a new bufferedimage
        BufferedImage image = new BufferedImage(
                component.getWidth(),
                component.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );
        // Draw the frame on the bufferedimage
        component.paint(image.createGraphics());
        return image;
    }

    private void clickAddWin(ActionEvent event) {
        String tag;
        // Find out if the code has to get the TAG from Name and LastName text field or from the tag text field
        if(tagInput.getText().equals("")){
            tag = generateTag(nameInput.getText(), lastNameInput.getText());
        } else
            tag = tagInput.getText().toUpperCase();
        addWin(tag);
    }

    private void clickRemWin(ActionEvent event) {
        String tag;
        // Find out if the code has to get the TAG from Name and LastName text field or from the tag text field
        if(tagInput.getText().equals("")){
            tag = generateTag(nameInput.getText(), lastNameInput.getText());
        } else
            tag = tagInput.getText().toUpperCase();
        remWin(tag);
    }
    private void clickAddLoss(ActionEvent event) {
        String tag;
        // Find out if the code has to get the TAG from Name and LastName text field or from the tag text field
        if(tagInput.getText().equals("")){
            tag = generateTag(nameInput.getText(), lastNameInput.getText());
        } else
            tag = tagInput.getText().toUpperCase();
        addLoss(tag);
    }
    private void clickRemLoss(ActionEvent event) {
        String tag;
        // Find out if the code has to get the TAG from Name and LastName text field or from the tag text field
        if(tagInput.getText().equals("")){
            tag = generateTag(nameInput.getText(), lastNameInput.getText());
        } else
            tag = tagInput.getText().toUpperCase();
        remLoss(tag);
    }

    public void addWin(String tag){
        // Initialize wins variable
        int wins = 0;
        // Try to connect to the SQL Database
        try(Connection conn = DriverManager.getConnection(link, name, pass);
            Statement stmt = conn.createStatement()){
            // Initialize string to get wins from the database
            String strSelect = "select Wins from persone where Tag='" + tag+"'";
            // Execute the string
            ResultSet rset = stmt.executeQuery(strSelect);
            while(rset.next()){
                // Get wins and add one
                wins = rset.getInt("Wins");
                wins++;
            }
            // Send the changed result to the database
            String strAdd = "update persone set Wins="+wins+" where Tag='"+tag+"'";
            stmt.executeUpdate(strAdd);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        UpdateList();
    }

    public void addLoss(String tag){
        // Try to connect to the SQL Database
        try(Connection conn = DriverManager.getConnection(link, name, pass);
            Statement stmt = conn.createStatement()){
            // Initialize string to get loss from the database
            String strSelect = "select Loss from persone where Tag='" + tag+"'";
            ResultSet rset = stmt.executeQuery(strSelect);
            // Initialize loss variable
            int loss = 0;
            while(rset.next()){
                // Get loss and add one
                loss = rset.getInt("Loss");
                loss++;
            }
            // Send the changed result to the database
            String strAdd = "update persone set Loss="+loss+" where Tag='"+tag+"'";
            stmt.executeUpdate(strAdd);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        UpdateList();
    }

    public void remLoss(String tag){
        // Try to connect to the SQL Database
        try(Connection conn = DriverManager.getConnection(link, name, pass);
            Statement stmt = conn.createStatement()){
            // Initialize string to get loss from the database
            String strSelect = "select Loss from persone where Tag='" + tag+"'";
            ResultSet rset = stmt.executeQuery(strSelect);
            // Initialize loss variable
            int loss = 0;
            while(rset.next()){
                // Get loss and remove one
                loss = rset.getInt("Loss");
                // If the player has 0 loss then change nothing
                if(loss==0){
                    return;
                }
                loss--;
            }
            // Send the changed result to the database
            String strAdd = "update persone set Loss="+loss+" where Tag='"+tag+"'";
            stmt.executeUpdate(strAdd);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        UpdateList();
    }

    public void remWin(String tag){
        // Try to connect to the SQL Database
        try(Connection conn = DriverManager.getConnection(link, name, pass);
            Statement stmt = conn.createStatement()){
            // Initialize string to get wins from the database
            String strSelect = "select Wins from persone where Tag='" + tag+"'";
            // Execute the string
            ResultSet rset = stmt.executeQuery(strSelect);
            // Initialize wins variable
            int wins = 0;
            while(rset.next()){
                // Get wins and remove one
                wins = rset.getInt("Wins");
                // If the player has 0 wins then change nothing
                if(wins==0){
                    return;
                }
                wins--;
            }
            // Send the changed result to the database
            String strAdd = "update persone set Wins="+wins+" where Tag='"+tag+"'";
            stmt.executeUpdate(strAdd);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        UpdateList();
    }

    private void clickNewPerson(ActionEvent event){
        AddPerson(nameInput.getText(), lastNameInput.getText());
    }

    public void AddPerson(String username, String lastname) {
        // If the name and lastname text field are empty, do nothing
        if(username.equals("") || lastname.equals("")){
            JOptionPane.showMessageDialog(null, "Ti sei dimenticato di scrivere nome e/o cognome", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Connect to the database
        try(Connection conn = DriverManager.getConnection(link, name, pass);
        Statement stmt = conn.createStatement()){
            // Get the name and lastname of the person using the tag
            String strSelect = "SELECT Name, LastName from persone where Tag='" + generateTag(username, lastname) + "'";
            ResultSet rset = stmt.executeQuery(strSelect);
            while(rset.next()){
                // If the person already exist in the database then do nothing
                if(rset.getString("Name").equalsIgnoreCase(username) || rset.getString("LastName").equalsIgnoreCase(lastname)){
                    return;
                }
            }
            // Initialize string to create person
            String strCreate = "INSERT INTO persone VALUES ('" + username + "', '" + lastname + "', 0, 0, '" + generateTag(username, lastname)+ "', 0)";
            // Update the database
            stmt.executeUpdate(strCreate);
            UpdateList();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args){
        // Create settings frame
        settings = new SettingsPane();
        // Create a new frame
        f = new Finestra(settings.mainScreen, settings.secondScreen);

        // Create new json file if needed
        File config = new File("config.json");
        try{
            if(config.createNewFile()){
                System.out.println("File creato");
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void UpdateList(){
        // Connect to the database
        try (Connection conn = DriverManager.getConnection(link, name, pass);
        Statement stmt = conn.createStatement()){
            // Set Ratio variable in the database to Wins-Loss
            String strUpdate = "update persone set Ratio=Wins-Loss";
            // Execute the update
            stmt.executeUpdate(strUpdate);
            // Get all the elements in the database sorted by the Ratio variable in decreasing order
            String strSelect = "SELECT * FROM persone ORDER BY Ratio DESC";
            ResultSet rset = stmt.executeQuery(strSelect);
            // Remove all the people from the "Persone" list
            persone.clear();
            // Initialize i variable to 1
            int i = 1;
            while(rset.next()){
                // Get tag, wins and loss
                String name = rset.getString("Tag");
                int wins = rset.getInt("Wins");
                int loss = rset.getInt("Loss");
                // Add i, name, wins, loss in the "persone" list
                persone.add(settings.personFormat.getText().replace("%pos", String.valueOf(i)).replace("%tag", name).replace("%wins", String.valueOf(wins)).replace("%loss", String.valueOf(loss)).replace("%c1", settings.c1).replace("%c2", settings.c2).replace("%c3", settings.c3).replace("%c4", settings.c4));
                // Increase i
                i++;
            }
            // Put the "persone" list converted to array in the JList
            people.setListData(persone.toArray());
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void startMatch(ActionEvent event){
        // Check if one of the field is empty, then do nothing
        if(nomePrimoSfidante.getText().equals("") || cognomePrimoSfidante.getText().equals("") || cognomeSecondoSfidante.getText().equals("") || nomeSecondoSfidante.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ti sei dimenticato di scrivere nome e/o cognome", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Initialize wins and losses variables
        int firstWins = 0;
        int firstLoss = 0;
        int secondWins = 0;
        int secondLoss = 0;
        // Add people to the database
        AddPerson(cognomePrimoSfidante.getText(), nomePrimoSfidante.getText());
        AddPerson(nomeSecondoSfidante.getText(), cognomeSecondoSfidante.getText());

        // Generate the people's tags
        String firstTag = generateTag(cognomePrimoSfidante.getText(), nomePrimoSfidante.getText());
        String secondTag = generateTag(nomeSecondoSfidante.getText(), cognomeSecondoSfidante.getText());
        // Connect to the database
        try(
                Connection conn = DriverManager.getConnection(link, name, pass);
                Statement stmt = conn.createStatement()
                ){
            // Get the amount of wins and loss the two players have
            String strGetFirst = "select Wins, Loss from persone where Tag='" + firstTag + "'";
            String strGetSecond = "select Wins, Loss from persone where Tag='" + secondTag + "'";
            ResultSet firstSet = stmt.executeQuery(strGetFirst);
            while(firstSet.next()){
                firstWins = firstSet.getInt("Wins");
                firstLoss = firstSet.getInt("Loss");
            }
            ResultSet secondSet = stmt.executeQuery(strGetSecond);
            while(secondSet.next()){
                secondWins = secondSet.getInt("Wins");
                secondLoss = secondSet.getInt("Loss");
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

        // Hide the JList
        people.setVisible(false);
        // Set the JLabel configs
        challNames.setHorizontalAlignment(JLabel.CENTER);
        challNames.setVerticalAlignment(0);
        challNames.setBounds(600, 200, 200, 200);
        challNames.setBounds(0, 0, width, height);
        // Set the text
        challNames.setText("<html><font color='#0000cc'>"+ cognomePrimoSfidante.getText() + " " + nomePrimoSfidante.getText() + "</font><font color='#ffffff'>              VS.              </font><font color='#ff0066'>"+ nomeSecondoSfidante.getText() + " " + cognomeSecondoSfidante.getText());
        // Make the text visible
        challNames.setVisible(true);

        // Create an options array
        String[] options = {firstTag, secondTag};
        // Create a dialog to get which one wins
        int result = JOptionPane.showOptionDialog(
                null,
                "Chi ha vinto?",
                "Vincitore",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        // Change wins and losses variables of the two contestants
        if(result == JOptionPane.YES_OPTION){
            firstWins++;
            secondLoss++;
        } else if(result==JOptionPane.NO_OPTION){
            secondWins++;
            firstLoss++;
        }
        // Change the amount of wins and losses the contestants have in the database
        String strUpdateFirst = "update persone set Wins=" + firstWins + ", Loss=" + firstLoss + " where Tag='"+firstTag+"'";
        String strUpdateSecond = "update persone set Wins=" + secondWins + ", Loss=" + secondLoss + " where Tag='"+secondTag+"'";

        try(Connection conn = DriverManager.getConnection(link, name, pass);
        Statement stmt = conn.createStatement()){
            stmt.executeUpdate(strUpdateFirst);
            stmt.executeUpdate(strUpdateSecond);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        // Reset the text fields
        nomePrimoSfidante.setText("");
        cognomePrimoSfidante.setText("");
        nomeSecondoSfidante.setText("");
        cognomeSecondoSfidante.setText("");
        // Return to the normal list mode
        challNames.setVisible(false);
        people.setVisible(true);
        UpdateList();
    }
}