package org.com.sportsdata;

import java.time.Clock;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scoreboard scoreboard = new Scoreboard(Clock.systemUTC());
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("\nEnter a command: start ,update, finish, summary, or exit");
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "start" -> {
                    System.out.println("Home Team: ");
                    String home = scanner.nextLine();
                    System.out.println("Away Team: ");
                    String away = scanner.nextLine();
                    scoreboard.startMatch(home, away);
                }
                case "update" -> {
                    System.out.println("Home Team: ");
                    String home = scanner.nextLine();
                    System.out.println("Away Team: ");
                    String away = scanner.nextLine();
                    int homeScore;
                    int awayScore;
                    try {
                        System.out.println("Home Score: ");
                        homeScore = Integer.parseInt(scanner.nextLine());
                        System.out.println("Away Score: ");
                        awayScore = Integer.parseInt(scanner.nextLine());
                        scoreboard.updateScore(home, away, homeScore, awayScore);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid score input. Scores must be numeric.");
                    }
                }
                case "finish" -> {
                    System.out.println("Home Team: ");
                    String home = scanner.nextLine();
                    System.out.println("Away Team: ");
                    String away = scanner.nextLine();
                    scoreboard.finishMatch(home, away);
                }
                case "summary" -> {
                    System.out.println("Live Matches: ");
                    scoreboard.getSummary().forEach(System.out::println);
                }
                case "exit" -> {
                    System.out.println("Existing program.");
                    return;
                }
                default -> System.out.println("Invalid command. Try again!!");
            }
        }
    }
}