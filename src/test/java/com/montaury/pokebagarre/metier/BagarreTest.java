package com.montaury.pokebagarre.metier;

import com.montaury.pokebagarre.erreurs.ErreurMemePokemon;
import com.montaury.pokebagarre.erreurs.ErreurPokemonNonRenseigne;
import com.montaury.pokebagarre.erreurs.ErreurRecuperationPokemon;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.montaury.pokebagarre.webapi.PokeBuildApi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class BagarreTest {
    Bagarre  bagarre;
    PokeBuildApi fausseApi;

    @BeforeEach
    void creer_bagarre(){
        fausseApi = Mockito.mock(PokeBuildApi.class);
                this.bagarre = new Bagarre(fausseApi);
    }

    @Test
    void pokemon_1_devrait_envoyer_une_erreur_car_il_est_vide(){
        var erreur = catchThrowable(() -> bagarre.demarrer("","mewtwo"));

        assertThat(erreur)
                .isInstanceOf(ErreurPokemonNonRenseigne.class)
                .hasMessage("Le premier pokemon n'est pas renseigne");
    }

    @Test
    void pokemon_2_devrait_envoyer_une_erreur_car_il_est_vide(){
        var erreur = catchThrowable(() -> bagarre.demarrer("mewtwo",""));

        assertThat(erreur)
                .isInstanceOf(ErreurPokemonNonRenseigne.class)
                .hasMessage("Le second pokemon n'est pas renseigne");
    }

    @Test
    void pokemon_1_devrait_envoyer_une_erreur_car_il_est_null(){

        var erreur = catchThrowable(() -> bagarre.demarrer(null,"mewtwo"));

        assertThat(erreur)
                .isInstanceOf(ErreurPokemonNonRenseigne.class)
                .hasMessage("Le premier pokemon n'est pas renseigne");
    }

    @Test
    void pokemon_2_devrait_envoyer_une_erreur_car_il_est_null(){

        var erreur = catchThrowable(() -> bagarre.demarrer("mewtwo",null));

        assertThat(erreur)
                .isInstanceOf(ErreurPokemonNonRenseigne.class)
                .hasMessage("Le second pokemon n'est pas renseigne");
    }

    @Test
    void devrait_envoyer_une_erreur_car_pokemon_1_et_pokemon_2_sont_les_memes(){

        var erreur = catchThrowable(() -> bagarre.demarrer("mewtwo","mewtwo"));

        assertThat(erreur)
                .isInstanceOf(ErreurMemePokemon.class)
                .hasMessage("Impossible de faire se bagarrer un pokemon avec lui-meme");
    }

    @Test
    void devrait_envoyer_erreur_quand_premier_pokemon_non_rensaigne(){//given
        Mockito.when(fausseApi.recupererParNom("pikachu"))
                .thenReturn(CompletableFuture.failedFuture(new ErreurRecuperationPokemon("pikachu"))
                );

        Mockito.when(fausseApi.recupererParNom("mewtwo"))
                .thenReturn(CompletableFuture.completedFuture(new Pokemon("mewtwo", "url2",
                                new Stats(3, 24))
                        )
                );
        //when
        CompletableFuture<Pokemon> futurVainqueur = bagarre.demarrer ("pikachu","mewtwo");

        //then
        assertThat(futurVainqueur)
                .failsWithin(Duration.ofSeconds(2))
                .withThrowableOfType(ExecutionException.class)
                .havingCause()
                .isInstanceOf(ErreurRecuperationPokemon.class)
                .withMessage("Impossible de recuperer les details sur 'pikachu'") ;
    }

    @Test
    void devrait_envoyer_erreur_quand_second_pokemon_non_rensaigne(){
        //given
        Mockito.when(fausseApi.recupererParNom("mewtwo"))
                .thenReturn(CompletableFuture.completedFuture(new Pokemon("mewtwo", "url1",
                                new Stats(3, 24))
                        )
                );
        Mockito.when(fausseApi.recupererParNom("pikachu"))
                .thenReturn(CompletableFuture.failedFuture(new ErreurRecuperationPokemon("pikachu"))
                );
        //when
        CompletableFuture<Pokemon> futurVainqueur = bagarre.demarrer ("mewtwo","pikachu");

        //then
        assertThat(futurVainqueur)
                .failsWithin(Duration.ofSeconds(2))
                .withThrowableOfType(ExecutionException.class)
                .havingCause()
                .isInstanceOf(ErreurRecuperationPokemon.class)
                .withMessage("Impossible de recuperer les details sur 'pikachu'") ;
    }


    @Test
    void devrait_envoyer_victoir_du_second_car_plus_attaque() {
        //given
        Mockito.when(fausseApi.recupererParNom("mewtwo"))
                .thenReturn(CompletableFuture.completedFuture(new Pokemon("mewtwo", "url1",
                                new Stats(3, 24))
                        )
                );
        Mockito.when(fausseApi.recupererParNom("mew"))
                .thenReturn(CompletableFuture.completedFuture(new Pokemon("mew", "url2",
                                new Stats(30, 24))
                        )
                );
        //when
        CompletableFuture<Pokemon> futurVainqueur = bagarre.demarrer("mewtwo", "mew");

        //then
        assertThat(futurVainqueur)
                .succeedsWithin(Duration.ofSeconds(2))
                .satisfies(pokemon -> {
                            assertThat(pokemon.getNom())
                                    .isEqualTo("mew");
                        }
                );

    }

    @Test
    void devrait_envoyer_victoir_du_second_car_egalite() {
        //given
        Mockito.when(fausseApi.recupererParNom("mewtwo"))
                .thenReturn(CompletableFuture.completedFuture(new Pokemon("mewtwo", "url1",
                                new Stats(30, 24))
                        )
                );
        Mockito.when(fausseApi.recupererParNom("mew"))
                .thenReturn(CompletableFuture.completedFuture(new Pokemon("mew", "url2",
                                new Stats(30, 24))
                        )
                );
        //when
        CompletableFuture<Pokemon> futurVainqueur = bagarre.demarrer("mewtwo", "mew");

        //then
        assertThat(futurVainqueur)
                .succeedsWithin(Duration.ofSeconds(2))
                .satisfies(pokemon -> {
                            assertThat(pokemon.getNom())
                                    .isEqualTo("mewtwo");
                        }
                );

    }
}