package com.piiottiles.iottiles;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.Font;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.net.URL;
import java.net.MalformedURLException;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.piiottiles.util.WebBrowser;

import com.piiottiles.RPiIoTTiles;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
// import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
// import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
// import com.pi4j.io.gpio.event.GpioPinListenerDigital;

/**
 * Main window implementation for the Raspberry Pi IoT Tiles example
 */
public class IoTTiles {
	private final JFrame frameIoT;
	private static IoTTiles IOTTILES_INSTANCE = null;
	private boolean alive = true;
	
	private ImageIcon alarm_bellIcon;
	private ImageIcon autosIcon;
	private ImageIcon biosIcon;
	private ImageIcon bluetoothIcon;
	private ImageIcon bulbIcon;
	private ImageIcon cameraIcon;
	private ImageIcon camera_addIcon;
	private ImageIcon camera_errorIcon;
	private ImageIcon celsiusIcon;
	private ImageIcon computerIcon;
	private ImageIcon computer_addIcon;
	private ImageIcon computer_deleteIcon;
	private ImageIcon computer_keyIcon;

	private ImageIcon doorIcon;
	private ImageIcon door_inIcon;
	private ImageIcon door_openIcon;
	private ImageIcon door_outIcon;

	private ImageIcon emblem_systemIcon;
	private ImageIcon exclamationIcon;
	private ImageIcon informationIcon;
	private ImageIcon keyIcon;
	private ImageIcon key_addIcon;

	private ImageIcon lightbulbIcon;
	private ImageIcon lightbulb_addIcon;
	private ImageIcon lightbulb_deleteIcon;
	private ImageIcon lightbulb_offIcon;

	private ImageIcon lockIcon;
	private ImageIcon lock_addIcon;
	private ImageIcon lock_breakIcon;
	private ImageIcon lock_openIcon;

	private ImageIcon notification_bellIcon;
	private ImageIcon personalIcon;
	private ImageIcon personal2Icon;
	private ImageIcon phone_openIcon;
	private ImageIcon textfieldIcon;
	private ImageIcon textfield_addIcon;
	private ImageIcon textfield_deleteIcon;
	private ImageIcon timeIcon;
	private ImageIcon time_addIcon;
	private ImageIcon time_deleteIcon;

	private ImageIcon weather_cloudsIcon;
	private ImageIcon weather_cloudyIcon;
	private ImageIcon weather_lightningIcon;
	private ImageIcon weather_rainIcon;
	private ImageIcon weather_snowIcon;
	private ImageIcon weather_sunIcon;

	private ImageIcon web_conciergeIcon;

	private JPanel panel_1;
	private JLabel lblBottomLabel_1;
	private JLabel lblIconLabel_1;

	private JPanel panel_2;
	private JLabel lblBottomLabel_2;
	private JLabel lblIconLabel_2;

	private JPanel panel_3;
	private JLabel lblBottomLabel_3;
	private JLabel lblIconLabel_3;

	private JPanel panel_4;
	private JLabel lblBottomLabel_4;
	private JLabel lblIconLabel_4;

	private JPanel panel_5;
	private JLabel lblTopLabel_5;
	private JLabel lblBottomLabel_5;
	private JLabel lblIconLabel_5;

	private JPanel panel_6;
	private JLabel lblBottomLabel_6;
	private JLabel lblIconLabel_6;

	private JPanel panel_7;
	private JLabel lblBottomLabel_7;
	private JLabel lblIconLabel_7;

	private JPanel panel_8;
	private JLabel lblBottomLabel_8;
	private JLabel lblIconLabel_8;

	private JPanel panel_9;
	private JLabel lblBottomLabel_9;
	private JLabel lblIconLabel_9;

	private JPanel panel_10;
	private JLabel lblBottomLabel_10;
	private JLabel lblIconLabel_10;

	private JPanel panel_11;
	private JLabel lblTopLabel_11;
	private JLabel lblBottomLabel_11;
	private JLabel lblIconLabel_11;

	private JPanel panel_12;
	private JLabel lblBottomLabel_12;
	private JLabel lblIconLabel_12;

	private JPanel panel_13;
	private JLabel lblBottomLabel_13;
	private JLabel lblIconLabel_13;

	private JPanel panel_14;
	private JLabel lblBottomLabel_14;
	private JLabel lblIconLabel_14;

	private JPanel panel_15;
	private JLabel lblBottomLabel_15;
	private JLabel lblIconLabel_15;

	private JPanel panel_16;
	private JLabel lblBottomLabel_16;
	private JLabel lblIconLabel_16;

	private JPanel panel_17;
	private JLabel lblBottomLabel_17;
	private JLabel lblIconLabel_17;

	private JPanel panel_18;
	private JLabel lblTopLabel_18;
	private JLabel lblBottomLabel_18;
	private JLabel lblIconLabel_18;

	private JPanel panel_19;
	private JLabel lblBottomLabel_19;
	private JLabel lblIconLabel_19;

	private JPanel panel_20;
	private JLabel lblIconLabel_20;
	private Boolean initpanel_20 = true;

	private JPanel panel_21;
	private JLabel lblIconLabel_21;
	private Boolean initpanel_21 = true;

	/**
	 *    Raspberry Pi Pinout
	 *      3V3  (1)  (2) 5V
	 *    GPIO2  (3)  (4) 5V
	 *    GPIO3  (5)  (6) GND
	 *    GPIO4  (7)  (8) GPIO14
	 *      GND  (9) (10) GPIO15
	 *   GPIO17 (11) (12) GPIO18
	 *   GPIO27 (13) (14) GND
	 *   GPIO22 (15) (16) GPIO23
	 *      3V3 (17) (18) GPIO24
	 *   GPIO10 (19) (20) GND
	 *    GPIO9 (21) (22) GPIO25
	 *   GPIO11 (23) (24) GPIO8
	 *      GND (25) (26) GPIO7
	 *    GPIO0 (27) (28) GPIO1
	 *    GPIO5 (29) (30) GND
	 *    GPIO6 (31) (32) GPIO12
	 *   GPIO13 (33) (34) GND
	 *   GPIO19 (35) (36) GPIO16
	 *   GPIO26 (37) (38) GPIO20
	 *      GND (39) (40) GPIO21
	 *
	 * BerryClip+ - 6 LED - 2 Switch - 1 Buzzer Board
	 * Hardware Reference
	 * =============================
	 * The components are connected to the main Pi GPIO header (P1)
	 * Component  Pin       BCM    WiringPi
	 * ---------|-------|--------|---------
	 * LED 1    - P1-07 - GPIO4  - GPIO. 7
	 * LED 2    - P1-11 - GPIO17 - GPIO. 0
	 * LED 3    - P1-15 - GPIO22 - GPIO. 3
	 * LED 4    - P1-19 - GPIO10
	 * LED 5    - P1-21 - GPIO9
	 * LED 6    - P1-23 - GPIO11
	 * Buzzer   - P1-24 - GPIO8
	 * Switch 1 - P1-26 - GPIO7
	 * Swtich 2 - P1-22 - GPIO25
	 *
	 * Jam HAT - 6 LED - 2 Switch - 1 Buzzer Board
	 * The table below shows the pin numbers for BCM, Board and the matching GPIO Zero objects.
	 * |Component |GPIO.BCM | BOARD  |GPIO Zero object |WiringPi | Notes 
	 * |----------|---------|--------|-----------------|---------|
	 * | LED1     | GPIO 5  | Pin 29 | lights_1.red    | GPIO.21 |
	 * | LED2     | GPIO 6  | Pin 31 | lights_2.red    | GPIO.22 |
	 * | LED3     | GPIO 12 | Pin 32 | lights_1.yellow | GPIO.26 |
	 * | LED4     | GPIO 13 | Pin 33 | lights_2.yellow | GPIO.23 |
	 * | LED5     | GPIO 16 | Pin 36 | lights_1.green  | GPIO.27 |
	 * | LED6     | GPIO 17 | Pin 11 | lights_2.green  | GPIO. 0 |
	 * | Button 1 | GPIO 19 | Pin 35 | button_1        | GPIO.24 | Connected to R8/R10 
	 * | Button 2 | GPIO 18 | Pin 12 | button_2        | GPIO. 1 | Connected to R7/R9 
	 * | Buzzer   | GPIO 20 | Pin 38 | buzzer          | GPIO.28 |
	 *
	 * Wiring Pi - GPIO Interface library for the Raspberry Pi
	 * +-----+-----+---------+------+---+---Pi 4B--+---+------+---------+-----+-----+
	 * | BCM | wPi |   Name  | Mode | V | Physical | V | Mode | Name    | wPi | BCM |
 	 * +-----+-----+---------+------+---+----++----+---+------+---------+-----+-----+
	 * |     |     |    3.3v |      |   |  1 || 2  |   |      | 5v      |     |     |
	 * |   2 |   8 |   SDA.1 |   IN | 1 |  3 || 4  |   |      | 5v      |     |     |
	 * |   3 |   9 |   SCL.1 |   IN | 1 |  5 || 6  |   |      | 0v      |     |     |
	 * |   4 |   7 | GPIO. 7 |   IN | 1 |  7 || 8  | 1 | IN   | TxD     | 15  | 14  |
	 * |     |     |      0v |      |   |  9 || 10 | 1 | IN   | RxD     | 16  | 15  |
	 * |  17 |   0 | GPIO. 0 |  OUT | 0 | 11 || 12 | 0 | OUT  | GPIO. 1 | 1   | 18  |
	 * |  27 |   2 | GPIO. 2 |   IN | 0 | 13 || 14 |   |      | 0v      |     |     |
	 * |  22 |   3 | GPIO. 3 |  OUT | 0 | 15 || 16 | 0 | IN   | GPIO. 4 | 4   | 23  |
	 * |     |     |    3.3v |      |   | 17 || 18 | 0 | OUT  | GPIO. 5 | 5   | 24  |
	 * |  10 |  12 |    MOSI |   IN | 0 | 19 || 20 |   |      | 0v      |     |     |
	 * |   9 |  13 |    MISO |   IN | 0 | 21 || 22 | 1 | OUT  | GPIO. 6 | 6   | 25  |
	 * |  11 |  14 |    SCLK |   IN | 0 | 23 || 24 | 1 | IN   | CE0     | 10  | 8   |
	 * |     |     |      0v |      |   | 25 || 26 | 1 | IN   | CE1     | 11  | 7   |
	 * |   0 |  30 |   SDA.0 |   IN | 1 | 27 || 28 | 1 | IN   | SCL.0   | 31  | 1   |
	 * |   5 |  21 | GPIO.21 |  OUT | 0 | 29 || 30 |   |      | 0v      |     |     |
	 * |   6 |  22 | GPIO.22 |  OUT | 0 | 31 || 32 | 0 | OUT  | GPIO.26 | 26  | 12  |
	 * |  13 |  23 | GPIO.23 |  OUT | 0 | 33 || 34 |   |      | 0v      |     |     |
	 * |  19 |  24 | GPIO.24 |   IN | 0 | 35 || 36 | 1 | OUT  | GPIO.27 | 27  | 16  |
	 * |  26 |  25 | GPIO.25 |   IN | 0 | 37 || 38 | 0 | IN   | GPIO.28 | 28  | 20  |
	 * |     |     |      0v |      |   | 39 || 40 | 0 | IN   | GPIO.29 | 29  | 21  |
	 * +-----+-----+---------+------+---+----++----+---+------+---------+-----+-----+
	 * | BCM | wPi |   Name  | Mode | V | Physical | V | Mode | Name    | wPi | BCM |
	 * +-----+-----+---------+------+---+---Pi 4B--+---+------+---------+-----+-----+
	 */

