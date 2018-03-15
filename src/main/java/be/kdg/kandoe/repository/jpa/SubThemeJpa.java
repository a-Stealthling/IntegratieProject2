package be.kdg.kandoe.repository.jpa;

import be.kdg.kandoe.domain.theme.SubTheme;
import be.kdg.kandoe.domain.theme.Theme;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;

@Entity
@Table(name="SUBTHEME")
public class SubThemeJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Generated(GenerationTime.INSERT)
    @Column(nullable = false,name = "subthemeId")
    private long subThemeId;


    @ManyToOne(targetEntity = ThemeJpa.class,cascade = CascadeType.REMOVE)
    @JoinColumn(name="themeId_FK")
    private ThemeJpa theme;

    @Column
    private String subThemeName;

    @Column
    private String subThemeDescription;

    public SubThemeJpa(){

    }


    public SubThemeJpa(SubTheme subTheme){
//        if(subTheme.getTheme()!=null){
//            this.theme=ThemeJpa.fromTheme(subTheme.getTheme());
//        }
        this.subThemeName=subTheme.getSubThemeName();
        this.subThemeDescription=subTheme.getSubThemeDescription();
    }

    public long getSubThemeId() {
        return subThemeId;
    }

    public ThemeJpa getTheme() {
        return this.theme;
    }

    public void setTheme(Theme theme) {
        this.theme = ThemeJpa.fromTheme(theme);
    }

    public void setThemeNoConvert(ThemeJpa theme){
        this.theme = theme;
    }

    public String getSubThemeName() {
        return subThemeName;
    }

    public void setSubThemeName(String subThemeName) {
        this.subThemeName = subThemeName;
    }

    public String getSubThemeDescription() {
        return subThemeDescription;
    }

    public void setSubThemeDescription(String subThemeDescription) {
        this.subThemeDescription = subThemeDescription;
    }

    public static SubThemeJpa fromSubTheme(SubTheme subTheme){
        return new SubThemeJpa(subTheme);
    }

    public SubTheme toSubTheme(){
        SubTheme subTheme = new SubTheme();
        boolean called = false;
        if(this.theme!=null && !called){
            subTheme.setTheme(this.theme.toTheme());
            called = true;
        }
        subTheme.setSubThemeId(this.subThemeId);
        subTheme.setSubThemeName(this.subThemeName);
        subTheme.setSubThemeDescription(this.subThemeDescription);
        return subTheme;
    }
}
