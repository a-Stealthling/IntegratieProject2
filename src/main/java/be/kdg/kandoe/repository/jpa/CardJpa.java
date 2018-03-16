package be.kdg.kandoe.repository.jpa;

import be.kdg.kandoe.domain.theme.SubTheme;
import be.kdg.kandoe.dto.converter.DtoConverter;
import be.kdg.kandoe.dto.theme.SubThemeDto;
import be.kdg.kandoe.repository.jpa.converter.JpaConverter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "CARD")
public class CardJpa {
    @Column(name = "cardId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cardId;
    private String name;
    private String description;
    //private String imagePath;
    //private byte[] image;
    private boolean isDefaultCard;
    @Column
    @OneToMany(targetEntity = CardSubThemeJpa.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "card")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<CardSubThemeJpa> cardSubThemes;

    public CardJpa() {
        this.cardSubThemes = new ArrayList<>();
    }

    public long getCardId() {
        return cardId;
    }

    public void setCardId(long id) {
        this.cardId = id;
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

    /*public String getImagePath() {
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

    public List<CardSubThemeJpa> getCardSubThemes() {
        return this.cardSubThemes;
    }

    public void setCardSubThemes(List<CardSubThemeJpa> subThemes) {
        this.cardSubThemes = subThemes;
    }
}