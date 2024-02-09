package com.montaury.pokebagarre.metier;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

class PokemonTest {

    @Test
    void TestBestPokemonAttaque1(){
        // Creation des stats des pokemon
        Stats statsPokemon1 = new Stats(50,50);
        Stats statsPokemon2 = new Stats(1,50);

        //Creation des pokemon avec leurs stats respectives
        Pokemon pokemon1 = new Pokemon("Pokemon1","",statsPokemon1);
        Pokemon pokemon2 = new Pokemon("Pokemon2","",statsPokemon2);

        assertThat(pokemon1.estVainqueurContre(pokemon2)).isEqualTo(true);
    }

    @Test
    void TestBestPokemonAttaque2(){
        // Creation des stats des pokemon
        Stats statsPokemon1 = new Stats(1,50);
        Stats statsPokemon2 = new Stats(50,50);

        //Creation des pokemon avec leurs stats respectives
        Pokemon pokemon1 = new Pokemon("Pokemon1","",statsPokemon1);
        Pokemon pokemon2 = new Pokemon("Pokemon2","",statsPokemon2);

        assertThat(pokemon2.estVainqueurContre(pokemon1)).isEqualTo(true);
    }

    @Test
    void TestBestPokemonDefence1(){
        // Creation des stats des pokemon
        Stats statsPokemon1 = new Stats(50,50);
        Stats statsPokemon2 = new Stats(50,1);

        //Creation des pokemon avec leurs stats respectives
        Pokemon pokemon1 = new Pokemon("Pokemon1","",statsPokemon1);
        Pokemon pokemon2 = new Pokemon("Pokemon2","",statsPokemon2);

        assertThat(pokemon1.estVainqueurContre(pokemon2)).isEqualTo(true);
    }

    @Test
    void TestBestPokemonDefence2(){
        // Creation des stats des pokemon
        Stats statsPokemon1 = new Stats(50,1);
        Stats statsPokemon2 = new Stats(50,50);

        //Creation des pokemon avec leurs stats respectives
        Pokemon pokemon1 = new Pokemon("Pokemon1","",statsPokemon1);
        Pokemon pokemon2 = new Pokemon("Pokemon2","",statsPokemon2);

        assertThat(pokemon2.estVainqueurContre(pokemon1)).isEqualTo(true);
    }

    @Test
    void TestBestPokemonDefaultName(){
        // Creation des stats des pokemon
        Stats statsPokemon1 = new Stats(50,50);
        Stats statsPokemon2 = new Stats(50,50);

        //Creation des pokemon avec leurs stats respectives
        Pokemon pokemon1 = new Pokemon("Pokemon1","",statsPokemon1);
        Pokemon pokemon2 = new Pokemon("Pokemon2","",statsPokemon2);

        assertThat(pokemon1.estVainqueurContre(pokemon2)).isEqualTo(true);
    }
}