package frc.team5115.subsystems;

import java.util.ArrayList;

public class Subsystem {

    ArrayList<String> dictionary;

    String state = "Stopped";
    String lastState;

    public int setState(String state){
        if(dictionary.contains(state)){
            lastState = currentState();
            this.state = state;
            return 0;
        } else {
            return -1;
        }
    }

    public String currentState(){
        return this.state;
    }

    public void update(){
        switch(this.state){
            case "Stopped":
                break;
        }
    }

}
