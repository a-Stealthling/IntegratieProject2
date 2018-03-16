package be.kdg.kandoe.repository.jpa;

import be.kdg.kandoe.domain.theme.Card;
import be.kdg.kandoe.domain.theme.CardSubTheme;
import be.kdg.kandoe.domain.theme.SubTheme;
import be.kdg.kandoe.domain.theme.Theme;
import be.kdg.kandoe.repository.jpa.CardJpa;
import be.kdg.kandoe.repository.jpa.CardSubThemeJpa;
import be.kdg.kandoe.repository.jpa.SubThemeJpa;
import be.kdg.kandoe.repository.jpa.ThemeJpa;
import be.kdg.kandoe.service.exception.ConversionException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class JpaConverter {
    public static Theme toTheme(ThemeJpa jpa, boolean callByConverter) {
        if (jpa == null) {
            return null;
        }
        if (!jpa.getClass().equals(ThemeJpa.class)) {
            throw new ConversionException("Parameter is not correct Class for conversion");
        }
        Theme theme = new Theme();
        theme.setThemeId(jpa.getThemeId());
        theme.setDescription(jpa.getDescription());
        theme.setName(jpa.getName());
        if (!callByConverter) {
            if (jpa.getSubThemes() != null) {
                theme.setSubThemes(jpa.getSubThemes().stream().map(st -> JpaConverter.toSubTheme(st, true)).collect(Collectors.toList()));
            }
        }
        return theme;
    }

    public static ThemeJpa toThemeJpa(Theme theme, boolean callByConverter) {
        if (theme == null) {
            return null;
        }
        if (!theme.getClass().equals(Theme.class)) {
            throw new ConversionException("Parameter is not correct Class for conversion");
        }
        ThemeJpa jpa = new ThemeJpa();
        jpa.setName(theme.getName());
        jpa.setDescription((theme.getDescription()));
        jpa.setThemeId(theme.getThemeId());
        if (!callByConverter) {
            if (theme.getSubThemes() != null) {
                jpa.setSubThemes(theme.getSubThemes().stream().map(st -> JpaConverter.toSubThemeJpa(st, true)).collect(Collectors.toList()));
            }
        }
        return jpa;
    }

    public static SubTheme toSubTheme(SubThemeJpa jpa, boolean callByConverter) {
        if (jpa == null) {
            return null;
        }
        if (!jpa.getClass().equals(SubThemeJpa.class)) {
            throw new ConversionException("Parameter is not correct Class for conversion");
        }
        SubTheme subTheme = new SubTheme();
        subTheme.setTheme(JpaConverter.toTheme(jpa.getTheme(), true));
        subTheme.setSubThemeId(jpa.getSubThemeId());
        subTheme.setSubThemeName(jpa.getSubThemeName());
        subTheme.setSubThemeDescription(jpa.getSubThemeDescription());
        if (!callByConverter) {
            if (jpa.getCardSubThemes() != null) {
                subTheme.setCardSubThemes(jpa.getCardSubThemes().stream().map(cst -> JpaConverter.toCardSubTheme(cst)).collect(Collectors.toList()));
            }
        }

        return subTheme;
    }

    public static SubThemeJpa toSubThemeJpa(SubTheme subTheme, boolean callByConverter) {
        if (subTheme == null) {
            return null;
        }
        if (!subTheme.getClass().equals(SubTheme.class)) {
            throw new ConversionException("Parameter is not correct Class for conversion");
        }
        SubThemeJpa jpa = new SubThemeJpa();
        jpa.setSubThemeId(subTheme.getSubThemeId());
        jpa.setSubThemeName(subTheme.getSubThemeName());
        jpa.setSubThemeDescription(subTheme.getSubThemeDescription());
        jpa.setTheme(JpaConverter.toThemeJpa(subTheme.getTheme(), true));
        if (!callByConverter) {
            if (subTheme.getCardSubThemes() != null) {
                jpa.setCardSubThemes(subTheme.getCardSubThemes().stream().map(cst -> JpaConverter.toCardSubThemeJpa(cst)).collect(Collectors.toList()));
            }
        }
        return jpa;
    }

    public static Card toCard(CardJpa cardJpa, boolean callByConverter) {
        if (cardJpa == null) {
            return null;
        }
        if (!cardJpa.getClass().equals(CardJpa.class)) {
            throw new ConversionException("Parameter is not correct Class for conversion");
        }
        Card newCard = new Card();
        newCard.setCardId(cardJpa.getCardId());
        newCard.setName(cardJpa.getName());
        newCard.setDescription(cardJpa.getDescription());
        newCard.setDefaultCard(cardJpa.isDefaultCard());
        if (!callByConverter) {
            newCard.setCardSubThemes(cardJpa.getCardSubThemes().stream().map(cst -> JpaConverter.toCardSubTheme(cst)).collect(Collectors.toList()));
        }
        return newCard;
    }

    public static CardJpa toCardJpa(Card card, boolean callByConverter) {
        if (card == null) {
            return null;
        }
        if (!card.getClass().equals(Card.class)) {
            throw new ConversionException("Parameter is not correct Class for conversion");
        }
        CardJpa newJpa = new CardJpa();
        newJpa.setCardId(card.getCardId());
        newJpa.setName(card.getName());
        newJpa.setDescription(card.getDescription());
        newJpa.setDefaultCard(card.isDefaultCard());
        if (!callByConverter) {
            newJpa.setCardSubThemes(card.getCardSubThemes().stream().map(cst -> JpaConverter.toCardSubThemeJpa(cst)).collect(Collectors.toList()));
        }
        return newJpa;
    }

    public static CardSubTheme toCardSubTheme(CardSubThemeJpa jpa) {
        if (jpa == null) {
            return null;
        }
        CardSubTheme cardSubTheme = new CardSubTheme();
        cardSubTheme.setCardSubThemeId(jpa.getCardSubThemeId());
        cardSubTheme.setCard(toCard(jpa.getCard(), true));
        cardSubTheme.setSubTheme(toSubTheme(jpa.getSubTheme(), true));
        return cardSubTheme;
    }

    public static CardSubThemeJpa toCardSubThemeJpa(CardSubTheme cardSubTheme) {
        if (cardSubTheme == null) {
            return null;
        }
        CardSubThemeJpa jpa = new CardSubThemeJpa();
        jpa.setCardSubThemeId(cardSubTheme.getCardSubThemeId());
        jpa.setCard(toCardJpa(cardSubTheme.getCard(), true));
        jpa.setSubTheme(toSubThemeJpa(cardSubTheme.getSubTheme(), true));
        return jpa;
    }
}
