package org.firstinspires.ftc.teamcode.control;

import java.util.TreeMap;
import java.util.HashMap;
import java.util.List;
//import org.firstinspires.ftc.teamcode

public class Master {
   private TreeMap<String, String> topicDataMap = new TreeMap<>();
   private HashMap<String, List<Subscriber>> topicSubsMap = new HashMap<>();
   private Master instance;
   
   /**
    * Master map:
    * topic name => (subscriber, subscriber, ...), data
    */
   
   public Master() {
      
   }
   
   public void publish(String topic, String data) {
      topicDataMap.put(topic, data);
      for (Subscriber sub : topicSubsMap.get(topic)) {
         sub.notify();
      }
   }
   
   public String getData(String topic) {
      return topicDataMap.get(topic);
   }
   
   // publisher calls Master.publish(topicName, data, dataType?)
   // 
}