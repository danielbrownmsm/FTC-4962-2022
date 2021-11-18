package org.firstinspires.ftc.teamcode.commandframework;

import com.qualcomm.robotcore.hardware.Gamepad;


public class ButtonR {
    private ButtonType buttonType;
    private Gamepad gamepad;
    
    public enum ButtonType {
        X, Y, A, B, DpadUp, DpadDown, DpadLeft, DpadRight, RightStick, LeftStick, RightBumper, LeftBumper, Start, Back
    }
    
    public ButtonR(Gamepad gamepad, ButtonType buttonType) {
        this.gamepad = gamepad;
        this.buttonType = buttonType;
    }
    
    public String toString() {
        return gamepad.toString() + " " + buttonType.toString();
    }
    
    public void whenReleased(final Command command) {
        CommandScheduler2.addButton(new Runnable() {
            private boolean lastState = get();
            
            @Override
            public void run() {
                boolean state = get();
                
                if (!state && lastState) {
                    CommandScheduler2.schedule(command);
                }
                lastState = state;
            }
        });
    }
    
    public void whileHeld(final Command command) {
        CommandScheduler2.addButton(new Runnable() {
            private boolean lastState = get();
            
            @Override
            public void run() {
                boolean state = get();
                
                if (state) {
                    CommandScheduler2.schedule(command);
                } else if (lastState) {
                    CommandScheduler2.cancel(command);
                }
                lastState = state;
            }
        });
    }
    
    public void whenPressed(final Command command) {
        CommandScheduler2.addButton(new Runnable() {
            private boolean lastState = get();
            
            @Override
            public void run() {
                boolean state = get();
                
                if (!lastState && state) {
                    CommandScheduler2.schedule(command);
                }
                lastState = state;
            }
        });
    }
    
    public boolean get() {
        switch(buttonType) {
            case A:
                return gamepad.a;
            case B:
                return gamepad.b;
            case X:
                return gamepad.x;
            case Y:
                return gamepad.y;
            case DpadUp:
                return gamepad.dpad_up;
            case DpadDown:
                return gamepad.dpad_down;
            case DpadLeft:
                return gamepad.dpad_left;
            case DpadRight:
                return gamepad.dpad_right;
            case RightStick:
                return gamepad.right_stick_button;
            case LeftStick:
                return gamepad.left_stick_button;
            case RightBumper:
                return gamepad.right_bumper;
            case LeftBumper:
                return gamepad.left_bumper;
            case Start:
                return gamepad.start;
            case Back:
                return gamepad.back;
            default:
                return false;
        }
    }
}