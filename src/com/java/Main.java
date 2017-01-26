package com.java;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends Applet {
    private static final String INPUT = "Input ";
    private JPanel mainJPanel;
    private JButton exportFileButton;
    private ArrayList<Passport> passports = new ArrayList<>();

    @Override
    public void init() {
        setSize(530, 540);
        JPanel topButtonJPanel = initializeTopButtons();

        mainJPanel = new JPanel();
        mainJPanel.setLayout(new BoxLayout(mainJPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setViewportView(mainJPanel);
        scrollPane.setPreferredSize(new Dimension(500, 500));

        JPanel rootJPanel = new JPanel();
        rootJPanel.setLayout(new BoxLayout(rootJPanel, BoxLayout.Y_AXIS));
        rootJPanel.add(topButtonJPanel);
        rootJPanel.add(scrollPane);

        this.add(rootJPanel);
    }

    private JPanel initializeTopButtons() {
        JButton openWindowInputPassportDataButton = new JButton("Open a new window for entering the passport data");
        openWindowInputPassportDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openNewJFrameInputPassportData();
            }
        });

        JButton importFileButton = new JButton("Import file");
        importFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // empty
            }
        });

        exportFileButton = new JButton("Export file");
        exportFileButton.setEnabled(false);
        exportFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportPassportDataToFile();
            }
        });

        JPanel topButtonJPanel = new JPanel();
        topButtonJPanel.setLayout(new BoxLayout(topButtonJPanel, BoxLayout.X_AXIS));
        topButtonJPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        topButtonJPanel.add(openWindowInputPassportDataButton);
        topButtonJPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        topButtonJPanel.add(importFileButton);
        topButtonJPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        topButtonJPanel.add(exportFileButton);
        return topButtonJPanel;
    }

    private void exportPassportDataToFile() {
        if (passports.size() > 0) {
            File selectedFile = getSelectedFileFromFileChooser("Export data");
            if (selectedFile != null) {
                writeDataToFile(selectedFile);
            }
        }
    }

    private void openNewJFrameInputPassportData() {
        final JFrame frame = new JFrame("Input Passport Data");

        final ArrayList<JLabel> labels = initializeLabels();
        final ArrayList<JTextField> textFields = initializeTextFields();

        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleOnClickOkButton(labels, textFields, frame);
            }
        });
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        JPanel jPanelRoot = initializeJPanels(labels, textFields, new JButton[]{okButton, closeButton});

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(jPanelRoot);
        frame.pack();
        frame.setVisible(true);
    }

    private ArrayList<JLabel> initializeLabels() {
        ArrayList<JLabel> labels = new ArrayList<>();
        for (String labelNames : Passport.PASSPORT_FILED_NAMES) {
            labels.add(new JLabel(INPUT.concat(labelNames).concat(":")));
        }
        // label error must be last element in array
        final JLabel errorEmptyFields = new JLabel("Error! One or more fields are not filled!");
        errorEmptyFields.setVisible(false);
        errorEmptyFields.setForeground(Color.RED);
        labels.add(errorEmptyFields);
        return labels;
    }

    private ArrayList<JTextField> initializeTextFields() {
        final int columnsTextField = 40;
        ArrayList<JTextField> textFields = new ArrayList<>();
        textFields.add(new JTextField(columnsTextField));
        textFields.add(new JTextField(columnsTextField));
        textFields.add(new JTextField(columnsTextField));
        textFields.add(new JTextField(columnsTextField));
        textFields.add(new JTextField(columnsTextField));
        textFields.add(new JTextField(columnsTextField));
        textFields.add(new JTextField(columnsTextField));
        textFields.add(new JTextField(columnsTextField));
        return textFields;
    }

    private JPanel initializeJPanels(ArrayList<JLabel> labels, ArrayList<JTextField> textFields, JButton[] buttons) {
        JPanel jPanelLabelAndTextFields = new JPanel();
        jPanelLabelAndTextFields.setLayout(new BoxLayout(jPanelLabelAndTextFields, BoxLayout.Y_AXIS));

        int biggerArraySize = labels.size() >= textFields.size() ? labels.size() : textFields.size();
        for (int i = 0; i < biggerArraySize; i++) {
            if (i < labels.size()) {
                jPanelLabelAndTextFields.add(labels.get(i));
            }
            if (i < textFields.size()) {
                jPanelLabelAndTextFields.add(textFields.get(i));
            }
        }

        JPanel jPanelButtons = new JPanel();
        jPanelButtons.setLayout(new BoxLayout(jPanelButtons, BoxLayout.X_AXIS));
        for (JButton button : buttons) {
            jPanelButtons.add(button);

        }

        JPanel jPanelRootPassportWindow = new JPanel();
        jPanelRootPassportWindow.setLayout(new BoxLayout(jPanelRootPassportWindow, BoxLayout.Y_AXIS));
        jPanelRootPassportWindow.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanelRootPassportWindow.add(jPanelLabelAndTextFields);
        jPanelRootPassportWindow.add(jPanelButtons);

        return jPanelRootPassportWindow;
    }

    private void handleOnClickOkButton(ArrayList<JLabel> labels, ArrayList<JTextField> textFields, JFrame frame) {
        boolean isEmptyField = isEmptyFieldExist(labels, textFields, frame);
        if (!isEmptyField) {
            ArrayList<String> passportData = new ArrayList<>();
            for (JTextField field : textFields) {
                passportData.add(field.getText());
            }
            Passport passport = addAndGetNewPassport(passportData);
            drawTableWithPassportData(passport);
            showExportButton();
        }
    }

    private void showExportButton() {
        if (!exportFileButton.isEnabled()) {
            exportFileButton.setEnabled(true);
        }
    }

    private Passport addAndGetNewPassport(ArrayList<String> passportData) {
        Passport passport = new Passport(passportData);
        passports.add(passport);
        return passport;
    }

    private boolean isEmptyFieldExist(ArrayList<JLabel> labels, ArrayList<JTextField> textFields, JFrame frame) {
        JLabel errorEmptyLabel = labels.get(labels.size() - 1);
        boolean isEmptyField = false;
        for (JTextField field : textFields) {
            if (field.getText().isEmpty()) {
                errorEmptyLabel.setVisible(true);
                frame.pack();
                isEmptyField = true;
                break;
            }
        }
        if (!isEmptyField) {
            if (errorEmptyLabel.isVisible()) {
                errorEmptyLabel.setVisible(false);
                frame.pack();
            }
        }
        return isEmptyField;
    }

    private void drawTableWithPassportData(Passport passport) {
        TablePassportData tablePassportData = new TablePassportData(passport);
        mainJPanel.add(tablePassportData);
        mainJPanel.updateUI();
    }

    private File getSelectedFileFromFileChooser(String dialogTitle) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(dialogTitle);
        FileFilter filter = new FileNameExtensionFilter("Passport data (*.psdt)", "psdt");
        fileChooser.setFileFilter(filter);
        int returnVal = fileChooser.showOpenDialog(null);
        File selectedFile = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
        }

        return selectedFile;
    }

    private void writeDataToFile(File selectedFile) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(selectedFile));
            StringBuilder stringBuilder = new StringBuilder();
            for (Passport passport : passports) {
                for (int i = 0; i < passport.getPassportData().size(); i++) {
                    stringBuilder.append(passport.getPassportData().get(i));
                    if (i == passport.getPassportData().size() - 1) {
                        stringBuilder.append("\n");
                    } else {
                        stringBuilder.append(",");
                    }
                }
                writer.write(stringBuilder.toString());
                stringBuilder.delete(0, stringBuilder.length());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class TablePassportData extends JPanel {
        public TablePassportData(Passport passport) {
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            String[][] tableData = new String[passport.getPassportData().size()][passport.getPassportData().size()];
            for (int i = 0; i < tableData.length; i++) {
                for (int j = 0; j < 2; j++) {
                    // first column
                    if (j == 0) {
                        tableData[i][j] = Passport.PASSPORT_FILED_NAMES[i];
                    } else {
                        tableData[i][j] = passport.getPassportData().get(i);
                    }
                }
            }
            String[] columnNames = {"Labels", "Data"};
            JTable jTable = new JTable(tableData, columnNames);
            add(jTable);
        }
    }
}