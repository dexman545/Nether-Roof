package dex.netherroof;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.gamerule.v1.CustomGameRuleCategory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class NetherRoof implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();
    public static final CustomGameRuleCategory GREEN_CATEGORY = new CustomGameRuleCategory(new Identifier("netherroof", "dodeath"),
            new LiteralText("Void Nether Roof Mod").styled(style -> style.withBold(true).withColor(Formatting.DARK_GREEN)));

    public static final String MOD_ID = "netherroof";
    public static final String MOD_NAME = "NetherRoofMod";
    public static final int NETHER_HEIGHT = 128;
    public static final GameRules.Key<GameRules.BooleanRule> DO_DEATH = register("killOnRoof", GREEN_CATEGORY, GameRuleFactory.createBooleanRule(true));

    private static <T extends GameRules.Rule<T>> GameRules.Key<T> register(String name, CustomGameRuleCategory category, GameRules.Type<T> type) {
        return GameRuleRegistry.register(name, category, type);
    }

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");

        ServerTickEvents.END_SERVER_TICK.register(minecraftServer -> {
            minecraftServer.getPlayerManager().getPlayerList().forEach(playerEntity -> {
                if (Objects.requireNonNull(playerEntity.getServer()).getGameRules().getBoolean(DO_DEATH) && playerEntity.getServerWorld().getDimension().hasCeiling() && !playerEntity.isCreativeLevelTwoOp()) {
                    Vec3d v = playerEntity.getPos();
                    if (v.y >= NETHER_HEIGHT) {
                        playerEntity.damage(DamageSource.OUT_OF_WORLD, 5f);
                    }
                }
            });
        });
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

}