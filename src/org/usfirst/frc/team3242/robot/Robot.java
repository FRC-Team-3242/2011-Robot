package org.usfirst.frc.team3242.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;   //this the 2011 robit
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	
	RobotDrive drive;
	XboxController controller;
	
	CANTalon motorOne;
	CANTalon motorTwo;
	CANTalon motorThree;
	CANTalon motorFour;
	CANTalon motorFive; 	//frisbee push
	CANTalon motorSix; 		// bottom shoot
	CANTalon motorSeven; 	//top shoot
	
	DigitalInput limitPush;
	DigitalInput limitPull;
	
	
	//random variables
	boolean shooterTest = false; 	//tests if the shooter is on
	boolean pushTouch = false;	 	//tests if either limit switch is pressed
	boolean pullTouch = false;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
		
		drive = new RobotDrive(new CANTalon(0), new CANTalon(1),new CANTalon(2), new CANTalon(3));
		controller = new XboxController(1);
		
		drive.setInvertedMotor(MotorType.kFrontLeft, true);
		drive.setInvertedMotor(MotorType.kRearLeft, true);
		
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		switch (autoSelected) {
		case customAuto:
			// Put custom auto code here
			break;
		case defaultAuto:
		default:
			// Put default auto code here
			break;
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
		drive.mecanumDrive_Cartesian(controller.getRawAxis(4), controller.getRawAxis(1), controller.getRawAxis(0), 0); //right X, left Y, left X
		
		if (controller.getAButton()) {			//pushes frisbee
			motorFive.set(0.1);
		}
		
		
		if (controller.getXButton() && shooterTest == false) {			//frisbee launcher on
			motorSix.set(0.4);
			motorSeven.set(0.4);
			shooterTest = true;
		}
		
		if (controller.getXButton() && shooterTest) {			//frisbee launcher off
			motorSix.set(0);
			motorSeven.set(0);
			shooterTest = false;
		}
		
		if (limitPush.get()) {		//limit switch things
			motorFive.set(-0.1);
			pushTouch = true;
		}
		
		if (limitPull.get()) {
			motorFive.set(0);
			pullTouch = true;
		}
	}
   
	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

