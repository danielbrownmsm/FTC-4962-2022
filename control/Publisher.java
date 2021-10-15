package org.firstinspires.ftc.teamcode.control;

import java.util.ArrayList;
import java.util.List;


public class Publisher {
    private ArrayList<Subscriber> subscribers = new ArrayList<>();
    
    public Publisher() {
    }
    
    public void addCallback(Subscriber subscriber) {
        subscribers.add(subscriber);
    }
    
    public void publish(String data, Subscriber.MsgType msgType) {
        for (Subscriber subscriber : subscribers) {
            subscriber.recieve(data, msgType);
        }
    }
}