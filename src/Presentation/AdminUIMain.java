package Presentation;

import BusinessLayer.AdminLogic;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.*;
import java.awt.event.*;

import static java.lang.Thread.sleep;


public class AdminUIMain {

    private AdminLogic adminLogic;

    public AdminUIMain(){
        adminLogic = new AdminLogic();
    }

   private JFrame jFrame;

    private JPanel firstTwoChartsPanel;
   private JPanel thirdChartPanel;

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


    private void initializeFrame(){
        jFrame = new JFrame();
        jFrame.setSize(screenSize.width/2, (int) (screenSize.height/1.5));
        jFrame.setLayout(new BorderLayout());
        jFrame.setResizable(false);
}


    private void initializeTopPanel(String adminName){
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(new Color(255,255,255));
        topPanel.setBorder(BorderFactory.createRaisedBevelBorder());

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout());

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(actionEvent -> {
            jFrame.setVisible(false);
            adminLogic.Logout(adminName);
            new LoginFrameMain().run();
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                adminLogic.Logout(adminName);
                System.exit(0);
            }
        });

        rightPanel.add(logoutButton);
        rightPanel.add(exitButton);
        rightPanel.setBackground(new Color(255,255,255));
        topPanel.add(rightPanel, BorderLayout.EAST);

        JLabel adminNameLabel = new JLabel("Logged in as " + adminName);
        topPanel.add(adminNameLabel, BorderLayout.WEST);

        jFrame.add(topPanel, BorderLayout.NORTH);

    }





    private void InitializeDashboardPanel(){

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(2,1,0,0));
        jPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        jPanel.setBackground(new Color(250,250,250 , 250 ));


        firstTwoChartsPanel = new JPanel();
        firstTwoChartsPanel.setLayout(new GridLayout(1,2,0,0));
        jPanel.add(firstTwoChartsPanel);

        thirdChartPanel = new JPanel();
        thirdChartPanel.setLayout(new GridLayout(1,1,0,0));
        jPanel.add(thirdChartPanel);

        jFrame.add(jPanel, BorderLayout.CENTER);


        addPieChart();
        addBarChart();
        addLineChart();

    }

    private void addPieChart() {

        JFreeChart pieChart = ChartFactory.createPieChart(adminLogic.getPieChartName() , adminLogic.createPieChartDataset());
        pieChart.removeLegend();

        ChartPanel pieChartPanel = new ChartPanel(pieChart);

        firstTwoChartsPanel.add(pieChartPanel);

    }

    private void addBarChart() {

        JFreeChart barChart = ChartFactory.createBarChart(adminLogic.getBarChartName(),
                "Category",
                "Score",
                adminLogic.createBarChartDataset(),
                PlotOrientation.VERTICAL,
                false, true, false);


        ChartPanel barChartPanel = new ChartPanel(barChart);


        firstTwoChartsPanel.add(barChartPanel);

    }

    private void addLineChart(){

        final JFreeChart chart = ChartFactory.createXYLineChart(
                adminLogic.getLineChartName(),      // chart title
                "Day",                      // x axis label
                "Number of Entries",                      // y axis label
                adminLogic.createLineChartDataset(),                  // data
                PlotOrientation.VERTICAL,
                true,                     // include legend
                true,                     // tooltips
                false                     // urls
        );


        ChartPanel lineChartPanel = new ChartPanel(chart);

        thirdChartPanel.add(lineChartPanel);

    }





    private void addUserManagementButton(){

        JPanel lowerPanel = new JPanel();
        lowerPanel.setBorder(BorderFactory.createEmptyBorder());
        lowerPanel.setBackground(new Color(255,255,255));

        JButton editUser = new JButton("Manage Users");


        lowerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        lowerPanel.add(editUser);

        jFrame.add(lowerPanel, BorderLayout.SOUTH);

        editUser.addActionListener(actionEvent -> InitializeEditFrame());

    }








    private JFrame editFrame;
    private JTable jTable;

    private Object[][] data = null;


    private void InitializeEditFrame(){
        editFrame = new JFrame();
        editFrame.setLayout(new BorderLayout());
        editFrame.setSize(screenSize.width / 2 , (int) (screenSize.height/1.5));
        editFrame.setResizable(false);


        JPanel middlePanel = new JPanel();

        middlePanel.setLayout(new BorderLayout());


        String[] columnsNames = adminLogic.getColumnNames();
        data = adminLogic.getTableData();
        TableModel tableModel = new DefaultTableModel(data, columnsNames);

        jTable = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == columnsNames.length - 1;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                if (column == columnsNames.length - 1) {
                    return Boolean.class;
                }
                return String.class;
            }
        };


        jTable.setModel(tableModel);


        jTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                int row = jTable.rowAtPoint(mouseEvent.getPoint());
                int col = jTable.columnAtPoint(mouseEvent.getPoint());

                if(col == columnsNames.length - 1){

                        jTable.setModel(new DefaultTableModel(data , columnsNames));


                        InitializeEditOrDeleteUserFrame(data[row][0].toString() , data[row][1].toString() , data[row][2].toString() , data[row][3].toString() , data[row][4].toString() , data[row][5].toString() , data[row][6].toString());

//                        jTable.setModel(new DefaultTableModel(data,columnsNames));


                }

            }
            @Override
            public void mousePressed(MouseEvent mouseEvent) { }
            @Override
            public void mouseReleased(MouseEvent mouseEvent) { }
            @Override
            public void mouseEntered(MouseEvent mouseEvent) { }
            @Override
            public void mouseExited(MouseEvent mouseEvent) { }
        });

        jTable.getTableHeader().setReorderingAllowed(false);


        JScrollPane jScrollPane = new JScrollPane(jTable);

        middlePanel.add(jScrollPane);


        JPanel topPanel = new JPanel(new GridLayout(1,2,0,0));
        JPanel topEditPanel = new JPanel();

        JButton searchUserButton = new JButton("Search");
        JButton addUserButton = new JButton("Add");
        JButton exitEditButton = new JButton("Exit");
        JButton backEditButton = new JButton("Back");
        topEditPanel.setLayout(new GridLayout(1,4,0,0));
        topEditPanel.add(searchUserButton);
        topEditPanel.add(addUserButton);
        topEditPanel.add(backEditButton);
        topEditPanel.add(exitEditButton);

        JTextField searchArea = new JTextField();

        topPanel.add(searchArea);
        topPanel.add(topEditPanel);
        topPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY , 3 , true));


        searchUserButton.addActionListener(actionEvent -> {

            new Thread(() -> {
                data = adminLogic.getFilteredUsers(searchArea.getText());
                jTable.setModel(new DefaultTableModel(data,columnsNames));
            }).start();

        });

        addUserButton.addActionListener(actionEvent -> InitializeAddUserFrame());

        exitEditButton.addActionListener(actionEvent -> System.exit(0));

        backEditButton.addActionListener(actionEvent -> { jFrame.setVisible(true); editFrame.setVisible(false); });



        middlePanel.add(topPanel, BorderLayout.NORTH);

        editFrame.add(middlePanel, BorderLayout.CENTER);


        editFrame.setVisible(true);
        jFrame.setVisible(false);

    }


    private void InitializeAddUserFrame(){

        JFrame popupFrame = new JFrame();
        popupFrame.setLayout(new BorderLayout());
        popupFrame.setSize(screenSize.width / 3,screenSize.height / 3);
        popupFrame.setResizable(false);
//        popupFrame.setMaximumSize(new Dimension(screenSize.width / 3,screenSize.height / 3));
//        popupFrame.setMinimumSize(new Dimension(screenSize.width / 3,screenSize.height / 3));
//        popupFrame.setLocationRelativeTo(addUser);

        JPanel popupPanel = new JPanel();
        popupPanel.setLayout(new GridLayout(6,2,10,10));


        JLabel firstNameLabel = new JLabel("First Name ");
        JTextField firstNameTextArea = new JTextField();
        popupPanel.add(firstNameLabel);
        popupPanel.add(firstNameTextArea);


        JLabel lastNameLabel = new JLabel("Last Name ");
        JTextField lastNameTextArea = new JTextField();
        popupPanel.add(lastNameLabel);
        popupPanel.add(lastNameTextArea);



        JLabel typeLabel = new JLabel("Type ");
        String[] type = {"student" , "conseiller"};
        JComboBox typeComboBox = new JComboBox(type);
        popupPanel.add(typeLabel);
        popupPanel.add(typeComboBox);


        JLabel facultyLabel = new JLabel("Faculty ");
        String[] faculties = adminLogic.getFaculties("None");
        JComboBox facComboBox = new JComboBox(faculties);
        popupPanel.add(facultyLabel);
        popupPanel.add(facComboBox);



        JLabel exUniLabel = new JLabel("Old University ");
        String[] unies = adminLogic.getUniversities("None");
        JComboBox uniesComboBox = new JComboBox(unies);
        popupPanel.add(exUniLabel);
        popupPanel.add(uniesComboBox);


        JLabel exSchoolLabel = new JLabel("Old School ");
        String[] schools = adminLogic.getSchools("None");
        JComboBox schoolsComboBox = new JComboBox(schools);
        popupPanel.add(exSchoolLabel);
        popupPanel.add(schoolsComboBox);


        JPanel popupLowerPanel = new JPanel();
        popupLowerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton submitButton = new JButton("Submit");
        popupLowerPanel.add(submitButton);

        JButton cancelButton = new JButton("Cancel");
        popupLowerPanel.add(cancelButton);

        popupFrame.add(popupPanel);
        popupFrame.add(popupLowerPanel, BorderLayout.SOUTH);
//        popupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        popupFrame.setVisible(true);


        final String[] typeChosen = {"student"};
        typeComboBox.addItemListener(itemEvent -> {
            if(itemEvent.getItemSelectable().getSelectedObjects()[0].equals("student")){
                typeChosen[0] = "student";
                uniesComboBox.setEnabled(true);
                schoolsComboBox.setEnabled(true);
            }else{
                typeChosen[0] = "conseiller";
                uniesComboBox.setEnabled(false);
                schoolsComboBox.setEnabled(false);
            }
        });

        final String[] facChosen = {"None"};
        facComboBox.addItemListener(itemEvent -> {
            facChosen[0] = itemEvent.getItem().toString();
        });

        final String[] oldUniChosen = {"None"};
        uniesComboBox.addItemListener(itemEvent -> {
            oldUniChosen[0] = itemEvent.getItem().toString();
        });

        final String[] oldSchoolChosen = {"None"};
        schoolsComboBox.addItemListener(itemEvent -> {
            oldSchoolChosen[0] = itemEvent.getItem().toString();
        });


        submitButton.addActionListener(actionEvent -> {
            if(!facChosen[0].equals("None") && !facChosen[0].equals(" ")) {

            int isAdded =  adminLogic.AddUser(firstNameTextArea.getText(),lastNameTextArea.getText(),typeChosen[0],
                    facChosen[0], oldUniChosen[0],oldSchoolChosen[0]);
            if (isAdded != -1) {

                new Thread(() -> {
                    data = adminLogic.getTableData();
                    jTable.setModel(new DefaultTableModel(data, adminLogic.getColumnNames()));
                    popupFrame.setVisible(false);
                }).start();

            } else {
                popupFrame.setForeground(Color.RED);

            }
            }
        });

        cancelButton.addActionListener(actionEvent -> popupFrame.setVisible(false));

    }

    private void InitializeEditOrDeleteUserFrame(String uniId , String fn , String ln , String usertype , String fac , String olduni , String oldschool){
        editFrame.setVisible(false);
        JFrame popupFrame = new JFrame();
        popupFrame.setLayout(new BorderLayout());
        popupFrame.setSize(screenSize.width / 3,screenSize.height / 3);
        popupFrame.setResizable(false);
        popupFrame.setVisible(true);
//        popupFrame.setMaximumSize(new Dimension(screenSize.width / 3,screenSize.height / 3));
//        popupFrame.setMinimumSize(new Dimension(screenSize.width / 3,screenSize.height / 3));
//        popupFrame.setLocationRelativeTo(addUser);

        JPanel popupPanel = new JPanel();
        popupPanel.setLayout(new GridLayout(7,2,10,10));


        JLabel uniIDLabel = new JLabel("Uni id ");
        JTextField uniIDTextField = new JTextField(uniId);
        uniIDTextField.setEditable(false);
        popupPanel.add(uniIDLabel);
        popupPanel.add(uniIDTextField);

        JLabel firstNameLabel = new JLabel("First Name ");
        JTextField firstNameTextArea = new JTextField(fn);
        popupPanel.add(firstNameLabel);
        popupPanel.add(firstNameTextArea);


        JLabel lastNameLabel = new JLabel("Last Name ");
        JTextField lastNameTextArea = new JTextField(ln);
        popupPanel.add(lastNameLabel);
        popupPanel.add(lastNameTextArea);



        JLabel typeLabel = new JLabel("Type ");
        String[] type = { usertype ,"student" , "conseiller"};
        JComboBox userTypeComboBox = new JComboBox(type);
        popupPanel.add(typeLabel);
        popupPanel.add(userTypeComboBox);


        JLabel facultyLabel = new JLabel("Faculty ");
//        JTextField facultyTextField = new JTextField(fac);
//        popupPanel.add(facultyLabel);
//        popupPanel.add(facultyTextField);
        String[] faculties = adminLogic.getFaculties(fac);
        JComboBox facComboBox = new JComboBox(faculties);
        popupPanel.add(facultyLabel);
        popupPanel.add(facComboBox);



        JLabel exUniLabel = new JLabel("Old University ");
//        JTextField exUniTextArea = new JTextField(olduni);
//        popupPanel.add(exUniLabel);
//        popupPanel.add(exUniTextArea);
        String[] unies = adminLogic.getUniversities(olduni);
        JComboBox uniesComboBox = new JComboBox(unies);
        popupPanel.add(exUniLabel);
        popupPanel.add(uniesComboBox);


        JLabel exSchoolLabel = new JLabel("Old School ");
//        JTextField exSchoolTextArea = new JTextField(oldschool);
//        popupPanel.add(exSchoolLabel);
//        popupPanel.add(exSchoolTextArea);
        String[] schools = adminLogic.getSchools(oldschool);
        JComboBox schoolsComboBox = new JComboBox(schools);
        popupPanel.add(exSchoolLabel);
        popupPanel.add(schoolsComboBox);


        JPanel popupLowerPanel = new JPanel();
        popupLowerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton resetPassword = new JButton("Reset Password");
        popupLowerPanel.add(resetPassword);

        JButton editButton = new JButton("Edit");
        popupLowerPanel.add(editButton);

        JButton deleteButton = new JButton("Delete");
        popupLowerPanel.add(deleteButton);

        JButton cancelButton = new JButton("Cancel");
        popupLowerPanel.add(cancelButton);


        popupFrame.add(popupPanel);
        popupFrame.add(popupLowerPanel , BorderLayout.SOUTH);
//        popupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        popupFrame.setVisible(true);


        final String[] typeChosen = {usertype};
//        System.out.println(usertype);

        if(!typeChosen[0].equals("student")){
            schoolsComboBox.setEnabled(false);
            uniesComboBox.setEnabled(false);
        }

        userTypeComboBox.addItemListener(itemEvent -> {

            if(!itemEvent.getItemSelectable().getSelectedObjects()[0].equals("student")){
                typeChosen[0] = "conseiller";
                schoolsComboBox.setEnabled(false);
                uniesComboBox.setEnabled(false);
            }else{
                typeChosen[0] = "student";
                uniesComboBox.setEnabled(true);
                schoolsComboBox.setEnabled(true);
            }

        });

        final String[] facChosen = {fac};
        facComboBox.addItemListener(itemEvent -> {
            facChosen[0] = itemEvent.getItem().toString();
        });

        final String[] oldUniChosen = {olduni};
        uniesComboBox.addItemListener(itemEvent -> {
            oldUniChosen[0] = itemEvent.getItem().toString();
        });

        final String[] oldSchoolChosen = {oldschool};
        schoolsComboBox.addItemListener(itemEvent -> {
            oldSchoolChosen[0] = itemEvent.getItem().toString();
        });

        editButton.addActionListener(actionEvent -> {

            Boolean ed = adminLogic.EditUser(uniIDTextField.getText(),firstNameTextArea.getText(),lastNameTextArea.getText(),typeChosen[0],
                    facChosen[0], oldUniChosen[0],oldSchoolChosen[0]);

            if (ed){

                new Thread(() -> {
                    data = adminLogic.getTableData();
                    jTable.setModel(new DefaultTableModel(data, adminLogic.getColumnNames()));
                }).start();


                popupFrame.setVisible(false);
                editFrame.setVisible(true);
            } else {
                System.out.println("Not edited");
            }
        });

        deleteButton.addActionListener(actionEvent -> {
            Boolean del = adminLogic.DeleteUser(uniIDTextField.getText());

            if (del){

                new Thread(() -> {
                    data = adminLogic.getTableData();
                    jTable.setModel(new DefaultTableModel(data, adminLogic.getColumnNames()));
                }).start();


                popupFrame.setVisible(false);
                editFrame.setVisible(true);
            } else {
                System.out.println("Not deleted");
            }
        });

        cancelButton.addActionListener(actionEvent -> {
            popupFrame.setVisible(false);
            editFrame.setVisible(true);
        });

        resetPassword.addActionListener(actionEvent -> {
            adminLogic.ResetPassword(uniId);
            popupFrame.setVisible(true);
        });

        popupFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


    }



    public void run(String adminName){
        initializeFrame();
        InitializeDashboardPanel();
        addUserManagementButton();
        initializeTopPanel(adminName);

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }



}
