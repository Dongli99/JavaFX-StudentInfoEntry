package exercise1;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.*;

public class StudentInfoEntry extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // SECTION 1: DECLARE_OBJECTS
        // labels
        Label nameLbl = new Label("Name");
        Label addressLbl = new Label("Address:");
        Label cityLbl = new Label("City:");
        Label provinceLbl = new Label("Province:");
        Label postalCodeLbl = new Label("Post Code:");
        Label phoneNumLbl = new Label("Phone Number:");
        Label emailLbl = new Label("Email:");
        // Text fields
        TextField nameTxf = new TextField();
        TextField addressTxf = new TextField();
        TextField cityTxf = new TextField();
        TextField provinceTxf = new TextField();
        TextField postalCodeTxf = new TextField();
        TextField phoneNumTxf = new TextField();
        TextField emailTxf = new TextField();
        // checkboxes
        CheckBox studentCouncilChk = new CheckBox("Student Council");
        CheckBox volunteerChk = new CheckBox("Volunteer Work");
        // toggle group and radio buttons
        ToggleGroup programsTg = new ToggleGroup();
        RadioButton csRb = new RadioButton("Computer Science");
        RadioButton businessRb = new RadioButton("Business");
        csRb.setToggleGroup(programsTg);
        businessRb.setToggleGroup(programsTg);
        programsTg.selectToggle(csRb); // init value of program

        // choice box and list box, text area
        ChoiceBox coursesChc = new ChoiceBox<>();
        ObservableList<String> csCourses = FXCollections.observableArrayList( // prepare list for courseChc
                "Java", "Python", "C#", "Software Design", "English");
        ObservableList<String> businessCourses = FXCollections.observableArrayList(
                "Management", "Marketing", "MS Office", "Communication");
        coursesChc.setItems(csCourses); // init value of courseChc
        coursesChc.setPrefWidth(100);
        ListView<String> selectedCoursesLv = new ListView<>();
        selectedCoursesLv.setPrefWidth(Region.USE_PREF_SIZE);
        selectedCoursesLv.setPrefHeight(Region.USE_PREF_SIZE);
        TextArea summaryTxa = new TextArea();
        summaryTxa.setMaxWidth(Double.MAX_VALUE);

        // button
        Button displayBtn = new Button("Display");

        // SECTION 2: EVENT_HANDLERS
        // toggle value of course list when switching program
        csRb.setOnAction(e->{
            coursesChc.setItems(csCourses); // change the content of the choice box
            selectedCoursesLv.getItems().clear(); // when toggling, clean the listview
        });
        businessRb.setOnAction(e->{
            coursesChc.setItems(businessCourses);
            selectedCoursesLv.getItems().clear();
        });

        // handling listview items adding when selecting a course
        coursesChc.setOnAction(e->{
            String courseSelected = (String) coursesChc.getSelectionModel().getSelectedItem();
            if (!selectedCoursesLv.getItems().contains(courseSelected)){
                selectedCoursesLv.getItems().add(courseSelected);
            }
        });

        // handling Display button
        displayBtn.setOnAction(e->{
            summaryTxa.clear(); // clear the remain content
            String message = ""; // declare a message string
            // get values from text field
            String name = nameTxf.getText();
            String address = addressTxf.getText();
            String city = cityTxf.getText();
            String province = provinceTxf.getText();
            String postCode = postalCodeTxf.getText();
            String phoneNum = phoneNumTxf.getText();
            String email = emailTxf.getText();
            // Join basic information
            String basicInfo = String.join(
                    ", ",
                    name, address, city, province, postCode, phoneNum, email);
            message += basicInfo;

            // Optional involvement message
            if (studentCouncilChk.isSelected() || volunteerChk.isSelected()){
                String invloveInfo = "\nInvolved in:";
                if (studentCouncilChk.isSelected()){
                    invloveInfo += " ";
                    invloveInfo += studentCouncilChk.getText();
                }
                if (volunteerChk.isSelected()){
                    invloveInfo += " ";
                    invloveInfo += volunteerChk.getText();
                }
                message += invloveInfo;
            }

            // Program infomation
            String programInfo = "\nProgram: ";
            programInfo += (csRb.isSelected()?csRb.getText():businessRb.getText());
            message += programInfo;

            // Courses information
            if (!selectedCoursesLv.getItems().isEmpty()){
                String courseInfo = "\nCourses: ";
                for (String item: selectedCoursesLv.getItems()){
                    courseInfo += "\n-";
                    courseInfo += item;
                }
                message += courseInfo;
            }

            // set content in summary text area
            summaryTxa.setText(message);
        });

        // SECTION 3: LAYOUT
        // create Vboxes and fill with objects
        // top column 1
        VBox infoLabelsVb = new VBox(10);
        infoLabelsVb.getChildren().addAll(
                nameLbl,
                addressLbl,
                cityLbl,
                provinceLbl,
                postalCodeLbl,
                phoneNumLbl,
                emailLbl
        );
        infoLabelsVb.setAlignment(Pos.CENTER_LEFT);

        // top column 2
        VBox infoInputsVb = new VBox(2);
        infoInputsVb.getChildren().addAll(
                nameTxf,
                addressTxf,
                cityTxf,
                provinceTxf,
                postalCodeTxf,
                phoneNumTxf,
                emailTxf
        );

        // top column 3
        VBox checkBoxesVb = new VBox(
                studentCouncilChk,
                volunteerChk
        );
        checkBoxesVb.setAlignment(Pos.CENTER);
        checkBoxesVb.setSpacing(50);

        // top column 4
        HBox programHbox = new HBox(csRb, businessRb);
        programHbox.setSpacing(2);
        VBox coursesVb = new VBox(
                programHbox,
                coursesChc,
                selectedCoursesLv
        );
        coursesVb.setSpacing(10);
        // set courseList to grow the increasing space
        coursesVb.setVgrow(selectedCoursesLv, Priority.ALWAYS);

        // a HBox contains the top 4 columns
        HBox top = new HBox(
                infoLabelsVb,
                infoInputsVb,
                checkBoxesVb,
                coursesVb
        );
        top.setSpacing(10);

        // Bottom HBox
        VBox bottom = new VBox(
               displayBtn,
               summaryTxa
        );
        bottom.setAlignment(Pos.CENTER);
        bottom.setPrefWidth(560);
        bottom.setSpacing(10);

        // Integrate the sections
        FlowPane pane = new FlowPane(top, bottom);
        pane.setPadding(new Insets(20));
        Scene scene = new Scene(pane);
        primaryStage.setTitle("Student Information Entry");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
