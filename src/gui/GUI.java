package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import data.*;

/**
 * authors: jake love, nathan tabb ist 311
 **/
public class GUI implements ActionListener {

    // objects used in all GUIs
    private final JFrame frame = new JFrame();
    private final CardLayout cl = new CardLayout(0, 0);// objects used in MenuGUI
    private final JPanel mainMasterPanel = new JPanel();
    private final JLabel libraryName = new JLabel("Stonybrook Library");
    private final Color menuBlue = new Color(173, 216, 230);

    // objects used in LoginGUI
    private final JPanel loginMasterPanel = new JPanel();
    private final JPanel loginSecondaryPanel = new JPanel();
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
    private final JButton splashPatrons = new JButton("PATRONS");
    private final JButton splashVendors = new JButton("VENDORS");

    private final ArrayList<JButton> splashButtons = new ArrayList<>();
    private final ArrayList<ArrayList<JButton>> empButtons = new ArrayList<>();

    WriteData write;
    ReadFile read;

    // objects used in SearchGUI
    private final JPanel searchMasterPanel = new JPanel();
    private final JComboBox search1Box = new JComboBox();
    private final JComboBox search2Box = new JComboBox();
    private final ArrayList<String> filterDepartment = new ArrayList<>();
    private final ArrayList<String> filterSubject = new ArrayList<>();
    private final ArrayList<String> filterProject = new ArrayList<>();

    // objects used in DataGUI
    private final JPanel mediaMasterPanel = new JPanel();
    private final JPanel mediaSuperMasterPanel = new JPanel();
    private final JPanel employeeMasterPanel = new JPanel();
    private final JPanel employeeSuperMasterPanel = new JPanel();

    // gui attributes
    private int currentUser = 1;
    private int currentRank = 1;
    private ArrayList<String> empAtt = new ArrayList<>();
    private final String[] headings = {"ID", "Name", "Department", "Boss ID", "Project", "Subject", "Req. Materials",
                                 "Alloc. Budget", "Spent Budget", "Perm. Level", "Employed"};

    // index values
    private final int permissionIndex = 8;
    private final int employedIndex = 9;

    public GUI(WriteData write, ReadFile read){
        this.write = write;
        this.read = read;
        for (var entry : read.employee.employeeT.entrySet()) {
            entry.getValue().flipBudgets();
        }
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
        frame.setTitle("Login");
        frame.setResizable(false);

        frame.addWindowListener(onClose());

        buildMainPanel();
        buildLogin();
        buildSplash();
        buildSearch();
        buildEmployee();
        buildMedia();
        Collections.addAll(splashButtons, splashMedia, splashEmployees, splashDatabases, splashPatrons, splashVendors);
    }

    private WindowAdapter onClose(){
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowOpened(e);
                WriteData.writeEmp(read.employee.employeeT);
            }
        };
    }

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

    private void buildSplash() {
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

        splashEmployees.setVisible(false);
        splashVendors.setVisible(false);
        splashPatrons.setVisible(false);
        splashDatabases.setVisible(false);

        splashSecondaryPanel.add(splashPatrons);
        splashSecondaryPanel.add(splashVendors);
        splashSecondaryPanel.add(splashDatabases);
    }

    private void buildSearch(){
        employeeSuperMasterPanel.add(searchMasterPanel);
        searchMasterPanel.setPreferredSize(new Dimension(1920, 100));
        searchMasterPanel.setBackground(menuBlue);
        searchMasterPanel.add(search1Box);
        searchMasterPanel.add(search2Box);
    }

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
        int currentRow = 0;

        //------------------------------------------------------------------- action listeners
        addEmpHeadings();

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
            currentRow++;
        }
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
        int currentRow = 0;

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
            currentRow++;
        }
    }

    private void addEmpHeadings(){
        Color headingColor = new Color(150,205,225);
        for(int i = 0; i != headings.length; i++){
            JButton headingButton = new JButton(headings[i]);
            headingButton.setBackground(headingColor);
            employeeMasterPanel.add(headingButton);
        }
    }
    private void updateButtons(String filter, String category){
        int departmentIndex = 1;
        int projectIndex = 3;
        int subjectIndex = 4;
        int hGap = 25;
        int vGap = 25;
        int passwordIndex = 9;
        int currentRow = 0;

        employeeMasterPanel.removeAll();
        employeeMasterPanel.revalidate();
        employeeMasterPanel.repaint();

        HashMap<String, employeeData> filteredData = new HashMap<>();

        addEmpHeadings();
        ArrayList<JButton> tempButtons = new ArrayList<>();

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
            currentRow++;
        }
    }

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

    private boolean checkOutrank(String key){
        return(currentRank <= Integer.parseInt(read.employee.employeeT.get(key).getData().get(permissionIndex)));
    }

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

    private void changeRank(String key, int newRank){
        int permIndex = 9; // must make new one since arraylist is 9
        employeeData user = read.employee.employeeT.get(key);
        try {
            user.updateRank(newRank);
            for(int i = 0; i != empButtons.size(); i++){
                if(empButtons.get(i).get(0).getText().equals(key)){ // should have used a hashmap
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

    private void buildMedia(){
        //mediaMasterPanel.setBackground(menuBlue);
        mediaMasterPanel.setBackground(Color.PINK);
    }

    private void changeScreen(int screen) {
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

    private void buildMainPanel() {
        mainMasterPanel.setLayout(cl);
        mainMasterPanel.add(loginMasterPanel, "login");
        mainMasterPanel.add(splashMasterPanel, "splash");
        mainMasterPanel.add(employeeSuperMasterPanel, "employee");
        mainMasterPanel.add(mediaMasterPanel, "media");
        frame.add(mainMasterPanel);
        changeScreen(1);
    }

    private void switchTest()
    {
        for(int i = 1; i < 4; i++)
        {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
            changeScreen(i);
        }
    }

    private void logIn(){
        boolean valid = false;
        boolean empty = false;

        String user = loginUsernameField.getText();
        String password = loginPasswordField.getText();

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
            frame.setTitle("Splash");
            changeScreen(2);
        }
        else if (e.getSource() == loginQuit){
            System.exit(0);
        }
        else if (e.getSource() == splashEmployees){
            frame.setTitle("Employees");
            changeScreen(3);
        }
        else if (e.getSource() == splashMedia){
            frame.setTitle("Media");
            changeScreen(4);
        }

    }
}