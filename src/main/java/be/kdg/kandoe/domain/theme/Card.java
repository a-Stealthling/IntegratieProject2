package be.kdg.kandoe.domain.theme;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Card {
    private long cardId;
    private String name;
    private String description;
    private String imagePath;
    private boolean isDefaultCard;
    private List<CardSubTheme> cardSubThemes;




    public Card() {
        cardSubThemes = new ArrayList<>();
    }

    public List<CardSubTheme> getCardSubThemes() {
        return this.cardSubThemes;
    }

    public void setCardSubThemes(List<CardSubTheme> cardSubThemes) {
        this.cardSubThemes = cardSubThemes;
    }

    public long getCardId() {
        return cardId;
    }

    public void setCardId(long cardId) {
        this.cardId = cardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /*public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }*/

    public boolean isDefaultCard() {
        return isDefaultCard;
    }

    public void setDefaultCard(boolean defaultCard) {
        isDefaultCard = defaultCard;
    }

    public void addCardSubTheme(CardSubTheme cardSubTheme) {
        if (cardSubTheme != null) {
            cardSubThemes.add(cardSubTheme);
        }
    }

    public void removeCardSubTheme(CardSubTheme cardSubTheme) {
        if (cardSubThemes.contains(cardSubTheme)) {
            cardSubThemes.remove(cardSubTheme);
        }
    }

}
