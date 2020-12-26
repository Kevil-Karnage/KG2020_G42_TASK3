package com;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private DrawPanel dp;
    public MainWindow() throws HeadlessException {

        this.setLayout(new GridLayout());
        this.dp = new DrawPanel();
        this.add(dp);
    }
}
