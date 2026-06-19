package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConvertDados;
import org.springframework.boot.CommandLineRunner;

//Quando essa instrução esta comentada, a aplicação não lê esta classe para executar
//@SpringBootApplication
public class MyScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		//SpringApplication.run(MyScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoApi();

		//System.out.println("Todas as infos da Serie: ");
		//System.out.println(json);

		String nomeSerie = "stranger+things";
		String apiKey = "c64822c4";
		var json = consumoApi.obterDados("https://www.omdbapi.com/?t="+nomeSerie+"&apikey="+apiKey);
		ConvertDados conversor = new ConvertDados();

		System.out.print("Dados da serie: ");
		DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dadosSerie);
		System.out.println("---------------------------------------------------------------------------------------------------------------------");

		DadosEpisodio dadosEpisodio;

		for (int i = 1; i <= dadosSerie.totalTemporadas() ; i++) {
			System.out.println("TEMPORADA: "+i);
			json = consumoApi.obterDados("https://www.omdbapi.com/?t="+nomeSerie+"&Season="+i+"&apikey="+apiKey);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			for (int j = 1; j <= dadosTemporada.episodios().size(); j++) {
				json  = consumoApi.obterDados("https://www.omdbapi.com/?t="+nomeSerie+"&Season="+i+"&episode="+j+"&apikey="+apiKey);
				System.out.println("Infomações do episódio: "+j);
				dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);
				System.out.println(dadosEpisodio);
			}

		}
	}
}
