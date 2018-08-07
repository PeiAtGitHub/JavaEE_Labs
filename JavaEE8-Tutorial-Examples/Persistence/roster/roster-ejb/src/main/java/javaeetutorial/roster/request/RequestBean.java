package javaeetutorial.roster.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaeetutorial.roster.entity.League;
import javaeetutorial.roster.entity.League_;
import javaeetutorial.roster.entity.Player;
import javaeetutorial.roster.entity.Player_;
import javaeetutorial.roster.entity.SummerLeague;
import javaeetutorial.roster.entity.Team;
import javaeetutorial.roster.entity.Team_;
import javaeetutorial.roster.entity.WinterLeague;
import javaeetutorial.roster.util.IncorrectSportException;
import javaeetutorial.roster.util.LeagueDetails;
import javaeetutorial.roster.util.PlayerDetails;
import javaeetutorial.roster.util.TeamDetails;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateful
public class RequestBean implements Request, Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger("roster.request.RequestBean");
    
    @PersistenceContext
    private EntityManager em;
    private CriteriaBuilder cb;

    @PostConstruct
    private void init() {
        cb = em.getCriteriaBuilder();
    }

    @Override
    public void createPlayer(String id, String name, String position, double salary) {
        try {
            em.persist(new Player(id, name, position, salary));
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public void addPlayer(String playerId, String teamId) {
        try {
            Player player = em.find(Player.class, playerId);
            Team team = em.find(Team.class, teamId);
            team.addPlayer(player);
            player.addTeam(team);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public void removePlayer(String playerId) {
        try {
            Player player = em.find(Player.class, playerId);
            for(Team team : player.getTeams()) {
                team.dropPlayer(player);
            }
            em.remove(player);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public void dropPlayer(String playerId, String teamId) {
        try {
            Player player = em.find(Player.class, playerId);
            Team team = em.find(Team.class, teamId);
            team.dropPlayer(player);
            player.dropTeam(team);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public PlayerDetails getPlayer(String playerId) {
        try {
            Player player = em.find(Player.class, playerId);
            return new PlayerDetails(player.getId(), player.getName(), player.getPosition(), player.getSalary());
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public List<PlayerDetails> getPlayersOfTeam(String teamId) {
        List<PlayerDetails> playerList = null;
        try {
            Team team = em.find(Team.class, teamId);
            playerList = this.copyPlayersToDetails((List<Player>) team.getPlayers());
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
        return playerList;
    }

    @Override
    public List<TeamDetails> getTeamsOfLeague(String leagueId) {
        List<TeamDetails> detailsList = new ArrayList<>();
        try {
            League league = em.find(League.class, leagueId);
            for(Team team : league.getTeams()) {
                detailsList.add(new TeamDetails(team.getId(), team.getName(), team.getCity()));
            }
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
        return detailsList;
    }

    @Override
    public List<PlayerDetails> getPlayersByPosition(String position) {
        List<Player> players = null;
        try {
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            if (cq != null) {
                Root<Player> player = cq.from(Player.class);
                // Get MetaModel from Root
                //EntityType<Player> Player_ = player.getModel();

                // set the where clause
                cq.where(cb.equal(player.get(Player_.position), position));
                cq.select(player);
                players = em.createQuery(cq).getResultList();
            }
            return copyPlayersToDetails(players);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public List<PlayerDetails> getPlayersByHigherSalary(String name) {
        List<Player> players = null;
        try {
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            if (cq != null) {
                Root<Player> player1 = cq.from(Player.class);
                Root<Player> player2 = cq.from(Player.class);
                // Get MetaModel from Root
                //EntityType<Player> Player_ = player1.getModel();

                // create a Predicate object that finds players with a salary
                // greater than player1
                Predicate gtPredicate = cb.greaterThan(player1.get(Player_.salary), player2.get(Player_.salary));
                // create a Predicate object that finds the player based on
                // the name parameter
                Predicate equalPredicate = cb.equal(player2.get(Player_.name), name);
                // set the where clause with the predicates
                cq.where(gtPredicate, equalPredicate);
                // set the select clause, and return only unique entries
                cq.select(player1).distinct(true);
                players = em.createQuery(cq).getResultList();
            }
            return copyPlayersToDetails(players);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public List<PlayerDetails> getPlayersBySalaryRange(double low, double high) {
        List<Player> players = null;
        try {
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            if (cq != null) {
                Root<Player> player = cq.from(Player.class);
                // Get MetaModel from Root
                //EntityType<Player> Player_ = player.getModel();

                // set the where clause
                cq.where(cb.between(player.get(Player_.salary), low, high));
                // set the select clause
                cq.select(player).distinct(true);
                players = em.createQuery(cq).getResultList();
            }
            return copyPlayersToDetails(players);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public List<PlayerDetails> getPlayersByLeagueId(String leagueId) {
        List<Player> players = null;
        try {
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            if (cq != null) {
                Root<Player> player = cq.from(Player.class);
                Join<Player, Team> team = player.join(Player_.teams);
                Join<Team, League> league = team.join(Team_.league);

                // Get MetaModel from Root
                //EntityType<Player> Player_ = player.getModel();

                // set the where clause
                cq.where(cb.equal(league.get(League_.id), leagueId));
                cq.select(player).distinct(true);
                players = em.createQuery(cq).getResultList();
            }
            return copyPlayersToDetails(players);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public List<PlayerDetails> getPlayersBySport(String sport) {
        List<Player> players = null;
        try {
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            if (cq != null) {
                Root<Player> player = cq.from(Player.class);
                Join<Player, Team> team = player.join(Player_.teams);
                Join<Team, League> league = team.join(Team_.league);
                // Get MetaModel from Root
                //EntityType<Player> Player_ = player.getModel();

                // set the where clause
                cq.where(cb.equal(league.get(League_.sport), sport));
                cq.select(player).distinct(true);
                players = em.createQuery(cq).getResultList();
            }
            return copyPlayersToDetails(players);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public List<PlayerDetails> getPlayersByCity(String city) {
        List<Player> players = null;
        try {
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            if (cq != null) {
                Root<Player> player = cq.from(Player.class);
                Join<Player, Team> team = player.join(Player_.teams);
                // Get MetaModel from Root
                //EntityType<Player> Player_ = player.getModel();

                // set the where clause
                cq.where(cb.equal(team.get(Team_.city), city));
                cq.select(player).distinct(true);
                players = em.createQuery(cq).getResultList();
            }
            return copyPlayersToDetails(players);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public List<PlayerDetails> getAllPlayers() {
        List<Player> players = null;
        try {
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            if (cq != null) {
                Root<Player> player = cq.from(Player.class);
                cq.select(player);
                players = em.createQuery(cq).getResultList();
            }
            return copyPlayersToDetails(players);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public List<PlayerDetails> getPlayersNotOnTeam() {
        List<Player> players = null;
        try {
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            if (cq != null) {
                Root<Player> player = cq.from(Player.class);
                // Get MetaModel from Root
                //EntityType<Player> Player_ = player.getModel();

                // set the where clause
                cq.where(cb.isEmpty(player.get(Player_.teams)));
                cq.select(player).distinct(true);
                players = em.createQuery(cq).getResultList();
            }
            return copyPlayersToDetails(players);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public List<PlayerDetails> getPlayersByPositionAndName(String position, String name) {
        List<Player> players = null;
        try {
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            if (cq != null) {
                Root<Player> player = cq.from(Player.class);
                // Get MetaModel from Root
                //EntityType<Player> Player_ = player.getModel();

                // set the where clause
                cq.where(cb.equal(player.get(Player_.position), position),
                        cb.equal(player.get(Player_.name), name));
                cq.select(player).distinct(true);
                players = em.createQuery(cq).getResultList();
            }
            return copyPlayersToDetails(players);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public List<LeagueDetails> getLeaguesOfPlayer(String playerId) {
        List<LeagueDetails> detailsList = new ArrayList<>();
        List<League> leagues = null;
        try {
            CriteriaQuery<League> cq = cb.createQuery(League.class);
            if (cq != null) {
                Root<League> league = cq.from(League.class);
                //EntityType<League> League_ = league.getModel();
                Join<League, Team> team = league.join(League_.teams);
                //EntityType<Team> Team_ = team.getModel();
                Join<Team, Player> player = team.join(Team_.players);

                cq.where(cb.equal(player.get(Player_.id), playerId));
                cq.select(league).distinct(true);
                leagues = em.createQuery(cq).getResultList();
            }
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
        if (leagues == null) {
            logger.log(Level.WARNING, "No leagues found for player with ID {0}.", playerId);
            return null;
        } else {
            for(League league : leagues) {
                detailsList.add(new LeagueDetails(league.getId(), league.getName(), league.getSport()));
            }
        }
        return detailsList;
    }

    @Override
    public List<String> getSportsOfPlayer(String playerId) {
        List<String> sports = new ArrayList<>();
        try {
            CriteriaQuery<String> cq = cb.createQuery(String.class);
            if (cq != null) {
                Root<Player> player = cq.from(Player.class);
                Join<Player, Team> team = player.join(Player_.teams);
                Join<Team, League> league = team.join(Team_.league);
                // Get MetaModel from Root
                //EntityType<Player> Player_ = player.getModel();

                // set the where clause
                cq.where(cb.equal(player.get(Player_.id), playerId));
                cq.select(league.get(League_.sport)).distinct(true);
                sports = em.createQuery(cq).getResultList();
            }
//        Player player = em.find(Player.class, playerId);
//        Iterator<Team> i = player.getTeams().iterator();
//        while (i.hasNext()) {
//            Team team = i.next();
//            League league = team.getLeague();
//            sports.add(league.getSport());
//        }
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
        return sports;
    }

    @Override
    public void createTeamInLeague(TeamDetails teamDetails, String leagueId) {
        try {
            League league = em.find(League.class, leagueId);
            Team team = new Team(teamDetails.getId(), teamDetails.getName(), teamDetails.getCity());
            em.persist(team);
            team.setLeague(league);
            league.addTeam(team);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public void removeTeam(String teamId) {
        try {
            Team team = em.find(Team.class, teamId);
            for(Player player : team.getPlayers()) {
                player.dropTeam(team);
            }
            em.remove(team);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public TeamDetails getTeam(String teamId) {
        TeamDetails teamDetails = null;
        try {
            Team team = em.find(Team.class, teamId);
            teamDetails = new TeamDetails(team.getId(), team.getName(), team.getCity());
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
        return teamDetails;
    }

    @Override
    public void createLeague(LeagueDetails leagueDetails) {
        try {
            if (leagueDetails.getSport().equalsIgnoreCase("soccer")
                    || leagueDetails.getSport().equalsIgnoreCase("swimming")
                    || leagueDetails.getSport().equalsIgnoreCase("basketball")
                    || leagueDetails.getSport().equalsIgnoreCase("baseball")) {
                SummerLeague league = new SummerLeague(leagueDetails.getId(), leagueDetails.getName(), 
                        leagueDetails.getSport());
                em.persist(league);
            } else if (leagueDetails.getSport().equalsIgnoreCase("hockey")
                    || leagueDetails.getSport().equalsIgnoreCase("skiing")
                    || leagueDetails.getSport().equalsIgnoreCase("snowboarding")) {
                WinterLeague league = new WinterLeague(leagueDetails.getId(), leagueDetails.getName(),
                        leagueDetails.getSport());
                em.persist(league);
            } else {
                throw new IncorrectSportException("The specified sport is not valid.");
            }
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public void removeLeague(String leagueId) {
        try {
            League league = em.find(League.class, leagueId);
            em.remove(league);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public LeagueDetails getLeague(String leagueId) {
        LeagueDetails leagueDetails = null;
        try {
            League league = em.find(League.class, leagueId);
            leagueDetails = new LeagueDetails(league.getId(), league.getName(), league.getSport());
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
        return leagueDetails;
    }

    private List<PlayerDetails> copyPlayersToDetails(List<Player> players) {
        List<PlayerDetails> detailsList = new ArrayList<>();
        for (Player player : players) {
            detailsList.add(new PlayerDetails(player.getId(), player.getName(), player.getPosition(),
                    player.getSalary()));
        }
        return detailsList;
    }
    
}
