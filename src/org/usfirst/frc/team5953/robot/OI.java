
package org.usfirst.frc.team5953.robot;

import edu.wpi.first.wpilibj.RobotDrive;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    public OI(Robot redo) {
    	
    	// You can put Some buttons on the SmartDashboard
    	//maybe we can add buttons onto the dashboard for extra functionality?
    	
        //connect some buttons to commands
    	//here is an example
        if (redo.xbox.getAButton()){
        	redo.driveForward();
        }else{
          	redo.makeItStop();
        }
        
        while(redo.xbox.getRightBumper()){
        	//do something specific, or do a script of commands
        }

       //for the rest of the controls for an XboxController, 
       //add methods to control the robot
        //x, y, a b, leftStick, rightStick, dPad, leftTrigger, rightTrigger, back, start
        //left/rightStick X, left/rightStick Y
        
    }
    
    
}

