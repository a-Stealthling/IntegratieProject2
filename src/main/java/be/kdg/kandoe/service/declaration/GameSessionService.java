package be.kdg.kandoe.service.declaration;

import be.kdg.kandoe.domain.GameSession;
import be.kdg.kandoe.domain.user.User;

import java.util.List;

public interface GameSessionService {
    List<GameSession> getAllGameSessions();
    List<GameSession> getUserGameSessions(String username);
    GameSession addGameSession(GameSession gameSession); //save
    GameSession getGameSessionWithId(Long id);
    GameSession updateGameSession(GameSession gameSession);
    GameSession addUserToGameSession(Long id, User user);
    boolean removeUserFromGameSession(Long id, User user);
//    GameSessionRole getRoleOfUserInGameSession(long id, String username);
//    GameSession upgradeAccessLevelOfUserInGameSession(long id, User user);
}
