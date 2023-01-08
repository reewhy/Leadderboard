import javax.swing.*;

public class CustomPane extends JFrame {
    JTextArea textField;
    JScrollPane scroll;
    JPanel panel;
    JButton textButton;
    JButton closeBtn;

    public CustomPane(){
        setSize(500, 400);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("List to text");
        setResizable(false);
        setLayout(null);

        textField = new JTextArea();
        textField.setBounds(5, 5, 475, 280);
        scroll = new JScrollPane(textField);
        scroll.setBounds(5, 5, 475, 280);
        add(scroll);
        textButton = new JButton("Salva");
        textButton.setBounds(395, 300, 80, 30);
        textButton.addActionListener(Finestra::saveText);
        closeBtn = new JButton("Chiudi");
        closeBtn.setBounds(310, 300, 80, 30);
        closeBtn.addActionListener(e -> dispose());
        add(closeBtn);
        add(textButton);

        setVisible(true);


    }
}
