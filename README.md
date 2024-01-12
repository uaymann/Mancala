# Mancala

## Description

This Java project implements the classic Mancala board game, a two-player strategy game where the objective is to capture as many stones as possible into your store. The program takes user input for the names of two players and provides a user-friendly Graphical interface that guides players through their moves. Whether you're new to Mancala or an experienced player, the program ensures an engaging gaming experience.

### Features:

1. **Player Interaction:** Enter the names of two players to start the game. The program then guides users through the game, making it accessible for players of all levels.
2. **Save and Load Games:** Save your current game progress and load saved games to continue playing at a later time. Enjoy the flexibility of quitting and resuming games whenever you want.
3. **View your stats:** You can view your user profile to see how many games you've played and won so far.
4. **Game Modes:**
- **Kalah** - Play the classic Mancala game with Kalah rules.
- **Ayo** - Experience a different variation of the game with Ayo rules.

## Getting Started

### Dependencies

* Java Development Kit (JDK) 8 or higher
* Gradle

### Testing

* JUnit 5

### Executing program

To build and run the program, follow these steps:

1. Clone the repository:

```
git clone https://github.com/uaymann/Mancala.git
```

2. Navigate to the project directory:

```
cd Mancala
```

3. Compile the Java files:

```
gradle build
```

4. Run the program:

```
To run from jar:
java -jar build/libs/Mancala.jar

To run from class files:
java -cp build/classes/java/main ui.GUI
```

### Expected output:

* GUI will pop up showing the main menu screen of the Mancala game, where you can enter two player's usernames and choose one of the two game modes: Kalah or Ayo. From the main menu, you may also choose to continue a previously saved game, or view your stats.

## Limitations

* a two-player game; current version has no player vs. computer option.

## Author Information

**Name:** Umme Aymann

**Email:** uaymann@uoguelph.ca / ummeaymann109@gmail.com

## Development History

* 0.4
    * Various bug fixes, error handling and optimizations
* 0.3
    * Implemented user-friendly GUI
* 0.2
    * Added persistence (implemented the Serializable interface)
* 0.1
    * Completed functionalities of each java file in mancala package; implemented a second (Ayo) rule set

## Acknowledgments

* [mancala-rules] (https://www.officialgamerules.org/mancala)
* [mancala-game] (https://www.mathsisfun.com/games/mancala.html)
