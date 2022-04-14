package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import data.*;

/**
 * authors: jake love, nathan tabb ist 311
 **/
public class GUI implements ActionListener {

    // objects used in all GUIs
    public JFrame frame = new JFrame();
    private CardLayout cl = new CardLayout(0, 0);// objects used in MenuGUI
    public JPanel mainMasterPanel = new JPanel();
    public JLabel libraryName = new JLabel("Stonybrook Library");
    public Color menuBlue = new Color(173, 216, 230);

    // objects used in LoginGUI
    public JPanel loginMasterPanel = new JPanel();
    public JPanel loginSecondaryPanel = new JPanel();
    public JLabel loginUsernameLabel = new JLabel("Username");
    public JLabel loginPasswordLabel = new JLabel("Password");
    public JTextField loginUsernameField = new JTextField();
    public JTextField loginPasswordField = new JTextField();
    public JButton loginSubmit = new JButton("SUBMIT");
    public JButton loginGuestLogin = new JButton("CONTINUE AS GUEST");
    public JButton loginQuit = new JButton("QUIT");

    // objects used in SplashGUI
    public JPanel splashMasterPanel = new JPanel();
    public JPanel splashSecondaryPanel = new JPanel();
    public JButton splashMedia = new JButton("MEDIA");
    public JButton splashEmployees = new JButton("EMPLOYEES");
    public JButton splashDatabases = new JButton("DATABASES");
    public JButton splashPatrons = new JButton("PATRONS");
    public JButton splashVendors = new JButton("VENDORS");

    public ArrayList<JButton> splashButtons = new ArrayList<>();
    //private ArrayList<JButton> empButtons = new ArrayList<>();
    private ArrayList<ArrayList<JButton>> empButtons = new ArrayList<>();

    WriteData write;
    ReadFile read;

    // objects used in SearchGUI
    public JPanel searchMasterPanel = new JPanel();
    public JComboBox search1Box = new JComboBox();
    public JComboBox search2Box = new JComboBox();
    private ArrayList<String> filterDepartment = new ArrayList<>();
    private ArrayList<String> filterSubject = new ArrayList<>();
    private ArrayList<String> filterProject = new ArrayList<>();

    // objects used in DataGUI
    public JPanel mediaMasterPanel = new JPanel();
    public JPanel mediaSuperMasterPanel = new JPanel();
    public JPanel employeeMasterPanel = new JPanel();
    public JPanel employeeSuperMasterPanel = new JPanel();

    // gui attributes
    private int currentUser = 0;
    private int currentRank = 0;
    private ArrayList<String> empAtt = new ArrayList<>();

    public GUI(WriteData write, ReadFile read){
        this.write = write;
        this.read = read;
        buildGUI();
    }

    /**
     * Sets settings for the frame and builds the menu, game, and results screen.
     */
    private void buildGUI() {
        frame.setSize(new Dimension(500, 300));
        frame.setPreferredSize(new Dimension(500, 300));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        buildMainPanel();
        buildLogin();
        buildSplash();
        buildSearch();
        buildEmployee();
        buildMedia();
        Collections.addAll(splashButtons, splashMedia, splashEmployees, splashDatabases, splashPatrons, splashVendors);
    }

    public void buildLogin() {
        loginMasterPanel.setBackground(menuBlue);
        loginMasterPanel.add(loginSecondaryPanel);
        BoxLayout box = new BoxLayout(loginSecondaryPanel, BoxLayout.Y_AXIS);
        loginSecondaryPanel.setLayout(box);
        //loginPasswordField.setEchoChar('*');
        loginSecondaryPanel.add(libraryName);
        libraryName.setFont(new Font("Courier", Font.BOLD, 25));
        loginSecondaryPanel.add(loginUsernameLabel);
        loginSecondaryPanel.add(loginUsernameField);
        loginSecondaryPanel.add(loginPasswordLabel);
        loginSecondaryPanel.add(loginPasswordField);
        loginSecondaryPanel.add(loginSubmit);
        loginSecondaryPanel.add(loginGuestLogin);
        loginSecondaryPanel.add(loginQuit);
        loginSubmit.addActionListener(this);
        loginGuestLogin.addActionListener(this);
        loginQuit.addActionListener(this);
    }

