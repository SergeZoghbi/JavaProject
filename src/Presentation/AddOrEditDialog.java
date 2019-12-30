package Presentation;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AddOrEditDialog {
    private List<JComponent> components;

    private String title;
    private int messageType;
    private String[] options;
    private int optionIndex;

    public AddOrEditDialog() {
        components = new ArrayList<>();
        setMessageType(JOptionPane.PLAIN_MESSAGE);
        setOptionSelection(0);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public void addComponent(JComponent component) {
        components.add(component);
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public void setOptionSelection(int optionIndex) {
        this.optionIndex = optionIndex;
    }

    public int show() {
        int optionType = JOptionPane.OK_CANCEL_OPTION;
        Object optionSelection = null;

        if (options.length != 0) {
            optionSelection = options[optionIndex];
        }

        return JOptionPane.showOptionDialog(null,
                components.toArray(), title, optionType, messageType, null,
                options, optionSelection);
    }

}