package Presentation;

import BusinessLayer.LoginFrameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


import static java.lang.Thread.sleep;

public class LoginFrameMain {

    private LoginFrameLogic loginFrameLogic;

    public LoginFrameMain() {
        loginFrameLogic = new LoginFrameLogic();
    }

    private JFrame jFrame;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


    private void InitializeFrame() {
        jFrame = new JFrame();
        jFrame.setLayout(new GridLayout(4, 1, 0, 0));
        jFrame.setSize(screenSize.width / 2, screenSize.height / 2);
        jFrame.setResizable(false);

    }

    private void InitializeTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        String textMessage = "Welcome to Antonine University";
        JLabel topPanelLabel = new JLabel(textMessage);
        topPanelLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        topPanelLabel.setForeground(Color.BLUE);
        topPanel.add(topPanelLabel);
        jFrame.add(topPanel, BorderLayout.NORTH);
    }


    private void InitializeLoginPanel() {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2, 0, 0));
        loginPanel.setSize(jFrame.getWidth() / 4, jFrame.getHeight() / 4);


        JLabel nameLabel = new JLabel("User ID");
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, (int) (jFrame.getWidth() / 3.5), 0, 0));
        loginPanel.add(nameLabel);

        JTextField nameField = new JTextField();
        JPanel nameFieldPanel = new JPanel();
        nameFieldPanel.setLayout(new GridLayout(1, 2, 0, 0));
        nameFieldPanel.add(nameField);
        nameFieldPanel.add(new Component() {
        });
        loginPanel.add(nameFieldPanel);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBorder(BorderFactory.createEmptyBorder(0, (int) (jFrame.getWidth() / 3.5), 0, 0));
        loginPanel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        JPanel passwordFieldPanel = new JPanel();
        passwordFieldPanel.setLayout(new GridLayout(1, 2, 0, 0));
        passwordFieldPanel.add(passwordField);
        passwordFieldPanel.add(new Component() {
        });
        loginPanel.add(passwordFieldPanel);

        JButton submitButton = new JButton("Submit");
        loginPanel.add(new Component() {
        });
        JPanel submitButtonPanel = new JPanel();
        submitButtonPanel.setLayout(new GridLayout(1, 2, 0, 0));
        submitButtonPanel.add(submitButton);
        submitButtonPanel.add(new Component() {
        });
        loginPanel.add(submitButtonPanel);


        submitButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

                int authenticationResponse = loginFrameLogic.CheckAuthentication(nameField.getText(), passwordField.getPassword());

                if (authenticationResponse == 1) {
                    new AdminUIMain().run(nameField.getText());
                    jFrame.setVisible(false);
                } else if (authenticationResponse == -1) {
                    new Thread(() -> {
                        nameLabel.setForeground(Color.RED);
                        nameField.setForeground(Color.RED);
                        passwordLabel.setForeground(Color.RED);
                        passwordField.setForeground(Color.RED);
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        nameLabel.setForeground(Color.BLACK);
                        nameField.setForeground(Color.BLACK);
                        passwordLabel.setForeground(Color.BLACK);
                        passwordField.setForeground(Color.BLACK);
                    }).start();

                } else {
                    new UserUIMain().run(nameField.getText(), authenticationResponse);
                    jFrame.setVisible(false);
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


        jFrame.add(loginPanel);
        jFrame.add(new Component() {
        });


        JPanel jokePanel = new JPanel();
        jokePanel.setLayout(new GridLayout(2, 1, 0, 0));
        JButton fetchQuote = new JButton("Fetch Quote");
        JLabel quoteLabel = new JLabel();

        fetchQuote.addActionListener(actionEvent ->
                new Thread(() -> {
                    quoteLabel.setText(loginFrameLogic.readApi());
                }).start());

        jokePanel.add(quoteLabel);
        jokePanel.add(fetchQuote);
        jFrame.add(jokePanel);


    }


    public void run() {
        InitializeFrame();
        InitializeTopPanel();
        InitializeLoginPanel();


        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }


    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        LoginFrameMain x = new LoginFrameMain();
        x.run();
    }

}
