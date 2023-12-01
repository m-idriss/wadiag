package com.dime.wadiag.diag.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dime.wadiag.diag.dto.WordDto;
import com.dime.wadiag.diag.model.Word;

@Component
public class WordMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public WordMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureModelMapper();
    }

    private void configureModelMapper() {
        if (modelMapper.getConfiguration() == null) {
            modelMapper.getConfiguration().setFieldMatchingEnabled(true)
                    .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                    .setMatchingStrategy(MatchingStrategies.STRICT);
        }

        // Customize the mapping between Word and WordDto using typeMap
        TypeMap<Word, WordDto> typeMapWord = modelMapper.createTypeMap(Word.class, WordDto.class);
        typeMapWord.addMapping(Word::getName, WordDto::setWord);

        // Add other custom mappings as needed
        TypeMap<WordDto, Word> typeMapWordDto = modelMapper.createTypeMap(WordDto.class, Word.class);
        typeMapWordDto.addMapping(WordDto::getWord, Word::setName);
    }

    public WordDto toWordDto(Word entity) {
        return modelMapper.map(entity, WordDto.class);
    }

    public Word fromWordDto(WordDto dto) {
        return modelMapper.map(dto, Word.class);
    }
}
