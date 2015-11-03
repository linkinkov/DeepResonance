package mcjty.deepresonance.blocks.laser;

import mcjty.deepresonance.DeepResonance;
import mcjty.lib.container.GenericGuiContainer;
import mcjty.lib.gui.Window;
import mcjty.lib.gui.layout.PositionalLayout;
import mcjty.lib.gui.widgets.EnergyBar;
import mcjty.lib.gui.widgets.Label;
import mcjty.lib.gui.widgets.Panel;
import mcjty.lib.gui.widgets.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

import java.awt.*;

public class GuiLaser extends GenericGuiContainer<LaserTileEntity> {
    public static final int LASER_WIDTH = 180;
    public static final int LASER_HEIGHT = 152;

    private EnergyBar energyBar;
    private EnergyBar crystalBar;
//    private ImageLabel burningImage;
    private Label percentage;

    private static final ResourceLocation iconLocation = new ResourceLocation(DeepResonance.MODID, "textures/gui/laser.png");
//    private static final ResourceLocation iconBurning = new ResourceLocation(DeepResonance.MODID, "textures/gui/burning.png");

    public GuiLaser(LaserTileEntity laserTileEntity, LaserContainer container) {
        super(DeepResonance.instance, DeepResonance.networkHandler.getNetworkWrapper(), laserTileEntity, container, 0, "laser");
        laserTileEntity.setCurrentRF(laserTileEntity.getEnergyStored(ForgeDirection.DOWN));

        xSize = LASER_WIDTH;
        ySize = LASER_HEIGHT;
    }

    @Override
    public void initGui() {
        super.initGui();

        int maxEnergyStored = tileEntity.getMaxEnergyStored(ForgeDirection.DOWN);
        energyBar = new EnergyBar(mc, this).setVertical().setMaxValue(maxEnergyStored).setLayoutHint(new PositionalLayout.PositionalHint(10, 7, 8, 54)).setShowText(false);
        energyBar.setValue(tileEntity.getCurrentRF());

        crystalBar = new EnergyBar(mc, this).setVertical().setMaxValue(10000).setLayoutHint(new PositionalLayout.PositionalHint(150, 7, 20, 54)).setShowText(false);
        crystalBar.setEnergyOnColor(0xff0066ff);
        crystalBar.setEnergyOffColor(0xff003366);
        crystalBar.setSpacerColor(0xff001122);
        crystalBar.setValue(5000);

//        burningImage = new ImageLabel(mc, this).setImage(iconBurning, 0, 0);
//        burningImage.setLayoutHint(new PositionalLayout.PositionalHint(90, 2, 64, 64));

        percentage = new Label(mc, this);
        percentage.setLayoutHint(new PositionalLayout.PositionalHint(54, 44, 32, 14));

        Widget toplevel = new Panel(mc, this).setBackground(iconLocation).setLayout(new PositionalLayout()).addChild(energyBar).addChild(percentage).addChild(crystalBar);
        toplevel.setBounds(new Rectangle(guiLeft, guiTop, xSize, ySize));

        window = new Window(this, toplevel);
        tileEntity.requestRfFromServer(DeepResonance.networkHandler.getNetworkWrapper());
//        tileEntity.requestProgressFromServer();
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float v, int i, int i2) {
//        int progress = tileEntity.getClientProgress();
//        percentage.setText(progress + "%");

        drawWindow();

        energyBar.setValue(tileEntity.getCurrentRF());

        tileEntity.requestRfFromServer(DeepResonance.networkHandler.getNetworkWrapper());
//        tileEntity.requestProgressFromServer();
    }
}
