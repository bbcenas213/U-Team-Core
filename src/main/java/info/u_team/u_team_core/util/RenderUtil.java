package info.u_team.u_team_core.util;

import com.mojang.blaze3d.systems.RenderSystem;

@SuppressWarnings("deprecation")
public class RenderUtil {
	
	public static void enableBlend() {
		RenderSystem.enableBlend();
	}
	
	public static void disableBlend() {
		RenderSystem.disableBlend();
	}
	
	public static void defaultBlendFunc() {
		RenderSystem.defaultBlendFunc();
	}
	
	public static void enableAlphaTest() {
		RenderSystem.enableAlphaTest();
	}
	
	public static void disableAlphaTest() {
		RenderSystem.disableAlphaTest();
	}
	
	public static void defaultAlphaFunc() {
		RenderSystem.defaultAlphaFunc();
	}
	
	public static void enableScissor(int x, int y, int width, int height) {
		RenderSystem.enableScissor(x, y, width, height);
	}
	
	public static void disableScissor() {
		RenderSystem.disableScissor();
	}
	
}
