package org.com.sportsdata;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scoreboard scoreboard = new Scoreboard();
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
                    System.out.println("Match started!");
                }
                case "update" -> {
                    System.out.println("Home Team: ");
                    String home = scanner.nextLine();
                    System.out.println("Away Team: ");
                    String away = scanner.nextLine();
                    System.out.println("Home Score: ");
                    int homeScore = Integer.parseInt(scanner.nextLine());
                    System.out.println("Away Score: ");
                    int awayScore = Integer.parseInt(scanner.nextLine());
                    scoreboard.updateScore(home, away, homeScore, awayScore);
                    System.out.println("Match updated!");
                }
                case "finish" -> {
                    System.out.println("Home Team: ");
                    String home = scanner.nextLine();
                    System.out.println("Away Team: ");
                    String away = scanner.nextLine();
                    scoreboard.finishMatch(home, away);
                    System.out.println("Match finished!");
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