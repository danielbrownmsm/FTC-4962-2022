package org.firstinspires.ftc.teamcode.control;

import java.util.ArrayList;


public class Node {
    protected ArrayList<String> topics = new ArrayList<>();
    
    public Node() {
    }
    
    public void give(String topic, String message) {
        
    }
    
    public ArrayList<String> topics() {
        return topics;
    }
    
    // OVERRIDE THIS!
    public void spin() {
        
    }
}