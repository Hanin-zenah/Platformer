# Platformer
A simple customisable 2D platformer game implemented with JavaFX

## HOW TO RUN PROGRAM 
The program can be run by entering "gradle build" then "gradle run" in the terminal.

## DESCRIPTION OF THE JSON FILE FORMAT 
JSON files for the level configurations can be found in the resources folder. \
To make a custom level, please create a JSON file. Each JSON file should have: 
- the starting x position for stickman, stickman's size 

- an array of the different cloud velocities, as well as an array for the different 
heights, and different size. All the arrays should be of the same size

- level configuration which consists of the height, width of the level, as well as the floor height. 

- platform configuration (if any): arrays that store the x, y position and the movement type for each platform (movement can be none, vertical, or horizontal). Arrays should be of the same size

- enemy configuration (if any): arrays that specify the x position, velocity, height, and width of the enemy. Depending of the velocity given the enemy's starting movement could be from left to right or right to left (i.e. if velocity is negative, the enemy will move left until it hits a platform or the left border which will then move to the right and vice versa)

- mushroom configuration (if any): the x and y positions of the mushroom.

- grass block configuration (if any): the x position of the grass block. Its y position will be on the floor of the current level

### CONFIGURATION FILE NAMES TO LOAD DIFFERENT LEVELS
Different available levels configuration files: 
- "level1.json"
- "level2.json"
- "level3.json"

To load one of the two levels please go to src > main > java > stickman > App.java class file and on line 26 change the name of the file to the desired file.
example:\

<App.java class>\
line 26:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;JSONFileExtractor jsonFileExtractor = new JSONFileExtractor("level2.json");

## CONTROLS 
Once game loads you can use keys: \
**LEFT ARROW KEY:** to move left, \
**RIGHT ARROW KEY:** to move right,\
**UP ARROW KEY:** to jump, \
**SPACE BAR:** to shoot (after stickman eats the mushroom).
