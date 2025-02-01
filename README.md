Sports Data Application
This application provides a system for managing sports data, including handling teams and match results. The primary function is to calculate and display the scoreboard based on matches and validate team information. 

Features
Match Finder: Generates unique match keys based on home and away teams.
Match Validator: Validates team names and match data.
Scoreboard: Calculates and displays the current rankings based on match results.
Match Comparator: Sorts teams based on their performance.

Setup
Clone the repository:

git clone https://github.com/yiit625/ScoreboardApplication.git
cd ScoreboardApplication

Build the application:
Using Maven:
mvn clean install

Run the application:
To start the application, run the following command:
mvn exec:java

Start game
* Captures Home Team and Away Team
* Start current game with team names
* Validates team names

Finish game
* Validates match is existing or not
* Remove match from scoreboard

Update score
* Captures home and away teams score
* Update a pair of home and away team score
* Update total score as sum
* Validates scores and match existent

Get summary by total score
* returns sorted list of all games in format "HomeTeam 0 - 0 AwayTeam"
* games sorted by descending total score
* if total score is same - sorted by the most recent
* returns all games joined in one String