    GpioController gpio;

    // provision gpio pin #01 & #03 as an output pins and blink
    GpioPinDigitalOutput led1; // GPIO pin GPIO05 (LED pin29)
    GpioPinDigitalOutput led2; // GPIO pin GPIO12 (LED pin32)

    // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
    GpioPinDigitalInput switch1; // GPIO pin GPIO19 (Switch pin35)
    GpioPinDigitalInput switch2; // GPIO pin GPIO18 (Switch pin12)
	
    // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
    GpioPinDigitalInput button1;
    
	public IoTTiles(boolean exitOnClose) {
		this.frameIoT = buildFrame(exitOnClose);
		IoTTiles.IOTTILES_INSTANCE = this;
	}

	public static IoTTiles getInstance() {
		return IOTTILES_INSTANCE;
	}

	// Initialize the contents of the frame
	private JFrame buildFrame(boolean exitOnClose) {
		JFrame frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				windowClosingAction(e);
			}
		});
		frame.setResizable(false);

		frame.getContentPane().setBackground(Color.BLACK);
		frame.setTitle(
				"Raspberry Pi IoT Tiles :: Internet of Things Drools-jBPM using Raspberry Pi IoT Tron AI-IoTBPM");
		frame.setBounds(100, 100, 953, 465);
		frame.setDefaultCloseOperation(exitOnClose ? JFrame.EXIT_ON_CLOSE : WindowConstants.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null); // Center in screen

		alarm_bellIcon = new ImageIcon("icons" + File.separator + "alarm_bell.png");
		autosIcon = new ImageIcon("icons" + File.separator + "autos.png");
		biosIcon = new ImageIcon("icons" + File.separator + "bios.png");
		bluetoothIcon = new ImageIcon("icons" + File.separator + "bluetooth.png");
		bulbIcon = new ImageIcon("icons" + File.separator + "bulb.png");

		cameraIcon = new ImageIcon("icons" + File.separator + "camera.png");
		camera_addIcon = new ImageIcon("icons" + File.separator + "camera_add.png");
		camera_errorIcon = new ImageIcon("icons" + File.separator + "camera_error.png");

		celsiusIcon = new ImageIcon("icons" + File.separator + "celsius.png");
		computerIcon = new ImageIcon("icons" + File.separator + "computer.png");
		computer_addIcon = new ImageIcon("icons" + File.separator + "computer_add.png");
		computer_deleteIcon = new ImageIcon("icons" + File.separator + "computer_delete.png");
		computer_keyIcon = new ImageIcon("icons" + File.separator + "computer_key.png");

		doorIcon = new ImageIcon("icons" + File.separator + "door.png");
		door_inIcon = new ImageIcon("icons" + File.separator + "door_in.png");
		door_openIcon = new ImageIcon("icons" + File.separator + "door_open.png");
		door_outIcon = new ImageIcon("icons" + File.separator + "door_out.png");

		emblem_systemIcon = new ImageIcon("icons" + File.separator + "emblem_system.png");
		exclamationIcon = new ImageIcon("icons" + File.separator + "exclamation.png");
		informationIcon = new ImageIcon("icons" + File.separator + "information.png");
		keyIcon = new ImageIcon("icons" + File.separator + "key.png");
		key_addIcon = new ImageIcon("icons" + File.separator + "key_add.png");

		lightbulbIcon = new ImageIcon("icons" + File.separator + "lightbulb.png");
		lightbulb_addIcon = new ImageIcon("icons" + File.separator + "lightbulb_add.png");
		lightbulb_deleteIcon = new ImageIcon("icons" + File.separator + "lightbulb_delete.png");
		lightbulb_offIcon = new ImageIcon("icons" + File.separator + "lightbulb_off.png");

		lockIcon = new ImageIcon("icons" + File.separator + "lock.png");
		lock_addIcon = new ImageIcon("icons" + File.separator + "lock_add.png");
		lock_breakIcon = new ImageIcon("icons" + File.separator + "lock_break.png");
		lock_openIcon = new ImageIcon("icons" + File.separator + "lock_open.png");

		notification_bellIcon = new ImageIcon("icons" + File.separator + "notification_bell.png");
		personalIcon = new ImageIcon("icons" + File.separator + "personal.png");
		personal2Icon = new ImageIcon("icons" + File.separator + "personal2.png");
		phone_openIcon = new ImageIcon("icons" + File.separator + "phone_open.png");

		textfieldIcon = new ImageIcon("icons" + File.separator + "textfield.png");
		textfield_addIcon = new ImageIcon("icons" + File.separator + "textfield_add.png");
		textfield_deleteIcon = new ImageIcon("icons" + File.separator + "textfield_delete.png");
		timeIcon = new ImageIcon("icons" + File.separator + "time.png");
		time_addIcon = new ImageIcon("icons" + File.separator + "time_add.png");
		time_deleteIcon = new ImageIcon("icons" + File.separator + "time_delete.png");

		weather_cloudsIcon = new ImageIcon("icons" + File.separator + "weather_clouds.png");
		weather_cloudyIcon = new ImageIcon("icons" + File.separator + "weather_cloudy.png");
		weather_lightningIcon = new ImageIcon("icons" + File.separator + "weather_lightning.png");
		weather_rainIcon = new ImageIcon("icons" + File.separator + "weather_rain.png");
		weather_snowIcon = new ImageIcon("icons" + File.separator + "weather_snow.png");
		weather_sunIcon = new ImageIcon("icons" + File.separator + "weather_sun.png");

		web_conciergeIcon = new ImageIcon("icons" + File.separator + "web_concierge.png");

		panel_1 = new JPanel();
		panel_1.setToolTipText(
				"Raspberry Pi IoT Tiles control smart office automation and monitoring is a control panel (dashboard) for Raspberry Pi IoT Tron Things, like the Raspberry Pi Tron IoT Web Camera.");
		panel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_1Clicked(e);
			}
		});
		panel_1.setBackground(Color.BLUE);
		panel_1.setBounds(5, 5, 205, 100);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		JLabel lblTopLabel_1 = new JLabel("Raspberry Pi IoT Web Camera");
		lblTopLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTopLabel_1.setForeground(Color.WHITE);
		panel_1.add(lblTopLabel_1, BorderLayout.NORTH);

		lblBottomLabel_1 = new JLabel("Video Stream");
		lblBottomLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBottomLabel_1.setForeground(Color.WHITE);
		panel_1.add(lblBottomLabel_1, BorderLayout.SOUTH);

		lblIconLabel_1 = new JLabel("");
		lblIconLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblIconLabel_1.setForeground(Color.WHITE);
		lblIconLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblIconLabel_1, BorderLayout.CENTER);
		lblIconLabel_1.setIcon(cameraIcon);

		panel_2 = new JPanel();
		panel_2.setToolTipText("The Raspberry Pi IoT Things activity and behavior is fully control from IoT Tiles.");
		panel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_2Clicked(e);
			}
		});
		panel_2.setBackground(Color.MAGENTA);
		panel_2.setBounds(215, 5, 205, 100);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		JLabel lblTopLabel_2 = new JLabel("Raspberry Pi Mode");
		lblTopLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTopLabel_2.setForeground(Color.WHITE);
		panel_2.add(lblTopLabel_2, BorderLayout.NORTH);

		lblBottomLabel_2 = new JLabel("Active");
		lblBottomLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBottomLabel_2.setForeground(Color.WHITE);
		panel_2.add(lblBottomLabel_2, BorderLayout.SOUTH);

		lblIconLabel_2 = new JLabel("");
		lblIconLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblIconLabel_2.setForeground(Color.WHITE);
		lblIconLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblIconLabel_2, BorderLayout.CENTER);
		lblIconLabel_2.setIcon(computerIcon);

		panel_3 = new JPanel();
		panel_3.setToolTipText(
				"The IoT Door RFID-RC522 Smart Card Reader Sensor can alert you to individuals or equipment location and movement.");
		panel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_3Clicked(e);
			}
		});
		panel_3.setBackground(new Color(199, 21, 133));
		panel_3.setBounds(425, 5, 100, 100);
		frame.getContentPane().add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));

		JLabel lblTopLabel_3 = new JLabel("Employee");
		lblTopLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTopLabel_3.setForeground(Color.WHITE);
		panel_3.add(lblTopLabel_3, BorderLayout.NORTH);

		lblBottomLabel_3 = new JLabel("Present");
		lblBottomLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBottomLabel_3.setForeground(Color.WHITE);
		panel_3.add(lblBottomLabel_3, BorderLayout.SOUTH);

		lblIconLabel_3 = new JLabel("");
		lblIconLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblIconLabel_3.setForeground(Color.WHITE);
		lblIconLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(lblIconLabel_3, BorderLayout.CENTER);
		lblIconLabel_3.setIcon(personalIcon);

		panel_4 = new JPanel();
		panel_4.setToolTipText(
				"Raspberry Pi IoT Tiles control smart office automation and monitoring, including IoT smart desks in your office.");
		panel_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_4Clicked(e);
			}
		});
		panel_4.setBackground(Color.RED);
		panel_4.setBounds(740, 5, 205, 100);
		frame.getContentPane().add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));

		JLabel lblTopLabel_4 = new JLabel("Smart Office Monitor");
		lblTopLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTopLabel_4.setForeground(Color.WHITE);
		panel_4.add(lblTopLabel_4, BorderLayout.NORTH);

		lblBottomLabel_4 = new JLabel("Office Day");
		lblBottomLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBottomLabel_4.setForeground(Color.WHITE);
		panel_4.add(lblBottomLabel_4, BorderLayout.SOUTH);

		lblIconLabel_4 = new JLabel("");
		lblIconLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblIconLabel_4.setForeground(Color.WHITE);
		lblIconLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_4.add(lblIconLabel_4, BorderLayout.CENTER);
		lblIconLabel_4.setIcon(time_addIcon);

		panel_5 = new JPanel();
		panel_5.setToolTipText(
				"The IoT DHT11 WiFi wireless module sends temperature and humidity environment information to the IoT Tiles Panel and Tron IoT Display.");
		panel_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_5Clicked(e);
			}
		});
		panel_5.setBackground(Color.RED);
		panel_5.setBounds(5, 110, 205, 100);
		frame.getContentPane().add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));

		lblTopLabel_5 = new JLabel("Office Temperature");
		lblTopLabel_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTopLabel_5.setForeground(Color.WHITE);
		panel_5.add(lblTopLabel_5, BorderLayout.NORTH);

		lblBottomLabel_5 = new JLabel("DHT11 Temperature Humidity");
		lblBottomLabel_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBottomLabel_5.setForeground(Color.WHITE);
		panel_5.add(lblBottomLabel_5, BorderLayout.SOUTH);

		lblIconLabel_5 = new JLabel("72' 36%");
		lblIconLabel_5.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		lblIconLabel_5.setForeground(Color.WHITE);
		lblIconLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel_5.add(lblIconLabel_5, BorderLayout.CENTER);
		lblIconLabel_5.setIcon(celsiusIcon);

		panel_6 = new JPanel();
		panel_6.setToolTipText(
				"The IoT ESP-01S WiFi Relay Expansion Module board is a simple to operate a relay to control a door lock wirelessly.");
		panel_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_6Clicked(e);
			}
		});
		panel_6.setBackground(Color.LIGHT_GRAY);
		panel_6.setBounds(215, 110, 100, 100);
		frame.getContentPane().add(panel_6);
		panel_6.setLayout(new BorderLayout(0, 0));

		JLabel lblTopLabel_6 = new JLabel("Front Door");
		lblTopLabel_6.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTopLabel_6.setForeground(Color.WHITE);
		panel_6.add(lblTopLabel_6, BorderLayout.NORTH);

		lblBottomLabel_6 = new JLabel("Unlocked");
		lblBottomLabel_6.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBottomLabel_6.setForeground(Color.WHITE);
		panel_6.add(lblBottomLabel_6, BorderLayout.SOUTH);

		lblIconLabel_6 = new JLabel("");
		lblIconLabel_6.setForeground(Color.WHITE);
		lblIconLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(lblIconLabel_6, BorderLayout.CENTER);
		lblIconLabel_6.setIcon(lock_openIcon);

		panel_7 = new JPanel();
		panel_7.setToolTipText(
				"The IoT Door Open Sensor jBPM Automation process is triggered by an Raspberry Pi contact switch event.");
		panel_7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_7Clicked(e);
			}
		});
		panel_7.setBackground(Color.BLUE);
		panel_7.setBounds(320, 110, 100, 100);
		frame.getContentPane().add(panel_7);
		panel_7.setLayout(new BorderLayout(0, 0));

		JLabel lblTopLabel_7 = new JLabel("Front Door");
		lblTopLabel_7.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTopLabel_7.setForeground(Color.WHITE);
		panel_7.add(lblTopLabel_7, BorderLayout.NORTH);

		lblBottomLabel_7 = new JLabel("Closed");
		lblBottomLabel_7.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBottomLabel_7.setForeground(Color.WHITE);
		panel_7.add(lblBottomLabel_7, BorderLayout.SOUTH);

		lblIconLabel_7 = new JLabel("");
		lblIconLabel_7.setForeground(Color.WHITE);
		lblIconLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
		panel_7.add(lblIconLabel_7, BorderLayout.CENTER);
		lblIconLabel_7.setIcon(doorIcon);

		panel_8 = new JPanel();
		panel_8.setToolTipText(
				"The IoT ESP-01S WiFi Relay Expansion Module board is a simple to operate a relay to control a door lock wirelessly.");
		panel_8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_8Clicked(e);
			}
		});
		panel_8.setBackground(new Color(128, 128, 128));
		panel_8.setBounds(425, 110, 100, 100);
		frame.getContentPane().add(panel_8);
		panel_8.setLayout(new BorderLayout(0, 0));

		JLabel lblTopLabel_8 = new JLabel("Lobby Door");
		lblTopLabel_8.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTopLabel_8.setForeground(Color.WHITE);
		panel_8.add(lblTopLabel_8, BorderLayout.NORTH);

		lblBottomLabel_8 = new JLabel("Unlocked");
		lblBottomLabel_8.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBottomLabel_8.setForeground(Color.WHITE);
		panel_8.add(lblBottomLabel_8, BorderLayout.SOUTH);

		lblIconLabel_8 = new JLabel("");
		lblIconLabel_8.setForeground(Color.WHITE);
		lblIconLabel_8.setHorizontalAlignment(SwingConstants.CENTER);
		panel_8.add(lblIconLabel_8, BorderLayout.CENTER);
		lblIconLabel_8.setIcon(lock_openIcon);

		panel_9 = new JPanel();
		panel_9.setToolTipText(
				"The IoT Door Open Sensor jBPM Automation process is triggered by an Raspberry Pi contact switch event.");
		panel_9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_9Clicked(e);
			}
		});
		panel_9.setBackground(Color.BLUE);
		panel_9.setBounds(740, 110, 100, 100);
		frame.getContentPane().add(panel_9);
		panel_9.setLayout(new BorderLayout(0, 0));

		JLabel lblTopLabel_9 = new JLabel("Lobby Door");
		lblTopLabel_9.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTopLabel_9.setForeground(Color.WHITE);
		panel_9.add(lblTopLabel_9, BorderLayout.NORTH);

		lblBottomLabel_9 = new JLabel("Closed");
		lblBottomLabel_9.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBottomLabel_9.setForeground(Color.WHITE);
		panel_9.add(lblBottomLabel_9, BorderLayout.SOUTH);

		lblIconLabel_9 = new JLabel("");
		lblIconLabel_9.setForeground(Color.WHITE);
		lblIconLabel_9.setHorizontalAlignment(SwingConstants.CENTER);
		panel_9.add(lblIconLabel_9, BorderLayout.CENTER);
		lblIconLabel_9.setIcon(doorIcon);

		panel_10 = new JPanel();
		panel_10.setToolTipText(
				"The IoT ESP-01S WiFi Relay Expansion Module board is a simple and easy-to-use expansion board that uses the ESP-01S breakout board to drive a relay and operate devices or machines wirelessly.");
		panel_10.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_10Clicked(e);
			}
		});
		panel_10.setBackground(new Color(0, 128, 0));
		panel_10.setBounds(845, 110, 100, 205);
		frame.getContentPane().add(panel_10);
		panel_10.setLayout(new BorderLayout(0, 0));

		JLabel lblTopLabel_10 = new JLabel("Device Control");
		lblTopLabel_10.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTopLabel_10.setForeground(Color.WHITE);
		panel_10.add(lblTopLabel_10, BorderLayout.NORTH);

		lblBottomLabel_10 = new JLabel("Light Off");
		lblBottomLabel_10.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBottomLabel_10.setForeground(Color.WHITE);
		panel_10.add(lblBottomLabel_10, BorderLayout.SOUTH);

		lblIconLabel_10 = new JLabel("");
		lblIconLabel_10.setForeground(Color.WHITE);
		lblIconLabel_10.setHorizontalAlignment(SwingConstants.CENTER);
		panel_10.add(lblIconLabel_10, BorderLayout.CENTER);
		lblIconLabel_10.setIcon(lightbulb_offIcon);

		panel_11 = new JPanel();
		panel_11.setToolTipText(
				"The IoT Kiosk is a complete cloud-connected Kiosk architecture that integrates all your IoT Device and enterprise computer systems.");
		panel_11.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_11Clicked(e);
			}
		});
		panel_11.setBackground(new Color(244, 164, 96));
		panel_11.setBounds(5, 215, 100, 205);
		frame.getContentPane().add(panel_11);
		panel_11.setLayout(new BorderLayout(0, 0));

		lblTopLabel_11 = new JLabel("IoT Kiosk Cloud");
		lblTopLabel_11.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTopLabel_11.setForeground(Color.WHITE);
		panel_11.add(lblTopLabel_11, BorderLayout.NORTH);

		lblBottomLabel_11 = new JLabel("Kiosk Status");
		lblBottomLabel_11.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBottomLabel_11.setForeground(Color.WHITE);
		panel_11.add(lblBottomLabel_11, BorderLayout.SOUTH);

		lblIconLabel_11 = new JLabel("");
		lblIconLabel_11.setForeground(Color.WHITE);
		lblIconLabel_11.setHorizontalAlignment(SwingConstants.CENTER);
		panel_11.add(lblIconLabel_11, BorderLayout.CENTER);
		lblIconLabel_11.setIcon(informationIcon);

		panel_12 = new JPanel();
		panel_12.setToolTipText(
				"The Raspberry Pi IoT is used connect to Office Door Locks, Activate Security Alarms, Turn Office Lights: ON, Control Thermostats, Answer Doorbell, Open Window Shades, Activate Motion Sensors.");
		panel_12.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_12Clicked(e);
			}
		});
		panel_12.setBackground(Color.DARK_GRAY);
		panel_12.setBounds(110, 215, 100, 100);
		frame.getContentPane().add(panel_12);
		panel_12.setLayout(new BorderLayout(0, 0));

		JLabel lblTopLabel_12 = new JLabel("IoT Message");
		lblTopLabel_12.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTopLabel_12.setForeground(Color.WHITE);
		panel_12.add(lblTopLabel_12, BorderLayout.NORTH);

		lblBottomLabel_12 = new JLabel("Status");
		lblBottomLabel_12.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBottomLabel_12.setForeground(Color.WHITE);
		panel_12.add(lblBottomLabel_12, BorderLayout.SOUTH);

		lblIconLabel_12 = new JLabel("");
		lblIconLabel_12.setForeground(Color.WHITE);
		lblIconLabel_12.setHorizontalAlignment(SwingConstants.CENTER);
		panel_12.add(lblIconLabel_12, BorderLayout.CENTER);
		lblIconLabel_12.setIcon(bluetoothIcon);

		panel_13 = new JPanel();
		panel_13.setToolTipText(
				"The Raspberry Pi IoT Display is a small device that can sit on your desk or be mounted to a wall. It provides situational awareness, alerts and notification messages from your IoT devices.");
		panel_13.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_13Clicked(e);
			}
		});
		panel_13.setBackground(new Color(0, 128, 0));
		panel_13.setBounds(215, 215, 205, 100);
		frame.getContentPane().add(panel_13);
		panel_13.setLayout(new BorderLayout(0, 0));

		JLabel lblTopLabel_13 = new JLabel("Raspberry Pi IoT Display");
		lblTopLabel_13.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTopLabel_13.setForeground(Color.WHITE);
		panel_13.add(lblTopLabel_13, BorderLayout.NORTH);

		lblBottomLabel_13 = new JLabel("Send Tron IoT Message");
		lblBottomLabel_13.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBottomLabel_13.setForeground(Color.WHITE);
		panel_13.add(lblBottomLabel_13, BorderLayout.SOUTH);

		lblIconLabel_13 = new JLabel("");
		lblIconLabel_13.setForeground(Color.WHITE);
		lblIconLabel_13.setHorizontalAlignment(SwingConstants.CENTER);
		panel_13.add(lblIconLabel_13, BorderLayout.CENTER);
		lblIconLabel_13.setIcon(phone_openIcon);

		panel_14 = new JPanel();
		panel_14.setToolTipText(
				"The Raspberry Pi Wireless Alert Sensor provides for audio (door chime) and can be integrated with any wireless security system, driveway alarms, motion sensor, delivery detect alerts, wireless door entry chime, doorbell or panic button alarms.");
		panel_14.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_14Clicked(e);
			}
		});
		panel_14.setBackground(Color.BLUE);
		panel_14.setBounds(425, 215, 100, 100);
		frame.getContentPane().add(panel_14);
		panel_14.setLayout(new BorderLayout(0, 0));

		JLabel lblTopLabel_14 = new JLabel("Door Open");
		lblTopLabel_14.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTopLabel_14.setForeground(Color.WHITE);
		panel_14.add(lblTopLabel_14, BorderLayout.NORTH);

		lblBottomLabel_14 = new JLabel("Chime Signal");
		lblBottomLabel_14.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBottomLabel_14.setForeground(Color.WHITE);
		panel_14.add(lblBottomLabel_14, BorderLayout.SOUTH);

		lblIconLabel_14 = new JLabel("");
		lblIconLabel_14.setForeground(Color.WHITE);
		lblIconLabel_14.setHorizontalAlignment(SwingConstants.CENTER);
		panel_14.add(lblIconLabel_14, BorderLayout.CENTER);
		lblIconLabel_14.setIcon(notification_bellIcon);

		panel_15 = new JPanel();
		panel_15.setToolTipText(
				"The Raspberry Pi IoT Dash Button is a programmable button based on the Raspberry Pi WiFi Dash Button alert and can be configured in Raspberry Pi AI-IoTBPM Drools-jBPM Server IoT cloud services.");
		panel_15.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_15Clicked(e);
			}
		});
		panel_15.setBackground(Color.DARK_GRAY);
		panel_15.setBounds(740, 215, 100, 205);
		frame.getContentPane().add(panel_15);
		panel_15.setLayout(new BorderLayout(0, 0));

		JLabel lblTopLabel_15 = new JLabel("Dash Button");
		lblTopLabel_15.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTopLabel_15.setForeground(Color.WHITE);
		panel_15.add(lblTopLabel_15, BorderLayout.NORTH);

		lblBottomLabel_15 = new JLabel("IoT Chime");
		lblBottomLabel_15.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBottomLabel_15.setForeground(Color.WHITE);
		panel_15.add(lblBottomLabel_15, BorderLayout.SOUTH);

		lblIconLabel_15 = new JLabel("");
		lblIconLabel_15.setForeground(Color.WHITE);
		lblIconLabel_15.setHorizontalAlignment(SwingConstants.CENTER);
		panel_15.add(lblIconLabel_15, BorderLayout.CENTER);
		lblIconLabel_15.setIcon(web_conciergeIcon);

		panel_16 = new JPanel();
		panel_16.setToolTipText(
				"This is the default IoT Sensors jBPM Automation extended process. Use Drools Rules to start a specific IoT jBPM Automation process for this IoT sensor event.");
		panel_16.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_16Clicked(e);
			}
		});
		panel_16.setBackground(Color.BLUE);
		panel_16.setBounds(110, 320, 100, 100);
		frame.getContentPane().add(panel_16);
		panel_16.setLayout(new BorderLayout(0, 0));

		JLabel lblTopLabel_16 = new JLabel("IoT Sensors");
		lblTopLabel_16.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTopLabel_16.setForeground(Color.WHITE);
		panel_16.add(lblTopLabel_16, BorderLayout.NORTH);

		lblBottomLabel_16 = new JLabel("Monitor");
		lblBottomLabel_16.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBottomLabel_16.setForeground(Color.WHITE);
		panel_16.add(lblBottomLabel_16, BorderLayout.SOUTH);

		lblIconLabel_16 = new JLabel("");
		lblIconLabel_16.setForeground(Color.WHITE);
		lblIconLabel_16.setHorizontalAlignment(SwingConstants.CENTER);
		panel_16.add(lblIconLabel_16, BorderLayout.CENTER);
		lblIconLabel_16.setIcon(emblem_systemIcon);

		panel_17 = new JPanel();
		panel_17.setToolTipText(
				"The Raspberry Pi IoT Dash Button is a programmable button based on the Raspberry Pi WiFi Dash Button alert and can be configured in Raspberry Pi AI-IoTBPM Drools-jBPM Server IoT cloud services.");
		panel_17.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_17Clicked(e);
			}
		});
		panel_17.setBackground(Color.GRAY);
		panel_17.setBounds(215, 320, 100, 100);
		frame.getContentPane().add(panel_17);
		panel_17.setLayout(new BorderLayout(0, 0));

		JLabel lblTopLabel_17 = new JLabel("Dash Button");
		lblTopLabel_17.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTopLabel_17.setForeground(Color.WHITE);
		panel_17.add(lblTopLabel_17, BorderLayout.NORTH);

		lblBottomLabel_17 = new JLabel("Button Press");
		lblBottomLabel_17.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBottomLabel_17.setForeground(Color.WHITE);
		panel_17.add(lblBottomLabel_17, BorderLayout.SOUTH);

		lblIconLabel_17 = new JLabel("");
		lblIconLabel_17.setForeground(Color.WHITE);
		lblIconLabel_17.setHorizontalAlignment(SwingConstants.CENTER);
		panel_17.add(lblIconLabel_17, BorderLayout.CENTER);
		lblIconLabel_17.setIcon(web_conciergeIcon);

		panel_18 = new JPanel();
		panel_18.setToolTipText(
				"IoT DHT11 Temperature and Humidity WiFi Module Wireless Module ESP-01S sends temperature and humidity environment information to the IoT Tiles Panel and Tron IoT Display.");
		panel_18.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_18Clicked(e);
			}
		});
		panel_18.setBackground(Color.RED);
		panel_18.setBounds(320, 320, 205, 100);
		frame.getContentPane().add(panel_18);
		panel_18.setLayout(new BorderLayout(0, 0));

		lblTopLabel_18 = new JLabel("Outside Temperature");
		lblTopLabel_18.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTopLabel_18.setForeground(Color.WHITE);
		panel_18.add(lblTopLabel_18, BorderLayout.NORTH);

		lblBottomLabel_18 = new JLabel("DHT11 Temperature Humidity");
		lblBottomLabel_18.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBottomLabel_18.setForeground(Color.WHITE);
		panel_18.add(lblBottomLabel_18, BorderLayout.SOUTH);

		lblIconLabel_18 = new JLabel("69' 32%");
		lblIconLabel_18.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		lblIconLabel_18.setForeground(Color.WHITE);
		lblIconLabel_18.setHorizontalAlignment(SwingConstants.CENTER);
		panel_18.add(lblIconLabel_18, BorderLayout.CENTER);
		lblIconLabel_18.setIcon(weather_sunIcon);

		panel_19 = new JPanel();
		panel_19.setToolTipText(
				"The Raspberry Pi Web Server is a cloud-connected complete SoC System on a Chip architecture that integrates all components of a computer.");
		panel_19.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_19Clicked(e);
			}
		});
		panel_19.setBackground(new Color(0, 191, 255));
		panel_19.setBounds(845, 320, 100, 100);
		frame.getContentPane().add(panel_19);
		panel_19.setLayout(new BorderLayout(0, 0));

		JLabel lblTopLabel_19 = new JLabel("Lobby Light");
		lblTopLabel_19.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTopLabel_19.setForeground(Color.WHITE);
		panel_19.add(lblTopLabel_19, BorderLayout.NORTH);

		lblBottomLabel_19 = new JLabel("Relay Module");
		lblBottomLabel_19.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBottomLabel_19.setForeground(Color.WHITE);
		panel_19.add(lblBottomLabel_19, BorderLayout.SOUTH);

		lblIconLabel_19 = new JLabel("");
		lblIconLabel_19.setForeground(Color.WHITE);
		lblIconLabel_19.setHorizontalAlignment(SwingConstants.CENTER);
		panel_19.add(lblIconLabel_19, BorderLayout.CENTER);
		lblIconLabel_19.setIcon(lightbulb_offIcon);

		panel_20 = new JPanel();
		panel_20.setToolTipText(
				"Raspberry Pi IoT Tiles control smart office automation and monitoring is a control panel (dashboard) for Raspberry Pi IoT Things, like the Raspberry Pi IoT Web Camera.");
		panel_20.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_20Clicked(e);
			}
		});
		panel_20.setBackground(Color.LIGHT_GRAY);
		panel_20.setBounds(530, 5, 205, 205);
		frame.getContentPane().add(panel_20);
		panel_20.setLayout(new BorderLayout(0, 0));
  		panel_20Clicked(null);

		panel_21 = new JPanel();
		panel_21.setToolTipText(
				"Raspberry Pi IoT Tiles control smart office automation and monitoring is a control panel (dashboard) for Raspberry Pi IoT Things, like the Raspberry Pi IoT Web Camera.");
		panel_21.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_21Clicked(e);
			}
		});
		panel_21.setBackground(Color.LIGHT_GRAY);
		panel_21.setBounds(530, 215, 205, 205);
		frame.getContentPane().add(panel_21);
		panel_21.setLayout(new BorderLayout(0, 0));
  		panel_21Clicked(null);

		gpioController();

		if ((RPiIoTTiles.gpio == "") || (RPiIoTTiles.gpio.indexOf("none") != -1)) {
			System.err.println(
					"Note: create gpio controller e.g. gpio=GPIO_01 not defined in iotbpm.properties file.");
		} else {
			gpioSwitchState();
		}
		
		return frame;
	}

	public void gpioSwitchState() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (alive) {
					if (switch1.getState().isHigh()) {
						panel_7Blink();
					}
					if (switch2.getState().isHigh()) {
						panel_9Blink();
					}
					try {
						Thread.sleep(900L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	public void show() {
		this.frameIoT.setVisible(true);
	}

	// Raspberry Pi IoT Tiles
	public void panel_1Clicked(MouseEvent e) {
		String webCamURL = com.piiottiles.server.AgentConnect.getInstance().agentURL("WebCam1");
		if ((webCamURL == "") || (webCamURL.indexOf("0.0.0.0") != -1)) {
			lblIconLabel_1.setIcon(camera_errorIcon);
			return;
		}
  		WebBrowser wb = new WebBrowser();
  		wb.url(webCamURL);
		lblIconLabel_1.setIcon(camera_addIcon);
	}

	// Raspberry Pi Mode
	public void panel_2Clicked(MouseEvent e) {
		String mode = com.piiottiles.model.StateList.getInstance().getState("Mode");
		if (mode.indexOf("Lock") != -1) {
			com.piiottiles.server.AgentConnect.getInstance().sendPost("TronIoT",
					"&textMessage=Arduino_Tron_Active");					
  			com.piiottiles.model.StateList.getInstance().putState("Mode", "Active");
			lblBottomLabel_2.setText("Active");
			lblIconLabel_2.setIcon(computerIcon);
		} else {
			com.piiottiles.server.AgentConnect.getInstance().sendPost("TronIoT",
					"textMessage=Arduino_Tron_Lock");
  			com.piiottiles.model.StateList.getInstance().putState("Mode", "Lock");
			lblBottomLabel_2.setText("Lock");
			lblIconLabel_2.setIcon(computer_keyIcon);
		}
	}

//	String alert = com.piiottiles.model.StateList.getInstance().getState("Alert");
//	if (alert.indexOf("Quite") != -1) {
//		com.piiottiles.server.AgentConnect.getInstance().sendPost("TronIoT",
//				"&textMessage=*_Tiles_Alert_Active");
//		com.piiottiles.model.StateList.getInstance().putState("Alert", "Active");
//		lblBottomLabel_1.setText("Alert Active");
//		lblIconLabel_1.setIcon(textfield_addIcon);
//	} else {
// 		com.piiottiles.server.AgentConnect.getInstance().sendPost("TronIoT",
//				"&textMessage=*_Tiles_Quite_Alert");
//		com.piiottiles.model.StateList.getInstance().putState("Alert", "Quite");
//		lblBottomLabel_1.setText("Quite Alert");
//		lblIconLabel_1.setIcon(textfield_deleteIcon);
//	} 	

	// RFID-RC522 Smart Card
	public void panel_3Clicked(MouseEvent e) {
		String personal = com.piiottiles.model.StateList.getInstance().getState("Personal");
		if (personal.indexOf("Occupied") != -1) {
			com.piiottiles.server.AgentConnect.getInstance().sendPost("TronIoT",
					"&textMessage=Employee_Present");			
  			com.piiottiles.model.StateList.getInstance().putState("Personal", "Present");
			lblBottomLabel_3.setText("Present");
			lblIconLabel_3.setIcon(personalIcon);
		} else {
			com.piiottiles.server.AgentConnect.getInstance().sendPost("TronIoT",
					"&textMessage=Employee_Occupied");
  			com.piiottiles.model.StateList.getInstance().putState("Personal", "Occupied");
			lblBottomLabel_3.setText("Occupied");
			lblIconLabel_3.setIcon(personal2Icon);
		}
	}

	// Smart Office Monitor
	public void panel_4Clicked(MouseEvent e) {
		String office = com.piiottiles.model.StateList.getInstance().getState("Office");
		if (office.indexOf("Night") != -1) {
			com.piiottiles.server.AgentConnect.getInstance().sendPost("TronIoT",
					"&textMessage=Office_Day_Mode");
  			com.piiottiles.model.StateList.getInstance().putState("Office", "Day");
			lblBottomLabel_4.setText("Office Day");
			lblIconLabel_4.setIcon(time_addIcon);
		} else {
			com.piiottiles.server.AgentConnect.getInstance().sendPost("TronIoT",
					"&textMessage=Office_Night_Mode");
  			com.piiottiles.model.StateList.getInstance().putState("Office", "Night");
			lblBottomLabel_4.setText("Office Night");
			lblIconLabel_4.setIcon(time_deleteIcon);
		}
	}

	// Office Temperature
	public void panel_5Clicked(MouseEvent e) {
		JOptionPane.showMessageDialog(null,
				"The IoT DHT11 WiFi wireless module sends temperature and humidity environment information to the IoT Tiles Panel and Tron IoT Display.",
				"IoT DHT11 Temperature Humidity Sensor", JOptionPane.INFORMATION_MESSAGE);
	}

	public void panel_5Temp(String temp) {
		lblIconLabel_5.setText(temp);
		panel_5Blink();
	}

	public void panel_5Temp(String topLabel, String temp) {
		lblTopLabel_5.setText(topLabel);
		panel_5Temp(temp);
	}

	public void panel_5Blink() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean blink = true;
				for (int i = 0; i < 4; i++) {
					if (blink) {
						panel_5.setBackground(new Color(0, 191, 255));
					} else {
						panel_5.setBackground(Color.RED);
					}
					blink = !blink;
					try {
						Thread.sleep(500L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	// Front Door Locked
	public void panel_6Clicked(MouseEvent e) {
		// continuously blink the led every 1/2 second for 5 seconds
		if ((RPiIoTTiles.gpio == "") || (RPiIoTTiles.gpio.indexOf("none") != -1)) {
			System.err.println(
					"Note: create gpio controller e.g. gpio=GPIO_01 not defined in iotbpm.properties file.");
		} else {
			led1.blink(500, 5000);
		}
		com.piiottiles.server.AgentConnect.getInstance().sendPost("TronIoT",
				"&event=DoorLock");
	}

	public void panel_6DoorLocked() {
		lblIconLabel_6.setIcon(lockIcon);
		lblBottomLabel_6.setText("Locked");
		panel_6.setBackground(Color.RED);
	}

	public void panel_6DoorUnlocked() {
		lblIconLabel_6.setIcon(lock_openIcon);
		lblBottomLabel_6.setText("Unlocked");
		panel_6.setBackground(Color.LIGHT_GRAY);
	}

	// Front Door Open
	public void panel_7Clicked(MouseEvent e) {
		JOptionPane.showMessageDialog(null,
				"The IoT Door Open Sensor jBPM Automation process is triggered by an Raspberry Pi contact switch event.",
				"IoT Door Sensor", JOptionPane.INFORMATION_MESSAGE);
	}

	public void panel_7DoorOpened() {
		lblIconLabel_7.setIcon(door_inIcon);
		lblBottomLabel_7.setText("Opened");
		panel_7.setBackground(Color.RED);
		panel_7Blink();
	}

	public void panel_7DoorClosed() {
		lblIconLabel_7.setIcon(doorIcon);
		lblBottomLabel_7.setText("Closed");
		panel_7.setBackground(Color.BLUE);
	}

	public void panel_7Blink() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean blink = true;
				for (int i = 0; i < 16; i++) {
					if (i > 7)
						lblIconLabel_7.setIcon(door_outIcon);
					if (blink) {
						panel_7.setBackground(Color.RED);
					} else {
						panel_7.setBackground(Color.BLUE);
						if (i == 15) {
							panel_7DoorClosed();
						}
					}
					blink = !blink;
					try {
						Thread.sleep(500L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	// Lobby Door Locked
	public void panel_8Clicked(MouseEvent e) {
		if ((RPiIoTTiles.gpio == "") || (RPiIoTTiles.gpio.indexOf("none") != -1)) {
			System.err.println(
					"Note: create gpio controller e.g. gpio=GPIO_01 not defined in iotbpm.properties file.");
		} else {
			// continuously blink the led every 1/2 second for 15 seconds
			led1.blink(500, 15000);
			// continuously blink the led every 1 second
			led2.blink(1000, 15000);
		}
		com.piiottiles.server.AgentConnect.getInstance().sendPost("TronIoT",
				"&event=DoorLobby");
	}

	public void panel_8DoorLocked() {
		lblIconLabel_8.setIcon(lockIcon);
		lblBottomLabel_8.setText("Locked");
		panel_8.setBackground(Color.RED);
	}

	public void panel_8DoorUnlocked() {
		lblIconLabel_8.setIcon(lock_openIcon);
		lblBottomLabel_8.setText("Unlocked");
		panel_8.setBackground(Color.LIGHT_GRAY);
	}

	// Lobby Door Open
	public void panel_9Clicked(MouseEvent e) {
		JOptionPane.showMessageDialog(null,
				"The IoT Door Open Sensor jBPM Automation process is triggered by an Raspberry Pi contact switch event.",
				"IoT Door Sensor", JOptionPane.INFORMATION_MESSAGE);
	}

	public void panel_9DoorOpened() {
		lblIconLabel_9.setIcon(door_inIcon);
		lblBottomLabel_9.setText("Opened");
		panel_9.setBackground(Color.RED);
		panel_9Blink();
	}

	public void panel_9DoorClosed() {
		lblIconLabel_9.setIcon(doorIcon);
		lblBottomLabel_9.setText("Closed");
		panel_9.setBackground(Color.BLUE);
	}

	public void panel_9Blink() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean blink = true;
				for (int i = 0; i < 16; i++) {
					if (i > 7)
						lblIconLabel_9.setIcon(door_outIcon);
					if (blink) {
						panel_9.setBackground(Color.RED);
					} else {
						panel_9.setBackground(Color.BLUE);
						if (i == 15) {
							panel_9DoorClosed();
						}
					}
					blink = !blink;
					try {
						Thread.sleep(500L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	// Lobby Light
	public void panel_10Clicked(MouseEvent e) {
		com.piiottiles.server.AgentConnect.getInstance().sendPost("TronIoT",
				"&keypress=1.0&event=LightModule");
	}

	public void panel_10LightOn() {
		lblIconLabel_10.setIcon(lightbulbIcon);
		lblBottomLabel_10.setText("Light On");
		// panel_10.setBackground(new Color(0, 128, 0));
	}

	public void panel_10LightOff() {
		lblIconLabel_10.setIcon(lightbulb_offIcon);
		lblBottomLabel_10.setText("Light Off");
		// panel_10.setBackground(new Color(0, 128, 0));
	}

	public void panel_11Clicked(MouseEvent e) {
		lblBottomLabel_11 = new JLabel("IoT Kiosk Status");
		lblIconLabel_11.setIcon(informationIcon);

		panel_11.setBackground(new Color(244, 164, 96));
		lblTopLabel_11 = new JLabel("IoT Kiosk Cloud");
		lblBottomLabel_11 = new JLabel("IoT Kiosk Status");
		lblIconLabel_11.setIcon(informationIcon);

		JOptionPane.showMessageDialog(null,
				"The IoT Kiosk is a complete cloud-connected Kiosk architecture that integrates all your IoT Device and enterprise computer systems. "
						+ "The IoT Kiosk has WiFi and Web Server software for complete control of IoT Internet of things devices from the cloud.",
				"IoT Kiosk Web Services is getting your IoT project working in the Cloud",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public void panel_11IoTKiosk(String IoTKiosk) {
		lblIconLabel_11.setIcon(autosIcon);
		lblBottomLabel_11.setText(IoTKiosk);
		panel_11.setBackground(new Color(0, 191, 255));
		// panel_9.setBackground(Color.BLUE);
	}

	public void panel_12Clicked(MouseEvent e) {
		// continuously blink the led every 1/2 second for 5 seconds
		if ((RPiIoTTiles.gpio == "") || (RPiIoTTiles.gpio.indexOf("none") != -1)) {
			System.err.println(
					"Note: create gpio controller e.g. gpio=GPIO_01 not defined in iotbpm.properties file.");
		} else {
			led1.blink(500, 5000);
		}
		JOptionPane.showMessageDialog(null,
				"The Raspberry Pi IoT is used connect to Office Door Locks, Activate Security Alarms, Turn Office Lights: ON, Control Thermostats, Answer Doorbell, "
						+ "Open Window Shades, Activate Motion Sensors.",
				"The Raspberry Pi IoT is used to Connect to External Devices", JOptionPane.INFORMATION_MESSAGE);
	}

	// Tron IoT Message
	public void panel_13Clicked(MouseEvent e) {
		com.piiottiles.server.AgentConnect.getInstance().sendPost("TronIoT",
				"&textMessage=IoT_Tiles_Message");		
	}

	// DoorOpen, Chime-Tron IoT
	public void panel_14Clicked(MouseEvent e) {
		String doorOpen = com.piiottiles.model.StateList.getInstance().getState("DoorOpen");
		if (doorOpen.indexOf("Tron") != -1) {
  			com.piiottiles.model.StateList.getInstance().putState("DoorOpen", "Chime");
			lblBottomLabel_14.setText("Chime Signal");
			lblIconLabel_14.setIcon(notification_bellIcon);
		} else {
  			com.piiottiles.model.StateList.getInstance().putState("DoorOpen", "Tron");
			lblBottomLabel_14.setText("IoT Display");
			lblIconLabel_14.setIcon(phone_openIcon);
		}
	}

	// IoT Dash Button
	public void panel_15Clicked(MouseEvent e) {
		// continuously blink the led every 1/2 second for 5 seconds
		if ((RPiIoTTiles.gpio == "") || (RPiIoTTiles.gpio.indexOf("none") != -1)) {
			System.err.println(
					"Note: create gpio controller e.g. gpio=GPIO_01 not defined in iotbpm.properties file.");
		} else {
			led2.blink(500, 5000);
		}
		JOptionPane.showMessageDialog(null,
				"Clear IoT Dash Button Sensor process triggered by an Raspberry Pi event message.", "IoT Dash Button",
				JOptionPane.INFORMATION_MESSAGE);
		lblIconLabel_15.setText("");
	}

	public void panel_15DashButtonAlert(String alert) {
		lblIconLabel_15.setText(alert);
		panel_15Blink();
		gpioController();
	}

	public void panel_15Blink() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean blink = true;
				for (int i = 0; i < 6; i++) {
					if (blink) {
						panel_15.setBackground(Color.RED);
					} else {
						panel_15.setBackground(Color.DARK_GRAY);
					}
					blink = !blink;
					try {
						Thread.sleep(500L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	// IoT Sensors
	public void panel_16Clicked(MouseEvent e) {
		JOptionPane.showMessageDialog(null,
				"This is the default IoT Sensors jBPM Automation extended process. Use Drools Rules to start a specific IoT jBPM Automation process for this IoT sensor event.",
				"IoT Sensors Extended Event", JOptionPane.INFORMATION_MESSAGE);
	}

	public void panel_16IoTSensors(String IoT_Sensors) {
		lblBottomLabel_16.setText(IoT_Sensors);
		panel_16Blink();
	}

	public void panel_16Blink() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean blink = true;
				for (int i = 0; i < 6; i++) {
					if (blink) {
						panel_16.setBackground(Color.RED);
					} else {
						panel_16.setBackground(Color.BLUE);
					}
					blink = !blink;
					try {
						Thread.sleep(500L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	// Dash Button
	public void panel_17Clicked(MouseEvent e) {
		if ((RPiIoTTiles.gpio == "") || (RPiIoTTiles.gpio.indexOf("none") != -1)) {
			System.err.println(
					"Note: create gpio controller e.g. gpio=GPIO_01 not defined in iotbpm.properties file.");
		} else {
			// continuously blink the led every 1/2 second for 15 seconds
			led1.blink(500, 15000);
			// continuously blink the led every 1 second
			led2.blink(1000, 15000);
		}
		com.piiottiles.server.AgentConnect.getInstance().sendPost("TronIoT",
				"&agentCount=0&alarm=IoTTiles&keypress=1.0");
	}

	// Outside Temperature
	public void panel_18Clicked(MouseEvent e) {
		JOptionPane.showMessageDialog(null,
				"The IoT DHT11 WiFi wireless module sends temperature and humidity environment information to the IoT Tiles Panel and Tron IoT Display.",
				"IoT DHT11 Temperature and Humidity WiFi Module Wireless Module ESP-01S",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public void panel_18Temp(String temp) {
		lblIconLabel_18.setText(temp);
		int iTemp = Integer.parseInt(temp.substring(0, 2));
		if (iTemp < 32)
			lblIconLabel_18.setIcon(weather_snowIcon);
		if ((iTemp >= 32) && (iTemp <= 50))
			lblIconLabel_18.setIcon(weather_cloudsIcon);
		if ((iTemp >= 51) && (iTemp <= 70))
			lblIconLabel_18.setIcon(weather_cloudyIcon);
		if (iTemp > 70)
			lblIconLabel_18.setIcon(weather_sunIcon);
		panel_18Blink();
	}

	public void panel_18Temp(String topLabel, String temp) {
		lblTopLabel_18.setText(topLabel);
		panel_18Temp(temp);
	}

	public void panel_18Blink() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean blink = true;
				for (int i = 0; i < 4; i++) {
					if (blink) {
						panel_18.setBackground(new Color(0, 191, 255));
					} else {
						panel_18.setBackground(Color.RED);
					}
					blink = !blink;
					try {
						Thread.sleep(500L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	// Device Control
	public void panel_19Clicked(MouseEvent e) {
		lblIconLabel_19.setIcon(lightbulb_addIcon);
		JOptionPane.showMessageDialog(null,
				"The little IoT ESP-01S Relay Expansion Module is a simple and easy-to-use expansion board that uses the ESP-01S breakout board to drive a relay and operate devices or machines wirelessly.",
				"IoT ESP-01S WiFi Relay Expansion Module Board", JOptionPane.INFORMATION_MESSAGE);
		lblIconLabel_19.setIcon(lightbulb_offIcon);
	}

	public void panel_20Clicked(MouseEvent e) {
		URL camURL = null;
		BufferedImage image = null;
		String webCamURL = com.piiottiles.server.AgentConnect.getInstance().agentURL("WebCam2");
		if ((webCamURL == "") || (webCamURL.indexOf("0.0.0.0") != -1)) {
			try {
				image = ImageIO.read(new File("images" + File.separator + "pic1.jpg"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			try {
				camURL = new URL(webCamURL);
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				image = ImageIO.read(camURL);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (image != null) {
			if (initpanel_20) {
				initpanel_20 = false;
				lblIconLabel_20 = new JLabel(new ImageIcon(image));
				lblIconLabel_20.setFont(new Font("Tahoma", Font.BOLD, 11));
				lblIconLabel_20.setForeground(Color.WHITE);
				lblIconLabel_20.setHorizontalAlignment(SwingConstants.CENTER);
				panel_20.add(lblIconLabel_20, BorderLayout.CENTER);
			} else {
				lblIconLabel_20.setIcon(new ImageIcon(image));
			}
		}
	}

	public void panel_21Clicked(MouseEvent e) {
		URL camURL = null;
		BufferedImage image = null;
		String webCamURL = com.piiottiles.server.AgentConnect.getInstance().agentURL("WebCam3");
		if ((webCamURL == "") || (webCamURL.indexOf("0.0.0.0") != -1)) {
			try {
				image = ImageIO.read(new File("images" + File.separator + "pic2.jpg"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			try {
				camURL = new URL(webCamURL);
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				image = ImageIO.read(camURL);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (image != null) {
			if (initpanel_21) {
				initpanel_21 = false;
				lblIconLabel_21 = new JLabel(new ImageIcon(image));
				lblIconLabel_21.setFont(new Font("Tahoma", Font.BOLD, 11));
				lblIconLabel_21.setForeground(Color.WHITE);
				lblIconLabel_21.setHorizontalAlignment(SwingConstants.CENTER);
				panel_21.add(lblIconLabel_21, BorderLayout.CENTER);
			} else {
				lblIconLabel_21.setIcon(new ImageIcon(image));
			}
		}
	}

	/**
	 *    Raspberry Pi Pinout
	 *      3V3  (1)  (2) 5V
	 *    GPIO2  (3)  (4) 5V
	 *    GPIO3  (5)  (6) GND
	 *    GPIO4  (7)  (8) GPIO14
	 *      GND  (9) (10) GPIO15
	 *   GPIO17 (11) (12) GPIO18
	 *   GPIO27 (13) (14) GND
	 *   GPIO22 (15) (16) GPIO23
	 *      3V3 (17) (18) GPIO24
	 *   GPIO10 (19) (20) GND
	 *    GPIO9 (21) (22) GPIO25
	 *   GPIO11 (23) (24) GPIO8
	 *      GND (25) (26) GPIO7
	 *    GPIO0 (27) (28) GPIO1
	 *    GPIO5 (29) (30) GND
	 *    GPIO6 (31) (32) GPIO12
	 *   GPIO13 (33) (34) GND
	 *   GPIO19 (35) (36) GPIO16
	 *   GPIO26 (37) (38) GPIO20
	 *      GND (39) (40) GPIO21
	 *
	 * BerryClip+ - 6 LED - 2 Switch - 1 Buzzer Board
	 * Hardware Reference
	 * =============================
	 * The components are connected to the main Pi GPIO header (P1)
	 * Component  Pin       BCM    WiringPi
	 * ---------|-------|--------|---------
	 * LED 1    - P1-07 - GPIO4  - GPIO. 7
	 * LED 2    - P1-11 - GPIO17 - GPIO. 0
	 * LED 3    - P1-15 - GPIO22 - GPIO. 3
	 * LED 4    - P1-19 - GPIO10
	 * LED 5    - P1-21 - GPIO9
	 * LED 6    - P1-23 - GPIO11
	 * Buzzer   - P1-24 - GPIO8
	 * Switch 1 - P1-26 - GPIO7
	 * Swtich 2 - P1-22 - GPIO25
	 *
	 * Jam HAT - 6 LED - 2 Switch - 1 Buzzer Board
	 * The table below shows the pin numbers for BCM, Board and the matching GPIO Zero objects.
	 * |Component |GPIO.BCM | BOARD  |GPIO Zero object |WiringPi | Notes 
	 * |----------|---------|--------|-----------------|---------|
	 * | LED1     | GPIO 5  | Pin 29 | lights_1.red    | GPIO.21 |
	 * | LED2     | GPIO 6  | Pin 31 | lights_2.red    | GPIO.22 |
	 * | LED3     | GPIO 12 | Pin 32 | lights_1.yellow | GPIO.26 |
	 * | LED4     | GPIO 13 | Pin 33 | lights_2.yellow | GPIO.23 |
	 * | LED5     | GPIO 16 | Pin 36 | lights_1.green  | GPIO.27 |
	 * | LED6     | GPIO 17 | Pin 11 | lights_2.green  | GPIO. 0 |
	 * | Button 1 | GPIO 19 | Pin 35 | button_1        | GPIO.24 | Connected to R8/R10 
	 * | Button 2 | GPIO 18 | Pin 12 | button_2        | GPIO. 1 | Connected to R7/R9 
	 * | Buzzer   | GPIO 20 | Pin 38 | buzzer          | GPIO.28 |
	 *
	 * Wiring Pi - GPIO Interface library for the Raspberry Pi
	 * +-----+-----+---------+------+---+---Pi 4B--+---+------+---------+-----+-----+
	 * | BCM | wPi |   Name  | Mode | V | Physical | V | Mode | Name    | wPi | BCM |
 	 * +-----+-----+---------+------+---+----++----+---+------+---------+-----+-----+
	 * |     |     |    3.3v |      |   |  1 || 2  |   |      | 5v      |     |     |
	 * |   2 |   8 |   SDA.1 |   IN | 1 |  3 || 4  |   |      | 5v      |     |     |
	 * |   3 |   9 |   SCL.1 |   IN | 1 |  5 || 6  |   |      | 0v      |     |     |
	 * |   4 |   7 | GPIO. 7 |   IN | 1 |  7 || 8  | 1 | IN   | TxD     | 15  | 14  |
	 * |     |     |      0v |      |   |  9 || 10 | 1 | IN   | RxD     | 16  | 15  |
	 * |  17 |   0 | GPIO. 0 |  OUT | 0 | 11 || 12 | 0 | OUT  | GPIO. 1 | 1   | 18  |
	 * |  27 |   2 | GPIO. 2 |   IN | 0 | 13 || 14 |   |      | 0v      |     |     |
	 * |  22 |   3 | GPIO. 3 |  OUT | 0 | 15 || 16 | 0 | IN   | GPIO. 4 | 4   | 23  |
	 * |     |     |    3.3v |      |   | 17 || 18 | 0 | OUT  | GPIO. 5 | 5   | 24  |
	 * |  10 |  12 |    MOSI |   IN | 0 | 19 || 20 |   |      | 0v      |     |     |
	 * |   9 |  13 |    MISO |   IN | 0 | 21 || 22 | 1 | OUT  | GPIO. 6 | 6   | 25  |
	 * |  11 |  14 |    SCLK |   IN | 0 | 23 || 24 | 1 | IN   | CE0     | 10  | 8   |
	 * |     |     |      0v |      |   | 25 || 26 | 1 | IN   | CE1     | 11  | 7   |
	 * |   0 |  30 |   SDA.0 |   IN | 1 | 27 || 28 | 1 | IN   | SCL.0   | 31  | 1   |
	 * |   5 |  21 | GPIO.21 |  OUT | 0 | 29 || 30 |   |      | 0v      |     |     |
	 * |   6 |  22 | GPIO.22 |  OUT | 0 | 31 || 32 | 0 | OUT  | GPIO.26 | 26  | 12  |
	 * |  13 |  23 | GPIO.23 |  OUT | 0 | 33 || 34 |   |      | 0v      |     |     |
	 * |  19 |  24 | GPIO.24 |   IN | 0 | 35 || 36 | 1 | OUT  | GPIO.27 | 27  | 16  |
	 * |  26 |  25 | GPIO.25 |   IN | 0 | 37 || 38 | 0 | IN   | GPIO.28 | 28  | 20  |
	 * |     |     |      0v |      |   | 39 || 40 | 0 | IN   | GPIO.29 | 29  | 21  |
	 * +-----+-----+---------+------+---+----++----+---+------+---------+-----+-----+
	 * | BCM | wPi |   Name  | Mode | V | Physical | V | Mode | Name    | wPi | BCM |
	 * +-----+-----+---------+------+---+---Pi 4B--+---+------+---------+-----+-----+
	 */
    
	/**
	 * This code demonstrates how to perform simple blinking LED logic of a
	 * GPIO pin on the Raspberry Pi using the Pi4J library.
	 */
	public void gpioController() {
		if ((RPiIoTTiles.gpio == "") || (RPiIoTTiles.gpio.indexOf("none") != -1)) {
			System.err.println(
					"Note: create gpio controller e.g. gpio=GPIO_01 not defined in iotbpm.properties file.");
			return;
		}

		if (gpio == null) {
			// create gpio controller
			gpio = GpioFactory.getInstance();

			// provision gpio pin #01 & #03 as an output pins and blink
			led1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_21);
			led2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_22);

			// provision gpio pin #02 as an input pin with its internal pull down resistor enabled
			switch1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_24, PinPullResistance.PULL_DOWN);
			switch2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_DOWN);
		}
	}

	void windowClosingAction(WindowEvent e) {
		alive = false;
		// stop all GPIO activity/threads
		// (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
		if ((RPiIoTTiles.gpio == "") || (RPiIoTTiles.gpio.indexOf("none") != -1)) {
			System.err.println(
					"Note: create gpio controller e.g. gpio=GPIO_01 not defined in iotbpm.properties file.");
		} else {
			// Pi4J GPIO controller
			gpio.shutdown(); // implement this method call if you wish to terminate the Pi4J GPIO controller
		}
		
		RPiIoTTiles.stopIoTServer();
		// System.exit(0);
	}
}
