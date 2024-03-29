package com.montaury.pokebagarre.metier;

import com.montaury.pokebagarre.erreurs.ErreurMemePokemon;
import com.montaury.pokebagarre.erreurs.ErreurPokemonNonRenseigne;
import com.montaury.pokebagarre.erreurs.ErreurRecuperationPokemon;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



class BagarreTest {

    Bagarre  bagarre;
    @BeforeEach
    void creer_bagarre(){
        this.bagarre = new Bagarre();
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
}