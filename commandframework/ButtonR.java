package org.firstinspires.ftc.teamcode.commandframework;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * This class is pretty much stolen completely from WPILib/FTCLib
 * modified, but mostly stolen
 */
public class ButtonR {
    private ButtonType buttonType;
    private Gamepad gamepad;
    
    // all the different types of buttons we can bind to
    public enum ButtonType {
        X, Y, A, B, DpadUp, DpadDown, DpadLeft, DpadRight, RightStick, LeftStick, RightBumper, LeftBumper, Start, Back
    }
    
    /**
     * Makes a new ButtonR (R for Rockettes because I thought there would be a name-collision but there's not)
     * @param gamepad the gamepad instance the button is on
     * @param buttonType which button on the gamepad this is
     */
    public ButtonR(Gamepad gamepad, ButtonType buttonType) {
        this.gamepad = gamepad;
        this.buttonType = buttonType;
    }
    
    /**
     * runs the given command when this button is released
     * @param command the command to run
     */
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
    
    /**
     * continually schedules the given command until the button is released
     * @param command the command to run
     */
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
    
    /**
     * runs the given command when this button is pressed
     * @param command the command to run
     */
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
    
    /**
     * For inteneral use. Gets the state of this button from the gamepad instance.
     */
    protected boolean get() {
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