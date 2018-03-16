package be.kdg.kandoe.repository.jpa;

import be.kdg.kandoe.domain.theme.SubTheme;

import javax.persistence.*;

@Entity
@Table(name = "CARD_SUBTHEME")
public class CardSubThemeJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long cardSubThemeId;

    @ManyToOne
    @JoinColumn(name = "subThemeId_FK")
    private SubThemeJpa subTheme;

    @ManyToOne
    @JoinColumn(name = "cardId_FK")
    private CardJpa card;

    public CardSubThemeJpa() {

    }

    public CardSubThemeJpa(CardJpa card, SubThemeJpa subTheme) {
        this.card = card;
        this.subTheme = subTheme;
    }

    public CardSubThemeJpa(long id, CardJpa card, SubThemeJpa subTheme) {
        this.card = card;
        this.subTheme = subTheme;
    }

    public long getCardSubThemeId() {
        return cardSubThemeId;
    }

    public void setCardSubThemeId(long cardSubThemeId) {
        this.cardSubThemeId = cardSubThemeId;
    }

    public SubThemeJpa getSubTheme() {
        return subTheme;
    }

    public void setSubTheme(SubThemeJpa subTheme) {
        this.subTheme = subTheme;
    }

    public CardJpa getCard() {
        return card;
    }

    public void setCard(CardJpa card) {
        this.card = card;
    }
}
