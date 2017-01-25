package com.java;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends Applet {

    @Override
    public void init() {
        Button openWindowInputPassportData = new Button("Open a new window for entering the passport data");
        openWindowInputPassportData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        this.add(openWindowInputPassportData);
    }
}