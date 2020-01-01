package Presentation;

import BusinessLayer.UserLogic;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static java.lang.Thread.sleep;


public class UserUIMain {

    private UserLogic userLogic;
    private JFrame jFrame;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JTable jTable;
    private String[] columnsNames = null;
    private Object[][] data = null;
    private String uid = "";
    private String fac_name = "";
    public static String filteredDate = "";


    public UserUIMain() {
        userLogic = new UserLogic();
    }

    public void setTableDataAndColumns() {
        this.columnsNames = userLogic.getColumnsName();
        this.data = userLogic.getTableData(this.fac_name);
    }

    private void InitializeJFrame() {
        jFrame = new JFrame();
        jFrame.setSize(screenSize.width / 2, (int) (screenSize.height / 1.5));
        jFrame.setLayout(new BorderLayout());
        jFrame.setResizable(false);
    }

    private void InitializeTopPanel(String uni_id, int priority) {

        JPanel topPanel = new JPanel();
        topPanel.setBorder(BorderFactory.createRaisedBevelBorder());

        JButton nameLabel = new JButton("Logged in as " + uni_id + "   ");
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, screenSize.width / 10));
        nameLabel.setBorderPainted(false);
        nameLabel.setFocusPainted(false);
        nameLabel.setContentAreaFilled(false);

        nameLabel.addActionListener(actionEvent -> {

            JFrame infoFrame = new JFrame();
            infoFrame.setLayout(new BorderLayout());
            infoFrame.setSize(screenSize.width / 6, screenSize.height / 6);
            infoFrame.setLocation(nameLabel.getLocation());


            JPanel centralPanel = new JPanel();
            centralPanel.setLayout(new GridLayout(4, 2));


            centralPanel.add(new JLabel("Old Password"));
            JPasswordField oldPasswordField = new JPasswordField();
            centralPanel.add(oldPasswordField);

            centralPanel.add(new JLabel("New Password"));
            JPasswordField newPasswordField = new JPasswordField();
            centralPanel.add(newPasswordField);


            centralPanel.add(new JLabel("Retype New Password"));
            JPasswordField newPasswordField2 = new JPasswordField();
            centralPanel.add(newPasswordField2);


            centralPanel.add(new Component() {
            });

            JPanel acceptPanel = new JPanel();
            acceptPanel.setLayout(new GridLayout(1, 2, 0, 0));

            JButton changeButton = new JButton("Reset");
            changeButton.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
            changeButton.addActionListener(actionEvent12 -> {

                if (userLogic.CharArrayToString(newPasswordField.getPassword()).equals(userLogic.CharArrayToString(newPasswordField2.getPassword())) && newPasswordField.getPassword().length != 0) {
                    userLogic.resetPassword(uni_id, oldPasswordField.getPassword(), newPasswordField.getPassword());
                    infoFrame.setVisible(false);
                } else {
                    new Thread(() -> {
                        newPasswordField2.setForeground(Color.RED);
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        newPasswordField2.setForeground(Color.BLACK);
                    }).start();
                }


            });

            JButton cancelButton = new JButton("Cancel");
            cancelButton.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
            cancelButton.addActionListener(actionEvent1 -> infoFrame.setVisible(false));


            acceptPanel.add(changeButton);
            acceptPanel.add(cancelButton);
            centralPanel.add(acceptPanel);

            infoFrame.add(centralPanel, BorderLayout.CENTER);
            infoFrame.setVisible(true);
        });


        JButton logoutButton = new JButton("Logout");
        JButton exitButton = new JButton("Exit");

        topPanel.setLayout(new BorderLayout());
        topPanel.add(nameLabel, BorderLayout.WEST);


        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(1, 1, 0, 0));


        JDateChooser filterButton = new JDateChooser();
        final int[] entered = {0};
        filterButton.addPropertyChangeListener(propertyChangeEvent -> {

            entered[0]++;
            if (entered[0] > 2) {

                DateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
                String date = dateFormat.format(propertyChangeEvent.getNewValue());
                filteredDate = date;
                    data = userLogic.filterData(uni_id, date, this.fac_name);

                    jTable.setModel(new DefaultTableModel(data, columnsNames));

            }

        });


        if (priority == 3) {
            nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, screenSize.width / 12));

            JButton addCirculaire = new JButton("Add Circulaire");

            searchPanel.add(addCirculaire);

            addCirculaire.addActionListener(actionEvent -> addCirculaireFrame());
        }


        searchPanel.add(filterButton);
        searchPanel.add(logoutButton);
        searchPanel.add(exitButton);


        topPanel.add(searchPanel, BorderLayout.CENTER);

        logoutButton.addActionListener(actionEvent -> {
            userLogic.Logout(uni_id);
            jFrame.setVisible(false);
            new LoginFrameMain().run();
        });
        exitButton.addActionListener(actionEvent -> {
            userLogic.Logout(uni_id);
            System.exit(0);
        });


        jFrame.add(topPanel, BorderLayout.NORTH);

    }

    private void addCirculaireFrame() {
        JFrame circFrame = new JFrame();
        circFrame.setLayout(new BorderLayout());
        circFrame.setLocationRelativeTo(jTable);
        circFrame.setSize(screenSize.width / 3, screenSize.height / 3);
        circFrame.setResizable(false);

        JPanel topCircPanel = new JPanel();
        topCircPanel.setLayout(new GridLayout(2, 1, 0, 0));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new GridLayout(1, 2, 0, 0));
        titlePanel.add(new JLabel("Title : "));
        JTextField titleField = new JTextField();
        titlePanel.add(titleField);
        titlePanel.setBackground(new Color(255, 255, 255));


        JPanel facPanel = new JPanel();
        facPanel.setLayout(new GridLayout(1, 2, 0, 0));
        facPanel.add(new JLabel("Faculty : "));
        JTextField facField = new JTextField(this.fac_name);
        facField.setEnabled(false);
        facPanel.add(facField);
        facPanel.setBackground(new Color(255, 255, 255));


        JPanel contenuPanel = new JPanel();
        contenuPanel.setLayout(new BorderLayout());
        JTextArea contenuCirculaireTextArea = new JTextArea();
        contenuPanel.add(contenuCirculaireTextArea, BorderLayout.CENTER);
        contenuPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        JButton addCirculaireButton = new JButton("Add");
        contenuPanel.add(addCirculaireButton, BorderLayout.SOUTH);

        addCirculaireButton.addActionListener(actionEvent -> {

            new Thread(() -> {

                userLogic.AddNewCirculaire(uid, titleField.getText(), this.fac_name, contenuCirculaireTextArea.getText());
                data = userLogic.getTableData(this.fac_name);
                jTable.setModel(new DefaultTableModel(data, columnsNames));

            }).start();

            circFrame.setVisible(false);

        });


        topCircPanel.add(titlePanel);
        topCircPanel.add(facPanel);
        circFrame.add(topCircPanel, BorderLayout.NORTH);
        circFrame.add(contenuPanel, BorderLayout.CENTER);

        circFrame.setVisible(true);

    }

    private void InitializeCirculaireTablePanel() {
        JPanel circulaireTablePanel = new JPanel();

        circulaireTablePanel.setLayout(new BorderLayout());


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

        jTable.getTableHeader().setReorderingAllowed(false);


        jTable.setModel(tableModel);

        // to make cells not editable
//           jTable.setDefaultEditor(Object.class , null);


        jTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

                int row = jTable.rowAtPoint(mouseEvent.getPoint());
                int col = jTable.columnAtPoint(mouseEvent.getPoint());

                if (col == columnsNames.length - 1) {
//                         jFrame.setVisible(false);


                    JFrame popupFrame = new JFrame();
                    popupFrame.setLayout(new BorderLayout());
                    popupFrame.setResizable(false);

                    popupFrame.setLocationRelativeTo(jTable);

                    JPanel topPanel = new JPanel(new GridLayout(2, 1, 0, 0));
                    JLabel titreLabel = new JLabel("Title : " + data[row][0].toString());
                    titreLabel.setFont(new Font("SansSerif", Font.ITALIC, 20));
                    JLabel facultyLabel = new JLabel("Date : " + data[row][1].toString());
                    facultyLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));


                    topPanel.add(titreLabel);
                    topPanel.add(facultyLabel);

                    topPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));


                    JPanel midPanel = new JPanel(new BorderLayout());
                    JTextArea jTextArea = new JTextArea();
                    String[] str = data[row][2].toString().split("");

                    for (int i = 0; i < str.length; i++) {
                        if (i % 120 == 0) {
                            jTextArea.append("\n");
                        }
                        jTextArea.append(str[i]);
                    }

                    jTextArea.setEditable(false);
                    midPanel.add(jTextArea, BorderLayout.NORTH);


                    JPanel lowerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                    JButton exitButton = new JButton("Close");
                    exitButton.addActionListener(actionEvent -> {
                        popupFrame.setVisible(false);
                        DefaultTableModel mod = new DefaultTableModel(data, columnsNames);
                        jTable.setModel(mod);
//                             jFrame.setVisible(true);
                    });
                    lowerPanel.add(exitButton);


                    popupFrame.add(topPanel, BorderLayout.NORTH);
                    topPanel.setBackground(new Color(255, 255, 255));
                    popupFrame.add(midPanel, BorderLayout.CENTER);
                    midPanel.setBackground(new Color(255, 255, 255));
                    popupFrame.add(lowerPanel, BorderLayout.SOUTH);
                    lowerPanel.setBackground(new Color(255, 255, 255));

                    popupFrame.setSize(screenSize.width / 2, (int) (screenSize.height / 1.5));

//                         popupFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                    popupFrame.setVisible(true);


                    jTable.setModel(new DefaultTableModel(data, columnsNames));

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


        JScrollPane jScrollPane = new JScrollPane(jTable);

        circulaireTablePanel.add(jScrollPane);

        jFrame.add(circulaireTablePanel, BorderLayout.CENTER);
    }

    public void run(String uni_id, int priority) {
        this.uid = uni_id;
        this.fac_name = userLogic.getFacName(this.uid);
        setTableDataAndColumns();
        InitializeJFrame();
        InitializeTopPanel(this.uid, priority);
        InitializeCirculaireTablePanel();

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }

}
