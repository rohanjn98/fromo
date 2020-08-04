# fromo
FROMO - Friendly Robot Mobile

Android in today's world is a widely accepted operating system which is accessible, affordable and available across maximum part of the globe.

FROMO(Friendly Robot Mobile) is capable of performing tasks like remote controlled movement, face following mechanism (follows your face wherever you go!), programming mechanism (you can basically programme the robot’s moves in a predefined way!), colored line following mechanism (the robot can follow colored lines!).

<img src="images/IMG_20170714_133324736_HDR.jpg" width=400 height=600 style="float:left">
<img src="images/IMG_20170714_222216385.jpg" width=400 height=600 style="float:left">

## Flowchart of project
![alt text](images/flowchart.png)

### 1. Remote Controlled Movement

1. Screen consists of the image buttons of the respective directions.
2. In this app Bluetooth is interfaced.
3. On clicking any button of the app activity, data is send corresponding to that button to Arduino Uno which is interfaced via Bluetooth module HC05.
4. Corresponding to the data sent, the Arduino instructs the motor driver L293D to turn to the specified direction.

<img src="images/rcmode.png" width=400 height=600 style="float:left">


