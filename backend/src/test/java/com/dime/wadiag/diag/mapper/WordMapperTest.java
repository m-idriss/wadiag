package com.dime.wadiag.diag.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import com.dime.wadiag.diag.dto.WordDto;
import com.dime.wadiag.diag.model.Word;

class WordMapperTest {

    @Mock
    private ModelMapper modelMapper;

    private final WordMapper wordMapper = new WordMapper(new ModelMapper());

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should map Word entity to WordDto")
    void test_map_to_word_dto() {
        // Given
        Word wordEntity = new Word();
        wordEntity.setName("exampleWord");

        WordDto expectedDto = new WordDto();
        expectedDto.setWord("exampleWord");

        when(modelMapper.map(wordEntity, WordDto.class)).thenReturn(expectedDto);

        // When
        WordDto resultDto = wordMapper.toWordDto(wordEntity);

        // Then
        assertThat(resultDto).isNotNull();
        assertThat(resultDto.getWord()).isEqualTo(expectedDto.getWord());
    }

    @Test
    @DisplayName("Should map WordDto to Word entity")
    void test_map_to_word() {
        // Given
        WordDto wordDto = new WordDto();
        wordDto.setWord("exampleWord");

        Word expectedEntity = new Word();
        expectedEntity.setName("exampleWord");

        when(modelMapper.map(wordDto, Word.class)).thenReturn(expectedEntity);

        // When
        Word resultEntity = wordMapper.fromWordDto(wordDto);

        // Then
        assertThat(resultEntity).isNotNull();
        assertThat(resultEntity.getName()).isEqualTo(expectedEntity.getName());
    }
}
