package javaeetutorial.roster.client;

import java.util.List;
import javaeetutorial.roster.request.Request;
import javaeetutorial.roster.util.LeagueDetails;
import javaeetutorial.roster.util.TeamDetails;
import javax.ejb.EJB;

public class RosterClient {
    
    @EJB
    private static Request request;

    public static void main(String[] args) {
        RosterClient client = new RosterClient();
        try {
            client.insertInfo();
            client.getSomeInfo();
            client.getMoreInfo();
            client.removeInfo();
            System.exit(0);
        } catch (Exception ex) {
            System.err.println("Caught an exception:" + ex.getMessage());
        }
    }

    private void insertInfo() {
        try {
            // Leagues
            request.createLeague(new LeagueDetails("L1", "Mountain", "Soccer"));
            request.createLeague(new LeagueDetails("L2", "Valley", "Basketball"));
            request.createLeague(new LeagueDetails("L3", "Foothills", "Soccer"));
            request.createLeague(new LeagueDetails("L4", "Alpine", "Snowboarding"));

            // Teams
            request.createTeamInLeague(new TeamDetails("T1", "Honey Bees", "Visalia"), "L1");
            request.createTeamInLeague(new TeamDetails("T2", "Gophers", "Manteca"), "L1");
            request.createTeamInLeague(new TeamDetails("T5", "Crows", "Orland"), "L1");
            request.createTeamInLeague(new TeamDetails("T3", "Deer", "Bodie"), "L2");
            request.createTeamInLeague(new TeamDetails("T4", "Trout", "Truckee"), "L2");
            request.createTeamInLeague(new TeamDetails("T6", "Marmots", "Auburn"), "L3");
            request.createTeamInLeague(new TeamDetails("T7", "Bobcats", "Grass Valley"), "L3");
            request.createTeamInLeague(new TeamDetails("T8", "Beavers", "Placerville"), "L3");
            request.createTeamInLeague(new TeamDetails("T9", "Penguins", "Incline Village"), "L4");
            request.createTeamInLeague(new TeamDetails("T10", "Land Otters", "Tahoe City"), "L4");

            // Players, Team T1
            request.createPlayer("P1", "Phil Jones", "goalkeeper", 100.00);
            request.addPlayer("P1", "T1");
            request.createPlayer("P2", "Alice Smith", "defender", 505.00);
            request.addPlayer("P2", "T1");
            request.createPlayer("P3", "Bob Roberts", "midfielder", 65.00);
            request.addPlayer("P3", "T1");
            request.createPlayer("P4", "Grace Phillips", "forward", 100.00);
            request.addPlayer("P4", "T1");
            request.createPlayer("P5", "Barney Bold", "defender", 100.00);
            request.addPlayer("P5", "T1");

            // Players, Team T2
            request.createPlayer("P6", "Ian Carlyle", "goalkeeper", 555.00);
            request.addPlayer("P6", "T2");
            request.createPlayer("P7", "Rebecca Struthers", "midfielder", 777.00);
            request.addPlayer("P7", "T2");
            request.createPlayer("P8", "Anne Anderson", "forward", 65.00);
            request.addPlayer("P8", "T2");
            request.createPlayer("P9", "Jan Wesley", "defender", 100.00);
            request.addPlayer("P9", "T2");
            request.createPlayer("P10", "Terry Smithson", "midfielder", 100.00);
            request.addPlayer("P10", "T2");

            // Players, Team T3
            request.createPlayer("P11", "Ben Shore", "point guard", 188.00);
            request.addPlayer("P11", "T3");
            request.createPlayer("P12", "Chris Farley", "shooting guard", 577.00);
            request.addPlayer("P12", "T3");
            request.createPlayer("P13", "Audrey Brown", "small forward", 995.00);
            request.addPlayer("P13", "T3");
            request.createPlayer("P14", "Jack Patterson", "power forward", 100.00);
            request.addPlayer("P14", "T3");
            request.createPlayer("P15", "Candace Lewis", "point guard", 100.00);
            request.addPlayer("P15", "T3");

            // Players, Team T4
            request.createPlayer("P16", "Linda Berringer", "point guard", 844.00);
            request.addPlayer("P16", "T4");
            request.createPlayer("P17", "Bertrand Morris", "shooting guard", 452.00);
            request.addPlayer("P17", "T4");
            request.createPlayer("P18", "Nancy White", "small forward", 833.00);
            request.addPlayer("P18", "T4");
            request.createPlayer("P19", "Billy Black", "power forward", 444.00);
            request.addPlayer("P19", "T4");
            request.createPlayer("P20", "Jodie James", "point guard", 100.00);
            request.addPlayer("P20", "T4");

            // Players, Team T5
            request.createPlayer("P21", "Henry Shute", "goalkeeper", 205.00);
            request.addPlayer("P21", "T5");
            request.createPlayer("P22", "Janice Walker", "defender", 857.00);
            request.addPlayer("P22", "T5");
            request.createPlayer("P23", "Wally Hendricks", "midfielder", 748.00);
            request.addPlayer("P23", "T5");
            request.createPlayer("P24", "Gloria Garber", "forward", 777.00);
            request.addPlayer("P24", "T5");
            request.createPlayer("P25", "Frank Fletcher", "defender", 399.00);
            request.addPlayer("P25", "T5");

            // Players, Team T9
            request.createPlayer("P30", "Lakshme Singh", "downhill", 450.00);
            request.addPlayer("P30", "T9");
            request.createPlayer("P31", "Mariela Prieto", "freestyle", 420.00);
            request.addPlayer("P31", "T9");

            // Players, Team T10
            request.createPlayer("P32", "Soren Johannsen", "freestyle", 375.00);
            request.addPlayer("P32", "T10");
            request.createPlayer("P33", "Andre Gerson", "freestyle", 396.00);
            request.addPlayer("P33", "T10");
            request.createPlayer("P34", "Zoria Lepsius", "downhill", 431.00);
            request.addPlayer("P34", "T10");

            // Players, no team
            request.createPlayer("P26", "Hobie Jackson", "pitcher", 582.00);
            request.createPlayer("P27", "Melinda Kendall", "catcher", 677.00);

            // Players, multiple teams
            request.createPlayer("P28", "Constance Adams", "substitute", 966.00);
            request.addPlayer("P28", "T1");
            request.addPlayer("P28", "T3");

            // Adding existing players to second soccer league
            request.addPlayer("P24", "T6");
            request.addPlayer("P21", "T6");
            request.addPlayer("P9", "T6");
            request.addPlayer("P7", "T5");
        } catch (Exception ex) {
            System.err.println("Caught an exception:");
        }
    }

