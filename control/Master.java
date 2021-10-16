package org.firstinspires.ftc.teamcode.control;

import java.util.ArrayList;
import java.util.TreeMap;

import org.firstinspires.ftc.teamcode.control.Node;

public class Master {
   private ArrayList<Node> nodes = new ArrayList<>(); // a list of each node
   private TreeMap<String, String> topics = new TreeMap<>();
   private Master instance;
   
   
   public Master() {
      
   }
   
   public void push(String topic, String message) {
      topics.put(topic, message);
      for (Node node : nodes) {
         if (node.topics().contains(topic)) {
            node.give(topic, message);
         }
      }
   }
   
   // calls each node's spin()
   // call this each loop iteration in op-modes
   public void execute() {
      for (Node node : nodes) {
         node.spin();
      }
   }
}