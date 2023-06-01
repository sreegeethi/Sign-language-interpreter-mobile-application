package com.example.loginpage;

public class OptionsLibrary {
    private String options[][] ={
            {"Force","Fight","Shut","Move"},
            {"Drown","Fall","Bottom","Low"},
            {"Hear","Ignore","Impossible","Arrive"},
            {"Dare","Devil","Meet","Surprise"},
            {"Degree","Daydream","High","Theory"}
    };

    private String correctAns[] ={"Force","Fall","Ignore","Dare","Theory"};

    public String getOption1(int a){
        String option1 = options[a][0];
        return option1;
    }
    public String getOption2(int a){
        String option2 = options[a][1];
        return option2;
    }
    public String getOption3(int a){
        String option3 = options[a][2];
        return option3;
    }
    public String getOption4(int a){
        String option4 = options[a][3];
        return option4;
    }

    public String getCorrectAns(int a){
        String correct = correctAns[a];
        return correct;
    }


}

