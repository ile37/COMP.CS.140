package fi.tuni.prog3.sisu;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import static fi.tuni.prog3.sisu.ReadJsonToStudent.readDegreeStructure;
import static fi.tuni.prog3.sisu.ReadWriteToJson.readFromJson;
import static fi.tuni.prog3.sisu.ReadWriteToJson.writeToJson;
import static fi.tuni.prog3.sisu.SisuAPI.getData;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX Sisu app implementation.
 * Includes login screen, where student can sign in or move to a 
 * "choose degree" -screen, which lists all available study programs.
 * Signing in opens a window which displays the student's chosen program.
 */
public class Sisu extends Application {

    /*Temporary list to store all students*/
    static ArrayList<Student> students = new ArrayList<>();
    
    /*Temporary Student object that is saved to display correct degree program*/
    static Student currentStudent;
    
    // TEMPORARY list that's supposed to be inside student
    private static ArrayList<String> selectedCourses = new ArrayList<>();
    
    // list so the right view can be changed
    private ObservableList<String> rightBoxCourseList;
    
    // Private parameter of the left VBox's listView, to give
    // chooseCourseButton access to the names of courses
    private ListView<String> dpNames;
    
    // Button that exits the program
    private Button getQuitButton() {
        Button button = new Button("Lopeta");
        button.setOnAction((ActionEvent event) -> {
            currentStudent.setSelectedCourses(selectedCourses);
            
                try {
                    writeToJson("StudentsDegreeSave", currentStudent, null);
                } catch (IOException ex) {
                    Platform.exit();
                }
            
            Platform.exit();
        });
        return button;
    }
    
    private Button getChooseCourseButton() {
        Button button = new Button("Valitse kurssi");
        return button;
    }
    
    // Formats the Right side of the center box
    private VBox getRightVBox() {
        //Creating a VBox for the right side.
        VBox rightVBox = new VBox();
        rightVBox.setPrefWidth(280);
        rightVBox.setStyle("-fx-background-color: #c5cbd1;");
        
        Label rightLabel = new Label(" Opiskelijan valitsemat kurssit ");
        rightLabel.setFont(Font.font(18));
        
        HBox labelHBox = new HBox();
        labelHBox.getChildren().add(rightLabel);
        labelHBox.setAlignment(Pos.TOP_CENTER);
        rightVBox.getChildren().add(labelHBox);
        
        // Matin koodia vähän kopioitu, thanks buddy
        ObservableList<String> selectedCourseList = 
                FXCollections.observableArrayList();
        if (currentStudent.getSelectedCourses() != null) {
            for (String name : currentStudent.getSelectedCourses()) {
            selectedCourseList.add(name);
            }
        }
        
        
        ListView<String> listView = new ListView<>(selectedCourseList);
        rightVBox.getChildren().add(listView);
        
        rightBoxCourseList = selectedCourseList;
        
        return rightVBox;
    }
    
    // Adds the study program structure with relevant indentation to the list
    private ObservableList<String> recursiveAdd(
            StudyGrouping module, ObservableList<String> obsList, int depth) {
        
        ArrayList<Course> subCourses = module.getSubCourses();
        ArrayList<StudyGrouping> subModules = module.getSubModules();
        
        for (Course subCourse : subCourses) {  
            String minCredits = "";

            if (subCourse.getMinCredits() != null) {
                minCredits = " " + Integer.toString(subCourse.getMinCredits())
                        + "op";
            }
            String indent = "    ".repeat(depth);
            String line = indent + subCourse.getName() + minCredits;
            obsList.add(line);
        }
        
        for (StudyGrouping subModule : subModules) {
            String minCredits = "";

            if (subModule.getMinCredits() != null) {
                minCredits = " " + Integer.toString(subModule.getMinCredits());
            }
            String indent = "    ".repeat(depth);
            String line = indent + subModule.getName() + minCredits;
            obsList.add(line);
            
            obsList = recursiveAdd(subModule, obsList, depth + 1);
        }
        return obsList;
    }
    
    // Formats the left side of the center box
    private VBox getLeftVBox() {
        VBox leftVBox = new VBox();
        leftVBox.setPrefWidth(480);
        
        ObservableList<String> DPnameList = FXCollections.observableArrayList();
        
        if (currentStudent != null) {
            DegreeProgramme currentDP = currentStudent.getDP();
            
            ArrayList<StudyGrouping> subModules = currentDP.getSubModules();
            int depth = 1; // indent of the next level = depth * "  "
            
            for (StudyGrouping subModule : subModules) {
                DPnameList.add(subModule.getName());
                
                DPnameList = recursiveAdd(subModule, DPnameList, depth);
            }
            
            ListView<String> listView = new ListView<>(DPnameList);
            leftVBox.getChildren().add(listView);
            // Save the listview so it can be easily accessed while making         
            // course selections.
            this.dpNames = listView;
        }
        return leftVBox;
    }
    
