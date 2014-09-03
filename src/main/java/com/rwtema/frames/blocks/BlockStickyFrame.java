package com.rwtema.frames.blocks;

import framesapi.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockStickyFrame extends BlockFrame {
    public final int index;
    public static BlockStickyFrame[] blocks = new BlockStickyFrame[4];

    public BlockStickyFrame(int i) {
        super();
        index = i * 16;
        blocks[i] = this;
    }

    IIcon filled;

    @Override
    public boolean isStickySide(World world, BlockPos pos, ForgeDirection side) {
        return ((index + world.getBlockMetadata(pos.x, pos.y, pos.z)) & (1 << side.ordinal())) != 0;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return ((index + meta) & (1 << side)) != 0 ? filled : blockIcon;
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        filled = p_149651_1_.registerIcon("stonebrick");
        super.registerBlockIcons(p_149651_1_);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        ItemStack item = player.getHeldItem();
        if (item == null || !item.getItem().equals(Items.stick))
            return false;

        int a = (index + world.getBlockMetadata(x, y, z)) ^ (1 << side);

        if (a > 63 || a < 0)
            a = 0;

        int meta = a % 16;
        Block block = blocks[(a - meta) / 16];

        world.setBlock(x, y, z, block, meta, 2);
        return true;
    }
}
