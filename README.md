# MasterMind
MasterMind board game made in javafx.
Migrated to gradle.

## Table of Contents
* [General Info](#general-information)
* [Technologies Used](#technologies-used)
* [Features](#features)
* [Pictures](#pictures)
* [Setup](#setup)

<!-- * [License](#license) -->

## General Information

MasterMind is a logical board game. Normally there is one player who sets a row of colors.
The second player must find the right colors in correct order. In this case the role of the first player
is taken by computer who randomly generates a set of colors and grades the human player. 

## Technologies Used

- Java - version 11
- JavaFX - version 16

## Features

- choose between two style palletes (wood, metal)
- Adjust difficulty. Set number of rows and columns of the board. Also repeating colors can be set.

## Pictures
<p>Start game</p>
<img src="./img/start_metal.png" width=350 height=400>
<p>Choose different theme</p>
<img src="./img/start_wood.png" width=350 height=400>
<p>Configure your game</p>
<img src="./img/config.png" width=350 height=400>
<p>Playing the game</p>
<div style="display: flex; flex-direction:row;">
  <img src="./img/game_1.png" width=300 height=350>
  <img src="./img/game_2.png" width=300 height=350>
  <img src="./img/game_3.png" width=300 height=350>
</div>

## Setup
Java 11 and gradle 5 are required. Preferably use IntelliJ IDE. 
Download dependencies with:\
`gradle build`\
Then start the app with:\
`gradle run`
