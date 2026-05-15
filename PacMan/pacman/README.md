PACMAN video game by Alexander Már and Jacub Šmejkal 

Overview:
This project was based on recreating Pacman using Object-Oriented Programming (OOP) principles in Java. The architecture emphasizes encapsulation, inheritancem, polymorphism, abstraction and state based behaviour to create a modular and maintainable game structure. Also state design patterns, JavaFX rendering, gameloops and timing, collision detection and basic AI logic.

The game follows a model-view-controller (MVC) inspired structure and invcludes:
Pacman movement
Ghost AI and movement 
Pellet and power pellet 
Highscore (score tracking)
Lives system for Pacman
Pause function (TAB)
Game over state
Tunnel teleportation
PowerMode and scared ghosts

Features:
Arrow keys / WASD to move Pacman
TAB to pause/unpause the game
ESC to close the game
R to restart after Game over

Ghost modes: 
Scatter: Ghosts move towards their assigned corners and loop around them for a set of seconds
Chase: Ghosts chase after Pacman's location
Frightened/scared: When Pacman eats a power pellet triggers: ghosts move at half speed away from Pacman. Pacman can eat the ghosts and that increases the score and ghosts will respawn after a certain amount of seconds.

Power pellets:
Turn ghosts into frightened/scared
Pacman can eat ghosts
Reset the frightened timer if another power pellet is eaten by Pacman

Ghost score values:
200,400,800,1600 based on how many ghosts Pacman can eat in a PowerState

Pause system:
Stops all movement
Displays in the center "GAME PAUSED" 
Allows the player to resume playing instantly by re-pressing TAB

Game over:
When Pacman looses all of his lives:
The game enters FinishedState
Gameplay freezes
Display in the center "GAME OVER" appears 
Display in the center "Press R to restart"

Design Pattern:
These states allow logic and gameplay behaviour to change through out the course of the game by overriding:
NormalState - When no other state is triggered
ImmuneState - Pacman becomes invunerable after loosing a life
PowerState - When Pacman eats a power pellet
FinishedState - When Pacman looses all of his lives

Technologies used:
Java
JavaFX
Maven
VS Code
Git/Github

HOW TO RUN:
Java 17+
Maven installed
JavaFX configured through Maven

Run the game:
From the project directory terminal, write: mvn javafx:run