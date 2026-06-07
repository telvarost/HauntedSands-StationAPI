package com.github.telvarost.hauntedsands.datafixer;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.modificationstation.stationapi.api.datafixer.TypeReferences;

import java.util.Map;

/**
 * Renames entities saved with the pre-StationAPI-2.0.0-alpha.6 string ids
 * (e.g. "LostSoul") to the namespaced identifiers used by the new
 * EntityRegisterEvent (e.g. "hauntedsands:LostSoul").
 *
 * The old and new ids are intentionally hardcoded: they describe historical
 * save data and must not change even if registration code changes later.
 */
public class EntityIdentifierFix extends DataFix {
    private static final Map<String, String> RENAMES = Map.of(
            "LostSoul", "hauntedsands:LostSoul"
    );

    public EntityIdentifierFix(Schema outputSchema) {
        super(outputSchema, false);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        return TypeRewriteRule.seq(
                fixTypeEverywhereTyped(
                        "HauntedSandsChunkEntityIdentifierFix",
                        getInputSchema().getType(TypeReferences.CHUNK),
                        typed -> typed.update(DSL.remainderFinder(), chunk -> chunk.update("Level",
                                level -> level.update("Entities",
                                        entities -> entities.createList(entities.asStream().map(EntityIdentifierFix::fixEntity)))))
                ),
                fixTypeEverywhereTyped(
                        "HauntedSandsPlayerEntityIdentifierFix",
                        getInputSchema().getType(TypeReferences.PLAYER),
                        typed -> typed.update(DSL.remainderFinder(),
                                player -> player.update("Riding", EntityIdentifierFix::fixEntity))
                )
        );
    }

    private static Dynamic<?> fixEntity(Dynamic<?> entity) {
        // fix the ridden entity first, then this entity's own id
        Dynamic<?> fixed = entity.update("Riding", EntityIdentifierFix::fixEntity);
        String renamed = RENAMES.get(fixed.get("id").asString(""));
        return renamed == null ? fixed : fixed.set("id", fixed.createString(renamed));
    }
}
