
package be.kdg.kandoe.domain.theme;

public class CardSubTheme {
    private long cardSubThemeId;
    private Card card;
    private SubTheme subTheme;


    public CardSubTheme() {

    }

    public CardSubTheme(Card card, SubTheme subTheme) {
        this.card = card;
        this.subTheme = subTheme;
    }

    public CardSubTheme(long id, Card card, SubTheme subTheme) {
        this.cardSubThemeId = id;
        this.card = card;
        this.subTheme = subTheme;
    }

    public long getCardSubThemeId() {
        return cardSubThemeId;
    }

    public void setCardSubThemeId(long cardSubThemeId) {
        this.cardSubThemeId = cardSubThemeId;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public SubTheme getSubTheme() {
        return subTheme;
    }

    public void setSubTheme(SubTheme subTheme) {
        this.subTheme = subTheme;
    }
}
