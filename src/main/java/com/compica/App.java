package com.compica;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        VBox vbox = new VBox(8);
        vbox.setPadding(new Insets(10));

        TextArea senderAddress = new TextArea();
        senderAddress.setEditable(false);
        senderAddress.setPromptText("Enter Sender's Address");
        senderAddress.setText("Martin Fournier\n128 allée des cigales\nSaint-jean-sur-richelieu, J2Y 1B3");
        senderAddress.setStyle("-fx-control-inner-background: lightgray; -fx-border-color: black;");     
        
        ComboBox<Receiver> receiverComboBox = new ComboBox<>();
        receiverComboBox.getItems().addAll(
            new Receiver("Annie Mathieu", "\n884 Des Écluses\nSainte-catherine, J5C 1N8"),
            new Receiver("Claude Godbout", "\n882 Des Écluses\nSainte-catherine, J5C 1N8"),
            new Receiver("Guy Brisebois", "\n880 Des Écluses\nSainte-catherine, J5C 1N8"),
            new Receiver("Marcel Lacasse", "\n4960 Saint-Laurent\nSainte-catherine, J5C 1S3"),
            new Receiver("Jean-Paul Levesque", "\n4962 Saint-Laurent\nSainte-catherine, J5C 1S3"),
            new Receiver("Stephanie Labre", "\n4964 Saint-Laurent\nSainte-catherine, J5C 1S3")
            );
        
        TextArea receiverAddress = new TextArea();
        receiverAddress.setPromptText("Enter Receiver's Address");

        receiverComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                receiverAddress.setText(newSelection.getName() + newSelection.getAddress());
            }
        });

        Button printButton = new Button("Print Envelope");
        printButton.setOnAction(e -> printEnvelope(senderAddress.getText(), receiverAddress.getText()));


        senderAddress.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.TAB) {
                e.consume(); // Consume the event so it doesn't propagate further
                receiverAddress.requestFocus(); // Move focus to the receiver address field
            }
        });

        receiverAddress.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.TAB) {
                e.consume(); // Consume the event so it doesn't propagate further
                printButton.requestFocus(); // Move focus to the sender address field
            }
        });
    
        vbox.getChildren().addAll(senderAddress,receiverComboBox, receiverAddress, printButton);

        Scene scene = new Scene(vbox, 300, 300);
        stage.setScene(scene);
        stage.setTitle("Envelope Printer");
        stage.show();
    }

    private void printEnvelope(String sender, String receiver) {
        Text senderText = new Text(sender);
        senderText.setLayoutX(100); // Adjust as needed
        senderText.setLayoutY(125); // Adjust as needed

        Text receiverText = new Text(receiver);
        receiverText.setLayoutX(300); // Adjust as needed
        receiverText.setLayoutY(250); // Adjust as needed

        Pane printRoot = new Pane(senderText, receiverText);
        printRoot.setPrefSize(200, 200);

        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.LANDSCAPE,
                Printer.MarginType.DEFAULT);
        PrinterJob job = PrinterJob.createPrinterJob(printer);
        if (job != null) {
            job.getJobSettings().setPageLayout(pageLayout);
            boolean success = job.printPage(printRoot);
            if (success) {
                job.endJob();
            }
        }
    }

    public static void main(String[] args) {
        System.setProperty("javafx.userAgentStylesheetUrl", "MODENA");
        launch();
    }

}