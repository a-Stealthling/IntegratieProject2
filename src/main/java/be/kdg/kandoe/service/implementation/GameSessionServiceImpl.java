package be.kdg.kandoe.service.implementation;

import be.kdg.kandoe.domain.GameSession;
import be.kdg.kandoe.domain.GameSessionRole;
import be.kdg.kandoe.domain.UserGameSessionInfo;
import be.kdg.kandoe.domain.user.User;
import be.kdg.kandoe.repository.declaration.GameSessionRepository;
import be.kdg.kandoe.service.declaration.GameSessionService;
import be.kdg.kandoe.service.declaration.UserService;
import be.kdg.kandoe.service.exception.GameSessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class GameSessionServiceImpl implements GameSessionService{


    private UserService userService;
    private GameSessionRepository gameSessionRepository;


    @Autowired
    public GameSessionServiceImpl(GameSessionRepository gameSessionRepository, UserService userService) {
        this.gameSessionRepository = gameSessionRepository;
        this.userService = userService;
    }

    @Override
    public List<GameSession> getAllGameSessions() {
        return gameSessionRepository.findAll();
    }

    @Override
    public List<GameSession> getUserGameSessions(String username) {
        return gameSessionRepository.findGameSessionsOfUserWithUsername(username);
    }

    @Override
    public GameSession addGameSession(GameSession gameSession) {
        return gameSessionRepository.save(gameSession);
    }

    @Override
    public GameSession getGameSessionWithId(Long id) {
        GameSession gameSession = gameSessionRepository.findOne(id);

        if (gameSession == null) throw new GameSessionException("Gamesession with " + id + "was not found!");

        return gameSession;
    }

    @Override
    public GameSession updateGameSession(GameSession gameSession) {
        GameSession u = gameSessionRepository.save(gameSession);
        if (u == null)
            throw new GameSessionException("GameSession not saved");
        return u;
    }

    @Override
    public GameSession addUserToGameSession(Long id, User user) {
        if (checkIfGameSessionExists(id)) throw new GameSessionException("Game session with " + id + "was not found!");
        if (checkIfUserAlreadyInSession(id, user)) throw new GameSessionException("User is already part of the game session!");

        GameSession gameSession = gameSessionRepository.findOne(id);
        UserGameSessionInfo userGameSessionInfo = gameSession.addUserToGameSession(user);
        updateGameSession(gameSession);
        //userService.addUserGameSessionInfo(user.getUserId(), userGameSessionInfo);
        return gameSession;
    }



    @Override
    public boolean removeUserFromGameSession(Long id, User user) {
        if (checkIfGameSessionExists(id)) throw new GameSessionException("Game session with " + id + "was not found!");
        if (!checkIfUserAlreadyInSession(id, user)) throw new GameSessionException("User is not part of the game session!");
        boolean done = false;
        GameSession gameSession = gameSessionRepository.findOne(id);

        for(UserGameSessionInfo userGameSessionInfo : gameSession.getUserGameSessionInfos()){
            if(userGameSessionInfo.getUser() == user){
                gameSession.removeUserFromGameSession(userGameSessionInfo);
                user.removeUserGameSessionInfo(userGameSessionInfo);
                done = true;
                break;
            }
        }
        updateGameSession(gameSession);
        userService.updateUserNoPassword(user);
        return done;
    }



    //Returns false when gameSession doesn't exist yet / true if it does
    private boolean checkIfGameSessionExists(Long gameSessionId){
        return gameSessionRepository.findOne(gameSessionId) == null;
    }

    //Returns true when the user is already part of the session
    private boolean checkIfUserAlreadyInSession(Long gameSessionId, User userToAdd){
        GameSession gameSession = gameSessionRepository.findOne(gameSessionId);

        for(UserGameSessionInfo userGameSessionInfo : gameSession.getUserGameSessionInfos()){
            if(userGameSessionInfo.getUser().getUserId() == userToAdd.getUserId()){
                return true;
            }
        }

        return false;
    }

}
