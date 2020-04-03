import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is the Graphical user interface of the program
 */
public class GUI extends JFrame {
    private JFrame f;
    private DatabaseHandler databaseHandler = new DatabaseHandler();
    private JPanel inputPanel = new JPanel();
    private JPanel outputPanel = new JPanel();
    private JTextField nameInput;
    private JTextField emailInput;
    private JTextField homepageInput;
    private JTextField commentInput;
    private JButton addButton;

    private JScrollPane databaseInformation;

    /**
     * Constructor that builds the Graphical user interface and sets default values to the components in the GUI
     * @throws SQLException
     */
    GUI() throws SQLException {
        //Input
        inputPanel.setLayout(new GridLayout(5, 2));
        JLabel nameText = new JLabel("Name:");
        inputPanel.add(nameText);
        nameInput = new JTextField();
        inputPanel.add(nameInput);
        JLabel emailText = new JLabel("E-mail:");
        inputPanel.add(emailText);
        emailInput = new JTextField();
        inputPanel.add(emailInput);
        JLabel homepageText = new JLabel("Homepage:");
        inputPanel.add(homepageText);
        homepageInput = new JTextField();
        inputPanel.add(homepageInput);
        JLabel commentText = new JLabel("Comment:");
        inputPanel.add(commentText);
        commentInput = new JTextField();
        inputPanel.add(commentInput);
        JLabel addText = new JLabel("Add:");
        inputPanel.add(addText);
        addButton = new JButton("Add");
        inputPanel.add(addButton);
        /**
         * Add actionListener to Button to trigger method "addButtonClicked()"
         */
        addButton.addActionListener(e -> {
                addButtonClicked();
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
        setLayout(new GridLayout(2, 5));
        setVisible(true);
        databaseInformation.setViewportView(new TextArea("Loading database... Please wait"));
        updateContents();

    }

    /**
     * This method updates all the content of the program which is taken from the database
     * @throws SQLException
     */
    private void updateContents() throws SQLException {
        this.databaseHandler.connectToDatabase();
        ArrayList<HashMap<String, String>> result= this.databaseHandler.getAllFromGuestBook();

        updateScrollpane(result);


    }

    /**
     * This method updates the actual component with the material gotten from the database
     * @param result
     */
    private void updateScrollpane(ArrayList<HashMap<String, String>> result) {

        JTextArea displayStrings = new JTextArea(5, 30);

        for (HashMap map : result) {
            displayStrings.append("Name: " + map.get("name")+ "\n");
            displayStrings.append("Email: " + map.get("email")+ "\n");
            displayStrings.append("Homepage: " + map.get("homepage")+ "\n");
            displayStrings.append("Comment: " + map.get("comment") + "\n\n");
        }

        System.out.println(databaseInformation);
        databaseInformation.setViewportView(displayStrings);
        repaint();


    }

    /**
     * This method is called when clicking the addButton, it creates a new connection to the database and send a new entry to it.
     */
    private void addButtonClicked() {
        databaseInformation.setViewportView(new TextArea("Loading please wait"));
        repaint();

        try {
            this.databaseHandler.connectToDatabase();
            this.databaseHandler.createPost(checkHtml(nameInput.getText()), checkHtml(emailInput.getText()), checkHtml(homepageInput.getText()), checkHtml(commentInput.getText()));
            this.databaseHandler.getAllFromGuestBook();
            updateContents();
        } catch (SQLException e){
            System.out.println(e);
        }

    }

    /**
     * This method checks that the parameter is not a HTML element, if it is, the it is replace with the word "cencur"
     * @param s String to be checked if it contrains HTML elements.
     * @return
     */
    private String checkHtml(String s){
        Pattern p = Pattern.compile("<.*>");
        Matcher matcher = p.matcher(s);

        return matcher.replaceAll("cencur");
    }


}
