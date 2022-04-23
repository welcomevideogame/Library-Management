package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

import data.*;

/**
 * Class for creating the GUI for the Stonybrook Library
 */
public class GUI implements ActionListener {

    // objects used in all GUIs
    private final JFrame frame = new JFrame();
    private final CardLayout cl = new CardLayout(0, 0);
    private final JPanel mainMasterPanel = new JPanel();
    private final JLabel libraryName = new JLabel("Stonybrook Library");
    private final Color menuBlue = new Color(173, 216, 230);

    // objects used in LoginGUI
    private final JPanel loginMasterPanel = new JPanel();
    private final JLabel loginUsernameLabel = new JLabel("Username");
    private final JLabel loginPasswordLabel = new JLabel("Password");
    private final JTextField loginUsernameField = new JTextField();
    private final JPasswordField loginPasswordField = new JPasswordField();
    private final JButton loginSubmit = new JButton("SUBMIT");
    private final JButton loginGuestLogin = new JButton("CONTINUE AS GUEST");
    private final JButton loginQuit = new JButton("QUIT");

    // objects used in SplashGUI
    private final JPanel splashMasterPanel = new JPanel();
    private final JPanel splashSecondaryPanel = new JPanel();
    private final JButton splashMedia = new JButton("MEDIA");
    private final JButton splashEmployees = new JButton("EMPLOYEES");
    private final JButton splashDatabases = new JButton("DATABASES");
    private final JButton splashPassword = new JButton("CHANGE PASSWORD");
    private final JButton splashAccount = new JButton("CREATE NEW EMPLOYEE");

    private final ArrayList<JButton> splashButtons = new ArrayList<>();
    private final ArrayList<ArrayList<JButton>> empButtons = new ArrayList<>();

    WriteData write;
    ReadFile read;
    useData useData;
    ArrayList<ArrayList<String>> dbUsage = new ArrayList<>();

    // objects used in SearchGUI
    private final JPanel searchMasterPanel = new JPanel();
    private final JComboBox search1Box = new JComboBox();
    private final JComboBox search2Box = new JComboBox();
    private final JComboBox searchMedBox = new JComboBox();
    private final ArrayList<String> filterDepartment = new ArrayList<>();
    private final ArrayList<String> filterSubject = new ArrayList<>();
    private final ArrayList<String> filterProject = new ArrayList<>();
    private final ArrayList<String> filterBoss = new ArrayList<>();
    private final ArrayList<String> filterVendor = new ArrayList<>();

    // objects used in DataGUI
    private final JPanel mediaMasterPanel = new JPanel();
    private final JPanel mediaGridPanel = new JPanel();
    private final JPanel employeeMasterPanel = new JPanel();
    private final JPanel employeeSuperMasterPanel = new JPanel();
    private final JPanel databaseMasterPanel = new JPanel();

    private final JPanel passwordMasterPanel = new JPanel();
    private final ArrayList<JPasswordField> newPasswordFields = new ArrayList<>();

    private final JPanel accountMasterPanel = new JPanel();
    private final ArrayList<Object> newAccountFields = new ArrayList<>();

    private final JScrollPane databaseScrollPane = new JScrollPane(databaseMasterPanel);
    private final JButton mediaOverdue = new JButton();

    // gui attributes
    private int currentUser = 1;
    private int currentRank = 1;
    private ArrayList<String> empAtt = new ArrayList<>();
    private final String[] headings = {"ID", "Name", "Department", "Boss ID", "Project", "Subject", "Req. Materials",
                                 "Alloc. Budget", "Spent Budget", "Perm. Level", "Employed"};

    // index values
    private final int permissionIndex = 8;
    private final int employedIndex = 9;

    /**
     * Constructor for the GUI
     * @param write The object for writing data back. Not really needed because write methods are static
     * @param read The object that contains the read data information. Contains employee and media information
     */
    public GUI(WriteData write, ReadFile read){
        this.write = write;
        this.read = read;
        useData = new useData();
        for (var entry : read.employee.employeeT.entrySet()) {
            entry.getValue().flipBudgets();
        }
        buildGUI();
    }

    /**
     * Sets settings for the frame and builds the menu, game, and results screen
     * Handles calling all the methods needed for building the entire GUI
     */
    private void buildGUI() {
        frame.setSize(new Dimension(500, 300));
        frame.setPreferredSize(new Dimension(500, 300));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setTitle("Login");
        frame.setResizable(false);

        frame.addWindowListener(onClose());

        buildMainPanel();
        buildLogin();
        buildSplash();
        buildSearch();
        buildEmployee();
        buildMedia();
        buildDatabase();
        buildPassword();
        buildAccount();
        Collections.addAll(splashButtons, splashMedia, splashEmployees, splashDatabases, splashPassword, splashAccount);
    }

