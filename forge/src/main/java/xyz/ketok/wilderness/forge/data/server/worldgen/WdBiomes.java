package xyz.ketok.wilderness.forge.data.server.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import xyz.ketok.wilderness.Wilderness;
import net.minecraft.data.worldgen.BootstapContext;

import static net.minecraft.data.worldgen.placement.VegetationPlacements.*;
import static net.minecraft.data.worldgen.BiomeDefaultFeatures.*;
import static net.minecraft.world.level.levelgen.GenerationStep.Decoration.*;
import static net.minecraft.world.entity.EntityType.*;
import static net.minecraft.world.entity.MobCategory.*;
import static xyz.ketok.wilderness.forge.data.server.worldgen.WdPlacedFeatures.*;

//TODO: Biome tags
public class WdBiomes {
    public static final ResourceKey<Biome> OLD_GROWTH_FOREST = key("old_growth_forest");
    public static final ResourceKey<Biome> MIXED_FOREST = key("mixed_forest");

    private static ResourceKey<Biome> key(String name) {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(Wilderness.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<Biome> context) {
        context.register(OLD_GROWTH_FOREST, createOldGrowthForestBiome(context));
        context.register(MIXED_FOREST, createMixedForestBiome(context));
    }

    private static Biome createOldGrowthForestBiome(BootstapContext<Biome> context) {
        var generation = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(generation);

        addForestFlowers(generation);
        addDefaultOres(generation);
        addDefaultSoftDisks(generation);
        addDefaultFlowers(generation);
        addForestGrass(generation);
        generation.addFeature(VEGETAL_DECORATION, PATCH_TALL_GRASS);
        generation.addFeature(VEGETAL_DECORATION, PATCH_DEAD_BUSH);
        addDefaultExtraVegetation(generation);
        generation.addFeature(VEGETAL_DECORATION, TREES_OLD_GROWTH_FOREST);
        generation.addFeature(VEGETAL_DECORATION, BROWN_RED_MUSHROOM_PATCH);
        generation.addFeature(LOCAL_MODIFICATIONS, FOREST_ROCK_RARE);
        generation.addFeature(LOCAL_MODIFICATIONS, PATCH_MOSS);

        var spawns = new MobSpawnSettings.Builder();
        farmAnimals(spawns);
        commonSpawns(spawns);
        spawns.addSpawn(CREATURE, new SpawnerData(WOLF, 8, 4, 4));
        spawns.addSpawn(CREATURE, new SpawnerData(RABBIT, 2, 2, 3));

        return new WdBiomeBuilder()
                .generationAndSpawns(generation, spawns)
                .temperatureAndDownfall(0.7F, 0.8F)
                .build();
    }

    private static Biome createMixedForestBiome(BootstapContext<Biome> context) {
        var generation = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(generation);

        addDefaultOres(generation);
        addDefaultSoftDisks(generation);
        addDefaultFlowers(generation);
        addForestGrass(generation);
        generation.addFeature(VEGETAL_DECORATION, PATCH_DEAD_BUSH_2);
        addDefaultMushrooms(generation);
        addDefaultExtraVegetation(generation);
        generation.addFeature(VEGETAL_DECORATION, TREES_MIXED_FOREST);
        generation.addFeature(LOCAL_MODIFICATIONS, FOREST_ROCK_RARE);
        generation.addFeature(LOCAL_MODIFICATIONS, PATCH_COARSE_DIRT);
        generation.addFeature(LOCAL_MODIFICATIONS, PATCH_PODZOL);

        var spawns = new MobSpawnSettings.Builder();
        farmAnimals(spawns);
        commonSpawns(spawns);
        spawns.addSpawn(CREATURE, new SpawnerData(WOLF, 8, 4, 4));
        spawns.addSpawn(CREATURE, new SpawnerData(RABBIT, 2, 2, 3));
        spawns.addSpawn(CREATURE, new SpawnerData(FOX, 8, 2, 4));

        return new WdBiomeBuilder()
                .generationAndSpawns(generation, spawns)
                .temperatureAndDownfall(0.5F, 0.8F)
                .build();
    }

    private static void globalOverworldGeneration(BiomeGenerationSettings.Builder generation) {
        addDefaultCarversAndLakes(generation);
        addDefaultCrystalFormations(generation);
        addDefaultMonsterRoom(generation);
        addDefaultUndergroundVariety(generation);
        addDefaultSprings(generation);
        addSurfaceFreezing(generation);
    }
}
