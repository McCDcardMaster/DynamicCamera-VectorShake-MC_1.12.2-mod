package daniel;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


@Mod(modid = DynamicCamera.MOD_ID, name = DynamicCamera.NAME, version = DynamicCamera.VERSION)
public class DynamicCamera {

    public static final String MOD_ID = "daniel";
    public static final String NAME = "Dynamic Camera";
    public static final String VERSION = "1.1";
    @SidedProxy(clientSide = "daniel.ClientProxy", serverSide = "daniel.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
    }
}