    public void buildSplash() {
        splashMasterPanel.setBackground(menuBlue);
        splashMasterPanel.add(splashSecondaryPanel);
        BoxLayout box = new BoxLayout(splashSecondaryPanel, BoxLayout.Y_AXIS);
        splashSecondaryPanel.add(libraryName);
        libraryName.setFont(new Font("Courier", Font.BOLD, 25));
        splashSecondaryPanel.setLayout(box);
        splashSecondaryPanel.add(splashMedia);
        splashMedia.addActionListener(this);
        splashSecondaryPanel.add(splashEmployees);
        splashEmployees.addActionListener(this);
        splashSecondaryPanel.add(splashPatrons);
        splashSecondaryPanel.add(splashVendors);
        splashSecondaryPanel.add(splashDatabases);
    }

    public void buildSearch(){
        employeeSuperMasterPanel.add(searchMasterPanel);
        searchMasterPanel.setPreferredSize(new Dimension(1920, 100));
        searchMasterPanel.setBackground(menuBlue);
        searchMasterPanel.add(search1Box);
        searchMasterPanel.add(search2Box);

    }

    public void buildEmployee() {

        int passwordIndex = 9;
        int permissionIndex = 8;

        int hGap = 25;
        int vGap = 25;

        employeeSuperMasterPanel.add(employeeMasterPanel);

        employeeSuperMasterPanel.setBackground(menuBlue);
        employeeMasterPanel.setBackground(menuBlue);

        int rows = read.employee.employeeT.size() ;

        GridLayout layout = new GridLayout(rows, 8);
        layout.setHgap(hGap);
        layout.setVgap(vGap);

        employeeMasterPanel.setLayout(layout);
        int currentRow = 0;

        //------------------------------------------------------------------- action listeners
        ActionListener empAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        };
        ArrayList<JButton> tempButtons = new ArrayList<>();
        for (var entry : read.employee.employeeT.entrySet()) {
            empAtt = entry.getValue().getData();
            String currentKey = entry.getKey();

            // -- filter stuff
            if(!filterDepartment.contains(empAtt.get(1))){
                filterDepartment.add(empAtt.get(1));
            }
            if(!filterProject.contains(empAtt.get(3))){
                filterProject.add(empAtt.get(3));
            }
            if(!filterSubject.contains(empAtt.get(4))){
                filterSubject.add(empAtt.get(4));
            }
            JButton idButton = new JButton(entry.getKey());
            tempButtons.add(idButton);
            employeeMasterPanel.add(idButton, currentRow, 0);
            for (int i = 1; i != empAtt.size() - 1; i++){
                JButton empButton;
                if(i != passwordIndex) {
                    empButton = new JButton(empAtt.get(i));
                    tempButtons.add(empButton);
                    if (i == permissionIndex) {
                        empButton.addActionListener(superActionListener(i, currentKey));
                    }
                }
                else{
                    empButton = new JButton(empAtt.get(i + 1));
                    tempButtons.add(empButton);
                }
                employeeMasterPanel.add(empButton, currentRow, i);
            }
            empButtons.add((ArrayList<JButton>) tempButtons.clone());
            tempButtons.clear();
            currentRow++;
        }
        System.out.println(filterDepartment);
        System.out.println(filterProject);
        System.out.println(filterSubject);
        populateComboBox();
    }

    private void populateComboBox(){
        String[] filters = {"Department", "Project", "Subject"};
        search1Box.addItem("All");

        for(int i = 0; i != filters.length; i++) {
            search1Box.addItem(filters[i]);
        }

        search1Box.setSelectedIndex(0);
        search1Box.addActionListener(search1ActionListener());
        search2Box.addActionListener(search2ActionListener());
        search2Box.setEnabled(false);
    }

    private ActionListener search1ActionListener(){

        ActionListener filter = e -> {
            String item = String.valueOf(search1Box.getSelectedItem());
            if(item.equals("All")){
                search2Box.removeAllItems();
                search2Box.setEnabled(false);
                rebuild();
            }
            else{
                search2Box.setEnabled(true);
                search2Box.removeAllItems();
                search2Box.addItem("All");
                search2Box.setSelectedIndex(0);
                switch(item){
                    case "Department" ->  {
                        for(int i = 0; i != filterDepartment.size(); i++){
                            search2Box.addItem(filterDepartment.get(i));
                        }
                    }

                    case "Project" -> {
                        for(int i = 0; i != filterProject.size(); i++){
                            search2Box.addItem(filterProject.get(i));
                        }
                    }

                    case "Subject" -> {
                        for(int i = 0; i != filterSubject.size(); i++){
                            search2Box.addItem(filterSubject.get(i));
                        }
                    }
                }
            }
        };
        return filter;
    }

    private ActionListener search2ActionListener(){
        ActionListener filter = e -> {
            String filter1 = String.valueOf(search1Box.getSelectedItem());
            String filter2 = String.valueOf(search2Box.getSelectedItem());

            if(filter2.equals("All")){
                rebuild();
            }
            else{
                updateButtons(filter2, filter1);
            }
        };
        return filter;
    }

    private void rebuild(){
        int hGap = 25;
        int vGap = 25;
        int passwordIndex = 9;
        int permissionIndex = 8;
        int currentRow = 0;

        employeeMasterPanel.removeAll();
        employeeMasterPanel.revalidate();
        employeeMasterPanel.repaint();

        GridLayout layout = new GridLayout(read.employee.employeeT.size(), 10);
        layout.setHgap(hGap);
        layout.setVgap(vGap);
        employeeMasterPanel.setLayout(layout);

        for (var entry : read.employee.employeeT.entrySet()) {
            empAtt = entry.getValue().getData();
            JButton idButton = new JButton(entry.getKey());
            employeeMasterPanel.add(idButton, currentRow, 0);
            for (int i = 1; i != empAtt.size() - 1; i++){
                JButton empButton;
                if(i != passwordIndex) {
                    empButton = new JButton(empAtt.get(i));
                    if (i == permissionIndex) {
                    }
                }
                else{
                    empButton = new JButton(empAtt.get(i + 1));
                }
                employeeMasterPanel.add(empButton, currentRow, i);
            }
            currentRow++;
        }
    }
    private void updateButtons(String filter, String category){
        int departmentIndex = 1;
        int projectIndex = 3;
        int subjectIndex = 4;
        int hGap = 25;
        int vGap = 25;
        int passwordIndex = 9;
        int permissionIndex = 8;
        int currentRow = 0;

        employeeMasterPanel.removeAll();
        employeeMasterPanel.revalidate();
        employeeMasterPanel.repaint();

        HashMap<String, employeeData> filteredData = new HashMap<>();

        for (var entry : read.employee.employeeT.entrySet()) {
            empAtt = entry.getValue().getData();
            if(category.equals("Department")) {
                if (empAtt.get(departmentIndex).equals(filter)) {
                    filteredData.put(entry.getKey(), entry.getValue());
                }
            }
            else if(category.equals("Project")) {
                if (empAtt.get(projectIndex).equals(filter)) {
                    filteredData.put(entry.getKey(), entry.getValue());
                }
            }
            else if(category.equals("Subject")) {
                if (empAtt.get(subjectIndex).equals(filter)) {
                    filteredData.put(entry.getKey(), entry.getValue());
                }
            }
        }

        GridLayout layout = new GridLayout(filteredData.size(), 10);
        layout.setHgap(hGap);
        layout.setVgap(vGap);
        employeeMasterPanel.setLayout(layout);

        for (var entry : filteredData.entrySet()) {
            empAtt = entry.getValue().getData();
            JButton idButton = new JButton(entry.getKey());
            employeeMasterPanel.add(idButton, currentRow, 0);
            for (int i = 1; i != empAtt.size() - 1; i++){
                JButton empButton;
                if(i != passwordIndex) {
                    empButton = new JButton(empAtt.get(i));
                    if (i == permissionIndex) {

                    }
                }
                else{
                    empButton = new JButton(empAtt.get(i + 1));
                }
                employeeMasterPanel.add(empButton, currentRow, i);
            }
            currentRow++;
        }
    }

    private ActionListener superActionListener(int index, String key){
        int permissionIndex = 8;
        if (index == permissionIndex){
            ActionListener permission = e -> {
                System.out.println(key);
            };
            return permission;
        }
        return null;
    }

    public void buildMedia(){
        //mediaMasterPanel.setBackground(menuBlue);
        mediaMasterPanel.setBackground(Color.PINK);
    }

    public void changeScreen(int screen) {
        String screenName = "";
        switch (screen) {
            case 1 -> screenName = "login";
            case 2 -> screenName = "splash";
            case 3 -> screenName = "employee";
            case 4 -> screenName = "media";

        }
        setScreenSize(screenName);
        cl.show(mainMasterPanel, screenName);
    }

    private void setScreenSize(String screen){
        HashMap<String, Dimension> size = new HashMap<>(Map.of("login", new Dimension(500, 300),
                "splash", new Dimension(500, 300),
                "employee", new Dimension(1920, 1080),
                "media", new Dimension(1500, 1000)));
        frame.setSize(size.get(screen));
        frame.setLocationRelativeTo(null);
    }

    public void buildMainPanel() {
        mainMasterPanel.setLayout(cl);
        mainMasterPanel.add(loginMasterPanel, "login");
        mainMasterPanel.add(splashMasterPanel, "splash");
        mainMasterPanel.add(employeeSuperMasterPanel, "employee");
        mainMasterPanel.add(mediaMasterPanel, "media");
        frame.add(mainMasterPanel);
        changeScreen(1);
    }

    public void switchTest()
    {
        for(int i = 1; i < 4; i++)
        {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
            System.out.println(i);
            changeScreen(i);
        }
    }

    public void logIn(){
        boolean valid = false;

        String user = loginUsernameField.getText();
        String password = loginPasswordField.getText();

        //valid = read.employee.login(user, password);
        valid = true;

        // check user and password
        if (valid){
            // get rank
            currentRank = 5; // test
            setButtons();
            changeScreen(2);
        }
        else{
            JOptionPane optionPane = new JOptionPane("Invalid Login", JOptionPane.ERROR_MESSAGE);
            JDialog dialog = optionPane.createDialog("Error");
            dialog.setVisible(true);
        }
    }

    private void setButtons(){
        ArrayList<Integer> index = new ArrayList<Integer>();
        switch(currentRank){
            case 1:{
                Collections.addAll(index, 0);
                break;
            }
            case 2:{
                Collections.addAll(index, 0, 2, 4);
                break;
            }
            case 3:{
                Collections.addAll(index, 0, 2, 3, 4);
                break;
            }
            case 4:{
                Collections.addAll(index, 0, 1, 2, 3);
                break;
            }
            case 5:{
                Collections.addAll(index, 0, 1, 2, 3, 4);
                break;
            }
            default:{
                break;
            }
        }
        for (int i = 0; i != splashButtons.size(); i++) {
            splashButtons.get(i).setVisible(false);
        }
        System.out.println(index);
        for (int i = 0; i != splashButtons.size(); i++){
            if (index.contains(i)){
                splashButtons.get(i).setVisible(true);
            }
        }
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == loginSubmit){
            logIn();
        }
        else if (e.getSource() == loginGuestLogin){
            changeScreen(2);
        }
        else if (e.getSource() == loginQuit){
            System.exit(0);
        }
        else if (e.getSource() == splashEmployees){
            changeScreen(3);
        }
        else if (e.getSource() == splashMedia){
            changeScreen(4);
        }

    }
}