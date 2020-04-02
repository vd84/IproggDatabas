import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class GUI extends JFrame {
    private DatabaseHandler databaseHandler = new DatabaseHandler();
    private JPanel inputPanel = new JPanel();
    private JPanel outputPanel = new JPanel();
    private JFrame f;
    private JTextArea nameInput;
    private JTextArea emailInput;
    private JTextArea homepageInput;
    private JTextArea commentInput;
    private JButton addButton;

    private JScrollPane databaseInformation;

    GUI() throws SQLException {
        //Input
        inputPanel.setLayout(new GridLayout(5, 2));
        JLabel nameText = new JLabel("Name:");
        inputPanel.add(nameText);
        nameInput = new JTextArea();
        inputPanel.add(nameInput);
        JLabel emailText = new JLabel("E-mail:");
        inputPanel.add(emailText);
        emailInput = new JTextArea();
        inputPanel.add(emailInput);
        JLabel homepageText = new JLabel("Homepage:");
        inputPanel.add(homepageText);
        homepageInput = new JTextArea();
        inputPanel.add(homepageInput);
        JLabel commentText = new JLabel("Comment:");
        inputPanel.add(commentText);
        commentInput = new JTextArea();
        inputPanel.add(commentInput);
        JLabel addText = new JLabel("Add:");
        inputPanel.add(addText);
        addButton = new JButton("Add");
        inputPanel.add(addButton);
        addButton.addActionListener(e -> {
            try {
                addButtonClicked();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        //Output
        outputPanel.setLayout(new GridLayout());
        databaseInformation = new JScrollPane();
        databaseInformation.setSize(1000, 300);
        outputPanel.setSize(1000, 300);
        outputPanel.add(databaseInformation);
        add(inputPanel);
        add(outputPanel);
        setSize(1000, 500);
        setLayout(new GridLayout(5, 2));
        setVisible(true);
        updateContents();

    }

    private void updateContents() throws SQLException {
        this.databaseHandler.connectToDatabase();
        String result = this.databaseHandler.getAllFromGuestBook();
        System.out.println("Resultat " + result);
        String[] users = result.split(" ");
        updateScrollpane(users);


    }

    private void updateScrollpane(String[] users) {
        JTextArea displayStrings = new JTextArea(5, 30);
        for (String user : users) {
            displayStrings.append("\n");
            String[] propertiesOfUser = user.split(",");
            for(String property : propertiesOfUser){
                displayStrings.append(property + " ");
            }
        }
        System.out.println(databaseInformation);
        databaseInformation.setViewportView(displayStrings);
        repaint();


    }

    private void addButtonClicked() throws SQLException {

        this.databaseHandler.connectToDatabase();
        this.databaseHandler.createPost(nameInput.getText(), emailInput.getText(), homepageInput.getText(), commentInput.getText());
        this.databaseHandler.getAllFromGuestBook();
        updateContents();

    }

    public JPanel getInputPanel() {
        return inputPanel;
    }

    public JPanel getOutputPanel() {
        return outputPanel;
    }

    public JFrame getF() {
        return f;
    }
}