    /**
     * Method that gives the function when the JFrame is closed
     * @return WindowAdapter with functionality
     */
    private WindowAdapter onClose(){
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowOpened(e);
                WriteData.writeEmp(read.employee.employeeT);
                if(useData.accessed()) WriteData.writeUse(useData);
            }
        };
    }

    /**
     * Builds the login screen. Could use some optimization
     */
    private void buildLogin() {
        loginMasterPanel.setBackground(menuBlue);
        loginMasterPanel.setLayout(new BoxLayout(loginMasterPanel, BoxLayout.PAGE_AXIS));
        JLabel spacer = new JLabel();
        JLabel spacer2 = new JLabel();
        JLabel spacer3 = new JLabel();
        JLabel spacer4 = new JLabel();
        spacer.setPreferredSize(new Dimension(550, 15));
        spacer2.setPreferredSize(new Dimension(550, 15));
        spacer3.setPreferredSize(new Dimension(1000, 15));
        spacer4.setPreferredSize(new Dimension(1000, 30));
        JPanel loginPanel1 = new JPanel();
        JPanel loginPanel2 = new JPanel();
        loginPasswordField.setColumns(10);
        loginUsernameField.setColumns(10);
        loginPanel1.setBackground(menuBlue);
        loginPanel2.setBackground(menuBlue);
        loginMasterPanel.add(loginPanel1);
        loginMasterPanel.add(loginPanel2);
        loginPasswordField.setEchoChar('*');
        JLabel name = new JLabel("Stonybrook Library");
        name.setFont(new Font("Courier", Font.BOLD, 25));
        loginPanel1.add(name);
        loginPanel1.add(spacer3);
        loginPanel1.add(loginUsernameLabel);
        loginPanel1.add(loginUsernameField);
        loginPanel1.add(spacer);
        loginPanel1.add(loginPasswordLabel);
        loginPanel1.add(loginPasswordField);
        loginPanel1.add(spacer2);
        loginPanel2.add(spacer4);
        loginPanel2.add(loginSubmit);
        loginPanel2.add(loginGuestLogin);
        loginPanel2.add(loginQuit);
        loginSubmit.addActionListener(this);
        loginGuestLogin.addActionListener(this);
        loginQuit.addActionListener(this);
    }

    /**
     * Builds the splash screen
     * Adds all the buttons that will be on the splash screen as well as add their ActionListeners
     */
    private void buildSplash() {
        splashMasterPanel.setBackground(menuBlue);
        splashSecondaryPanel.setBackground(menuBlue);
        GridLayout layout = new GridLayout(5, 1);
        layout.setVgap(25);
        JLabel spacer = new JLabel();
        spacer.setPreferredSize(new Dimension(1900, 35));
        splashMasterPanel.add(libraryName);
        splashMasterPanel.add(spacer);
        libraryName.setFont(new Font("Courier", Font.BOLD, 25));
        splashSecondaryPanel.setLayout(layout);

        splashSecondaryPanel.add(splashMedia);
        splashSecondaryPanel.add(splashEmployees);
        splashSecondaryPanel.add(splashDatabases);
        splashSecondaryPanel.add(splashPassword);
        splashSecondaryPanel.add(splashAccount);
        splashMasterPanel.add(splashSecondaryPanel);

        splashMedia.addActionListener(this);
        splashEmployees.addActionListener(this);
        splashDatabases.addActionListener(this);
        splashPassword.addActionListener(this);
        splashAccount.addActionListener(this);

        Dimension buttonSize = new Dimension(250, 35);
        splashMedia.setPreferredSize(buttonSize);
        splashEmployees.setPreferredSize(buttonSize);
        splashDatabases.setPreferredSize(buttonSize);
        splashPassword.setPreferredSize(buttonSize);
        splashAccount.setPreferredSize(buttonSize);

        splashEmployees.setVisible(false);
        splashPassword.setVisible(false);
        splashDatabases.setVisible(false);
        splashAccount.setVisible(false);
    }

    /**
     * Builds the top panel on the employee screen that has search boxes and filters
     */
    private void buildSearch(){
        employeeSuperMasterPanel.add(searchMasterPanel);
        searchMasterPanel.setPreferredSize(new Dimension(1920, 100));
        searchMasterPanel.setBackground(menuBlue);
        searchMasterPanel.add(search1Box);
        searchMasterPanel.add(search2Box);
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(exitActionListener("Employees"));
        searchMasterPanel.add(exitButton);
    }

    /**
     * Builds the employee screen
     * Also adds to a list of filters that will be used in the search boxes
     * Could be optimized better like the media panel
     */
    private void buildEmployee() {

        int passwordIndex = 9;

        int hGap = 25;
        int vGap = 25;

        employeeSuperMasterPanel.add(employeeMasterPanel);

        employeeSuperMasterPanel.setBackground(menuBlue);
        employeeMasterPanel.setBackground(menuBlue);

        int rows = read.employee.employeeT.size() ;

        GridLayout layout = new GridLayout(rows + 1, 12);
        layout.setHgap(hGap);
        layout.setVgap(vGap);

        employeeMasterPanel.setLayout(layout);

        addEmpHeadings();

        ArrayList<JButton> tempButtons = new ArrayList<>();
        for (var entry : read.employee.employeeT.entrySet()) {
            empAtt = entry.getValue().getData();
            String currentKey = entry.getKey();

            // -- filter stuff
            if(!filterDepartment.contains(empAtt.get(1))){
                filterDepartment.add(empAtt.get(1));
            }
            if(!filterBoss.contains(empAtt.get(2))){
                filterBoss.add(empAtt.get(2));
            }
            if(!filterProject.contains(empAtt.get(3))){
                filterProject.add(empAtt.get(3));
            }
            if(!filterSubject.contains(empAtt.get(4))){
                filterSubject.add(empAtt.get(4));
            }
            JButton idButton = new JButton(entry.getKey());
            tempButtons.add(idButton);
            employeeMasterPanel.add(idButton);
            for (int i = 0; i != empAtt.size() - 1; i++){
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
                    empButton.addActionListener(superActionListener(i, currentKey));
                }
                employeeMasterPanel.add(empButton);
            }
            empButtons.add((ArrayList<JButton>) tempButtons.clone());
            tempButtons.clear();
        }
        populateComboBox();
    }

    /**
     * Populates the ComboBoxes with data from the filter arraylist data
     */
    private void populateComboBox(){
        String[] filters = {"Department", "Boss", "Project", "Subject"};
        search1Box.addItem("All");

        for(int i = 0; i != filters.length; i++) {
            search1Box.addItem(filters[i]);
        }

        search1Box.setSelectedIndex(0);
        search1Box.addActionListener(search1ActionListener());
        search2Box.addActionListener(search2ActionListener());
        search2Box.setEnabled(false);
    }

    /**
     * Builds the ActionListeners for the first ComboBox
     * @return Changes depending on what item is selected. Affects second ComboBox
     */
    private ActionListener search1ActionListener(){

        return e -> {
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

                    case "Boss" -> {
                        for(int i = 0; i != filterBoss.size(); i++){
                            search2Box.addItem(filterBoss.get(i));
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
    }

    /**
     * Builds the ActionListeners for the second ComboBox
     * @return Will rebuild the buttons with the selected ComboBox item
     */
    private ActionListener search2ActionListener(){
        return e -> {
            String filter1 = String.valueOf(search1Box.getSelectedItem());
            String filter2 = String.valueOf(search2Box.getSelectedItem());

            if(filter2.equals("All")){
                rebuild();
            }
            else{
                updateButtons(filter2, filter1);
            }
        };
    }

    /**
     * Rebuilds the employee screen with the entire employeeData HashTable
     */
    private void rebuild(){
        int hGap = 25;
        int vGap = 25;
        int passwordIndex = 9;

        employeeMasterPanel.removeAll();
        employeeMasterPanel.revalidate();
        employeeMasterPanel.repaint();

        GridLayout layout = new GridLayout(read.employee.employeeT.size() + 1, 10);
        layout.setHgap(hGap);
        layout.setVgap(vGap);
        employeeMasterPanel.setLayout(layout);

        addEmpHeadings();

        ArrayList<JButton> tempButtons = new ArrayList<>();
        for (var entry : read.employee.employeeT.entrySet()) {
            String currentKey = entry.getKey();
            empAtt = entry.getValue().getData();
            JButton idButton = new JButton(entry.getKey());
            tempButtons.add(idButton);
            employeeMasterPanel.add(idButton);
            for (int i = 0; i != empAtt.size() - 1; i++){
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
                    empButton.addActionListener(superActionListener(i, currentKey));
                }
                employeeMasterPanel.add(empButton);
            }
            empButtons.add((ArrayList<JButton>) tempButtons.clone());
            tempButtons.clear();
        }
    }

    /**
     * Method primarily used for the account creating process to add possible new departments
     */
    private void filterBuilder(){
        ArrayList<String> empAtt;

        filterDepartment.clear();
        filterBoss.clear();
        filterProject.clear();
        filterSubject.clear();

        for (var entry : read.employee.employeeT.entrySet()) {
            empAtt = entry.getValue().getData();

            if (!filterDepartment.contains(empAtt.get(1))) {
                filterDepartment.add(empAtt.get(1));
            }
            if (!filterBoss.contains(empAtt.get(2))) {
                filterBoss.add(empAtt.get(2));
            }
            if (!filterProject.contains(empAtt.get(3))) {
                filterProject.add(empAtt.get(3));
            }
            if (!filterSubject.contains(empAtt.get(4))) {
                filterSubject.add(empAtt.get(4));
            }
        }
    }

    /**
     * Adds the employee headings to the employee screen
     * Made into a method since it is used by multiple things
     */
    private void addEmpHeadings(){
        Color headingColor = new Color(150,205,225);
        for(int i = 0; i != headings.length; i++){
            JButton headingButton = new JButton(headings[i]);
            headingButton.setBackground(headingColor);
            employeeMasterPanel.add(headingButton);
        }
    }

    /**
     * Updates the employee screen with new buttons
     * @param filter The selected item from the first ComboBox
     * @param category The selected item from the second CombBox
     */
    private void updateButtons(String filter, String category){
        int departmentIndex = 1;
        int bossIndex = 2;
        int projectIndex = 3;
        int subjectIndex = 4;
        int hGap = 25;
        int vGap = 25;
        int passwordIndex = 9;

        employeeMasterPanel.removeAll();
        employeeMasterPanel.revalidate();
        employeeMasterPanel.repaint();

        HashMap<String, employeeData> filteredData = new HashMap<>();

        addEmpHeadings();
        ArrayList<JButton> tempButtons = new ArrayList<>();

        for (var entry : read.employee.employeeT.entrySet()) {
            empAtt = entry.getValue().getData();
            switch (category) {
                case "Department":
                    if (empAtt.get(departmentIndex).equals(filter)) {
                        filteredData.put(entry.getKey(), entry.getValue());
                    }
                    break;
                case "Boss":
                    if (empAtt.get(bossIndex).equals(filter)){
                        filteredData.put(entry.getKey(), entry.getValue());
                    }
                case "Project":
                    if (empAtt.get(projectIndex).equals(filter)) {
                        filteredData.put(entry.getKey(), entry.getValue());
                    }
                    break;
                case "Subject":
                    if (empAtt.get(subjectIndex).equals(filter)) {
                        filteredData.put(entry.getKey(), entry.getValue());
                    }
                    break;
            }
        }

        GridLayout layout = new GridLayout(filteredData.size() + 1, 10);
        layout.setHgap(hGap);
        layout.setVgap(vGap);
        employeeMasterPanel.setLayout(layout);

        for (var entry : filteredData.entrySet()) {
            String currentKey = entry.getKey();
            empAtt = entry.getValue().getData();
            JButton idButton = new JButton(entry.getKey());
            tempButtons.add(idButton);
            employeeMasterPanel.add(idButton);
            for (int i = 0; i != empAtt.size() - 1; i++){
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
                    empButton.addActionListener(superActionListener(i, currentKey));
                }
                employeeMasterPanel.add(empButton);
            }
            empButtons.add((ArrayList<JButton>) tempButtons.clone());
            tempButtons.clear();
        }
    }

    /**
     * Returns the ActionListeners for all the buttons on employee screen
     * @param index The index for which button is going to be used
     * @param key The key of the employee whose attributes will be edited
     * @return The ActionListener with all the functionality
     */
    private ActionListener superActionListener(int index, String key){
        if (index == permissionIndex){
            return e -> {

                boolean valid = false;
                boolean outRank = checkOutrank(key);
                boolean user = String.valueOf(currentUser).equals(key);

                if (outRank || user){
                    JOptionPane optionPane;
                    JDialog dialog;
                    if(outRank) {
                        optionPane = new JOptionPane("Not enough permissions ", JOptionPane.ERROR_MESSAGE);
                        dialog = optionPane.createDialog("Permissions");
                    }
                    else{
                        optionPane = new JOptionPane("Cannot change your own rank ", JOptionPane.ERROR_MESSAGE);
                        dialog = optionPane.createDialog("Self");
                    }
                    dialog.setVisible(true);
                }

                else {
                    do {
                        String newRank = JOptionPane.showInputDialog("What will their new rank be?");
                        try {

                            if (newRank != null) {
                                int a = Integer.parseInt(newRank);
                                if (a > currentRank || a < 1) {
                                    JOptionPane optionPane = new JOptionPane("Rank must be between 1 and "
                                            + currentRank, JOptionPane.ERROR_MESSAGE);
                                    JDialog dialog = optionPane.createDialog("Out of range");
                                    dialog.setVisible(true);
                                } else {
                                    valid = true;
                                    changeRank(key, a);
                                }
                            } else {
                                valid = true;
                            }

                        } catch (NumberFormatException err) {
                            JOptionPane optionPane = new JOptionPane("Invalid number", JOptionPane.ERROR_MESSAGE);
                            JDialog dialog = optionPane.createDialog("Bad input");
                            dialog.setVisible(true);
                        }

                    } while (!valid);
                }
            };
        }
        else if(index == employedIndex){
            return e -> {
                boolean outRank = checkOutrank(key);
                boolean user = String.valueOf(currentUser).equals(key);

                if (outRank || user){
                    JOptionPane optionPane;
                    JDialog dialog;
                    if(outRank) {
                        optionPane = new JOptionPane("Not enough permissions ", JOptionPane.ERROR_MESSAGE);
                        dialog = optionPane.createDialog("Permissions");
                    }
                    else{
                        optionPane = new JOptionPane("Cannot edit yourself ", JOptionPane.ERROR_MESSAGE);
                        dialog = optionPane.createDialog("Self");
                    }
                    dialog.setVisible(true);
                }
                else{
                    changeEmployment(key);
                }
            };
        }
        return null;
    }

    /**
     * Method that compares current user's rank to another employee's
     * @param key ID for the other employee
     * @return true if they are outranked, otherwise will be false
     */
    private boolean checkOutrank(String key){
        return(currentRank <= Integer.parseInt(read.employee.employeeT.get(key).getData().get(permissionIndex)));
    }

    /**
     * Toggles the user's employment
     * @param key ID for the employee
     */
    private void changeEmployment(String key){
        String newState = "";
        int empIndex = 10;

        employeeData emp = read.employee.employeeT.get(key);
        if(emp.getEmployment().equals("Yes")){
            emp.setEmployment("No");
            newState = "No";
        }
        else if(emp.getEmployment().equals("No")){
            emp.setEmployment("Yes");
            newState = "Yes";
        }
        else{
            JOptionPane optionPane = new JOptionPane("Failed to change employment ", JOptionPane.ERROR_MESSAGE);
            JDialog dialog = optionPane.createDialog("Self");
            dialog.setVisible(true);
        }
        if(!newState.isBlank()){
            for(int i = 0; i != empButtons.size(); i++) {
                if (empButtons.get(i).get(0).getText().equals(key)) { // should have used a hashmap
                    empButtons.get(i).get(empIndex).setText(newState);
                }
            }
        }
    }

    /**
     * Changes the rank for the employee
     * Makes sure that it is a valid move beforehand
     * @param key ID for the employee
     * @param newRank The new rank that the employee wil get
     */
    private void changeRank(String key, int newRank){
        int permIndex = 9; // must make new one since arraylist is 9
        employeeData user = read.employee.employeeT.get(key);
        try {
            user.updateRank(newRank);
            for(int i = 0; i != empButtons.size(); i++){
                if(empButtons.get(i).get(0).getText().equals(key)){
                    empButtons.get(i).get(permIndex).setText(String.valueOf(newRank));
                }
            }
        }
        catch(NullPointerException e){
            JOptionPane optionPane = new JOptionPane("Could not change rank", JOptionPane.ERROR_MESSAGE);
            JDialog dialog = optionPane.createDialog("Error");
            dialog.setVisible(true);
        }
    }

    /**
     * Builds the media screen
     */
    private void buildMedia(){

        JPanel mediaNorthPanel = new JPanel();
        mediaMasterPanel.setBackground(menuBlue);
        mediaGridPanel.setBackground(menuBlue);
        mediaNorthPanel.setBackground(menuBlue);

        mediaOverdue.setText("Overdue");
        mediaOverdue.addActionListener(this);
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(exitActionListener("Media"));

        JLabel title = new JLabel("Stonybrook Media Viewer");
        title.setFont(new Font("Courier", Font.BOLD, 25));

        JLabel spacer = new JLabel();
        spacer.setPreferredSize(new Dimension(1920, 15));
        JLabel spacer2 = new JLabel();
        spacer2.setPreferredSize(new Dimension(100, 0));

        mediaNorthPanel.add(title);
        mediaNorthPanel.add(new Label());
        mediaNorthPanel.add(mediaOverdue);
        mediaNorthPanel.add(searchMedBox);
        mediaNorthPanel.add(spacer2);
        mediaNorthPanel.add(exitButton);
        mediaMasterPanel.add(mediaNorthPanel);
        mediaMasterPanel.add(spacer);
        mediaMasterPanel.add(mediaGridPanel);
        getMediaFilter();
        populateMedCombo();
        buildMediaButtons(read.Media.mediaT);
    }

    /**
     * Populates the ComboBoxes with filter data
     */
    private void populateMedCombo(){
        searchMedBox.addItem("All");
        for(int i = 0; i != filterVendor.size(); i++){
            searchMedBox.addItem(filterVendor.get(i));
        }
        searchMedBox.setSelectedIndex(0);
        searchMedBox.addActionListener(medComboAction());
    }

    /**
     * Gives ActionListener for the overdue button
     * @return The ActionListener will toddle on and off depending on what state it is in
     */
    private ActionListener medComboAction(){
        return e -> {
            if(mediaOverdue.getText().equals("Revert")) mediaOverdue.setText("Overdue");

            String filter = String.valueOf(searchMedBox.getSelectedItem());

            if(filter.equals("All")){
                clearMedia();
                buildMediaButtons(read.Media.mediaT);
            }
            else{
                filterVendor(filter);
            }
        };
    }

    /**
     * Method for filtering by the vendor
     * @param filter The vendor that will be filtered by
     */
    private void filterVendor(String filter){
        int vendorIndex = 5;
        clearMedia();
        Hashtable<String, mediaData> filteredData = new Hashtable<>();
        for(var entry: read.Media.mediaT.entrySet()){
            if(entry.getValue().getData().get(vendorIndex).equals(filter)){
                filteredData.put(entry.getKey(), entry.getValue());
            }
        }
        buildMediaButtons(filteredData);
    }

    /**
     * Populates the filterVendor data by unique vendor names
     */
    private void getMediaFilter(){
        int vendorIndex = 5;
        for (var entry : read.Media.mediaT.entrySet()) {
            String vendorName = entry.getValue().getData().get(vendorIndex);
            if(!filterVendor.contains(vendorName)) filterVendor.add(vendorName);
        }
    }

    /**
     * Builds the buttons on the media screen
     * @param ht Takes a HashTable of employeeData objects that can be filtered
     */
    private void buildMediaButtons(Hashtable<String, mediaData> ht){

        int hGap = 25;
        int vGap = 25;

        int dateIndex = 4;

        String[] headings = {"MediaID", "Name", "Type", "BorrowTime", "Borrowable", "DueDate", "Vendor"};
        GridLayout layout = new GridLayout(ht.size() + 1, headings.length);
        layout.setHgap(hGap);
        layout.setVgap(vGap);

        mediaGridPanel.setLayout(layout);

        for(int i = 0; i != headings.length; i++){
            JButton useButton = new JButton(headings[i]);
            mediaGridPanel.add(useButton);
        }

        ArrayList<String> tempValues;
        for (var entry : ht.entrySet()) {
            tempValues = entry.getValue().getData();
            JButton idButton = new JButton(entry.getKey());
            mediaGridPanel.add(idButton);
            for(int i = 0; i != tempValues.size(); i++) {
                JButton attButton = new JButton(tempValues.get(i));
                mediaGridPanel.add(attButton);
                if (i == dateIndex) {
                    if(checkOverdue(tempValues.get(i))){
                        attButton.setBackground(new Color(255, 50, 50));
                    }
                }
            }
        }
    }

    /**
     * Checks if the book is overdue
     * @param date Gets the due date for the object
     * @return Boolean that will be true if it is overdue
     */
    private boolean checkOverdue(String date){
        try {
            return LocalDate.parse(date).isBefore(LocalDate.now());
        }
        catch(DateTimeParseException e){
            return true;
        }
    }

    /**
     * Clears the media button objects
     */
    private void clearMedia(){
        mediaGridPanel.removeAll();
        mediaGridPanel.revalidate();
        mediaGridPanel.repaint();
    }

    /**
     * Filters the buttons by overdue
     * Takes the data that is in the ComboBox
     */
    private void filterOverdue(){

        int dateIndex = 4;

        Hashtable<String, mediaData> filteredMedia = new Hashtable<>();

        for (var entry : read.Media.mediaT.entrySet()) {
            if(checkOverdue(entry.getValue().getData().get(dateIndex))) {
                filteredMedia.put(entry.getKey(), entry.getValue());
            }
        }
        buildMediaButtons(filteredMedia);
    }

    /**
     * Builds the database screen
     * Optimized with heading array
     */
    private void buildDatabase(){

        dbUsage = ReadFile.readUseData();
        assert dbUsage != null;
        int columns = dbUsage.get(0).size();
        int hGap = 25;
        int vGap = 25;

        String[] headings = {"EmpID", "Used Med", "Used Emp", "Used Pat", "Used Ven", "Used DB",
                             "Med Time", "Emp Time", "Pat Time", "Ven Time", "DB Time"};

        JLabel title = new JLabel("Stonybrook Access Viewer");
        title.setFont(new Font("Courier", Font.BOLD, 25));
        JPanel databaseNorthPanel = new JPanel();

        JPanel databaseGridPanel = new JPanel();
        GridLayout layout = new GridLayout(dbUsage.size() + 1, columns);
        layout.setHgap(hGap);
        layout.setVgap(vGap);
        databaseGridPanel.setLayout(layout);

        for(int i = 0; i != headings.length; i++){
            JButton useButton = new JButton(headings[i]);
            databaseGridPanel.add(useButton);
        }
        for(int i = 0; i != dbUsage.size(); i++){
            for(int j = 0; j != dbUsage.get(i).size(); j++){
                JButton useButton = new JButton(dbUsage.get(i).get(j));
                databaseGridPanel.add(useButton);
            }
        }

        databaseScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        databaseScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(exitActionListener("Databases"));
        JLabel spacer = new JLabel();
        spacer.setPreferredSize(new Dimension(1, 30));

        databaseNorthPanel.add(title);
        databaseNorthPanel.add(exitButton);

        databaseMasterPanel.setLayout(new BorderLayout());
        databaseMasterPanel.add(databaseNorthPanel, BorderLayout.NORTH);
        databaseMasterPanel.add(spacer, BorderLayout.CENTER);
        databaseMasterPanel.add(databaseGridPanel, BorderLayout.SOUTH);

        databaseMasterPanel.setBackground(menuBlue);
        databaseNorthPanel.setBackground(menuBlue);
        databaseGridPanel.setBackground(menuBlue);

    }

    /**
     * Builds the password screen
     * Could be optimized better
     */
    private void buildPassword(){
        JPanel passwordFields = new JPanel();
        passwordFields.setBackground(menuBlue);
        passwordMasterPanel.setBackground(menuBlue);

        GridLayout layout = new GridLayout(10, 1);
        passwordFields.setLayout(layout);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(exitActionListener(""));

        JButton submitButton = new JButton("Submit");
        submitButton.setPreferredSize(new Dimension(75, 30));
        submitButton.addActionListener(passwordActionListener());

        JLabel title = new JLabel("Account Settings: Password");
        title.setFont(new Font("Courier", Font.BOLD, 25));

        JLabel oldPasswordLabel = new JLabel("Old Password                      ");
        oldPasswordLabel.setFont(new Font("Courier", Font.BOLD, 15));
        JPasswordField oldPassword = new JPasswordField();
        JLabel newPasswordLabel = new JLabel("New Password");
        newPasswordLabel.setFont(new Font("Courier", Font.BOLD, 15));
        JPasswordField newPassword = new JPasswordField();

        JLabel conPasswordLabel = new JLabel("Confirm Password");
        conPasswordLabel.setFont(new Font("Courier", Font.BOLD, 15));
        JPasswordField conPassword = new JPasswordField();

        passwordFields.add(oldPasswordLabel);
        passwordFields.add(oldPassword);
        passwordFields.add(new JLabel());
        passwordFields.add(newPasswordLabel);
        passwordFields.add(newPassword);
        passwordFields.add(new JLabel());
        passwordFields.add(conPasswordLabel);
        passwordFields.add(conPassword);
        passwordFields.add(new JLabel());
        passwordMasterPanel.add(title);
        passwordMasterPanel.add(exitButton);
        JLabel spacer = new JLabel();
        spacer.setPreferredSize(new Dimension(1000, 50));
        JLabel spacer2 = new JLabel();
        spacer2.setPreferredSize(new Dimension(1000, 10));
        passwordMasterPanel.add(spacer);
        passwordMasterPanel.add(passwordFields);
        passwordMasterPanel.add(spacer2);
        passwordMasterPanel.add(submitButton);
        Collections.addAll(newPasswordFields, oldPassword, newPassword, conPassword);
    }

    /**
     * ActionListener for the JPasswordFields. Checks to see if any of them are empty
     * @return Returns the anonymous function
     */
    private ActionListener passwordActionListener(){
        return e -> {
            String oP = String.valueOf(newPasswordFields.get(0).getPassword());
            String nP = String.valueOf(newPasswordFields.get(1).getPassword());
            String cP = String.valueOf(newPasswordFields.get(2).getPassword());
            if(!(oP.isEmpty() || nP.isEmpty() || cP.isEmpty())){
                handlePassword(oP, nP, cP);
            }
            else{
                JOptionPane optionPane = new JOptionPane("One or more fields is empty", JOptionPane.ERROR_MESSAGE);
                JDialog dialog = optionPane.createDialog("Empty field");
                dialog.setVisible(true);
            }
        };
    }

    /**
     * Method for checking for password validity
     * @param oP Old password, current password of the use
     * @param nP New password, gotten from the second JPasswordField
     * @param cP Confirmation password, gotten from the third JPasswordField
     */
    private void handlePassword(String oP, String nP, String cP){
        String currentPassword = read.employee.employeeT.get(String.valueOf(currentUser)).getPassword();
        JOptionPane optionPane;
        JDialog dialog;

        boolean valid = false;

        if(!oP.equals(currentPassword)){
            optionPane = new JOptionPane("Old password is incorrect", JOptionPane.ERROR_MESSAGE);
            dialog = optionPane.createDialog("Incorrect password");
        }
        else if(!nP.equals(cP)){
            optionPane = new JOptionPane("Passwords do not match", JOptionPane.ERROR_MESSAGE);
            dialog = optionPane.createDialog("Not matching");
        }
        else{
            optionPane = new JOptionPane("Password has been changed", JOptionPane.INFORMATION_MESSAGE);
            dialog = optionPane.createDialog("Success");
            read.employee.employeeT.get(String.valueOf(currentUser)).setPassword(nP);
            valid = true;
        }
        dialog.setVisible(true);
        if(valid){
            for(int i = 0; i != newPasswordFields.size(); i++) {
                ((JTextField) newPasswordFields.get(i)).setText("");
            }
            changeScreen(2);
        }
    }

    /**
     * Builds the account screen
     */
    private void buildAccount(){
        JPanel accountSecondaryPanel = new JPanel();

        accountMasterPanel.setBackground(menuBlue);
        accountSecondaryPanel.setBackground(menuBlue);

        GridLayout layout = new GridLayout(30, 1);
        accountSecondaryPanel.setLayout(layout);

        JLabel title = new JLabel("Employee Account Creator");
        title.setFont(new Font("Courier", Font.BOLD, 25));

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(exitActionListener(""));

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(accountActionListener());

        String[] names = {"Name", "Department", "Boss ID", "Project", "Subject", "Requested Materials",
        "Allocated Budget", "Amount Spent", "Permission", "Password"};

        ArrayList<Object> objects = new ArrayList<>();
        for(int i = 0; i != names.length; i++){
            JLabel label = new JLabel(names[i]);
            label.setFont(new Font("Courier", Font.BOLD, 15));
            objects.add(label);
            if(i != 9) {
                JTextField nameField = new JTextField();
                nameField.setPreferredSize(new Dimension(200, 20));
                objects.add(nameField);
                newAccountFields.add(nameField);
            }
            else{
                JPasswordField passField = new JPasswordField();
                passField.setPreferredSize(new Dimension(200, 20));
                objects.add(passField);
                newAccountFields.add(passField);
            }
        }

        for(int i = 0; i != objects.size(); i ++){
            accountSecondaryPanel.add((JLabel)objects.get(i));
            i++;
            accountSecondaryPanel.add((JTextField)objects.get(i));
            accountSecondaryPanel.add(new JLabel());
        }


        JLabel spacer = new JLabel();
        spacer.setPreferredSize(new Dimension(1920, 15));
        JLabel spacer2 = new JLabel();
        spacer2.setPreferredSize(new Dimension(1920, 0));

        accountMasterPanel.add(title);
        accountMasterPanel.add(exitButton);
        accountMasterPanel.add(spacer);
        accountMasterPanel.add(accountSecondaryPanel);
        accountMasterPanel.add(spacer2);
        accountMasterPanel.add(submitButton);

    }

    /**
     * Method or handling the TextFields
     * @return Gives anonymous function that gets all the fields from the account builder
     */
    private ActionListener accountActionListener(){
        return e -> {
            String name = ((JTextField) newAccountFields.get(0)).getText();
            String department = ((JTextField) newAccountFields.get(1)).getText();
            String bossID = ((JTextField) newAccountFields.get(2)).getText();
            String project = ((JTextField) newAccountFields.get(3)).getText();
            String subject = ((JTextField) newAccountFields.get(4)).getText();
            String req = ((JTextField) newAccountFields.get(5)).getText();
            String alloc = ((JTextField) newAccountFields.get(6)).getText();
            String amt = ((JTextField) newAccountFields.get(7)).getText();
            String perm = ((JTextField) newAccountFields.get(8)).getText();
            String pass = String.valueOf((((JPasswordField) newAccountFields.get(9)).getPassword()));
            if(!(name.isEmpty() || department.isEmpty() || bossID.isEmpty() || project.isEmpty() || subject.isEmpty() ||
            req.isEmpty() || alloc.isEmpty() || amt.isEmpty() || perm.isEmpty() || pass.isEmpty())){
                try {
                    int test = Integer.parseInt(perm);
                    if(test >= 1 && test <= 5) {
                        int max = 0;
                        for (var entry : read.employee.employeeT.entrySet()) {
                            int current;
                            try {
                                current = Integer.parseInt(entry.getKey());
                            } catch (NumberFormatException ex) {
                                current = 0;
                            }
                            if (current > max) max = current;
                        }
                        read.employee.employeeT.put(String.valueOf(max + 1),
                                new employeeData(name, department, bossID, project, subject, req,
                                        alloc, perm, pass, "Yes", amt));

                        JOptionPane optionPane = new JOptionPane("Successfully created employee", JOptionPane.INFORMATION_MESSAGE);
                        JDialog dialog = optionPane.createDialog("Success");
                        dialog.setVisible(true);
                        for(int i = 0; i != newAccountFields.size(); i++){
                            if(i!=9){
                                ((JTextField) newAccountFields.get(i)).setText("");
                            }
                            else{
                                ((JPasswordField) newAccountFields.get(i)).setText("");
                            }
                        }
                        rebuild();
                        filterBuilder();
                        changeScreen(2);
                    }
                    else{
                        JOptionPane optionPane = new JOptionPane("Invalid permission level", JOptionPane.ERROR_MESSAGE);
                        JDialog dialog = optionPane.createDialog("Perm level");
                        dialog.setVisible(true);
                    }
                }
                catch(NumberFormatException ex){
                    JOptionPane optionPane = new JOptionPane("Invalid permission level", JOptionPane.ERROR_MESSAGE);
                    JDialog dialog = optionPane.createDialog("Perm level");
                    dialog.setVisible(true);
                }
            }
            else{
                JOptionPane optionPane = new JOptionPane("One or more fields is empty", JOptionPane.ERROR_MESSAGE);
                JDialog dialog = optionPane.createDialog("Empty field");
                dialog.setVisible(true);
            }
        };
    }

    /**
     * ActionListener for the exit buttons on all the screens
     * Returns to the splash screen
     * @param db The current page that they are on. Keep blank if non-database page
     * @return Anonymous function
     */
    private ActionListener exitActionListener(String db){
        return e -> {
            changeScreen(2);
            if(!db.isEmpty()) {
                useData.endTimer(db);
            }
        };
    }

    /**
     * Changes the screen to something else and with the screen size method
     * @param screen The current number of screen. Goes through switch statement
     */
    private void changeScreen(int screen) {
        String screenName = "";
        switch (screen) {
            case 1 -> screenName = "login";
            case 2 -> screenName = "splash";
            case 3 -> screenName = "employee";
            case 4 -> screenName = "media";
            case 5 -> screenName = "database";
            case 6 -> screenName = "password";
            case 7 -> screenName = "account";

        }
        setScreenSize(screenName);
        cl.show(mainMasterPanel, screenName);
    }

    /**
     * Has a HashMap of dimensions for screen sizes
     * @param screen The string of the name for the screen
     */
    private void setScreenSize(String screen){
        HashMap<String, Dimension> size = new HashMap<>(Map.of("login", new Dimension(500, 300),
                "splash", new Dimension(500, 450),
                "employee", new Dimension(1920, 1080),
                "media", new Dimension(1500, 1000),
                "database", new Dimension(1500, 1000),
                "password", new Dimension(500, 500),
                "account", new Dimension(500, 750)));
        frame.setSize(size.get(screen));
        frame.setLocationRelativeTo(null);
    }

    /**
     * Builds the main panel for the screen
     * Adds all the individual screens to the main master panel for the card layout
     */
    private void buildMainPanel() {
        mainMasterPanel.setLayout(cl);
        mainMasterPanel.add(loginMasterPanel, "login");
        mainMasterPanel.add(splashMasterPanel, "splash");
        mainMasterPanel.add(employeeSuperMasterPanel, "employee");
        mainMasterPanel.add(mediaMasterPanel, "media");
        mainMasterPanel.add(databaseScrollPane, "database");
        mainMasterPanel.add(passwordMasterPanel, "password");
        mainMasterPanel.add(accountMasterPanel, "account");
        frame.add(mainMasterPanel);
        changeScreen(1);
    }

    /**
     * Method for logging in
     * Checks to see if the login is valid
     */
    private void logIn(){
        boolean valid = false;
        boolean empty = false;

        String user = loginUsernameField.getText();
        String password = new String(loginPasswordField.getPassword());

        try {
            valid = read.employee.login(user, password);
        }

        catch(NullPointerException e){
            JOptionPane optionPane = new JOptionPane("Could not log in", JOptionPane.ERROR_MESSAGE);
            JDialog dialog = optionPane.createDialog("Database error");
            dialog.setVisible(true);
            empty = true;
        }

        if (valid){
            try{
                currentUser = Integer.parseInt(user);
                currentRank = Integer.parseInt(read.employee.employeeT.get(user).getPermissisonLevel());
                setButtons();
                useData.setEmpID(String.valueOf(currentUser));
                changeScreen(2);
            }
            catch (NumberFormatException e){
                JOptionPane optionPane = new JOptionPane("Could not log in", JOptionPane.ERROR_MESSAGE);
                JDialog dialog = optionPane.createDialog("Database error");
                dialog.setVisible(true);
            }
        }
        else if (!empty){
            JOptionPane optionPane = new JOptionPane("Invalid Login", JOptionPane.ERROR_MESSAGE);
            JDialog dialog = optionPane.createDialog("Error");
            dialog.setVisible(true);
        }
    }

    /**
     * Sets the active buttons depending on what the permission level of the current user is
     */
    private void setButtons(){
        ArrayList<Integer> index = new ArrayList<>();

        switch (currentRank) {
            case 1 -> Collections.addAll(index, 0);
            case 2 -> Collections.addAll(index, 0, 3);
            case 3 -> Collections.addAll(index, 0, 2, 3);
            case 4 -> Collections.addAll(index, 0, 1, 2, 3);
            case 5 -> Collections.addAll(index, 0, 1, 2, 3, 4);
        }

        for (int i = 0; i != splashButtons.size(); i++) {
            splashButtons.get(i).setVisible(false);
        }
        for (int i = 0; i != splashButtons.size(); i++){
            if (index.contains(i)){
                splashButtons.get(i).setVisible(true);
            }
        }
    }

    /**
     * Main actionPerformed method for all the main buttons
     * @param e The event
     */
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == loginSubmit){
            logIn();
        }
        else if (e.getSource() == loginGuestLogin){
            frame.setTitle("Splash");
            changeScreen(2);
        }
        else if (e.getSource() == loginQuit){
            System.exit(0);
        }
        else if (e.getSource() == splashEmployees){
            frame.setTitle("Employees");
            useData.startTimer();
            changeScreen(3);
        }
        else if (e.getSource() == splashMedia){
            frame.setTitle("Media");
            useData.startTimer();
            changeScreen(4);
        }
        else if (e.getSource() == splashDatabases){
            frame.setTitle("Databases");
            useData.startTimer();
            changeScreen(5);
        }
        else if (e.getSource() == splashPassword){
            frame.setTitle("Account Settings");
            changeScreen(6);
        }
        else if (e.getSource() == splashAccount){
            frame.setTitle("Employee Creator");
            changeScreen(7);
        }
        else if (e.getSource() == mediaOverdue){
            String text = mediaOverdue.getText();
            String[] states = {"Overdue", "Revert"};
            searchMedBox.setSelectedIndex(0);
            clearMedia();
            if(text.equals(states[0])) {
                mediaOverdue.setText(states[1]);
                filterOverdue();
            }
            else{
                mediaOverdue.setText(states[0]);
                buildMediaButtons(read.Media.mediaT);
            }
        }
    }
}
