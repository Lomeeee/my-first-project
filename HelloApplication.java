package com.example.lomelomejavatest;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.*;


public class HelloApplication extends Application {
    Stage window;
    Scene scene;


    @Override
    public void start(Stage stage) throws IOException {
        window = stage;
        window.setTitle("ProjectNotePad");


        //textarea
        TextArea mainTextArea = new TextArea("A notepad system developed by ℒome for simple and daily activities...");
        //menuBar
        MenuBar mainMenuBar = createMenuBar(window, mainTextArea);
        //create Layout

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(mainMenuBar);
        borderPane.setCenter(mainTextArea);


        scene = new Scene(borderPane, 600, 400);


        window.setTitle("Project Notepad");
        window.setScene(scene);
        window.show();
    }

    private MenuBar createMenuBar(Stage stage, TextArea textArea) {

        MenuBar menuBar = new MenuBar();
        //add to MenuBar
        Menu menuFile = new Menu("File");
        MenuItem NewItem = new MenuItem("New");
        MenuItem SaveItem = new MenuItem("Save");
        MenuItem ExitItem = new MenuItem("Exit");
        MenuItem DuplicateItem = new MenuItem("Duplicate");


        // Add action to the "New" menu item
        NewItem.setOnAction(e -> {
            textArea.clear();
            stage.setTitle("New NotePad");
        });



        // Add action to the "Save" menu item
        SaveItem.setOnAction(e -> saveFile(stage, textArea.getText()));

        // Add action to the "Exit" menu item
        ExitItem.setOnAction(e -> ExitFile());

        //Add action to the "Duplicate" menu item
        DuplicateItem.setOnAction(e -> duplicateTextArea(stage, textArea));


        //add items to file menu
        menuFile.getItems().addAll(NewItem,  new SeparatorMenuItem(), SaveItem, DuplicateItem, new SeparatorMenuItem(), ExitItem);

        //edit menu
        Menu menuEdit = new Menu("Edit");
        MenuItem CutItem = new MenuItem("Cut");
        MenuItem CopyItem = new MenuItem("Copy");
        MenuItem PasteItem = new MenuItem("Paste");


        CutItem.setOnAction(e -> cut(textArea));
        CopyItem.setOnAction(e -> copy(textArea));
        PasteItem.setOnAction(e -> paste(textArea));


        //add items to edit menu
        menuEdit.getItems().addAll(CutItem, CopyItem, PasteItem);



        //About Menu
        Menu menuAbout = new Menu("About");
        MenuItem aboutUs = new MenuItem("About us");
        aboutUs.setOnAction(e -> about());
        menuAbout.getItems().add(aboutUs);


        //add menus to menuBar
        menuBar.getMenus().addAll(menuFile, menuEdit, menuAbout);


        return menuBar;
    }

    //exitFile Function
    private static void ExitFile() {
        boolean answer = display("Exit file", "Are you sure you want to close this file?");
        if (answer){
            System.exit(0);
        }
    }
    static boolean answer;
    public static boolean display(String title, String message){
        Stage window = new Stage();


        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        Label label=new Label(message);

        //Create two buttons
        Button yes=new Button("Yes");
        Button no=new Button("No");

        yes.setOnAction(e -> {
            answer=true;
            window.close();
        });

        no.setOnAction(e -> {
            answer=false;
            window.close();
        });


        VBox layout = new VBox(10, label, yes, no);
        layout.setAlignment(Pos.CENTER);



        Scene scene=new Scene(layout);
        window.setScene(scene);
        window.show();

        return answer;

    }

    //ExitFile Function
    private void saveFile(Stage stage, String content) {
        //create a new FileChooser object.
        // This class is part of the JavaFX library and is used to show a file dialog that allows the user to choose a file for opening or saving.
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(stage);


        if (file != null) {
            //The try-catch block is used for exception handling in Java.
            try (FileWriter fileWriter = new FileWriter(file)) {
                //Creates a new instance of the FileWriter class.
                //The FileWriter is used for writing character data to a file
                fileWriter.write(content);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle exception as needed
            }
        }
    }

    //duplicateFile Function
    private void duplicateTextArea(Stage window, TextArea originalTextArea) {
        Stage duplicatedStage = new Stage();


        // Create a new TextArea and set its content to the original TextArea
        TextArea duplicatedTextArea = new TextArea(originalTextArea.getText());


        MenuBar duplicatedMenubar = createMenuBar(window, originalTextArea);


        // Create a BorderPane layout for the duplicated scene
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(duplicatedMenubar);
        borderPane.setCenter(duplicatedTextArea);

        // Create Scene for the duplicated stage
        Scene duplicatedScene = new Scene(borderPane, 600, 400);

        // Set the stage properties
        duplicatedStage.setTitle("ProjectNotepadCopy");
        duplicatedStage.setScene(duplicatedScene);


        // Show the duplicated stage
        duplicatedStage.show();
    }

    //cut function
    private void cut(TextArea textArea) {
        String selectedText = textArea.getSelectedText();
        if (!selectedText.isEmpty()) {
            //Obtains the system clipboard using Clipboard.getSystemClipboard().
            Clipboard clipboard = Clipboard.getSystemClipboard();

            //Creates a new ClipboardContent object(content), which will hold the data to be placed on the clipboard.
            ClipboardContent content = new ClipboardContent();

            //sets selected text as a String content in the ClipboardContent object.
            content.putString(selectedText);

            //Sets the content of the system clipboard to the ClipboardContent object, effectively placing the selected text on the clipboard.
            clipboard.setContent(content);

            // Remove the selected text
            textArea.deleteText(textArea.getSelection());
        }
    }

    //copy function
    private void copy(TextArea textArea) {
        String selectedText = textArea.getSelectedText();
        if (!selectedText.isEmpty()) {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            //The Clipboard class is part of the JavaFX library and is used for managing clipboard operations.
            ClipboardContent content = new ClipboardContent();
            content.putString(selectedText);
            clipboard.setContent(content);
        }
    }

    //paste function
    private void paste(TextArea textArea) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        if (clipboard.hasString()) {
            int caretPosition = textArea.getCaretPosition();
            textArea.insertText(caretPosition, clipboard.getString());
        }
    }

    //about us
    private void about(){
        Stage aboutStage = new Stage();
        Label label = new Label("Welcome to the exploration of an efficient organizational tool:\nThe Notepad System.\n" +
                "In a world filled with tasks and information.\nI aim to introduce this system which simplifies workflow and enhances productivity.\n"
                );
        Button button = new Button("Close");
        button.setOnAction(e -> aboutStage.close());



        VBox vBox = new VBox(10, label, button);
        vBox.setAlignment(Pos.CENTER);


        Scene scene = new Scene(vBox);
        aboutStage.setScene(scene);
        aboutStage.setTitle("About ProjectNotePad");
        aboutStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}

/*

        //open file
        OpenItem.setOnAction(e -> openFile(stage, textArea));


    private void openFile(Stage window, TextArea textArea) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        File file = fileChooser.showOpenDialog(window);

        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                textArea.setText(content.toString());
            } catch (IOException e) {
                e.printStackTrace();
                // Handle exception as needed
            }
        }
    }
 */
