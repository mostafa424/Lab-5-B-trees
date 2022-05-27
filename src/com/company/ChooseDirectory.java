package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ChooseDirectory extends Component {
    public String chooseFile() {
        JFileChooser jFileChooser = new JFileChooser();
        /*
        set default place
         */
        jFileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        //set title of window
        jFileChooser.setDialogTitle("Select Directory");
        int result = jFileChooser.showOpenDialog(this);
        String directory = "";
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jFileChooser.setAcceptAllFileFilterUsed(false);


        if(result == JFileChooser.APPROVE_OPTION) {
            /*
            get the required direction from user
            */
            File file = jFileChooser.getCurrentDirectory();
            directory = file.getAbsolutePath();
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
