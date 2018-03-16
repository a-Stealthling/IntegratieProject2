package be.kdg.kandoe.dto.gameSession;

import be.kdg.kandoe.dto.ThemeDto;

import java.util.List;

public class CreateGameSessionDto {
    private String title;
    private String organisator; //Username of organisator
    private boolean isOrganisatorPlaying;
    private boolean allowUsersToAdd;
    private int limit; //add limit
    private int selectionLimit;
    private int timer; //Turn length
    private Long gameSessionId;
    private List<String> subOrganisators;

    private ThemeDto themeForSession;

    public CreateGameSessionDto() {
    }

    public CreateGameSessionDto(String title, String organisator, boolean isOrganisatorPlaying, boolean allowUsersToAdd, int limit, int selectionLimit, int timer) {
        this.title = title;
        this.organisator = organisator;
        this.isOrganisatorPlaying = isOrganisatorPlaying;
        this.allowUsersToAdd = allowUsersToAdd;
        this.limit = limit;
        this.selectionLimit = selectionLimit;
        this.timer = timer;
    }

    public CreateGameSessionDto(String title, String organisator, boolean isOrganisatorPlaying, boolean allowUsersToAdd, int limit, int selectionLimit, int timer, ThemeDto themeForSession) {
        this.title = title;
        this.organisator = organisator;
        this.isOrganisatorPlaying = isOrganisatorPlaying;
        this.allowUsersToAdd = allowUsersToAdd;
        this.limit = limit;
        this.selectionLimit = selectionLimit;
        this.timer = timer;
        this.themeForSession = themeForSession;
    }

    public CreateGameSessionDto(String title, String organisator, boolean isOrganisatorPlaying, boolean allowUsersToAdd, int limit, int selectionLimit, int timer, List<String> subOrganisators) {
        this.title = title;
        this.organisator = organisator;
        this.isOrganisatorPlaying = isOrganisatorPlaying;
        this.allowUsersToAdd = allowUsersToAdd;
        this.limit = limit;
        this.selectionLimit = selectionLimit;
        this.timer = timer;
        this.subOrganisators = subOrganisators;
    }

    public CreateGameSessionDto(String title, String organisator, boolean isOrganisatorPlaying, boolean allowUsersToAdd, int limit, int selectionLimit, int timer, List<String> subOrganisators, ThemeDto themeForSession) {
        this.title = title;
        this.organisator = organisator;
        this.isOrganisatorPlaying = isOrganisatorPlaying;
        this.allowUsersToAdd = allowUsersToAdd;
        this.limit = limit;
        this.selectionLimit = selectionLimit;
        this.timer = timer;
        this.subOrganisators = subOrganisators;
        this.themeForSession = themeForSession;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrganisator() {
        return organisator;
    }

    public void setOrganisator(String organisator) {
        this.organisator = organisator;
    }

    public boolean isOrganisatorPlaying() {
        return isOrganisatorPlaying;
    }

    public void setisOrganisatorPlaying(boolean organisatorPlaying) {
        isOrganisatorPlaying = organisatorPlaying;
    }

    public boolean isAllowUsersToAdd() {
        return allowUsersToAdd;
    }

    public void setAllowUsersToAdd(boolean allowUsersToAdd) {
        this.allowUsersToAdd = allowUsersToAdd;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getSelectionLimit() {
        return selectionLimit;
    }

    public void setSelectionLimit(int selectionLimit) {
        this.selectionLimit = selectionLimit;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public Long getGameSessionId() {
        return gameSessionId;
    }

    public void setGameSessionId(Long gameSessionId) {
        this.gameSessionId = gameSessionId;
    }

    public void setOrganisatorPlaying(boolean organisatorPlaying) {
        isOrganisatorPlaying = organisatorPlaying;
    }

    public List<String> getSubOrganisators() {
        return subOrganisators;
    }

    public void setSubOrganisators(List<String> subOrganisators) {
        this.subOrganisators = subOrganisators;
    }

    public ThemeDto getthemeForSession() {
        return themeForSession;
    }

    public void setthemeForSession(ThemeDto themeForSession) {
        this.themeForSession = themeForSession;
    }

}

