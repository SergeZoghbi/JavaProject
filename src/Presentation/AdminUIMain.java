package Presentation;

import BusinessLayer.AdminLogic;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class AdminUIMain {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private AdminLogic adminLogic = new AdminLogic();
    private JFrame jFrame;

    private JPanel firstTwoChartsPanel;
    private JPanel thirdChartPanel;
    private DefaultPieDataset defaultPieDataset = null;
    private DefaultCategoryDataset defaultCategoryDataset = null;
    private XYDataset xyDataset = null;
    private JFrame editFrame;
    private JTable jTable;
    private Object[][] data = null;
    private String adminName = "";
    public static String filterId = "";

    public AdminUIMain() {
        adminLogic.fillUniverstiesArrayList();
        adminLogic.fillSchoolArrayList();
        adminLogic.fillFacultiesArrayList();
    }

    private void initializeFrame() {
        jFrame = new JFrame();
        jFrame.setSize(screenSize.width / 2, (int) (screenSize.height / 1.5));
        jFrame.setLayout(new BorderLayout());
        jFrame.setResizable(false);
    }

    private void initializeTopPanel(String adminName) {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(new Color(255, 255, 255));
        topPanel.setBorder(BorderFactory.createRaisedBevelBorder());

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout());

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(actionEvent -> {
            jFrame.setVisible(false);
            new LoginFrameMain().run();
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        rightPanel.add(logoutButton);
        rightPanel.add(exitButton);
        rightPanel.setBackground(new Color(255, 255, 255));
        topPanel.add(rightPanel, BorderLayout.EAST);

        JLabel adminNameLabel = new JLabel("Logged in as " + adminName);
        topPanel.add(adminNameLabel, BorderLayout.WEST);

        jFrame.add(topPanel, BorderLayout.NORTH);

    }

    private void InitializeDashboardPanel() {

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(2, 1, 0, 0));
        jPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        jPanel.setBackground(new Color(250, 250, 250, 250));


        firstTwoChartsPanel = new JPanel();
        firstTwoChartsPanel.setLayout(new GridLayout(1, 2, 0, 0));
        jPanel.add(firstTwoChartsPanel);

        thirdChartPanel = new JPanel();
        thirdChartPanel.setLayout(new GridLayout(1, 1, 0, 0));
        jPanel.add(thirdChartPanel);

        jFrame.add(jPanel, BorderLayout.CENTER);


        addPieChart();
        addBarChart();
        addLineChart();

    }

    private void addPieChart() {
        defaultPieDataset = adminLogic.createPieChartDataset();

        JFreeChart pieChart = ChartFactory.createPieChart(adminLogic.getPieChartName(), defaultPieDataset);
        pieChart.removeLegend();

        ChartPanel pieChartPanel = new ChartPanel(pieChart);

        firstTwoChartsPanel.add(pieChartPanel);

    }

    private void addBarChart() {

        defaultCategoryDataset = adminLogic.createBarChartDataset();
        JFreeChart barChart = ChartFactory.createBarChart(adminLogic.getBarChartName(),
                "Category",
                "Score",
                defaultCategoryDataset,
                PlotOrientation.VERTICAL,
                false, true, false);


        ChartPanel barChartPanel = new ChartPanel(barChart);


        firstTwoChartsPanel.add(barChartPanel);

    }

    private void addLineChart() {
        xyDataset = adminLogic.createLineChartDataset();
        final JFreeChart chart = ChartFactory.createXYLineChart(
                adminLogic.getLineChartName(),      // chart title
                "Day",                      // x axis label
                "Number of Entries",                      // y axis label
                xyDataset,                  // data
                PlotOrientation.VERTICAL,
                true,                     // include legend
                true,                     // tooltips
                false                     // urls
        );


        ChartPanel lineChartPanel = new ChartPanel(chart);

        thirdChartPanel.add(lineChartPanel);

    }

    private void addUserManagementButton() {

        JPanel lowerPanel = new JPanel();
        lowerPanel.setBorder(BorderFactory.createEmptyBorder());
        lowerPanel.setBackground(new Color(255, 255, 255));

        JButton editUser = new JButton("Manage Users");


        lowerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        lowerPanel.add(editUser);

        jFrame.add(lowerPanel, BorderLayout.SOUTH);

        editUser.addActionListener(actionEvent -> InitializeEditFrame());

    }

    private void InitializeEditFrame() {
        editFrame = new JFrame();
        editFrame.setLayout(new BorderLayout());
        editFrame.setSize(screenSize.width / 2, (int) (screenSize.height / 1.5));
        editFrame.setResizable(false);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());
        String[] columnsNames = adminLogic.getColumnNames();
        data = adminLogic.getTableData();
        TableModel tableModel = new DefaultTableModel(data, columnsNames);

        jTable = new JTable() {
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

                if (col == columnsNames.length - 1) {

                    jTable.setModel(new DefaultTableModel(data, columnsNames));

                    InitializeEditOrDeleteUserFrame(data[row][0].toString(), data[row][1].toString(), data[row][2].toString(), data[row][3].toString(), data[row][4].toString(), data[row][5].toString(), data[row][6].toString());


                }

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
            }
        });

        jTable.getTableHeader().setReorderingAllowed(false);


        JScrollPane jScrollPane = new JScrollPane(jTable);

        middlePanel.add(jScrollPane);


        JPanel topPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        JPanel topEditPanel = new JPanel();

        JButton searchUserButton = new JButton("Search");
        JButton addUserButton = new JButton("Add");
        JButton exitEditButton = new JButton("Exit");
        JButton backEditButton = new JButton("Back");
        topEditPanel.setLayout(new GridLayout(1, 4, 0, 0));
        topEditPanel.add(searchUserButton);
        topEditPanel.add(addUserButton);
        topEditPanel.add(backEditButton);
        topEditPanel.add(exitEditButton);

        JTextField searchArea = new JTextField();

        topPanel.add(searchArea);
        topPanel.add(topEditPanel);
        topPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3, true));


        searchUserButton.addActionListener(actionEvent -> {
            filterId = searchArea.getText();
                data = adminLogic.getFilteredUsers(searchArea.getText());
                jTable.setModel(new DefaultTableModel(data, columnsNames));
        });

        addUserButton.addActionListener(actionEvent -> InitializeAddUserFrame());

        exitEditButton.addActionListener(actionEvent -> System.exit(0));

        backEditButton.addActionListener(actionEvent -> {
            run(this.adminName);
            editFrame.setVisible(false);
        });


        middlePanel.add(topPanel, BorderLayout.NORTH);

        editFrame.add(middlePanel, BorderLayout.CENTER);


        editFrame.setVisible(true);
        jFrame.setVisible(false);

    }

    private void InitializeAddUserFrame() {

        AddOrEditDialog addDialog = new AddOrEditDialog();
        addDialog.setTitle("Add User");
        addDialog.setOptions(new String[]{"Add"});

        JLabel firstNameLabel = new JLabel("First Name ");
        JTextField firstNameTextArea = new JTextField();
        addDialog.addComponent(firstNameLabel);
        addDialog.addComponent(firstNameTextArea);

        JLabel lastNameLabel = new JLabel("Last Name ");
        JTextField lastNameTextArea = new JTextField();
        addDialog.addComponent(lastNameLabel);
        addDialog.addComponent(lastNameTextArea);

        JLabel typeLabel = new JLabel("Type ");
        String[] type = {"Student", "Conseiller"};
        JComboBox typeComboBox = new JComboBox(type);
        typeComboBox.setSelectedItem("None");
        addDialog.addComponent(typeLabel);
        addDialog.addComponent(typeComboBox);


        JLabel facultyLabel = new JLabel("Faculty ");
        String[] faculties = adminLogic.getFaculties();
        JComboBox facComboBox = new JComboBox(faculties);
        addDialog.addComponent(facultyLabel);
        addDialog.addComponent(facComboBox);


        JLabel exUniLabel = new JLabel("Old University ");
        String[] unies = adminLogic.getUniversities();
        JComboBox uniesComboBox = new JComboBox(unies);
        uniesComboBox.setSelectedItem("None");
        addDialog.addComponent(exUniLabel);
        addDialog.addComponent(uniesComboBox);


        JLabel exSchoolLabel = new JLabel("Old School ");
        String[] schools = adminLogic.getSchools();
        JComboBox schoolsComboBox = new JComboBox(schools);
        schoolsComboBox.setSelectedItem("None");
        addDialog.addComponent(exSchoolLabel);
        addDialog.addComponent(schoolsComboBox);


        final String[] typeChosen = {"Student"};
        typeComboBox.addItemListener(itemEvent -> {
            if (itemEvent.getItemSelectable().getSelectedObjects()[0].equals("Student")) {
                typeChosen[0] = "Student";
                uniesComboBox.setEnabled(true);
                schoolsComboBox.setEnabled(true);
            } else {
                typeChosen[0] = "Conseiller";
                uniesComboBox.setSelectedItem("None");
                schoolsComboBox.setSelectedItem("None");
                uniesComboBox.setEnabled(false);
                schoolsComboBox.setEnabled(false);
            }
        });

        final String[] facChosen = {faculties[0]};
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

        int actionChosen = addDialog.show();
        if (actionChosen == 0) {
            if (!facChosen[0].equals("None") && !facChosen[0].equals(" ")) {
                String isAdded = adminLogic.AddUser(firstNameTextArea.getText(), lastNameTextArea.getText(), typeChosen[0],
                        facChosen[0], oldUniChosen[0], oldSchoolChosen[0]);

                if (!isAdded.equals("-1")) {
                    new Thread(() -> {
                        data = adminLogic.getTableData();
                        jTable.setModel(new DefaultTableModel(data, adminLogic.getColumnNames()));
                    }).start();

                }
            }
        }
    }

    private void InitializeEditOrDeleteUserFrame(String uniId, String fn, String ln, String usertype, String fac, String olduni, String oldschool) {

        AddOrEditDialog editDialog = new AddOrEditDialog();
        editDialog.setTitle("Edit User");
        editDialog.setOptions(new String[]{"Reset Password", "Edit", "Delete"});

        JLabel uniIDLabel = new JLabel("Uni id ");
        JTextField uniIDTextField = new JTextField(uniId);
        uniIDTextField.setEditable(false);
        editDialog.addComponent(uniIDLabel);
        editDialog.addComponent(uniIDTextField);

        JLabel firstNameLabel = new JLabel("First Name ");
        JTextField firstNameTextArea = new JTextField(fn);
        editDialog.addComponent(firstNameLabel);
        editDialog.addComponent(firstNameTextArea);


        JLabel lastNameLabel = new JLabel("Last Name ");
        JTextField lastNameTextArea = new JTextField(ln);
        editDialog.addComponent(lastNameLabel);
        editDialog.addComponent(lastNameTextArea);


        JLabel typeLabel = new JLabel("Type ");
        String[] type = {"Student", "Conseiller"};
        JComboBox userTypeComboBox = new JComboBox(type);
        userTypeComboBox.setSelectedItem(usertype);
        editDialog.addComponent(typeLabel);
        editDialog.addComponent(userTypeComboBox);


        JLabel facultyLabel = new JLabel("Faculty ");
        String[] faculties = adminLogic.getFaculties();
        JComboBox facComboBox = new JComboBox(faculties);
        facComboBox.setSelectedItem(fac);
        editDialog.addComponent(facultyLabel);
        editDialog.addComponent(facComboBox);


        JLabel exUniLabel = new JLabel("Old University ");
        String[] unies = adminLogic.getUniversities();
        JComboBox uniesComboBox = new JComboBox(unies);
        uniesComboBox.setSelectedItem(olduni);
        editDialog.addComponent(exUniLabel);
        editDialog.addComponent(uniesComboBox);


        JLabel exSchoolLabel = new JLabel("Old School ");
        String[] schools = adminLogic.getSchools();
        JComboBox schoolsComboBox = new JComboBox(schools);
        schoolsComboBox.setSelectedItem(oldschool);
        editDialog.addComponent(exSchoolLabel);
        editDialog.addComponent(schoolsComboBox);

        final String[] typeChosen = {usertype};

        if (!typeChosen[0].equals("Student")) {
            schoolsComboBox.setEnabled(false);
            uniesComboBox.setEnabled(false);
        }

        userTypeComboBox.addItemListener(itemEvent -> {

            if (!itemEvent.getItemSelectable().getSelectedObjects()[0].equals("Student")) {
                typeChosen[0] = "Conseiller";
                schoolsComboBox.setEnabled(false);
                uniesComboBox.setEnabled(false);
            } else {
                typeChosen[0] = "Student";
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
        int actionChosen = editDialog.show();

        if (actionChosen == 1) {
            boolean ed = adminLogic.EditUser(uniIDTextField.getText(), firstNameTextArea.getText(), lastNameTextArea.getText(), typeChosen[0],
                    facChosen[0], oldUniChosen[0], oldSchoolChosen[0]);

            if (ed) {

                new Thread(() -> {
                    data = adminLogic.getTableData();
                    jTable.setModel(new DefaultTableModel(data, adminLogic.getColumnNames()));
                }).start();

            } else {
                System.out.println("Not edited");
            }
        } else if (actionChosen == 2) {
            boolean del = adminLogic.DeleteUser(uniIDTextField.getText());
            if (del) {

                new Thread(() -> {
                    data = adminLogic.getTableData();
                    jTable.setModel(new DefaultTableModel(data, adminLogic.getColumnNames()));
                }).start();

            } else {
                System.out.println("Not deleted");
            }
        } else if (actionChosen == 0) {
            adminLogic.ResetPassword(uniId);
        }
    }

    public void run(String adminName) {
        this.adminName = adminName;
        initializeFrame();
        InitializeDashboardPanel();
        addUserManagementButton();
        initializeTopPanel(adminName);

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }


}