    // Formats the center box of a borderpane
    private HBox getCenterHbox() {
        HBox centerHBox = new HBox(10);
        centerHBox.getChildren().addAll(getLeftVBox(), getRightVBox());
        return centerHBox;
    }
    
    /** 
     * Creating a "default" GridPane where:
     * @param text shows the topic of the window
     */
    private GridPane newGridPane(String text) {
        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setPadding(new Insets(25, 25, 25, 25));
        
        // Textbox
        Text scenetitle = new Text(text);
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gp.add(scenetitle, 0, 0, 2, 1);
        
        return gp; 
    }
    
    // Method to choose the course which is currently selected in the left box
    // this method is called by the 'Choose course' button.
    private void chooseCourse(String name) {
        
        if(selectedCourses.contains(name)) {
            selectedCourses.remove(name);
            rightBoxCourseList.remove(name);
        }
        else if(selectedCourses.contains(name) == false) {
            selectedCourses.add(name);
            rightBoxCourseList.add(name);
        }

    }  
    
    /**
     * Starts the app and begins by displaying the login screen.
     * @param stage The main stage on which everything is displayed on
     * @throws java.net.MalformedURLException 
     * @throws java.io.IOException 
     */
    @Override
    public void start(Stage stage) throws MalformedURLException, IOException {
        
        // save file is read to the students arrayList
        students = readFromJson("StudentsDegreeSave");
             
        
        stage.setTitle("Sisu Login");
        
        // Login scene ---------------------------------------------------------
        GridPane logInGrid = newGridPane("Tunnistaudu");
        
        // "Student line"-label and input field
        Label studentNameLabel = new Label("Opiskelijan nimi:");
        logInGrid.add(studentNameLabel, 0, 1);
        TextField studentNameField = new TextField();
        logInGrid.add(studentNameField, 1, 1);

        // "StudentID"-label and password field
        Label studentIDLabel = new Label("Opiskelijanumero:");
        logInGrid.add(studentIDLabel, 0, 2);        
        PasswordField passwordField = new PasswordField();
        logInGrid.add(passwordField, 1, 2);
        
        // Text field for Login errors
        final Text loginErrorText = new Text();
        logInGrid.add(loginErrorText, 1, 3);
        
        // Adding buttons for "Choose study program" and "Sign in"
        Button newStudentBtn = new Button("Valitse tutkinto");
        Button signInBtn = new Button("Kirjaudu sisään");
        HBox loginBtnBox = new HBox(10);
        loginBtnBox.setAlignment(Pos.BOTTOM_RIGHT);
        loginBtnBox.getChildren().addAll(newStudentBtn, signInBtn);
        
        // Adding buttons to the bottom of the grid
        logInGrid.add(loginBtnBox, 1, 4);
        
        Scene login = new Scene(logInGrid);
        stage.setScene(login);
        // Login scene ends ----------------------------------------------------
        
        
        // Select program scene ------------------------------------------------
        // Url where all of the study programMap are
        var url = new URL("https://sis-tuni.funidata.fi/kori/api/module-"
                + "search?curriculumPeriodId=uta-lvv-2021&universityId=tuni-"
                + "university-root-id&moduleType=DegreeProgramme&limit=1000");
        
        // Opening and reading the url to a JsonObject
        String jsonText = new String(url.openStream().readAllBytes());            
        Gson dataGson = new Gson();
        JsonObject data = dataGson.fromJson(jsonText, JsonObject.class);

        // Creating a list for the study programMap which can be observed
        ObservableList<String> degreeNameList = 
                FXCollections.observableArrayList();
        
        // Map holding degree line as key and value as id
        HashMap<String, String> programMap = new HashMap<>();
        // JsonArray of all study programs
        JsonArray degreePrograms = data.get("searchResults").getAsJsonArray();
        for (var degree : degreePrograms) {
            
            String degreeName = degree.getAsJsonObject().get("name")
                    .toString().replace("\"", "");
            String degreeID = degree.getAsJsonObject().get("id").toString();
            
            programMap.put(degreeName, degreeID);
            degreeNameList.add(degreeName);
        }

        // A scrollable list of the programs
        ListView<String> listView = new ListView<>(degreeNameList);
            
        GridPane newStudentGrid = newGridPane("Valitse tutkinto-ohjelmasi");
        
        // Adding buttons for "return" and "choose"
        Button backBtn = new Button("Takaisin");
        Button selectBtn = new Button("Valitse");
        HBox selPrgmBtnBox = new HBox(10);
        selPrgmBtnBox.setAlignment(Pos.BOTTOM_RIGHT);
        selPrgmBtnBox.getChildren().addAll(backBtn, selectBtn);
        
        // Adding the buttons to the bottom
        newStudentGrid.add(selPrgmBtnBox, 0, 4);
        
        newStudentGrid.add(listView, 0, 2);
        Scene newstudent = new Scene(newStudentGrid);
        // Select program scene ends -------------------------------------------
                
        // Action for selecting the study program
        selectBtn.setOnAction((ActionEvent e) -> {
            String selected = listView.getSelectionModel()
                    .selectedItemProperty().getValue();
            
            try {
                JsonObject chosenDegree = getData(programMap.get(selected));
                
                //System.err.println("tutkinnon rakentaminen aloitettu");
                DegreeProgramme testDegree = readDegreeStructure(chosenDegree);
                
                //System.err.println("alotettu alustava tallennus");
                Student studentUser = new Student(studentNameField.getText(), 
                        passwordField.getText(), testDegree);
                studentUser.setSelectedCourses(new ArrayList<>());
                //System.err.println("clearattu valitut");
                students.add(studentUser);
                writeToJson("StudentsDegreeSave", studentUser, chosenDegree);
                
                //System.err.println("rakentaminen valmis, lopetettu "
                //        + "alustava tallennus");
            } catch (IOException ex) { 
            }     
        });
        
        // Action when pressing sign in button
        signInBtn.setOnAction((ActionEvent e) -> {
            String studentID = passwordField.getText();
            
            boolean isUser = false;
            for (Student student : students) {
                if (student.getId().equals(studentID)) {
                    currentStudent = student;
                    isUser = true;
                    break;
                }
            }
            
            if (isUser) {
                studentNameField.clear();
                passwordField.clear();
                loginErrorText.setText("");
                
                // Sisu scene --------------------------------------------------
                //Creating a new BorderPane.
                BorderPane sisuBorderPane = new BorderPane();
                sisuBorderPane.setPadding(new Insets(10, 10, 10, 10));
                sisuBorderPane.setCenter(getCenterHbox());

                //"Quit" button
                var quitButton = getQuitButton();
                BorderPane.setMargin(quitButton, new Insets(10, 10, 0, 10));
                
                // Button for choosing course
                var chooseCourseButton = getChooseCourseButton();
                // Giving chooseCourseButton functionality here
                chooseCourseButton.setOnAction( (ActionEvent event) -> {
                    var chosenCourse = dpNames.getSelectionModel()
                            .selectedItemProperty().getValue();
                    if (chosenCourse != null) {
                        String selected = chosenCourse;

                        // if string doesn't end in op, choosing the selection
                        // does nothing.
                        boolean isCourse = selected.endsWith("op");
                        if(isCourse) {

                            selected = selected.trim();
                            chooseCourse(selected);
                        }  
                    }
                });
                
                // Button for signing out
                Button signOutBtn = new Button("Kirjaudu ulos");
                signOutBtn.setOnAction( (ActionEvent event) -> {
                    currentStudent.setSelectedCourses(selectedCourses);
                    try {
                        writeToJson("StudentsDegreeSave", currentStudent, null);
                    } catch (IOException ex) {
                        System.err.println("Couldn't save changes");
                        stage.setScene(login);
                    }
                    stage.setScene(login);
                });
                
                // Box where the buttons are added
                HBox buttonBox = new HBox(100);
                buttonBox.setAlignment(Pos.TOP_RIGHT);
                buttonBox.getChildren().addAll(signOutBtn, chooseCourseButton, quitButton);
                sisuBorderPane.setBottom(buttonBox);
                BorderPane.setAlignment(buttonBox, Pos.TOP_RIGHT);

                Scene sisu = new Scene(sisuBorderPane, 800, 500);
                stage.setTitle("SisuGUI");
                
                stage.setScene(sisu);
                
                // Sisu scene ends ---------------------------------------------
            }
            else {
                loginErrorText.setFill(Color.FIREBRICK);
                loginErrorText.setText("Väärä tunnus");
            }
        });
        
        // Change scene from login to student registration
        newStudentBtn.setOnAction((ActionEvent e) -> {
            loginErrorText.setText("");
            String studentName = studentNameField.getText();
            String studentID = passwordField.getText();
            if ("".equals(studentName) || "".equals(studentID)) {
                loginErrorText.setFill(Color.FIREBRICK);
                loginErrorText.setText("Aseta nimi ja opnumero");
            } else {
                stage.setScene(newstudent);
            }
        });
        
        // Change scene from student registration to login scene
        backBtn.setOnAction((ActionEvent e) -> {
            stage.setScene(login);
        });

        stage.show();
    }
    
    // Launches the stage creation
    public static void main() throws IOException {
        launch();
    }
}