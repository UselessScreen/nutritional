package com.ThePheonix3k.nutritional.Blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;

public class NutritionalFarmlandBlockBlockEntity extends BlockEntity {
    //start of new stuff

    public float nitrogenLevel;
    public float phosphorusLevel;
    public float potassiumLevel;

    private double hydrationLevel;

    public NutritionalFarmlandBlockBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.hydrationLevel = findNearestWaterDistance(world, pos);
//        float localdistance = findNearestWaterDistance(world, pos);
//        if(Float.isInfinite(localdistance)) {
//            this.hydrationLevel = 0.0f;
//        } else {
//            this.hydrationLevel = localdistance * 100.0f;
//        }
    }

    public double findNearestWaterDistance(WorldView world, BlockPos pos) {
        double waterDist = 0;

        for(int i = -1; i <= 1; ++i) {
            for(int j = -1; j <= 1; ++j) {
                for(int k = -1; k <= 1; ++k) {
                    BlockPos p = pos.add(i, k, j);
                    if (world.getFluidState(p).isIn(FluidTags.WATER)) {
                        double dx = (p.getX()) - (pos.getX());
                        double dy = (p.getY()) - (pos.getY());
                        double dz = (p.getZ()) - (pos.getZ());
                        waterDist = Math.abs(dx) + Math.abs(dy) + Math.abs(dz);
                    }
                }
            }
        }
        return waterDist;
    }

    @Override
    public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        // Save the current value of the number to the nbt
        super.writeNbt(nbt, registries);
        nbt.putInt("number", number);
    }
}
