package data;

import gui.*;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class Main {
    public static void main(String[] args) {
        WriteData right = new WriteData();
        ReadFile read = new ReadFile();

        read.readEmployeeFile();
        read.readMediaFile();
        GUI gui = new GUI(right, read);

        right.writeMed(read.Media.mediaT); // will move this to GUI soon


    }
}

