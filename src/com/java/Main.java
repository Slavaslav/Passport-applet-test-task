package com.java;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends Applet {
    private static final String PASSPORT_NUMBER_IS_NOT_UNIQUE = "Passport number is not unique!";
    private static final String COMMA = ",";
    private static final String PASSPORT_DATA_PSDT = "Passport data (*.psdt)";
    private static final String PSDT = "psdt";
    private static final String ERROR_ONE_OR_MORE_FIELDS_ARE_NOT_FILLED = "Error! One or more fields are not filled!";
    private static final String INPUT_PASSPORT_DATA = "Input Passport Data";
    private static final String OK = "Ok";
    private static final String CLOSE = "Close";
    private static final String EXPORT_DATA = "Export data";
    private static final String IMPORT_DATA = "Import data";
    private static final String EXPORT_FILE = "Export file";
    private static final String IMPORT_FILE = "Import file";
    private static final String OPEN_A_NEW_WINDOW_FOR_ENTERING_THE_PASSPORT_DATA = "Open a new window for entering the passport data";
    private final ArrayList<Passport> passports = new ArrayList<>();
    private JPanel mainJPanel;
    private JButton exportFileButton;
    private JLabel errorJFrameField;
    private JFrame frameInputPassportData;

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
        JButton openWindowInputPassportDataButton = new JButton(OPEN_A_NEW_WINDOW_FOR_ENTERING_THE_PASSPORT_DATA);
        openWindowInputPassportDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openNewJFrameInputPassportData();
            }
        });

        JButton importFileButton = new JButton(IMPORT_FILE);
        importFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importPassportDataFromFile();
            }
        });

        exportFileButton = new JButton(EXPORT_FILE);
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

    private void importPassportDataFromFile() {
        File selectedFile = getSelectedFileFromFileChooser(IMPORT_DATA);
        if (selectedFile != null) {
            hideExportButton();
            clearOldPassportData();
            readAndHandleDataFromFile(selectedFile);
        }
    }

    private void clearOldPassportData() {
        if (passports.size() > 0) {
            passports.clear();
        }
        mainJPanel.removeAll();
        mainJPanel.updateUI();
    }

    private void readAndHandleDataFromFile(File selectedFile) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(selectedFile));
            String line;
            while ((line = reader.readLine()) != null) {
                handlePassportDataFromFile(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handlePassportDataFromFile(String line) {
        String[] passportDataSplit = line.split(COMMA);
        ArrayList<String> passportData = new ArrayList<>(Arrays.asList(passportDataSplit));
        handlePassportDataToShow(passportData);
    }

    private void handlePassportDataToShow(ArrayList<String> passportData) {
        boolean isPassportUnique = isPassportNumberUnique(passportData);
        if (isPassportUnique) {
            Passport passport = addAndGetNewPassport(passportData);
            drawTableWithPassportData(passport);
            showExportButton();
        }
    }

    private boolean isPassportNumberUnique(ArrayList<String> passportData) {
        String passportNo = passportData.get(0);
        boolean isPassportUnique = true;
        for (Passport passport : passports) {
            if (passport.getPassportData().get(0).equals(passportNo)) {
                isPassportUnique = false;
                showErrorJFrameField(PASSPORT_NUMBER_IS_NOT_UNIQUE);
                break;
            }
        }
        if (isPassportUnique) {
            hideErrorJFrameField();
        }
        return isPassportUnique;
    }

    private void showErrorJFrameField(String textError) {
        errorJFrameField.setText(textError);
        errorJFrameField.setVisible(true);
        frameInputPassportData.pack();
    }

    private void hideErrorJFrameField() {
        if (errorJFrameField != null) {
            errorJFrameField.setVisible(false);
            frameInputPassportData.pack();
        }
    }

    private void exportPassportDataToFile() {
        if (passports.size() > 0) {
            File selectedFile = getSelectedFileFromFileChooser(EXPORT_DATA);
            if (selectedFile != null) {
                writeDataToFile(selectedFile);
            }
        }
    }

    private void openNewJFrameInputPassportData() {
        frameInputPassportData = new JFrame(INPUT_PASSPORT_DATA);

        final ArrayList<JLabel> labels = initializeLabels();
        final ArrayList<JTextField> textFields = initializeTextFields();

        JButton okButton = new JButton(OK);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleOnClickOkButton(textFields);
            }
        });
        JButton closeButton = new JButton(CLOSE);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameInputPassportData.dispose();
            }
        });

        JPanel jPanelRoot = initializeJPanels(labels, textFields, new JButton[]{okButton, closeButton});

        frameInputPassportData.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameInputPassportData.add(jPanelRoot);
        frameInputPassportData.pack();
        frameInputPassportData.setVisible(true);
    }

    private ArrayList<JLabel> initializeLabels() {
        ArrayList<JLabel> labels = new ArrayList<>();
        for (String labelNames : Passport.PASSPORT_FILED_NAMES) {
            labels.add(new JLabel(Passport.INPUT.concat(labelNames).concat(Passport.DOTS)));
        }
        errorJFrameField = new JLabel();
        errorJFrameField.setVisible(false);
        errorJFrameField.setForeground(Color.RED);
        labels.add(errorJFrameField);
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

    private void handleOnClickOkButton(ArrayList<JTextField> textFields) {
        boolean isEmptyField = isEmptyFieldExist(textFields);
        if (!isEmptyField) {
            ArrayList<String> passportData = new ArrayList<>();
            for (JTextField field : textFields) {
                passportData.add(field.getText());
            }
            handlePassportDataToShow(passportData);
        }
    }

    private void showExportButton() {
        if (!exportFileButton.isEnabled()) {
            exportFileButton.setEnabled(true);
        }
    }

    private void hideExportButton() {
        if (exportFileButton.isEnabled()) {
            exportFileButton.setEnabled(false);
        }
    }

    private Passport addAndGetNewPassport(List<String> passportData) {
        Passport passport = new Passport(passportData);
        passports.add(passport);
        return passport;
    }

    private boolean isEmptyFieldExist(ArrayList<JTextField> textFields) {
        boolean isEmptyField = false;
        for (JTextField field : textFields) {
            if (field.getText().isEmpty()) {
                isEmptyField = true;
                showErrorJFrameField(ERROR_ONE_OR_MORE_FIELDS_ARE_NOT_FILLED);
                break;
            }
        }
        if (!isEmptyField) {
            if (errorJFrameField.isVisible()) {
                hideErrorJFrameField();
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
        FileFilter filter = new FileNameExtensionFilter(PASSPORT_DATA_PSDT, PSDT);
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
                        stringBuilder.append(COMMA);
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
            String[] columnNames = {"", ""};
            JTable jTable = new JTable(tableData, columnNames);
            add(jTable);
        }
    }
}