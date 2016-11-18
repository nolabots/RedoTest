
package org.usfirst.frc.team5953.robot;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    public OI(Robot redo) {
    	
    	// You can put Some buttons on the SmartDashboard
   
        //connect some buttons to commands
        if (redo.xbox.getAButton()){
        	redo.autonomousPeriodic();
        }
        
    }
}

