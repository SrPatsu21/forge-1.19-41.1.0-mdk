package com.github.uranus_mod_group.uranus_mod.block.custom;
// no use
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class ManaGemOre extends Block {

    public static BooleanProperty LIT = BooleanProperty.create("lit");

    public ManaGemOre(Properties properties){
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult result) {
        if(!level.isClientSide()) {
            level.setBlock(blockPos, state.cycle(LIT), 3);
        }

        return super.use(state, level, blockPos, player, hand, result);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(LIT);
    }


}
