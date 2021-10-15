package org.firstinspires.ftc.teamcode.control;

import java.util.ArrayList;
import java.util.List;


public class Node {
    private ArrayList<Subscriber> subscribers = new ArrayList<>();
    
    public Node() {
    }
    
    public void addCallback(Subscriber subscriber) {
        subscribers.add(subscriber);
    }
    
    public void publish(String data, Subscriber.MsgType msgType) {
        for (Subscriber subscriber : subscribers) {
            subscriber.recieve(data, msgType);
        }
    }
    
    public void recieve(String data, Subscriber.MsgType msgType) {
        
    }
}