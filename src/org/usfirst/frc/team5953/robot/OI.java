
package org.usfirst.frc.team5953.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	Robot redo;
    public OI(Robot redo) {
        this.redo = redo;
    }
    
    public void checkButtons() {
    	
        redo.myRobot.tankDrive(-redo.xboxClear.getLeftStickY(), 
				 -redo.xboxClear.getRightStickY(),
				  false);

		if(redo.xboxClear.getRightBumper()){
			redo.pneumatics.set(DoubleSolenoid.Value.kReverse);	
		}
		
		if(redo.xboxClear.getLeftBumper()){
			redo.pneumatics.set(DoubleSolenoid.Value.kForward);
		}
		
		if(redo.toggle && redo.xboxClear.getAButton()){
			redo.toggle = false;
			if(!redo.solenoidHasBeenFired){
				redo.solenoidHasBeenFired = true;
				redo.pneumatics2.set(DoubleSolenoid.Value.kReverse);
			} else {
				redo.solenoidHasBeenFired = false;
				redo.pneumatics2.set(DoubleSolenoid.Value.kForward);
			} 
		
		} else if(!redo.xboxClear.getAButton()){
			redo.toggle = true;
		}
		
    }
    
}