    private void getSomeInfo() {
        try {
            System.out.println("List all players in team T2:");
            printDetailsList(request.getPlayersOfTeam("T2"));

            System.out.println("List all teams in league L1:");
            printDetailsList(request.getTeamsOfLeague("L1"));

            System.out.println("List all defenders:");
            printDetailsList(request.getPlayersByPosition("defender"));

            System.out.println("List the leagues of player P28");
            printDetailsList(request.getLeaguesOfPlayer("P28"));
        } catch (Exception ex) {
            System.err.println("Caught an exception:" + ex.getMessage());
        }
    } 

    private void getMoreInfo() {
        try {
            System.out.println("Details of league L1:");
            System.out.println(request.getLeague("L1").toString());

            System.out.println("Details of team T3:");
            System.out.println(request.getTeam("T3").toString());

            System.out.println("Details of player P20:");
            System.out.println(request.getPlayer("P20").toString());

            System.out.println("List all teams in league L3:");
            printDetailsList(request.getTeamsOfLeague("L3"));

            System.out.println("List all players:");
            printDetailsList( request.getAllPlayers());

            System.out.println("List all players not on a team:");
            printDetailsList(request.getPlayersNotOnTeam());

            System.out.println("Details of Jack Patterson, a power forward:");
            printDetailsList(request.getPlayersByPositionAndName("power forward", "Jack Patterson"));

            System.out.println("List all players in the city of Truckee:");
            printDetailsList(request.getPlayersByCity("Truckee"));

            System.out.println("List all soccer players:");
            printDetailsList(request.getPlayersBySport("Soccer"));

            System.out.println("List all players in league L1:");
            printDetailsList(request.getPlayersByLeagueId("L1"));

            System.out.println("List all players making a higher salary than Ian Carlyle:");
            printDetailsList(request.getPlayersByHigherSalary("Ian Carlyle"));

            System.out.println("List all players with a salary between 500 and 800:");
            printDetailsList(request.getPlayersBySalaryRange(500.00, 800.00));

            System.out.println("List all players of team T5:");
            printDetailsList(request.getPlayersOfTeam("T5"));

            System.out.println("List all the leagues of player P28:");
            printDetailsList(request.getLeaguesOfPlayer("P28"));

            System.out.println("List all the sports of player P28:");
            printDetailsList(request.getSportsOfPlayer("P28"));
        } catch (Exception ex) {
            System.err.println("Caught an exception:" + ex.getMessage());
        }
    } 

    private void removeInfo() {
        try {
            System.out.println("Removing team T6.");
            request.removeTeam("T6");
            System.out.println();

            System.out.println("Removing player P24");
            request.removePlayer("P24");
            System.out.println();
        } catch (Exception ex) {
            System.err.println("Caught an exception:" + ex.getMessage());
        }
    }

    private static void printDetailsList(List<?> list) {
        for(Object item : list) {
            System.out.println(item.toString());
        }
        System.out.println();
    } 

}
