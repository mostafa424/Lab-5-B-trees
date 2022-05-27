package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ChooseFile extends Component implements IChooser{
    @Override
    public  String getDirectory() {
        JFileChooser jFileChooser = new JFileChooser();
        /*
        set default place
         */
        jFileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        //set title of window
        jFileChooser.setDialogTitle("Select File");
        int result = jFileChooser.showOpenDialog(this);
        String directory = "";
        if(result == JFileChooser.APPROVE_OPTION) {
            /*
            get the required direction from user
             */
            directory = jFileChooser.getSelectedFile().getAbsolutePath();
        }
        StringBuilder resultPath = new StringBuilder();
        // handle "\\"
        for (int i=0; i<directory.length(); i++) {
            if(directory.charAt(i) != '\\')
                resultPath.append(directory.charAt(i));
            else {
                resultPath.append('\\');
                resultPath.append('\\');
            }
        }
        return resultPath.toString();
    }
}
