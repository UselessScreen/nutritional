package com.ThePheonix3k.nutritional.Blocks;

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
    public float nitrogenLevel;
    public float phosphorusLevel;
    public float potassiumLevel;

    private double hydrationLevel;

    public NutritionalFarmlandBlockBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.NUTRITIONAL_FARMLAND_BLOCK_ENTITY, pos, state);
        this.nitrogenLevel = 0.0f;
        this.phosphorusLevel = 0.0f;
        this.potassiumLevel = 0.0f;
        this.hydrationLevel = 0.0;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.hydrationLevel = findNearestWaterDistance(world, pos);
        markDirty();
    }

    public double getHydrationLevel() {
        return this.hydrationLevel;
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
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        nbt.putFloat("nitrogenLevel", nitrogenLevel);
        nbt.putFloat("phosphorusLevel", phosphorusLevel);
        nbt.putFloat("potassiumLevel", potassiumLevel);
        nbt.putDouble("hydrationLevel", hydrationLevel);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        this.nitrogenLevel = nbt.getFloat("nitrogenLevel");
        this.phosphorusLevel = nbt.getFloat("phosphorusLevel");
        this.potassiumLevel = nbt.getFloat("potassiumLevel");
        this.hydrationLevel = nbt.getDouble("hydrationLevel");
    }
}
