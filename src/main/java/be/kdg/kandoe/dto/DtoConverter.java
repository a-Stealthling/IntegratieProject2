package be.kdg.kandoe.dto;

import be.kdg.kandoe.domain.theme.Theme;

public abstract class DtoConverter {

    public static ThemeDto toThemeDto(Theme theme){
        ThemeDto newDto = new ThemeDto();
        newDto.setThemeId(theme.getThemeId());
        newDto.setName(theme.getName());
        newDto.setDescription(theme.getDescription());
        return newDto;
    }
}
