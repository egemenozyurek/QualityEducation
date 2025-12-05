/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.assessment;

/**
 *
 * @author dhali
 */
public class Question {

    private String text;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private char correctOption; // option 'A', 'B', 'C', or 'D'
    private String category;    // categories include: "Science", "Geography" , etc.

    public Question(String text,
            String optionA,
            String optionB,
            String optionC,
            String optionD,
            char correctOption,
            String category) {
        this.text = text;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctOption = Character.toUpperCase(correctOption);
        this.category = category;
    }

    //  getters
    public String getText() {
        return text;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public char getCorrectOption() {
        return correctOption;
    }

    public String getCategory() {
        return category;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public void setCorrectOption(char correctOption) {
        this.correctOption = correctOption;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    
}
