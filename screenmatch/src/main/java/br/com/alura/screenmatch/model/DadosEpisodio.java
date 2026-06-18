package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Aqui definimos que o que não for mapeado "Desconhecido" ele só ignora
@JsonIgnoreProperties(ignoreUnknown = true)

//Aqui fazemos o mapemantos dos atributos que desejamos "Bindar" lá da api
public record DadosEpisodio(@JsonAlias("Title") String titulo,
                            @JsonAlias("Episode") Integer numero,
                            @JsonAlias("imdbRating")String avaliacao,
                            @JsonAlias("Released")String dataLancamento) {
}
