package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConvertDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConvertDados conversor = new ConvertDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String APIKEY = "&apikey=c64822c4";

    public void exibeMenu() {

        var menu = """
                1 - Buscar séries
                2 - Buscar epísodios
                
                0- Sair
                
                """;

        System.out.println(menu);
        var opcao = leitura.nextInt();
        leitura.nextLine();

        switch (opcao) {
            case 1:
                buscarSerieWeb();
                break;
            case 2:
                buscarEpisodioPorSerie();
                break;
            case 0:
                System.out.println("Saindo...");
                break;
            default:
                System.out.println("Opção inválida");

        }

    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + APIKEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie(){
        DadosSerie dadosSerie = getDadosSerie();
        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 0; i<= dadosSerie.totalTemporadas();i++){
            var json = consumo.obterDados(ENDERECO + dadosSerie.titulo().replace(" ", "+") + "&season=" + i + APIKEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);
    }

}


//        List<DadosTemporada> temporadas = new ArrayList<>();
//        for (int i = 1; i <= dadosSerie.totalTemporadas() ; i++) {
//			json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+")+"&season="+i+APIKEY);
//			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
//			temporadas.add(dadosTemporada);
//		}

//		temporadas.forEach(System.out::println);

//        for (int i =0; i <dadosSerie.totalTemporadas(); i++){
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//            for (int j = 0; j <episodiosTemporada.size(); j++){
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }

//        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));


//        List <DadosEpisodio> dadosEpisodios = temporadas.stream()
//                .flatMap(t -> t.episodios().stream())
//                .collect(Collectors.toList());

//        System.out.println("\n Top 5 Episodios");
//        dadosEpisodios.stream()
//                .filter( e-> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//                .limit(5)
//                .forEach(System.out::println);
//
//        List<Episodio> episodios = temporadas.stream()
//                .flatMap(t -> t.episodios().stream()
//                        .map(d -> new Episodio(t.numero(), d))
//                ).collect(Collectors.toList());
//
//        episodios.forEach(System.out::println);

//        System.out.println("Informe um trecho do titulo de um episodio: ");
//        var trechoTitulo = leitura.nextLine();
//
//
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
//                .findFirst();
//
//        if (episodioBuscado.isPresent()){
//            System.out.println("Episodio encontrado: ");
//            System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
//        }else {
//            System.out.println("Episodio não encontrado");
//        }
//
//        System.out.println("A partir de que ano gostaria de ver os episodios");
//
//        var ano = leitura.nextInt();
//        leitura.nextLine();
//
//        LocalDate dateBusca = LocalDate.of(ano, 1, 1);
//
//        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dateBusca))
//                .forEach(e -> System.out.println(
//                        "Temporada: "+e.getTemporada()+
//                                " Episodio: "+ e.getTitulo() +
//                                " Data Lançamento: "+ e.getDataLancamento().format(formatador)
//                ));

        //Mapeamento de estaticas com Collectors
//        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
//                .filter(e-> e.getAvaliacao()> 0.0)
//                .collect(Collectors.groupingBy(Episodio::getTemporada,
//                        Collectors.averagingDouble(Episodio::getAvaliacao)));
//
//        System.out.println(avaliacoesPorTemporada);
//
//        //Sumario de estatisticas basicas cont, sum, min, max, media
//        DoubleSummaryStatistics est = episodios.stream()
//                .filter(e-> e.getAvaliacao() > 0.0)
//                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
//
//        System.out.println("Média: "+ est.getAverage());
//        System.out.println("Melhor episodio: "+ est.getMax());
//        System.out.println("Pior episodio: "+ est.getMin());
//        System.out.println("Quantidade: "+ est.getCount());
//    }
//
//    public void exercicioStream(){
//        //filtrar por uma letra inicial e tranformar em maiuscula
//        List<String> nomes = Arrays.asList("Jacque", "Iasmin","Paulo", "Rodrigo", "Nico");
//
//        nomes.stream().sorted().limit(3).filter(n-> n.startsWith("N")).map(n -> n.toUpperCase()).forEach(System.out::println);
//    }
//
//    public void exerciciosStream(){
        //Dada a lista de números inteiros a seguir, encontre o maior número dela.
       // List<Integer> numeros = Arrays.asList(10, 20, 30, 40, 50);

        //Dada a lista de nomes abaixo, concatene-os separados por vírgula
        //List<String> nomes = Arrays.asList("Alice", "Bob", "Charlie");

        //Dada a lista de números inteiros abaixo, calcule a soma dos quadrados dos números pares
        //List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6);

        //Dada uma lista de números inteiros, separe os números pares dos ímpares.
        //List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6);






