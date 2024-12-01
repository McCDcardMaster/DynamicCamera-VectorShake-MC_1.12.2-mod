package daniel.camera;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.util.Random;

public class CameraEvent {
    private float rotationZ = 0; // угол поворота по оси Z
    private float rotationX = 0; // угол поворота по оси X
    private float targetRotationZ = 0; // целевой угол поворота по оси Z
    private float targetRotationX = 0; // целевой угол поворота по оси X
    private float radiusLimit = 3.0f; // ограничение радиуса работы камеры
    private float interpolationSpeed = 0.05f; // скорость интерполяции
    private Random random = new Random(); // генератор случайных чисел

    @SubscribeEvent
    public void render(EntityViewRenderEvent.CameraSetup e) {
        // Интерполяция для плавного возвращения камеры к rotationZ и rotationX = 0
        rotationZ = rotationZ + (targetRotationZ - rotationZ) * interpolationSpeed;
        rotationX = rotationX + (targetRotationX - rotationX) * interpolationSpeed;

        // Ограничение радиуса
        if (rotationZ > radiusLimit) {
            rotationZ = radiusLimit;
        } else if (rotationZ < -radiusLimit) {
            rotationZ = -radiusLimit;
        }

        // Ограничение угла по X
        if (rotationX > radiusLimit) {
            rotationX = radiusLimit;
        } else if (rotationX < -radiusLimit) {
            rotationX = -radiusLimit;
        }

        // Проверяем, находится ли игрок в состоянии бега
        EntityPlayer player = Minecraft.getMinecraft().player; // Получите ссылку на игрока
        if (player != null) {
            if (player.isSprinting()) {
                // Создаем случайное смещение для тряски
                float shakeX = (random.nextFloat() - 0.5f) * 0.5f; // Случайное значение для X
                float shakeZ = (random.nextFloat() - 0.5f) * 0.5f; // Случайное значение для Z
                rotationX += shakeX; // Применяем смещение по X
                rotationZ += shakeZ; // Применяем смещение по Z
            }

            // Проверяем, прыгает ли игрок
            if (player.isAirBorne) {
                targetRotationX = -150; // Наклоняем камеру вниз при прыжке
            } else {
                targetRotationX = 0; // Возвращаемся в нормальное состояние, когда не прыгаем
            }
        }

        GlStateManager.rotate(rotationZ, 0, 0, 1);  // наклон по Z
        GlStateManager.rotate(rotationX, 1, 0, 0);  // наклон по X
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        // Проверяем, нажаты ли клавиши A или D
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            targetRotationZ = -4; // Устанавливаем целевой угол поворота в минус
        } else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            targetRotationZ = 4; // Устанавливаем целевой угол поворота в плюс
        } else {
            targetRotationZ = 0; // Если ни одна из клавиш не нажата, возвращаемся к rotationZ = 0
        }
    }
}
